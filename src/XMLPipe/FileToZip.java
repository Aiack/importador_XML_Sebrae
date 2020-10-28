package XMLPipe;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileToZip {
	
	String XMLNAME = "NotaFiscalEletronica.xml";
	String OUTPUTNAME = "compressed.zip";
		
	String filePath;
	
	public void StringToFile(String data) throws FileNotFoundException {
		PrintWriter out = new PrintWriter("NotaFiscalEletronica.xml");
		out.print(data);
		out.close();
	}

	
	public void zipFile() throws IOException {
		File file = new File(XMLNAME);
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(new File(OUTPUTNAME));
		
		ZipEntry zipEntry = new ZipEntry(XMLNAME);
		ZipOutputStream zipOut = new ZipOutputStream(fos);
		
		zipOut.putNextEntry(zipEntry);
		
		byte[] bytes = new byte[1024];
		int length;
		while((length = fis.read(bytes)) >= 0) {
			zipOut.write(bytes, 0, length);
		}
		zipOut.close();
		fis.close();
		fos.close();
		file.delete();
	}
	
	public InputStream getBinaryStream() throws FileNotFoundException {
		File file = new File(OUTPUTNAME);
		File zipFile = new File(OUTPUTNAME);
		InputStream fin = new FileInputStream(zipFile);
		file.delete();
		
		return fin;
		
	}
	
}
