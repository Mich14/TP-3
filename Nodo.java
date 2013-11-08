//Creacion de la clase nodo
public class Nodo {
	
	Nodo Puntero,  anterior, siguiente;//Puntero: espacio para apuntar a otra lista.
	String dato, tipo, valor;
	boolean cumple;//Un espacio en el nodo mediante el cual se almacena el cumplimiento de todas las condiciones
	
	//Nodo que recibe un dato
	Nodo(String Dato ) {
		dato = Dato;
		siguiente = null;
	}
	
	//Nodo que almacena un dato, junto con el ambiente dinamico y el estatico
	Nodo(String Dato, String Tipo, String Valor ) {
		dato = Dato;
		tipo = Tipo;
		valor = Valor;
		siguiente = null;
		anterior = null;
	}
	
	//Nodo que recibe un dato y un boolean utilizado en el if	
    	Nodo (String Dato, boolean Cumple) {  
	    	dato = Dato;
	    	cumple = Cumple;
	    	siguiente = null;  
	    	anterior = null;
    	}
}
