import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Admin extends User{
    Scanner scanner = new Scanner(System.in);
    
    
    public Admin(String email, String password){
        super(email, password);
    }
    
    public void viewItems(){
        if(Item.items.isEmpty()){
            System.out.println("NO ITEMS IN THE MENU");
            return;
        }
        int i=1;
        for(Item item : Item.items){
            System.out.print(i + ".CATEGORY: " + item.getCategory() + " || NAME: " + item.getName() + " || PRICE: " + item.getPrice() + " || AVAILABLE: ");
            if(item.isAvailable()) System.out.println("YES :)");
            else System.out.println("NO :(");
            i++;
        }
    }
    
    public void addItem(){
        while(true) {
            try {
                System.out.println("ENTER THE CATEGORY OF ITEM; ");
                String category = scanner.nextLine();
                System.out.println("ENTER THE NAME OF ITEM; ");
                String name = scanner.nextLine();
                System.out.println("ENTER THE PRICE OF ITEM; ");
                int price = scanner.nextInt();
                scanner.nextLine();
                Item.items.add(new Item(category,name, price));
                System.out.println("ITEM ADDED SUCCESSFULLY :)");
                System.out.println();
                return;
            } catch (Exception e) {
                System.out.println("INVALID INPUT");
            }
        }
    }
    
    public void updateItem(){
        int choice;
        int updateChoice;
        while(true){
            viewItems();
            System.out.println("--------------ENTER THE CHOICE TO UPDATE--------------");
            choice = scanner.nextInt();
            scanner.nextLine();
            if(choice==-1) return;
            choice--;
            System.out.println("WHAT DO YOU WANT TO UPDATE\n1.CATEGORY\n2.NAME\n3.PRICE\n4.AVAILABILITY(YES/NO)");
            updateChoice = scanner.nextInt();
            if(updateChoice==-1) return;
            scanner.nextLine();
            switch (updateChoice){
                case 1:
                    try{
                        System.out.println("ENTER THE CATEGORY");
                        String category = scanner.nextLine();
                        Item.items.get(choice).setCategory(category);
                    }
                    catch (Exception e){
                        System.out.println("INVALID INPUT");
                    }
                    break;
                case 2:
                    try{
                        System.out.println("ENTER THE NAME");
                        String name = scanner.nextLine();
                        Item.items.get(choice).setName(name);
                    }
                    catch (Exception e){
                        System.out.println("INVALID INPUT");
                    }
                    break;
                case 3:
                    try{
                        System.out.println("ENTER THE PRICE");
                        int price = scanner.nextInt();
                        scanner.nextLine();
                        Item.items.get(choice).setPrice(price);
                    }
                    catch (Exception e){
                        System.out.println("INVALID INPUT");
                    }
                    break;
                case 4:
                    try{
                        boolean bool;
                        System.out.println("ENTER THE AVAILABILITY");
                        String availability = scanner.nextLine();
                        if(availability.equals("yes") || availability.equals("y") || availability.equals("YES")) bool = true;
                        else bool = false;
                        Item.items.get(choice).setAvailability(bool);
                    }
                    catch (Exception e){
                        System.out.println("INVALID INPUT");
                    }
                    break;
            }
        }
    }
    
    public void removeItem(){
        int choice;
        while(true){
            viewItems();
            System.out.println("ENTER THE ITEM NUMBER YOU WANT TO REMOVE");
            choice = scanner.nextInt();
            if (choice==-1) return;
            choice--;
            Item.items.get(choice).printCNPA();
            System.out.println(" REMOVED SUCCESSFULLY");
            Item.items.remove(Item.items.get(choice));
        }
    } //TO BE UPDATED LATER  VERY CRUCIAL JUST REMOVES FROM THE LIST NOT THE ORDER
 
    
    
    
    public void viewOrders(){
        OrderQueue orderQueue = new OrderQueue();
        orderQueue.viewOrders();
    }
    
    private void viewOrders(ArrayList<Order> orders){
        int i=1;
        for(Order order : orders){
            System.out.print(i + ".NAME: " + order.getItem().getName() + " || QUANTITY: "
                    + order.getQuantity() + " || SPECIAL REQUEST: " + order.getSpecialRequest() + " || ORDER STATUS: " + order.getOrderStatus());
            if (order.getCustomer().getPriority() == 1) {
                System.out.println(" **(VIP)**");
            } else {
                System.out.println();
            }
            System.out.println();
            i++;
        }
    }
    
    public void updateStatus(){
        String status = "";
        ArrayList<Order> orders = new ArrayList<>(OrderQueue.orderQueue);
        viewOrders(orders);
        int choice;
        while(true){
            System.out.println("ENTER THE CHOICE NUMBER TO BE UPDATED");
            choice = scanner.nextInt();
            scanner.nextLine();
            if(choice==-1){
                viewOrders(orders);
                return;
            }
            choice--;
            System.out.println("ENTER THE CURRENT STATUS OF THE ORDER (PROCESSED / DONE)");
            status = scanner.nextLine();
            orders.get(choice).setOrderStatus(status);
            System.out.println("UPDATED");
            if(status.equals("DONE")){
                orders.get(choice).getCustomer().placedOrders.remove(orders.get(choice));
                orders.get(choice).getCustomer().oldOrders.add(orders.get(choice));
            }
        }
    }
    
    public void canceledOrders(){
    
    }
    
    
}
