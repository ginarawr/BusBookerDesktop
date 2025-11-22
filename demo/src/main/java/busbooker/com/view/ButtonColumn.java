package busbooker.com.view;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class ButtonColumn extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
    private JTable table;
    private JButton button;
    private int column;
    private Object value;

    public ButtonColumn(JTable table, int column) {
        this.table = table;
        this.column = column;
        button = new JButton("Pilih Bus");
        button.setFocusPainted(false);
        button.addActionListener(this);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.value = value;
        return button;
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.value = value;
        return button;
    }

    public Object getCellEditorValue() {
        return value;
    }

    public void actionPerformed(ActionEvent e) {
        // Action saat tombol ditekan
        int row = table.getSelectedRow();
        if (row != -1) {
            // Ambil data dari baris yang dipilih
            String busName = (String) table.getValueAt(row, 0); // Nama bus
            System.out.println("Bus yang dipilih: " + busName); // Debug output, bisa diganti dengan aksi lain seperti membuka form pemesanan
        }
        fireEditingStopped();
    }
}

