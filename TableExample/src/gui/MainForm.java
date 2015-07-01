package gui;

import components.Tab;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.beans.Beans;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;

import static java.beans.Beans.isDesignTime;

public class MainForm extends JFrame {

    /**
     * @wbp.nonvisual location=123,391
     */
    private final JPanel contentPane = new JPanel();
    private JTabbedPane tabbedPane;
    private Timer timer;
    private JLabel lblTime;

    private Tab panelTamu;

    /**
     * Create the frame.
     */
    public MainForm() {
	initComponents();
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	try {
	    UIManager.setLookAndFeel(new MetalLookAndFeel());
	} catch (UnsupportedLookAndFeelException ignored) {
	}
	EventQueue.invokeLater(() -> {
	    try {
		MainForm frame = new MainForm();
		frame.setVisible(true);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	});
    }

    private void initComponents() {
	DateFormat sdf = DateFormat.getTimeInstance(DateFormat.SHORT);
	timer = new Timer(60000, e -> {
	    lblTime.setText(sdf.format(new Date()));
	});
	timer.start();
	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	setBounds(100, 100, 450, 300);
	setContentPane(contentPane);
	contentPane.setLayout(new BorderLayout(0, 0));
	JPanel panel = new JPanel();
	panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
	contentPane.add(panel, BorderLayout.SOUTH);

	JLabel lblUser = new JLabel("User");

	lblTime = new JLabel(isDesignTime() ? "Time" : sdf.format(new Date()));
	GroupLayout gl_panel = new GroupLayout(panel);
	gl_panel.setHorizontalGroup(
		gl_panel.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
			gl_panel.createSequentialGroup().addComponent(lblUser)
				.addPreferredGap(ComponentPlacement.RELATED, 394, Short.MAX_VALUE)
				.addComponent(lblTime)));
	gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
		.addGroup(gl_panel.createSequentialGroup().addGap(5).addGroup(
			gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(lblTime).addComponent(lblUser))));
	panel.setLayout(gl_panel);

	tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	tabbedPane.setMinimumSize(new Dimension(5, 300));

	contentPane.add(tabbedPane, BorderLayout.CENTER);

	JMenuBar menuBar = new JMenuBar();
	setJMenuBar(menuBar);

	JMenu mnAdmin = new JMenu("Administrasi");
	mnAdmin.setMnemonic('f');
	menuBar.add(mnAdmin);

	JMenuItem mntmLog = new JMenuItem("Log");
	mnAdmin.add(mntmLog);

	JSeparator separator = new JSeparator();
	mnAdmin.add(separator);

	JMenuItem mntmExit = new JMenuItem("Exit");
	mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
	mnAdmin.add(mntmExit);
	mntmExit.addActionListener(e -> dispose());

	JMenu mnTamu = new JMenu("Tamu");
	menuBar.add(mnTamu);

	JMenuItem mntmLihatDaftarTamu = new JMenuItem("Lihat daftar tamu");
	mntmLihatDaftarTamu.addActionListener(e -> {
	    if (panelTamu == null) {
		panelTamu = new TabTamu(tabbedPane);
	    }
	    if (tabbedPane.indexOfComponent(panelTamu) == -1) {
		tabbedPane.add(panelTamu, panelTamu.getName());
		tabbedPane.setTabComponentAt(tabbedPane.indexOfComponent(panelTamu), panelTamu.getTabComponent());
	    } else
		tabbedPane.setSelectedComponent(panelTamu);
	    pack();
	});
	mnTamu.add(mntmLihatDaftarTamu);

	JMenu mnKamar = new JMenu("Kamar");
	menuBar.add(mnKamar);

	JMenuItem mntmLihatDaftarKamar = new JMenuItem("Lihat daftar kamar");
	mnKamar.add(mntmLihatDaftarKamar);

	JMenu mnReservasi = new JMenu("Reservasi");
	menuBar.add(mnReservasi);

	JMenuItem mntmTambahReservasi = new JMenuItem("Tambah reservasi");
	mnReservasi.add(mntmTambahReservasi);

	JMenuItem mntmUbahReservasi = new JMenuItem("Ubah reservasi");
	mnReservasi.add(mntmUbahReservasi);

	JMenuItem mntmLihatDaftarReservasi = new JMenuItem("Lihat daftar reservasi");
	mnReservasi.add(mntmLihatDaftarReservasi);

	JMenu mnBantuan = new JMenu("Bantuan");
	menuBar.add(mnBantuan);

	JLabel lblLogo;
	if (isDesignTime()) {
	    lblLogo = new JLabel("Logo");
	} else {
	    try {
		InputStream is = ClassLoader.getSystemResourceAsStream("res/logo-resized.png");
		lblLogo = new JLabel(new ImageIcon(ImageIO.read(is)));
	    } catch (IOException e) {
		lblLogo = new JLabel("Logo");
		e.printStackTrace();
	    }
	}
	lblLogo.setHorizontalAlignment(SwingConstants.LEFT);

	// contentPane.add(lblLogo, BorderLayout.NORTH);

	JPanel panel_1 = new JPanel();
	FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
	flowLayout.setAlignment(FlowLayout.LEFT);
	contentPane.add(panel_1, BorderLayout.NORTH);

	// Component horizontalStrut = Box.createHorizontalStrut(20);
	// panel_1.add(horizontalStrut);
	panel_1.add(lblLogo);
	if (!Beans.isDesignTime())
	    pack();
    }
}
