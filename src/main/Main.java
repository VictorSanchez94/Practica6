package main;

import java.util.ArrayList;
import java.util.Scanner;

import org.owasp.esapi.errors.EncodingException;

import utils.EsapiUtilities;

public class Main {

	private static boolean canonicalization = false;		//TRUE if we want to canonicalize input
	private static boolean validation = false;				//TRUE if we want to validate input
	private static boolean SQLcofidication = false;			//TRUE if we want to codify SQL
	private static boolean HTMLcofidication = false;		//TRUE if we want to codify HTML
	private static boolean URLcofidication = false;			//TRUE if we want to codify URL
	private static Scanner scanner = new Scanner(System.in);
	
	private static String name;
	private static ArrayList<String> encodedNames;
	private static String direction;
	private static ArrayList<String> encodedDirections;
	private static String creditCardNumber;
	private static ArrayList<String> encodedCDNumbers;
	private static String creditCardType;
	private static ArrayList<String> encodedCDTypes;
	private static String creditCardExpirationMonth;
	private static ArrayList<String> encodedCDExpMonths;
	private static String creditCardExpirationYear;
	private static ArrayList<String> encodedCDExpYears;
	private static String creditCardCVN;
	private static ArrayList<String> encodedCDCVNs;
	private static String DNI;
	private static ArrayList<String> encodedDNIs;
	
