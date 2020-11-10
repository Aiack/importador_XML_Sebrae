package XMLPipe;

import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DerbyHelper {
    private Connection connect = null;
    
    String dataBasePath;
	
	public DerbyHelper(String dataBasePath) throws Exception {
		try {
			this.dataBasePath = dataBasePath;
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			connect = DriverManager.getConnection("jdbc:derby:" + dataBasePath);
		}
		catch (Exception e) {
			throw new Exception("Conexão do DB sob acesso, feche o sistema da SEBRAE");
		}
	}
	
	public ArrayList<String> getCompanysNrDocument() throws SQLException {
		ArrayList<String> result = new ArrayList<String>();
		PreparedStatement statement = connect.prepareStatement("SELECT NR_DOCUMENTO FROM NFE.EMITENTE");
		ResultSet resultSet = statement.executeQuery();
		
		if(resultSet.next()) {
			result.add(resultSet.getString("NR_DOCUMENTO"));
		}
		return result;
	}
	
	public void insertNewNF(HashMap<String, String> data, InputStream nfZip, int emitenteId, int idNum) throws Exception {
		if(checkIfUnique(data.get("NUMERO"), data.get("SERIE"), data.get("ANO"), emitenteId)) {
			closeConnection();
			throw new Exception("Nota Fiscal já cadastrado no banco de dados");
		}
		
		PreparedStatement ps = connect.prepareStatement(
				"INSERT INTO NFE.NOTA_FISCAL (" +
				"ID_NOTA_FISCAL," + 
				"NUMERO," + 
				"SERIE," + 
				"MODELO," + 
				"SITUACAO," + 
				"MES," + 
				"ANO," + 
				"TIPO_EMISSAO," + 
				"DATA_EMISSAO," + 
				"DATA_AUTORIZACAO," + 
				"CODIGO_NUMERICO_CHAVE_ACESSO," + 
				"DIGITO_CHAVE_ACESSO," + 
				"AUTORIZACAO_EXPORTADA_XML," + 
				"DOCUMENTO_DEST," + 
				"UF_DEST," + 
				"NUMERO_RECIBO," + 
				"DANFE_IMPRESSO," + 
				"DOC_XML," + 
				"DATA_SISTEMA," + 
				"PROTOCOLO," + 
				"NUMERO_PROTOCOLO," + 
				"DATA_PROTOCOLO," + 
				"CODIGO_UF_EMITENTE," + 
				"ID_EMITENTE," + 
				"ID_LOTE," + 
				"CODIGO_ERRO," + 
				"MENSAGEM_ERRO," + 
				"VERSAO)"+ " VALUES " +
				"(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		
		ps.setInt(1, idNum);
		setCharOrNull(2,data.get("NUMERO"),ps);
		setCharOrNull(3,data.get("SERIE"),ps);
		setCharOrNull(4,data.get("MODELO"),ps);
		setCharOrNull(5,data.get("SITUACAO"),ps);
		setCharOrNull(6,data.get("MES"),ps);
		setCharOrNull(7,data.get("ANO"),ps);
		setCharOrNull(8,data.get("TIPO_EMISSAO"),ps);
		setTimeStampOrNull(9,data.get("DATA_EMISSAO"),ps);
		setTimeStampOrNull(10,data.get("DATA_AUTORIZACAO"),ps);
		setCharOrNull(11,data.get("CODIGO_NUMERICO_CHAVE_ACESSO"),ps);
		setCharOrNull(12,data.get("DIGITO_CHAVE_ACESSO"),ps);
		setCharOrNull(13,data.get("AUTORIZACAO_EXPORTADA_XML"),ps);
		setCharOrNull(14,data.get("DOCUMENTO_DEST"),ps);
		setCharOrNull(15,data.get("UF_DEST"),ps);
		setCharOrNull(16,data.get("NUMERO_RECIBO"),ps);
		setCharOrNull(17,data.get("DANFE_IMPRESSO"),ps);
		ps.setBinaryStream(18,nfZip);
		setTimeStampOrNull(19,data.get("DATA_SISTEMA"),ps);
		setBlobToNull(20,data.get("PROTOCOLO"),ps);
		setCharOrNull(21,data.get("NUMERO_PROTOCOLO"),ps);
		setTimeStampOrNull(22,data.get("DATA_PROTOCOLO"),ps);
		setCharOrNull(23,data.get("CODIGO_UF_EMITENTE"),ps);
		ps.setInt(24, emitenteId); //Found a way to found the emitent ID
		setIntOrNull(25,data.get("ID_LOTE"),ps);
		setCharOrNull(26,data.get("CODIGO_ERRO"),ps);
		setCharOrNull(27,data.get("MENSAGEM_ERRO"),ps);
		setCharOrNull(28,data.get("VERSAO"),ps);
		
		ps.execute();	
	}
	
	public void closeConnection() throws SQLException {
		try {
			connect = DriverManager.getConnection("jdbc:derby:" + dataBasePath + ";shutdown=true");
		} catch (Exception e) {
			return;
		}
	}
	
	public int getNextId() throws SQLException {
		int nextId = 0;
		PreparedStatement statement = connect.prepareStatement("SELECT MAX(ID_NOTA_FISCAL) AS MAXIMUM FROM NFE.NOTA_FISCAL");
		
		ResultSet resultSet = statement.executeQuery();
		
		if(resultSet.next()) {
			nextId = resultSet.getInt("MAXIMUM");
		}
		return nextId + 1;
	}
	
	public int getCNPJId(String CNPJ) throws SQLException {
		int id = -1;
		PreparedStatement statement = connect.prepareStatement("SELECT ID_EMITENTE, NR_DOCUMENTO FROM NFE.EMITENTE");
		
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next()) {
			String cnpj = resultSet.getString("NR_DOCUMENTO");
			if(cnpj.equals(CNPJ)) {
				id = resultSet.getInt("ID_EMITENTE");
			}
		}
		return id;
	}
	
	private boolean checkIfUnique(String NUMERO, String SERIE, String ANO, int ID_EMITENTE) throws SQLException{
		PreparedStatement statement = connect.prepareStatement("SELECT * FROM NFE.NOTA_FISCAL");
		
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next()) {
			String numero = resultSet.getString("NUMERO");
			String serie = resultSet.getString("SERIE");
			String ano = resultSet.getString("ANO");
			int id_emitente = resultSet.getInt("ID_EMITENTE");

			if(numero.equals(NUMERO) && serie.equals(SERIE) && ano.equals(ANO) && id_emitente == ID_EMITENTE) {
				return true;
			}
		}
		return false;
	}
	
	public static void setCharOrNull(int column, String value, PreparedStatement pstmt) throws SQLException
	{
	    if (!value.isEmpty()) {
	        pstmt.setString(column, value);
	    } else {
	        pstmt.setNull(column, java.sql.Types.VARCHAR);
	    }
	}
	
	public static void setIntOrNull(int column, String value, PreparedStatement pstmt) throws NumberFormatException, SQLException
	{
	    if (!value.isEmpty()) {
	        pstmt.setInt(column, Integer.valueOf(value));
	    } else {
	        pstmt.setNull(column, java.sql.Types.INTEGER);
	    }
	}
	
	public static void setBlobToNull(int column, String value, PreparedStatement pstmt) throws NumberFormatException, SQLException
	{
	    pstmt.setNull(column, java.sql.Types.BLOB);
	}
	
	public static void setTimeStampOrNull(int column, String value, PreparedStatement pstmt) throws NumberFormatException, SQLException, ParseException
	{
	    if (!value.isEmpty()) {
	    	SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	    	Date date = dateFormater.parse(value);
	        pstmt.setTimestamp(column, new Timestamp(date.getTime()));
	    } else {
	        pstmt.setNull(column, java.sql.Types.TIMESTAMP);
	    }
	}
}
