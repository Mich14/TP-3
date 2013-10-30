public class Lista {
	
	// Crea la clase Lista
	Nodo Primero; // creaci�n de Nodos 
	Nodo Ultimo;
	
	//Esta es una funci�n boolean que se encargara ir vaciando la Lista//
	boolean ListaVacia(){ 
		if (Primero==null){
			return true;
		}
		else{return false;}
	}
			
	void InsertaFinal2(String Dato, String Tipo, String Valor) {
		Nodo Nuevo = new Nodo(Dato, Tipo, Valor);
		if (ListaVacia()){ Primero = Ultimo = Nuevo;}
		else {
			Ultimo.siguiente = Nuevo;
			Nuevo.anterior = Ultimo;
			Ultimo=Ultimo.siguiente;
		}
	}

	void InsertaBool(String Dato, Boolean Cumple) {	
		if (ListaVacia()){
			Primero = Ultimo = new Nodo(Dato, Cumple);}
		
		else {
			Nodo nuevo = new Nodo(Dato, Cumple);
			Ultimo.siguiente = nuevo;
			nuevo.anterior = Ultimo;
			Ultimo=Ultimo.siguiente;
		}
	}
			
			
	// se encarga de insertar al final de la lista //
	void InsertaFinal(String Dato) {
		if (ListaVacia()){
			Primero = Ultimo = new Nodo(Dato);
		}
		
		else {
			Ultimo.siguiente = new Nodo(Dato);
			Ultimo=Ultimo.siguiente;
		}
	}
	
	void Imprimir () {
		if (ListaVacia()){
			System.out.println("No puede imprimir nada por que la lista no tiene nada ");
		}
		else {
			Nodo aux=Primero;
			while (aux!=null){
				System.out.println (aux.dato + " "+aux.tipo+" "+aux.valor+" ");
				aux=aux.siguiente;
			}
		}
	}
			
}