package gui;

import java.awt.Component;

import javax.swing.JTabbedPane;

import components.Tab;
import model.ClassTableModel;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import bean.Kamar;

public class TabKamar extends Tab {
    private JTable table;

    public TabKamar(JTabbedPane pane) {
	super("Daftar kamar", pane);
	setLayout(new BorderLayout(5, 5));

	JScrollPane scrollPane = new JScrollPane();
	add(scrollPane, BorderLayout.CENTER);

	table = new JTable();
	scrollPane.setViewportView(table);

	table.setModel(new ClassTableModel<>(Kamar.class));
	// TODO Auto-generated constructor stub
    }
}
