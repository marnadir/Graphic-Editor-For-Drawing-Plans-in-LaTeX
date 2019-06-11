package Dialog;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;

import Action.Action;
import Action.ActionDomainCanvas;
import Action.GlobalValue;
import View.ActionView;
import View.TreeActioDomainView;
import resourceLoader.ResourceLoader;

public class CreateActionDialog extends IDialog {


	Composite compCanvas;
	List listPrec;
	List listEff;
	Text newPrec;
	Text newEff;
	Button buttonNegPrec;
	Button buttonNegEff;
	Text actionName;
	Button btnPrim;
	Button btnAbst;
	

	Combo combOption;
	Combo comboAction;

	ArrayList<String> prec;
	ArrayList<String> effect;
	Action action;
	TreeActioDomainView treeActions;
	ArrayList<Action> actions;
	protected Text newPrecEd;
	protected Text newEffEd;
	Button btnEditPrec;

	
	
	

	public CreateActionDialog(TreeActioDomainView list,ArrayList<Action> actions) {
		super(list.getShell(),SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER | SWT.RESIZE);
		this.treeActions=list;
		this.actions=actions;
		prec = new ArrayList<>();
		effect = new ArrayList<>();

		// TODO Auto-generated constructor stub
	}

	@Override
	public void createDialog() {
		// TODO Auto-generated method stub
		super.createDialog();
	}

