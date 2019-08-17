package command;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;

import Action.Action;
import Action.Node;
import PlanPart.OrderConstrain;
import PlanPart.OrderConstrainCanvas;
import PlanPart.PlanContent;
import container.IContainerState;
import so_goalState.GoalState;
import so_goalState.GoalStateCanvas;
import so_goalState.InitialState;
import so_goalState.InitialStateCanvas;
/**
 * Command which allows to open the dialog for loading a plan which was saved.
 * @author nadir
 * */
public class LoadPlanCommand  implements ICommand{

	File filePlan;
	ArrayList<Object> data;
	ArrayList<Object> info;
	PlanContent planContent;
	boolean isLoad=false;
	
	@Override
	public boolean canExecute(Object var1, Object var2) {
		// TODO Auto-generated method stub
		return false;
	}

	public void execute(Object var1, Object var2) {
		String path = null;
		if (var1 instanceof String) {
			path = (String) var1;
		}
		if (var2 instanceof PlanContent) {
			this.planContent = (PlanContent) var2;
		}
		
		ReadObjectToFile(path);
		
	
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void ReadObjectToFile(String path) {
		isLoad=false;
		if(path !=null) {
			isLoad=true;
			try {
				filePlan=new File(path);
				FileInputStream fileIn = new FileInputStream(path);

				if (!fileIsEmpty(path)) {

					
					
					
					ObjectInputStream objectIn = new ObjectInputStream(fileIn);
					data = (ArrayList<Object>) objectIn.readObject();
					info = (ArrayList<Object>) data.get(0);
					
					//plan space is free(button "link" always present)
					if(planContent.getChildren().length<2) {
						if (info.size()>0) {
							loadInitialState();
						}

						info = (ArrayList<Object>) data.get(1);
						if (info.size()>0) {
							loadGoalState();
						}
						
						info = (ArrayList<Object>) data.get(2);
						// this check isn't now neccessary

						for (int i = 0; i < info.size(); i++) {
							loadNode(i);
						}
						
						info = (ArrayList<Object>) data.get(3);
						for (int i = 0; i < info.size(); i++) {
							loadOrd(i);
						}
						
						info = (ArrayList<Object>) data.get(4);
//						for(int i=0;i<data.size();i++) {
//							arraylink.add(data.get(i));
//						}
						
						planContent.setLinkStored(info);
						
						if(info.size()>0) {
							planContent.getButton().setVisible(true);

						}

						planContent.getPlanview().getConsoleView().getConsoleViewPlan().updateView();;
						
						objectIn.close();
					}else {
						MessageBox messageBox = new MessageBox(planContent.getShell(),
								SWT.ICON_WARNING |  SWT.OK);

						messageBox.setText("Warning");
						messageBox.setMessage("Plan already present, not possible overwriting."
								+ "\n"+"Please oper a new plan view and load the plan");
						messageBox.open();
					
						
					}
					
					

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
		IContainerState comp = new IContainerState(planContent, SWT.DRAW_DELIMITER);
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
		IContainerState comp = new IContainerState(planContent, SWT.DRAW_DELIMITER);
		comp.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
		comp.setLayout(new FillLayout());
		comp.setLocation(position.x, position.y);
		GoalStateCanvas stateCanvas = new GoalStateCanvas(comp, SWT.BORDER, inState);
		stateCanvas.draw();
		planContent.setGoalStateCanvas((GoalStateCanvas) stateCanvas);
		planContent.addMoveListener(comp);
	}
	
	private void loadNode(int i) {
		ArrayList<Object> list=(ArrayList<Object>) info.get(i);
		Action action = (Action) list.get(0);
		Point position=(Point) list.get(1);
		Composite comp = new Composite(planContent, SWT.DRAW_DELIMITER);
		comp.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
		comp.setLayout(new FillLayout());
		comp.setLocation(position.x, position.y);
		Node node = new Node(comp, SWT.None, action);
		node.draw();
		node.pack();
		comp.pack();
		planContent.getActionInPlan().add(node);
		setNodeID(node);
		planContent.addMoveListener(comp);
	}
	
	private void loadOrd(int i) {
		ArrayList<Object> list = (ArrayList<Object>) info.get(i);
		String id1=(String) list.get(0);
		String id2=(String) list.get(1);
		
		Point p1= (Point) list.get(2);
		Point p2= (Point) list.get(3);
		
		Node nod1 = null;
		Node nod2 = null;
		
		for(Node node:planContent.getActionInPlan()) {
			if(node.getID().equals(id1)) {
				nod1=node;
			}
		}
		
		for(Node node:planContent.getActionInPlan()) {
			if(node.getID().equals(id2)) {
				nod2=node;
			}
		}

		Composite parent = new Composite(planContent, SWT.ALL);

		OrderConstrain orderConstrain=new OrderConstrain(parent);
		orderConstrain.setNod1(nod1);
		orderConstrain.setNod2(nod2);
		
	
		parent.setSize(90,60);	
		parent.setLocation(p1.x+((p2.x-p1.x-parent.getBounds().width)/2), p1.y - 30);
		
		orderConstrain.setP1(p1);
		orderConstrain.setP2(p2);
		
		OrderConstrainCanvas c=new OrderConstrainCanvas(parent, SWT.ALL,orderConstrain);
		c.draw();
		c.pack();
		c.setSize(parent.getSize().x,parent.getSize().y);
		planContent.getOrds().add(orderConstrain);

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

	public boolean isLoad() {
		return isLoad;
	}

	public File getFilePlan() {
		return filePlan;
	}

	
	

	

}
