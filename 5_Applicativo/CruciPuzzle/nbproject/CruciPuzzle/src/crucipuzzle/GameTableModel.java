package crucipuzzle;

import crucipuzzle.Lettera;
import javax.swing.table.AbstractTableModel;

/**
 * Modello per la tabella dove verrà 
 * mostrato il campo di gioco pieno di lettere.
 * 
 * @author Jacopo Faul
 * @version 01.12.2023
 */
public class GameTableModel extends AbstractTableModel {
    private String[][] puzzleData;

    public GameTableModel(int grandezzaCampo) {
        this.puzzleData = new String[grandezzaCampo][grandezzaCampo];
    }

    @Override
    public int getRowCount() {
        return puzzleData.length;
    }

    @Override
    public int getColumnCount() {
        return puzzleData[0].length;
    }

    @Override
    public Object getValueAt(int row, int column) {
        return puzzleData[row][column];
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        Lettera oggetto = (Lettera)value;
        puzzleData[row][column] = (String)oggetto.lettera;
        fireTableCellUpdated(row, column);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return true;
    }
}

