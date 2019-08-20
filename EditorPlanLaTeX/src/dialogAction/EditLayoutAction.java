package dialogAction;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import Action.Action;
import Action.GlobalValue;
import Action.ICanvas;
import Menu.MenuContentAction;
import dialog.IDialog;
/**
 * Dialog which allows to edit the layout of an action(e.g. color,border and form).
 * @author nadir
 * */
public class EditLayoutAction extends IDialog{
	
	Action action;
	ICanvas canvas;
	Combo cCorner,cForm,cBord,cColor;
	MenuContentAction menu;


	public EditLayoutAction(Shell shell, int style) {
		super(shell, style);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createContent() {
		label.setText("Set layout of the action: "+action.getName());
		mainComposite.setLayout(new GridLayout(2,true));
		mainComposite.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
		
		Label lForm=new Label(mainComposite, SWT.ALL);
		lForm.setText("Border Color: ");
		cForm=new Combo(mainComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		String[] items = new String[] { "Black", "White" };
		cForm.setItems(items);
		if(action.Isborder()) {
			cForm.select(0);	
		}else {
			cForm.select(1);	
		}
		
		

		Label lCorner=new Label(mainComposite, SWT.ALL);
		lCorner.setText("Corner: ");
		cCorner=new Combo(mainComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		items = new String[] { "Round", "Square" };
		cCorner.setItems(items);
		if(action.isBorderIsSquare()) {
			cCorner.select(1);
		}else {
			cCorner.select(0);
		}
		
		Label  lBord=new Label(mainComposite, SWT.ALL);
		lBord.setText("Fat Border: ");
		cBord=new Combo(mainComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		items = new String[] { "Fat", "Normal" };
		cBord.setItems(items);
		if(action.isFett()) {
			cBord.select(0);
		}else {
			cBord.select(1);
		}
		
		Label  lColor=new Label(mainComposite, SWT.ALL);
		lColor.setText("Fill Color: ");
		cColor=new Combo(mainComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		items = new String[] { "Cyan", "Yellow","None" };
		cColor.setItems(items);
		if(action.isFillColor()) {
			switch (action.getColorString()) {
			case "Cyan":
				cColor.select(0);
				break;	
			case "Yellow":
				cColor.select(1);
				break;

			default:
				cColor.select(2);
				break;
			}
			
			
		}else {
			cColor.select(2);

		}
		Button btnUpdateP=new Button(mainComposite, SWT.PUSH);
		btnUpdateP.setText("Refresh with global values");
		btnUpdateP.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				
				if(action.isPrimitive()) {
					if(GlobalValue.formIsBlackPr==true) {
						cForm.select(0);	
					}else {
						cForm.select(1);	
					}
					
					if (GlobalValue.borderIsFatPr == true) {
						cBord.select(0);
					} else {
						cBord.select(1);
					}
					
					if(GlobalValue.cornerIsSquarePr==true) {
						cCorner.select(1);

					}else {
						cCorner.select(0);

					}
					
					if(GlobalValue.colorP.equalsIgnoreCase("Cyan")) {
						cColor.select(0);

					}else if(GlobalValue.colorP.equalsIgnoreCase("Yellow")) {
						cColor.select(1);

					}else {
						cColor.select(2);

					}

				} else if(action.isAbstract()) {
					if(GlobalValue.formIsBlackAbst==true) {
						cForm.select(0);	
					}else {
						cForm.select(1);	
					}
					
					if (GlobalValue.borderIsFatAbst = true) {
						cBord.select(0);
					} else {
						cBord.select(1);
					}
					
					if(GlobalValue.cornerIsSquareAbst==true) {
						cCorner.select(1);

					}else {
						cCorner.select(0);

					}
					
					if(GlobalValue.colorAbst.equalsIgnoreCase("Cyan")) {
						cColor.select(0);

					}else if(GlobalValue.colorAbst.equalsIgnoreCase("Yellow")) {
						cColor.select(1);

					}else {
						cColor.select(2);

					}					
				}

				action.setDefaultAction(true);
				dispose();
			}

		});
		
		setSize(350,300);

	}

	@Override
	public Listener getOkbtnListener() {
		Listener l;
		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				canvas.getAction().setDefaultAction(false);
				if(cForm.getItem(cForm.getSelectionIndex()).equals("Black")) {
					canvas .getAction().setIsborder(true);
				}else {
					canvas.getAction().setIsborder(false);

				}
				
				if(cCorner.getItem(cCorner.getSelectionIndex()).equals("Square")) {
					action.setBorderIsSquare(true);
				}else {
					action.setBorderIsSquare(false);

				}
				
				
				if(cBord.getItem(cBord.getSelectionIndex()).equals("Fat")) {
					action.setIsFett(true);
				}else {
					action.setIsFett(false);

				}
				
				
				action.setColorString((cColor.getItem(cColor.getSelectionIndex())));
				if(!(action.getColorString().equals("None"))) {
					canvas.getAction().setIsFillColor(true);
				}
			
				
				canvas.getContentCanvas().redraw();
				canvas.getAction().setDefaultAction(false);


				dispose();
			}
		};
		return l;
	}



	public void setCanvas(ICanvas canvas,Action a) {
		this.canvas = canvas;
		this.action=a;
	}

	
	

}
