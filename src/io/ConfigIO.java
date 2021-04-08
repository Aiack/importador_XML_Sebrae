package io;

import java.awt.print.Printable;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import sun.security.util.Length;


public class ConfigIO {
	private final String DIRETORIO = ".";
	private final String ARQUIVO = "config.cf";
		
	public GeneralInfo generalInfo;
	public List<CompanyInfo> companyInfos = new ArrayList<CompanyInfo>();
	
	public ConfigIO() throws Exception {
		File tmpDir = new File(DIRETORIO + "\\" + ARQUIVO);
		if(!tmpDir.exists()) {
			System.out.println("Directory don't exist");
			setNewConfig();
			saveConfig();
		}
		else {
			System.out.println("The file was found");
			//Check if config is updated then update it
			try {
				getConfig();
				System.out.println("config loaded with sucess");
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				setNewConfig();
				saveConfig();
			}
		}
	}
	
//	public String get(String key) {
//		String value = config.get(key);
//		if(value.equals("null")) {
//			value = "";
//		}
//		return value;
//	}
	
//	public void updateConfig() throws Exception {
//		config = getConfig();
//	}
	
//	public void set(String key, String value) throws Exception {
//		config = getConfig();
//		if(config.containsKey(key)) {
//			config.replace(key, value.replace(",", " ").replace(";", " "));
//			saveConfig(config);
//		}
//	}
	
	private void setNewConfig() {
		generalInfo = new GeneralInfo("", "", 5, true, (float) 1.1);
	}
	
	public void saveConfig() throws Exception{
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(DIRETORIO, ARQUIVO), false));
			writer.write(generalInfo.toString());
			for(CompanyInfo companyInfo : companyInfos) {
				writer.newLine();
				writer.write(companyInfo.toString());
			}
			writer.newLine();
			writer.close();
		}
		catch(FileNotFoundException fnfe) {
			throw new Exception("Arquivo não encontrado");
		}
		catch (Exception e) {
			throw new Exception("Problemas na gravação do arquivo");
		}
	}
	
	public void getConfig() throws Exception{		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(DIRETORIO, ARQUIVO)));
			String linha = reader.readLine();
			int lineCount = 1;
			companyInfos = new ArrayList<CompanyInfo>();
			while(linha != null) {
				if(lineCount == 1) {
					System.out.println(linha);
					generalInfo = new GeneralInfo();
					generalInfo.fromString(linha);
				}
				else {
					System.out.println(linha);
					if(!linha.isEmpty()) {
						CompanyInfo cInfo = new CompanyInfo();
						cInfo.fromString(linha);						
						companyInfos.add(cInfo);
					}
				}
				linha = reader.readLine();
				lineCount = lineCount + 1;
			}
			System.out.println("Finished reading");
			System.out.println(companyInfos);
			reader.close();
		}
		catch (FileNotFoundException fnfe) {
			throw new Exception("Arquivo não encontrado");
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Problemas na leitura do arquivo do evento " + e + " " + e.getMessage());
		}		
	}
	
	private String toString(HashMap<String, String> lista) {
		String data = "";
		for(Map.Entry m : lista.entrySet()){
			String value = (String) m.getValue();
			if( value.isEmpty()) {
				value = "null";
			}
			data = data + m.getKey() + "," + value + ";";
		}
		return data = data.substring(0, data.length() - 1);
	}
	
	public String[][] getCompanyInfoList() throws Exception{
		getConfig();
		String[][] data = {};
		for(CompanyInfo companyInfo : companyInfos) {
			SimpleDateFormat dateMask = new SimpleDateFormat("dd/MM/yyyy");
			String[][] tempData = {{companyInfo.getCNPJ(), companyInfo.getName(), companyInfo.getCertType(), dateMask.format(companyInfo.getCreateDate())}};
			data = append(data, tempData);
		}
		return data;
	}
	
    public static String[][] append(String[][] a, String[][] b) {
        String[][] result = new String[a.length + b.length][];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
}
