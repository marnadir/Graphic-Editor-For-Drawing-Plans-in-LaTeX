package LaTex;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;

import Action.Action;
import State.IState;

public class LaTexGeneratorStateDomain {
	

	public LaTexGeneratorStateDomain() {
	}
	
	public String getLatexSocode(IState stateCanvas) {
		StringBuilder sb = new StringBuilder();
		sb.append(getSoCode(stateCanvas));
		sb.append("\n");
		sb.append(getSoCodeL(stateCanvas));
		return sb.toString();
	}
	
	public String getLatexGoalcode(IState stateCanvas) {
		StringBuilder sb = new StringBuilder();
		sb.append(getGoalCode(stateCanvas));
		sb.append("\n");
		sb.append(getGoalCodeL(stateCanvas));
		return sb.toString();
	}
	
	/*1cm=37,7957517575025 pixel*/
	
	/*
	 * get the source code for STARTL, which represents  conditions 
	 */
	public String getSoCodeL(IState stateCanvas) {
		StringBuilder sb = new StringBuilder();
		String space="  ";
		sb.append("% PRIMITIVE");
		sb.append("\n");
		sb.append("\\scheme");
		sb.append("{STARTL}{3}");
		sb.append("{");
		sb.append("\n");
		
		sb.append(space+"text="+"{\\hspace*"+"{-2mm}"+"},"+"\n");
		sb.append(space+"pres = {},"+"\n");
		sb.append(space+"effs = {");
		sb.append(getTextPrecEffL(stateCanvas.getConds())+"},"+"\n");
		sb.append(space+"eff length =  "+getLenghtCondL(stateCanvas)+"\n");
		sb.append(space+"height = 5.5cm"+","+"\n");
		sb.append(space+"width = 1mm"+""+"\n"+"}"+"\n");

		return sb.toString();
	}
	
	/*
	 * get the source code for STARTL, which represents  just the empty conditions 
	 */
	public String getSoCode(IState state) {
		StringBuilder sb = new StringBuilder();
		String space="  ";
		sb.append("% PRIMITIVE");
		sb.append("\n");
		sb.append("\\scheme");
		sb.append("{START}{3}");
		sb.append("{");
		sb.append("\n");
		
		sb.append(space+"text="+"{\\hspace*"+"{-2mm}"+"},"+"\n");
		sb.append(space+"pres = {},"+"\n");
		sb.append(space+"effs = {");
		sb.append(getTextPrecEff(state.getConds())+"},"+"\n");
		sb.append(space+"eff length =  "+getLenghtConds(state)+"\n");
		sb.append(space+"height = 5.5cm"+","+"\n");
		sb.append(space+"width = 1mm"+""+"\n"+"}"+"\n");

		return sb.toString();
	}
	
	public String getGoalCodeL(IState state) {
		StringBuilder sb = new StringBuilder();
		String space="  ";
		sb.append("% PRIMITIVE");
		sb.append("\n");
		sb.append("\\scheme");
		sb.append("{GOALL}{3}");
		sb.append("{");
		sb.append("\n");
		
		sb.append(space+"text="+"{\\hspace*"+"{-2mm}"+"},"+"\n");
		sb.append(space+"pres = {");
		sb.append(getTextPrecEffL(state.getConds())+"},"+"\n");
		sb.append(space+"effs = {},"+"\n");
		sb.append(space+"eff length =  "+getLenghtCondL(state)+"\n");
		sb.append(space+"height = 5.5cm"+","+"\n");
		sb.append(space+"width = 1mm"+""+"\n"+"}"+"\n");

		return sb.toString();
	}
	
	public String getGoalCode(IState state) {
		StringBuilder sb = new StringBuilder();
		String space="  ";
		sb.append("% PRIMITIVE");
		sb.append("\n");
		sb.append("\\scheme");
		sb.append("{GOAL}{3}");
		sb.append("{");
		sb.append("\n");
		
		sb.append(space+"text="+"{\\hspace*"+"{-2mm}"+"},"+"\n");
		sb.append(space+"pres = {");
		sb.append(getTextPrecEff(state.getConds())+"},"+"\n");
		sb.append(space+"effs = {},"+"\n");
		sb.append(space+"eff length =  "+getLenghtConds(state)+"\n");
		sb.append(space+"height = 5.5cm"+","+"\n");
		sb.append(space+"width = 1mm"+""+"\n"+"}"+"\n");

		return sb.toString();
	}
	
	


	/*take the prec and affect actions and trasform into latex code*/
	public String getTextPrecEff(ArrayList<String> cond) {
		String space="  ";
		StringBuilder sb=new StringBuilder();
		sb.append("\n"+"\t");
		for(int i=0;i<cond.size();i++) {
			sb.append("{}");
			if(i<cond.size()-1) {
				sb.append(",");
			}
		}
		if(cond.size()>0) {
			sb.append("\n"+space);
		}
		return sb.toString();
	}
	
	public String getTextPrecEffL(ArrayList<String> cond) {
		String space="  ";
		StringBuilder sb=new StringBuilder();
		
		for(int i=0;i<cond.size();i++) {
			sb.append("\n"+"\t");
			sb.append("{{\\footnotesize ");
			String text = cond.get(i);

			if(text.startsWith("¬")){
				text=text.replaceAll("¬", "");
				text= "{$\\neg$}"+text;
			}
			text=text.replace("(", "($");
			
			text=text.replace(")","$)");
			sb.append(text+"}}");
			//cond.get(i)+"}}"
			if(i<cond.size()-1) {
				sb.append(",");
			}
		}
		if(cond.size()>0) {
			sb.append("\n"+space);
		}
		return sb.toString();
	}

	
	public String getLenghtConds(IState state) {

		StringBuilder sb = new StringBuilder();

		if (state.isGlobalCond()) {
			sb.append("\\LenghtOfConds");
		} else {
			sb.append(state.getLengthCondInCm() + "cm,");
		}

		return sb.toString();
	}
	
	public String getLenghtCondL(IState state) {

		StringBuilder sb = new StringBuilder();

		if (state.isGlobalEmpty()) {
			sb.append("\\LengthsOfEmptyTasks");
		} else {
			sb.append(state.getStandardLengthInCm() + "cm,");
		}

		return sb.toString();
	}
	
	
}
