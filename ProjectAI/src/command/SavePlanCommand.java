package command;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.eclipse.swt.graphics.Point;

import Action.Node;
import PlanPart.LinkCanvas;
import PlanPart.OrderConstrain;
import PlanPart.PlanContent;
import State.GoalStateCanvas;
import State.IStateCanvas;
import State.InitialStateCanvas;

public class SavePlanCommand implements ICommand {

	File planFile;
	ArrayList<Object> data;
	PlanContent planContent;
	private ArrayList<Node> actionInPlan;
	private ArrayList<LinkCanvas> link;
	private ArrayList<OrderConstrain> ords;
	private InitialStateCanvas initialStateCanvas;
	private GoalStateCanvas goalStateCanvas;
	
	
	@Override
	public boolean canExecute(Object var1, Object var2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute(Object var1, Object var2) {
		
		String path = null;
		String name = null;
		
		if(var1 instanceof String) {
			path=(String)var1;
		}
		if(var2 instanceof String) {
			name=(String)var2;
		}
		createFile(path, name);
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
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void prepareObject() {
		
		initialStateCanvas=planContent.getInitialStateCanvas();
		goalStateCanvas=planContent.getGoalStateCanvas();
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
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public File getPlanFile() {
		return planFile;
	}
	
	
}
