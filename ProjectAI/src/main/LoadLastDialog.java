package main;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import PlanPart.PlanContent;
import View.PlanView;
import command.LoadDomainCommand;
import command.LoadPlanCommand;

public class LoadLastDialog {

	private Shell shell;
	private String text;
	private PlanView planView;
	String path;

	
	public LoadLastDialog(Shell shell,String text) {
		this.shell=shell;
		this.text=text;
		path = System.getProperty("user.home");
		path = path + "/TDP" + "/dirLog";
		
	}

	public void load(PlanView planView) {
		this.planView=planView;
		MessageBox dialog = new MessageBox(shell, SWT.OK | SWT.CANCEL | SWT.ICON_QUESTION);
		dialog.setText("Question");
		dialog.setMessage(text);
		if(dialog.open() == SWT.OK) {
			loadstateProgram();
		}
		
	}
	
	private void loadstateProgram() {
		loadDomain(planView.getAllPlan().get(0));
		for(PlanContent planContent:planView.getAllPlan()) {
			loadPlan(planContent);
		}
	}
	
	private void loadPlan(PlanContent planContent) {
		LoadPlanCommand command=new LoadPlanCommand();
		command.execute(path+"/Plan.txt",planContent);
	}
	
	
	private void loadDomain(PlanContent planContent) {
		if(path != null) {
			File filePlan=new File(path);
//			planContent.setSavedPllan(filePlan);
//			planContent.setDirectory(filePlan.getParentFile());
			LoadDomainCommand command=new LoadDomainCommand();
			String path2=filePlan+"/Domain.txt";
			PlanView planView = null;
			if(planContent.getParent() instanceof PlanView) {
				 planView=(PlanView) planContent.getParent();
			}
			command.execute(path2,planView.getDomainView());
			
		}
	}
	
	

	}
	

