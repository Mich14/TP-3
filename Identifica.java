import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.text.html.parser.Parser;


public class Identifica {
	static Lista LResultado = new Lista();
	static Lista and_or = new Lista();
	static String tipo = "(";
	static String tipoLista = "[";

	static void Inserta(Lista Linea) throws ScriptException{
	
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
					LResultado.Ultimo.tipo = "bool";
					LResultado.Ultimo.valor = aux.dato;
				}
			
				else if (aux.dato.equals("if")) {
					sentencia_if(aux);
					break;
				}
			
				else if (aux.dato.equals("let")) {
					sentencia_let(aux);
					break;
				}
			
				else if((aux.dato.equals("[")) || (aux.dato.equals("("))){
					Lista Laux= new Lista();
					while (aux.siguiente != null){
						Laux.InsertaFinal(aux.dato);
						aux=aux.siguiente;
					}
				
					tipo = "(";
					tipoLista = "[";
				
					if (Laux.Primero.dato.equals("[")) {
						Laux.InsertaFinal("]");
						LResultado.Ultimo.tipo=VerificaLista(Laux);
					}
				
					if (Laux.Primero.dato.equals("(")) {
						Laux.InsertaFinal(")");
						LResultado.Ultimo.tipo=VerificaTupla(Laux);
					}
					
					Nodo aux1=Laux.Primero;
					String Valores="";
					while(aux1 != null){
						if(esNumero(aux1.dato)||(aux1.dato.equals("["))||(aux1.dato.equals("]"))||(aux1.dato.equals("("))||(aux1.dato.equals(")")) || (aux1.dato.equals(","))){
							Valores+=aux1.dato;
						}
						else if ((aux1.dato.equals("True"))||(aux1.dato.equals("False"))){
							Valores+=aux1.dato;
						}
						else{
							Valores += RevisaTabla(aux1.dato);
						}
						aux1=aux1.siguiente;
					}
					LResultado.Ultimo.valor=Valores;
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
	
	static void sentencia_if (Nodo act){
		while (!act.dato.equals("then")){
			if (act.dato.equals("orelse")) {
				and_or.InsertaBool("orelse", true);
			}
			if (act.dato.equals("andalso")) {
				and_or.InsertaBool("andalso", true);
			}
			if (act.siguiente.siguiente.dato.equals("=")){
				if (act.siguiente.siguiente.siguiente.dato.equals(RevisaTabla(act.siguiente.dato))) 
					and_or.InsertaBool("", true);	
				else and_or.InsertaBool("", false);
			}
			else {
				if (act.dato.equals("if") || act.dato.equals("andalso") || act.dato.equals("orelse")){
					if (act.siguiente.siguiente.dato.equals("then") || act.siguiente.siguiente.dato.equals("andalso") || act.siguiente.siguiente.dato.equals("orelse")){
						if (RevisaTabla(act.siguiente.dato).equals("True")) and_or.InsertaBool("", true);	
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
		}
		catch(Exception e){
			return false;
		}
		return true;
	}

	//Metodo que realiza operaciones a partir de una cadena
	static String Operar(String op) throws ScriptException{
		String resultado = "";
		float resultadoAux;
		int resulInt;
		ScriptEngineManager motorMate = new ScriptEngineManager();
		ScriptEngine Operador = motorMate.getEngineByName("JavaScript");//Crea una instancia de la libreria engine que realiza operaciones
		resultado = Operador.eval(op).toString();
		resultadoAux = Float.parseFloat(resultado);
		resulInt = (int) resultadoAux;
		System.out.println(resulInt);
		resultado = resulInt+"";
		
		return resultado;
	}
	
	
	static String VerificaTupla(Lista L){
		Nodo ap=L.Primero.siguiente;
	
		while(ap!=null){
			while(!ap.dato.equals(")")){
				
				if(esNumero(ap.dato)||(esNumero(RevisaTabla(ap.dato)))){
					if(ap.siguiente.dato.equals(")")){
						tipo+="int)";
						return tipo;
					}
					else tipo+="int*";
					ap=ap.siguiente;
				}
				
				else if(ap.dato.equals("[")){
					Lista Laux=new Lista();
					Nodo apt = ap;
					while(!apt.dato.equals("]")){
						Laux.InsertaFinal(apt.dato);
						apt=apt.siguiente;
					}
				
					Laux.InsertaFinal("]");
					tipo+=VerificaLista(Laux);
					ap=apt.siguiente;
					if(ap.siguiente != null) tipo+="*";
				}
				
				else if(ap.dato.equals("True")||ap.dato.equals("False")||(RevisaTabla(ap.dato).equals("True"))||(RevisaTabla(ap.dato).equals("False"))){
					if(ap.siguiente.dato.equals(")")){
						tipo+="bool)";
						return tipo;
					}
					else tipo+="bool*";
					ap=ap.siguiente;
				}
				
				else if(ap.dato.equals("(")){
					Lista L1 = new Lista();
					Nodo apuntador=ap;
					while(!apuntador.dato.equals(")")){
						L1.InsertaFinal(apuntador.dato);
						apuntador=apuntador.siguiente;
					}
					
					L1.InsertaFinal(")");
					tipo += "(";
					tipo=VerificaTupla(L1);
					ap=apuntador.siguiente;
					if((ap.siguiente != null) && (!ap.siguiente.dato.equals(")")))tipo+="*";
				}
			
				else{
					tipo += VerificaTipo (ap.dato);
					ap = ap.siguiente;
					if((ap.siguiente != null) && (!ap.siguiente.dato.equals(")")))tipo+="*";
				}
			
				if(ap.dato.equals(")"))break;
				ap=ap.siguiente;
			}
			
			if (ap.siguiente == null) break;
			else ap = ap.siguiente;
		}
	
		tipo+=")";
		return tipo;
	}


	static String VerificaLista (Lista L){
		Nodo aux = L.Primero.siguiente;
		
		if (aux.siguiente == null){
			tipoLista = "[]";
		}
		
		else if (esNumero(aux.dato)){
			tipoLista = "int list "; 
		}
		
		else if ((aux.dato.equals("True")) || (aux.dato.equals("False"))){
			tipoLista = "bool list ";
			
		}
		
		else if (aux.dato.equals("(")){
			Lista Tupla = new Lista ();
			while (!aux.dato.equals(")")){
				Tupla.InsertaFinal(aux.dato);
				aux = aux.siguiente;
			}
			Tupla.InsertaFinal(")");
			tipoLista = VerificaTupla (Tupla);
			tipoLista += "list ";
		}
		
		else if (aux.dato.equals("[")){
			Lista Lista = new Lista ();
			while (!aux.dato.equals("]") ){
				Lista.InsertaFinal(aux.dato);
				aux = aux.siguiente;
			}
				Lista.InsertaFinal("]");
				tipoLista = VerificaLista (Lista);
				tipoLista += "list ";
		}
		
		else{
			tipoLista = VerificaTipo (aux.dato);
			tipoLista += "list ";
		}
		
		return tipoLista;
	}


	//Busca en la tabla dinamica el valor de un elemento
	static String RevisaTabla(String elemento){
		if(!elemento.equals("(")&&!elemento.equals(",")&&!elemento.equals(")")&&!elemento.equals("[")&&!elemento.equals("]")&&!elemento.equals("True")&&!elemento.equals("False")){
			Nodo puntero = LResultado.Ultimo;
			while(!puntero.dato.equals(elemento)){
				puntero = puntero.anterior;
			}
			return puntero.valor;
		}
		return "";
	}


	static String VerificaTipo(String elemento){
		Nodo aux = LResultado.Ultimo;
			while(!aux.dato.equals(elemento)){
				aux = aux.anterior;
			}
			return aux.tipo;
	}
 
}
