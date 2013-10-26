
public class Nodo {

	
	Nodo Puntero,  anterior, siguiente;//Puntero: es pacio para apuntar a otra lista.
	String dato, tipo, valor;
	boolean cumple;//Un espacio en el nodo mediante el cual se almacena el cumplimiento de todas las condiciones
				
	Nodo(String Dato, String Valor ) {//Nodo que recibe dos valores de tipo String
		dato = Dato;
		valor = Valor;
		siguiente = null;
	}
	
	Nodo(String Dato, String Tipo, String Valor ) {//Nodo que recibe dos valores de tipo String
		dato = Dato;
		tipo = Tipo;
		valor = Valor;
		siguiente = null;
		anterior = null;
	}
	
    Nodo (String Dato, Nodo P, boolean Cumple) {//Nodo que almacena un dato tipo string, otro de tipo nodo, y un boolean  
    	dato = Dato;
    	Puntero = P;
    	cumple = Cumple;
    	siguiente = null;  //siguiente con valor de nulo
    	anterior = null;
    }
    
    Nodo (Nodo P, boolean Cumple) {  //Nodo que recibe un dato de tipo noto y el boolean que verifica todas las condiciones
    	Puntero = P;
    	cumple = Cumple;
    	siguiente = null;  //siguiente con valor de nulo
    	anterior = null;
    }
    
    Nodo (String Dato, boolean Cumple) {  //Nodo que recibe un dato de tipo string y un boolean .
    	dato = Dato;
    	cumple = Cumple;
    	siguiente = null;  //siguiente con valor de nulo
    	anterior = null;
    }
    
    
}
