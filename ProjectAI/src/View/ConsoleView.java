package View;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import GraphPart.GraphContent;

public class ConsoleView extends Group{

	public ConsoleView(Composite parent, int style) {
		super(parent, style);
		setText("Console");
		// TODO Auto-generated constructor stub
	}
	
	
	public void setLayout() {
		setLayout(new GridLayout(2, true));

	}
	
	public void createContent(DomainView domainView,GraphContent contentAction,PlanView planView) {
		ConsoleViewDomain consoleViewDomain=new ConsoleViewDomain(this, SWT.ALL);
		consoleViewDomain.setLayout();
		consoleViewDomain.createContent(domainView);
		
		ConsoleViewPlan consoleViewPlan=new ConsoleViewPlan(this, SWT.ALL);
		consoleViewPlan.setLayout();
		consoleViewPlan.createContent(contentAction, planView);
		
		
	}
	
	@Override
	protected void checkSubclass() {
		
	}
}
