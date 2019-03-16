

import java.io.File;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class SaveDirectory {
 public static void main(String[] args) {
	 Display display = new Display ();
		Shell shell = new Shell (display);
		shell.open ();
		FileDialog dialog = new FileDialog (shell, SWT.SAVE);
		String [] filterNames = new String [] {"All Files (*)"};
		String [] filterExtensions = new String [] {"*.dir", "*"};
		String filterPath = "/";
		String platform = SWT.getPlatform();
		if (platform.equals("win32")) {
			filterNames = new String [] {"Image Files", "All Files (*.*)"};
			filterExtensions = new String [] {"*.gif;*.png;*.bmp;*.jpg;*.jpeg;*.tiff", "*.*"};
			filterPath = "c:\\";
		}
		dialog.setFilterNames (filterNames);
		dialog.setFilterExtensions (filterExtensions);
		dialog.setFilterPath (filterPath);
		dialog.setFileName ("myfile");
		System.out.println ("Save to: " + dialog.open ());
		File directory = new File(dialog.getFilterPath());				
		directory.mkdir();
		
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
}

}