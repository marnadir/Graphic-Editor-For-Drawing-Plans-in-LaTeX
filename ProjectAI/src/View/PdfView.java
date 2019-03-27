package View;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class PdfView  extends Composite{

	
	Image myImage;
	Label myLabel;
	
	public PdfView(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}
	
	public void draw(String imagePath) {
		
		if(myLabel!=null) {
			myLabel.dispose();
		}
		myImage = new Image( getDisplay(),imagePath );
		myLabel = new Label( this, SWT.NONE );
		//accentrare
		myLabel.setLocation(0, 0);
		myLabel.setImage( myImage );
		myLabel.pack();
		pack();
	}

}
