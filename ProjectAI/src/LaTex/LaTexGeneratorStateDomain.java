package LaTex;

import java.util.ArrayList;
import State.IState;

public class LaTexGeneratorStateDomain {
	

	public LaTexGeneratorStateDomain() {
	}
	
	public String getLatexSocode(IState stateCanvas) {
		StringBuilder sb = new StringBuilder();
		sb.append(getSoCodeNoop(stateCanvas));
		sb.append("\n");
		sb.append(getSoCode(stateCanvas));
		return sb.toString();
	}
	
	public String getLatexGoalcode(IState stateCanvas) {
		StringBuilder sb = new StringBuilder();
		sb.append(getGoalCodeNoop(stateCanvas));
		sb.append("\n");
		sb.append(getGoalCode(stateCanvas));
		return sb.toString();
	}
	
	/*1cm=37,7957517575025 pixel*/
	
	/*
	 * get the source code for INIT which represents  conditions 
	 */
	public String getSoCode(IState state) {
		StringBuilder sb = new StringBuilder();
		String space="  ";
		sb.append("% PRIMITIVE");
		sb.append("\n");
		sb.append("\\scheme");
		sb.append("{INIT}{3}");
		sb.append("{");
		sb.append("\n");
		
		sb.append(space+"text="+"{\\hspace*"+"{-2mm}"+"},"+"\n");
		sb.append(space+"pres = {},"+"\n");
		sb.append(space+"effs = {");
		sb.append(getTextPrecEff(state.getConds())+"},"+"\n");
		sb.append(space+"eff length =  "+getLenghtCond(state)+"\n");
		sb.append(space+"height = "+state.getHeiInCm()+"cm,"+"\n");
		sb.append(space+"width = "+state.getWidInCm()+"cm \n"+"}"+"\n");

		return sb.toString();
	}
	
	/*
	 * get the source code for INIT-noop,, which represents  just the empty conditions 
	 */
	public String getSoCodeNoop(IState state) {
		StringBuilder sb = new StringBuilder();
		String space="  ";
		sb.append("% PRIMITIVE");
		sb.append("\n");
		sb.append("\\scheme");
		sb.append("{INIT-noop}{3}");
		sb.append("{");
		sb.append("\n");
		
		sb.append(space+"text="+"{\\hspace*"+"{-2mm}"+"},"+"\n");
		sb.append(space+"pres = {},"+"\n");
		sb.append(space+"effs = {");
		sb.append(getTextPrecEffNoop(state.getConds())+"},"+"\n");
		sb.append(space+"eff length =  "+getLenghtCondsNoop(state)+"\n");
		sb.append(space+"height = "+state.getHeiInCm()+"cm,"+"\n");
		sb.append(space+"width = "+state.getWidInCm()+"cm \n"+"}"+"\n");

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
		sb.append(space+"pre length =  "+getLenghtCond(state)+"\n");
		sb.append(space+"height = "+state.getHeiInCm()+"cm,"+"\n");
		sb.append(space+"width = "+state.getWidInCm()+"cm \n"+"}"+"\n");

		return sb.toString();
	}
	
	public String getGoalCodeNoop(IState state) {
		StringBuilder sb = new StringBuilder();
		String space="  ";
		sb.append("% PRIMITIVE");
		sb.append("\n");
		sb.append("\\scheme");
		sb.append("{GOAL-noop}{3}");
		sb.append("{");
		sb.append("\n");
		
		sb.append(space+"text="+"{\\hspace*"+"{-2mm}"+"},"+"\n");
		sb.append(space+"pres = {");
		sb.append(getTextPrecEffNoop(state.getConds())+"},"+"\n");
		sb.append(space+"effs = {},"+"\n");
		sb.append(space+"pre length =  "+getLenghtCondsNoop(state)+"\n");
		sb.append(space+"height = "+state.getHeiInCm()+"cm,"+"\n");
		sb.append(space+"width = "+state.getWidInCm()+"cm \n"+"}"+"\n");

		return sb.toString();
	}
	
	


	/*take the prec and affect actions and trasform into latex code*/
	public String getTextPrecEffNoop(ArrayList<String> cond) {
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
	
	public String getTextPrecEff(ArrayList<String> cond) {
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

	
	public String getLenghtCond(IState state) {

		StringBuilder sb = new StringBuilder();

		if (state.isGlobalCond()) {
			sb.append("\\LenghtOfConds");
		} else {
			sb.append(state.getLengthCondInCm() + "cm,");
		}

		return sb.toString();
	}
	
	public String getLenghtCondsNoop(IState state) {

		StringBuilder sb = new StringBuilder();

		if (state.isGlobalEmpty()) {
			sb.append("\\LengthsOfEmptyTasks");
		} else {
			sb.append(state.getStandardLengthInCm() + "cm,");
		}

		return sb.toString();
	}
	
	
}
