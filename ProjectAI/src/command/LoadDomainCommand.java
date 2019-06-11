package command;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import Action.Action;
import Action.GlobalValue;
import State.GoalState;
import State.GoalStateCanvas;
import State.InitialState;
import State.InitialStateCanvas;
import View.DomainView;
import View.GlobalOptionView;

public class LoadDomainCommand implements ICommand{

	ArrayList<Action> updateActionListDomain;
	DomainView domainView;
	
	
	@Override
	public boolean canExecute(Object var1, Object var2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute(Object var1, Object var2) {
		
		String path = null;
		if (var1 instanceof String) {
			path = (String) var1;
		}
		if (var2 instanceof DomainView) {
			this.domainView = (DomainView) var2;
		}

		ReadObjectToFile(path);
		domainView.restoreActionList(updateActionListDomain);

	}
	
	public boolean ReadObjectToFile(String path) {
		
		
		boolean result=false;
		if(path!=null) {
			result=true;
			try {
				FileInputStream fileIn = new FileInputStream(path);

				if (!fileIsEmpty(path)) {
					
					ObjectInputStream objectIn = new ObjectInputStream(fileIn);
					ArrayList<Object> data = (ArrayList<Object>) objectIn.readObject();
					
					GlobalValue.setValue((ArrayList<Object>) data.get(0));
					setGlobalOptionView();
					
					
					updateActionListDomain = (ArrayList<Action>) data.get(1);

					if (data.get(2) != null) {
						InitialState in = (InitialState) data.get(2);
						if (domainView.getInitStateView().getContainerState().getChildren().length > 0) {
							domainView.getInitStateView().getContainerState().getChildren()[0].dispose();
						}
						InitialStateCanvas initialStateCanvas = new InitialStateCanvas(
								domainView.getInitStateView().getContainerState(), SWT.ALL, in);
						domainView.getInitStateView().getContainerState().setVisible(true);
						initialStateCanvas.draw();
						initialStateCanvas.addDNDListener();
						in.generateLatexCodeDomain();
						in.getLatexCodeDomain();
					}
					if (data.get(3) != null) {
						GoalState goal = (GoalState) data.get(3);
						if (domainView.getGoalStateView().getContainerState().getChildren().length > 0) {
							domainView.getGoalStateView().getContainerState().getChildren()[0].dispose();
						}
						GoalStateCanvas goalStateCanvas = new GoalStateCanvas(
								domainView.getGoalStateView().getContainerState(), SWT.ALL, goal);
						domainView.getGoalStateView().getContainerState().setVisible(true);
						goalStateCanvas.draw();
						goalStateCanvas.pack();
						goalStateCanvas.addDNDListener();
						goal.generateLatexCodeDomain();
						goal.getLatexCodeDomain();
					}
					
					
					
					System.out.println(GlobalValue.isWidthOfAction);
					objectIn.close();

				}else {
					MessageBox messageBox = new MessageBox(domainView
							.getTreeAction().getShell(),
							SWT.ICON_WARNING |  SWT.OK);

					messageBox.setText("Warning");
					messageBox.setMessage("There are no stored information");
					messageBox.open();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	

	
	public void setGlobalOptionView() {
		GlobalOptionView globalOptionView=domainView.getGlobalOptionView();
		globalOptionView.update();
		
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
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
