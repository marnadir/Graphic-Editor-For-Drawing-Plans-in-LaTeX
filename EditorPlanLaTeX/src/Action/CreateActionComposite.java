package Action;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import View.TreeActioDomainView;
import command.CreateActionDialogCommand;
import resourceLoader.ResourceLoader;
/**
 * Container where are created treeActionView and the ActionView
 * @author nadir
 * */
public class CreateActionComposite extends Composite{

	CreateActionDialogCommand actionCommnd = new CreateActionDialogCommand();
	TreeActioDomainView treeAction;
	
	
	public CreateActionComposite(Composite parent, int style,String name) {
		super(parent, style);
		this.setLayout();
		// TODO Auto-generated constructor stub
	}

	public void setLayout() {
		this.setLayout(new GridLayout(2, false));
	}
	
	
	public void setTreeAction(TreeActioDomainView treeAction) {
		this.treeAction = treeAction;
	}

	public void createContent() {

		Image img ;
		img = new Image(getDisplay(), ResourceLoader.load("img/add.png"));
		
		Button bntAct = new Button(this, SWT.PUSH);
		bntAct.setText("Action");
		bntAct.setImage(img);
		
		bntAct.addListener(SWT.Selection, getListenerbtnAction());

	
	}
	

	public Listener getListenerbtnAction() {
		Listener l =  new Listener() {

			@Override
			public void handleEvent(Event event) {
				actionCommnd.execute(treeAction, treeAction.getActionList());
				pack();
			}	
		};
		
		
		return l;
	}
	
	@Override
	protected void checkSubclass() {
	}
	
}

