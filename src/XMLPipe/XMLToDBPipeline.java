package XMLPipe;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import io.CompanyInfo;
import io.ConfigIO;

public class XMLToDBPipeline {

	public XMLToDBPipeline(String PATH, String dataBasePath) throws Exception {
		ConfigIO configIO = new ConfigIO();
		
		XMLInfoExtractor xmlInfoExtractor = new XMLInfoExtractor(PATH);
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
			throw new Exception("Empresa emitente da NF não cadastrada no sistema SEBRAE, sistema pausado.");
		}
		
		String signed_xml;
		if(configIO.companyInfos.get(emitIndex).getCertType().equals("A!")) {
			new AssinarXMLsCertfificadoA1();
			String certPath = configIO.companyInfos.get(emitIndex).getCertFolder();
			String password = configIO.companyInfos.get(emitIndex).getCertPassword();
			signed_xml = AssinarXMLsCertfificadoA1.assinarArquivo(certPath, password, PATH);
		}
		else {
			new AssinarXMLsCertfificadoA3();
			String password = configIO.companyInfos.get(emitIndex).getCertPassword();
			String certType = configIO.companyInfos.get(emitIndex).getCertType();
			signed_xml = AssinarXMLsCertfificadoA3.assinarArquivo(password, PATH, certType);
		}
		
		FileToZip fileToZip = new FileToZip();
		fileToZip.StringToFile(signed_xml);
		fileToZip.zipFile();
		InputStream fis = fileToZip.getBinaryStream();
		
		int nextId = derbyHelper.getNextId();
		
		derbyHelper.insertNewNF(xmlInfoExtractor.nota_fiscal, fis, 1, nextId);
		derbyHelper.closeConnection();
	}
}
