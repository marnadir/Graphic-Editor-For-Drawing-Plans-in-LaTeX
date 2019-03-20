package Dialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import Action.Action;
import Action.Node;
import PlanPart.Contrain;
import PlanPart.LinkCanvas;
import PlanPart.Oval;
import PlanPart.OvalCounter;
import PlanPart.PlanContent;
import State.GoalState;
import State.GoalStateCanvas;
import State.InitialState;
import State.InitialStateCanvas;

public class LoadPlanDialog extends FileDialog {
	
	File file;
	ArrayList<Object> data;
	PlanContent planContent;
	private OvalCounter ovalCounter;
	String path;


	public LoadPlanDialog(Shell parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}
	
	public void createContent() {
		
		String filterPath = System.getProperty("user.home")+"/TDP"+"/dirLatex";
		setFilterPath(filterPath);
	    path=open();
	    ReadObjectToFile();
	   
	}
	
	public void ReadObjectToFile() {

		if(path !=null) {
			try {
				FileInputStream fileIn = new FileInputStream(path);

				if (!fileIsEmpty(path)) {

					ObjectInputStream objectIn = new ObjectInputStream(fileIn);
					data = (ArrayList<Object>) objectIn.readObject();
					ArrayList<Object> info;
					
					info = (ArrayList<Object>) data.get(0);
					if (info.size()>0) {
						loadInitialState();
					}

					info = (ArrayList<Object>) data.get(1);
					if (info.size()>0) {
						loadGoalState();
					}

					int i=2;
					info=(ArrayList<Object>) data.get(i);
					while(info.get(0) instanceof Action ) {
						loadNode(i);
						i++;
						if(i==data.size()) {
							break;
						}
						if(data.get(i).equals("Link")) {
							break;
						}
						info=(ArrayList<Object>) data.get(i);
					}
					
					
					while(!(data.get(i).equals("Link"))) {
						loadOrd(i);
						i++;
					}
					
					
					ArrayList<Object> arraylink = new ArrayList<>();
					for(int j=i;j<data.size();j++) {
						arraylink.add(data.get(j));
					}
					
					planContent.setLinkStored(arraylink);
					
					if(arraylink.size()>0) {
						planContent.getButton().setVisible(true);

					}


					
					objectIn.close();
					System.out.println("The Object  was succesfully read from a file");

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		

	}
	

	
	public void setPlanContent(PlanContent planContent) {
		this.planContent = planContent;
	}

	public boolean fileIsEmpty(String path) {
		File file = new File(path);
		if(file.length()>0) {
			return false;
		}else {
			return true;
		}
	}
	
	private void loadInitialState() {
		ArrayList<Object> info=(ArrayList<Object>) data.get(0);
		InitialState inState = (InitialState) info.get(0);
		Point position=(Point) info.get(1);
		Composite comp = new Composite(planContent, SWT.DRAW_DELIMITER);
		comp.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
		comp.setLayout(new FillLayout());
		comp.setLocation(position.x, position.y);
		InitialStateCanvas stateCanvas = new InitialStateCanvas(comp, SWT.ALL, inState);
		stateCanvas.draw();
		planContent.setInitialStateCanvas((InitialStateCanvas) stateCanvas);
		planContent.addMoveListener(comp);
	}
	
	private void loadGoalState() {
		ArrayList<Object> info=(ArrayList<Object>) data.get(1);
		GoalState inState = (GoalState) info.get(0);
		Point position=(Point) info.get(1);
		Composite comp = new Composite(planContent, SWT.DRAW_DELIMITER);
		comp.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
		comp.setLayout(new FillLayout());
		comp.setLocation(position.x, position.y);
		GoalStateCanvas stateCanvas = new GoalStateCanvas(comp, SWT.ALL, inState);
		stateCanvas.draw();
		planContent.setGoalStateCanvas((GoalStateCanvas) stateCanvas);
		planContent.addMoveListener(comp);
	}
	
	private void loadNode(int i) {
		ArrayList<Object> info=(ArrayList<Object>) data.get(i);
		Action action = (Action) info.get(0);
		Point position=(Point) info.get(1);
		Composite comp = new Composite(planContent, SWT.DRAW_DELIMITER);
		comp.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
		comp.setLayout(new FillLayout());
		comp.setLocation(position.x, position.y);
		Node node = new Node(comp, SWT.ALL, action);
		node.draw();
		node.pack();
		comp.pack();
		planContent.getActionInPlan().add(node);
		setNodeID(node);
		planContent.addMoveListener(comp);
	}
	
	private void loadOrd(int i) {
		ArrayList<Object> info = (ArrayList<Object>) data.get(i);
		Point p1 = (Point) info.get(0);
		Point p2 = (Point) info.get(0);
		Composite parent = new Composite(planContent, SWT.ALL);

		// sulla definizione di cio, ce qualcosa che mi turba!!
		parent.setSize(50, 50);
		parent.setLocation(20, 30);

		parent.setSize(90, 60);

		parent.setLocation(p1.x + ((p2.x - p1.x - parent.getBounds().width) / 2), p1.y - 30);

		Contrain c = new Contrain(parent, SWT.ALL);
		c.draw();
		c.pack();
		c.setSize(parent.getSize().x, parent.getSize().y);

	}
	
	private void setNodeID(Node node) {
		int t = 1;
		String ID = getNameAction(node.getAction().getName()) + "-" + t;
		for (int i = 0; i < planContent.getActionInPlan().size(); i++) {
			if (planContent.getActionInPlan().get(i).getID() != null) {
				if (planContent.getActionInPlan().get(i).getID().equals(ID)) {
					t++;
					ID = getNameAction(node.getAction().getName()) + "-" + t;
					i = 0;
				}
			}

		}
		node.setID(ID);
	}
	
	private String getNameAction(String string) {
		String name[] = string.split("\\(");
		StringBuilder sb = new StringBuilder();
		sb.append(name[0]);
		return sb.toString();
	}
	

	
	@Override
	protected void checkSubclass() {
		
	}

}
