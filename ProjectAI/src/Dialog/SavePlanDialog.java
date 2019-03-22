package Dialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Link;
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

	File file;
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
		
		saveState(initialStateCanvas);
		
		saveState(goalStateCanvas);
		for(Node node:actionInPlan) {
			saveAction(node);
		}
		
		
		
		for(OrderConstrain ord:ords) {
			saveOrd(ord);
		}
		
		data.add("Link");
		
		for(LinkCanvas link:link) {
			saveLink(link);
		}
				
	}

	public void setPlanContent(PlanContent planContent) {
		this.planContent = planContent;
	}
	
	private void saveAction(Node node) {
		Point point;
		ArrayList<Object> info;
        info=new ArrayList<Object>();
        point=new Point(node.getParent().getLocation().x, 
				node.getParent().getLocation().y);
        info.add(node.getAction());
        info.add(point);
        data.add(info);
		
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
	
	private void saveOrd(OrderConstrain ord) {
		ArrayList<Object> info=new ArrayList<Object>();
		if(ord != null) {
//			info.add(ord.getP1());
//			info.add(ord.getP2());
			info.add(ord.getCond1().getID());
			System.out.println(ord.getCond1().getID());
			info.add(ord.getCond2().getID());
			System.out.println(ord.getCond2().getID());

		}
		
		data.add(info);
		
	}
	
	
	private void saveLink(LinkCanvas linkCanvas) {
		ArrayList<Object> info=new ArrayList<Object>();
		if(linkCanvas != null) {
			info.add(linkCanvas.getOval1().getP());
			info.add(linkCanvas.getOval2().getP());
		}
		
		data.add(info);
		
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
	
}