	@Override
	public void createContent() {
		label.setText("Create a new action");

		mainComposite.setLayout(new GridLayout(2, false));
		
		btnPrim=new  Button(mainComposite, SWT.CHECK);
		btnPrim.setText("Primitive");
		
		btnAbst=new  Button(mainComposite, SWT.CHECK);
		btnAbst.setText("Abstract");
		
		btnPrim.setSelection(true);
		
		btnPrim.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				if(btnPrim.getSelection()) {
					btnAbst.setSelection(false);
				}else {
					btnAbst.setSelection(true);
				}
				
			}
		});
		
		
		btnAbst.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				if(btnAbst.getSelection()) {
					btnPrim.setSelection(false);
				}else {
					btnPrim.setSelection(true);
				}
				
			}
		});
		

		Label lNameAct = new Label(mainComposite, SWT.ALL);
		lNameAct.setText("Name of the action: ");

		GridData gridData = new GridData(GridData.FILL, GridData.FILL, false, false);
		// gridData.horizontalSpan = 3;
		lNameAct.setLayoutData(gridData);

		actionName = new Text(mainComposite, SWT.SINGLE | SWT.BORDER | SWT.RESIZE);
		gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		actionName.setLayoutData(gridData);
		actionName.addListener(SWT.FocusIn, new Listener() {
			public void handleEvent(Event e) {
				setDefaultButton (okButton);	
			}
		});

		Group groupPrec = new Group(mainComposite, SWT.ALL | SWT.RESIZE);
		groupPrec.setText("Precondition");
		groupPrec.setLayout(new GridLayout(3, false));
		groupPrec.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));

		newPrec = new Text(groupPrec, SWT.BORDER | SWT.RESIZE);
		GridData gd1 = new GridData ();
		gd1.widthHint=100;
		newPrec.setLayoutData(gd1);
		newPrec.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		buttonNegPrec = new Button(groupPrec, SWT.CHECK);
		buttonNegPrec.setText("neg");

		Button addPrec = new Button(groupPrec, SWT.PUSH);
		Image icon = new Image(mainComposite.getDisplay(),ResourceLoader.load("img/add.png"));
		addPrec.setImage(icon);
		addPrec.addListener(SWT.Selection, addPrecListener());

		newPrec.addListener(SWT.FocusIn, new Listener() {
			public void handleEvent(Event e) {
				setDefaultButton (addPrec);	
			}
		});
		
		listPrec = new List(groupPrec, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL |SWT.RESIZE);
		Button btnDeletePrec = new Button(groupPrec, SWT.PUSH);
		icon = new Image(groupPrec.getDisplay(), ResourceLoader.load("img/deleteCond.png"));
		btnDeletePrec.setImage(icon);
		btnDeletePrec.addListener(SWT.Selection, getDelPrecListener());
		
		
		Composite compOrd=new Composite(groupPrec, SWT.ALL);
		compOrd.setLayout(new RowLayout(SWT.VERTICAL));
		
		Button btnUp=new Button(compOrd, SWT.PUSH);
		icon = new Image(mainComposite.getDisplay(), ResourceLoader.load("img/up.png"));
		btnUp.setImage(icon);
		btnUp.setToolTipText("Up");
		implementBtnUpDown(btnUp,listPrec,prec);
		
		Button btnDown=new Button(compOrd, SWT.PUSH);
		icon = new Image(mainComposite.getDisplay(), ResourceLoader.load("img/down.png"));
		btnDown.setImage(icon);
		btnDown.setToolTipText("Down");
		implementBtnUpDown(btnDown,listPrec,prec);
		
		
		newPrecEd=new Text(groupPrec,  SWT.SINGLE | SWT.BORDER | SWT.RESIZE);
		newPrecEd.setLayoutData(gd1);
		newPrecEd.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		
		Button btnNegPrec=new Button(groupPrec, SWT.PUSH);
		btnNegPrec.setText("neg");
		btnNegPrec.addListener(SWT.Selection, addBtnNegListener1());
		
		
		btnEditPrec=new Button(groupPrec, SWT.PUSH);
		icon = new Image(mainComposite.getDisplay(),ResourceLoader.load( "img/edit.png"));
		btnEditPrec.setImage(icon);
		btnEditPrec.addListener(SWT.Selection, addBtnEditListener(listPrec,prec,newPrecEd));

		listPrec.addListener(SWT.Selection,addListListener(listPrec,newPrecEd));

		
		//Effects part
		
		Group groupEff = new Group(mainComposite, SWT.ALL | SWT.RESIZE);
		groupEff.setText("Effect");
		groupEff.setLayout(new GridLayout(3, false));
		groupEff.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));



		newEff = new Text(groupEff, SWT.BORDER | SWT.RESIZE);
		newEff.setLayoutData(gd1);
		buttonNegEff = new Button(groupEff, SWT.CHECK);
		buttonNegEff.setText("neg");
		newEff.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));


		Button addEff = new Button(groupEff, SWT.PUSH);
		icon = new Image(mainComposite.getDisplay(), ResourceLoader.load("img/add.png"));
		addEff.setImage(icon);
		addEff.addListener(SWT.Selection, addEffListener());

		listEff = new List(groupEff, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL|SWT.H_SCROLL | SWT.RESIZE);
		
		GridData gd2=new GridData();
		gd2.heightHint=100;
		gd2.widthHint=100;
		listEff.setLayoutData(gd2);
		listPrec.setLayoutData(gd2);
		listPrec.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		listEff.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));

		

		Button btnDeleteEff = new Button(groupEff, SWT.PUSH);
		icon = new Image(mainComposite.getDisplay(), ResourceLoader.load("img/deleteCond.png"));
		btnDeleteEff.setImage(icon);
		btnDeleteEff.addListener(SWT.Selection, getDelEffListener());
		newEff.addListener(SWT.FocusIn, new Listener() {
			public void handleEvent(Event e) {
				setDefaultButton (addEff);	
			}
		});
		
		Composite compOrd2=new Composite(groupEff, SWT.ALL);
		compOrd2.setLayout(new RowLayout(SWT.VERTICAL));
		
		btnUp=new Button(compOrd2, SWT.PUSH);
		icon = new Image(mainComposite.getDisplay(), ResourceLoader.load("img/up.png"));
		btnUp.setImage(icon);
		btnUp.setToolTipText("Up");
		implementBtnUpDown(btnUp,listEff,effect);
		
		btnDown=new Button(compOrd2, SWT.PUSH);
		icon = new Image(mainComposite.getDisplay(), ResourceLoader.load("img/down.png"));
		btnDown.setImage(icon);
		btnDown.setToolTipText("Down");
		implementBtnUpDown(btnDown,listEff,effect);
		
		
		newEffEd=new Text(groupEff,  SWT.SINGLE | SWT.BORDER | SWT.RESIZE);
		newEffEd.setLayoutData(gd1);
		newEffEd.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));

		Button btnNegEff=new Button(groupEff, SWT.PUSH);
		btnNegEff.setText("neg");
		btnNegEff.addListener(SWT.Selection, addBtnNegListener2());
		
		
		btnEditPrec=new Button(groupEff, SWT.PUSH);
		icon = new Image(mainComposite.getDisplay(),ResourceLoader.load( "img/edit.png"));
		btnEditPrec.setImage(icon);
		btnEditPrec.addListener(SWT.Selection, addBtnEditListener(listEff,effect,newEffEd));
		listEff.addListener(SWT.Selection,addListListener(listEff,newEffEd));

		
		
		pack();
	}

	public Listener getDelPrecListener() {
		Listener buttonListener = new Listener() {

			@Override
			public void handleEvent(Event event) {
				int index = listPrec.getSelectionIndex();
				if (!(index > prec.size()) && index != -1) {
					listPrec.remove(index);
					prec.remove(index);
				}

			}
		};

		return buttonListener;

	}

	private Listener addBtnEditListener(List list,ArrayList<String> listCond,Text newCond) {
		Listener l;
		l=new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				 
				int index=list.getSelectionIndex();
				String cond=newCond.getText();
				boolean atleastOneAlpha = cond.matches(".*[a-zA-Z]+.*");
				if(atleastOneAlpha && !(listCond.contains(cond))) {
					list.setItem(index, cond);
					listCond.set(index, cond);
				}
				
			}
		};
		return l;
	}
	
	
	private Listener addBtnNegListener1() {
		Listener l;
		l=new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				 
				if(!(newPrecEd.getText().startsWith("¬")) && (newPrecEd.getText().length()>0)) {
					newPrecEd.setText("¬"+newPrecEd.getText());
				}
				
			}
		};
		return l;
	}
	
	
	
	private Listener addBtnNegListener2() {
		Listener l;
		l=new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				 
				if(!(newEffEd.getText().startsWith("¬")) && (newEffEd.getText().length()>0)) {
					newEffEd.setText("¬"+newEffEd.getText());
				}
				
			}
		};
		return l;
	}
	
	
	private void implementBtnUpDown(Button btn,List list,ArrayList<String> listPCond) {
		btn.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				
				int index = list.getSelectionIndex();
				if(index != -1) {
					if(btn.getToolTipText().equals("Up")){
						
						if(index !=0) {
							listPCond.add(index-1, listPCond.get(index));
							listPCond.remove(index+1);
							String[] stockArr = new String[listPCond.size()];
							stockArr = listPCond.toArray(stockArr);
							list.setItems(stockArr);
							
						}
							
						}else if (btn.getToolTipText().equals("Down")){
							if(index!= list.getItemCount()-1) {
								String temp=listPCond.get(index);
								listPCond.remove(index);
								listPCond.add(index+1, temp);
								
								String[] stockArr = new String[listPCond.size()];
								stockArr = listPCond.toArray(stockArr);
								list.setItems(stockArr);

							}
							
						}
				}

				
				
				
			}
		});
	}
	
	public Listener getDelEffListener() {
		Listener buttonListener = new Listener() {

			@Override
			public void handleEvent(Event event) {
				int index = listEff.getSelectionIndex();
				if (!(index > effect.size()) && index != -1) {
					listEff.remove(index);
					effect.remove(index);
				}

			}
		};

		return buttonListener;

	}

	public Listener addPrecListener() {

		Listener listener = new Listener() {

			@Override
			public void handleEvent(Event event) {

				String cond = newPrec.getText();
				//cond=cond.toLowerCase();
				boolean isChecked = buttonNegPrec.getSelection();
				if (!(prec.contains(cond)) && !cond.equals("")) {
					if (isChecked) {
						cond = "¬" + cond;
					}
					prec.add(cond);
					listPrec.add(cond);
				}
				newPrec.setText("");
			}
		};

		return listener;
	}

	public Listener addEffListener() {

		Listener listener = new Listener() {

			@Override
			public void handleEvent(Event event) {

				String cond = newEff.getText();
				//cond=cond.toLowerCase();
				boolean isChecked = buttonNegEff.getSelection();
				if (!(effect.contains(cond)) && !cond.equals("")) {
					if (isChecked) {
						cond = "¬" + cond;
					}
					effect.add(cond);
					listEff.add(cond);
				}
				newEff.setText("");
			}
		};

		return listener;
	}

	@Override
	public Listener getOkbtnListener() {
		Listener btn = new Listener() {

			@Override
			public void handleEvent(Event event) {
				boolean atleastOneAlpha = actionName.getText().matches(".*[a-zA-Z]+.*");

				if (atleastOneAlpha && !isAlreadyCreated(actionName.getText())) {
						action = new Action(actionName.getText(), prec, effect);
						if(btnPrim.getSelection()) {
							action.setIsFett(GlobalValue.borderIsFatPr);
							action.setIsborder(GlobalValue.formIsBlackPr);
							action.setBorderIsSquare(GlobalValue.cornerIsSquarePr);
						}else {
							action.setIsFett(GlobalValue.borderIsFatAbst);
							action.setIsborder(GlobalValue.formIsBlackAbst);
							action.setBorderIsSquare(GlobalValue.cornerIsSquareAbst);
						}
						TreeItem item=new TreeItem(treeActions, SWT.BORDER);
						item.setText(actionName.getText());
						TreeItem childPrec = new TreeItem(item, SWT.NONE);
						childPrec.setText("Preconditions");
						TreeItem childEff = new TreeItem(item, SWT.NONE);
						childEff.setText("Effect");
						for(String pString:prec) {
							TreeItem child = new TreeItem(childPrec, SWT.NONE);
							child.setText(pString);
						}

						for(String eString:effect) {
							TreeItem child = new TreeItem(childEff, SWT.NONE);
							child.setText(eString);
						}
	
						if(btnPrim.getSelection()) {
							action.setPrimitive(true);
							action.setAbstract(false);

						}else {
							action.setPrimitive(false);
							action.setAbstract(true);
						}
						
						actions.add(action);
						
						setVisible(false);
//						treeActions.pack();
						drawAction();
				}
			}
		};
		
		return btn;
	}

	private void drawAction() {
		ActionView actionView=treeActions.getActionView();
		Composite containerAction=actionView.getContainerAction();
		
		
		for (Control child : containerAction.getChildren()) {
			child.dispose();
		}
	
		ActionDomainCanvas canvasAction = new ActionDomainCanvas(containerAction,
				SWT.DOUBLE_BUFFERED | SWT.NO_REDRAW_RESIZE, action);
		canvasAction.draw();
		canvasAction.addDNDListener();
		canvasAction.resizeParent();
		actionView.setContainerAction(containerAction);

	}
	
	
	private boolean isAlreadyCreated(String actionName) {
		TreeItem[] items=treeActions.getItems();

			for(int i=0;i<items.length;i++) {
				if(items[i].getText().equals(actionName))
					return true;
			}
			return false;
	}
	private Listener addListListener(List list,Text newCond) {
		Listener l;
		l=new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				newCond.setText(list.getItem(list.getSelectionIndex()));
				
			}
		};
		return l;
		
	}

	
}
