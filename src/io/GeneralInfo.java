package io;

public class GeneralInfo {
	private String XMLFolder;
	private String DBFolder;
	private int Delay;
	private boolean firstInit;
	private float version;
	
	public GeneralInfo() {
		super();
	}
	
	public GeneralInfo(String XMLFolder, String DBFolder, int Delay, boolean firstInit, float version) {
		this.XMLFolder = XMLFolder;
		this.DBFolder = DBFolder;
		this.Delay = Delay;
		this.firstInit = firstInit;
		this.version = version;
	}
	
	public String getXMLFolder() {
		return this.XMLFolder;
	}
	public void setXMLFolder(String XMLFolder) {
		this.XMLFolder = XMLFolder;
	}
	
	public String getDBFolder() {
		return this.DBFolder;
	}
	public void setDBFolder(String DBFolder) {
		this.DBFolder = DBFolder;
	}
	
	public int getDelay() {
		return this.Delay;
	}
	public void setDelay(int Delay) {
		this.Delay = Delay;
	}
	
	public boolean getFirstInit() {
		return this.firstInit;
	}
	public void setFirstInit(boolean firstInit) {
		this.firstInit = firstInit;
	}
	
	public float getVersion() {
		return this.version;
	}
	public void setVersion(float version) {
		this.version = version;
	}
	
	public String toString() {
		return getXMLFolder() + ";" + getDBFolder() + ";" + getDelay() + ";" + getFirstInit() + ";" + getVersion();
	}
	
	public void fromString(String string) {
		String[] data = string.split(";");
		setXMLFolder(data[0]);
		setDBFolder(data[1]);
		setDelay(Integer.parseInt(data[2]));
		setFirstInit(Boolean.parseBoolean(data[3]));
		setVersion(Float.parseFloat(data[4]));
	}
}
