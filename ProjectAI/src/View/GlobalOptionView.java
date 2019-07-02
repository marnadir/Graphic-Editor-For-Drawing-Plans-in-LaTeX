package View;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import Action.Action;
import Action.GlobalValue;
import Action.Node;
import resourceLoader.ResourceLoader;


public class GlobalOptionView extends Composite{

	
	Composite compListdetail;
	Text tWidth,tHeight,tLenEff,tLenPre,tLenEmpty,tLenCond;
	Label confermW,confermH,confermP,confermE,confermEmtpy,lEmpty,confermC;
	Button bntWidth,bntHeigt,bntPrec,bntEff,bntEmty,bntCond;
	Group primGroup,abstrGroup;
	Combo cCornerP,cFormP,cBordP,cColorP;
	Combo cCornerA,cFormA,cBordA,cColorA;
	Label infoP,infoA;
	PlanView planView;
	
	
	DomainView domainView;
	
	public GlobalOptionView(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}
	
	public void setLayout() {
		setLayout(new GridLayout(2, false));
	}
	
	public void createContent() {
			
		Label l=new Label(this, SWT.ALL);
		l.setText("Enable global values");
		GridData gridData = new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false);
		gridData.horizontalSpan = 2;
		l.setLayoutData(gridData);
		
		
		compListdetail=new Composite(this, SWT.ALL);
		compListdetail.setLayout(new GridLayout(5, false));
		gridData = new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false);
		gridData.horizontalSpan = 2;
		compListdetail.setLayoutData(gridData);

		bntWidth=new Button(compListdetail, SWT.CHECK | SWT.BORDER);
		bntWidth.addListener(SWT.Selection, getAddBtnWListener(bntWidth));
		Label lWidth=new Label(compListdetail, SWT.ALL);
		lWidth.setText("Box-width of actions");
		tWidth=new Text(compListdetail, SWT.BORDER );
		tWidth.setEditable(false);
		tWidth.setText("NONE");
		Button btnOkW=new Button(compListdetail, SWT.ALL);
		btnOkW.setText("Ok");
		btnOkW.addListener(SWT.Selection, getOkBtnWListener());
		confermW=new Label(compListdetail, SWT.ALL);
		confermW.setText("Global value not present,check the box.");
		
		
		bntHeigt=new Button(compListdetail, SWT.CHECK | SWT.BORDER);
		bntHeigt.addListener(SWT.Selection, getAddBtnHListener(bntHeigt));
		Label lHeight=new Label(compListdetail, SWT.ALL);
		lHeight.setText("Box-height of actions");
		tHeight=new Text(compListdetail, SWT.BORDER );
		tHeight.setEditable(false);
		tHeight.setText("NONE");
		Button btnOkH=new Button(compListdetail, SWT.ALL);
		btnOkH.setText("Ok");
		btnOkH.addListener(SWT.Selection, getOkBtnHListener());
		confermH=new Label(compListdetail, SWT.ALL);
		confermH.setText("Global value not present,check the box.");
		
		
		bntCond=new Button(compListdetail, SWT.CHECK | SWT.BORDER);
		bntCond.addListener(SWT.Selection, getAddBtnCondListener(bntCond));
		Label lConds=new Label(compListdetail, SWT.ALL);
		lConds.setText("Lenght cond. of state");
		tLenCond=new Text(compListdetail, SWT.BORDER );
		tLenCond.setEditable(false);
		tLenCond.setText("NONE");
		Button btnOkCond=new Button(compListdetail, SWT.ALL);
		btnOkCond.setText("Ok");
		btnOkCond.addListener(SWT.Selection, getOkBtnCondListener());
		confermC=new Label(compListdetail, SWT.ALL);
		confermC.setText("Global value not present,check the box.");
		
		bntPrec=new Button(compListdetail, SWT.CHECK | SWT.BORDER);
		bntPrec.addListener(SWT.Selection, getAddBtnPListener(bntPrec));
		Label lPrec=new Label(compListdetail, SWT.ALL);
		lPrec.setText("Lenght precond. of actions");
		tLenPre=new Text(compListdetail, SWT.BORDER );
		tLenPre.setEditable(false);
		tLenPre.setText("NONE");
		Button btnOkP=new Button(compListdetail, SWT.ALL);
		btnOkP.setText("Ok");
		btnOkP.addListener(SWT.Selection, getOkBtnPListener());
		confermP=new Label(compListdetail, SWT.ALL);
		confermP.setText("Global value not present,check the box.");
		
		
		
		bntEff=new Button(compListdetail, SWT.CHECK | SWT.BORDER);
		bntEff.addListener(SWT.Selection, getAddBtnEListener(bntEff));
		Label lEffs=new Label(compListdetail, SWT.ALL);
		lEffs.setText("Lenght effects of actions");
		tLenEff=new Text(compListdetail, SWT.BORDER );
		tLenEff.setEditable(false);
		tLenEff.setText("NONE");
		Button btnOkE=new Button(compListdetail, SWT.ALL);
		btnOkE.setText("Ok");
		btnOkE.addListener(SWT.Selection, getOkBtnEListener());
		confermE=new Label(compListdetail, SWT.ALL);
		confermE.setText("Global value not present,check the box.");
		
		bntEmty=new Button(compListdetail, SWT.CHECK | SWT.BORDER );
		bntEmty.setSelection(true);
		bntEmty.setEnabled(false);
		lEmpty=new Label(compListdetail, SWT.ALL);
		lEmpty.setText("Lenght of empty tasks");
		tLenEmpty=new Text(compListdetail, SWT.BORDER );
		tLenEmpty.setText(GlobalValue.lengthsOfEmptyTasks);
		Button btnOkEmpty1=new Button(compListdetail, SWT.ALL);
		btnOkEmpty1.setText("Ok");
		btnOkEmpty1.addListener(SWT.Selection, getOkBtnEmptyListener());
		confermEmtpy=new Label(compListdetail, SWT.ALL);
		confermEmtpy.setText("Insert the value and click OK");
		
		
		primGroup=new Group(this, SWT.ALL);
		primGroup.setLayout(new GridLayout(2,true));
		primGroup.setText("Primitive");
		
		Label lForm=new Label(primGroup, SWT.ALL);
		lForm.setText("Border Color: ");
		cFormP=new Combo(primGroup, SWT.DROP_DOWN | SWT.READ_ONLY);
		String[] items = new String[] { "Black", "White" };
		cFormP.setItems(items);
		cFormP.select(0);

		Label lCorner=new Label(primGroup, SWT.ALL);
		lCorner.setText("Corner: ");
		cCornerP=new Combo(primGroup, SWT.DROP_DOWN | SWT.READ_ONLY);
		items = new String[] { "Round", "Square" };
		cCornerP.setItems(items);
		cCornerP.select(1);
		
		Label  lBord=new Label(primGroup, SWT.ALL);
		lBord.setText("Fat Border: ");
		cBordP=new Combo(primGroup, SWT.DROP_DOWN | SWT.READ_ONLY);
		items = new String[] { "Fat", "Normal" };
		cBordP.setItems(items);
		cBordP.select(0);
		
		Label  lCOlor=new Label(primGroup, SWT.ALL);
		lCOlor.setText("Color: ");
		cColorP=new Combo(primGroup, SWT.DROP_DOWN | SWT.READ_ONLY);
		items = new String[] { "None", "Cyan","Yellow" };
		cColorP.setItems(items);
		cColorP.select(0);
		
		

		Button btnUpdateP=new Button(primGroup, SWT.PUSH);
		Image icon = new Image(getDisplay(),ResourceLoader.load("img/refresh.png") );
		btnUpdateP.setImage(icon);
		infoP=new Label(primGroup, SWT.ALL);
		btnUpdateP.addListener(SWT.Selection, getOkPrimListener());
		
		
		abstrGroup=new Group(this, SWT.ALL);
		abstrGroup.setLayout(new GridLayout(2, true));
		abstrGroup.setText("Abstract");
		
		lForm=new Label(abstrGroup, SWT.ALL);
		lForm.setText("Border color: ");
		cFormA=new Combo(abstrGroup, SWT.DROP_DOWN | SWT.READ_ONLY);
		items = new String[] { "Black", "White" };
		cFormA.setItems(items);
		cFormA.select(0);
		

		lCorner=new Label(abstrGroup, SWT.ALL);
		lCorner.setText("Corner: ");
		cCornerA=new Combo(abstrGroup, SWT.DROP_DOWN | SWT.READ_ONLY);
		items = new String[] { "Round", "Square" };
		cCornerA.setItems(items);
		cCornerA.select(0);
		
		lBord=new Label(abstrGroup, SWT.ALL);
		lBord.setText("Fat Border: ");
		cBordA=new Combo(abstrGroup, SWT.DROP_DOWN | SWT.READ_ONLY);
		items = new String[] { "Fat", "Normal" };
		cBordA.setItems(items);
		cBordA.select(1);
		
		Label  lCOlorA=new Label(abstrGroup, SWT.ALL);
		lCOlorA.setText("Color: ");
		cColorA=new Combo(abstrGroup, SWT.DROP_DOWN | SWT.READ_ONLY);
		items = new String[] { "None", "Cyan","Yellow" };
		cColorA.setItems(items);
		cColorA.select(0);
		
		
		Button btnUpdateA=new Button(abstrGroup, SWT.PUSH);
		btnUpdateA.setImage(icon);
		infoA=new Label(abstrGroup, SWT.ALL);
		btnUpdateA.addListener(SWT.Selection, getAbstrListener());
		pack();
		tLenEmpty.setSize(tLenEff.getSize().x, tLenEff.getSize().y);
		
		
	}
	
	
	public void update() {
		if(GlobalValue.isWidthOfAction) {
			bntWidth.setSelection(true);
			tWidth.setText(GlobalValue.widthOfAction);			
			tWidth.setEditable(true);
			confermW.setText("Update successfully");

		}
		
		if(GlobalValue.isHeightOfAction) {
			bntHeigt.setSelection(true);
			tHeight.setText(GlobalValue.heightOfAction);	
			tHeight.setEditable(true);
			confermH.setText("Update successfully");
			

		}
		
		if(GlobalValue.isLengthsOfPrecs) {
			bntPrec.setSelection(true);
			tLenPre.setText(GlobalValue.lengthsOfPrecs);			
			tLenPre.setEditable(true);
			confermP.setText("Update successfully");

		}
		
		if(GlobalValue.isLengthsOfEffs) {
			bntEff.setSelection(true);
			tLenEff.setText(GlobalValue.lengthsOfEffs);
			tLenEff.setEditable(true);
			confermE.setText("Update successfully");

		}
		
		if(GlobalValue.isLengthsOfConds) {
			bntCond.setSelection(true);
			tLenCond.setText(GlobalValue.lengthsOfConds);
			tLenCond.setEditable(true);
			confermC.setText("Update successfully");

		}
		
		
		
		
		
		
	
		tLenEmpty.setText(GlobalValue.lengthsOfEmptyTasks);
		tLenEmpty.setEditable(true);
	
		
		
	}
	
	
	
	private Listener getOkPrimListener() {
		Listener l;

		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				infoP.setText("Successfully update");
				//infoP.pack();
				if(cFormP.getText().equals("Black")) {
					GlobalValue.formIsBlackPr=true;
				}else {
					GlobalValue.formIsBlackPr=false;
				}
				
				if(cBordP.getText().equals("Fat")) {
					GlobalValue.borderIsFatPr=true;

				}else {
					GlobalValue.borderIsFatPr=false;

				}
				if(cCornerP.getText().equals("Square")) {
					GlobalValue.cornerIsSquarePr=true;
				}else {
					GlobalValue.cornerIsSquarePr=false;
				}	
				
				
				GlobalValue.colorP=cColorP.getText();
				
				
				domainView.getContentCanvas().redraw();
				
				for(Node node:planView.getPlan().getActionInPlan()) {
					node.getAction().setDefaultAction(true);
				}
				
				for(Action canvas:domainView.getTreeAction().getActionList()) {
					canvas.setDefaultAction(true);
				}
				
			}
		};

		return l;
	}
	
	private Listener getAbstrListener() {
		Listener l;

		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				infoA.setText("Successfully update");
				//infoA.pack();
				if(cFormA.getText().equals("Black")) {
					GlobalValue.formIsBlackAbst=true;
				}else {
					GlobalValue.formIsBlackAbst=false;
				}
				
				if(cBordA.getText().equals("Fat")) {
					GlobalValue.borderIsFatAbst=true;

				}else {
					GlobalValue.borderIsFatAbst=false;

				}
				if(cCornerA.getText().equals("Square")) {
					GlobalValue.cornerIsSquareAbst=true;
				}else {
					GlobalValue.cornerIsSquareAbst=false;
				}

				GlobalValue.colorAbst=cColorA.getText();
				
			}
		};

		return l;
	}
	
	//insert  global width (Button+Label)
	private Listener getAddBtnWListener(Button b1) {
		Listener l;

		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (b1.getSelection()) {
					tWidth.setEditable(true);
					confermW.setText("Insert the value and click OK");

					
					//insert the new command in the latex code
				} else {
					tWidth.setEditable(false);
					tWidth.setText("NONE");
					GlobalValue.isWidthOfAction=false;
					confermW.setText("Global value not present,check the box.");
					for(int i=0;i<planView.getPlan().getActionInPlan().size();i++) {
						planView.getPlan().getActionInPlan().get(i).getAction().setGlobalWidth(false);
					}
					
					for(int i=0;i<domainView.getTreeAction().getActionList().size();i++) {
						domainView.getTreeAction().getActionList().get(i).setGlobalWidth(false);
					}
					domainView.getContentCanvas().redraw();

				}
				confermW.pack();
				tWidth.pack();
				
			}
		};

		return l;
	}

	//Global width of action
	private Listener getOkBtnWListener() {
		Listener l;

		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				//CHECK IF TEXT IS NUMERIC
				if(tWidth.getEditable()) {
					GlobalValue.isWidthOfAction=true;
					GlobalValue.widthOfAction=tWidth.getText();
					confermW.setText("Update successfully");
					for(int i=0;i<planView.getPlan().getActionInPlan().size();i++) {
						planView.getPlan().getActionInPlan().get(i).getAction().setGlobalWidth(true);
					}
					
					for(int i=0;i<domainView.getTreeAction().getActionList().size();i++) {
						domainView.getTreeAction().getActionList().get(i).setGlobalWidth(true);
					}
					domainView.getContentCanvas().redraw();

				}
				
			}
		};

		return l;
	}
	
	
	//insert  global height (Button+Label)
	private Listener getAddBtnHListener(Button b1) {
		Listener l;

		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (b1.getSelection()) {
					tHeight.setEditable(true);
					confermH.setText("Insert the value and click OK");

					
					//insert the new command in the latex code
				} else {
					tHeight.setEditable(false);
					tHeight.setText("NONE");
					GlobalValue.isHeightOfAction=false;
					confermH.setText("Global value not present,check the box.");
					for(int i=0;i<planView.getPlan().getActionInPlan().size();i++) {
						planView.getPlan().getActionInPlan().get(i).getAction().setGlobalHeight(false);
					}
					for(int i=0;i<domainView.getTreeAction().getActionList().size();i++) {
						domainView.getTreeAction().getActionList().get(i).setGlobalHeight(false);
					}
					domainView.getContentCanvas().redraw();


				}
				confermH.pack();
				tHeight.pack();
			}
		};

		return l;
	}

	//Global height of action
	private Listener getOkBtnHListener() {
		Listener l;

		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				//CHECK IF TEXT IS NUMERIC
				if(tHeight.getEditable()) {
					GlobalValue.isHeightOfAction=true;
					GlobalValue.heightOfAction=tHeight.getText();
					confermH.setText("Update successfully");
					for(int i=0;i<planView.getPlan().getActionInPlan().size();i++) {
						planView.getPlan().getActionInPlan().get(i).getAction().setGlobalHeight(true);
					}
					for(int i=0;i<domainView.getTreeAction().getActionList().size();i++) {
						domainView.getTreeAction().getActionList().get(i).setGlobalHeight(true);
					}
					
					domainView.getContentCanvas().redraw();

					
				}
				
			}
		};

		return l;
	}
	
	//insert  global Prec (Button+Label)
	private Listener getAddBtnPListener(Button b1) {
		Listener l;

		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (b1.getSelection()) {
					tLenPre.setEditable(true);
					confermP.setText("Insert the value and click OK");

					
					//insert the new command in the latex code
				} else {
					tLenPre.setEditable(false);
					tLenPre.setText("NONE");
					GlobalValue.isLengthsOfPrecs=false;
					confermP.setText("Global value not present,check the box.");
					for(int i=0;i<planView.getPlan().getActionInPlan().size();i++) {
						planView.getPlan().getActionInPlan().get(i).getAction().setGlobalPrec(false);
					}
					for(int i=0;i<domainView.getTreeAction().getActionList().size();i++) {
						domainView.getTreeAction().getActionList().get(i).setGlobalPrec(false);
					}
					
					domainView.getContentCanvas().redraw();
					
				}
				confermP.pack();

				tLenPre.pack();
			}
		};

		return l;
	}

	//Global Prec-Lenght of action
	private Listener getOkBtnPListener() {
		Listener l;

		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				//CHECK IF TEXT IS NUMERIC
				if(tLenPre.getEditable()) {
					GlobalValue.isLengthsOfPrecs=true;
					GlobalValue.lengthsOfPrecs=tLenPre.getText();
					confermP.setText("Update successfully");
					for(int i=0;i<planView.getPlan().getActionInPlan().size();i++) {
						planView.getPlan().getActionInPlan().get(i).getAction().setGlobalPrec(true);
					}
					for(int i=0;i<domainView.getTreeAction().getActionList().size();i++) {
						domainView.getTreeAction().getActionList().get(i).setGlobalPrec(true);
					}
					
					domainView.getContentCanvas().redraw();

				}
				
			}
		};

		return l;
	}
	
	
	//insert  global Eff (Button+Label)
	private Listener getAddBtnEListener(Button b1) {
		Listener l;

		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (b1.getSelection()) {
					tLenEff.setEditable(true);
					confermE.setText("Insert the value and click OK");

					
					//insert the new command in the latex code
				} else {
					tLenEff.setEditable(false);
					tLenEff.setText("NONE");
					GlobalValue.isLengthsOfEffs=false;
					confermE.setText("Global value not present,check the box.");
					for(int i=0;i<planView.getPlan().getActionInPlan().size();i++) {
						planView.getPlan().getActionInPlan().get(i).getAction().setGlobalEff(false);
					}
					for(int i=0;i<domainView.getTreeAction().getActionList().size();i++) {
						domainView.getTreeAction().getActionList().get(i).setGlobalEff(false);
					}
					domainView.getContentCanvas().redraw();

				}
				confermE.pack();
				tLenEff.pack();
			}
		};

		return l;
	}

	//Global Eff-Lenght of action
	private Listener getOkBtnEListener() {
		Listener l;

		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				//CHECK IF TEXT IS NUMERIC
				if(tLenEff.getEditable()) {
					GlobalValue.isLengthsOfEffs=true;
					GlobalValue.lengthsOfEffs=tLenEff.getText();
					confermE.setText("Update successfully");
					for(int i=0;i<planView.getPlan().getActionInPlan().size();i++) {
						planView.getPlan().getActionInPlan().get(i).getAction().setGlobalEff(true);
					}
					for(int i=0;i<domainView.getTreeAction().getActionList().size();i++) {
						domainView.getTreeAction().getActionList().get(i).setGlobalEff(true);
					}
					domainView.getContentCanvas().redraw();

				}
				
			}
		};

		return l;
	}
	
		
	//insert  global Conds (Button+Label)
	private Listener getAddBtnCondListener(Button b1) {
		Listener l;

		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (b1.getSelection()) {
					tLenCond.setEditable(true);
					confermC.setText("Insert the value and click OK");

					
					//insert the new command in the latex code
				} else {
					tLenCond.setEditable(false);
					tLenCond.setText("NONE");
					GlobalValue.isLengthsOfConds=false;
					confermC.setText("Global value not present,check the box.");
					if(planView.getPlan().getInitialStateCanvas() != null) {
						planView.getPlan().getInitialStateCanvas().getState().setGlobalCond(false);
					}
					if(planView.getPlan().getGoalStateCanvas()!= null) {
						planView.getPlan().getGoalStateCanvas().getState().setGlobalCond(false);
					}
					
					if(domainView.getInitStateView().getContainerState()!=null) {
						domainView.getInitStateView().getContainerState().getCanvas().getState().setGlobalCond(false);
					}
				
					if(domainView.getGoalStateCanvas().getContainerState()!=null) {
						domainView.getGoalStateCanvas().getContainerState().getCanvas().getState().setGlobalCond(false);
					}
					
				
					
					domainView.getContentCanvas().redraw();


				}
				confermC.pack();
				tLenCond.pack();
			}
		};

		return l;
	}

	// Global Cond-Lenght of state
	private Listener getOkBtnCondListener() {
		Listener l;

		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				// CHECK IF TEXT IS NUMERIC
				if (tLenCond.getEditable()) {
					GlobalValue.isLengthsOfConds = true;
					GlobalValue.lengthsOfConds = tLenCond.getText();
					confermC.setText("Update successfully");
					if(planView.getPlan().getInitialStateCanvas() != null) {
						planView.getPlan().getInitialStateCanvas().getState().setGlobalCond(true);
					}
					if(planView.getPlan().getGoalStateCanvas()!= null) {
						planView.getPlan().getGoalStateCanvas().getState().setGlobalCond(true);
					}
					
					if(domainView.getInitStateView().getContainerState()!=null) {
						domainView.getInitStateView().getContainerState().getCanvas().getState().setGlobalCond(true);
					}
				
					if(domainView.getGoalStateCanvas().getContainerState()!=null) {
						domainView.getGoalStateCanvas().getContainerState().getCanvas().getState().setGlobalCond(true);
					}
					
				
					
					domainView.getContentCanvas().redraw();

				}
			}
		};

		return l;
	}
	
	
	// Global Empty-Lenght of state
	private Listener getOkBtnEmptyListener() {
		Listener l;

		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				// CHECK IF TEXT IS NUMERIC

				GlobalValue.lengthsOfEmptyTasks = tLenEmpty.getText();
				confermEmtpy.setText("Update successfully");

				
				/*
				 * Plan View
				 */
				if (planView.getPlan().getInitialStateCanvas() != null) {
					planView.getPlan().getInitialStateCanvas().getState().setGlobalEmpty(false);
				}
				if (planView.getPlan().getGoalStateCanvas() != null) {
					planView.getPlan().getGoalStateCanvas().getState().setGlobalEmpty(false);
				}
				
				for(int i=0;i<planView.getPlan().getActionInPlan().size();i++) {
					planView.getPlan().getActionInPlan().get(i).getAction().setGlobalEmptyPrec(false);
					planView.getPlan().getActionInPlan().get(i).getAction().setGlobalEmptyEff(false);

				}
				
				//Domain view
				if(domainView.getInitialStateCanvas()!=null) {
					domainView.getInitStateView().getContainerState().getCanvas().getState().setGlobalEmpty(false);
				}
			
				if(domainView.getGoalStateCanvas()!=null) {
					domainView.getGoalStateCanvas().getContainerState().getCanvas().getState().setGlobalEmpty(false);
				}
			
				for(int i=0;i<domainView.getTreeAction().getActionList().size();i++) {
					domainView.getTreeAction().getActionList().get(i).setGlobalEmptyPrec(false);
					domainView.getTreeAction().getActionList().get(i).setGlobalEmptyEff(false);

				}
				
				domainView.getContentCanvas().redraw();

			}
		};

		return l;
	}

	public void setDomainView(DomainView domainView) {
		this.domainView = domainView;
	}

	public void setPlanView(PlanView planView) {
		this.planView = planView;
	}
	
	
	
}
