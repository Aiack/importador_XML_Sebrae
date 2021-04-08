package io;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CompanyInfo {
	private String CNPJ;
	private String name;
	private String certType;
	private String A3Type;
	private String certPassword;
	private Timestamp createDate;
	private String certFolder;
	
	public CompanyInfo() {
		super();
	}
	
	public CompanyInfo(String CNPJ, String name, String certType, String A3Type, String certPassword, String certFolder) {
		super();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		this.CNPJ = CNPJ;
		this.name = name;
		this.certType = certType;
		this.A3Type = A3Type;
		this.certPassword = certPassword;
		this.createDate = timestamp;
		this.certFolder = certFolder;
	}
	
	public String getCNPJ() {
		return this.CNPJ;
	}
	public void setCNPJ(String CNPJ) {
		this.CNPJ = CNPJ;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name.replace(";", "");
	}
	
	public String getCertType() {
		return this.certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
	}	
	
	public String getA3Type() {
		return this.A3Type;
	}
	public void setA3Type(String A3Type) {
		this.A3Type = A3Type;
	}
	
	public String getCertPassword() {
		return this.certPassword;
	}
	public void setCertPassword(String certPassword) {
		this.certPassword = certPassword;
	}
	
	public Timestamp getCreateDate() {
		return this.createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	
	public String getCertFolder() {
		return this.certFolder;
	}
	public void setCertFolder(String certFolder) {
		this.certFolder = certFolder;
	}
	
	public String toString() {
		SimpleDateFormat dateMask = new SimpleDateFormat("dd/MM/yyyy");
		return getCNPJ() + ";" + 
			   getName() + ";" + 
			   getCertType() + ";" + 
			   getA3Type() + ";" + 
			   getCertPassword() + ";" + 
			   getCertFolder() + ";" + 
			   dateMask.format(getCreateDate());
	}
	
	public void fromString(String string) throws ParseException {
		String[] data = string.split(";");
		setCNPJ(data[0]);
		setName(data[1]);
		setCertType(data[2]);
		setA3Type(data[3]);
		setCertPassword(data[4]);
		setCertFolder(data[5]);
		SimpleDateFormat dateMask = new SimpleDateFormat("dd/MM/yyyy");
		Date parsedDate = dateMask.parse(data[6]);
		Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
		setCreateDate(timestamp);
	}
	
}
