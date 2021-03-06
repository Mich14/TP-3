import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
	
public class LeerArchivo extends JFrame implements ActionListener {
	
	// Parte de la interfaz para facilitar al usuario seleccionar el archivo
	JButton abrir;
	  
	public LeerArchivo() {
		// INTERFAZ
		Container ventana = getContentPane();
		setBounds(500, 200, 600, 100);
		setTitle("Manejador de tablas de símbolos");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel P_principal = new JPanel();
		P_principal.setBackground(Color.gray);
		P_principal.setLayout(new BorderLayout());
		
		
		JPanel P_indicaciones = new JPanel();
		P_indicaciones.setBackground(Color.gray);
		
		abrir = new JButton("Seleccionar");
	    abrir.addActionListener(this);
	    abrir.setBackground(new Color (139, 28, 98));
	    abrir.setForeground(Color.white);
	    abrir.setFont(new java.awt.Font("Segoe Print", Font.BOLD, 15));
		
	    JLabel L_indicaciones = new JLabel ("    Haga click en seleccionar para elegir el archivo"  );
	    L_indicaciones.setForeground(new Color (139, 28, 98));
	    L_indicaciones.setFont(new java.awt.Font("Segoe Print", Font.BOLD, 20));
	    
	    JLabel L_relleno = new JLabel (""  );
	    
	    P_indicaciones.add(abrir);
	    P_principal.add(L_indicaciones, BorderLayout.NORTH);
	    P_principal.add(L_relleno, BorderLayout.SOUTH);
	    P_principal.add(P_indicaciones, BorderLayout.CENTER);
	    ventana.add(P_principal);
	    setVisible(true);
	}
	    
	public void actionPerformed(ActionEvent evento) {
		
		JFileChooser elegir = new JFileChooser(); // Elige el archivo que se va leer
		elegir.showOpenDialog(abrir);
		  
	      if (evento.getSource() == abrir) {
	    	if(elegir.getSelectedFile()==null){
	    		setVisible(false);
	    	}
	    	else {
			    String Ruta = elegir.getSelectedFile().getPath(); //Obtiene la ruta del archivo
			    setVisible(false);
			    leerArchivo(Ruta); 
			    new Ambientes();
	    	}
	    }
	    
	}
	          
    //
	
	//Método para leer archivos
	public static void leerArchivo  (String ruta) {
		FileReader fr = null;                          //Archivo que se va a leer
		BufferedReader br = null;                      //Lee el archivo
		String linea;                                 //Lee linea por línea el archivo
		String archivo = "";                          //Se almacena las líneas que contiene la expresión
				   
		try {
			fr = new FileReader( ruta );
			br = new BufferedReader( fr );
			
			Lista LExpresion = new Lista();
				    
			//Obtenemos el contenido del archivo linea por linea
			while( ( linea = br.readLine() ) != null ) {
				    	
				String numeros = "";
				String letras = "";
						
				archivo = archivo + linea;
				
				// se recorre la línea que se acaba de recibir y se separa en tokens
				for (int i=0; i<linea.length(); i++) {
									
					String caracter = Character.toString(linea.charAt(i)); 
									
					if (caracter.matches("[0-9]*")) // Se une los números para insertarlos en la lista
						numeros = numeros + caracter;
								
					else {
						if (numeros != ""){
							LExpresion.InsertaFinal(numeros);
							numeros = "";
						}
					}
					//Valida las letras
					if (caracter.matches("[a-z]*") || caracter.matches("[A-Z]*"))
						letras = letras + caracter;	
							
					else {
						if (letras != ""){
							LExpresion.InsertaFinal(letras);
							letras = "";
						}
					}
					
					// Inserta caracteres especiales
					if (!caracter.matches("[a-z]*") && !caracter.matches("[A-Z]*") && !caracter.matches("[0-9]*") && !caracter.equals(" "))
						LExpresion.InsertaFinal(caracter);
				}
					
				if (numeros != "")
					LExpresion.InsertaFinal(numeros);
						
				if (letras != "")
					LExpresion.InsertaFinal(letras);
			}
			
			Nodo Aux_expresion = LExpresion.Primero;
			Lista L_aux = new Lista();
			
			// Manejo de expresiones de un let con otro
			boolean es_let = false;
			boolean sub_let = false;
			
			// recorre la lista donde se guardo todo el archivo y lo recorre para separarlo por expresiones
			while (Aux_expresion != null)  {
				Identifica.let = false;
				
				
				// Para manejar el let
				if (Aux_expresion.dato.equals("let")) {
					if (!es_let) es_let = true;
					else sub_let = true;
				}
				
				if (Aux_expresion.dato.equals("end")) 
					if (es_let && !sub_let) es_let = false;
					else sub_let = false;
				// ------------------------------------------------
				
				
				if ((Aux_expresion.dato.equals("val") || Aux_expresion.siguiente == null) && !es_let){
					
					
					if (Aux_expresion.siguiente == null) L_aux.InsertaFinal(Aux_expresion.dato);
				    					    		
				    if (L_aux.Primero != null){
				    	// Si el ultimo de la lisya es ; o . se elimina antes de trabajar con la expresión
				    	if (L_aux.Ultimo.dato.equals(";") || L_aux.Ultimo.dato.equals(".")) L_aux.EliminarFinal();
				    	
				    	// se llama al método insertar, para determinar el valor y el tipo de la expresión
				    	Identifica.Inserta(L_aux);
				    }
				    
				    L_aux.Primero = null;
				
				}
				
				
				L_aux.InsertaFinal(Aux_expresion.dato); // Se inserta en la lista que contiene la expresión
				
				Aux_expresion = Aux_expresion.siguiente;
			}
				    
		}	
		
		catch( Exception e ){ }
		//finally se utiliza para que si todo ocurre correctamente o si ocurre
		//algun error se cierre el archivo que anteriormente abrimos
			finally{
				
				try{
					br.close();  
				}
				catch( Exception ex ){}
			}	
	}

}
   