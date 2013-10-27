
public class Lista {
	// Crea la clase Lista
			Nodo Primero; // creaci�n de Nodos 
			Nodo Ultimo;
	//Esta es una funci�n boolean que se encargara ir vaciando la Lista//
			boolean VaciaLista(){ 
				if (Primero==null){
					return true;
				}
				else{return false;}
			}
			
			void InsertaFinal2(String Dato, String Tipo, String Valor) {
				if (VaciaLista()){
					Primero = Ultimo = new Nodo(Dato, Tipo, Valor);}
					
				else {
					Ultimo.siguiente = new Nodo(Dato, Tipo,  Valor);
					Ultimo.siguiente.anterior = Ultimo;
					Ultimo=Ultimo.siguiente;
				}
			}
	// Se encarga de 
			
			void InsertaBool(String Dato, Boolean Cumple) {	
				if (VaciaLista()){
					Primero = Ultimo = new Nodo(Dato, Cumple);}
					
				else {
					Nodo nuevo = new Nodo(Dato, Cumple);
					nuevo.siguiente = Primero;
					Primero.anterior = nuevo;
					Primero = nuevo;
				}}
			
			
	// se encarga de insertar al final de la lista //
			
			void InsertaFinal(String Dato) {
				if (VaciaLista()){
					Primero = Ultimo = new Nodo(Dato);}
					
				else {
					Ultimo.siguiente = new Nodo(Dato);
					Ultimo=Ultimo.siguiente;
				}}
			void InsertaPila(Nodo P, boolean cumple) {
				if (VaciaLista()){
					Primero = Ultimo = new Nodo(P, cumple);}
					
				else {
					Ultimo.siguiente= new Nodo(P, cumple);
					Ultimo.siguiente.anterior = Ultimo;
					Ultimo=Ultimo.siguiente;
				}
			}
	// se encarga de eliminar el �ltimo elemento de la lista //
			
			void EliminarFinal(){
				if(VaciaLista()){System.out.println("NO hay elementos esta vacia");}
				else{
					if(Primero.siguiente==null){
						Primero=null;
					}
					else{
						Nodo aux=Primero;
						while(aux.siguiente.siguiente!=null){
							aux=aux.siguiente;
						}
						aux.siguiente=null;
					}
				}
			}
				
	// Inserta a la Bace de conocimiento //
			
			void InsertaFinal2 (String Dato, Nodo Puntero, boolean c ){
				if (ListaVacia()){
					Primero=new Nodo (Dato, Puntero, c);
				}
				else {
					Nodo aux=Primero;
					while (aux.siguiente!=null){
						aux=aux.siguiente;
					}
					Nodo nuevo= new Nodo (Dato, Puntero, c);                                                    
					nuevo.anterior=aux;
					aux.siguiente=nuevo;
				}		
			}
	//  se encraga de limpiar los espacios de los nodos que corresponden ala averific�n de todos los posibles casos en que se verifique el estado //
			void limpiarBoolean (){
				if (ListaVacia()){
					System.out.println("Lista vacia");
				}
				else {
					Nodo aux = Primero;
					while (aux != null){
						aux.cumple = true;
						aux = aux.siguiente;
					}
				}
			}
			
	//
			boolean ListaVacia(){
				return Primero==null;		
			}
		
			
	// metodo para imprimir una lista //
			
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
			
	// imprime el espacio en el nodo que corresponde al boolean //
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
			
	// imprime la lista de abyacencia que almacena la base de conocimiento //		
			
				
	
}
