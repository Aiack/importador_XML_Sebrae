package io;

import java.awt.print.Printable;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import sun.security.util.Length;


public class ConfigIO {
	private final String DIRETORIO = ".";
	private final String ARQUIVO = "config.cf";
	
	private HashMap<String, String> config;
	
	public ConfigIO() throws Exception {
		File tmpDir = new File(DIRETORIO + "\\" + ARQUIVO);
		if(!tmpDir.exists()) {
			setNewConfig();
			saveConfig(config);
		}
		else {
			config = getConfig();
		}
	}
	
	public String get(String key) {
		String value = config.get(key);
		if(value.equals("null")) {
			value = "";
		}
		return value;
	}
	
	public void updateConfig() throws Exception {
		config = getConfig();
	}
	
	public void set(String key, String value) throws Exception {
		config = getConfig();
		if(config.containsKey(key)) {
			config.replace(key, value.replace(",", " ").replace(";", " "));
			saveConfig(config);
		}
	}
	
	private void setNewConfig() {
		config = new HashMap<String, String>();
		config.put("tfCompanyName", "");
		config.put("txtFilePassword", "");
		config.put("tfCertFolder", "");
		config.put("cbWindowsRepository", "");
		config.put("tfXMLFolder", "");
		config.put("tfDBfolder", "");
		config.put("rdbtn", "0");
		config.put("tfDelay", "5");
		config.put("firstInit", "");
	}
	
	private void saveConfig(HashMap<String, String> cf) throws Exception{
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(DIRETORIO, ARQUIVO), false));
			writer.write(toString(cf));
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
	
	private HashMap<String, String> getConfig() throws Exception{
		HashMap<String, String> lista = new HashMap<String, String>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(DIRETORIO, ARQUIVO)));
			String linha = reader.readLine();
			while(linha != null) {
				String[] tokens = linha.split(";");
				
				for(String token : tokens) {
					String[] data = token.split(",");
					lista.put(data[0], data[1]);
				}				
				linha = reader.readLine();
			}
			
			reader.close();
		}
		catch (FileNotFoundException fnfe) {
			throw new Exception("Arquivo não encontrado");
		}
		catch (Exception e) {
			throw new Exception("Problemas na leitura do arquivo do evento");
		}
		
		return lista;
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
}
