package XMLPipe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;



public class XMLInfoExtractor {
	private String path;
	private String qName;
	
	public HashMap<String, String> nota_fiscal;
	public String emitente;
	
	private XMLEvent event;
	private XMLEventReader eventReader;
	
	private boolean isDest;
	private String dest;
	
	private Boolean certA3HasPassed;
	
	
	public XMLInfoExtractor(String path, Boolean certA3HasPassed) throws FileNotFoundException, XMLStreamException, ParseException{
		this.path = path;
		
		this.certA3HasPassed = certA3HasPassed;
		
		nota_fiscal = new HashMap<String, String>();
		emitente = "";
		isDest = false;
		dest = "";
		
		XMLParser();
	}
	
	public void XMLParser() throws FileNotFoundException, XMLStreamException, ParseException{
		XMLInputFactory factory = XMLInputFactory.newInstance();
        eventReader = factory.createXMLEventReader(new FileReader(path));
        
        while(eventReader.hasNext()) {
        	event = eventReader.nextEvent();
        	
        	if(event.getEventType() == XMLStreamConstants.START_ELEMENT) {
        		StartElement startElement = event.asStartElement();
        		qName = startElement.getName().getLocalPart();
        		
        		if(qName.equals("dest")) {
        			isDest = true;
        		}
        		
        		if((qName.equals("CNPJ") || qName.equals("CPF")) && isDest && dest.isEmpty()) {
        			if(qName.equals("CNPJ")) {
        				event = eventReader.nextEvent();
        				Characters characters = event.asCharacters();
        				dest = characters.getData();
        			}
        			if(qName.equals("CPF")) {
        				event = eventReader.nextEvent();
        				Characters characters = event.asCharacters();
        				dest = characters.getData();
        			}
            		nota_fiscal.put("DOCUMENTO_DEST", dest);
        		}
        		
        		if(qName.equals("CNPJ") && emitente.isEmpty()) {
        			event = eventReader.nextEvent();
        			Characters characters = event.asCharacters();
        			emitente = characters.getData();
        		}
        		
        		dictPut("nNF", "NUMERO");
        		dictPut("serie", "SERIE");
        		dictPut("mod", "MODELO");
        		if(this.certA3HasPassed) {
        			nota_fiscal.put("SITUACAO", "ASSINADA");
        		}
        		else {
        			nota_fiscal.put("SITUACAO", "EM_DIGITACAO");
        		}
        		
        		dictPut("dhEmi", "DATA_EMISSAO");
        		nota_fiscal.put("TIPO_EMISSAO", "NORMAL");
        		nota_fiscal.put("DATA_AUTORIZACAO", "");
        		dictPut("cNF", "CODIGO_NUMERICO_CHAVE_ACESSO");
        		dictPut("cDV", "DIGITO_CHAVE_ACESSO");
        		nota_fiscal.put("AUTORIZACAO_EXPORTADA_XML", "0");
        		dictPut("cUF", "UF_DEST");
        		nota_fiscal.put("NUMERO_RECIBO", "");
        		nota_fiscal.put("DANFE_IMPRESSO", "0");
        		dictPut("DATA_SISTEMA", "DATA_SISTEMA");
        		nota_fiscal.put("PROTOCOLO", "");
        		nota_fiscal.put("NUMERO_PROTOCOLO", "");
        		nota_fiscal.put("DATA_PROTOCOLO", "");
        		nota_fiscal.put("ID_LOTE", "");
        		nota_fiscal.put("CODIGO_ERRO", "");
        		nota_fiscal.put("MENSAGEM_ERRO", "");
        		nota_fiscal.put("VERSAO", "4.00");
        		
        		if(!nota_fiscal.containsKey("DATA_SISTEMA")) {
        			String timeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(Calendar.getInstance().getTime());
        			nota_fiscal.put("DATA_SISTEMA", timeStamp);
        		}
        	}
        }
        
	}
	
	void dictPut(String inputName, String outputName) throws XMLStreamException, ParseException {
		if(qName.equals(inputName)) {
			event = eventReader.nextEvent();
			if(event.getEventType() == XMLStreamConstants.CHARACTERS) {
				Characters characters = event.asCharacters();
				
				String data = null;
				
				if(inputName.equals("nNF")) {
					data = String.format("%09d", Integer.parseInt(characters.getData()));
				}
				else if(inputName.equals("nNF")){
					data = String.format("%03d", Integer.parseInt(characters.getData()));
				}
				else if(outputName.equals("DATA_EMISSAO")){
					String dateString = characters.getData();
					SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					Date date = dateFormater.parse(dateString);
					LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

					data = String.format("%02d", localDate.getMonthValue());
					nota_fiscal.put("MES", data);
					
					data = String.valueOf(localDate.getYear()).substring(2, 4);
					nota_fiscal.put("ANO", data);
					
					data = dateString.substring(0, dateString.length() - 6) + "Z";
					nota_fiscal.put("DATA_EMISSAO", data);
					
					return;
				}
				else if(outputName.equals("SERIE")){
					data = String.format("%03d", Integer.parseInt(characters.getData()));
				}
				else if(outputName.equals("UF_DEST")){
					data = UfCodeToString(Integer.parseInt(characters.getData()));
					nota_fiscal.put("CODIGO_UF_EMITENTE", characters.getData());
				}
				else {
					data = characters.getData();
				}
				
				
				nota_fiscal.put(outputName, data);
			}
		}
	}
	
	private String UfCodeToString(int cod) {
		String ufName = "";
		switch (cod) {
		case 11:
			ufName = "RO";
			break;
		case 12:
			ufName = "AC";
			break;
		case 13:
			ufName = "AM";
			break;
		case 14:
			ufName = "RR";
			break;
		case 15:
			ufName = "PA";
			break;
		case 16:
			ufName = "AP";
			break;
		case 17:
			ufName = "TO";
			break;
		case 22:
			ufName = "PI";
			break;
		case 23:
			ufName = "CE";
			break;
		case 24:
			ufName = "RN";
			break;
		case 25:
			ufName = "PB";
			break;
		case 21:
			ufName = "MA";
			break;
		case 26:
			ufName = "PE";
			break;
		case 27:
			ufName = "AL";
			break;
		case 28:
			ufName = "SE";
			break;
		case 29:
			ufName = "BA";
			break;
		case 31:
			ufName = "MG";
			break;
		case 32:
			ufName = "ES";
			break;
		case 35:
			ufName = "SP";
			break;
		case 41:
			ufName = "PR";
			break;
		case 42:
			ufName = "SC";
			break;
		case 43:
			ufName = "RS";
			break;
		case 50:
			ufName = "MS";
			break;
		case 51:
			ufName = "MT";
			break;
		case 52:
			ufName = "GO";
			break;
		case 53:
			ufName = "DF";
			break;
		default:
			break;
		}
		
		return ufName;
	}
}
 