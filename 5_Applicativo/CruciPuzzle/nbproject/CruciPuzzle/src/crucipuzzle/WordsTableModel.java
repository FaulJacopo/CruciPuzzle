package crucipuzzle;

import crucipuzzle.Lettera;
import javax.swing.table.AbstractTableModel;

/**
 * Modello per la tabella contenente le parole.
 * 
 * @author Jacopo Faul
 * @version 01.12.2023
 */
public class WordsTableModel extends AbstractTableModel {
    private String[][] parole;

    public WordsTableModel(int numeroRighe, int numeroColonne) {
        this.parole = new String[numeroRighe][numeroColonne];
    }

    @Override
    public int getRowCount() {
        return parole.length;
    }

    @Override
    public int getColumnCount() {
        return parole[0].length;
    }

    @Override
    public Object getValueAt(int row, int column) {
        return parole[row][column];
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        parole[row][column] = (String)value;
        fireTableCellUpdated(row, column);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return true;
    }
}