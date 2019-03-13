package LaTex;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import Action.Action;



public class LaTexGeneratorAction {
	
	Map<String, String> mapping;
	

	public LaTexGeneratorAction() {
	}
	
	

	
	
	public String getLatexActionCodeDomain(Action action) {
		StringBuilder sb = new StringBuilder();
		sb.append(generatAction(action));
		sb.append("\n");
		sb.append(generatActionE(action));
		return sb.toString();
	}
	
	/*
	 * action with preconditions and effects representation 
	 */
	public String generatAction(Action a) {
		StringBuilder sb = new StringBuilder();
		String space="  ";
		sb.append("% PRIMITIVE");
		sb.append("\n");
		sb.append("\\scheme");
		sb.append(getScheme(a.getName(),false));
		sb.append("{");
		sb.append("\n");
		
		sb.append(space+"text="+"{\\textbf"+getText(a.getName())+"},"+"\n");
		sb.append(space+"pres = {");
		sb.append(getTextPrecEff(a.getPrec())+"},"+"\n");
		sb.append(space+"effs = {");
		sb.append(getTextPrecEff(a.getEffect())+"},"+"\n");
		sb.append(space+"pre length = "+a.getLengthPrecInCm()+"cm,"+"\n");
		sb.append(space+"eff length = "+a.getLengthEffInCm()+"cm,"+"\n");
		sb.append(space+"height = "+a.getHeightRectInCm()+"cm,"+"\n");
		sb.append(space+"width = "+a.getWidthRectInCm()+"cm"+"\n"+"}"+"\n");

		return sb.toString();
	}
	
	
	public String generatActionE(Action a) {
		StringBuilder sb = new StringBuilder();
		String space="  ";
		sb.append("% PRIMITIVE");
		sb.append("\n");
		sb.append("\\scheme");
		sb.append(getScheme(a.getName(),true));
		sb.append("{");
		sb.append("\n");
		
		sb.append(space+"text"+"{\\textbf"+getText(a.getName())+"},"+"\n");
		sb.append(space+"pres = {");
		sb.append(getTextPrecEffE(a.getPrec())+"},"+"\n");
		sb.append(space+"effs = {");
		sb.append(getTextPrecEffE(a.getEffect())+"},"+"\n");
		sb.append(space+"pre length = "+a.getStandardLengthPrecInCm()+"cm,"+"\n");
		sb.append(space+"eff length = "+a.getStandardLengthEffInCm()+"cm,"+"\n");
		sb.append(space+"height = "+a.getHeightRectInCm()+"cm,"+"\n");
		sb.append(space+"width = "+a.getWidthRectInCm()+"cm"+"\n"+"}"+"\n");

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
			sb.append("-E");
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
	public String getTextPrecEff(ArrayList<String> cond) {
		String space="  ";
		StringBuilder sb=new StringBuilder();
		
		for(int i=0;i<cond.size();i++) {
			sb.append("\n"+"\t"+getCond(cond.get(i)));
			if(i<cond.size()-1) {
				sb.append(",");
			}
		}
		if(cond.size()>0) {
			sb.append("\n"+space);
		}
	
		return sb.toString();
	}
	
	public String getTextPrecEffE(ArrayList<String> cond) {
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
	
	
	/*take the PRec/Eff and convert into correct format
	 * 
	 * at(Home) ---> at(#1)
	 */
	public String getCond(String string) {

		String name[] = string.split("\\(");
		String variable[] = name[1].split("\\)");
		variable = variable[0].split(",");
		int num = variable.length;
		String testo = "";
		for (int i = 0; i < num; i++) {
			testo += mapping.get(variable[i]) + ",";
		}
		testo = testo.substring(0, testo.length() - 1);

		if(name[0].charAt(0)=='¬'){
			name[0]=name[0].replaceAll("¬", "");
			name[0]= "{$\\neg$}"+name[0];
		}
		StringBuilder sb = new StringBuilder();
		sb.append("{"+name[0]);
		sb.append("(" + testo + ")"+"}");

		return sb.toString();
	}

	

	
	
	
}
