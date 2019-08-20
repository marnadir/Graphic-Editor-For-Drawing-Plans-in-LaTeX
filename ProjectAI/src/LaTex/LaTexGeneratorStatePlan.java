package LaTex;

import java.text.NumberFormat;
import java.util.Locale;
import PlanPart.PlanContent;
import so_goalState.GoalStateCanvas;
import so_goalState.IState;
import so_goalState.IStateCanvas;
import so_goalState.InitialStateCanvas;
/**
 * Generate the LateX code for the initial/goal state dropped in the plan  view.
 * @author nadir
 * */
public class LaTexGeneratorStatePlan {
	
	final double PIXEL_MEASUREMNT= 0.026458;

	
	public LaTexGeneratorStatePlan() {
		// TODO Auto-generated constructor stub
	}
	
	public String getLatexPlanCode(PlanContent graphContent) {
		StringBuilder sb = new StringBuilder();
		InitialStateCanvas initialStateCanvas=graphContent.getInitialStateCanvas();
		GoalStateCanvas goalStateCanvas=graphContent.getGoalStateCanvas();
		if(initialStateCanvas != null) {
			sb.append(generatexSo(initialStateCanvas));
			sb.append("\n");

		}
		if(goalStateCanvas != null) {
			sb.append(generatexGoal(goalStateCanvas));
			sb.append("\n");

		}
		return sb.toString();
	}
	
	
	
	private String generatexSo(InitialStateCanvas initialStateCanvas) {
		StringBuilder sb = new StringBuilder();
		sb.append("\\action{init}{");
		if(initialStateCanvas.getState().isShownCond()) {
			sb.append("INIT,");
		}else {
			sb.append("INIT-noop,");
		}
		
		sb.append("body = {");
		
		if(initialStateCanvas.getState().isText()) {
			sb.append(isFillColor(initialStateCanvas.getState()));

		}else {
			sb.append("fill=black,");
		}
		sb.append("at={"+getPosition(initialStateCanvas)+"}}}");
		
		return sb.toString();
		
	} 
	
	
	private String generatexGoal(GoalStateCanvas goalStateCanvas) {
		StringBuilder sb = new StringBuilder();
		sb.append("\\action{goal}{");
		if(goalStateCanvas.getState().isShownCond()) {
			sb.append("GOAL,");
		}else {
			sb.append("GOAL-noop,");
		}
		sb.append("body = {");
		if(goalStateCanvas.getState().isText()) {
			sb.append(isFillColor(goalStateCanvas.getState()));
		}else {
			sb.append("fill=black,");
		}
				sb.append("at={"+getPosition(goalStateCanvas)+"}}}");
		
		return sb.toString();
		
	}
	
	
	private String isFillColor(IState state) {
		StringBuilder sb=new StringBuilder();
		if(state.isFillColor()) {
			sb.append("fill="+state.getColor().toLowerCase()+",");
		}else {
				sb.append("fill= white,");
			
		}
		return sb.toString();
	}
	
	private String getPosition(IStateCanvas iState) {
		StringBuilder sb=new StringBuilder();	
		if(iState instanceof InitialStateCanvas) {
			sb.append("(0,");

		}else {
			sb.append("("+convertInCm( (iState.getParent().getLocation().x)+(iState.getParent().getBounds().width)/3)+",");

		}
		sb.append("0)");	
		return sb.toString();
	}
	
	private String convertInCm(int x) {
		NumberFormat nf_out = NumberFormat.getNumberInstance(Locale.UK);
		nf_out.setMaximumFractionDigits(2);
		String result = nf_out.format(x*PIXEL_MEASUREMNT);
	    return result;
	    
	}
}
