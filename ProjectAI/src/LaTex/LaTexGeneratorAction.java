package LaTex;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import Action.Action;

/**
 * Generate the LateX code for the domain-actions.
 * @author nadir
 * */

public class LaTexGeneratorAction {
	
	Map<String, String> mapping;
	

	public LaTexGeneratorAction() {
	}
	
	

	
	
	public String getLatexActionCodeDomain(Action action) {
		StringBuilder sb = new StringBuilder();
		if(actionHasVariable(action)) {
			sb.append(generatAction(action));
			sb.append("\n");
			sb.append(generatActionNoop(action));
		}else {
			sb.append(generateActionWoVariable(action));
			sb.append("\n");
			sb.append(generateActionWoVariableE(action));
		}
		
		return sb.toString();
	}
	
	/*
	 * action with preconditions and effects representation 
	 */
	
	
	private boolean actionHasVariable(Action a) {
		boolean result=false;
		String name=a.getName();
		if(name.contains("(")&& name.contains(",")) {
			result=true;
		}
		
		return result;
	}
	
	
	private String generateActionWoVariable(Action a) {
		StringBuilder sb = new StringBuilder();
		String space="  ";
		sb.append("\\scheme");
		sb.append("{"+a.getName()+"}{0}{");
		sb.append("\n");
		sb.append(space+"text = " +"{\\textbf{"+(a.getName())+"}},"+"\n");
		sb.append(space+"pres = {");
		sb.append(getTextPrecEff(a.getPrec(),false)+"},"+"\n");
		sb.append(space+"effs = {");
		sb.append(getTextPrecEff(a.getEffect(),false)+"},"+"\n");
		if(a.getPrec().size()>0 && a.getPrec()!=null) {
			sb.append(space+"pre length = "+getLenghtPrecs(a)+"\n");

		}
		if(a.getEffect().size()>0 && a.getEffect()!=null) {
			sb.append(space+"eff length = "+getLenghtEffs(a)+"\n");
		}
		sb.append(space+"height = "+getHeigthRect(a)+"\n");
		sb.append(space+"width = "+getWidthRect(a)+"\n"+"}"+"\n");
		
		return sb.toString();

	}
	
	
	private String generateActionWoVariableE(Action a) {

		StringBuilder sb = new StringBuilder();
		String space = "  ";
		sb.append("\\scheme");
		sb.append("{" + a.getName() + "-noop}{0}{");
		sb.append("\n");
		sb.append(space + "text = " + "{\\textbf{" + (a.getName()) + "}}," + "\n");
		sb.append(space + "pres = {");
		sb.append(getTextPrecEffNoop(a.getPrec()) + "}," + "\n");
		sb.append(space + "effs = {");
		sb.append(getTextPrecEffNoop(a.getEffect()) + "}," + "\n");
		if (a.getPrec().size() > 0 && a.getPrec() != null) {
			sb.append(space + "pre length = " + getLenghtPrecsNoop(a) + "\n");
		}
		if (a.getEffect().size() > 0 && a.getEffect() != null) {
			sb.append(space + "eff length = " + getLenghtEffsNoop(a) + "\n");
		}
		sb.append(space + "height = " + getHeigthRect(a) + "\n");
		sb.append(space + "width = " + getWidthRect(a) + "\n" + "}" + "\n");

		return sb.toString();

	}

	public String generatAction(Action a) {
		StringBuilder sb = new StringBuilder();
		String space="  ";
		sb.append("\n");
		sb.append("\\scheme");
		sb.append(getScheme(a.getName(),false));
		sb.append("{");
		sb.append("\n");
		
		sb.append(space+"text = " +"{\\textbf"+getText(a.getName())+"},"+"\n");
		sb.append(space+"pres = {");
		sb.append(getTextPrecEff(a.getPrec(),true)+"},"+"\n");
		sb.append(space+"effs = {");
		sb.append(getTextPrecEff(a.getEffect(),true)+"},"+"\n");
		sb.append(space+"pre length = "+getLenghtPrecs(a)+"\n");
		sb.append(space+"eff length = "+getLenghtEffs(a)+"\n");
		sb.append(space+"height = "+getHeigthRect(a)+"\n");
		sb.append(space+"width = "+getWidthRect(a)+"\n"+"}"+"\n");

		return sb.toString();
	}
	
	
	public String generatActionNoop(Action a) {
		StringBuilder sb = new StringBuilder();
		String space="  ";
		sb.append("% PRIMITIVE");
		sb.append("\n");
		sb.append("\\scheme");
		sb.append(getScheme(a.getName(),true));
		sb.append("{");
		sb.append("\n");
		
		sb.append(space+"text = "+"{\\textbf"+getText(a.getName())+"},"+"\n");
		sb.append(space+"pres = {");
		sb.append(getTextPrecEffNoop(a.getPrec())+"},"+"\n");
		sb.append(space+"effs = {");
		sb.append(getTextPrecEffNoop(a.getEffect())+"},"+"\n");
		sb.append(space+"pre length = "+getLenghtPrecsNoop(a)+"\n");
		sb.append(space+"eff length = "+getLenghtEffsNoop(a)+"\n");
		sb.append(space+"height = "+getHeigthRect(a)+"\n");
		sb.append(space+"width = "+getWidthRect(a)+"\n"+"}"+"\n");

		return sb.toString();
	}
	
	
	
