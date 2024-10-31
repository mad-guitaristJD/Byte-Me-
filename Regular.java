import java.util.ArrayList;

public class Regular extends Customer{
    private int priority;
    
    
    public Regular(String email, String password){
        super(email, password);
        priority=2;
    }
    
    @Override
    public int getPriority() {
        return priority;
    }
    
    public void checkout(){
        System.out.println("ENTER PAYMENT DETAILS");
        scanner.nextLine();
        OrderQueue orderQueue = new OrderQueue();
        for(Order order : cart){
            orderQueue.addOrder(order);
            placedOrders.add(order);
        }
        cart.clear();
    }
    
    
    
    
    
    
    
    public void showType(){
        System.out.println("regular");
    }
}
