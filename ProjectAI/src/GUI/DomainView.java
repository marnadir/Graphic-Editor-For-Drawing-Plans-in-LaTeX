package GUI;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import Action.Action;
import Action.CanvasAction;
import State.IStateCanvas;
import State.InitialStateCanvas;
import command.ChangeEffCommand;
import command.ChangeNameCommand;
import command.ChangePrecCommand;
import command.CreateActionDialogCommand;
import command.CreateGoalDialogCommand;
import command.CreateSoDialogCommand;
import command.EliminateActionCommand;

public class DomainView {

	Group domainGroup;
	Shell shell;
	Group stateGroup;
	SashForm sashForm;
	Composite outer;
	Composite inside;
	Composite contentCanvas;
	Composite contentInitState;
	Composite containerInitState;
	Composite part1;
	Composite part2;
	Composite contentGoalState;
	Composite containerGoalState;
	Composite compositeAction;
	Composite containerAction;
	Tree treeAction;
	Group subOption;
	ArrayList<Action> actionList;
	
	
	MenuItem showAction;
	MenuItem showActionW;
	MenuItem elimAction;
	MenuItem modifAction;

	CreateActionDialogCommand actionCommnd;

	InitialStateCanvas initialState = null;

	public DomainView(SashForm sashForm) {
		this.sashForm = sashForm;
		this.shell = sashForm.getShell();
		setLayout();
		actionCommnd = new CreateActionDialogCommand();
		actionList = new ArrayList<>();

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

		subOption = new Group(inside, SWT.ALL);
		subOption.setText("Option");

		subOption.setLayout(new GridLayout(4, false));

		Label initialState = new Label(subOption, SWT.ALL);
		initialState.setText("Initial State: ");

		Button bInitState = new Button(subOption, SWT.PUSH);
		Image img = new Image(shell.getDisplay(), "img/ok.png");
		bInitState.setImage(img);

		GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
		gridData.horizontalSpan = 2;

		Label finalState = new Label(subOption, SWT.ALL);
		finalState.setText("Final State: ");
		
		Button bFnState = new Button(subOption, SWT.PUSH);
		bFnState.setImage(img);

		Label actionLabel = new Label(subOption, SWT.ALL);
		actionLabel.setText("Action:  ");

		Button bntAct = new Button(subOption, SWT.PUSH);
		img = new Image(shell.getDisplay(), "img/ok.png");
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

		contentInitState = new Composite(part1, SWT.BORDER);
		containerInitState=new Composite(contentInitState, SWT.ALL);
		containerInitState.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
		containerInitState.setLayout(new FillLayout());
		containerInitState.setLocation(30,80);


		contentGoalState = new Composite(part1, SWT.BORDER);
		containerGoalState=new Composite(contentGoalState, SWT.ALL);
		containerGoalState.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
		containerGoalState.setLayout(new FillLayout());
		containerGoalState.setLocation(70,80);

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

		compositeAction = new Composite(part2, SWT.BORDER);
		gridData = new GridData(GridData.FILL, GridData.FILL, false, false);
		gridData.horizontalSpan = 2;
		compositeAction.setLayoutData(gridData);
		
		firstScroll.setContent(contentCanvas);
		firstScroll.setExpandHorizontal(true);
		firstScroll.setExpandVertical(true);
		firstScroll.setMinSize(contentCanvas.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		CreateSoDialogCommand so = new CreateSoDialogCommand();
		CreateGoalDialogCommand goalCommand = new CreateGoalDialogCommand();
		EliminateActionCommand elimAct = new EliminateActionCommand();

		
		
		showAction.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {

				TreeItem[] actions = treeAction.getSelection();

				if (actions.length > 0) {
					TreeItem actionItem = getRoot(actions[0]);
					Action action = findAction(actionItem.getText());
					if (containerAction != null) {
						containerAction.dispose();
					}
					containerAction = new Composite(compositeAction, SWT.BORDER);
					containerAction.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
					containerAction.setLayout(new FillLayout());
					containerAction.setLocation(15, 150);

					CanvasAction canvasAction = new CanvasAction(containerAction,
							SWT.DOUBLE_BUFFERED | SWT.NO_REDRAW_RESIZE, action);
					canvasAction.draw();
					canvasAction.addDNDListener();

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
					actionList.remove(action);
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

		Listener buttonInLister = new Listener() {

			@Override
			public void handleEvent(Event event) {
				so.execute(containerInitState);
			}
		};

		Listener buttonFinLister = new Listener() {

			
			@Override
			public void handleEvent(Event event) {
				goalCommand.execute(containerGoalState);

			}
		};


		Listener buttonActLister = new Listener() {

			@Override
			public void handleEvent(Event event) {
				actionCommnd.execute(treeAction, actionList);
				elimAct.execute(actionList);
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
		for (int i = 0; i < actionList.size(); i++) {
			if (actionList.get(i).getName().equals(actionName)) {
				return actionList.get(i);
			}
		}
		return null;
	}
	
	public ArrayList<Action> getListAction(){
		return actionList;
	}
	
	public void restoreActionList(ArrayList<Action> actions) {
		actionList=actions;
		for(Action action:actionList) {
			TreeItem item=new TreeItem(treeAction, SWT.BORDER);
			item.setText(action.getName());
			TreeItem childPrec = new TreeItem(item, SWT.NONE);
			childPrec.setText("Preconditions");
			TreeItem childEff = new TreeItem(item, SWT.NONE);
			childEff.setText("Effect");
			for(String pString:action.getPrec()) {
				TreeItem child = new TreeItem(childPrec, SWT.NONE);
				child.setText(pString);
			}

			for(String eString:action.getEffect()) {
				TreeItem child = new TreeItem(childEff, SWT.NONE);
				child.setText(eString);
			}
		}
	}
	
	public IStateCanvas getInitialState() {
		Control[] child=containerInitState.getChildren();
		for(int i=0;i<child.length;i++) {
			if(child[i] instanceof IStateCanvas) {
				IStateCanvas iStateCanvas=(IStateCanvas) child[i];
				return iStateCanvas;
			}
		}
		return null;
	}
	public IStateCanvas getGoalState() {
		Control[] child=containerGoalState.getChildren();
		for(int i=0;i<child.length;i++) {
			if(child[i] instanceof IStateCanvas) {
				IStateCanvas iStateCanvas=(IStateCanvas) child[i];
				return iStateCanvas;
			}
		}
		return null;
	}
	
	
	
}
