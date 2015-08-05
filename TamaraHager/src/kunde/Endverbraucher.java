package kunde;

/**
 * @author Tamara Hager
 * AKunde ist die Superklasse von Endverbraucher
 *
 */
public class Endverbraucher extends AKunde {

	private boolean bonitaet;
	
	
	//Konstruktor
	public Endverbraucher(){
	}
	
	/**
	 * @param rabatt
	 * @param bonitaet
	 */
	public Endverbraucher(double rabatt, boolean bonitaet) {
		this.rabatt = 0.0;
		this.bonitaet = bonitaet;
	}

	//Getter/ Setter
	public boolean getBonitaet() {
		return bonitaet;
	}

	public void setBonitaet(boolean bonitaet) {
		this.bonitaet = bonitaet;
	}

	//toString
	public String toString() {
		return getKundenNummer() +"|"+ anschrift + " [Bonitaet=" + bonitaet + ", Rabatt=" + rabatt + "]";
	}


	
}
