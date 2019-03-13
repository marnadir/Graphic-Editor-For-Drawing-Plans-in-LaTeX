package Dialog;

import java.util.ArrayList;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public abstract class IDialogNewState extends IDialog{




	ArrayList<String>  listPCond;
	List list;
	Text newPrec;
	Button buttonNeg;
	protected Composite compositeDialog;
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
		compositeDialog = composite;
		compositeDialog.setLayout(new GridLayout(3, false));

		

		
		newPrec = new Text(compositeDialog, SWT.SINGLE | SWT.BORDER);

		buttonNeg = new Button(compositeDialog, SWT.CHECK);
		buttonNeg.setText("neg");

		Button btnAddCond = new Button(compositeDialog, SWT.PUSH);
		Image icon = new Image(compositeDialog.getDisplay(), "img/addCond.png");
		btnAddCond.setImage(icon);

		btnAddCond.addListener(SWT.Selection, getAddListener());
		
		
		list = new List (compositeDialog, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		
		Button btnDeletePrec=new Button(compositeDialog, SWT.PUSH);
		icon = new Image(compositeDialog.getDisplay(), "img/deleteCond.png");
		btnDeletePrec.setImage(icon);
		btnDeletePrec.addListener(SWT.Selection, getDelListener());
		
		Composite compOrd=new Composite(compositeDialog, SWT.ALL);
		compOrd.setLayout(new RowLayout(SWT.VERTICAL));
		
		Button btnUp=new Button(compOrd, SWT.PUSH);
		icon = new Image(compositeDialog.getDisplay(), "img/up.png");
		btnUp.setImage(icon);
		btnUp.setToolTipText("Up");
		implementBtnUpDown(btnUp);
		
		Button btnDown=new Button(compOrd, SWT.PUSH);
		icon = new Image(compositeDialog.getDisplay(), "img/down.png");
		btnDown.setImage(icon);
		btnDown.setToolTipText("Down");
		implementBtnUpDown(btnDown);

		

		newCond=new Text(compositeDialog,  SWT.SINGLE | SWT.BORDER);
		btnEdit=new Button(compositeDialog, SWT.PUSH);
		icon = new Image(compositeDialog.getDisplay(), "img/edit.png");
		btnEdit.setImage(icon);
		
		btnEdit.setVisible(false);
		btnEdit.addListener(SWT.Selection, addBtnEditListener());
		newCond.setVisible(false);

		list.addListener(SWT.Selection,addListListener());


		pack();
		
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
					list.setItem(index, newCond.getText());
					listPCond.set(index, newCond.getText());

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
				if (!(listPCond.contains(cond)) && !cond.equals("")) {
					if (isChecked) {
						cond = "¬" + cond;
					}
					listPCond.add(cond);
					list.add(cond);
					list.pack();
					layout();
					newPrec.requestLayout();
					pack();
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
