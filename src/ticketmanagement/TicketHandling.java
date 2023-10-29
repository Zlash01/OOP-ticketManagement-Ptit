package ticketmanagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TicketHandling {

    private MainFrame mainFrame;

    TicketHandling(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        mainFrame.addTicketSaveBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSaveBtn();
            }
        });

        mainFrame.addTicketEditBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleEditBtn();
            }
        });

        mainFrame.addTicketDBActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GlobalData globalData = GlobalData.getInstance();
                globalData.writeToFile();
            }
        });

        mainFrame.addTicketSortActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GlobalData globalData = GlobalData.getInstance();
                globalData.sortTicketByPrice();
                notifyTable();
            }
        });
    }

    private void handleSaveBtn() {
        String priceText = mainFrame.getCreateTicketPrice().getText();
        int price = Integer.parseInt(priceText);

        int typeIndex = mainFrame.getCreateTicketType().getSelectedIndex();
        String type = "";
        switch (typeIndex) {
            case 0 ->
                type = "Economy";
            case 1 ->
                type = "Premium Economy";
            case 2 ->
                type = "Bussiness";
            case 3 ->
                type = "First Class";
            default ->
                throw new AssertionError();
        }

        GlobalData globalData = GlobalData.getInstance();
        globalData.addNewTicket(type, price);

        System.out.println("debugging 1");
        notifyTable();
    }

    private void handleEditBtn() {
        String priceText = mainFrame.getCreateTicketPrice().getText();
        int price = Integer.parseInt(priceText);

        int typeIndex = mainFrame.getCreateTicketType().getSelectedIndex();
        String type = "";
        switch (typeIndex) {
            case 0 ->
                type = "Economy";
            case 1 ->
                type = "Premium Economy";
            case 2 ->
                type = "Bussiness";
            case 3 ->
                type = "First Class";
            default ->
                throw new AssertionError();
        }

        DefaultTableModel model = (DefaultTableModel) mainFrame.getCreateTicketTable().getModel();
        int selectedRow = mainFrame.getCreateTicketTable().getSelectedRow();
        if (selectedRow != -1) {
            Object value = mainFrame.getCreateTicketTable().getValueAt(selectedRow, 0);
            int id = Integer.parseInt((String) value);

            GlobalData globalData = GlobalData.getInstance();
            globalData.editTicket(id, type, price);

            notifyTable();
        } else {
            JOptionPane.showMessageDialog(null, "Please Select a row to edit!");
        }
    }

    private void notifyTable() {
        DefaultTableModel model = (DefaultTableModel) mainFrame.getCreateTicketTable().getModel();

        GlobalData globalData = GlobalData.getInstance();
        ArrayList<Ticket> data = globalData.getTickets();

        model.setRowCount(0);
        for (Ticket ticket : data) {
            int id = ticket.getId();
            String formattedId = String.format("%03d", id);
            Object[] rowData = {formattedId, ticket.getType(), ticket.getPrice()};
            model.addRow(rowData);
        }
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}
