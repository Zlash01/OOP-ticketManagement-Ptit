package ticketmanagement;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TabChangeListener implements ChangeListener {

    private MainFrame mainFrame;

    public TabChangeListener(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
        int selectedIndex = sourceTabbedPane.getSelectedIndex();

        if (selectedIndex == 0) {
            // Code to run when Tab 1 is selected
            System.out.println("Tab 1 selected");
        } else if (selectedIndex == 1) {
            // Code to run when Tab 2 is selected
            System.out.println("Tab 2 selected");
        } else if (selectedIndex == 2) {
            System.out.println("Tabs 3 selected");
        }
    }
}
