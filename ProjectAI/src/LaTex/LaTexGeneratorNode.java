package LaTex;

import java.util.HashMap;

import Action.Action;

public class LaTexGeneratorNode {

	public LaTexGeneratorNode() {
		// TODO Auto-generated constructor stub
	}
	
	public String getLatexActionCodePlan(Action action) {
		StringBuilder sb = new StringBuilder();
		sb.append("/action");
		sb.append("{"+action.getName()+"}");//numerare le azioni
		sb.append("{"+action.getName()+"=");
		sb.append(getVariable(action.getName())+",");
		sb.append("body="+"position");
		

		return sb.toString();
	}
	
	public String getVariable(String string) {
		String name[]=string.split("\\(");
		String variable[]=name[1].split("\\)");
		variable=variable[0].split(",");
		int num=variable.length;
	
		
		String testo="";
		for(int i=0;i<num;i++) {
			testo +="{"+variable[i]+"},";
		}
		testo=testo.substring(0, testo.length()-1);
		
		StringBuilder sb=new StringBuilder();
		sb.append(testo);

		
		return sb.toString();
	}
}
