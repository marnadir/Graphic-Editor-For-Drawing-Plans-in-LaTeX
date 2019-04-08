package Dialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import Action.Action;
import Action.Node;
import PlanPart.OrderConstrainCanvas;
import PlanPart.OrderConstrain;
import PlanPart.OvalCounter;
import PlanPart.PlanContent;
import State.GoalState;
import State.GoalStateCanvas;
import State.InitialState;
import State.InitialStateCanvas;
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
		String [] filterNames = new String [] {"*.txt","All Files (*)"};
		String [] filterExtensions = new String [] {"*.txt", "*"};
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
