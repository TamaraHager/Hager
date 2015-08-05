package gui;

import java.io.File;

import javax.swing.*;

/**
 * @author Hager
 *Hier kann der Benutzer den verzeichnispfad von den Bildern anschauen.
 */
public class Info {

	/**
	 * Informationsfenster erzeugen, welches den Benutzer den Pfad zeigt.
	 */
	public Info() {

		File f = new File(".././pic");
		String msg = "Verzeichnispfad der Kundenbilder:  "
				+ f.getAbsolutePath();

		JOptionPane optionPane = new NarrowOptionPane();
		//verzeichnispfad
		optionPane.setMessage(msg);
		optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
		JDialog dialog = optionPane.createDialog(null, "Info");
		
		dialog.setVisible(true);
	}

	private class NarrowOptionPane extends JOptionPane {

		NarrowOptionPane() {
		}

		public int getMaxCharactersPerLineCount() {
			return 100;
		}

	}

}