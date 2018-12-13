package GUI;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import DND.MyDragSourceListener;
import Dialog.CreateActionDialogCommand;
import Dialog.CreateGoalDialogCommand;
import Dialog.CreateSoDialogCommand;
import logic.Action;
import command.ChangeEffCommand;
import command.ChangeNameCommand;
import command.ChangePrecCommand;
import command.ChangeStateCommand;
import command.EliminateActionCommand;
import command.EliminateStateCommand;
import logic.ContentAction;
import logic.InitialState;
import logic.CanvasAction;

class CreateDomainView {

	Group domainGroup;
	Shell shell;
	Group stateGroup;
	SashForm sashForm;
	Composite outer;
	Composite inside;
	Composite contentCanvas;
	Combo comboOptionInSt;
	Composite ContentInitState;
	Composite part1;
	Composite part2;
	Composite ContentFinalState;
	Combo comboOptionFnst;
	ContentAction compositeAction;
	Composite containerAction;
	Tree treeAction;
	Group subOption;
	ArrayList<Action> actionsArray;

	MenuItem showAction;
	MenuItem showActionW;
	MenuItem elimAction;
	MenuItem modifAction;

	CreateActionDialogCommand actionCommnd;

	InitialState initialState = null;

	public CreateDomainView(SashForm sashForm) {
		this.sashForm = sashForm;
		this.shell = sashForm.getShell();
		setLayout();
		actionCommnd = new CreateActionDialogCommand();
		actionsArray = new ArrayList<>();

	}

	public void setLayout() {

		outer = new Composite(sashForm, SWT.ALL);
		outer.setLayout(new FillLayout());

		this.domainGroup = new Group(outer, SWT.BORDER);
		Font boldFont = new Font(this.domainGroup.getDisplay(), new FontData("Arial", 12, SWT.BOLD));
		this.domainGroup.setText("Domain Graph");
		this.domainGroup.setFont(boldFont);

		domainGroup.setLayout(new GridLayout(1, false));

		inside = new Composite(domainGroup, SWT.ALL);
		inside.setLayout(new GridLayout(1, true));
		inside.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

	}

