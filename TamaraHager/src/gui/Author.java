package gui;

import javax.swing.JOptionPane;
import javax.swing.JDialog;

/**
 * @author Hager
 * erstellt ein Benachrichtigungsfenster mit Informationen über die Autoren und Versionen
 */
public class Author {

	public Author() {

		String msg = "Author: Tamara Hager \n"
				+ "Version: Luna Service Release 2 (4.4.2) \n"
				+ "Build id: 20140925-1800 \n"
				+ "Erstelldatum: 11. 07. 2015 \n"
				+ "Projekt-Version: Weinhändler 3.0";

		JOptionPane optionPane = new NarrowOptionPane();
		
		//befülle die Optionpane mit dem Stringinhalt
		optionPane.setMessage(msg);
		optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
		JDialog dialog = optionPane.createDialog(null, "Width 100");
		
		dialog.setVisible(true);
	}
}


class NarrowOptionPane extends JOptionPane {

	NarrowOptionPane() {
	}

	public int getMaxCharactersPerLineCount() {
		return 100;
	}
}
