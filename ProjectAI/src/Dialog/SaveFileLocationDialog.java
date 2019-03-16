package Dialog;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class SaveFileLocationDialog extends FileDialog{
 
	 
		
		public SaveFileLocationDialog(Shell parent, int style) {
			super(parent, style);
			// TODO Auto-generated constructor stub
		}
		
		public void createContent() {
			//FileDialog dialog = new FileDialog (shell, SWT.SAVE);
			String [] filterNames = new String [] {"All Files (*)"};
			String [] filterExtensions = new String [] {"*.txt", "*"};
			String filterPath = System.getProperty("user.home")+"/TDP"+"/dirLog";
			String platform = SWT.getPlatform();
			if (platform.equals("win32")) {
				filterNames = new String [] {"Image Files", "All Files (*.*)"};
				filterExtensions = new String [] {"*.gif;*.png;*.bmp;*.jpg;*.jpeg;*.tiff", "*.*"};
				filterPath = "c:\\";
			}
			setFilterNames (filterNames);
			setFilterExtensions (filterExtensions);
			setFilterPath (filterPath);
			setFileName ("domain");
			System.out.println ("Save to: " +open ());	
		}
		
	
}
