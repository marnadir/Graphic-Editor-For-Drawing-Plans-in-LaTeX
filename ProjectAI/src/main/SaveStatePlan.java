package main;

import java.util.TimerTask;

import View.DomainView;
import command.SaveDomainCommand;
import command.SavePlanCommand;

public class SaveStatePlan extends TimerTask {

	String path;
	DomainView DomainView;
	
	public void run() {
		path = System.getProperty("user.home");
		path = path + "/TDP" + "/dirLog";
		save();		
	}
	
	
	
	public void setDomainView(DomainView domainView) {
		DomainView = domainView;
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
		command.execute();
				
	}
}
