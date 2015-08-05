package kunde;

/**
 * @author Tamara Hager
 *
 */
public class GesellschaftMLiz extends AKunde {
	private int vertrieb = 0;
	
	
	/**
	 * @param rabatt
	 * @param vertrieb
	 * //Konstruktor
	 */
	public GesellschaftMLiz(double rabatt, int vertrieb) {
		this.rabatt = 7.0;
		this.vertrieb = vertrieb;
	}

	public GesellschaftMLiz() {
	}

	//Getter/Setter
	public int getVertrieb() {
		return vertrieb;
	}

	public void setVertrieb(int vertrieb) {
		this.vertrieb = vertrieb;
	}

	//toString
	public String toString() {
		return getKundenNummer() +"|"+ anschrift + " [Vertrieb=" + vertrieb + ", rabatt=" + rabatt + "]";
	}

}//end of class
