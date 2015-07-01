package gui;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import bean.Tamu;
import bean.Tamu.StatusKawin;
import components.Tab;
import components.TabComponent;
import dao.TamuDAO;
import model.ClassTableModel;
import model.EnumTableEditor;

public class TabTamu extends Tab {
    private JTable table;

    /**
     * Create the panel.
     */
    public TabTamu(JTabbedPane pane) {
	super("Daftar Tamu", pane);
	setLayout(new BorderLayout(5, 5));

	JScrollPane scrollPane = new JScrollPane();
	add(scrollPane, BorderLayout.CENTER);

	table = new JTable();
	scrollPane.setViewportView(table);
	ClassTableModel<Tamu> model = new ClassTableModel<>(Tamu.class);
	table.setModel(model);
	table.setDefaultEditor(Tamu.JenisKelamin.class, new EnumTableEditor<>(Tamu.JenisKelamin.class));
	table.setDefaultEditor(StatusKawin.class, new EnumTableEditor<>(StatusKawin.class));
	// model.addTableModelListener(e -> {
	// int type = e.getType();
	// int row = e.getLastRow();
	// int column = e.getColumn();
	// System.err.println(model.getValueAt(row, column));
	// });
	TamuDAO.Fetcher f = new TamuDAO.Fetcher(model);
	f.execute();

    }
}
