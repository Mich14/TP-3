//Se importa libreria para crear una tabla
import javax.swing.table.AbstractTableModel;

class Tabla1 extends AbstractTableModel {

	static Object[][] filas;
	static String[] columnas;
	
	//Obtiene la cantidad de columna de una tabla
	public int getColumnCount() {
		return columnas.length;
	}
	
	//Obtiene la cantdad de fila de una tabla
	public int getRowCount() {
		return filas.length;
	}

	//Obtiene una columna de una tabla
	public String getColumnName(int col) {
		return columnas[col];
	}
	
	//Obtiene una fila de una tabla
	public Object getValueAt(int row, int col) {
		 return filas[row][col];
	}
	
	//Permite que la celda obtenida no puede ser editable
	public boolean isCellEditable(int row, int col) {
		return false;
	}
	
	//Permite actualizar en caso de que se requiera los datos de la tabla
	public void setValueAt(Object value, int row, int col) {
		filas[row][col] = value;
		fireTableCellUpdated(row, col);
	}
}
