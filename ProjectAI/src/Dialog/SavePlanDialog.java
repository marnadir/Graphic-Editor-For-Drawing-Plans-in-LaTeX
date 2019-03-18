package Dialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import Action.Node;
import PlanPart.LinkCanvas;
import PlanPart.OrderCondition;
import PlanPart.OvalCounter;
import PlanPart.PlanContent;
import State.GoalStateCanvas;
import State.InitialStateCanvas;

public class SavePlanDialog extends FileDialog{

	File file;
	ArrayList<Object> data;
	PlanContent planContent;
	private OvalCounter ovalCounter;
	private ArrayList<Node> actionInPlan;
	private ArrayList<LinkCanvas> link;
	private ArrayList<OrderCondition> ords;
	private InitialStateCanvas initialStateCanvas;
	private GoalStateCanvas goalStateCanvas;
	
	public SavePlanDialog(Shell parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}
	
	public void createContent() {
		String [] filterNames = new String [] {"All Files (*)"};
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
		setFilterPath (filterPath);
		setFileName ("LatexPlan");
		System.out.println ("Save to: " +open ());	
		createFile(getFileName(),getFilterPath());
		data = new ArrayList<Object>();
		prepareObject();
		WriteObjectToFile(data);
		
	}
	
	private void createFile(String name,String path) {
		file = new File(path, name);
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (file.exists() && !file.isDirectory()) {

		}

	}
	
	
	private void WriteObjectToFile(Object serObj) {

		try {
			FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath());
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(serObj);
			objectOut.close();
			System.out.println("The Object  was succesfully written to a file");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@Override
	protected void checkSubclass() {
		
	}
	
	private void prepareObject() {
		
		initialStateCanvas=planContent.getInitialStateCanvas();
		goalStateCanvas=planContent.getGoalStateCanvas();
		ovalCounter=planContent.getOvalCounter();
		link=planContent.getLink();
		actionInPlan=planContent.getActionInPlan();
		ords=planContent.getOrds();
		
		data.add(initialStateCanvas);
		
				
	}

	public void setPlanContent(PlanContent planContent) {
		this.planContent = planContent;
	}
	
	
	
}