	public void createContent() {

		// first group option

		subOption = new Group(inside, SWT.ALL);
		subOption.setText("Option");

		subOption.setLayout(new GridLayout(4, false));

		Label initialState = new Label(subOption, SWT.ALL);
		initialState.setText("Initial State: ");

		comboOptionInSt = new Combo(subOption, SWT.READ_ONLY);
		ArrayList<String> possibleOption = new ArrayList<String>();
		possibleOption.add("Create");
		String[] convertList = possibleOption.toArray(new String[possibleOption.size()]);
		comboOptionInSt.setItems(convertList);

		Rectangle clientArea = subOption.getClientArea();
		comboOptionInSt.setBounds(clientArea.x, clientArea.y, 200, 200);

		Button bInitState = new Button(subOption, SWT.PUSH);
		Image img = new Image(shell.getDisplay(), "img/ok.png");
		bInitState.setImage(img);

		GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
		gridData.horizontalSpan = 2;
		comboOptionInSt.setLayoutData(gridData);

		Label finalState = new Label(subOption, SWT.ALL);
		finalState.setText("Final State: ");
		comboOptionFnst = new Combo(subOption, SWT.READ_ONLY);
		possibleOption = new ArrayList<String>();
		possibleOption.add("Create");
		convertList = possibleOption.toArray(new String[possibleOption.size()]);
		comboOptionFnst.setItems(convertList);
		comboOptionFnst.setBounds(clientArea.x, clientArea.y, 200, 200);
		comboOptionFnst.setLayoutData(gridData);

		Button bFnState = new Button(subOption, SWT.PUSH);
		bFnState.setImage(img);

		Label actionLabel = new Label(subOption, SWT.ALL);
		actionLabel.setText("Action:  ");

		Button bntAct = new Button(subOption, SWT.PUSH);
		img = new Image(shell.getDisplay(), "img/addCond.png");
		bntAct.setImage(img);

		stateGroup = new Group(inside, SWT.NONE);
		stateGroup.setText("Items for the plan");
		stateGroup.setLayout(new GridLayout(1, true));
		GridData firstData = new GridData(SWT.FILL, SWT.FILL, true, true);
		firstData.heightHint = 750;
		stateGroup.setLayoutData(firstData);

		ScrolledComposite firstScroll = new ScrolledComposite(stateGroup, SWT.V_SCROLL | SWT.H_SCROLL);
		firstScroll.setLayout(new GridLayout(1, false));
		firstScroll.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		contentCanvas = new Composite(firstScroll, SWT.ALL);
		FillLayout fillLayout = new FillLayout();
		fillLayout.type = SWT.VERTICAL;
		contentCanvas.setLayout(fillLayout);

		part1 = new Composite(contentCanvas, SWT.ALL);
		fillLayout = new FillLayout();
		fillLayout.type = SWT.HORIZONTAL;
		part1.setLayout(fillLayout);

		ContentInitState = new Composite(part1, SWT.BORDER);
		ContentInitState.setLayout(new GridLayout(1, false));

		ContentFinalState = new Composite(part1, SWT.BORDER);
		ContentFinalState.setLayout(new GridLayout(1, false));

		part2 = new Composite(contentCanvas, SWT.ALL);
		part2.setLayout(new GridLayout(3, true));

		final ScrolledComposite composite = new ScrolledComposite(part2, SWT.V_SCROLL);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		treeAction = new Tree(composite, SWT.BORDER);
		final Menu menu = new Menu(treeAction);
		treeAction.setToolTipText("Created Action");
		treeAction.setMenu(menu);

		MenuItem[] items = menu.getItems();
		for (int i = 0; i < items.length; i++) {
			items[i].dispose();
		}
		showAction = new MenuItem(menu, SWT.PUSH);
		showAction.setText("Draw Action");

		elimAction = new MenuItem(menu, SWT.PUSH);
		elimAction.setText("Eliminate Action");

		modifAction = new MenuItem(menu, SWT.CASCADE);
		modifAction.setText("Modify...");

		Menu subMenu = new Menu(menu);
		modifAction.setMenu(subMenu);

		MenuItem modifName = new MenuItem(subMenu, SWT.PUSH);
		modifName.setText("Name");

		MenuItem modifPrec = new MenuItem(subMenu, SWT.PUSH);
		modifPrec.setText("Preconditions");

		MenuItem modifEff = new MenuItem(subMenu, SWT.PUSH);
		modifEff.setText("Effects");

		composite.setContent(treeAction);
		composite.setExpandHorizontal(true);
		composite.setExpandVertical(true);
		composite.setAlwaysShowScrollBars(true);
		composite.setMinSize(treeAction.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		compositeAction = new ContentAction(part2, SWT.BORDER);
		//compositeAction.setLayout(new GridLayout(1, false));
		gridData = new GridData(GridData.FILL, GridData.FILL, false, false);
		gridData.horizontalSpan = 2;
		compositeAction.setLayoutData(gridData);
		
		compositeAction.setBackground(compositeAction.getDisplay().getSystemColor(SWT.COLOR_RED));

		//TODO need to put the container center in the parent composite and resize enough for canvasAction 
		
		
		
		containerAction=new Composite(compositeAction, SWT.BORDER);
		containerAction.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
		containerAction.setLayout(new FillLayout());
		//containerAction.setEnabled(false);

		
	
		containerAction.setLocation(40,150);
		containerAction.setBackground(containerAction.getDisplay().getSystemColor(SWT.COLOR_BLUE));
		containerAction.setSize(50,50);
		//compositeAction.addlistener(containerAction);
		
//		DragSource source = new DragSource(stateGroup,DND.DROP_NONE );
//		final TextTransfer textTransfer = TextTransfer.getInstance();
//		final FileTransfer fileTransfer = FileTransfer.getInstance();
//		Transfer[] types = new Transfer[] { fileTransfer, textTransfer };
//		source.setTransfer(types); // varargs are supported as of 4.7
//		source.addDragListener(new MyDragSourceListener(source));
		

		firstScroll.setContent(contentCanvas);
		firstScroll.setExpandHorizontal(true);
		firstScroll.setExpandVertical(true);
		firstScroll.setMinSize(contentCanvas.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		
		

		
		
		
		
		
		
		
		
		
		
		
		showAction.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {

				//TODO posso creare solo un azione,dovrei ripulire ad ogni nuovo comando
				
				TreeItem[] actions = treeAction.getSelection();
				
				if(containerAction.getChildren().length>0) {
					containerAction.redraw();
				}
				if (actions.length > 0) {
					TreeItem actionItem = getRoot(actions[0]);
					Action action = findAction(actionItem.getText());
					CanvasAction canvasAction=new CanvasAction(containerAction,SWT.ALL,action);
					action.setPaint(canvasAction);
					canvasAction.draw();
					canvasAction.addDNDListener();
					compositeAction.setPaintAction(canvasAction);

				}
					
			}
		});

		elimAction.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				TreeItem[] actions = treeAction.getSelection();
				if (actions.length > 0) {

					TreeItem actionItem = getRoot(actions[0]);
					Action action = findAction(actionItem.getText());
					actionItem.dispose();
					actionsArray.remove(action);
				}

			}
		});

		modifName.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				TreeItem[] actions = treeAction.getSelection();
				if (actions.length > 0) {
					TreeItem actionItem = getRoot(actions[0]);
					Action action = findAction(actionItem.getText());
					ChangeNameCommand cmd = new ChangeNameCommand();
					cmd.execute(actionItem, action);

				}

			}
		});

		modifPrec.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				TreeItem[] actions = treeAction.getSelection();
				if (actions.length > 0) {
					TreeItem actionItem = getRoot(actions[0]);
					Action action = findAction(actionItem.getText());
					ChangePrecCommand cmd = new ChangePrecCommand();
					cmd.execute(action, actionItem);

				}

			}
		});

		modifEff.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				TreeItem[] actions = treeAction.getSelection();
				if (actions.length > 0) {
					TreeItem actionItem = getRoot(actions[0]);
					Action action = findAction(actionItem.getText());
					ChangeEffCommand cmd = new ChangeEffCommand();
					cmd.execute(action, actionItem);

				}

			}
		});

		CreateSoDialogCommand so = new CreateSoDialogCommand();
		EliminateStateCommand elimCmd = new EliminateStateCommand();
		ChangeStateCommand changeCmd = new ChangeStateCommand();

		Listener buttonInLister = new Listener() {

			@Override
			public void handleEvent(Event event) {

				so.execute(comboOptionInSt, ContentInitState);
				if (elimCmd.canExecute(comboOptionInSt)) {
					elimCmd.execute(comboOptionInSt, so.getInitialState());

				} else if (changeCmd.canExecute(comboOptionInSt)) {
					changeCmd.execute(so.getCreateSoDialog(), so.getInitialState());

				}

			}
		};

		CreateGoalDialogCommand goalCommand = new CreateGoalDialogCommand();

		Listener buttonFinLister = new Listener() {

			// TODO
			@Override
			public void handleEvent(Event event) {

				goalCommand.execute(comboOptionFnst, ContentFinalState);
				if (elimCmd.canExecute(comboOptionFnst)) {
					elimCmd.execute(comboOptionFnst, goalCommand.getGoalState());

				} else if (changeCmd.canExecute(comboOptionFnst)) {
					changeCmd.execute(goalCommand.getCreateGoalDialog(), goalCommand.getGoalState());

				}

			}
		};

		EliminateActionCommand elimAct = new EliminateActionCommand();

		Listener buttonActLister = new Listener() {

			// TODO
			@Override
			public void handleEvent(Event event) {
				actionCommnd.execute(treeAction, actionsArray);
				elimAct.execute(actionsArray);
				subOption.pack();
			}	
		};

		bInitState.addListener(SWT.Selection, buttonInLister);
		bFnState.addListener(SWT.Selection, buttonFinLister);
		bntAct.addListener(SWT.Selection, buttonActLister);
		
		
	
		

	}

	public TreeItem getRoot(TreeItem a) {
		while (a.getParentItem() instanceof TreeItem) {
			a = a.getParentItem();
		}
		return a;
	}

	public Action findAction(String actionName) {
		for (int i = 0; i < actionsArray.size(); i++) {
			if (actionsArray.get(i).getName().equals(actionName)) {
				return actionsArray.get(i);
			}
		}
		return null;
	}
}
