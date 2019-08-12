package dialog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import Action.Action;
import PlanPart.Oval;
import PlanPart.OvalCounter;
import PlanPart.PlanContent;
/** 
 *dialog used to set the variable into constant. 
 *
 **/

public class InitializationVariableDialog extends IDialog{
	
	Action action;
	PlanContent plan;
	Map<String, String> mapping;
	ArrayList<Text> textList;
	ArrayList<Label> labelList;
	

	public InitializationVariableDialog(Shell shell, int style) {
		super(shell, style);
		textList=new ArrayList<>();
		labelList=new ArrayList<>();
		// TODO Auto-generated constructor stub
	}

	public void setAction(Action a) {
		this.action=a;
	}
	
	
	public void setPlan(PlanContent plan) {
		this.plan = plan;
	}

	@Override
	public void createContent() {
		label.setText("Set the name of variables");
		mainComposite.setLayout(new GridLayout(2, false));
		String[] variable2=getVariable(action.getName());
		
		for(int i=0;i<variable2.length;i++) {
			Label l1=new Label(mainComposite, SWT.ALL);
			l1.setText(variable2[i]);
			labelList.add(l1);
			Text t1=new Text(mainComposite, SWT.BORDER);
			textList.add(t1);
			t1.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
		}
		
	
	}

	public String[] getVariable(String string) {
		String name[]=string.split("\\(");
		String variable[]=name[1].split("\\)");
		variable=variable[0].split(",");
		return variable;
	}
	
	
	public String getNewName(String string) {

		StringBuilder sb = new StringBuilder();

		String name[] = string.split("\\(");
		String variable[] = name[1].split("\\)");
		variable = variable[0].split(",");

		sb.append(name[0] + "(");
		for (int i = 0; i < variable.length; i++) {
			sb.append(mapping.get(variable[i]));
			if (i < variable.length - 1) {
				sb.append(",");
			}

		}

		sb.append(")");

		return sb.toString();

	}
	
	public String getNewNameCond(String string) {
		StringBuilder sb = new StringBuilder();
		String name[]=string.split("\\(");
		String variable[]=name[1].split("\\)");
		variable=variable[0].split(",");
		String test = ""  ;
		int num=variable.length;
		for(int i=0;i<num;i++) {
			if(mapping.get(variable[i])!=null) {
				test+=mapping.get(variable[i]);
			}else {
				test+=(variable[i]);
			}
			if(i<num-1) {
				test=test+",";
			}
		}
		
	
		sb.append(name[0]+"("+test+")");
		

		
	
		
		return sb.toString();
	}
	
	private ArrayList<String> getNewCond(ArrayList<String> cond){
		ArrayList<String> conds=new ArrayList<>();
		
		for(String c1:cond) {
			conds.add(getNewNameCond(c1));			
		}
		return conds;
	}
	
	
	
	@Override
	public Listener getOkbtnListener() {
		Listener l;
		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				String[] variable2=getVariable(action.getName());
				mapping=new TreeMap<String,String>(String.CASE_INSENSITIVE_ORDER);
				for(int i=0;i<variable2.length;i++) {
					if(textList.get(i).getText().equals("")) {
						textList.get(i).setText(variable2[i]);
					}
					mapping.put(variable2[i],textList.get(i).getText());
				}
				changeNameOfOval();
				action.setName(getNewName(action.getName()));
				action.setPrec(getNewCond(action.getPrec()));
				action.setEffect(getNewCond(action.getEffect()));
				dispose();

			}
		};
		return l;

	}
	/*
	 * when changed name of variable, need also to change the cond of oval
	 * otherwise we have the double of oval(old and new)
	 * 
	 * */
	private void changeNameOfOval() {
		OvalCounter ovalCounter = plan.getOvalCounter();
		Iterator<Oval> iter = ovalCounter.getListOval().iterator();
		for (int i = 0; i < textList.size(); i++) {
			if (!(labelList.get(i).getText().equals(textList.get(i).getText()))) {
				while (iter.hasNext()) {
					Oval o = iter.next();
					if (o.getNode() != null) {
						if (o.getNode().getAction().equals(action)) {
								if(isVariable(o.getCond(), action)) {
									o.dispose();
									iter.remove();
								}					
							}	
					}
				}
			}
		}

	}

	private boolean isVariable(String string,Action action) {
		
		String name[]=string.split("\\(");
		String variable[]=name[1].split("\\)");
		variable=variable[0].split(",");
		
		String name2[]=action.getName().split("\\(");
		String variable2[]=name2[1].split("\\)");
		variable2=variable2[0].split(",");
		
		for(int i=0;i<variable.length;i++) {
			for(int j=0;j<variable2.length;j++) {
				if(variable[i].equals(variable2[j])) {
					return true;

				}
			}
		}
		return false;
	}
	
	
}
