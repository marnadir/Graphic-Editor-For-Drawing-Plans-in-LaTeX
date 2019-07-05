package View;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import Action.Action;
import Action.ActionDomainCanvas;
import Action.Node;
import PlanPart.LinkCanvas;
import PlanPart.OrderConstrain;
import PlanPart.Oval;
import PlanPart.PlanContent;
import command.ChangeActionDialogCommand;
/**
 * View which represents the tree of the action.
 * @author nadir
 * */
public class TreeActioDomainView extends Tree {

	
	MenuItem showActionW;
	MenuItem elimAction;
	MenuItem modifAction;
	Composite parent;
	ArrayList<Action> actionList;
	ActionView actionView;
	
	

	
	public TreeActioDomainView(Composite parent, int style) {
		super(parent, style);
		this.parent=parent;
		actionList=new ArrayList<>();
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
		modifAction.setText("Modify Action");
		elimAction.addListener(SWT.Selection, getListenerElimAction());
		modifAction.addListener(SWT.Selection, getListenerModifAction());
		
	}
		

	private Listener getListenerElimAction() {
		Listener l = new Listener() {
			@Override
			public void handleEvent(Event event) {
				TreeItem[] actions = getSelection();
				if (actions.length > 0) {
					TreeItem actionItem = getRoot(actions[0]);
					Action action = findAction(actionItem.getText());
					actionItem.dispose();
					actionList.remove(action);
					if(actionView.getContainerAction().getChildren().length>0) {
						actionView.getContainerAction().getChildren()[0].dispose();
					}	
					eliminateItemsInPlan(action);
				}

			}
		};
		
		
		return l;
	}
	
	private void eliminateItemsInPlan(Action a) {
		PrincipalView principalView=actionView.getDomainView().getPrincipalView();
		ArrayList<PlanContent> plans=principalView.getPlanView().getAllPlan();
		for(PlanContent plan:plans) {
			ArrayList<Node> nodes=plan.getActionInPlan();
			ArrayList<Node> nodesToDelete=new ArrayList<>();
			for(Node node:nodes) {
				if(node.getAction().getName().equals(a.getName())) {
					nodesToDelete.add(node);
					clearNodeInPLan(node);
				}
			}
			nodes.removeAll(nodesToDelete);
			
			
			
			ArrayList<LinkCanvas> links=plan.getLink();
			ArrayList<LinkCanvas> linksToDelete=new ArrayList<>();
			for(LinkCanvas link:links) {
				if(link.getOval1().getNode().getAction().getName().equals(a.getName()) || 
						link.getOval2().getNode().getAction().getName().equals(a.getName())) {
					
					link.setOval1(null);
					link.setOval2(null);
					linksToDelete.add(link);
				}
			}
			links.removeAll(linksToDelete);
			
			ArrayList<OrderConstrain> orderConstrains=plan.getOrds();
			ArrayList<OrderConstrain> orderConstrainsToDelete=new ArrayList<>();
			for(OrderConstrain orderConstrain:orderConstrains) {
				if(orderConstrain.getCond1().getAction().getName().equals(a.getName()) || 
						orderConstrain.getCond2().getAction().getName().equals(a.getName())) {
					
					orderConstrainsToDelete.add(orderConstrain);
					orderConstrain.getParent().dispose();
				}
			}

			
		}
		
	}
	
	
	
	
	private void clearNodeInPLan(Node canvas) {
		if(canvas instanceof Node) {
			if(canvas.getParent().getParent() instanceof PlanContent) {
				PlanContent contentAction=(PlanContent)canvas.getParent().getParent();
				canvas.getParent().setVisible(false);
				for (Oval oval : canvas.getOvalList()) {
					contentAction.getOvalCounter().getListOval().remove(oval);
					oval.dispose();

				}
				canvas.setOvalList(new ArrayList<>());
				canvas.clearDisplay();
				return;
			}
		}
		
		canvas.clearDisplay();
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

//					if(!alreadyShow(action, containerAction)) {
						containerAction.setVisible(true);
						for (Control child : containerAction.getChildren()) {
							child.dispose();
						}
						ActionDomainCanvas canvasAction = new ActionDomainCanvas(containerAction,
								SWT.NONE, action);
						canvasAction.draw();
						canvasAction.addDNDListener();
						canvasAction.resizeParent();
						actionView.setContainerAction(containerAction);
						actionView.getContainerAction().pack();
//					}
					

				}

			}
		};
		
		
		return l;
	}
	/*
	private boolean alreadyShow(Action action,Composite containerAction) {
		boolean result = false;
		if(containerAction.getChildren().length>0) {
			ActionDomainCanvas canvasAction=(ActionDomainCanvas) containerAction.getChildren()[0];
			if(action.getName().equals(canvasAction.getAction().getName())) {
				result=true;
			}
		}
		return result;
	}
	*/
	public Listener getListenerModifAction() {
		Listener l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				TreeItem[] actions = getSelection();
				if (actions.length > 0) {
					TreeItem actionItem = getRoot(actions[0]);
					Action action = findAction(actionItem.getText());

					ChangeActionDialogCommand cmd = new ChangeActionDialogCommand();
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



	public void setActionView(ActionView actionView) {
		this.actionView = actionView;
	}

	@Override
	protected void checkSubclass() {
	}
}
