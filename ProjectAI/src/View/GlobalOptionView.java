package View;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import Action.GlobalValue;


public class GlobalOptionView extends Composite{

	
	Composite compListdetail;
	Text tWidth,tHeight,tLenEff,tLenPre,tLenEmpty,tLenCond;
	Label confermW,confermH,confermP,confermE,confermEmtpy,lEmpty,confermC;
	Button bntWidth,bntHeigt,bntPrec,bntEff,bntEmty,bntCond;
	
	
	DomainView domainView;
	
	public GlobalOptionView(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}
	
	public void setLayout() {
		setLayout(new GridLayout(2, true));
	}
	
	public void createContent() {
			
		Label l=new Label(this, SWT.ALL);
		l.setText("enable the details, which you want to used");
		GridData gridData = new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false);
		gridData.horizontalSpan = 2;
	   l.setLayoutData(gridData);
		
		
		compListdetail=new Composite(this, SWT.ALL);
		compListdetail.setLayout(new GridLayout(5, false));
		

		bntWidth=new Button(compListdetail, SWT.CHECK | SWT.BORDER);
		bntWidth.addListener(SWT.Selection, getAddBtnWListener(bntWidth));
		Label lWidth=new Label(compListdetail, SWT.ALL);
		lWidth.setText("Width of Action");
		tWidth=new Text(compListdetail, SWT.BORDER );
		tWidth.setEditable(false);
		tWidth.setText("NONE");
		Button btnOkW=new Button(compListdetail, SWT.ALL);
		btnOkW.setText("ok");
		btnOkW.addListener(SWT.Selection, getOkBtnWListener());
		confermW=new Label(compListdetail, SWT.ALL);
		confermW.setText("Global value not present,check the box.");
		
		
		bntHeigt=new Button(compListdetail, SWT.CHECK | SWT.BORDER);
		bntHeigt.addListener(SWT.Selection, getAddBtnHListener(bntHeigt));
		Label lHeight=new Label(compListdetail, SWT.ALL);
		lHeight.setText("Height of Action");
		tHeight=new Text(compListdetail, SWT.BORDER );
		tHeight.setEditable(false);
		tHeight.setText("NONE");
		Button btnOkH=new Button(compListdetail, SWT.ALL);
		btnOkH.setText("ok");
		btnOkH.addListener(SWT.Selection, getOkBtnHListener());
		confermH=new Label(compListdetail, SWT.ALL);
		confermH.setText("Global value not present,check the box.");
		
		
		bntCond=new Button(compListdetail, SWT.CHECK | SWT.BORDER);
		bntCond.addListener(SWT.Selection, getAddBtnCondListener(bntCond));
		Label lConds=new Label(compListdetail, SWT.ALL);
		lConds.setText("Lenght Cond of State");
		tLenCond=new Text(compListdetail, SWT.BORDER );
		tLenCond.setEditable(false);
		tLenCond.setText("NONE");
		Button btnOkCond=new Button(compListdetail, SWT.ALL);
		btnOkCond.setText("ok");
		btnOkCond.addListener(SWT.Selection, getOkBtnCondListener());
		confermC=new Label(compListdetail, SWT.ALL);
		confermC.setText("Global value not present,check the box.");
		
		bntPrec=new Button(compListdetail, SWT.CHECK | SWT.BORDER);
		bntPrec.addListener(SWT.Selection, getAddBtnPListener(bntPrec));
		Label lPrec=new Label(compListdetail, SWT.ALL);
		lPrec.setText("Lenght Precs of Action");
		tLenPre=new Text(compListdetail, SWT.BORDER );
		tLenPre.setEditable(false);
		tLenPre.setText("NONE");
		Button btnOkP=new Button(compListdetail, SWT.ALL);
		btnOkP.setText("ok");
		btnOkP.addListener(SWT.Selection, getOkBtnPListener());
		confermP=new Label(compListdetail, SWT.ALL);
		confermP.setText("Global value not present,check the box.");
		
		
		
