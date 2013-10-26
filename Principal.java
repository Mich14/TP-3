public class Principal {

	public static void main(String[] args) {
		//new LeerArchivo ();
		Identifica nuevo = new Identifica();
		Lista p = new Lista();
		Lista p1 = new Lista();
		Lista p2 = new Lista();
		
		p.InsertaFinal("val", null);
		p.InsertaFinal("x", null);
		p.InsertaFinal("=", null);
		p.InsertaFinal("0", null);
		
		p1.InsertaFinal("val", null);
		p1.InsertaFinal("b", null);
		p1.InsertaFinal("=", null);
		p1.InsertaFinal("False", null);
		
		nuevo.Inserta(p);
		nuevo.Inserta(p1);
		
		nuevo.LResultado.Imprimir();
	}

}
