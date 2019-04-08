package Dialog;

import java.io.File;
import java.util.ArrayList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import PlanPart.PlanContent;
import command.LoadPlanCommand;

public class LoadPlanDialog extends FileDialog {
	
	File filePlan;
	ArrayList<Object> data;
	ArrayList<Object> info;
	PlanContent planContent;
	String path;
	LoadPlanCommand command;


	public LoadPlanDialog(Shell parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}
	
	public void createContent() {
		String [] filterNames = new String [] {"Plan.*","All Files (*)"};
		String [] filterExtensions = new String [] {"Plan*.txt", "*"};
		String filterPath = System.getProperty("user.home")+"/TDP"+"/dirLatex";
		String platform = SWT.getPlatform();
		if (platform.equals("win32")) {
			filterNames = new String [] {"Image Files", "All Files (*.*)"};
			filterExtensions = new String [] {"*.gif;*.png;*.bmp;*.jpg;*.jpeg;*.tiff", "*.*"};
			filterPath = "c:\\";
		}
		setFilterNames (filterNames);
		setFilterExtensions (filterExtensions);
		setFilterPath(filterPath);
	    path=open();
	    command=new LoadPlanCommand();
	    command.execute(path, planContent);
	   
	}
	
	public void setPlanContent(PlanContent planContent) {
		this.planContent = planContent;
	}

	
	public LoadPlanCommand getCommand() {
		return command;
	}

	@Override
	protected void checkSubclass() {
		
	}

}
