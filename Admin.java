import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
        JFrame frame = new JFrame("Items Menu");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
        
        String[] columnNames = {"Category", "Name", "Price", "Availability"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        
        for (Item item : Item.items) {
            String availability = item.isAvailable() ? "YES :)" : "NO :(";
            tableModel.addRow(new Object[]{item.getCategory(), item.getName(), item.getPrice(), availability});
        }
        
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        
        JButton addButton = new JButton("View Pending Orders");
        
        addButton.addActionListener(e -> {
            frame.dispose();
            viewOrdersGUI();
            viewOrders();
        });
        
        buttonPanel.add(addButton);
        
        frame.add(buttonPanel, BorderLayout.SOUTH);
        
        
        frame.setVisible(true);
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
        OrderQueue orderQ = new OrderQueue();
        while(true){
            viewItems();
            System.out.println("ENTER THE ITEM NUMBER YOU WANT TO REMOVE");
            choice = scanner.nextInt();
            if (choice==-1) return;
            choice--;
            Item.items.get(choice).printCNPA();
            System.out.println(" REMOVED SUCCESSFULLY");
            System.out.println();
            Item.items.remove(Item.items.get(choice));
            ArrayList<Order> replaced = new ArrayList<>(OrderQueue.orderQueue);
            OrderQueue.orderQueue.clear();
            for(Order order : replaced){
                if(Item.items.contains(order.getItem())) orderQ.addOrder(order);
                else order.setOrderStatus("DENIED");
            }
        }
    }
 
    
    
    public void viewOrdersGUI(){
        JFrame frame = new JFrame("Pending Orders");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLayout(new BorderLayout());
        
        String[] columnNames = {"Name", "Quantity", "Special Request", "Order Status", "Order Time"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss.SSSSS dd-MM-yyyy").withZone(ZoneId.systemDefault());
        PriorityQueue<Order> tempQueue = new PriorityQueue<>(OrderQueue.orderQueue);
        
        while (!tempQueue.isEmpty()) {
            Order order = tempQueue.poll();
            if (order.getOrderStatus().equals("DONE")) continue;
            
            String priority = order.getCustomer().getPriority() == 1 ? "**(VIP)**" : "";
            String orderTime = formatter.format(OrderQueue.insertionTimeMap.get(order));
            tableModel.addRow(new Object[]{
                    order.getItem().getName(),
                    order.getQuantity(),
                    order.getSpecialRequest(),
                    order.getOrderStatus() + " " + priority,
                    orderTime
            });
        }
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        
        JButton button = new JButton("Back");
        buttonPanel.add(button);
        
        button.addActionListener(e -> {
            frame.dispose();
            viewItems();
        });
        
        frame.add(buttonPanel, BorderLayout.SOUTH);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        
        
        
        
        frame.setVisible(true);
    }
    
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
    
    public void processRefunds(){
        ArrayList<Order> cancelledOrders = new ArrayList<>();
        PriorityQueue<Order> orders = OrderQueue.orderQueue;
        int i = 1;
        for(Order order : orders){
            if(order.getOrderStatus().equals("CANCELLED")){
                System.out.print(i+".");
                System.out.println(order);
                cancelledOrders.add(order);
            }
        }
        int choice;
        while(true){
            System.out.println("ENTER THE ITEM NUMBER TO PROCESS REFUND");
            choice = scanner.nextInt();
            scanner.nextLine();
            if(choice==-1) return;
            choice--;
            cancelledOrders.get(choice).setOrderStatus("REFUNDED");
            System.out.println("---REFUNDED---");
        }
        
    }
    
    
    
    public void reportGeneration(){
        HashMap<String, Integer> orderMap = new HashMap<>();
        int max = 0;
        int totalOders = 0;
        ArrayList<String> maxStrings = new ArrayList<>();
        int totalSales = 0;
        ArrayList<Order> orders = new ArrayList<>(OrderQueue.orderQueue);
        for(Order order : orders){
            totalSales+=(order.getQuantity()*order.getItem().getPrice());
            if(orderMap.containsKey(order.getItem().getName())){
                int curr = orderMap.get(order.getItem().getName());
                curr+=order.getQuantity();
                orderMap.put(order.getItem().getName(), curr);
            }
            else{
                orderMap.put(order.getItem().getName(), order.getQuantity());
            }
            totalOders++;
        }
        System.out.println("ITEM\t\tQUANTITY");
        for (Map.Entry<String, Integer> entry : orderMap.entrySet()) {
            int value = entry.getValue();
            System.out.println(entry.getKey() + "\t\t" + value);
            if(max<=value) {
                if(max==value){
                    maxStrings.add(entry.getKey());
                }
                else{
                    maxStrings.clear();
                    max=value;
                    maxStrings.add(entry.getKey());
                }
            }
        }
        System.out.println();
        System.out.println("TOTAL ORDERS: " + totalOders);
        System.out.println("TOTAL SALES: " + totalSales);
        System.out.print("MOST ORDERED: ");
        for(String s : maxStrings){
            System.out.print(s + ", ");
        }
        System.out.println();
    }
    
    
    
    
}
