package View;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TreeItem;

import Action.Action;
import Action.CreateActionComposite;
import container.ContainerGoalState;
import container.ContainerInitialState;
import so_goalState.CreateStateContainer;
import so_goalState.IStateCanvas;
/**
 * View which represents contains the domain components, where is subdivided in 3 subviews.
 * @see InitialStateView
 * @see GoalStateView
 * @see ActionView
 * @author nadir
 * */
public class DomainView {

	Group domainGroup;
//	Group stateGroup;
	SashForm sashForm;
	Composite outer;
	Composite inside;
	Composite contentCanvas;
	InitialStateView initStateView;
	GoalStateView goalStateView;
	Group part1;
	Group part2;
	Composite contentGoalState;
	ActionView actionView;
	TreeActioDomainView treeAction;
	GlobalOptionView globalOptionView;
	PrincipalView principalView;

	



	public DomainView(SashForm sashForm) {
		this.sashForm = sashForm;
		setLayout();

	}

	public void setLayout() {

		
		outer = new Composite(sashForm, SWT.ALL);
		outer.setLayout(new FillLayout());

		this.domainGroup = new Group(outer, SWT.BOLD);
		Font boldFont = new Font(this.domainGroup.getDisplay(), new FontData("Arial", 12, SWT.BOLD));
		this.domainGroup.setText("Domain Definition");
		this.domainGroup.setFont(boldFont);

		domainGroup.setLayout(new GridLayout(1, false));

		inside = new Composite(domainGroup, SWT.ALL);
		inside.setLayout(new GridLayout(1, true));
		inside.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

	}

	public void createContent() {
		
		contentCanvas = new Composite(inside, SWT.NONE);
		FillLayout fillLayout = new FillLayout();
		fillLayout.type = SWT.VERTICAL;
		contentCanvas.setLayout(fillLayout);
		
		contentCanvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		
		
		part1 = new Group(contentCanvas, SWT.NONE);
		part1.setLayout(new GridLayout(1, false));
		part1.setText("Initial/Goal state");



		CreateStateContainer newStateComp=new CreateStateContainer(part1, SWT.ALL,"Initial/Goal");
		newStateComp.createContent();
		
		Composite compViewState=new Composite(part1, SWT.ALL);	
		compViewState.setLayout(new GridLayout(2, false));
		compViewState.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		
		initStateView=new InitialStateView(compViewState, SWT.NONE);
		initStateView.createContent();
		initStateView.setText("Initial State");
		newStateComp.setContainerInitialState((ContainerInitialState) (initStateView.getContainerState()));
		initStateView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		initStateView.setDomainView(this);
		



		goalStateView=new GoalStateView(compViewState, SWT.NONE);
		goalStateView.createContent();
		goalStateView.setText("Goal State");
		newStateComp.setContainerGoalState((ContainerGoalState) goalStateView.getContainerState());
		goalStateView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		goalStateView.setDomainView(this);

		
		
		part2 = new Group(contentCanvas, SWT.NONE);
		part2.setLayout(new GridLayout(1, false));
		part2.setText("Actions");

		
		CreateActionComposite newActComp=new CreateActionComposite(part2, SWT.ALL, "Action");
		newActComp.createContent();
		
		Composite compViewAction=new Composite(part2, SWT.ALL);	
		compViewAction.setLayout(new GridLayout(3, true));
		compViewAction.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		
		
		
		treeAction=new TreeActioDomainView(compViewAction, SWT.BORDER);
		treeAction.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		actionView = new ActionView(compViewAction, SWT.BORDER);
		actionView.creareContent();
		actionView.setDomainView(this);
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.horizontalSpan = 2;
		actionView.setLayoutData(gridData);
		
		treeAction.setActionView(actionView);

		newActComp.setTreeAction(treeAction);

	
			
	}


	public void restoreActionList(ArrayList<Action> actions) {
		
		if(actions!=null) {
			treeAction.setActionList(actions);
			treeAction.removeAll();
			for(Action action:treeAction.getActionList()) {
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
	
	}
	
	public IStateCanvas getInitialStateCanvas() {
		Control[] child=initStateView.getContainerState().getChildren();
		for(int i=0;i<child.length;i++) {
			if(child[i] instanceof IStateCanvas) {
				IStateCanvas iStateCanvas=(IStateCanvas) child[i];
				return iStateCanvas;
			}
		}
		return null;
	}
	public IStateCanvas getGoalStateCanvas() {
		Control[] child=goalStateView.getContainerState().getChildren();
		for(int i=0;i<child.length;i++) {
			if(child[i] instanceof IStateCanvas) {
				IStateCanvas iStateCanvas=(IStateCanvas) child[i];
				return iStateCanvas;
			}
		}
		return null;
	}

	public TreeActioDomainView getTreeAction() {
		return treeAction;
	}

	public InitialStateView getInitStateView() {
		return initStateView;
	}

	public GoalStateView getGoalStateView() {
		return goalStateView;
	}

	public Composite getContentCanvas() {
		return contentCanvas;
	}

	public GlobalOptionView getGlobalOptionView() {
		return globalOptionView;
	}

	public void setGlobalOptionView(GlobalOptionView globalOptionView) {
		this.globalOptionView = globalOptionView;
	}

	public void setPrincipalView(PrincipalView principalView) {
		this.principalView = principalView;
	}

	public PrincipalView getPrincipalView() {
		return principalView;
	}



	
	
	
}
