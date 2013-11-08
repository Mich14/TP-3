import javax.swing.table.AbstractTableModel;

class Tabla extends AbstractTableModel {
	
	static Object[][] filas;
	static String[] columnas;
	
	//static int x;
	
	public int getColumnCount() {
		return columnas.length;
	}

	public int getRowCount() {
		return filas.length;
	}

	//retornamos el elemento indicado
	public String getColumnName(int col) {
		return columnas[col];
	 }
	
	public Object getValueAt(int row, int col) {
		return filas[row][col];
	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}
	
	public void setValueAt(Object value, int row, int col) {
		filas[row][col] = value;
		fireTableCellUpdated(row, col);
	}

}