		bntEff=new Button(compListdetail, SWT.CHECK | SWT.BORDER);
		bntEff.addListener(SWT.Selection, getAddBtnEListener(bntEff));
		Label lEffs=new Label(compListdetail, SWT.ALL);
		lEffs.setText("Lenght Effs of Action");
		tLenEff=new Text(compListdetail, SWT.BORDER );
		tLenEff.setEditable(false);
		tLenEff.setText("NONE");
		Button btnOkE=new Button(compListdetail, SWT.ALL);
		btnOkE.setText("ok");
		btnOkE.addListener(SWT.Selection, getOkBtnEListener());
		confermE=new Label(compListdetail, SWT.ALL);
		confermE.setText("Global value not present,check the box.");
		
		bntEmty=new Button(compListdetail, SWT.CHECK | SWT.BORDER );
		bntEmty.setSelection(true);
		bntEmty.setEnabled(false);
		lEmpty=new Label(compListdetail, SWT.ALL);
		lEmpty.setText("Lenght of Empty Tssks");
		tLenEmpty=new Text(compListdetail, SWT.BORDER );
		tLenEmpty.setText(GlobalValue.lengthsOfEmptyTasks);
		Button btnOkEmpty1=new Button(compListdetail, SWT.ALL);
		btnOkEmpty1.setText("ok");
		btnOkEmpty1.addListener(SWT.Selection, getOkBtnEmptyListener());
		confermEmtpy=new Label(compListdetail, SWT.ALL);
		confermEmtpy.setText("Insert the value and click OK");
		pack();
		tLenEmpty.setSize(tLenEff.getSize().x, tLenEff.getSize().y);
	}
	
	
	public void update() {
		if(GlobalValue.isWidthOfAction) {
			bntWidth.setSelection(true);
			tWidth.setText(GlobalValue.widthOfAction);
			confermW.setText("Update successfully");

		}
		
		if(GlobalValue.isHeightOfAction) {
			bntHeigt.setSelection(true);
			tHeight.setText(GlobalValue.heightOfAction);					
			confermH.setText("Update successfully");

		}
		
		if(GlobalValue.isLengthsOfPrecs) {
			bntPrec.setSelection(true);
			tLenPre.setText(GlobalValue.lengthsOfPrecs);
			confermP.setText("Update successfully");

		}
		
		if(GlobalValue.isLengthsOfEffs) {
			bntEff.setSelection(true);
			tLenEff.setText(GlobalValue.lengthsOfEffs);
			confermE.setText("Update successfully");

		}
		
		if(GlobalValue.isLengthsOfConds) {
			bntCond.setSelection(true);
			tLenCond.setText(GlobalValue.lengthsOfConds);
			confermC.setText("Update successfully");

		}
		
	
		tLenEmpty.setText(GlobalValue.lengthsOfEmptyTasks);
	
		
		
	}
	
	private Listener getAddBtnWListener(Button b1) {
		Listener l;

		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (b1.getSelection()) {
					tWidth.setEditable(true);
					confermW.setText("Insert the value and click OK");

					
					//insert the new command in the latex code
				} else {
					tWidth.setEditable(false);
					tWidth.setText("NONE");
					GlobalValue.isWidthOfAction=false;
					confermW.setText("Global value not present,check the box.");

				}
				confermW.pack();
				tWidth.pack();
			}
		};

		return l;
	}

	private Listener getOkBtnWListener() {
		Listener l;

		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				//CHECK IF TEXT IS NUMERIC
				if(tWidth.getEditable()) {
					GlobalValue.isWidthOfAction=true;
					GlobalValue.widthOfAction=tWidth.getText();
					confermW.setText("Update successfully");
					domainView.getContentCanvas().pack();
				}
				
			}
		};

		return l;
	}
	
	private Listener getAddBtnHListener(Button b1) {
		Listener l;

		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (b1.getSelection()) {
					tHeight.setEditable(true);
					confermH.setText("Insert the value and click OK");

					
					//insert the new command in the latex code
				} else {
					tHeight.setEditable(false);
					tHeight.setText("NONE");
					GlobalValue.isHeightOfAction=false;
					confermH.setText("Global value not present,check the box.");


				}
				confermH.pack();
				tHeight.pack();
			}
		};

		return l;
	}

	private Listener getOkBtnHListener() {
		Listener l;

		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				//CHECK IF TEXT IS NUMERIC
				if(tHeight.getEditable()) {
					GlobalValue.isHeightOfAction=true;
					GlobalValue.heightOfAction=tHeight.getText();
					confermH.setText("Update successfully");
					domainView.getContentCanvas().pack();

					
				}
				
			}
		};

		return l;
	}
	
	
	private Listener getAddBtnPListener(Button b1) {
		Listener l;

		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (b1.getSelection()) {
					tLenPre.setEditable(true);
					confermP.setText("Insert the value and click OK");

					
					//insert the new command in the latex code
				} else {
					tLenPre.setEditable(false);
					tLenPre.setText("NONE");
					GlobalValue.isLengthsOfPrecs=false;
					confermP.setText("Global value not present,check the box.");


				}
				confermP.pack();

				tLenPre.pack();
			}
		};

		return l;
	}

	private Listener getOkBtnPListener() {
		Listener l;

		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				//CHECK IF TEXT IS NUMERIC
				if(tLenPre.getEditable()) {
					GlobalValue.isLengthsOfPrecs=true;
					GlobalValue.lengthsOfPrecs=tLenPre.getText();
					confermP.setText("Update successfully");
					domainView.getContentCanvas().pack();

				}
				
			}
		};

		return l;
	}
	
	
	
	private Listener getAddBtnEListener(Button b1) {
		Listener l;

		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (b1.getSelection()) {
					tLenEff.setEditable(true);
					confermE.setText("Insert the value and click OK");

					
					//insert the new command in the latex code
				} else {
					tLenEff.setEditable(false);
					tLenEff.setText("NONE");
					GlobalValue.isLengthsOfEffs=false;
					confermE.setText("Global value not present,check the box.");


				}
				confermE.pack();
				tLenEff.pack();
			}
		};

		return l;
	}

	private Listener getOkBtnCondListener() {
		Listener l;

		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				//CHECK IF TEXT IS NUMERIC
				if(tLenCond.getEditable()) {
					GlobalValue.isLengthsOfConds=true;
					GlobalValue.lengthsOfConds=tLenCond.getText();
					confermC.setText("Update successfully");
					domainView.getContentCanvas().pack();

				}
				
			}
		};

		return l;
	}
	
	
	
	private Listener getAddBtnCondListener(Button b1) {
		Listener l;

		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (b1.getSelection()) {
					tLenCond.setEditable(true);
					confermC.setText("Insert the value and click OK");

					
					//insert the new command in the latex code
				} else {
					tLenCond.setEditable(false);
					tLenCond.setText("NONE");
					GlobalValue.isLengthsOfConds=false;
					confermC.setText("Global value not present,check the box.");


				}
				confermC.pack();
				tLenCond.pack();
			}
		};

		return l;
	}

	private Listener getOkBtnEListener() {
		Listener l;

		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				//CHECK IF TEXT IS NUMERIC
				if(tLenEff.getEditable()) {
					GlobalValue.isLengthsOfEffs=true;
					GlobalValue.lengthsOfEffs=tLenEff.getText();
					confermE.setText("Update successfully");
					domainView.getContentCanvas().pack();

				}
				
			}
		};

		return l;
	}
	
	
	private Listener getOkBtnEmptyListener() {
		Listener l;

		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				// CHECK IF TEXT IS NUMERIC

				GlobalValue.lengthsOfEmptyTasks= tLenEmpty.getText();
				confermEmtpy.setText("Update successfully");	
				domainView.getContentCanvas().pack();

			}
		};

		return l;
	}

	public void setDomainView(DomainView domainView) {
		this.domainView = domainView;
	}
	
	
	
}
