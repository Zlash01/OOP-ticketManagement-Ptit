/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ticketmanagement;

import java.util.Date;

/**
 *
 * @author Lenovo
 */
public class Bills {

    private int customerID;
    private int ticketID;
    private Date date;
    private int numberOfTickets;
    private int total;
    private int individualPrice;

    Bills(Date date, int cusId, int ticketID, int num, int invi) {
        this.customerID = cusId;
        this.date = date;
        this.ticketID = ticketID;
        this.numberOfTickets = num;
        this.individualPrice = invi;
        this.calculateMoney();
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getTicketID() {
        return ticketID;
    }

    public Date getDate() {
        return date;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public int getTotal() {
        return total;
    }

    public void addTicket(int n) {
        this.numberOfTickets = this.numberOfTickets + n;
    }

    public void calculateMoney() {
        this.total = this.numberOfTickets * this.individualPrice;
    }
}
