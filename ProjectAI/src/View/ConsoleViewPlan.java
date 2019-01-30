package View;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
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

import Action.Node;
import GraphPart.GraphContent;
import GraphPart.LinkCanvas;
import GraphPart.OrderCondition;

public class ConsoleViewPlan extends Group {

	public ConsoleViewPlan(Composite parent, int style) {
		super(parent, style);
		setText("Plan");
		// TODO Auto-generated constructor stub
	}

	public void setLayout() {
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		setLayout(new GridLayout(1, true));

	}
	
	public void createContent(GraphContent contentAction,CTabFolder planView) {

		ToolBar toolBarPlan = new ToolBar(this, SWT.FLAT | SWT.WRAP | SWT.RIGHT);

		ToolItem updateTextPlan = new ToolItem(toolBarPlan, SWT.PUSH);
		updateTextPlan.setText("update");
		Image icon = new Image(getDisplay(), "img/refresh.png");
		updateTextPlan.setImage(icon);
		ToolItem clearTextPlan = new ToolItem(toolBarPlan, SWT.PUSH);
		clearTextPlan.setText("clear");
		icon = new Image(getDisplay(), "img/clear.ico");
		clearTextPlan.setImage(icon);

		Text textPlan = new Text(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
		textPlan.insert("update data...");
		textPlan.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		textPlan.pack();

		toolBarPlan.pack();
		/* Plan */
		updateTextPlan.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				textPlan.setText("");

				
				GraphContent contentAction = (GraphContent)planView.getSelection().getControl();
				ArrayList<Node> updateNodeList = contentAction.getActionInPlan();
				for (int i = 0; i < updateNodeList.size(); i++) {
					updateNodeList.get(i).generateLatexCode();
					textPlan.insert(updateNodeList.get(i).getLatexCode());
				}

				ArrayList<LinkCanvas> updateLinkList = contentAction.getLink();
				for (int i = 0; i < updateLinkList.size(); i++) {
					updateLinkList.get(i).generateLatexCode();
					textPlan.insert(updateLinkList.get(i).getLatexCode());
				}

				ArrayList<OrderCondition>updateOrder = contentAction.getOrds();
				for (int i = 0; i < updateOrder.size(); i++) {
					updateOrder.get(i).generateLatexCode();
					textPlan.insert(updateOrder.get(i).getLatexCode());
				}

			}
		});
		
	}
	
	
	@Override
	protected void checkSubclass() {
		
	}
}
