package dialogAction;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;

import Action.Action;
import Action.GlobalValue;
import View.TreeActioDomainView;
import dialog.IDialog;
import resourceLoader.ResourceLoader;
/**
 * Dialog which allows to edit an domain-action(name,preconditions and effects).
 * @author nadir
 * */
public class ChangeActionDialog extends IDialog{


	Action a;
	TreeItem itemRoot;
	
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


	TreeActioDomainView treeActions;
	ArrayList<Action> actions;
	protected Text newPrecEd;
	protected Text newEffEd;
	Button btnEditPrec;

	final double CM_MEASUREMNT= 37.7957517575025;

	
	
	public ChangeActionDialog(Shell shell, int style) {
		super(shell, style);
		// TODO Auto-generated constructor stub
	}

	
	public void setVariable(Action a,TreeItem itemRoot) {
		this.a=a;
		this.itemRoot=itemRoot;
		this.treeActions=(TreeActioDomainView)itemRoot.getParent();
		this.actions=treeActions.getActionList();
	}




	@Override
	public void createContent() {
		label.setText("Set the action: "+ a.getName());

		mainComposite.setLayout(new GridLayout(2, false));
		
		btnPrim=new  Button(mainComposite, SWT.CHECK);
		btnPrim.setText("Primitive");
		
		btnAbst=new  Button(mainComposite, SWT.CHECK);
		btnAbst.setText("Abstract");
		
		
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
		
		//TODO
		if(a.isPrimitive()) {
			btnPrim.setSelection(true);
		}else {
			btnAbst.setSelection(true);
		}
		
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

		//TODO
		actionName.setText(a.getName());
		
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
		
		for (int i = 0; i < a.getPrec().size(); i++) {
			listPrec.add(a.getPrec().get(i));
		}
		
		Composite compOrd=new Composite(groupPrec, SWT.ALL);
		compOrd.setLayout(new RowLayout(SWT.VERTICAL));
		
		Button btnUp=new Button(compOrd, SWT.PUSH);
		icon = new Image(mainComposite.getDisplay(), ResourceLoader.load("img/up.png"));
		btnUp.setImage(icon);
		btnUp.setToolTipText("Up");
		implementBtnUpDown(btnUp,listPrec,a.getPrec());
		
		Button btnDown=new Button(compOrd, SWT.PUSH);
		icon = new Image(mainComposite.getDisplay(), ResourceLoader.load("img/down.png"));
		btnDown.setImage(icon);
		btnDown.setToolTipText("Down");
		implementBtnUpDown(btnDown,listPrec,a.getPrec());
		
		
		newPrecEd=new Text(groupPrec,  SWT.SINGLE | SWT.BORDER | SWT.RESIZE);
		newPrecEd.setLayoutData(gd1);
		newPrecEd.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		
		Button btnNegPrec=new Button(groupPrec, SWT.PUSH);
		btnNegPrec.setText("neg");
		btnNegPrec.addListener(SWT.Selection, addBtnNegListener1());
		
		
		btnEditPrec=new Button(groupPrec, SWT.PUSH);
		icon = new Image(mainComposite.getDisplay(),ResourceLoader.load( "img/edit.png"));
		btnEditPrec.setImage(icon);
		btnEditPrec.addListener(SWT.Selection, addBtnEditListener(listPrec,a.getPrec(),newPrecEd));

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

		for (int i = 0; i < a.getEffect().size(); i++) {
			listEff.add(a.getEffect().get(i));
		}

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
		implementBtnUpDown(btnUp,listEff,a.getEffect());
		
		btnDown=new Button(compOrd2, SWT.PUSH);
		icon = new Image(mainComposite.getDisplay(), ResourceLoader.load("img/down.png"));
		btnDown.setImage(icon);
		btnDown.setToolTipText("Down");
		implementBtnUpDown(btnDown,listEff,a.getEffect());
		
		
		newEffEd=new Text(groupEff,  SWT.SINGLE | SWT.BORDER | SWT.RESIZE);
		newEffEd.setLayoutData(gd1);
		newEffEd.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));

		Button btnNegEff=new Button(groupEff, SWT.PUSH);
		btnNegEff.setText("neg");
		btnNegEff.addListener(SWT.Selection, addBtnNegListener2());
		
		
		btnEditPrec=new Button(groupEff, SWT.PUSH);
		icon = new Image(mainComposite.getDisplay(),ResourceLoader.load( "img/edit.png"));
		btnEditPrec.setImage(icon);
		btnEditPrec.addListener(SWT.Selection, addBtnEditListener(listEff,a.getEffect(),newEffEd));
		listEff.addListener(SWT.Selection,addListListener(listEff,newEffEd));

		
		
		pack();
		
	}

	public Listener getDelPrecListener() {
		Listener buttonListener = new Listener() {

			@Override
			public void handleEvent(Event event) {
				int index = listPrec.getSelectionIndex();
				if (!(index > a.getPrec().size()) && index != -1) {
					listPrec.remove(index);
					a.getPrec().remove(index);
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
				if (!(index > a.getEffect().size()) && index != -1) {
					listEff.remove(index);
					a.getEffect().remove(index);
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
				if (!(a.getPrec().contains(cond)) && !cond.equals("")) {
					if (isChecked) {
						cond = "¬" + cond;
					}
					a.getPrec().add(cond);
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
				if (!(a.getEffect().contains(cond)) && !cond.equals("")) {
					if (isChecked) {
						cond = "¬" + cond;
					}
					a.getEffect().add(cond);
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

				if (atleastOneAlpha) {
					actions.remove(a);
						if(btnPrim.getSelection()) {
							a.setIsFett(GlobalValue.borderIsFatPr);
							a.setIsborder(GlobalValue.formIsBlackPr);
							a.setBorderIsSquare(GlobalValue.cornerIsSquarePr);
						}else {
							a.setIsFett(GlobalValue.borderIsFatAbst);
							a.setIsborder(GlobalValue.formIsBlackAbst);
							a.setBorderIsSquare(GlobalValue.cornerIsSquareAbst);
						}
						
						
						updatePrec(listPrec);
						updateEff(listEff);
						updateName();
						updateTree();
						
						if(btnPrim.getSelection()) {
							a.setPrimitive(true);
							a.setAbstract(false);

						}else {
							a.setPrimitive(false);
							a.setAbstract(true);
						}
						
						actions.add(a);
						
						setVisible(false);

				}
			}
		};
		
		return btn;
	}

	
	private void updatePrec(List l) {
		if(a.getPrec().size()>0 && a.getStandardLengthPrec()==0) {
			
			a.setStandardLengthPrec( CM_MEASUREMNT*Double.parseDouble(GlobalValue.lengthsOfEmptyTasks));
		}
		a.setPrec(new ArrayList<>());
		for(int i=0;i<l.getItemCount();i++) {
			a.getPrec().add(l.getItem(i));
		}
	}
	
	public void updateEff(List l) {
		if(a.getEffect().size()>0 && a.getStandardLengthEff()==0) {
			a.setStandardLengthEff(CM_MEASUREMNT*Double.parseDouble(GlobalValue.lengthsOfEmptyTasks));
		}
		a.setEffect(new ArrayList<>());
		for(int i=0;i<l.getItemCount();i++) {
			a.getEffect().add(l.getItem(i));
		}
	}
	
	private void updateName() {
		if (!actionName.getText().equals("")) {
			a.setName(actionName.getText());
			itemRoot.setText(actionName.getText());
		}
	}
	
	private void updateTree() {
		TreeItem prec=itemRoot.getItem(0);
		prec.removeAll();
		for(int i=0;i<a.getPrec().size();i++) {
			TreeItem item=new TreeItem(prec, SWT.ALL);
			item.setText(a.getPrec().get(i));
		}
		TreeItem eff=itemRoot.getItem(1);
		eff.removeAll();
		for(int i=0;i<a.getEffect().size();i++) {
			TreeItem item=new TreeItem(eff, SWT.ALL);
			item.setText(a.getEffect().get(i));
		}
		TreeActioDomainView tree=(TreeActioDomainView)itemRoot.getParent();
		if(tree.getActionView().getContainerAction()!=null) {
			tree.getActionView().getContainerAction().redraw();
		}
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
