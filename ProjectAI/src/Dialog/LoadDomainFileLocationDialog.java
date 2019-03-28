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
import Action.GlobalValue;
import State.GoalState;
import State.GoalStateCanvas;
import State.InitialState;
import State.InitialStateCanvas;
import View.DomainView;
import View.GlobalOptionView;

public class LoadDomainFileLocationDialog extends FileDialog {

	ArrayList<Action> updateActionListDomain;
	DomainView domainView;
	Shell shell;
	
	
	public LoadDomainFileLocationDialog(Shell parent, int style) {
		super(parent, style);
		this.shell=parent;
		// TODO Auto-generated constructor stub
	}

	
	public void createContent() {
		
		String [] filterNames = new String [] {"*.txt","All Files (*)"};
		String [] filterExtensions = new String [] {"*.txt", "*"};
		String filterPath = System.getProperty("user.home")+"/TDP"+"/dirLog";
		String platform = SWT.getPlatform();
		if (platform.equals("win32")) {
			filterNames = new String [] {"Image Files", "All Files (*.*)"};
			filterExtensions = new String [] {"*.gif;*.png;*.bmp;*.jpg;*.jpeg;*.tiff", "*.*"};
			filterPath = "c:\\";
		}
		setFilterNames (filterNames);
		setFilterExtensions (filterExtensions);
		setFilterPath(filterPath);
		
		String path;
		setFilterPath(filterPath);
	    path=open();
	    ReadObjectToFile(path);
		domainView.restoreActionList(updateActionListDomain);
	}
	@Override
	protected void checkSubclass() {
		
	}
	
	public void ReadObjectToFile(String path) {
		
		if(path!=null) {
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
					if (data.get(3) != null) {
						GoalState goal = (GoalState) data.get(3);
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
					
					
					
					System.out.println(GlobalValue.isWidthOfAction);
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
		

	}
	
	public void setDomainView(DomainView domainView) {
		this.domainView = domainView;
	}
	

	public void setUpdateActionListDomain(ArrayList<Action> updateActionListDomain) {
		this.updateActionListDomain = updateActionListDomain;
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
	
	
}