	public String getScheme(String string,boolean E) {
		String name[]=string.split("\\(");
		String variable[]=name[1].split("\\)");
		variable=variable[0].split(",");
		int num=variable.length;
		
		StringBuilder sb=new StringBuilder();
		sb.append("{"+name[0]);
		if(E) {
			sb.append("-noop");
		}
		sb.append("}");
		sb.append("{"+num+"}");

		
		return sb.toString();
	}
	
	public String getText(String string) {
		String name[]=string.split("\\(");
		String variable[]=name[1].split("\\)");
		variable=variable[0].split(",");
		int num=variable.length;
		mapping=new TreeMap<String,String>(String.CASE_INSENSITIVE_ORDER);
		for(int i=0;i<num;i++) {
			String key="#"+(i+1);
			mapping.put( variable[i],key);
		}
		
		String testo="";
		for(int i=0;i<num;i++) {
			testo +=mapping.get(variable[i])+",";
		}
		testo=testo.substring(0, testo.length()-1);
		
		StringBuilder sb=new StringBuilder();
		sb.append("{"+name[0]+"}");
		sb.append("("+testo+")");

		
		return sb.toString();
	}
	
	
	/*take the prec and affect actions and trasform into latex code*/
	public String getTextPrecEff(ArrayList<String> cond,boolean hasVariable) {
		String space="  ";
		StringBuilder sb=new StringBuilder();
		
		for(int i=0;i<cond.size();i++) {
			if(hasVariable) {
				sb.append("\n"+"\t"+getCond(cond.get(i)));
			}else {
				sb.append("\n"+"\t"+getCondWoVariable(cond.get(i)));
			}
			if(i<cond.size()-1) {
				sb.append(",");
			}
		}
		if(cond.size()>0) {
			sb.append("\n"+space);
		}
	
		return sb.toString();
	}
	
	public String getTextPrecEffNoop(ArrayList<String> cond) {
		String space="  ";
		StringBuilder sb=new StringBuilder();
		if(cond.size()>0) {
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
		}
		
		return sb.toString();
	}
	
	
	/*take the PRec/Eff and convert into correct format
	 * 
	 * at(Home) ---> at(#1)
	 */
	public String getCond(String string) {

		String testo = string;
		if(string.startsWith("¬")) {
			testo=testo.replace("¬","");
		}
        testo = mapping.get(testo);
		if(string.startsWith("¬")){
			testo=testo.replaceAll("¬", "");
			testo= "{$\\neg$}"+testo;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("{" + testo +"}");

		return sb.toString();
	}

	public String getCondWoVariable(String string) {

	
		
		if(string.startsWith("¬")){
			string=string.replaceAll("¬", "");
			string= "{$\\neg$}"+string;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(string);
		

		return sb.toString();
	}
	
	
	public String getWidthRect(Action a) {
		
		StringBuilder sb=new StringBuilder();
		if(a.isGlobalWidth()) {
			sb.append("\\WidthOfActions");
		}else {
			sb.append(a.getWidthRectInCm()+"cm");
		}
		return sb.toString();
	}
	
	public String getHeigthRect(Action a) {
		
		StringBuilder sb=new StringBuilder();
		if(a.isGlobalHeight()) {
			sb.append("\\HeightOfActions,");
		}else {
			sb.append(a.getHeightRectInCm()+"cm,");
		}
		return sb.toString();		
	}
	
	public String getLenghtPrecs(Action a) {
		
		StringBuilder sb = new StringBuilder();

		if (a.isGlobalPrec()) {
			sb.append("\\LengthsOfPrecs,");
		} else {
			sb.append(a.getLengthPrecInCm() + "cm,");
		}

		return sb.toString();		
	}
	
	public String getLenghtPrecsNoop(Action a) {
		
		StringBuilder sb=new StringBuilder();
		
			if(a.isGlobalEmptyPrec()) {
				sb.append("\\LengthsOfEmptyTasks,");
			}else {
				sb.append(a.getStandardLengthPrecInCm()+"cm,");
			}
		
		return sb.toString();		
	}
	
	public String getLenghtEffs(Action a) {

		StringBuilder sb = new StringBuilder();

		if (a.isGlobalEff()) {
			sb.append("\\LengthsOfEffs,");
		} else {
			sb.append(a.getLengthEffInCm() + "cm,");
		}

		return sb.toString();
	}
	
	public String getLenghtEffsNoop(Action a) {

		StringBuilder sb = new StringBuilder();

		if (a.isGlobalEmptyEff()) {
			sb.append("\\LengthsOfEmptyTasks,");
		} else {
			sb.append(a.getStandardLengthEffInCm() + "cm,");
		}

		return sb.toString();
	}

}
