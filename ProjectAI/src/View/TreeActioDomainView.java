package View;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import Action.Action;
import Action.CanvasAction;
import command.ChangeEffCommand;
import command.ChangeNameCommand;
import command.ChangePrecCommand;

public class TreeActioDomainView extends Tree {

	
	MenuItem showActionW;
	MenuItem elimAction;
	MenuItem modifAction;
	ScrolledComposite parent;
	ArrayList<Action> actionList;
	ActionView actionView;
	
	

	
	public TreeActioDomainView(ScrolledComposite parent, int style,ActionView actionView) {
		super(parent, style);
		this.parent=parent;
		actionList=new ArrayList<>();
		this.actionView=actionView;
		createMenu();
		addListener(SWT.MouseDoubleClick, getListenerList());
		
	}

	public void createMenu() {
		final Menu menu = new Menu(this);
		this.setToolTipText("Created Action");
		this.setMenu(menu);

		MenuItem[] items = menu.getItems();
		for (int i = 0; i < items.length; i++) {
			items[i].dispose();
		}
		

		elimAction = new MenuItem(menu, SWT.PUSH);
		elimAction.setText("Eliminate Action");

		modifAction = new MenuItem(menu, SWT.CASCADE);
		modifAction.setText("Set...");

		Menu subMenu = new Menu(menu);
		modifAction.setMenu(subMenu);

		MenuItem modifName = new MenuItem(subMenu, SWT.PUSH);
		modifName.setText("Name");

		MenuItem modifPrec = new MenuItem(subMenu, SWT.PUSH);
		modifPrec.setText("Preconditions");

		MenuItem modifEff = new MenuItem(subMenu, SWT.PUSH);
		modifEff.setText("Effects");



		elimAction.addListener(SWT.Selection, getListenerElimAction());

		modifName.addListener(SWT.Selection, getListenerModifName());

		modifPrec.addListener(SWT.Selection, getListenerModifPrec());

		modifEff.addListener(SWT.Selection, getListenerModifEff());
	}
		

	public Listener getListenerElimAction() {
		Listener l = new Listener() {
			@Override
			public void handleEvent(Event event) {
				TreeItem[] actions = getSelection();
				if (actions.length > 0) {

					TreeItem actionItem = getRoot(actions[0]);
					Action action = findAction(actionItem.getText());
					actionItem.dispose();
					actionList.remove(action);
				}

			}
		};
		
		
		return l;
	}
	
	private Listener getListenerList() {
		Listener l = new Listener() {

			@Override
			public void handleEvent(Event event) {
			
				TreeItem[] actions =getSelection();
				if (actions.length > 0) {
					Composite containerAction=actionView.getContainerAction();
					TreeItem actionItem = getRoot(actions[0]);
					Action action = findAction(actionItem.getText());

					if(!alreadyShow(action, containerAction)) {
						for (Control child : containerAction.getChildren()) {
							child.dispose();
						}
						CanvasAction canvasAction = new CanvasAction(containerAction,
								 SWT.NO_REDRAW_RESIZE, action);
						canvasAction.draw();
						canvasAction.addDNDListener();
						canvasAction.resizeParent();
						actionView.setContainerAction(containerAction);
					}
					

				}

			}
		};
		
		
		return l;
	}
	
	private boolean alreadyShow(Action action,Composite containerAction) {
		boolean result = false;
		if(containerAction.getChildren().length>0) {
			CanvasAction canvasAction=(CanvasAction) containerAction.getChildren()[0];
			if(action.getName().equals(canvasAction.getAction().getName())) {
				result=true;
			}
		}
		return result;
	}
	
	public Listener getListenerModifName() {
		Listener l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				TreeItem[] actions =getSelection();
				if (actions.length > 0) {
					TreeItem actionItem = getRoot(actions[0]);
					Action action = findAction(actionItem.getText());
					ChangeNameCommand cmd = new ChangeNameCommand();
					cmd.execute(actionItem, action);

				}

			}
		};
		
		
		return l;
	}
	
	public Listener getListenerModifPrec() {
		Listener l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				TreeItem[] actions =getSelection();
				if (actions.length > 0) {
					TreeItem actionItem = getRoot(actions[0]);
					Action action = findAction(actionItem.getText());
					ChangePrecCommand cmd = new ChangePrecCommand();
					cmd.execute(action, actionItem);

				}

			}
		};
		
		
		return l;
	}
	public Listener getListenerModifEff() {
		Listener l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				TreeItem[] actions = getSelection();

				if (actions.length > 0) {
					TreeItem actionItem = getRoot(actions[0]);
					Action action = findAction(actionItem.getText());
					ChangeEffCommand cmd = new ChangeEffCommand();
					cmd.execute(action, actionItem);

				}

			}
		};
		
		
		return l;
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
	
	 

	public ArrayList<Action> getActionList() {
		return actionList;
	}

	public void setActionList(ArrayList<Action> actionList) {
		this.actionList = actionList;
	}
	

	public ActionView getActionView() {
		return actionView;
	}



	@Override
	protected void checkSubclass() {
	}
}
