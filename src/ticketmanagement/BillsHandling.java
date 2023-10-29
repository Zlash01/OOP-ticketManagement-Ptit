package ticketmanagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lenovo
 */
public class BillsHandling {

    private MainFrame mainFrame;

    BillsHandling(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        mainFrame.addBuyTicketAL(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBuyBtn();
            }
        });

        mainFrame.addBuySortDateAL(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSortDate();
            }
        });

        mainFrame.addBuySortMoneyAL(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSortMoney();
            }
        });

        mainFrame.addBuySaveDBAL(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GlobalData globalData = GlobalData.getInstance();
                globalData.writeToFile();
            }
        });
    }

    public void handleBuyBtn() {
        int individualPrice = -1;
        String textCusID = mainFrame.getBuyTicketCusID().getText();
        int cusID = Integer.parseInt(textCusID);
        String textTicketID = mainFrame.getBuyTicketID().getText();
        int ticketID = Integer.parseInt(textTicketID);
        //check if the ticketID even exsit in the first place
        GlobalData globalData = GlobalData.getInstance();
        ArrayList<Ticket> tickets = globalData.getTickets();
        //iterate through tickets to check
        boolean ii = false;
        for (Ticket ticket : tickets) {
            if (ticket.getId() == ticketID) {
                individualPrice = ticket.getPrice();
                ii = true;
                break;
            }
        }
        if (!ii) {
            JOptionPane.showMessageDialog(null, "The ticket types doesnt exsist!");
            return;
        }

        int num = mainFrame.getBuyTicketCB().getSelectedIndex() + 1;
        Date date = mainFrame.getBuyDate().getDate();

        ArrayList<Bills> data = globalData.getBills();
        boolean fail = false;

        for (Bills bill : data) {
            if (bill.getCustomerID() == cusID && bill.getTicketID() == ticketID && bill.getDate().equals(date)) {
                System.out.println("debugging 2");
                if (bill.getNumberOfTickets() + num <= 5) {
                    bill.addTicket(num);
                } else {
                    fail = true;
                    break;
                }
            }
        }

        if (!fail) {

            Bills newBill = new Bills(date, cusID, ticketID, num, individualPrice);
            data.add(newBill);
        } else {
            JOptionPane.showMessageDialog(null, "You can only buy 5 tickets of the same types per day!");
        }

        notifyTable();
    }

    public void handleSortDate() {
        GlobalData globalData = GlobalData.getInstance();
        globalData.sortBillsByDate();
        notifyTable();
    }

    public void handleSortMoney() {
        GlobalData globalData = GlobalData.getInstance();
        globalData.sortBillsbyMoney();
        notifyTable();
    }

    private void notifyTable() {
        DefaultTableModel model = (DefaultTableModel) mainFrame.getBuyTable().getModel();

        GlobalData globalData = GlobalData.getInstance();
        ArrayList<Bills> data = globalData.getBills();

        model.setRowCount(0);
        for (Bills bills : data) {

            int cus_id = bills.getCustomerID();
            String cus_formattedId = String.format("%05d", cus_id);
            int ticket_id = bills.getTicketID();
            String ticket_formattedId = String.format("%03d", ticket_id);
            Object[] rowData = {bills.getDate(), cus_formattedId, ticket_formattedId, bills.getNumberOfTickets(), bills.getTotal()};
            model.addRow(rowData);
        }
        mainFrame.revalidate();
        mainFrame.repaint();
    }

}
