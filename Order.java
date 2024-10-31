import java.util.Comparator;
import java.util.PriorityQueue;


public class Order {
    private Customer customer;
    private Item item;
    private String specialRequest;
    private String orderStatus;
    private int priority;
    private int quantity;
    
    
    public Order(Customer customer, Item item, int quantity){
        this.customer = customer;
        this.item = item;
        this.quantity = quantity;
        priority = customer.getPriority();
        this.specialRequest = "NO SPECIAL REQUEST";
        this.orderStatus = "BOOKED";
    }
    
    
    public Order(Customer customer, Item item, String specialRequest, String orderStatus) {
        this.customer = customer;
        this.item = item;
        this.specialRequest = specialRequest;
        this.orderStatus = orderStatus;
        priority = customer.getPriority();
    }
    
    public Order(Customer customer, Item item, String orderStatus) {
        this.customer = customer;
        this.item = item;
        this.specialRequest = "NO SPECIAL REQUEST";
        this.orderStatus = orderStatus;
        priority = customer.getPriority();
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
    
    public void setOrderStatus(String status){
        this.orderStatus = status;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

