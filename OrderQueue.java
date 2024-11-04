import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class OrderQueue{
    
    public static HashMap<Order, Instant> insertionTimeMap = new HashMap<>();
    
    public static Comparator<Order> orderComparator = (o1, o2) -> {
        int priorityComparison = Integer.compare(o1.getPriority(), o2.getPriority());
        if (priorityComparison == 0) {
            return insertionTimeMap.get(o1).compareTo(insertionTimeMap.get(o2));
        }
        return priorityComparison;
    };
    
    public static PriorityQueue<Order> orderQueue = new PriorityQueue<>(orderComparator);
    
   
    
    public OrderQueue() {}
    
    public void addOrder(Order order) {
        insertionTimeMap.put(order, order.getTimestamp());
        orderQueue.add(order);
    }
    
    
    public void viewOrders() {
        int i=1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(" hh:mm:ss.SSSSS dd-MM-yyyy").withZone(ZoneId.systemDefault());
        PriorityQueue<Order> tempQueue = new PriorityQueue<>(orderQueue);
        System.out.println("-----ORDER QUEUE-----");
        while(!tempQueue.isEmpty()) {
            Order order = tempQueue.poll();
            if(order.getOrderStatus().equals("DONE")) continue;
            System.out.print(i + ".NAME: " + order.getItem().getName() + " || QUANTITY: "
                    + order.getQuantity() + " || SPECIAL REQUEST: " + order.getSpecialRequest() + " || ORDER STATUS: " + order.getOrderStatus());
            if (order.getCustomer().getPriority() == 1) {
                System.out.println(" **(VIP)**");
            } else {
                System.out.println();
            }
            System.out.println("ORDER TIME:" + formatter.format(insertionTimeMap.get(order)));
            System.out.println();
            i++;
        }
        System.out.println("-----END OF ORDER QUEUE-----");
    }
    
    
    
}
