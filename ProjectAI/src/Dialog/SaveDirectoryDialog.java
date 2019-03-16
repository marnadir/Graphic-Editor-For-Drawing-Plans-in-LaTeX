package Dialog;


import java.io.File;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class SaveDirectoryDialog {
 public static void main(String[] args) {
	 Display display = new Display ();
		Shell shell = new Shell (display);
		shell.open ();
		DirectoryDialog dialog = new DirectoryDialog (shell, SWT.SAVE);
		String filterPath = System.getProperty("user.home")+"/TDP"+"/dirLatex";
		String platform = SWT.getPlatform();
		if (platform.equals("win64")) {
			filterPath = "c:\\";

		}
	
		dialog.setFilterPath (filterPath);
	
		
		System.out.println ("Save to: " + dialog.open ());
		File directory = new File(dialog.getFilterPath()+"/CIAO");				
		directory.mkdir();

		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
}
}

