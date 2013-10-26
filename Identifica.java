
public class Identifica {
	
	static Lista LResultado = new Lista();
	
static void Inserta(Lista Linea){
	
	//Linea.Imprimir();
	
	Nodo aux = Linea.Primero.siguiente;
	LResultado.InsertaFinal2(aux.dato, null, null);
	aux=aux.siguiente;
	
	while(aux!=null){
		if(!aux.dato.equals("=")){
			if(esNumero(aux.dato)&&(aux.siguiente == null)){//Para diferenciar de las op
				LResultado.Ultimo.tipo = "int";
				LResultado.Ultimo.valor = aux.dato;
			}
			else if((aux.dato == "True")||(aux.dato == "False")){
				LResultado.Ultimo.tipo = "boolean";
				LResultado.Ultimo.valor = aux.dato;
			}
			/*else if((esNumero(aux.dato))&&(aux.siguiente!=null)){
				LResultado.Ultimo.tipo = "int";
				LResultado.Ultimo.valor =  Opera.Operacion(aux.dato);
			}*/
			
		}
		aux=aux.siguiente;
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
 
}
