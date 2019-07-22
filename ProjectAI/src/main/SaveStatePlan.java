package main;

import java.util.TimerTask;

import org.eclipse.swt.widgets.Display;

import PlanPart.PlanContent;
import View.DomainView;
import View.PlanView;
import command.SaveDomainCommand;
import command.SavePlanCommand;

public class SaveStatePlan extends TimerTask {

	String path;
	DomainView DomainView;
	private Runnable exe;
	Display display;
	public void run() {
		
		if(!display.isDisposed()) {
			display.asyncExec(getRunnable());
		}
	}
	
	private Runnable getRunnable() {
		exe=new Runnable() {
			
			@Override
			public void run() {
				path = System.getProperty("user.home");
				path = path + "/TDP" + "/dirLog";
				save();		
				
			}
		};
		
		return exe;
	}
	
	public void setDomainView(DomainView domainView) {
		DomainView = domainView;
		display=DomainView.getPrincipalView().getPlanView().getDisplay();
		
	}

	private void save() {
		saveDomain();
		savePlan();
	}

	private void saveDomain() {
		SaveDomainCommand command=new SaveDomainCommand();
		command.execute(path,DomainView);
	}
	
	private void savePlan() {
		SavePlanCommand command=new SavePlanCommand();
		PlanView planView=DomainView.getPrincipalView().getPlanView();
		for(PlanContent planContent:planView.getAllPlan()) {
			command.setPlanContent(planContent);
			command.execute(path,"Plan.txt");

		}
				
	}
}
