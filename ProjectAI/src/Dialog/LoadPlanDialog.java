package Dialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import Action.Action;
import PlanPart.PlanContent;
import State.GoalState;
import State.GoalStateCanvas;
import State.InitialState;
import State.InitialStateCanvas;

public class LoadPlanDialog extends FileDialog {
	
	File file;
	ArrayList<Object> data;
	PlanContent planContent;
	private InitialStateCanvas initialStateCanvas;


	public LoadPlanDialog(Shell parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}
	
	public void createContent() {
		String path;
		String filterPath = System.getProperty("user.home")+"/TDP"+"/dirLatex";
		setFilterPath(filterPath);
	    path=open();
	    ReadObjectToFile(path);
	}
	
	public void ReadObjectToFile(String path) {

		try {
			FileInputStream fileIn = new FileInputStream(path);

			if (!fileIsEmpty(path)) {
				
				ObjectInputStream objectIn = new ObjectInputStream(fileIn);
				
				System.out.println(objectIn.toString());
				Object data2=objectIn.readObject();
				ArrayList<Object> data = (ArrayList<Object>) objectIn.readObject();

//				if (data.get(1) != null) {
//					InitialStateCanvas in = (InitialStateCanvas) data.get(1);
//					planContent.setInitialStateCanvas(in);
//					in.draw();
//					initialStateCanvas.draw();
//					initialStateCanvas.addDNDListener();
//				
//				}
	
				objectIn.close();
				System.out.println("The Object  was succesfully read from a file");


			}else {
//				MessageBox messageBox = new MessageBox(g,
//						SWT.ICON_WARNING |  SWT.OK);
//
//				messageBox.setText("Warning");
//				messageBox.setMessage("There are no stored information");
//				messageBox.open();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public void setPlanContent(PlanContent planContent) {
		this.planContent = planContent;
	}

	public void setInitialStateCanvas(InitialStateCanvas initialStateCanvas) {
		this.initialStateCanvas = initialStateCanvas;
	}

	public boolean fileIsEmpty(String path) {
		File file = new File(path);
		if(file.length()>0) {
			return false;
		}else {
			return true;
		}
	}
	
	@Override
	protected void checkSubclass() {
		
	}

}
