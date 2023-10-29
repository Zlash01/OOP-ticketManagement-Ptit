package ticketmanagement;

public class Ticket {

    private int price;
    private String type;
    private int id;

    public Ticket(int id, String type, int price) {
        this.price = price;
        this.type = type;
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }
}
