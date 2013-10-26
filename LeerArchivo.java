import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
	
public class LeerArchivo extends JFrame implements ActionListener {
	
	JButton boton;
	  
	public LeerArchivo() {
		
		Container ventana = getContentPane();
		JPanel panel1 = new JPanel();
		setBounds(0, 0, 200, 100);
		setTitle("Progra 3");
		
		boton = new JButton("Abrir");
	    boton.addActionListener(this);
	    
		   
	    panel1.add(boton);
	    ventana.add(panel1);
	    setVisible(true);
	}
	    
	public void actionPerformed(ActionEvent evento) {
		Lista LGeneral = new Lista();
		JFileChooser elegir = new JFileChooser();
		elegir.showOpenDialog(boton);
	               
	    //Si presionamos el boton ABRIR en pathArchivo obtenemos el path del archivo
	    if (evento.getSource() == boton) {
		    String Ruta = elegir.getSelectedFile().getPath(); //Obtiene path del archivo
		    String nombre = elegir.getSelectedFile().getName(); //obtiene nombre del archivo
		        
		    System.out.println("El path del archivo es: "+ Ruta);
		    leerArchivo(Ruta,LGeneral);
		   		    
		   
	    }
	}          
    
	
	//Método para leer archivos
			public static void leerArchivo  (String ruta, Lista L) {
				FileReader fr = null;                          //Archivo que se va a leer
				BufferedReader br = null;                      //Lee el archivo
				String linea;                                 //Lee linea por línea el archivo
				String archivo = "";                          //Se almacena las líneas que contiene la expresión
				   
				try
				{
					fr = new FileReader( ruta );
				    br = new BufferedReader( fr );
				   //Obtenemos el contenido del archivo linea por linea
				    while( ( linea = br.readLine() ) != null )
				    {
				    	
				    	archivo += linea;
				    	
				    }
				    System.out.println(archivo);
				}
				    catch( Exception e ){ }
				    //finally se utiliza para que si todo ocurre correctamente o si ocurre
				    //algun error se cierre el archivo que anteriormente abrimos
				    finally
				    {
				        try{
				            br.close();
				        }catch( Exception ex ){}
				    }
				   
					//Se va agregando el contenido del archivo a un nodo de una lista
				   
					
			}
		}         //Fin de la clase lista
	

	   
	   
	        