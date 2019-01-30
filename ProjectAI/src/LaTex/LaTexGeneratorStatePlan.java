package LaTex;

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
	
	public String getLatexSocode(GraphContent graphContent) {
		StringBuilder sb = new StringBuilder();
		InitialStateCanvas initialStateCanvas=graphContent.getInitialStateCanvas();
		if(initialStateCanvas.isText()) {
			generatexTogheter(graphContent);
		}
		
		return sb.toString();
	}
	
	
	public String generatexTogheter(GraphContent graphContent) {
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
	
	
	public String generatexSo(InitialStateCanvas initialStateCanvas) {
		StringBuilder sb = new StringBuilder();
		sb.append("/action{start}{");
		if(initialStateCanvas.isShownCond()) {
			sb.append("START,");
		}else {
			sb.append("STARTL,");
		}
		
		
		sb.append("body = {fill=black,");
		
		
		return sb.toString();
		
	} 
	
	
	
	public String getEffPrec(IStateCanvas iStateCanvas) {
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
	
	public String getTextPrecEff(ArrayList<String> cond) {
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
	
	
	
	public String getTextPrecEffE(ArrayList<String> cond) {

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
}
