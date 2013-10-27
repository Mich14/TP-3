import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class Identifica {
	
	static Lista LResultado = new Lista();
	
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
			else if(aux.dato.equals("[")){
				//Poner a trabajar para listas, falta implementar . ;  y tuplas
				
			}
			else{
			
				if(esNumero(aux.dato))operacion+=aux.dato;
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
