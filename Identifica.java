import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Identifica {
	static Lista LResultado = new Lista(); // Lista que guarda el resultado de los ambientes
	static Lista and_or = new Lista();     // Lista para trabajar el if con or y and
	static String tipo = "(";              // Para el manejo de tuplas
	static String tipoLista = "[";		   // Para el manejo de listas
	static Lista L_aux = new Lista ();	   // lista para trabajar las variables temporales de los let
	static boolean es_let, let = false;		// Variables de clase para la implemtación del let
	
	// Método principal de la clase
	// Función : Trabajar con las lineas que se leen del archivo y las val
	static void Inserta(Lista Linea) throws ScriptException{
	
		Nodo aux = Linea.Primero.siguiente; // Se brinca el val
		if (es_let){ 										// si se trabaja con una expresión let se inserta en la lista
			L_aux.InsertaFinal2(aux.dato, null, null);      // especial para el let
		}
		else {
			LResultado.InsertaFinal2(aux.dato, null, null); // si no se inserta en la lista resultado
		}
		aux = aux.siguiente;
		Ambientes (aux.siguiente);  // llama al método ambientes que le otorga tipo y valor a las varibles
	}	
	
	// Método encargado de seleccionar el tipo de expresión y otorgar el valor y el tipo a la variable
	static void Ambientes(Nodo aux) throws ScriptException{
		String operacion = "";
		
		// Cuando se trata de números
		if (((esNumero(aux.dato)) && (aux.siguiente == null) && (operacion.equals(""))) || ((esNumero(aux.dato))&&(aux.siguiente.dato.equals("else")))) {//Para diferenciar de las op
			
			// Caso especial del let
			if (es_let){
				L_aux.Ultimo.tipo = "int";
				L_aux.Ultimo.valor = aux.dato;
			}
			else{
				LResultado.Ultimo.tipo = "int";
				LResultado.Ultimo.valor = aux.dato;
			}
			
		}
			
		// Cuando se trata de booleanos
		else if((aux.dato.equals("True")) || (aux.dato.equals("False"))){
			
			// Caso especial del let
			if (es_let){
				L_aux.Ultimo.tipo = "bool";
				L_aux.Ultimo.valor = aux.dato;
			}
			else{
				LResultado.Ultimo.tipo = "bool";
				LResultado.Ultimo.valor = aux.dato;
			}
		}
			
		// Cuando la expresión es un if
		else if (aux.dato.equals("if")) {
			sentencia_if(aux);
			return;
		}
		
		// Cuando la expresión es un let
		else if (aux.dato.equals("let")) {
			sentencia_let(aux);
			return;
		}
		
		// Cuando son tuplas o listas
		else if((aux.dato.equals("[")) || (aux.dato.equals("("))){
			Lista Laux= new Lista();
			while (aux.siguiente != null){
				if (aux.dato.equals("else")) break;
				Laux.InsertaFinal(aux.dato);
				aux = aux.siguiente;
			}
			
			tipo = "(";
			tipoLista = "[";
				
			if (Laux.Primero.dato.equals("[")) {
				Laux.InsertaFinal("]");
				
			// Se le otorga el tipo según lo devuelto por los métodos correspondientes	
				// Caso especial del let
				if (es_let){
					L_aux.Ultimo.tipo = VerificaLista (Laux);
				}
				else{
					LResultado.Ultimo.tipo=VerificaLista(Laux);
				}
				
			}
				
			if (Laux.Primero.dato.equals("(")) {
				Laux.InsertaFinal(")");
			
				// Caso especial del let
				if (es_let){
					L_aux.Ultimo.tipo = VerificaTupla (Laux);
				}
				else{
					LResultado.Ultimo.tipo=VerificaTupla(Laux);
				}
			}
			// En este fragmento de código se otrogan los valores a las tuplas y listas		
			Nodo aux1=Laux.Primero;
			String Valores = "";
			while(aux1 != null){
				if(esNumero(aux1.dato) || (aux1.dato.equals("[")) || (aux1.dato.equals("]")) || (aux1.dato.equals("(")) || (aux1.dato.equals(")")) || (aux1.dato.equals(","))){
					Valores += aux1.dato;
				}
				
				else if ((aux1.dato.equals("True")) || (aux1.dato.equals("False"))){
					Valores += aux1.dato;
				}
				
				else{
					Valores += RevisaTabla(aux1.dato);
				}
				
				aux1 = aux1.siguiente;
			}
			// Caso especial del let
			if (es_let){
				L_aux.Ultimo.valor = Valores;
			}
			else{
				LResultado.Ultimo.valor=Valores;
			}
		}
			// Este caso es si es una operación
		else{
			Nodo apuntador = aux;
			
			// Ciclo que uno la operación en un string
			while (apuntador != null && !apuntador.dato.equals("else")){
				if(esNumero(apuntador.dato))operacion += apuntador.dato;
				
				else if((apuntador.dato.equals("+"))||(apuntador.dato.equals("-"))||(apuntador.dato.equals("*"))||(apuntador.dato.equals("/"))||(apuntador.dato.equals("("))||(apuntador.dato.equals(")"))){
					operacion += apuntador.dato;
				}
				
				else{
					
					operacion += RevisaTabla(apuntador.dato);
				}
			apuntador = apuntador.siguiente;
			}
		}			
		
		// Se da el valor y el tipo a la varible
		if(!operacion.equals("")){
			
			// Caso especial del let
			if (es_let){
				L_aux.Ultimo.valor = Operar(operacion);
				L_aux.Ultimo.tipo = "int";
			}
			else{
				LResultado.Ultimo.valor = Operar(operacion);
				LResultado.Ultimo.tipo = "int";
			}
		}
		
		// Uso de la expresión let, para el manejo de la variables temporales
		if (es_let) {
			es_let = false;
			let = true;
		}
	}

	// Método que maneja el uso del if
	static void sentencia_if (Nodo act) throws ScriptException{
		
		boolean es_if = false; // Booleano para el manejo de if dentro de if
		
		// Ciclo que recorre la condición y determina si es verdader o no
		while (!act.dato.equals("then")){
			
			// en el caso de haber un or
			if (act.dato.equals("orelse")) {
				and_or.InsertaBool("orelse", true);
			}
			
			// en el caso de haber un and
			if (act.dato.equals("andalso")) {
				and_or.InsertaBool("andalso", true);
			}
			
			// si es una expresión comparativa
			if (act.siguiente.siguiente.dato.equals("=")){
				if (esNumero(act.siguiente.siguiente.siguiente.dato)){
					if (act.siguiente.siguiente.siguiente.dato.equals(RevisaTabla(act.siguiente.dato))) 
						and_or.InsertaBool("", true);	
					else and_or.InsertaBool("", false);
				}
				else {
					if (RevisaTabla(act.siguiente.siguiente.siguiente.dato).equals(RevisaTabla(act.siguiente.dato))) 
						and_or.InsertaBool("", true);	
					else and_or.InsertaBool("", false);
				}
			}
			
			// Si es la primera expresión es menor, o menor e igual
			if (act.siguiente.siguiente.dato.equals("<")){
				int x = 0;
				if (act.siguiente.siguiente.siguiente.dato.equals("=")){
					
					if (esNumero(act.siguiente.siguiente.siguiente.siguiente.dato))
						x = Integer.parseInt (act.siguiente.siguiente.siguiente.siguiente.dato); 
					else 
						x = Integer.parseInt (RevisaTabla(act.siguiente.siguiente.siguiente.siguiente.dato));
					
					int y = Integer.parseInt (RevisaTabla(act.siguiente.dato));
					
					if (y <= x) and_or.InsertaBool("", true);	
					else and_or.InsertaBool("", false);
					
					act = act.siguiente;
				}
				else {
					if (esNumero(act.siguiente.siguiente.siguiente.dato))
						x = Integer.parseInt (act.siguiente.siguiente.siguiente.dato); 
					else 
						x = Integer.parseInt (RevisaTabla(act.siguiente.siguiente.siguiente.dato));
					
					int y = Integer.parseInt (RevisaTabla(act.siguiente.dato));
					if (y < x) and_or.InsertaBool("", true);	
					else and_or.InsertaBool("", false);
				}
			}
			
			// si la priemra expresión es mayor, o mayor e igual
			if (act.siguiente.siguiente.dato.equals(">")){
				int x = 0;
				if (act.siguiente.siguiente.siguiente.dato.equals("=")){
					
					if (esNumero(act.siguiente.siguiente.siguiente.siguiente.dato))
						x = Integer.parseInt (act.siguiente.siguiente.siguiente.siguiente.dato); 
					else 
						x = Integer.parseInt (RevisaTabla(act.siguiente.siguiente.siguiente.siguiente.dato));
					
					int y = Integer.parseInt (RevisaTabla(act.siguiente.dato));
					
					if (y >= x) and_or.InsertaBool("", true);	
					else and_or.InsertaBool("", false);
					
					act = act.siguiente;
				}
				else {
					if (esNumero(act.siguiente.siguiente.siguiente.dato))
						x = Integer.parseInt (act.siguiente.siguiente.siguiente.dato); 
					else 
						x = Integer.parseInt (RevisaTabla(act.siguiente.siguiente.siguiente.dato));
					
					int y = Integer.parseInt (RevisaTabla(act.siguiente.dato));
					if (y > x) and_or.InsertaBool("", true);	
					else and_or.InsertaBool("", false);
				}
			}
			
			// si es solamente un booleano el que se desea considerar
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
		
		// Si la condición es verdadera 
		if (Evaluar()) Ambientes(act.siguiente);
		
		// Si la expresión es falsa
		else {
			// While que controla si hay un if dentro de un if
			while (!act.dato.equals("else") || (es_if)){
				if (act.dato.equals("if")) es_if = true;
				if (act.dato.equals("else")) es_if = false;
				act = act.siguiente;
			}
			Ambientes(act.siguiente);
			
		}
	}	

	// Método que maneja el uso del let
	static void sentencia_let (Nodo act) throws ScriptException {
		
		es_let = true; // Booleano que hace que las variables temp se inserten en la lista auxiliar
		Lista L_auxLet = new Lista();
		Nodo aux_let = act.siguiente;
		
		// Ciclo que recorre la varible temporal
		while (!aux_let.dato.equals("in")) {
			L_auxLet.InsertaFinal(aux_let.dato);
			aux_let = aux_let.siguiente;
		}
		
		// Se llama al método inserta pra que otorgue valor y tipo a las variables temp
		Inserta(L_auxLet);
		
		aux_let = aux_let.siguiente; // Brinca el in
		Lista aux = new Lista();
		boolean end = false; // Para el uso del let dentro del let
		
		// Ciclo para enviar el cuerpo del let
		while (!aux_let.dato.equals("end") || end) {
			if (aux_let.dato.equals("let")) end = true;
			if (aux_let.dato.equals("end") && end) end = false;
			aux.InsertaFinal(aux_let.dato);
			aux_let = aux_let.siguiente;
		}
		
		Ambientes(aux.Primero);
	}
	
	// Método para el uso del if que determina la verasidad de la condición
	static boolean Evaluar () {
		
		boolean resultado;
		
		// Si solamente es una expresión
		if (and_or.Primero.siguiente == null) {
			if (and_or.Primero.cumple) resultado = true;
			else resultado = false;
		}
		
		// Por si tiene operadores lógicos
		else {
			Nodo aux = and_or.Primero;
			resultado = Evaluar_aux (aux.cumple, aux.siguiente.dato, aux.siguiente.siguiente.cumple);
			
			aux = aux.siguiente.siguiente.siguiente;
			
			while (aux != null) {
				
				resultado = Evaluar_aux(resultado, aux.dato, aux.siguiente.cumple);
				aux = aux.siguiente.siguiente;
			}
		}
		and_or.Primero = null;
		return resultado;
	}
	
	// Método especial para el uso del and y el or en los if
	static boolean Evaluar_aux (boolean bool1, String operador, boolean bool2 ) {
		if (operador.equals("orelse")) return bool1 || bool2;
		else  return bool1 && bool2;
	}
	
	// Método que determina si un string es un número
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
		resultado = resulInt+"";
		
		return resultado;
	}
	
	//Método que otorga el tipo a als tuplas
	static String VerificaTupla(Lista L){
		Nodo ap=L.Primero.siguiente;
		
		//Recorre hasta el final de la tupla
		while(ap!=null){
			while(!ap.dato.equals(")")){
				
				//Verifica si es numero
				if(esNumero(ap.dato)||(esNumero(RevisaTabla(ap.dato)))){
					if(ap.siguiente.dato.equals(")")){
						tipo+="int)";
						return tipo;
					}
					else tipo+="int*";
					ap=ap.siguiente;
				}
				
				//Verifica si es lista
				else if(ap.dato.equals("[")){
					Lista Laux=new Lista();
					Nodo apt = ap;
					while(!apt.dato.equals("]")){
						Laux.InsertaFinal(apt.dato);
						apt=apt.siguiente;
					}
				
					Laux.InsertaFinal("]");
					tipo+=VerificaLista(Laux);   //Llama a verifica lista
					ap=apt.siguiente;
					if(ap.siguiente != null) tipo+="*";
				}
				//Verifica si es boolean
				else if(ap.dato.equals("True")||ap.dato.equals("False")||(RevisaTabla(ap.dato).equals("True"))||(RevisaTabla(ap.dato).equals("False"))){
					if(ap.siguiente.dato.equals(")")){
						tipo+="bool)";
						return tipo;
					}
					else tipo+="bool*";
					ap=ap.siguiente;
				}
				
				//Verifica si es tupla
				else if(ap.dato.equals("(")){
					Lista L1 = new Lista();
					Nodo apuntador=ap;
					while(!apuntador.dato.equals(")")){
						L1.InsertaFinal(apuntador.dato);
						apuntador=apuntador.siguiente;
					}
					
					L1.InsertaFinal(")");
					tipo += "(";
					tipo=VerificaTupla(L1); //Llama a verifica tupla
					ap=apuntador.siguiente;
					if((ap.siguiente != null) && (!ap.siguiente.dato.equals(")")))tipo+="*";
				}
			
				else{	
					//En caso de que sea una variable se llama a verifica a tipo que retorna el tipo de la variable
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

	//Método que otorga el tipo a las listas
	static String VerificaLista (Lista L){
		Nodo aux = L.Primero.siguiente;
		
		//Verifica si una lista esta vacia
		if (aux.siguiente == null){
			tipoLista = "'a list";
		}
		
		//Verifica si es numero
		else if (esNumero(aux.dato)){
			tipoLista = "int list "; 
		}
		
		//Verifica si es boolean
		else if ((aux.dato.equals("True")) || (aux.dato.equals("False"))){
			tipoLista = "bool list ";
			
		}
		
		//Verifica si es tupla
		else if (aux.dato.equals("(")){
			Lista Tupla = new Lista ();
			while (!aux.dato.equals(")")){
				Tupla.InsertaFinal(aux.dato);
				aux = aux.siguiente;
			}
			Tupla.InsertaFinal(")");
			tipoLista = VerificaTupla (Tupla); //Se llama a verifica tupla
			tipoLista += "list ";
		}
		
		//Verifica si es lista
		else if (aux.dato.equals("[")){
			Lista Lista = new Lista ();
			while (!aux.dato.equals("]") ){
				Lista.InsertaFinal(aux.dato);
				aux = aux.siguiente;
			}
				Lista.InsertaFinal("]");
				tipoLista = VerificaLista (Lista); //Se llama a verficaLista
				tipoLista += "list ";
		}
		
		else{
			//En caso de que sea una variable se llama a verifica a tipo que retorna el tipo de la variable			
			tipoLista = VerificaTipo (aux.dato);
			tipoLista += "list ";
		}
		
		return tipoLista;
	}

	//Busca en la tabla dinamica el valor de un elemento
	static String RevisaTabla(String elemento){
		if(!elemento.equals("(")&&!elemento.equals(",")&&!elemento.equals(")")&&!elemento.equals("[")&&!elemento.equals("]")&&!elemento.equals("True")&&!elemento.equals("False")){
			Nodo puntero;
			// Para el caso del let
			if (let) puntero = L_aux.Ultimo;
			else puntero = LResultado.Ultimo;
			
			while((puntero != null) && (!puntero.dato.equals(elemento))){
				puntero = puntero.anterior;
			}
			
			if (puntero == null && let) {
				puntero = LResultado.Ultimo;
				while(!puntero.dato.equals(elemento)){
					puntero = puntero.anterior;
				}
				
			}
			return puntero.valor;
		}
		return "";
	}
	
	// Método que retorna el valor de un elemento en la lista resultado
	static String VerificaTipo(String elemento){
		Nodo aux = LResultado.Ultimo;
			while(!aux.dato.equals(elemento)){
				aux = aux.anterior;
			}
			return aux.tipo;
	}
 
}
