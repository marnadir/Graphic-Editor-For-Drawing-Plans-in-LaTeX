package Dialog;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import PlanPart.LinkCanvas;
import PlanPart.OrderConstrain;
import PlanPart.OrderConstrainCanvas;
import View.PlanView;

public class NewConnectionDialog extends IDialog {
	
	Composite compButton;
	private Composite compPoint;
	Group compInfo;
	Label info;

	private LinkCanvas link;
	private OrderConstrainCanvas constrain;
	private OrderConstrain orderCond;
	Label l1 = null;
	Label l2 = null;
	String c1 = "....";
	String c2 = "....";
	Button archBtn;
	PlanView planView;

	public NewConnectionDialog(Shell shell, int style) {
		super(shell, style);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createContent() {

		label.setText("Add Ordering/Link");
		this.label.pack();
		mainComposite.setLayout(new GridLayout(1, false));
		compButton = new Composite(mainComposite, SWT.ALL);
		compButton.setLayout(new RowLayout(SWT.HORIZONTAL));

		archBtn = new Button(compButton, SWT.PUSH);
		archBtn.setText("Draw Link");

		Button ordBtn = new Button(compButton, SWT.PUSH);
		ordBtn.setText("Draw Ord");

		compPoint = new Composite(mainComposite, SWT.ALL);
		compPoint.setLayout(new GridLayout());
		l1 = new Label(compPoint, SWT.ALL);
		l2 = new Label(compPoint, SWT.ALL);
		compPoint.setVisible(false);
		
		compInfo=new Group(mainComposite, SWT.ALL);
		compInfo.setLayout(new GridLayout());
		compInfo.setText("Info");
		info=new Label(compInfo, SWT.ALL);
		info.setText("Creation of casual link"+"\n"+"or ordering constrain");
		ordBtn.addListener(SWT.Selection, getOrdBtnList());
		archBtn.addListener(SWT.Selection, getArchBtnList());

		
		pack();

	}

	public Listener getArchBtnList() {

		Listener archBtnList = new Listener() {

			@Override
			public void handleEvent(Event event) {

				orderCond = null;

				l1.setText("PreCond. :" + "Select the point");
				l1.pack();
				l2.setText("Effect   :" + "Select the point");
				l2.pack();
				compPoint.pack();
				pack();
				compPoint.setVisible(true);
				info.setText("For creating the casual link select"+"\n"+" with double-click the point(cirle)"+"\n"+"of pre/eff cond.");
				info.pack();
				mainComposite.pack();
				pack();
				link = new LinkCanvas(planView.getPlan());
				link.addlistener(l1, l2, archBtn);

			}
		};

		return archBtnList;
	}

	public Listener getOrdBtnList() {
		Listener ordBtnList = new Listener() {

			@Override
			public void handleEvent(Event event) {

				link = null;
				c1 = "null";
				c2 = "null";
				l1.setText("Ordering of actions");
				l2.setText(c1 + "<" + c2);
				l2.pack();
				compPoint.pack();
				pack();
				compPoint.setVisible(true);
				info.setText("For creating the ordering constrain"+"\n"+"select with double-click the action ");
				info.pack();
				mainComposite.pack();
				pack();

				Composite comp = new Composite(planView.getPlan(), SWT.ALL);
				// sulla definizione di cio, ce qualcosa che mi turba!!
				comp.setSize(50, 50);
				comp.setLocation(20, 30);

				orderCond = new OrderConstrain(comp);
				orderCond.addlistener(l1, l2);
			}
		};

		return ordBtnList;
	}
	
	
	@Override
	public Listener getOkbtnListener() {
		Listener l;
		l = new Listener() {

			@Override
			public void handleEvent(Event event) {

				if (link != null) {
					if (!l1.getText().contains("Select the point")
							&& !l2.getText().contains("Select the point")) {
						link.drawLine();
						planView.getPlan().getLink().add(link);
						l1.setText("First Cond. :" + "Select the point");
						l2.setText("Second Cond. :" + "Select the point");
						l1.pack();
						l2.pack();
						link.removeL();
						// link.removelistener(l1, l2,archBtn);

					}
				} else if (orderCond != null) {
					if (!(l1.getText().contains("null")) && !(l2.getText().contains("null"))) {
						orderCond.setLocationParent();
						Composite parent=orderCond.getParent();
						constrain=new OrderConstrainCanvas(parent, SWT.ALL,orderCond);
						constrain.draw();
						constrain.pack();
						constrain.setSize(parent.getSize().x,parent.getSize().y);
						planView.getPlan().getOrds().add(orderCond);
						c1 = "null";
						c2 = "null";
						l1.setText("ordering of actions");
						l2.setText(c1 + "<" + c2);
						l1.pack();
						l2.pack();
						// orderCond.removelistener(l2);

					}
				}

			}
		};
		return l;
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	public void setPlanView(PlanView planView) {
		this.planView = planView;
	}
	
	
}
