package LaTex;

import java.text.DecimalFormat;
import java.util.ArrayList;

import GraphPart.GraphContent;
import State.GoalStateCanvas;
import State.IStateCanvas;
import State.InitialStateCanvas;

public class LaTexGeneratorStatePlan {
	
	final double PIXEL_MEASUREMNT= 0.026458;

	
	public LaTexGeneratorStatePlan() {
		// TODO Auto-generated constructor stub
	}
	
	public String getLatexPlanCode(GraphContent graphContent) {
		StringBuilder sb = new StringBuilder();
		InitialStateCanvas initialStateCanvas=graphContent.getInitialStateCanvas();
		GoalStateCanvas goalStateCanvas=graphContent.getGoalStateCanvas();
		if(initialStateCanvas.isText()) {
			sb.append(generatexTogheter(graphContent));
		}else {
			sb.append(generatexSo(initialStateCanvas));
			sb.append("\n");
			sb.append(generatexGoal(goalStateCanvas));
			sb.append("\n");

		}
		
		return sb.toString();
	}
	
	
	private String generatexTogheter(GraphContent graphContent) {
		StringBuilder sb = new StringBuilder();
		InitialStateCanvas initialStateCanvas=graphContent.getInitialStateCanvas();
		GoalStateCanvas goalStateCanvas =graphContent.getGoalStateCanvas();

		sb.append("//stage{35em}{26em}");
		sb.append("{effs="+getEffPrec(initialStateCanvas));
		sb.append("{pres="+getEffPrec(goalStateCanvas));
		
		sb.append("{"+initialStateCanvas.getText()+"}");
		sb.append("{"+goalStateCanvas.getText()+"}");
		
		return sb.toString();
		
	}
	
	
	private String generatexSo(InitialStateCanvas initialStateCanvas) {
		StringBuilder sb = new StringBuilder();
		sb.append("/action{start}{");
		if(initialStateCanvas.isShownCond()) {
			sb.append("START,");
		}else {
			sb.append("STARTL,");
		}
		
		
		sb.append("body = {fill=black,");
		sb.append("at={"+getPosition(initialStateCanvas)+"}}}");
		
		return sb.toString();
		
	} 
	
	
	private String generatexGoal(GoalStateCanvas goalStateCanvas) {
		StringBuilder sb = new StringBuilder();
		sb.append("/action{start}{");
		if(goalStateCanvas.isShownCond()) {
			sb.append("GOAL,");
		}else {
			sb.append("GOALL,");
		}
		
		
		sb.append("body = {fill=black,");
		sb.append("at={"+getPosition(goalStateCanvas)+"}}}");
		
		return sb.toString();
		
	}
	
	
	private String getEffPrec(IStateCanvas iStateCanvas) {
		StringBuilder sb = new StringBuilder();
		if(iStateCanvas.isShownCond()) {
			sb.append(getTextPrecEff(iStateCanvas.getState().getConds()));

		}else {
			sb.append(getTextPrecEffE(iStateCanvas.getState().getConds()));
		}
		if(iStateCanvas instanceof InitialStateCanvas) {
			sb.append(",eff lenght  = ");

		}else {
			sb.append(",pre lenght  = ");

		}
		
		if(iStateCanvas.isShownCond()) {
			sb.append(iStateCanvas.getLengthCondInCm()+"em");
		}else {
			sb.append(iStateCanvas.getStandardLengthInCm()+"em");
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
		sb.append(convertInCm(iState.getParent().getLocation().y)+")");
		return sb.toString();
	}
	
	private String convertInCm(int x) {
		DecimalFormat df = new DecimalFormat("#.00");
	    String result = df.format(x*PIXEL_MEASUREMNT);
	    return result;
	    
	}
}