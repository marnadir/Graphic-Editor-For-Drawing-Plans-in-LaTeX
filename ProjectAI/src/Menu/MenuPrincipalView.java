package Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;

import Dialog.LoadDomainDialog;
import Dialog.LoadPlanDialog;
import Dialog.NewConnectionDialog;
import Dialog.SaveDomainFileLocationDialog;
import View.DomainView;
import View.PlanView;
import command.ExitCommand;
import command.LoadDomainCommand;




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
		
		MenuItem help = createItem("&Help", SWT.CASCADE);
		IMenu menuHelp= new IMenu(getShell(), SWT.DROP_DOWN);
		help.setMenu(menuHelp);
		

		MenuItem storeStateDomain = new MenuItem(menuFile, SWT.PUSH);
		storeStateDomain.setText("&Save Domain\tCtrl+S");
		storeStateDomain.setAccelerator( SWT.CONTROL + 'S');


		MenuItem restoreStateDomain = new MenuItem(menuFile, SWT.PUSH);
		restoreStateDomain.setText("&Load Domain\tCtrl+R");
		restoreStateDomain.setAccelerator( SWT.CONTROL + 'R');


		
		MenuItem loadPlan = new MenuItem(menuFile, SWT.PUSH);
		loadPlan.setText("&Load Plan\t");

		MenuItem exitItem = new MenuItem(menuFile, SWT.PUSH);
		exitItem.setText("&Exit");



		MenuItem menuLines = new MenuItem(menuOption, SWT.PUSH);
		menuLines.setText("Add Ordering/Link");
		
		MenuItem tutorial = new MenuItem(menuHelp, SWT.PUSH);
		tutorial.setText("&Tutorial");
		
		
		
		Listener listenerExit = new Listener() {
			@Override
			public void handleEvent(Event event) {
				ExitCommand command = new ExitCommand();
				command.execute(getShell(), event);
			}
		};

		
		Listener listenerStoreDomain = new Listener() {
			@Override
			public void handleEvent(Event event) {
				
				SaveDomainFileLocationDialog dialog=new SaveDomainFileLocationDialog(getShell(), SWT.SAVE);
				dialog.setDomainView(domainView);
				dialog.createContent();
			}
		};

		Listener listenerLoadDomain = new Listener() {

			@Override
			public void handleEvent(Event event) {
				
				LoadDomainDialog dialog=new LoadDomainDialog(getShell(), SWT.MULTI);
				dialog.setDomainView(domainView);
				dialog.createContent();
				

			}
		};

		
		Listener loadL = new Listener() {

			@Override
			public void handleEvent(Event event) {
				LoadPlanDialog dialog=new LoadPlanDialog(getShell(), SWT.MULTI);
				//devo creare una nuova pagina dove salvare il nuovo piano
				dialog.setPlanContent(planView.getPlan());
				dialog.createContent();
				//if i saved the plan, otherwise i get a null pointer 
				if(dialog.getCommand().isLoad()) {
					planView.getPlan().setSavedPllan(dialog.getCommand().getFilePlan());
					planView.getPlan().setDirectory(dialog.getCommand().getFilePlan().getParentFile());
					LoadDomainCommand command=new LoadDomainCommand();
					String path=dialog.getCommand().getFilePlan().getParentFile()+"/tempDomain.txt";
					command.execute(path,domainView);
					
				}
			
			

			}
		};
		
		
		Listener listenerLink=new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				NewConnectionDialog dialog=new NewConnectionDialog(getShell(), 
						SWT.DIALOG_TRIM | SWT.CENTER | SWT.RESIZE);
				dialog.setPlanView(planView);
				dialog.createContent();
				
			}
		};
		
		
		
		loadPlan.addListener(SWT.Selection, loadL);
	
		restoreStateDomain.addListener(SWT.Selection, listenerLoadDomain);
		storeStateDomain.addListener(SWT.Selection, listenerStoreDomain);
		exitItem.addListener(SWT.Selection, listenerExit);
		menuLines.addListener(SWT.Selection, listenerLink);

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

	
}
