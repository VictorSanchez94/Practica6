package utils;

import java.util.ArrayList;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;
import org.owasp.esapi.codecs.HTMLEntityCodec;
import org.owasp.esapi.errors.EncodingException;

public class EsapiUtilities {
	
	private static Codec ORACLE_CODEC = new OracleCodec();
	private static Codec HTML_CODEC = new HTMLEntityCodec();

	/**
	 * Pre: ---
	 * Post: Return canonicalized input
	 */
	public static String canonicalize(String input) {
		return ESAPI.encoder().canonicalize(input);
	}
	
	//VALIDATION METHODS
	public static boolean validateName (String input) {
		return ESAPI.validator().isValidInput("name", input, "name", 50, false);
	}
	
	public static boolean validateDirection (String input) {
		return ESAPI.validator().isValidInput("direction", input, "direction", 50, false);
	}
	
	public static boolean validateCreditCard(String type, String number, 
			String expirationMonth, String expirationYear, String CVN) {
		boolean valid = true;
		valid &= ESAPI.validator().isValidInput("creditCardType", type, "creditCardType", 4, false);
		valid &= ESAPI.validator().isValidInput("creditCardNumber", number, "creditCardNumber", 16, false);
		valid &= ESAPI.validator().isValidInput("creditCardExpirationMonth", expirationMonth, 
				"creditCardExpirationMonth", 2, false);
		valid &= ESAPI.validator().isValidInput("creditCardExpirationYear", expirationYear, 
				"creditCardExpirationYear", 4, false);
		valid &= ESAPI.validator().isValidInput("creditCardCVN", CVN, "creditCardCVN", 3, false);
		return valid;
	}
	
	public static boolean validateDNI (String input) {
		return ESAPI.validator().isValidInput("dni", input, "DNI", 9, false);
	}
	
	//ENCODING METHODS
	public static ArrayList<String> encoder (String input, boolean sql, boolean html, boolean url) throws EncodingException {
		ArrayList<String> list = new ArrayList<String>();
		if(sql){
			list.add("SQL: "+sqlEncoder(input));
		}
		if(html){
			list.add("HTML: "+htmlEncoder(input));
		}
		if(url){
			list.add("URL: "+urlEncoder(input));
		}
		return list;
	}

	private static String sqlEncoder(String name) {

		return ESAPI.encoder().encodeForSQL(ORACLE_CODEC, name);
	}
	
	private static String htmlEncoder(String name) {
		return ESAPI.encoder().encodeForHTML(name);
	}

	private static String urlEncoder(String name) throws EncodingException {
		return ESAPI.encoder().encodeForURL(name);
	}
}
