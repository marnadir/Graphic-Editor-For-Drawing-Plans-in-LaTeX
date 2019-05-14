package command;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;

import Action.Action;
import Dialog.IDialog;
import View.TreeActioDomainView;

public class ChangeNameCommand implements ICommand {

	Text textName;
	Shell shell;
	

	@Override
	public boolean canExecute(Object var1, Object var2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute(Object var1, Object var2) {
		if (var1 instanceof TreeItem) {
			TreeItem actionTree = (TreeItem) var1;
			if (var2 instanceof Action) {
				Action a = (Action) var2;
				IDialog dialof = new IDialog(actionTree.getParent().getShell(),
						SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER |SWT.RESIZE) {

					@Override
					public void createContent() {

						label.setText("Modificy name action: " + a.getName());
						mainComposite.setLayout(new GridLayout(1, true));
						textName = new Text(mainComposite, SWT.BORDER);
						textName.setText(a.getName());
						textName.setLayoutData(new GridData(SWT.FILL, SWT.FILL,true,true));
						pack();
						

					}

					@Override
					public Listener getOkbtnListener() {
						return new Listener() {

							@Override
							public void handleEvent(Event event) {
								if (!textName.getText().equals("")) {
									a.setName(textName.getText());
									actionTree.setText(textName.getText());
									TreeActioDomainView tree=(TreeActioDomainView)actionTree.getParent();
									if(tree.getActionView().getContainerAction()!=null) {
										tree.getActionView().getContainerAction().redraw();
									}
									//shell.dispose();
								}

							}
						};

					}
				};
				
				dialof.createContent();
				
			}
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
