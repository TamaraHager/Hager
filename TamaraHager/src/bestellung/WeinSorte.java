package bestellung;

/**
 * @author Hager
 * alle Weinsorten, die in der combobox beim anlegen oder ändern
 * einer Bestellung angezeigt werden
 *
 */
public class WeinSorte {

	/**
	 * @return weinsorten
	 */
	public static String[] getWeinsorte() {
		String[] weinsorte = { "Riesling", "Dornfelder", "Chianti","Chardonnay" };
		return weinsorte;
	}
}
