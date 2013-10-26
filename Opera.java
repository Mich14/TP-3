/*Jep Java parses and evaluates mathematical expressions 
with only a few lines of code. This package allows your users to enter a formula
as a string, and instantly evaluate it. Jep supports user defined variables, 
constants, and functions. A number of common mathematical functions
and constants are included.*/
//http://www.singularsys.com/jep/

public class Opera {

//Se utilizo la libreria JEP que recibe una hilera y la evalua.
static String Operacion(String op){
	double resultado;
	String resulta="";
	 org.nfunk.jep.JEP myParser = new org.nfunk.jep.JEP();
	 myParser.addStandardFunctions();
	 myParser.addStandardConstants();
	 myParser.parseExpression(op);
	 resultado = myParser.getValue();
	 System.out.println(resultado);
	 return resulta.valueOf(resultado);

}


}
