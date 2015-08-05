package util;

import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;

public class PicTool {

	/** 
	 * lädt das Bild in der tatsächlichen Größe
	 * @param file
	 * @return ImageIcon
	 */
	public static ImageIcon loadImage(File file) {
		ImageIcon tmpIcon =null;
        if (file != null) {
        //Don't use createImageIcon (which is a wrapper for getResource)
        //because the image we're trying to load is probably not one
        //of this program's own resources.
        	tmpIcon = new ImageIcon(file.getPath());
        }
        return tmpIcon;
    }
	
	
	/**
	 * lädt das Bild in Daumennagelgröße 
	 * @param file
	 * @return ImageIcon
	 */
	public static ImageIcon loadImageAsThumbNail(File file) {
		ImageIcon thumbnail=null;
		
        if (file != null) {
        //Don't use createImageIcon (which is a wrapper for getResource)
        //because the image we're trying to load is probably not one
        //of this program's own resources.
        	ImageIcon tmpIcon = new ImageIcon(file.getPath());
        	if (tmpIcon != null) {
        		int weite = 80;
            	if (tmpIcon.getIconWidth() > weite) {
               	 thumbnail = new ImageIcon(tmpIcon.getImage().
               			 getScaledInstance(weite, -1, Image.SCALE_DEFAULT));
            	} else { //no need to miniaturize
            		thumbnail = tmpIcon;
            	}
        	}
        }
        return thumbnail;
    }

}
