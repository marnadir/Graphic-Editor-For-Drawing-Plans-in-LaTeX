package View;

import java.util.ArrayList;

import javax.swing.Icon;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import Action.Action;

public class ConsoleViewDomain extends Group{

	public ConsoleViewDomain(Composite parent, int style) {
		super(parent, style);
		setText("Domain");
		// TODO Auto-generated constructor stub
	}

	public void setLayout() {
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		setLayout(new GridLayout(1, true));

	}
	
	public void createContent(DomainView domainView) {
		
		ToolBar toolBarDomain = new ToolBar(this, SWT.FLAT | SWT.WRAP | SWT.RIGHT);

		ToolItem updateTextDomain = new ToolItem(toolBarDomain, SWT.PUSH);
		updateTextDomain.setText("update");
		Image icon = new Image(getDisplay(), "img/refresh.png");
		updateTextDomain.setImage(icon);
		Text textDomain = new Text(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);

		textDomain.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		textDomain.pack();

		/* domain */
		updateTextDomain.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				textDomain.setText("");
				if (domainView.getInitialState() != null) {
					domainView.getInitialState().generateLatexCode();
					textDomain.insert(domainView.getInitialState().getLatexCode());
				}
				if (domainView.getGoalState() != null) {
					domainView.getGoalState().generateLatexCode();
					textDomain.insert(domainView.getGoalState().getLatexCode());
				}

				ArrayList<Action> updateActionListDomain = domainView.getTreeAction().getActionList();
				for (int i = 0; i < updateActionListDomain.size(); i++) {
					updateActionListDomain.get(i).generateLatexCode();
					textDomain.insert(updateActionListDomain.get(i).getLatexCode());
				}

			}
		});

		ToolItem clearTextDomain = new ToolItem(toolBarDomain, SWT.PUSH);
		clearTextDomain.setText("clear");
		icon = new Image(getDisplay(), "img/clear.ico");
		clearTextDomain.setImage(icon);
		clearTextDomain.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				textDomain.setText(".....");

			}
		});

		toolBarDomain.pack();

		
	}
	
	@Override
	protected void checkSubclass() {
		
	}
}
