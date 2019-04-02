package LaTex;

import java.text.DecimalFormat;


import Action.Action;
import Action.ICanvasAction;
import Action.Node;
import PlanPart.LinkCanvas;
import PlanPart.OrderConstrain;
import PlanPart.Oval;
import PlanPart.PlanContent;
import State.GoalStateCanvas;
import State.IStateCanvas;
import State.InitialStateCanvas;

public class LaTexGeneratorNode {

	final double PIXEL_MEASUREMNT= 0.026458;
	final double CM_MEASUREMNT= 37.7957517575025;
	PlanContent planContent;
	
	
	public LaTexGeneratorNode(PlanContent planContent) {
		this.planContent=planContent;
	}
	
	public String getLatexActionCodePlan(Action action,Node node) {
		StringBuilder sb = new StringBuilder();
		sb.append("\\action");
		sb.append("{"+node.getID()+"}");//numerare le azioni
		sb.append("{"+getNameAction(action));
		if(actionHasVariable(action))  {
			sb.append("="+getVariable(action.getName()));
		}
		sb.append(","+"\n");
		sb.append("  body="+"{");
		sb.append(isFillColor(action));
		sb.append(isRound(action));
		sb.append(isFett(action));
		//we need to take care of init/goal
		if(planContent.getInitialStateCanvas().getState().isText()) {
			sb.append(" below right="+getPositionToInit(node)+"}"+"\n"+"}"+"\n");

		}else {
			sb.append("at={"+getPosition(node)+"}}"+"\n"+"}"+"\n");

		}
		

		return sb.toString();
	}
	
	public String getLatexLinkCodePlan(LinkCanvas link) {
		StringBuilder sb = new StringBuilder();
		sb.append("\\link");
		
		sb.append("{"+isStateorAction(link.getOval1())+"/"+isPreOrEff(link.getOval1())+"}");
		sb.append("{"+isStateorAction(link.getOval2())+"/"+isPreOrEff(link.getOval2())+"}");
		sb.append("{edge"+getBend(link)+"}"+"\n"+"\n");
		return sb.toString();
	}
	
	
	public String getLatexOrderCodePlan(OrderConstrain order) {
		StringBuilder sb = new StringBuilder();
		sb.append("\\ordering");
		sb.append("{[yshift=.65cm,xshift=.2cm]"+order.getCond1().getID()+".east}");
		sb.append("{[yshift=.65cm,xshift=-.2cm]"+order.getCond2().getID()+".west}"+"\n"+"\n");
		return sb.toString();
	}
	
	private String isFillColor(Action a) {
		StringBuilder sb=new StringBuilder();
		if(a.isFillColor()) {
			sb.append("fill="+a.getColorString()+",");
		}
		return sb.toString();
	}
	
	private String isRound(Action a) {
		StringBuilder sb=new StringBuilder();
		if(a.isRectRound()) {
			sb.append("rounded corners,");
		}
		return sb.toString();

		
	}
	
	private String isFett(Action a) {
		StringBuilder sb=new StringBuilder();
		if(a.isFett()) {
			sb.append("fett,");
		}
		return sb.toString();

		
	}
	
	
	private String isPrimitive(Action a) {
		StringBuilder sb=new StringBuilder();
		if(a.isPrimitive()) {
			sb.append("primitive"+",");
		}else {
			sb.append("abstract"+",");
		}
		return sb.toString();
	}
	
	private String isStateorAction(Oval o) {
		StringBuilder sb = new StringBuilder();
		if(o.getNode()!= null) {
			sb.append(o.getNode().getID());
		}else if(o.getStateCanvas()!=null) {
			if(o.getStateCanvas() instanceof InitialStateCanvas) {
				if(o.getStateCanvas().getState().isText()) {
					sb.append("init");
				}else {
					sb.append("start");
				}
			}else if(o.getStateCanvas() instanceof GoalStateCanvas)  {
					sb.append("goal");
			
			}
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
	
	public String getNameAction(Action action) {
		String string=action.getName();
		String name[]=string.split("\\(");	
		StringBuilder sb=new StringBuilder();
		sb.append(name[0]);
		if(!action.isShownCond()) {
			sb.append("-E");
		}
		
		return sb.toString();
	}
	

	
	
	public String getBend(LinkCanvas link) {
		StringBuilder sb=new StringBuilder();		
		sb.append("[bend ");
		
		//ask if is left o right
		if(link.isRight()) {
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
			testo +="{$"+variable[i]+"$}";
		}
		
		StringBuilder sb=new StringBuilder();
		sb.append(testo);

		
		return sb.toString();
	}
	
	private String getPositionToInit(ICanvasAction node) {
		StringBuilder sb=new StringBuilder();	
		int y=(node.getParent().getLocation().y)-(planContent.getInitialStateCanvas().getParent().getLocation().y);
		sb.append("("+convertInCm(y)+"cm and ");
		int x=node.getParent().getLocation().x-planContent.getInitialStateCanvas().getParent().getLocation().x;
		sb.append(convertInCm(x)+"cm of init.north east");
		
		return sb.toString();

		
	}
	
	
	private String getPosition(ICanvasAction node) {
		StringBuilder sb=new StringBuilder();		
		sb.append("("+convertInCm(node.getParent().getLocation().x)+",");
		sb.append(convertInCm(node.getParent().getParent().getSize().y-node.getParent().getLocation().y)+")");
		return sb.toString();
	}
	
	public String convertInCm(int x) {
		DecimalFormat df = new DecimalFormat("#.00");
	    String result = df.format(x*PIXEL_MEASUREMNT);
	    return result;
	    
	}
	
	
	private boolean actionHasVariable(Action a) {
		boolean result=false;
		String name=a.getName();
		if(name.contains("(")&& name.contains(",")) {
			result=true;
		}
		
		return result;
	
	}
	
	
	
}
