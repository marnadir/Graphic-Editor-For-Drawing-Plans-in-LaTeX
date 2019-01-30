package LaTex;

import java.util.ArrayList;

import State.IStateCanvas;

public class LaTexGeneratorStateDomain {
	

	public LaTexGeneratorStateDomain() {
	}
	
	public String getLatexSocode(IStateCanvas stateCanvas) {
		StringBuilder sb = new StringBuilder();
		sb.append(getSoCode(stateCanvas));
		sb.append("\n");
		sb.append(getSoCodeL(stateCanvas));
		return sb.toString();
	}
	
	public String getLatexGoalcode(IStateCanvas stateCanvas) {
		StringBuilder sb = new StringBuilder();
		sb.append(getGoalCode(stateCanvas));
		sb.append("\n");
		sb.append(getGoalCodeL(stateCanvas));
		return sb.toString();
	}
	
	/*1cm=37,7957517575025 pixel*/
	public String getSoCodeL(IStateCanvas stateCanvas) {
		StringBuilder sb = new StringBuilder();
		String space="  ";
		sb.append("% PRIMITIVE");
		sb.append("\n");
		sb.append("\\scheme");
		sb.append("{STARTL}{3}");
		sb.append("{");
		sb.append("\n");
		
		sb.append(space+"text"+"{\\hspace*"+"{-2mm}"+"},"+"\n");
		sb.append(space+"pres = {},"+"\n");
		sb.append(space+"eff = {");
		sb.append(getTextPrecEffL(stateCanvas.getState().getConds())+"},"+"\n");
		sb.append(space+"eff length =  "+stateCanvas.getStandardLengthInCm()+"cm,"+"\n");
		sb.append(space+"height = 5.5cm"+","+"\n");
		sb.append(space+"width = 1mm"+""+"\n"+"}"+"\n");

		return sb.toString();
	}
	
	public String getSoCode(IStateCanvas stateCanvas) {
		StringBuilder sb = new StringBuilder();
		String space="  ";
		sb.append("% PRIMITIVE");
		sb.append("\n");
		sb.append("\\scheme");
		sb.append("{START}{3}");
		sb.append("{");
		sb.append("\n");
		
		sb.append(space+"text"+"{\\hspace*"+"{-2mm}"+"},"+"\n");
		sb.append(space+"pres = {},"+"\n");
		sb.append(space+"effs = {");
		sb.append(getTextPrecEff(stateCanvas.getState().getConds())+"},"+"\n");
		sb.append(space+"eff length =  "+stateCanvas.getStandardLengthInCm()+"cm,"+"\n");
		sb.append(space+"height = 5.5cm"+","+"\n");
		sb.append(space+"width = 1mm"+""+"\n"+"}"+"\n");

		return sb.toString();
	}
	
	public String getGoalCodeL(IStateCanvas stateCanvas) {
		StringBuilder sb = new StringBuilder();
		String space="  ";
		sb.append("% PRIMITIVE");
		sb.append("\n");
		sb.append("\\scheme");
		sb.append("{GOALL}{3}");
		sb.append("{");
		sb.append("\n");
		
		sb.append(space+"text"+"{\\hspace*"+"{-2mm}"+"},"+"\n");
		sb.append(space+"pres = {},"+"\n");
		sb.append(space+"eff = {");
		sb.append(getTextPrecEffL(stateCanvas.getState().getConds())+"},"+"\n");
		sb.append(space+"eff length =  "+stateCanvas.getStandardLengthInCm()+"cm,"+"\n");
		sb.append(space+"height = 5.5cm"+","+"\n");
		sb.append(space+"width = 1mm"+""+"\n"+"}"+"\n");

		return sb.toString();
	}
	
	public String getGoalCode(IStateCanvas stateCanvas) {
		StringBuilder sb = new StringBuilder();
		String space="  ";
		sb.append("% PRIMITIVE");
		sb.append("\n");
		sb.append("\\scheme");
		sb.append("{GOAL}{3}");
		sb.append("{");
		sb.append("\n");
		
		sb.append(space+"text"+"{\\hspace*"+"{-2mm}"+"},"+"\n");
		sb.append(space+"pres = {},"+"\n");
		sb.append(space+"effs = {");
		sb.append(getTextPrecEff(stateCanvas.getState().getConds())+"},"+"\n");
		sb.append(space+"eff length =  "+stateCanvas.getStandardLengthInCm()+"cm,"+"\n");
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

	
}
