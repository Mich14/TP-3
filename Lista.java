// Crea la clase Lista
public class Lista {
	
	Nodo Primero; // Creacion de nodo primero 
	Nodo Ultimo;  //Creacion de nodo ultimo
	
	//Verifica si una lista posee elementos
	boolean ListaVacia(){ 
		if (Primero==null){
			return true;
		}
		else{return false;}
	}
	
	//Inserta el dato, con su respectivo valor y tipo al final de una lista 
	//Crea una lista doblemente enlazada		
	void InsertaFinal2(String Dato, String Tipo, String Valor) {
		Nodo Nuevo = new Nodo(Dato, Tipo, Valor);
		if (ListaVacia()){ Primero = Ultimo = Nuevo;}
		else {
			Ultimo.siguiente = Nuevo;
			Nuevo.anterior = Ultimo;
			Ultimo=Ultimo.siguiente;
		}
	}

	//Metodo que inserta un dato y un boolean utilizado para realizar el if
	//Crea una lista doblemente enlazada
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
	
	//Metodo que elimina el ultimo elemento de una lista
	void EliminarFinal(){
		if(ListaVacia()) System.out.println("NO hay elementos esta vacia");
		else{
			if(Primero.siguiente==null){
				Primero = Ultimo = null;
			}
			else{
				Nodo aux = Primero;
				while(aux.siguiente.siguiente != null){
					aux = aux.siguiente;
				}
				Ultimo = aux;
				aux.siguiente.anterior = null;
				aux.siguiente = null;
			}
		}
	}

			
			
	// Se encarga de insertar un dato al final de la lista
	void InsertaFinal(String Dato) {
		if (ListaVacia()){
			Primero = Ultimo = new Nodo(Dato);
		}
		
		else {
			Ultimo.siguiente = new Nodo(Dato);
			Ultimo=Ultimo.siguiente;
		}
	}
	
	//Metodo que imprime una lista resultado con el dato el valor y el tipo
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
	
	//Metodo que imprime los datos de una lista
	void ImprimirS () {
		if (ListaVacia()){
			System.out.println("No puede imprimir nada por que la lista no tiene nada ");
		}
		else {
			Nodo aux=Primero;
			while (aux!=null){
				System.out.print (aux.dato + " ");
				aux=aux.siguiente;
			}
			System.out.println ("");
		}
	}
	
	//Metodo que imprime el boolean de una lista
	void ImprimirBool () {
		if (ListaVacia()){
			System.out.println("No puede imprimir nada por que la lista no tiene nada ");
		}
		
		else {
			Nodo aux=Primero;
			while (aux!=null){
				if (aux.dato.equals(""))
					System.out.print (aux.cumple + " ");
				else System.out.print (aux.dato + " "); 
				aux=aux.siguiente;
			}
		}
	}
			
}
