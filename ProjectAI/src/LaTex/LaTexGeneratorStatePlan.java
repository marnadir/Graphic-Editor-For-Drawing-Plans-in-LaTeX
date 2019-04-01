package LaTex;

import java.text.DecimalFormat;
import java.util.ArrayList;

import PlanPart.PlanContent;
import State.GoalStateCanvas;
import State.IState;
import State.IStateCanvas;
import State.InitialState;
import State.InitialStateCanvas;

public class LaTexGeneratorStatePlan {
	
	final double PIXEL_MEASUREMNT= 0.026458;

	
	public LaTexGeneratorStatePlan() {
		// TODO Auto-generated constructor stub
	}
	
	public String getLatexPlanCode(PlanContent graphContent) {
		StringBuilder sb = new StringBuilder();
		InitialStateCanvas initialStateCanvas=graphContent.getInitialStateCanvas();
		GoalStateCanvas goalStateCanvas=graphContent.getGoalStateCanvas();
		if(initialStateCanvas!=null) {
			if(initialStateCanvas.getState().isText()) {
				sb.append(generatexTogheter(graphContent));
				return sb.toString();

			}else {
				sb.append(generatexSo(initialStateCanvas));
				sb.append("\n");
				if(goalStateCanvas!=null) {
					sb.append(generatexGoal(goalStateCanvas));
					sb.append("\n");
					return sb.toString();
				}

			}
		}else {
			if(goalStateCanvas!=null) {
				sb.append(generatexGoal(goalStateCanvas));
				sb.append("\n");
			}
		}
		
		
		return sb.toString();
	}
	
	
	private String generatexTogheter(PlanContent graphContent) {
		StringBuilder sb = new StringBuilder();
		InitialStateCanvas initialStateCanvas=graphContent.getInitialStateCanvas();
		GoalStateCanvas goalStateCanvas =graphContent.getGoalStateCanvas();

		sb.append("\\stage{35em}{26em}"+"\n");
		sb.append("{effs="+getEffPrec(initialStateCanvas.getState())+"}"+"\n");
		sb.append("{pres="+getEffPrec(goalStateCanvas.getState())+"}"+"\n");
		
		sb.append("{"+initialStateCanvas.getState().getText()+"}");
		sb.append("{"+goalStateCanvas.getState().getText()+"}"+"\n");
		
		return sb.toString();
		
	}
	
	
	private String generatexSo(InitialStateCanvas initialStateCanvas) {
		StringBuilder sb = new StringBuilder();
		sb.append("\\action{start}{");
		if(initialStateCanvas.getState().isShownCond()) {
			sb.append("STARTL,");
		}else {
			sb.append("START,");
		}
		
		
		sb.append("body = {fill=black,");
		sb.append("at={"+getPosition(initialStateCanvas)+"}}}");
		
		return sb.toString();
		
	} 
	
	
	private String generatexGoal(GoalStateCanvas goalStateCanvas) {
		StringBuilder sb = new StringBuilder();
		sb.append("\\action{goal}{");
		if(goalStateCanvas.getState().isShownCond()) {
			sb.append("GOALL,");
		}else {
			sb.append("GOAL,");
		}
		
		
		sb.append("body = {fill=black,");
		sb.append("at={"+getPosition(goalStateCanvas)+"}}}");
		
		return sb.toString();
		
	}
	
	
	private String getEffPrec(IState iState) {
		StringBuilder sb = new StringBuilder();
		if(iState.isShownCond()) {
			sb.append(getTextPrecEff(iState.getConds()));

		}else {
			sb.append(getTextPrecEffE(iState.getConds()));
		}
		if(iState instanceof InitialState) {
			sb.append(",eff length  = ");

		}else {
			sb.append(",pre length  = ");

		}
		
		if(iState.isShownCond()) {
			sb.append(iState.getLengthCondInCm()+"em");
		}else {
			sb.append(iState.getStandardLengthInCm()+"em");
		}
		
		return sb.toString();

	}
	
	private String getTextPrecEff(ArrayList<String> cond) {
		StringBuilder sb=new StringBuilder();
		sb.append("{");

		for(int i=0;i<cond.size();i++) {
			sb.append(cond.get(i));
			if(i<cond.size()-1) {
				sb.append(",");
			}
		}
		sb.append("}");

		return sb.toString();
	}
	
	
	
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
		sb.append("("+convertInCm(iState.getParent().getLocation().x)+",");
		sb.append(convertInCm(iState.getParent().getParent().getSize().y-iState.getParent().getLocation().y)+")");
		return sb.toString();
	}
	
	private String convertInCm(int x) {
		DecimalFormat df = new DecimalFormat("#.00");
	    String result = df.format(x*PIXEL_MEASUREMNT);
	    return result;
	    
	}
}
