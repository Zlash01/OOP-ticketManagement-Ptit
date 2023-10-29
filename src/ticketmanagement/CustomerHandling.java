package ticketmanagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CustomerHandling {

    private MainFrame mainFrame;

    CustomerHandling(MainFrame main) {
        this.mainFrame = main;

        mainFrame.addCustomerSaveBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSaveBtn();
            }
        });

        mainFrame.addCustomerEditBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleEditBtn();
            }
        });

        mainFrame.addCustomerDBActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSaveDBBtn();
            }
        });
    }

    private void handleSaveBtn() {
        String name = mainFrame.getCustomerName().getText();
        Date dob = mainFrame.getCustomerDob().getDate();
        int typeIndex = mainFrame.getCustomerType().getSelectedIndex();
        String type = "";
        switch (typeIndex) {
            case 0:
                type = "Retail";
                break;
            case 1:
                type = "Team";
                break;
            case 2:
                type = "Online";
                break;
        }

        System.out.println(name + " " + dob + " " + type);

        GlobalData globalData = GlobalData.getInstance();
        globalData.addNewCustomer(name, dob, type);

        notifyTable();
    }

    private void handleEditBtn() {
        String name = mainFrame.getCustomerName().getText();
        Date dob = mainFrame.getCustomerDob().getDate();
        int typeIndex = mainFrame.getCustomerType().getSelectedIndex();
        String type = "";
        switch (typeIndex) {
            case 0:
                type = "Retail";
                break;
            case 1:
                type = "Team";
                break;
            case 2:
                type = "Online";
                break;
        }

        int selectedRow = mainFrame.getCustomerTable().getSelectedRow();
        if (selectedRow != -1) {
            Object value = mainFrame.getCustomerTable().getValueAt(selectedRow, 0);
            int id = Integer.parseInt((String) value);

            GlobalData globalData = GlobalData.getInstance();
            globalData.editCustomer(id, name, dob, type);

            notifyTable();
        } else {
            JOptionPane.showMessageDialog(null, "Please Select a row to delete!");
            return;
        }
    }

    private void notifyTable() {
        DefaultTableModel model = (DefaultTableModel) mainFrame.getCustomerTable().getModel();

        GlobalData globalData = GlobalData.getInstance();
        ArrayList<Customer> data = globalData.getCustomers();

        model.setRowCount(0);
        for (Customer customer : data) {
            int id = customer.getCode();
            String formattedId = String.format("%05d", id);
            Object[] rowData = {formattedId, customer.getFullName(), customer.getDob(), customer.getType()};
            model.addRow(rowData);
        }
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void handleSaveDBBtn() {
        GlobalData globalData = GlobalData.getInstance();
        globalData.writeToFile();
    }
}
