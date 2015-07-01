package model;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class EnumTableEditor<T extends Enum<T>> extends AbstractCellEditor
	implements TableCellEditor {
    private JComboBox<T> editor;
    private Object currentData;

    public EnumTableEditor(Class<T> clazz) {
	editor = new JComboBox<>(clazz.getEnumConstants());
	editor.addActionListener(ev -> {
	    currentData = editor.getSelectedItem().toString();
	    fireEditingStopped();
	});

    }

    @Override
    public Object getCellEditorValue() {
	return currentData;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
	    boolean isSelected, int row, int column) {
	editor.setSelectedItem(value);
	return editor;

    }

}
