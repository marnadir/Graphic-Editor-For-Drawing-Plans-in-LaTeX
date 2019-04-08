package Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import View.DomainView;
import command.LoadDomainCommand;

public class LoadDomainDialog extends FileDialog {

	DomainView domainView;

	
	
	public LoadDomainDialog(Shell parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}

	
	public void createContent() {
		
		String [] filterNames = new String [] {"*.txt","All Files (*)"};
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
		setFilterPath(filterPath);
		setFilterPath(filterPath);
		String path=open();
		LoadDomainCommand command=new LoadDomainCommand();
		command.execute(path,domainView);

	}
	@Override
	protected void checkSubclass() {
		
	}
	
	public void setDomainView(DomainView domainView) {
		this.domainView = domainView;
	}
	


}
