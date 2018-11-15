package GUI;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import command.CreateStateDialogCommand;
import logic.InitialState;


 class CreateDomainView{

	Group domainGroup;
	Shell shell;
	Group stateGroup;
	SashForm sashForm;
	
	InitialState initialState=null;
	

	public CreateDomainView(SashForm sashForm) {
		this.sashForm=sashForm;
		this.shell=sashForm.getShell();
		setLayout();
		
		
	}

	public void setLayout() {
		
//		Composite comp=new Composite(sashForm, SWT.ALL);
		
		this.domainGroup = new Group(sashForm, SWT.None);
		Font boldFont = new Font(this.domainGroup.getDisplay(), new FontData("Arial", 12, SWT.BOLD));
		this.domainGroup.setText("Domain Graph");
		this.domainGroup.setFont(boldFont);
		FormLayout ownerLayout = new FormLayout();
		ownerLayout.marginWidth = 5;
		ownerLayout.marginHeight = 5;
		this.domainGroup.setLayout(ownerLayout);
		
//		 GridLayout layout=new GridLayout();
//		  layout.numColumns=3;
//		  domainGroup.setLayout(layout);
//		this.domainGroup.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		
	}

	public void createContent() {

		//first group option
		
		Group subOption = new Group(this.domainGroup, SWT.ALL);
		subOption.setText("Option");
		subOption.setLayout(new GridLayout(4, false));
		
		Label initialState=new Label(subOption, SWT.ALL);
		initialState.setText("Initial State: ");
		Combo comboOptionInSt = new Combo (subOption, SWT.READ_ONLY);
		
		
		List<String> possibleOption=new ArrayList<String>();
		possibleOption.add("Create");
		String[] convertList=possibleOption.toArray(new String[possibleOption.size()]);
		comboOptionInSt.setItems (convertList);
		
		Rectangle clientArea = subOption.getClientArea ();
		comboOptionInSt.setBounds (clientArea.x, clientArea.y, 200, 200);
		
		
		Button bInitState=new Button(subOption, SWT.PUSH);
		Image img=new Image(shell.getDisplay(), "img/ok.png") ;
		bInitState.setImage(img);
		
		
		
		Listener buttonLister=new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				CreateStateDialogCommand so=new CreateStateDialogCommand();
				so.execute(comboOptionInSt, stateGroup);
				
			}
		};
		bInitState.addListener(SWT.Selection, buttonLister);
		
		GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
		gridData.horizontalSpan = 2;
		comboOptionInSt.setLayoutData(gridData);
		
		Label finalState=new Label(subOption, SWT.ALL);
		finalState.setText("Final State: ");
		Combo comboOptionFnst = new Combo (subOption, SWT.READ_ONLY);
		comboOptionFnst.setItems ("Create", "Change", "Eliminate");
		comboOptionFnst.setBounds (clientArea.x, clientArea.y, 200, 200);
		Button bFnState=new Button(subOption, SWT.PUSH);
		bFnState.setImage(img);
		
		comboOptionFnst.setLayoutData(gridData);

		
		Label actionLabel=new Label(subOption, SWT.ALL);
		actionLabel.setText("Action:  ");
		Combo comboOptionAction = new Combo (subOption, SWT.READ_ONLY);
		comboOptionAction.setItems ("Create", "Change", "Eliminate");
		comboOptionAction.setBounds (clientArea.x, clientArea.y, 200, 200);
		Combo CombolistAction=new Combo(subOption, SWT.READ_ONLY);
		//TODO action rename
		ArrayList<String> listAction=new ArrayList<>();
		listAction.add("Goto");
		CombolistAction.setItems(listAction.get(0));
		
		Button bAction=new Button(subOption, SWT.PUSH);
		bAction.setImage(img);
		
		
		stateGroup=new Group(this.domainGroup, SWT.ALL);
		stateGroup.setText("Items for the plan");
	

		FormData data = new FormData();
		data.top = new FormAttachment(subOption, 5);
		data.bottom=new FormAttachment(100,0);
     	data.width=100;
     	stateGroup.setLayoutData(data);
		
//     	Composite comp=new Composite(stateGroup, SWT.ALL);
//     	comp.pack();
//     	
//     	
//     	Canvas canvans=new Canvas(stateGroup, SWT.ALL);
//     	canvans.setSize(500, 500);
//     	
//     	canvans.addPaintListener(new PaintListener() {
//			
//			@Override
//			public void paintControl(PaintEvent e) {
//				e.gc.drawText("ciao come stai", 500, 500);
//			}
//		});
//		System.out.print(canvans.getClientArea());
//		System.out.print(comp.getClientArea());

	}
	
	
	public Group getCompCanvas() {
		return stateGroup;
	}
}