	public static void main(String[] args) {
		try {		
			EsapiUtilities.validateDNI("73412089-E");		//useless operation to charge ESAPI configuration files at the begining
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("\n=========================================================================\n");
		
		String argsList = "";
		for (String s : args){
			argsList += s.toLowerCase()+" ";
		}
		argsList = argsList.trim();
			
		//ARGUMENTS PARSER
		if(argsList.contains("-c")){
			canonicalization = true;
		}
		if(argsList.contains("-v")){
			validation = true;
		}
		if(argsList.contains("-e")){
			boolean stop = false;
			argsList = argsList.replaceFirst("-e", "");
			while(!stop){
				if(argsList.contains("sql")){
					argsList= argsList.replace("sql", "");
					SQLcofidication = true;
				}else if(argsList.contains("html")){
					argsList= argsList.replace("html", "");
					HTMLcofidication = true;
				}else if(argsList.contains("url")){
					argsList= argsList.replace("url", "");
					URLcofidication = true;
				}else{
					stop = true;
				}
			}
		}else{
			System.err.println("ERROR. Opcion '-e' debe tener tipo de codificacion a continuacion [ SQL | HTML | URL ].");
			System.exit(1);
			
		}
		
		//NAME PARSE
		System.out.print("Introduzca su nombre: ");
		name = scanner.nextLine();
		if(canonicalization){
			name = EsapiUtilities.canonicalize(name);
		}
		if(validation && !EsapiUtilities.validateName(name)){
			System.err.println("ERROR. Nombre introducido no valido.");
			System.exit(1);
		}
		try {
			encodedNames = EsapiUtilities.encoder(name, SQLcofidication, HTMLcofidication, URLcofidication);
		} catch (EncodingException e) {
			System.err.println("ERROR. No se ha podido codificar el nombre.");
		}
		
		//DIRECTION PARSE
		System.out.print("Introduzca su direccion: ");
		direction = scanner.nextLine();
		if(canonicalization){
			direction = EsapiUtilities.canonicalize(direction);
		}
		if(validation && !EsapiUtilities.validateDirection(direction)){
			System.err.println("ERROR. Direccion introducida no valido.");
			System.exit(1);
		}
		try {
			encodedDirections = EsapiUtilities.encoder(direction, SQLcofidication, HTMLcofidication, URLcofidication);
		} catch (EncodingException e) {
			System.err.println("ERROR. No se ha podido codificar la direccion.");
		}		
		
		//CREDIT CARD PARSE
		//Type
		System.out.print("Introduzca tipo de tarjeta de pago (VISA, MC, AMEX aceptadas): ");
		creditCardType = scanner.nextLine().toUpperCase();
		if(canonicalization){
			creditCardType = EsapiUtilities.canonicalize(creditCardType);
		}
		System.out.print("Introduzca el numero de tarjeta: ");
		creditCardNumber = scanner.nextLine();
		if(canonicalization){
			creditCardNumber = EsapiUtilities.canonicalize(creditCardNumber);
		}
		System.out.print("Introduzca el mes de caducidad de la tarjeta: ");
		creditCardExpirationMonth = scanner.nextLine();
		if(canonicalization){
			creditCardExpirationMonth = EsapiUtilities.canonicalize(creditCardExpirationMonth);
		}
		System.out.print("Introduzca el año de caducidad de la tarjeta: ");
		creditCardExpirationYear = scanner.nextLine();
		if(canonicalization){
			creditCardExpirationYear = EsapiUtilities.canonicalize(creditCardExpirationYear);
		}
		System.out.print("Introduzca el codigo de seguridad de la tarjeta: ");
		creditCardCVN = scanner.nextLine();
		if(canonicalization){
			creditCardCVN = EsapiUtilities.canonicalize(creditCardCVN);
		}
		if(validation && !EsapiUtilities.validateCreditCard(creditCardType, creditCardNumber, creditCardExpirationMonth, creditCardExpirationYear, creditCardCVN)){
			System.err.println("ERROR. Datos de la tarjeta de credito no validos.");
			System.exit(1);
		}
		
		//Credit Card Encoding
		try {
			encodedCDTypes = EsapiUtilities.encoder(creditCardType, SQLcofidication, HTMLcofidication, URLcofidication);
		} catch (EncodingException e) {
			System.err.println("ERROR. No se ha podido codificar el tipo de tarjeta.");
		}
		try {
			encodedCDNumbers = EsapiUtilities.encoder(creditCardNumber, SQLcofidication, HTMLcofidication, URLcofidication);
		} catch (EncodingException e) {
			System.err.println("ERROR. No se ha podido codificar el numero de tarjeta.");
		}
		try {
			encodedCDExpMonths = EsapiUtilities.encoder(creditCardExpirationMonth, SQLcofidication, HTMLcofidication, URLcofidication);
		} catch (EncodingException e) {
			System.err.println("ERROR. No se ha podido codificar el mes de caducidad de la tarjeta.");
		}
		try {
			encodedCDExpYears = EsapiUtilities.encoder(creditCardExpirationYear, SQLcofidication, HTMLcofidication, URLcofidication);
		} catch (EncodingException e) {
			System.err.println("ERROR. No se ha podido codificar el año de caducidad de la tarjeta.");
		}
		try {
			encodedCDCVNs = EsapiUtilities.encoder(creditCardCVN, SQLcofidication, HTMLcofidication, URLcofidication);
		} catch (EncodingException e) {
			System.err.println("ERROR. No se ha podido codificar el CVN de la tarjeta.");
		}
		
		//DNI
		System.out.print("Introduzca su DNI: ");
		DNI = scanner.nextLine();
		if(canonicalization){
			DNI = EsapiUtilities.canonicalize(DNI);
		}
		if(validation && !EsapiUtilities.validateDNI(DNI)){
			System.err.println("ERROR. DNI no valido.");
			System.exit(1);
		}
		try {
			encodedDNIs = EsapiUtilities.encoder(DNI, SQLcofidication, HTMLcofidication, URLcofidication);
		} catch (EncodingException e) {
			System.err.println("ERROR. No se ha podido codificar el DNI.");
		}
		
		//SHOW RESULTS
		System.out.println("\nRESULTADOS:\n");
		//Names
		if(!encodedNames.isEmpty()){
			System.out.println();
			System.out.println("Nombre codificado en distintos formatos:");
			for(String s : encodedNames){
				System.out.println("\t" + s);
			}
		}
		
		//Directions
		if(!encodedDirections.isEmpty()){
			System.out.println();
			System.out.println("Direccion codificada en distintos formatos:");
			for(String s : encodedDirections){
				System.out.println("\t" + s);
			}
		}
		
		//Credit Card Types
		if(!encodedCDTypes.isEmpty()){
			System.out.println();
			System.out.println("Tipo de tarjeta de credito codificado en distintos formatos:");
			for(String s : encodedCDTypes){
				System.out.println("\t" + s);
			}
		}
		
		//Credit Card Numbers
		if(!encodedCDNumbers.isEmpty()){
			System.out.println();
			System.out.println("Numero de tarjeta de credito codificado en distintos formatos:");
			for(String s : encodedCDNumbers){
				System.out.println("\t" + s);
			}
		}
		
		//Credit Card Numbers
		if(!encodedCDExpMonths.isEmpty()){
			System.out.println();
			System.out.println("Mes de caducidad de la tarjeta de credito codificado en distintos formatos:");
			for(String s : encodedCDExpMonths){
				System.out.println("\t" + s);
			}
		}
		
		//Credit Card Numbers
		if(!encodedCDExpYears.isEmpty()){
			System.out.println();
			System.out.println("Año de caducidad de la tarjeta de credito codificado en distintos formatos:");
			for(String s : encodedCDExpYears){
				System.out.println("\t" + s);
			}
		}
		
		//Credit Card Numbers
		if(!encodedCDCVNs.isEmpty()){
			System.out.println();
			System.out.println("CVN de la tarjeta de credito codificado en distintos formatos:");
			for(String s : encodedCDCVNs){
				System.out.println("\t" + s);
			}
		}
		
		//DNI
		if(!encodedDNIs.isEmpty()){
			System.out.println();
			System.out.println("DNI codificado en distintos formatos:");
			for(String s : encodedDNIs){
				System.out.println("\t" + s);
			}
		}
		
	}
	
}
