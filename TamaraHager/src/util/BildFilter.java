package util;

import java.io.File;

import javax.swing.filechooser.FileFilter;



public class BildFilter extends FileFilter {


	public boolean accept(File file) {
		
		if(file.isDirectory()){
			return true;
		}
		
		int pos = (file.getName()).lastIndexOf('.');
		String ext = (file.getName()).substring(pos+1);
		
		if(ext.equalsIgnoreCase("jpg")|| ext.equalsIgnoreCase("gif"))
		{	return true;
		}
		
		
		return false;
	}


	public String getDescription() {
		String beschreibung = new String("nur Bilddateien (.jpg/.gif)");
		return beschreibung;
	}		
}
