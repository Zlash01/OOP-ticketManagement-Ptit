package ticketmanagement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import javax.swing.JOptionPane;

public class GlobalData {

    private ArrayList<Ticket> tickets = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Bills> bills = new ArrayList<>();
    private int codeCustomerGen = 1;
    private int codeTicketGen = 1;

    private static GlobalData instance;

    private GlobalData() {
    }

    public static GlobalData getInstance() {
        if (instance == null) {
            instance = new GlobalData();
        }
        return instance;
    }

    //customer
    public void addNewCustomer(String fullName, Date dob, String type) {
        int code = codeCustomerGen++;

        Customer customer = new Customer(code, fullName, dob, type);
        customers.add(customer);
    }

    public void editCustomer(int id, String fullName, Date dob, String type) {
        removeCustomer(id);

        int code = id;
        Customer customer = new Customer(code, fullName, dob, type);
        customers.add(customer);
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void removeCustomer(int id) {
        for (Customer cus : customers) {
            if (cus.getCode() == id) {
                customers.remove(cus);
                break;
            }
        }
    }

    //ticket
    public void addNewTicket(String type, int price) {
        boolean ticketExists = false;

        // Check if a ticket with the same type already exists
        for (Ticket ticket : tickets) {
            if (ticket.getType().equals(type)) {
                ticketExists = true;
                break;
            }
        }

        if (ticketExists) {
            JOptionPane.showMessageDialog(null, "The ticket type already exists!");
        } else {
            int code = codeTicketGen++;
            Ticket t = new Ticket(code, type, price);
            tickets.add(t);
            JOptionPane.showMessageDialog(null, "Added!");
        }
    }

    public void editTicket(int id, String type, int price) {
        removeTicket(id);

        Ticket ticket = new Ticket(id, type, price);
        tickets.add(ticket);
    }

    public void removeTicket(int id) {
        for (Ticket ticket : tickets) {
            if (ticket.getId() == id) {
                tickets.remove(ticket);
                break;
            }
        }
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void sortBillsByDate() {
        Collections.sort(bills, new Comparator<Bills>() {
            @Override
            public int compare(Bills bill1, Bills bill2) {
                return bill2.getDate().compareTo(bill1.getDate());
            }
        });
    }

    public void sortBillsbyMoney() {
        Collections.sort(bills, new Comparator<Bills>() {
            @Override
            public int compare(Bills bill1, Bills bill2) {
                return Integer.compare(bill2.getTotal(), bill1.getTotal());
            }
        });
    }

    public void sortTicketByPrice() {
        Collections.sort(tickets, new Comparator<Ticket>() {
            @Override
            public int compare(Ticket ticket1, Ticket ticket2) {
                // Compare prices in descending order
                return Integer.compare(ticket2.getPrice(), ticket1.getPrice());
            }
        });
    }

    public ArrayList<Bills> getBills() {
        return bills;
    }

    public void writeToFile() {
        try (FileWriter fileWriter = new FileWriter("all_data.txt"); BufferedWriter writer = new BufferedWriter(fileWriter)) {
            // Write the tickets data
            for (Ticket ticket : tickets) {
                writer.write("Ticket," + ticket.getId() + "," + ticket.getType() + "," + ticket.getPrice());
                writer.newLine();
            }

            // Write the customers data
            for (Customer customer : customers) {
                writer.write("Customer," + customer.getCode() + "," + customer.getFullName() + "," + customer.getDob() + "," + customer.getType());
                writer.newLine();
            }

            // Write the bills data
            for (Bills bill : bills) {
                writer.write("Bill," + bill.getCustomerID() + "," + bill.getTicketID() + "," + bill.getDate() + "," + bill.getNumberOfTickets() + "," + bill.getTotal());
                writer.newLine();
            }

            JOptionPane.showMessageDialog(null, "Done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
