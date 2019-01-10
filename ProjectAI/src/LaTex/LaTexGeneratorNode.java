package LaTex;

import java.text.DecimalFormat;


import Action.Action;
import Action.ICanvasAction;
import GraphPart.LinkCanvas;
import GraphPart.Oval;
import State.GoalState;
import State.IState;
import State.InitialState;

public class LaTexGeneratorNode {

	final double PIXEL_MEASUREMNT= 0.026458;
	final double CM_MEASUREMNT= 37.7957517575025;
	
	
	public LaTexGeneratorNode() {
		// TODO Auto-generated constructor stub
	}
	
	public String getLatexActionCodePlan(Action action,ICanvasAction node) {
		StringBuilder sb = new StringBuilder();
		sb.append("/action");
		sb.append("{"+action.getName()+"}");//numerare le azioni
		sb.append("{"+action.getName()+"=");
		sb.append(getVariable(action.getName())+",");
		sb.append("body="+"{at={"+getPosition(node)+"}}}");
		

		return sb.toString();
	}
	
	public String getLatexLinkCodePlan(LinkCanvas link) {
		StringBuilder sb = new StringBuilder();
		sb.append("/link");
		sb.append("{"+link.getOval1().getCond()+"/"+isPreOrEff(link.getOval1())+"}");
		sb.append("{"+link.getOval2().getCond()+"}");
		sb.append("{edge"+getBend(link)+"}");
		return sb.toString();
	}
	
	/*tell if it is a prec or eff and the number*/
	public String isPreOrEff(Oval o){
		
		StringBuilder sb = new StringBuilder();
		String cond=o.getCond();

		/*if is a action*/
		if(o.getAction()!= null) {
			Action a=o.getAction();
			for(int i=0;i<a.getPrec().size();i++) {
				if(a.getPrec().get(i).equals(cond)) {
					sb.append("pre/");
					int num=i+1;
					sb.append(num);
					return sb.toString();
				}
			}
			for(int i=0;i<a.getEffect().size();i++) {
				if(a.getEffect().get(i).equals(cond)) {
					sb.append("eff/");
					int num=i+1;
					sb.append(num);
					return sb.toString();
				}
			}
			
			
		/*if it is a start/goal state*/
		}else if(o.getState()!= null) {
			IState state=o.getState();
			if(state instanceof InitialState) {
				for(int i=0;i<state.getConds().size();i++) {
					if(state.getConds().get(i).equals(cond)) {
						sb.append("eff/");
						int num=i+1;
						sb.append(num);
						return sb.toString();
					}
				}
				return sb.toString();
			}else if(state instanceof GoalState) {
				
				for(int i=0;i<state.getConds().size();i++) {
					if(state.getConds().get(i).equals(cond)) {
						sb.append("pre/");
						int num=i+1;
						sb.append(num);
						return sb.toString();
					}
				}
			}
		}
		
		return sb.toString();


	}
	
	
	public String getBend(LinkCanvas link) {
		StringBuilder sb=new StringBuilder();		
		sb.append("[bend");
		
		//ask if is left o right
		
		//ask the inclination of curve
		
		sb.append("->]");
		
		
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
	
	public String getPosition(ICanvasAction node) {
		StringBuilder sb=new StringBuilder();		
		sb.append("("+convertInCm(node.getLocation().x)+",");
		sb.append(convertInCm(node.getLocation().y)+")");
		return sb.toString();
	}
	
	public String convertInCm(int x) {
		DecimalFormat df = new DecimalFormat("#.00");
	    String result = df.format(x*PIXEL_MEASUREMNT);
	    return result;
	    
	}
	
	
	
	
	
	
}
