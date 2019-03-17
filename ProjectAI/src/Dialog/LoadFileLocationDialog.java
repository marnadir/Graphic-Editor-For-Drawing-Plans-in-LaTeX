package Dialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import Action.Action;
import State.GoalState;
import State.GoalStateCanvas;
import State.InitialState;
import State.InitialStateCanvas;
import View.DomainView;

public class LoadFileLocationDialog extends FileDialog {

	ArrayList<Action> updateActionListDomain;
	DomainView domainView;
	Shell shell;
	
	
	public LoadFileLocationDialog(Shell parent, int style) {
		super(parent, style);
		this.shell=parent;
		// TODO Auto-generated constructor stub
	}

	
	public void createContent() {
		String path;
		String filterPath = System.getProperty("user.home")+"/TDP"+"/dirLog";
		setFilterPath(filterPath);
	    path=open();
	    ReadObjectToFile(path);
		domainView.restoreActionList(updateActionListDomain);
	}
	@Override
	protected void checkSubclass() {
		
	}
	
	public void ReadObjectToFile(String path) {

		try {
			FileInputStream fileIn = new FileInputStream(path);

			if (!fileIsEmpty(path)) {
				
				ObjectInputStream objectIn = new ObjectInputStream(fileIn);
				ArrayList<Object> data = (ArrayList<Object>) objectIn.readObject();
				updateActionListDomain = (ArrayList<Action>) data.get(0);

				if (data.get(1) != null) {
					InitialState in = (InitialState) data.get(1);
					if (domainView.getInitStateView().getContainerInitState().getChildren().length > 0) {
						domainView.getInitStateView().getContainerInitState().getChildren()[0].dispose();
					}
					InitialStateCanvas initialStateCanvas = new InitialStateCanvas(
							domainView.getInitStateView().getContainerInitState(), SWT.ALL, in);
					domainView.getInitStateView().getContainerInitState().setVisible(true);
					initialStateCanvas.draw();
					initialStateCanvas.addDNDListener();
					in.generateLatexCodeDomain();
					in.getLatexCodeDomain();
				}
				if (data.get(2) != null) {
					GoalState goal = (GoalState) data.get(2);
					if (domainView.getGoalStateView().getContainerGoalState().getChildren().length > 0) {
						domainView.getGoalStateView().getContainerGoalState().getChildren()[0].dispose();
					}
					GoalStateCanvas goalStateCanvas = new GoalStateCanvas(
							domainView.getGoalStateView().getContainerGoalState(), SWT.ALL, goal);
					domainView.getGoalStateView().getContainerGoalState().setVisible(true);
					goalStateCanvas.draw();
					goalStateCanvas.pack();
					goalStateCanvas.addDNDListener();
					goal.generateLatexCodeDomain();
					goal.getLatexCodeDomain();
				}
				objectIn.close();
				System.out.println("The Object  was succesfully read from a file");


			}else {
				MessageBox messageBox = new MessageBox(shell,
						SWT.ICON_WARNING |  SWT.OK);

				messageBox.setText("Warning");
				messageBox.setMessage("There are no stored information");
				messageBox.open();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void setDomainView(DomainView domainView) {
		this.domainView = domainView;
	}
	

	public void setUpdateActionListDomain(ArrayList<Action> updateActionListDomain) {
		this.updateActionListDomain = updateActionListDomain;
	}
	
	public boolean fileIsEmpty(String path) {
		File file = new File(path);
		if(file.length()>0) {
			return false;
		}else {
			return true;
		}
	}
	
	
}
