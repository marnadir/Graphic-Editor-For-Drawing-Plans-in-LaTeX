package Dialog;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import resourceLoader.ResourceLoader;

public abstract class IDialogNewState extends IDialog{




	ArrayList<String>  listPCond;
	List list;
	Text newPrec;
	Button buttonNeg;
	protected Composite compositeEditText;
	protected Text newCond;
	public Button btnEdit;
	
	


	public IDialogNewState(Shell shell,int style) {
		super(shell,style);
		listPCond=new ArrayList<>();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createContent() {
		// TODO Auto-generated method stub
		label.setText("Create a new  state");
		mainComposite.setLayout(new GridLayout(3, false));

		GridData gd = new GridData ();
		gd.widthHint=100;
		newPrec = new Text(mainComposite, SWT.SINGLE | SWT.BORDER | SWT.RESIZE);
		newPrec.setLayoutData(gd);
		newPrec.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));


		buttonNeg = new Button(mainComposite, SWT.CHECK);
		buttonNeg.setText("neg");

		Button btnAddCond = new Button(mainComposite, SWT.PUSH);
		Image icon = new Image(mainComposite.getDisplay(), ResourceLoader.load("img/add.png"));
		btnAddCond.setImage(icon);

		btnAddCond.addListener(SWT.Selection, getAddListener());
		newPrec.addListener(SWT.FocusIn, new Listener() {
			public void handleEvent(Event e) {
				setDefaultButton (btnAddCond);	
			}
		});
		
		list = new List (mainComposite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL|SWT.H_SCROLL| SWT.RESIZE);
		

		Button btnDeletePrec=new Button(mainComposite, SWT.PUSH);
		icon = new Image(mainComposite.getDisplay(), ResourceLoader.load("img/deleteCond.png"));
		btnDeletePrec.setImage(icon);
		btnDeletePrec.addListener(SWT.Selection, getDelListener());
		
		Composite compOrd=new Composite(mainComposite, SWT.ALL);
		compOrd.setLayout(new RowLayout(SWT.VERTICAL));
		
		Button btnUp=new Button(compOrd, SWT.PUSH);
		icon = new Image(mainComposite.getDisplay(), ResourceLoader.load("img/up.png"));
		btnUp.setImage(icon);
		btnUp.setToolTipText("Up");
		implementBtnUpDown(btnUp);
		
		Button btnDown=new Button(compOrd, SWT.PUSH);
		icon = new Image(mainComposite.getDisplay(), ResourceLoader.load("img/down.png"));
		btnDown.setImage(icon);
		btnDown.setToolTipText("Down");
		implementBtnUpDown(btnDown);

		
		newCond=new Text(mainComposite,  SWT.SINGLE | SWT.BORDER | SWT.RESIZE);
		newCond.setLayoutData(gd);
		newCond.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		
		Button btnNegMod=new Button(mainComposite, SWT.PUSH);
		btnNegMod.setText("neg");
		btnNegMod.addListener(SWT.Selection, addBtnNegListener());
		
		btnEdit=new Button(mainComposite, SWT.PUSH);
		icon = new Image(mainComposite.getDisplay(),ResourceLoader.load( "img/edit.png"));
		btnEdit.setImage(icon);
		
		//btnEdit.setVisible(false);
		btnEdit.addListener(SWT.Selection, addBtnEditListener());
		//newCond.setVisible(false);

		list.addListener(SWT.Selection,addListListener());
		gd = new GridData ();
		gd.heightHint=100;
		gd.widthHint=100;
		list.setLayoutData(gd);
		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		//pack();
		
	}

	private Listener addListListener() {
		Listener l;
		l=new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				newCond.setText(list.getItem(list.getSelectionIndex()));
				
			}
		};
		return l;
		
	}
	
	private Listener addBtnEditListener() {
		Listener l;
		l=new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				 
				int index=list.getSelectionIndex();
				
				if(index!= -1) {
					boolean atleastOneAlpha = newCond.getText().matches(".*[a-zA-Z]+.*");
					if(atleastOneAlpha && !(listPCond.contains(newCond.getText()))) {
						list.setItem(index, newCond.getText());
						listPCond.set(index, newCond.getText());
					}
					
				}
				
			}
		};
		return l;
	}
	
	private Listener addBtnNegListener() {
		Listener l;
		l=new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				 
				if(!(newCond.getText().startsWith("¬")) && (newCond.getText().length()>0)) {
					newCond.setText("¬"+newCond.getText());
				}
				
			}
		};
		return l;
	}
	
	private void implementBtnUpDown(Button btn) {
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
	
	public Listener getAddListener() {
		Listener buttonListener = new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				String cond = newPrec.getText();
				boolean isChecked = buttonNeg.getSelection();
				boolean atleastOneAlpha = cond.matches(".*[a-zA-Z]+.*");
				if (!(listPCond.contains(cond)) && atleastOneAlpha) {
					if (isChecked) {
						cond = "¬" + cond;
					}
					listPCond.add(cond);
					list.add(cond);
					list.pack();
					layout();
					newPrec.requestLayout();
//					pack();
					newPrec.requestLayout();
					list.setSize(SWT.MAX, list.getSize().y);
					setSize(getSize().x+15,getSize().y);

				}
				newPrec.setText("");
			}
		};

		return buttonListener;
	}
	
	
	public Listener getDelListener() {
		Listener buttonListener = new Listener() {

			@Override
			public void handleEvent(Event event) {
				int index = list.getSelectionIndex();
				if (!(index > listPCond.size()) && index != -1) {
					list.remove(index);
					listPCond.remove(index);
				}

			}
		};

		return buttonListener;

	}
	
	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
	public ArrayList<String> getListPCond() {
		return listPCond;
	}

	public void setListPCond(ArrayList<String> listPCond) {
		this.listPCond = listPCond;
	}

	public ArrayList<String> getCond(){
		return listPCond;
	}
	
	
	
}
