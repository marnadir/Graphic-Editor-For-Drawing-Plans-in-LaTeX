package LaTex;

import Action.GlobalValue;

/**
 * Generate the LateX code, which consists in necessary library used to draw a plan in LaTex .
 * @author nadir
 * */

public class LaTexGeneratorPlan {

	public LaTexGeneratorPlan() {
		// TODO Auto-generated constructor stub
	}
	
	public String getLatexIntro() {
		StringBuilder sb = new StringBuilder();
		sb.append("\\documentclass{standalone}"+"\n");
		
		sb.append("\\usepackage[T1]{fontenc}"+"\n");
		
		sb.append("\\usepackage{lmodern}"+"\n");
		
		sb.append("\\usepackage{tikz}"+"\n");
		
		sb.append("\\usetikzlibrary{aiplans}"+"\n");

		sb.append("\\usetikzlibrary{domain}"+"\n");

		
		sb.append("\\tikzset{\n" + 
				"  every picture/.style = {font issue=\\tiny},\n" + 
				"  font issue/.style = {execute at begin picture={#1\\selectfont}},\n" + 
				"  TemplatePrimitive/.style = {"+getGlobalValuePrim()+"},\n"+
				
				"  TemplateAbstract/.style = {"+getGlobalValueAbstract()+"},\n"+
				" abstract/.style = {TemplateAbstract}, \n"+
				" primitive/.style = {TemplatePrimitive} \n"+
				"}"+"\n");

		sb.append("\\begin{document}"+"\n");

		
		sb.append("\\begin{tikzpicture}"+"\n");

	
		return sb.toString();
	}
	
	private String getGlobalValueAbstract() {
		
		StringBuilder sb = new StringBuilder();
		//rounded corners=3pt,draw=black
				
		
		
		
		if(GlobalValue.formIsBlackAbst) {
			sb.append("draw=black");
		}else {
			sb.append("draw=white");
		}
		
		if(!GlobalValue.cornerIsSquareAbst) {
			sb.append(",");
			sb.append("rounded corners=3pt");
		}
		
		
		if(GlobalValue.borderIsFatAbst) {
			sb.append(",");
			sb.append("thick");
		}
		
				
		return sb.toString();
	
	}
	
	private String getGlobalValuePrim() {
		
		StringBuilder sb = new StringBuilder();
		//rounded corners=3pt,draw=black
				
		
		
		
		if(GlobalValue.formIsBlackPr) {
			sb.append("draw=black");
		}else {
			sb.append("draw=white");
		}
		
		if(!GlobalValue.cornerIsSquarePr) {
			sb.append(",");
			sb.append("rounded corners=3pt");
		}
		
		if(GlobalValue.borderIsFatPr) {
			sb.append(",");
			sb.append("thick");
		}
		
		
		return sb.toString();
	
	}
	
	
	public String getLatexEnd() {
		StringBuilder sb = new StringBuilder();

		sb.append("\n"+"\\end{tikzpicture}");
		sb.append("\n");

		sb.append("\\end{document}");
		sb.append("\n");
		return sb.toString();
	}
	
	
}
