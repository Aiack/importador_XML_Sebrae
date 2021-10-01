package XMLPipe;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import io.CompanyInfo;
import io.ConfigIO;

public class XMLToDBPipeline {

	public XMLToDBPipeline(String PATH, String dataBasePath) throws Exception {
		Boolean certA3HasPassed = true;
		
		ConfigIO configIO = new ConfigIO();
		
		System.out.println("Before xmlInfoExtractor");
		
		XMLInfoExtractor xmlInfoExtractor = new XMLInfoExtractor(PATH, certA3HasPassed);
		
		System.out.println("After xmlInfoExtractor");
		DerbyHelper derbyHelper = new DerbyHelper(dataBasePath);
		
		int indexCounter = 0;
		int emitIndex = -1;
		
		for(CompanyInfo cInfo : configIO.companyInfos) {
			if(cInfo.getCNPJ().equals(xmlInfoExtractor.emitente)) {
				emitIndex = indexCounter; 
			}
			indexCounter ++;
		}
		if(emitIndex == -1) {
			throw new Exception("Empresa emitente da NF não cadastrada, sistema pausado.");
		}
		
		//Check if the CNPJ is on the SEBRAE database
		int emitId = derbyHelper.getCNPJId(xmlInfoExtractor.emitente);
		if(emitId == -1) {
			throw new Exception("Empresa emitente da NF não cadastrada no sistema da SEBRAE, sistema pausado.");
		}
		
		
		String signed_xml;
		if(configIO.companyInfos.get(emitIndex).getCertType().equals("A1")) {
			new AssinarXMLsCertfificadoA1();
			String certPath = configIO.companyInfos.get(emitIndex).getCertFolder();
			String password = configIO.companyInfos.get(emitIndex).getCertPassword();
			signed_xml = AssinarXMLsCertfificadoA1.assinarArquivo(certPath, password, PATH);
		}
		else {
			//new AssinarXMLsCertfificadoA3();
			//String password = configIO.companyInfos.get(emitIndex).getCertPassword();
			//String certType = configIO.companyInfos.get(emitIndex).getCertType();
			//signed_xml = AssinarXMLsCertfificadoA3.assinarArquivo(password, PATH, certType);
			//if(signed_xml == null) {
				certA3HasPassed = false;
				signed_xml = AssinarXMLsCertfificadoA1.lerXML(PATH);
			//}
		}
		
		FileToZip fileToZip = new FileToZip();
		fileToZip.StringToFile(signed_xml);
		fileToZip.zipFile();
		InputStream fis = fileToZip.getBinaryStream();
		
		int nextId = derbyHelper.getNextId();
		
		xmlInfoExtractor = new XMLInfoExtractor(PATH, certA3HasPassed);
		
		derbyHelper.insertNewNF(xmlInfoExtractor.nota_fiscal, fis, emitId, nextId);
		derbyHelper.closeConnection();
		
		if(!certA3HasPassed) {
			throw new Exception("Erro ao processar nota com certificado A3, nota inserida como EM DIGITAÇÂO");
		}
		
	}
}
