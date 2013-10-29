import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class Identifica {
	
	static Lista LResultado = new Lista();
	static Lista and_or = new Lista();
	
	static void Inserta(Lista Linea) throws ScriptException{
		
		//Linea.Imprimir();
		
		Nodo aux = Linea.Primero.siguiente;
		LResultado.InsertaFinal2(aux.dato, null, null);
		aux=aux.siguiente;
		String operacion="";
		while(aux!=null){
			
			if(!aux.dato.equals("=")){
				if(esNumero(aux.dato)&&(aux.siguiente==null)&&(operacion.equals(""))){//Para diferenciar de las op
					LResultado.Ultimo.tipo = "int";
					LResultado.Ultimo.valor = aux.dato;
				}
				else if((aux.dato.equals("True"))||(aux.dato.equals("False"))){
				
					LResultado.Ultimo.tipo = "boolean";
					LResultado.Ultimo.valor = aux.dato;
				}

				else if (aux.dato.equals("if")) {
					sentencia_if(aux);
					break;
				}
				else if(aux.dato.equals("[")){
					//Poner a trabajar para listas, falta implementar . ;  y tuplas
					
				}
				else if (aux.dato.equals("let")) {
					sentencia_let(aux);
					break;
				}
				else{
				
					if(esNumero(aux.dato))operacion += aux.dato;
					else if((aux.dato.equals("+"))||(aux.dato.equals("-"))||(aux.dato.equals("*"))||(aux.dato.equals("/"))||(aux.dato.equals("("))||(aux.dato.equals(")"))){
						operacion += aux.dato;
					}
					else{
						
						Nodo puntero = LResultado.Ultimo;
						while(!puntero.dato.equals(aux.dato)){
							puntero = puntero.anterior;
						}
						operacion+=puntero.valor;
					}
					
				}
				
			}
			aux=aux.siguiente;
		}
		
		if(!operacion.equals("")){
			
			LResultado.Ultimo.valor = Operar(operacion);
			LResultado.Ultimo.tipo = "int";
		}
		LResultado.Imprimir();
	}

static String Buscar (String elem){
	//System.out.println("BUSCAR");
	Nodo aux = LResultado.Ultimo;
	
	while (!aux.dato.equals(elem)){
		//System.out.println(aux.dato);
		aux = aux.anterior;
	}
	//System.out.println(aux.valor);
	return aux.valor;
}

static void sentencia_if (Nodo act){
	
	while (!act.dato.equals("then")){
			
		if (act.dato.equals("orelse")) {
			and_or.InsertaBool("orelse", true);
		}
		if (act.dato.equals("andalso")) {
			and_or.InsertaBool("andalso", true);
		}
		if (act.siguiente.siguiente.dato.equals("=")){
			if (act.siguiente.siguiente.siguiente.dato.equals(Buscar(act.siguiente.dato))) 
				and_or.InsertaBool("", true);	
			else and_or.InsertaBool("", false);
		}
		else {
			if (act.dato.equals("if") || act.dato.equals("andalso") || act.dato.equals("orelse")){
				if (act.siguiente.siguiente.dato.equals("then") || act.siguiente.siguiente.dato.equals("andalso") || act.siguiente.siguiente.dato.equals("orelse")){
					if (Buscar(act.siguiente.dato).equals("True")) and_or.InsertaBool("", true);	
					else and_or.InsertaBool("", false);  
				}
			}
		}
		act = act.siguiente;
	}
	
	//and_or.ImprimirBool();
	//System.out.println(Evaluar());
	
	if (Evaluar()) {
		if(esNumero(act.siguiente.dato)){//Para diferenciar de las op
			LResultado.Ultimo.tipo = "int";
			LResultado.Ultimo.valor = act.siguiente.dato;
		}
		if((act.siguiente.dato.equals("True"))||(act.siguiente.dato.equals("False"))){
			LResultado.Ultimo.tipo = "boolean";
			LResultado.Ultimo.valor = act.siguiente.dato;
		}
		if (act.siguiente.dato.equals("if")) {
			sentencia_if(act);
		}
	}
	
	else {
		if (act.siguiente.siguiente.dato.equals("else")){
			if(esNumero(act.siguiente.siguiente.siguiente.dato)){//Para diferenciar de las op
				LResultado.Ultimo.tipo = "int";
				LResultado.Ultimo.valor = act.siguiente.siguiente.siguiente.dato;
			}
			if((act.siguiente.siguiente.siguiente.dato.equals("True"))||(act.siguiente.siguiente.siguiente.dato.equals("False"))){
				LResultado.Ultimo.tipo = "boolean";
				LResultado.Ultimo.valor = act.siguiente.siguiente.siguiente.dato;
			}
			if (act.siguiente.dato.equals("if")) {
				sentencia_if(act);
			}
		}
	}
}	

static void sentencia_let (Nodo act) {
	
}

static boolean Evaluar () {
	
	//System.out.println ("EVAL");
	
	boolean resultado;
	
	if (and_or.Primero.siguiente == null) {
		if (and_or.Primero.cumple) resultado = true;
		else resultado = false;
	}
	
	else {
		Nodo aux = and_or.Primero;
		resultado = Evaluar_aux (aux.cumple, aux.siguiente.dato, aux.siguiente.siguiente.cumple);
		
		aux = aux.siguiente.siguiente.siguiente;
		
		while (aux != null) {
			//System.out.println(resultado + aux.dato + aux.siguiente.cumple);
			resultado = Evaluar_aux(resultado, aux.dato, aux.siguiente.cumple);
			aux = aux.siguiente.siguiente;
		}
	}
	//System.out.println ("SalI");
	return resultado;
}

static boolean Evaluar_aux (boolean bool1, String operador, boolean bool2 ) {
	if (operador.equals("orelse")) return bool1 || bool2;
	else  return bool1 && bool2;
}
	
static boolean esNumero(String entrada){
	try{
		Long.parseLong(entrada);
	}catch(Exception e){
		return false;
	}
	return true;
}
 
//Metodo que realiza operaciones a partir de una cadena
static String Operar(String op) throws ScriptException{
	String resultado;
	
	ScriptEngineManager motorMate = new ScriptEngineManager();
	ScriptEngine Operador = motorMate.getEngineByName("JavaScript");//Crea una instancia de la libreria engine que realiza operaciones
	resultado = Operador.eval(op).toString();
	return resultado;
}



}
