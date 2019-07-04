package LaTex;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import PlanPart.PlanContent;
import so_goalState.GoalStateCanvas;
import so_goalState.IState;
import so_goalState.IStateCanvas;
import so_goalState.InitialState;
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
		sb.append("\\action{start}{");
		if(initialStateCanvas.getState().isShownCond()) {
			sb.append("INIT,");
		}else {
			sb.append("INIT-noop,");
		}
		
		if(initialStateCanvas.getState().isText()) {
			sb.append("body = {fill=white,");

		}else {
			sb.append("body = {fill=black,");
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
		if(goalStateCanvas.getState().isText()) {
			sb.append("body = {fill=white,");

		}else {
			sb.append("body = {fill=black,");
		}
				sb.append("at={"+getPosition(goalStateCanvas)+"}}}");
		
		return sb.toString();
		
	}
	
	
//	private String getEffPrec(IState iState) {
//		StringBuilder sb = new StringBuilder();
//		if (iState.isShownCond()) {
//			sb.append(getTextPrecEff(iState.getConds()));
//
//		} else {
//			sb.append(getTextPrecEffE(iState.getConds()));
//		}
//		if (iState instanceof InitialState) {
//			sb.append(",eff length  = ");
//
//		} else {
//			sb.append(",pre length  = ");
//
//		}
//
//		if (iState.isShownCond()) {
//			sb.append(iState.getLengthCondInCm() + "em");
//		} else {
//			sb.append(iState.getStandardLengthInCm() + "em");
//		}
//
//		return sb.toString();
//	}
//	
//	private String getTextPrecEff(ArrayList<String> cond) {
//		StringBuilder sb=new StringBuilder();
//		sb.append("{");
//
//		for(int i=0;i<cond.size();i++) {
//			sb.append(cond.get(i));
//			if(i<cond.size()-1) {
//				sb.append(",");
//			}
//		}
//		sb.append("}");
//
//		return sb.toString();
//	}
	
	
	
	private String getTextPrecEffE(ArrayList<String> cond) {

		StringBuilder sb=new StringBuilder();
		sb.append("{");
		for(int i=0;i<cond.size();i++) {
			if(i<cond.size()-1) {
				sb.append(",");
			}
		}
		sb.append("}");

		return sb.toString();
	}
	
	private String getPosition(IStateCanvas iState) {
		StringBuilder sb=new StringBuilder();	
		if(iState instanceof InitialStateCanvas) {
			sb.append("(0,");

		}else {
			sb.append("("+convertInCm( (iState.getParent().getLocation().x)-(iState.getParent().getBounds().width)/2)+",");

		}
//		sb.append(convertInCm(iState.getParent().getParent().getSize().y-iState.getParent().getLocation().y)+")");
		sb.append("0)");	
		return sb.toString();
	}
	
	private String convertInCm(int x) {
//		DecimalFormat df = new DecimalFormat("#.00");
//	    String result = df.format(x*PIXEL_MEASUREMNT);
		NumberFormat nf_out = NumberFormat.getNumberInstance(Locale.UK);
		nf_out.setMaximumFractionDigits(2);
		String result = nf_out.format(x*PIXEL_MEASUREMNT);
	    return result;
	    
	}
}
