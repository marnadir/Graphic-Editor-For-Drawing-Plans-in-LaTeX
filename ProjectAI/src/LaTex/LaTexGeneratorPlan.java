package LaTex;

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
				"  TemplatePrimitive/.style = {thick,draw=black},\n"+
				"  TemplateAbstract/.style = {rounded corners=3pt,draw=black},\n"+
				" abstract/.style = {TemplateAbstract}, \n"+
				" primitive/.style = {TemplatePrimitive} \n"+
				"}"+"\n");

		sb.append("\\begin{document}"+"\n");

		
		sb.append("\\begin{tikzpicture}"+"\n");

	
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
