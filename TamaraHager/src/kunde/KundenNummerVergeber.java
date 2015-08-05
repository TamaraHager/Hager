package kunde;

import java.io.Serializable;

/**
 * @author Tamara Hager
 *generiert automatisch eine Kundennummer
 */
public class KundenNummerVergeber implements Serializable {

	// Singleton
	private static KundenNummerVergeber ref = null;

	private KundenNummerVergeber() {
	}

	public static KundenNummerVergeber getExample() {
		if (ref == null) { // lazy initialization
			ref = new KundenNummerVergeber();
		}
		return ref;
	}

	/**
	 * @return randmKundenNummer, eine durch zufall generierte Zahl,
	 * bestehend aus drei Kleinbuchstaben, drei Zahlen und drei Großbuchstaben
	 */
	public static String createKundenNummer() {

		String randomKundenNummer ="";
		// Array befüllen mit kleinen Buchstaben von a-z
		char[] smallAlphabet = new char[26];
		char a = 97; // erster Buchstabe (kleines a)
		for (int i = 0; i < smallAlphabet.length; i++, a++) {
			smallAlphabet[i] = a;
		}

		// Array befüllen mit großen Buchstaben von A-Z
		char[] bigAlphabet = new char[26];
		char großA = 65; // erster Buchstabe (großes A)
		for (int i = 0; i < smallAlphabet.length; i++, großA++) {
			bigAlphabet[i] = großA;
		}

		// 3 random small Letters
		for (int zahl = 0; zahl < 3; zahl++) {
			int smallLetter = (int) (Math.random() * 26);
			String s = Character.toString(smallAlphabet[smallLetter]);
			randomKundenNummer = randomKundenNummer.concat(s); // concat -> verbindet 2 Strings zu einem
		}
		// 3 random numbers
		for (int i = 0; i < 3; i++) {
			int zahl = (int) (Math.random() * 10);
			String s = Integer.toString(zahl);
			randomKundenNummer = randomKundenNummer.concat(s); // concat -> verbindet 2 Strings zu einem
		}
		// 3 random big letters
		for (int zahl = 0; zahl < 3; zahl++) {
			int bigLetter = (int) (Math.random() * 26);
			String s = Character.toString(bigAlphabet[bigLetter]);
			randomKundenNummer = randomKundenNummer.concat(s); // concat -> verbindet 2 Strings zu einem
		}
		return randomKundenNummer;
	}

}// end of class
