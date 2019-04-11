package View;

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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TreeItem;

import Action.Action;
import State.IStateCanvas;
import resourceLoader.ResourceLoader;

public class DomainView {

	Group domainGroup;
	Group stateGroup;
	SashForm sashForm;
	Composite outer;
	Composite inside;
	Composite contentCanvas;
	InitialStateView initStateView;
	GoalStateView goalStateView;
	Composite part1;
	Group part2;
	Composite contentGoalState;
	ActionView actionView;
	TreeActioDomainView treeAction;
	GlobalOptionView globalOptionView;



	public DomainView(SashForm sashForm) {
		this.sashForm = sashForm;
		setLayout();

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

		createStateGroup subOption=new createStateGroup(inside, SWT.ALL,"Creation");
		subOption.createContent();

		stateGroup = new Group(inside, SWT.NONE);
		stateGroup.setText("Items for the plan");
		stateGroup.setLayout(new GridLayout(1, true));
		GridData firstData = new GridData(SWT.FILL, SWT.FILL, true, true);
		firstData.heightHint = 750;
		stateGroup.setLayoutData(firstData);

		contentCanvas = new Composite(stateGroup, SWT.ALL);
		FillLayout fillLayout = new FillLayout();
		fillLayout.type = SWT.VERTICAL;
		contentCanvas.setLayout(fillLayout);
		
		
		Composite test=new Composite(contentCanvas, SWT.BORDER);
		fillLayout = new FillLayout();
		fillLayout.type = SWT.VERTICAL;
		test.setLayout(fillLayout);
		
		
		Composite test2=new Composite(test, SWT.BORDER);
//		test2.setLayout(new FillLayout());
		Label initialState = new Label(test2, SWT.ALL);
		initialState.setText("Initial State: ");
		initialState.pack();
		
		part1 = new Composite(test, SWT.ALL);
		fillLayout = new FillLayout();
		fillLayout.type = SWT.HORIZONTAL;
		part1.setLayout(fillLayout);
		

		initStateView=new InitialStateView(part1, SWT.BORDER);
		initStateView.createContent();
		//initStateView.setFont(new Font(initStateView.getDisplay(), "Consolas", 10, SWT.CENTER));

		initStateView.setText("Initial State");
		subOption.setContainerInitialState(initStateView.getContainerInitState());


		goalStateView=new GoalStateView(part1, SWT.BORDER);
		goalStateView.createContent();
		goalStateView.setText("Goal State");
		subOption.setContainerGoalState(goalStateView.getContainerGoalState());

		part2 = new Group(contentCanvas, SWT.BORDER);
		part2.setLayout(new GridLayout(3, true));
		part2.setText("Actions");

		final ScrolledComposite scrolledComposite = new ScrolledComposite(part2, SWT.V_SCROLL);
		scrolledComposite.setLayout(new GridLayout());
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		actionView = new ActionView(part2, SWT.BORDER);
		actionView.creareContent();
		
		
		treeAction=new TreeActioDomainView(scrolledComposite, SWT.BORDER, actionView);
		subOption.setTreeAction(treeAction);
		
		scrolledComposite.setContent(treeAction);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setAlwaysShowScrollBars(true);
		scrolledComposite.setMinSize(treeAction.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		
		test.setSize(contentCanvas.getSize().x, contentCanvas.getSize().y/2);
		test.pack();
//		firstScroll.setContent(contentCanvas);
//		firstScroll.setExpandHorizontal(true);
//		firstScroll.setExpandVertical(true);
//		firstScroll.setMinSize(contentCanvas.computeSize(SWT.DEFAULT, SWT.DEFAULT));

			
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
		Control[] child=initStateView.getContainerInitState().getChildren();
		for(int i=0;i<child.length;i++) {
			if(child[i] instanceof IStateCanvas) {
				IStateCanvas iStateCanvas=(IStateCanvas) child[i];
				return iStateCanvas;
			}
		}
		return null;
	}
	public IStateCanvas getGoalStateCanvas() {
		Control[] child=goalStateView.getContainerGoalState().getChildren();
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



	
	
	
}
