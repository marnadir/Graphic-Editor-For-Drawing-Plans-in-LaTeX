package LaTex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import logic.Action;

public class LaTexGenerator {
	
	Map<String, String> mapping;
	
	public static void main(String[] args) {
		LaTexGenerator l=new LaTexGenerator();
		ArrayList<String> prec=new ArrayList<>();
		prec.add("at(v,l)");
		prec.add("at(p,l)");
		
		ArrayList<String> eff=new ArrayList<>();
		eff.add("in(p,v)");
		eff.add("¬at(p,l)");
		
		Action a=new Action("Pick-Up(v,l,p)",prec , eff);
		
		System.out.println(l.generatAction(a));
		
	}
	public LaTexGenerator() {
		// TODO Auto-generated constructor stub
	}
	
	public String generatAction(Action a) {
		StringBuilder sb = new StringBuilder();
		String space="  ";
		sb.append("% PRIMITIVE");
		sb.append("\n");
		sb.append("\\scheme");
		sb.append(getScheme(a.getName()));
		sb.append("{");
		sb.append("\n");
		
		sb.append(space+"text"+"{/textit"+getText(a.getName())+"},"+"\n");
		sb.append(space+"pres = {");
		sb.append(getConds(a.getPrec())+"},"+"\n");
		sb.append(space+"eff = {");
		sb.append(getConds(a.getEffect())+"},"+"\n");
		sb.append(space+"pre length = "+","+"\n");
		sb.append(space+"eff length = "+","+"\n");
		sb.append(space+"height = "+","+"\n");
		sb.append(space+"width = "+""+"\n"+"}"+"\n");

		return sb.toString();
	}
	
	public String getScheme(String string) {
		String name[]=string.split("\\(");
		String variable[]=name[1].split("\\)");
		variable=variable[0].split(",");
		int num=variable.length;
		
		StringBuilder sb=new StringBuilder();
		sb.append("{"+name[0]+"}");
		sb.append("{"+num+"}");

		
		return sb.toString();
	}
	
	public String getText(String string) {
		String name[]=string.split("\\(");
		String variable[]=name[1].split("\\)");
		variable=variable[0].split(",");
		int num=variable.length;
		mapping = new HashMap<>();
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
	
	public String getConds(ArrayList<String> cond) {
		String space="  ";
		StringBuilder sb=new StringBuilder();
		
		for(int i=0;i<cond.size();i++) {
			sb.append("\n"+"\t"+getCond(cond.get(i))+",");
		}
		if(cond.size()>0) {
			sb.append("\n"+space);
		}
	
		return sb.toString();
	}

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
