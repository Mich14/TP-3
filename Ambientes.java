//Importa librerias para uso de la interfaz grafica
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;


//Creacion de la clase ambientes
public class Ambientes extends JFrame implements ActionListener {

	JButton b_volver = new JButton("Volver");
	
	public Ambientes () {
		
	//Creacion de la ventana y caracteristicas
		Container Ventana = getContentPane(); 
		setBounds(200, 200, 1000, 500);
		setTitle("Manejador de tablas de símbolos");
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	//Creacion de paneles
		JPanel p_principal = new JPanel();
		p_principal.setLayout(new BorderLayout());
		//p_principal.setBackground(Color.white);
		
		JPanel p_central = new JPanel();
		p_central.setLayout(new GridLayout(1,2));
		//p_central.setBackground(Color.red);
		
		JPanel p_estatico = new JPanel();
		p_estatico.setBackground(new Color(139,28,98));
		
	//Crea la tabla del ambiente estático
		Tabla t_estatico = new Tabla();
		
		Tabla.filas = new Object [100] [2]; 
				
		int i = 0; // Contador de filas
		int j = 0; // Contador de columnas
		Nodo actual = Identifica.LResultado.Primero;
		
	// Ciclo para llenar la matriz con los datos y los tipos		
		while(actual != null){ 
					
			Tabla.filas [i][j] = actual.dato; 
			j++;
			Tabla.filas [i][j] = actual.tipo;
			i++;
			j = 0;
			actual = actual.siguiente;
		}
		//Se crean las columnas de la tabla
		Tabla.columnas = new String [] {"Identificador", "Tipo"};

				
		//se crea la tabla con el defaultablemodel 
		JTable tabla = new JTable(t_estatico);
		tabla.setForeground(Color.white);
		tabla.setBackground(Color.gray);
		
		//Creamos un JscrollPane y se agrega a la tabla 
		JScrollPane scrollPane = new JScrollPane(tabla, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getViewport().setBackground(new Color (12, 93, 165));
		
		//-------------------Fin de caracetristicas de la tabla estatica-----------------------------------------------------\\ 				
		
		JPanel p_dinamico = new JPanel();
		p_dinamico.setBackground(new Color(139,28,98));
		
		//Creamos la tabla del ambiente dinamico 
		Tabla1 t_dinamico = new Tabla1(); 
		
		Tabla1.filas = new Object [100] [2]; // 10 = contador de variables
		
		int k = 0; // Contador de filas
		int l = 0; // Contador de columnas
		Nodo act = Identifica.LResultado.Primero;
			
	
		// Ciclo para llenar la matriz con los datos y los valores
		while(act != null){ 
					
			Tabla1.filas [k][l] = act.dato; 
			l++;
			Tabla1.filas [k][l] = act.valor;
			
			k++;
			
			l = 0;
			
			act = act.siguiente;
		} 
		//Se crean las columnas de la tabla
		Tabla1.columnas = new String [] {"Identificador", "Valor"};
				
		//se crea la tabla con el defaultablemodel 
		JTable tabla2 = new JTable(t_dinamico);
		tabla2.setForeground(Color.white);
		tabla2.setBackground(Color.gray);
						
		//Creamos un JscrollPane y se agrega a la tabla 
		JScrollPane scroll = new JScrollPane(tabla2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.getViewport().setBackground(new Color (12, 93, 165));
		
		
	//Creacion de paneles		
		JPanel p_norte = new JPanel();
		p_norte.setBackground(Color.gray);
		p_norte.setLayout(new GridLayout(1,2));
		
		JLabel l_estatico = new JLabel ("           Ambiente estático");
		l_estatico.setFont( new Font( "Arial rounded MT Bold", 12, 30 ) );
		l_estatico.setForeground(new Color (139, 28, 98));
		
		JLabel l_dinamico = new JLabel ("           Ambiente dinámico");
		l_dinamico.setFont( new Font( "Arial rounded MT Bold", 12, 30 ) );
		l_dinamico.setForeground(new Color (139, 28, 98));
		
		JPanel p_sur = new JPanel();
		p_sur.setBackground(new Color(139,28,98));
		
	//Caracteristicas del boton
		b_volver.addActionListener(this);
		b_volver.setBackground(new Color (139, 28, 98));
		b_volver.setForeground(Color.white);
		b_volver.setFont(new java.awt.Font("Segoe Print", Font.BOLD, 15));
		
	//Se agregan los componentes a la ventana
		Ventana.add(p_principal);
		p_principal.add(p_central, BorderLayout.CENTER);
		
		p_central.add(p_estatico);
		
		p_central.add(p_dinamico);
		
		p_estatico.add(scrollPane);
		
		p_dinamico.add(scroll);
		
		p_principal.add(p_norte, BorderLayout.NORTH);
		
		p_norte.add(l_estatico);
		p_norte.add(l_dinamico);
		
		p_principal.add(p_sur, BorderLayout.SOUTH);
		
		p_sur.add(b_volver);
		
		setVisible(true);
	}
	
	//Para efectuar un evento sobre un boton
	public void actionPerformed(ActionEvent e) {
	
		if (e.getSource() == b_volver) {
			setVisible(false);
			Identifica.LResultado.Primero = null;
			new LeerArchivo();
		}
			
	}

}
