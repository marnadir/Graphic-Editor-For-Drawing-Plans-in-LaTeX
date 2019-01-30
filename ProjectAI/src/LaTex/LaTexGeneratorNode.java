package LaTex;

import java.text.DecimalFormat;


import Action.Action;
import Action.ICanvasAction;
import Action.Node;
import GraphPart.LinkCanvas;
import GraphPart.OrderCondition;
import GraphPart.Oval;
import State.GoalStateCanvas;
import State.IStateCanvas;
import State.InitialStateCanvas;

public class LaTexGeneratorNode {

	final double PIXEL_MEASUREMNT= 0.026458;
	final double CM_MEASUREMNT= 37.7957517575025;
	
	
	public LaTexGeneratorNode() {
		// TODO Auto-generated constructor stub
	}
	
	public String getLatexActionCodePlan(Action action,Node node) {
		StringBuilder sb = new StringBuilder();
		sb.append("/action");
		sb.append("{"+node.getID()+"}");//numerare le azioni
		sb.append("{"+getNameAction(action.getName())+"=");
		sb.append(getVariable(action.getName())+","+"\n");
		sb.append("  body="+"{at={"+getPosition(node)+"}}}"+"\n"+"\n");
		

		return sb.toString();
	}
	
	public String getLatexLinkCodePlan(LinkCanvas link) {
		StringBuilder sb = new StringBuilder();
		sb.append("/link");
	
		sb.append("{"+isStateorAction(link.getOval1())+"/"+isPreOrEff(link.getOval1())+"}");
		sb.append("{"+isStateorAction(link.getOval2())+"/"+isPreOrEff(link.getOval2())+"}");
		sb.append("{edge"+getBend(link)+"}"+"\n"+"\n");
		return sb.toString();
	}
	
	
	public String getLatexOrderCodePlan(OrderCondition order) {
		StringBuilder sb = new StringBuilder();
		sb.append("/ordering");
		sb.append("{[yshift=.65cm,xshift=.2cm]"+order.getCond1().getID()+".east}");
		sb.append("{[yshift=.65cm,xshift=.2cm]"+order.getCond2().getID()+".west}"+"\n"+"\n");
		return sb.toString();
	}
	
	
	
	public String isStateorAction(Oval o) {
		StringBuilder sb = new StringBuilder();
		if(o.getNode()!= null) {
			sb.append(o.getNode().getID());
		}else if(o.getStateCanvas()!=null) {
			sb.append(o.getStateCanvas().getName());
		}
		
		
		return sb.toString();


	}
	
	/*tell if it is a prec or eff and the number*/
	public String isPreOrEff(Oval o){
		
		StringBuilder sb = new StringBuilder();
		String cond=o.getCond();

		/*if is a action*/
		if(o.getNode()!= null) {
			Action a=o.getNode().getAction();
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
		}else if(o.getStateCanvas()!= null) {
			IStateCanvas stateCanv=o.getStateCanvas();
			if(stateCanv instanceof InitialStateCanvas) {
				for(int i=0;i<stateCanv.getState().getConds().size();i++) {
					if(stateCanv.getState().getConds().get(i).equals(cond)) {
						sb.append("eff/");
						int num=i+1;
						sb.append(num);
						return sb.toString();
					}
				}
				return sb.toString();
			}else if(stateCanv instanceof GoalStateCanvas) {
				
				for(int i=0;i<stateCanv.getState().getConds().size();i++) {
					if(stateCanv.getState().getConds().get(i).equals(cond)) {
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
	
	public String getNameAction(String string) {
		String name[]=string.split("\\(");	
		StringBuilder sb=new StringBuilder();
		sb.append(name[0]);
		return sb.toString();
	}
	

	
	
	public String getBend(LinkCanvas link) {
		StringBuilder sb=new StringBuilder();		
		sb.append("[bend ");
		
		//ask if is left o right
		if(link.getOval1().getP().y>link.getOval2().getP().y) {
			sb.append("right,");
		}else {
			sb.append("left,");
		}
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
		sb.append("("+convertInCm(node.getParent().getLocation().x)+",");
		sb.append(convertInCm(node.getParent().getLocation().y)+")");
		return sb.toString();
	}
	
	public String convertInCm(int x) {
		DecimalFormat df = new DecimalFormat("#.00");
	    String result = df.format(x*PIXEL_MEASUREMNT);
	    return result;
	    
	}
	
	
	
	
	
	
}
