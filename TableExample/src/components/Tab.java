package components;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Tab extends JPanel {
    private Component tabComponent;

    public Component getTabComponent() {
	return tabComponent;
    }

    public Tab(String title, JTabbedPane pane) {
	setName(title);
	tabComponent = new TabComponent(getName(), pane);
    }
}
