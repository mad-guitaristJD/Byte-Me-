public class Vip extends Customer{
    private int priority;
    
    public Vip(String email, String password){
        super(email, password);
        priority=1;
    }
    
    @Override
    public int getPriority(){
        return priority;
    }
    
    public void setPriority(int priority){
        this.priority=priority;
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
        System.out.println("vip");
    }
}
