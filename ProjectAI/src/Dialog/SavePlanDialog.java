package Dialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import Action.Node;
import PlanPart.LinkCanvas;
import PlanPart.OrderConstrain;
import PlanPart.OvalCounter;
import PlanPart.PlanContent;
import State.GoalStateCanvas;
import State.IStateCanvas;
import State.InitialStateCanvas;

public class SavePlanDialog extends FileDialog{

	File planFile;
	ArrayList<Object> data;
	PlanContent planContent;
	private OvalCounter ovalCounter;
	private ArrayList<Node> actionInPlan;
	private ArrayList<LinkCanvas> link;
	private ArrayList<OrderConstrain> ords;
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
		setFileName ("PlanStore");
		System.out.println ("Save to: " +open ());	
		createFile(getFilterPath(),getFileName());
		
		
	}
	
	public void createFile(String path,String name) {
		planFile = new File(path,name);

		try {
			if(planFile.exists()) {
				planFile.delete();	
			}
			planFile.createNewFile();

			planContent.setDirectory(planFile.getParentFile());
			data = new ArrayList<Object>();
			prepareObject();
			WriteObjectToFile(data);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (planFile.exists() && !planFile.isDirectory()) {

		}

	}
	
	
	private void WriteObjectToFile(Object serObj) {

		try {
			FileOutputStream fileOut = new FileOutputStream(planFile.getAbsolutePath());
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
		
		saveState(initialStateCanvas);
		saveState(goalStateCanvas);
		
		
		ArrayList<Object> info=new ArrayList<Object>();
		for (Node node : actionInPlan) {
			saveAction(info, node);
		}
		data.add(info);

		
		
		info = new ArrayList<Object>();
		for (OrderConstrain ord : ords) {
			saveOrd(info,ord);
		}
		data.add(info);
		
	
		info = new ArrayList<Object>();		
		for(LinkCanvas link:link) {
			saveLink(info,link);
		}
		data.add(info);
		
	}

	public void setPlanContent(PlanContent planContent) {
		this.planContent = planContent;
	}
	
	private void saveAction(ArrayList<Object > info,Node node) {
		Point point;
		ArrayList<Object> list=new ArrayList<Object>();
        point=new Point(node.getParent().getLocation().x, 
				node.getParent().getLocation().y);
        list.add(node.getAction());
        list.add(point);
        info.add(list);
		
	}
	

	private void saveState(IStateCanvas stateCanvas) {
		Point point;
		ArrayList<Object> info=new ArrayList<Object>();
		if(stateCanvas != null) {
			info.add(stateCanvas.getState());
			point=new Point(stateCanvas.getParent().getLocation().x, 
					stateCanvas.getParent().getLocation().y);
			info.add(point);
		}
		
		data.add(info);
		
	}
	
	private void saveOrd(ArrayList<Object> info,OrderConstrain ord) {
		ArrayList<Object> list=new ArrayList<Object>();
		if(ord != null) {

			list.add(ord.getCond1().getID());
			list.add(ord.getCond2().getID());
			list.add(ord.getP1());
			list.add(ord.getP2());

		}
		
		info.add(list);
		
	}
	
	
	private void saveLink(ArrayList<Object> info,LinkCanvas linkCanvas) {
		ArrayList<Object> list=new ArrayList<Object>();
		if(linkCanvas != null) {
			list.add(linkCanvas.getOval1().getP());
			list.add(linkCanvas.getOval2().getP());
		}
		info.add(list);	
	}
	
	
	@Override
	public String open() {

		String fileName = null;

		boolean done = false;

		while (!done) {

			fileName = super.open();
			if (fileName == null) {

				done = true;
			} else {

				File file = new File(fileName);
				if (file.exists()) {

					MessageBox mb = new MessageBox(super.getParent(), SWT.ICON_WARNING | SWT.YES | SWT.NO);

					mb.setMessage(fileName + " already exists. Do you want to replace it?");

					done = mb.open() == SWT.YES;
				} else {

					done = true;
				}
			}
		}
		return fileName;
	}

	public File getPlanFile() {
		return planFile;
	}

	public void setPlanFile(File domainFile) {
		this.planFile = domainFile;
	}
	
	
	
}
