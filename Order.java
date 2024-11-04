import java.time.Instant;
import java.util.Comparator;
import java.util.PriorityQueue;


public class Order {
    private Customer customer;
    private Item item;
    private String specialRequest;
    private String orderStatus;
    private int priority;
    private int quantity;
    private Instant timestamp;
    
    
    public Order(Customer customer, Item item, int quantity){
        this.customer = customer;
        this.item = item;
        this.quantity = quantity;
        priority = customer.getPriority();
        this.specialRequest = "NO SPECIAL REQUEST";
        this.orderStatus = "BOOKED";
        this.timestamp = Instant.now();
    }
    
    public Order(Customer customer, Item item, int quantity, String specialRequest){
        this.customer = customer;
        this.item = item;
        this.quantity = quantity;
        priority = customer.getPriority();
        this.specialRequest = specialRequest;
        this.orderStatus = "BOOKED";
        this.timestamp = Instant.now();
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public Item getItem() {
        return item;
    }
    
    public String getSpecialRequest() {
        return specialRequest;
    }
    
    public String getOrderStatus() {
        return orderStatus;
    }
    
    public int getPriority() {
        return priority;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public Instant getTimestamp() {
        return timestamp;
    }
    
    public void setOrderStatus(String status){
        this.orderStatus = status;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    @Override
    public String toString() {
        return "ITEM: " + getItem().getName() + " || QUANTITY: " + getQuantity() + " || STATUS: " + getOrderStatus();
    }
}

