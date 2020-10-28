package XMLPipe;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class XMLToDBPipeline {

	public XMLToDBPipeline(String PATH, String CERT_PATH,String PASSWORD, String dataBasePath) throws Exception {
		XMLInfoExtractor xmlInfoExtractor = new XMLInfoExtractor(PATH);
		
		DerbyHelper derbyHelper = new DerbyHelper(dataBasePath);
		
		new AssinarXMLsCertfificadoA1();
		String signed_xml = AssinarXMLsCertfificadoA1.assinarArquivo(CERT_PATH, PASSWORD, PATH);
		
		
		FileToZip fileToZip = new FileToZip();
		fileToZip.StringToFile(signed_xml);
		fileToZip.zipFile();
		InputStream fis = fileToZip.getBinaryStream();
		
		int nextId = derbyHelper.getNextId();
		
		derbyHelper.insertNewNF(xmlInfoExtractor.nota_fiscal, fis, 1, nextId);
		derbyHelper.closeConnection();
	}
}
