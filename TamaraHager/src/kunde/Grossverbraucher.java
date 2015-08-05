package kunde;

/**
 * @author Tamara Hager
 *
 */
public class Grossverbraucher extends AKunde{
	private int status = 0;
	
	//Konstruktor
	public Grossverbraucher(){
		new Adresse();
		
	}
	
	/**
	 * @param rabatt
	 * @param status
	 */
	public Grossverbraucher(double rabatt, int status) {
		this.rabatt = 5.0;
		this.status = status;
	}
	
	//Getter/Setter
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	//toString
	public String toString() {
		return getKundenNummer() +"|"+anschrift + " [status=" + status + ", rabatt=" + rabatt + "]";
	}
	
}
