package com.diego.mediateca.app;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class MaterialTableModel extends AbstractTableModel {
    private final String[] columns = {"Código", "Título", "Autor/Director", "Año", "Unidades"};
    private List<Object[]> rows = new ArrayList<>();

    public void setData(List<Object[]> data) {
        this.rows = data;
        fireTableDataChanged();
    }

    @Override public int getRowCount() { return rows.size(); }
    @Override public int getColumnCount() { return columns.length; }
    @Override public String getColumnName(int c) { return columns[c]; }
    @Override public Object getValueAt(int r, int c) { return rows.get(r)[c]; }
}
