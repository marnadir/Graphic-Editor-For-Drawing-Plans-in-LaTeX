package GUI;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import command.ChangeStateCommand;
import command.CreateStateDialogCommand;
import command.ElimanateStateCommand;
import logic.InitialState;


 class CreateDomainView{

	Group domainGroup;
	Shell shell;
	Group stateGroup;
	SashForm sashForm;
	Composite outer;
	Composite inside;
	Composite contentCanvas;
	Combo comboOptionInSt;
	Composite ContentInitState;
	 Composite test;
	
	InitialState initialState=null;
	

	public CreateDomainView(SashForm sashForm) {
		this.sashForm=sashForm;
		this.shell=sashForm.getShell();
		setLayout();
		
		
	}

	public void setLayout() {
		
		outer=new Composite(sashForm, SWT.ALL);
//		FormLayout formLayout = new FormLayout();
//		formLayout.marginHeight = 5;
//		formLayout.marginWidth = 5;
//		formLayout.spacing = 5;
//		outer.setLayout( formLayout );
		outer.setLayout(new FillLayout());	
		
		this.domainGroup = new Group(outer, SWT.BORDER);
		Font boldFont = new Font(this.domainGroup.getDisplay(), new FontData("Arial", 12, SWT.BOLD));
		this.domainGroup.setText("Domain Graph");
		this.domainGroup.setFont(boldFont);
		
		domainGroup.setLayout(new GridLayout(1, false));
		
		
		inside=new Composite(domainGroup, SWT.ALL);
	    inside.setLayout(new GridLayout(1, true));
        inside.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));


	
		
	}

	public void createContent() {

		//first group option
		
		Group subOption = new Group(inside, SWT.ALL);
		subOption.setText("Option");
		subOption.setLayout(new GridLayout(4, false));
		
		Label initialState=new Label(subOption, SWT.ALL);
		initialState.setText("Initial State: ");
		
		comboOptionInSt = new Combo (subOption, SWT.READ_ONLY);
		List<String> possibleOption=new ArrayList<String>();
		possibleOption.add("Create");
		String[] convertList=possibleOption.toArray(new String[possibleOption.size()]);
		comboOptionInSt.setItems (convertList);
		
		Rectangle clientArea = subOption.getClientArea ();
		comboOptionInSt.setBounds (clientArea.x, clientArea.y, 200, 200);
		
		
		Button bInitState=new Button(subOption, SWT.PUSH);
		Image img=new Image(shell.getDisplay(), "img/ok.png") ;
		bInitState.setImage(img);
		
		
		CreateStateDialogCommand so=new CreateStateDialogCommand();
		ElimanateStateCommand elimCmd=new ElimanateStateCommand();
		ChangeStateCommand changeCmd=new ChangeStateCommand();

		Listener buttonLister=new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				
				so.execute(comboOptionInSt, ContentInitState);
				if(elimCmd.canExecute(comboOptionInSt)) {
					elimCmd.execute(comboOptionInSt,so.getInitialState());
				    ContentInitState.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_RED));//green
				
				}else if(changeCmd.canExecute(comboOptionInSt)) {
					changeCmd.execute(so.getCreateSoDialog(),so.getInitialState());
					
				}
				
				
				
			
				
				
				
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
		
		
		stateGroup=new Group(inside,SWT.NONE);
		stateGroup.setText("Items for the plan");
		stateGroup.setLayout(new GridLayout(1, true));
		GridData firstData = new GridData(SWT.FILL, SWT.FILL, true, true);
		firstData.heightHint = 750;
		stateGroup.setLayoutData(firstData);
		
		
		
		ScrolledComposite firstScroll = new ScrolledComposite(stateGroup, SWT.V_SCROLL | SWT.H_SCROLL);
	    firstScroll.setLayout(new GridLayout(1,false));
	    firstScroll.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

	    contentCanvas = new Composite(firstScroll, SWT.NONE);

		FillLayout fillLayout = new FillLayout();

		fillLayout.type = SWT.VERTICAL;

		contentCanvas.setLayout(fillLayout);
     
	    contentCanvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    contentCanvas.setBackground(new Color (shell.getDisplay(), 255, 0, 0));//red
	    
	    test=new Composite(contentCanvas, SWT.ALL);
	    test.setLayout(new GridLayout(2, false));
	   
	    
	    
	    //test.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    //test.setBackground(new Color (shell.getDisplay(), 0, 128, 0));//green
	   
	    Composite test2=new Composite(contentCanvas, SWT.ALL);
	    test2.setLayout(new GridLayout(1, false));
	    
	    
	    
	    
	    
	    ContentInitState=new Composite(test, SWT.PUSH);
	    ContentInitState.setLayout(new GridLayout(1, false));
	    ContentInitState.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    ContentInitState.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_BLACK));//green

	    
	    Composite ContentFinalState=new Composite(test, SWT.PUSH);
	    ContentFinalState.setLayout(new GridLayout(1, false));
	    ContentFinalState.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_BLUE));
	   
	   ContentFinalState.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//	    
	    Composite ContentActions=new Composite(test2, SWT.NONE);
	    ContentActions.setLayout(new FillLayout());
	    ContentActions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    
	    firstScroll.setContent(contentCanvas);
	    firstScroll.setExpandHorizontal(true);
	    firstScroll.setExpandVertical(true);
	    firstScroll.setMinSize(contentCanvas.computeSize(SWT.DEFAULT, SWT.DEFAULT));

	    


		


	}
	
	
	public Group getCompCanvas() {
		return stateGroup;
	}
}
