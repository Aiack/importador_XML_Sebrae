package utils;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;  
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Set; 

public class WindowsRepo {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private static KeyStore keyStore;
	private static Enumeration <String> al;
	
	
	public WindowsRepo() throws Exception {
		try {
			keyStore = KeyStore.getInstance("Windows-MY", "SunMSCAPI");
			keyStore.load(null, null);
			al = keyStore.aliases();
		} 
		catch (Exception e) {
			throw e;
		}
	}
	
	public ArrayList<String> getWindowsCert() throws Exception {
		ArrayList<String> items = new ArrayList<>();
		
		try {
			while (al.hasMoreElements()) {
				String alias = al.nextElement();
				if (keyStore.containsAlias(alias)) {
					items.add(alias);
				}
			}
		} 
		catch (Exception e) {
			throw e;
		}
		
		return items;
	}
	
	public X509Certificate getCertFromString(String certName) throws KeyStoreException {
		al = keyStore.aliases();
		while (al.hasMoreElements()) {
			String alias = al.nextElement();
			if (keyStore.containsAlias(alias)) {
				if (Objects.equals(alias, certName)) {
					return (X509Certificate) keyStore.getCertificate(alias);
				}
			}
		}
		return null;
	}

}
