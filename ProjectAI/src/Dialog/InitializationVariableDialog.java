package Dialog;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import Action.Action;

public class InitializationVariableDialog extends IDialog{
	
	Action action;
	Map<String, String> mapping;
	ArrayList<Text> textList;
	

	public InitializationVariableDialog(Shell shell, int style) {
		super(shell, style);
		textList=new ArrayList<>();
		// TODO Auto-generated constructor stub
	}

	public void setAction(Action a) {
		this.action=a;
	}
	
	@Override
	public void createContent() {
		label.setText("Set the name of variables");
		mainComposite.setLayout(new GridLayout(2, false));
		String[] variable2=getVariable(action.getName());
		
		for(int i=0;i<variable2.length;i++) {
			Label l1=new Label(mainComposite, SWT.ALL);
			l1.setText(variable2[i]);
			Text t1=new Text(mainComposite, SWT.BORDER);
			textList.add(t1);
		}
		
	
	}

	public String[] getVariable(String string) {
		String name[]=string.split("\\(");
		String variable[]=name[1].split("\\)");
		variable=variable[0].split(",");
		return variable;
	}
	
	
	public String getNewName(String string) {
		
		StringBuilder sb=new StringBuilder();
		
		String name[]=string.split("\\(");
		String variable[]=name[1].split("\\)");
		variable=variable[0].split(",");
		
		
		sb.append(name[0]+"(");
		for(int i=0;i<variable.length;i++) {
			sb.append(mapping.get(variable[i]));
			if(i<variable.length-1) {
				sb.append(",");
			}
			
		}

		sb.append(")");
		
		return sb.toString();
		
		
	}
	
	public ArrayList<String> getNewCond(ArrayList<String> cond){
		ArrayList<String> conds=new ArrayList<>();
		
		for(String c1:cond) {
			conds.add(getNewName(c1));			
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
				
				action.setName(getNewName(action.getName()));
				action.setPrec(getNewCond(action.getPrec()));
				action.setEffect(getNewCond(action.getEffect()));
				dispose();

			}
		};
		return l;

	}
	

}
