package Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;

import PlanPart.PlanContent;
import View.DomainView;
import View.PlanView;
import command.ExitCommand;
import command.SaveDomainCommand;
import dialog.option.NewConnectionDialog;
import dialog.save.LoadDomainDialog;
import dialog.save.LoadPlanDialog;
import dialog.save.SaveDomainFileLocationDialog;
import dialog.save.SavePlanDialog;

/**
 * Instance of object that implements the menu of the principal view.
 * @author nadir
 * */


public class MenuPrincipalView extends IMenu{
	
	DomainView domainView;

	public MenuPrincipalView(Decorations parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}

	public void fillMenu(DomainView domainView,PlanView planView) {
		
		this.domainView=domainView;
		
		MenuItem fileItem = createItem("&File", SWT.CASCADE);
		IMenu menuFile = new IMenu(getShell(), SWT.DROP_DOWN);
		fileItem.setMenu(menuFile);

		MenuItem option = createItem("&Option", SWT.CASCADE);
		IMenu menuOption = new IMenu(getShell(), SWT.DROP_DOWN);
		option.setMenu(menuOption);
		
		addSaveDomainOption(menuFile);
		addSavePlanOption(menuFile, planView.getCurrentPlan());
		
		addLoadDomainOption(menuFile);
		addLoadPlanOption(menuFile, planView);
		
		addExitOption(menuFile);
		
		addNewConstraintsOption(menuOption, planView);
		


		getShell().setMenuBar(this);
		getShell().addListener(SWT.Close, e -> {
			if (getShell().getModified()) {
				MessageBox box = new MessageBox(getShell(), SWT.PRIMARY_MODAL | SWT.OK | SWT.CANCEL);
				box.setText(getShell().getText());
				box.setMessage("You have unsaved changes, do you want to exit?");
				e.doit = box.open() == SWT.OK;
			}
		});
	}

	
	private void addSavePlanOption(IMenu menuFile,PlanContent planContent) {
		MenuItem savePlan = new MenuItem(menuFile, SWT.PUSH);
		savePlan.setText("&Save Plan\tCtrl+P");
		savePlan.setAccelerator( SWT.CONTROL + 'P');
		Listener listenerSavePlan = new Listener() {
			@Override
			public void handleEvent(Event event) {
			
				
				SavePlanDialog dialog=new SavePlanDialog(getShell(), SWT.SAVE);
				dialog.setPlanContent(planContent);
				dialog.createContent();
				planContent.setSavedPllan(dialog.getCommand().getPlanFile());
				SaveDomainCommand command=new SaveDomainCommand();
				command.copyFileDomain(planContent.getSavedPlanFile().getParentFile()
							.getAbsolutePath(), domainView);

				
				
			}
		};
		
		savePlan.addListener(SWT.Selection, listenerSavePlan);
	}
	
	private void addSaveDomainOption(IMenu menuFile) {
		MenuItem saveStateDomain = new MenuItem(menuFile, SWT.PUSH);
		saveStateDomain.setText("&Save Domain\tCtrl+S");
		saveStateDomain.setAccelerator( SWT.CONTROL + 'S');
		Listener listenerSaveDomain = new Listener() {
			@Override
			public void handleEvent(Event event) {
				
				SaveDomainFileLocationDialog dialog=new SaveDomainFileLocationDialog(getShell(), SWT.SAVE);
				dialog.setDomainView(domainView);
				dialog.createContent();
			}
		};
		
		saveStateDomain.addListener(SWT.Selection, listenerSaveDomain);

	}
	
	private void addExitOption(IMenu menuFile) {

		MenuItem exitItem = new MenuItem(menuFile, SWT.PUSH);
		exitItem.setText("&Exit");	
		Listener listenerExit = new Listener() {
			@Override
			public void handleEvent(Event event) {
				ExitCommand command = new ExitCommand();
				command.execute(getShell(), event);
			}
		};
		
		exitItem.addListener(SWT.Selection, listenerExit);

	}
	
	private void addLoadPlanOption(IMenu menuFile,PlanView planView){
		MenuItem loadPlan = new MenuItem(menuFile, SWT.PUSH);
		loadPlan.setText("&Load Plan\t");
		
		Listener listenerLoadPlan = new Listener() {

			@Override
			public void handleEvent(Event event) {
				LoadPlanDialog dialog=new LoadPlanDialog(getShell(), SWT.MULTI);
				dialog.setPlanContent(planView.getCurrentPlan());
				dialog.createContent();
			}
		};
		loadPlan.addListener(SWT.Selection, listenerLoadPlan);

		
		
	}
	
	private void addLoadDomainOption(IMenu menuFile) {
	
		
		MenuItem loadStateDomain = new MenuItem(menuFile, SWT.PUSH);
		loadStateDomain.setText("&Load Domain\tCtrl+R");
		loadStateDomain.setAccelerator( SWT.CONTROL + 'R');
		
		Listener listenerLoadDomain = new Listener() {

			@Override
			public void handleEvent(Event event) {
				
				LoadDomainDialog dialog=new LoadDomainDialog(getShell(), SWT.MULTI);
				dialog.setDomainView(domainView);
				dialog.createContent();
				

			}
		};
		loadStateDomain.addListener(SWT.Selection, listenerLoadDomain);

		
	}
	
	private void addNewConstraintsOption(IMenu menuOption,PlanView planView) {

		MenuItem menuLines = new MenuItem(menuOption, SWT.PUSH);
			menuLines.setText("Add Constraints");
			Listener listenerLink=new Listener() {	
				@Override
				public void handleEvent(Event event) {
					NewConnectionDialog dialog=new NewConnectionDialog(getShell(), 
							SWT.DIALOG_TRIM | SWT.CENTER | SWT.RESIZE);
					dialog.setPlanView(planView);
					dialog.createContent();		
				}
			};
			menuLines.addListener(SWT.Selection, listenerLink);
	}
	
	
}
