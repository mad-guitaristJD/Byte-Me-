import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Controller {
    protected User user = null;
    private Map<String, Regular> regularCustomers = new HashMap<>();
    private Map<String, Vip> vipCustomers = new HashMap<>();
    private final String adminEmail = "admin";
    private final String adminPassword = "admin";
    private Admin admin = new Admin(adminEmail, adminPassword);
    Scanner scanner = new Scanner(System.in);
    
    public Controller(){
        loadUsers();
        Regular regular1 = regularCustomers.get("regularUser");
        Vip vip1 = vipCustomers.get("vipUser");
        //Initialization of few items
        Item.items.add(new Item("snacks", "samosa", 10));
        Item.items.add(new Item("snacks", "patty", 20, false));
        Item.items.add(new Item("snacks", "burger", 30));
        Item.items.add(new Item("lunch", "special thali", 70));
        Item.items.add(new Item("lunch", "thali", 60));
        Item.items.add(new Item("drinks", "cold drink", 40));
        
        OrderQueue orderQueue = new OrderQueue();
        orderQueue.addOrder(new Order(regular1, Item.items.getFirst(), 1));
        orderQueue.addOrder(new Order(vip1, Item.items.get(5), 1));
        orderQueue.addOrder(new Order(regular1, Item.items.get(2), 2));
        orderQueue.addOrder(new Order(vip1, Item.items.getFirst(), 2));
        orderQueue.addOrder(new Order(regular1, Item.items.get(3), 3));
        
        
    }
    
    public Map<String, Regular> getRegularCustomers() {
        return regularCustomers;
    }
    
    public Map<String, Vip> getVipCustomers() {
        return vipCustomers;
    }
    
    private void loadUsers(){
        try(BufferedReader br = new BufferedReader(new FileReader("users.txt"))){
            String line;
            while((line = br.readLine()) !=null){
                String[] parts = line.split(",");
                if(parts[0].equals("VIP")){
                    if(!vipCustomers.containsKey(parts[1])){
                        vipCustomers.put(parts[1], new Vip(parts[1], parts[2]));
                    }
                }
                else if(parts[0].equals("REGULAR")){
                    if(!regularCustomers.containsKey(parts[1])){
                        regularCustomers.put(parts[1], new Regular(parts[1], parts[2]));
                    }
                }
            }
        }
        catch (Exception e){
            System.out.println("SOME EXCEPTION OCCURRED");
        }
    }
    
    private void addUser(User user, String type){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt", true))){
            bw.write(type + "," + user.getEmail() + "," + user.getPassword());
            bw.newLine();
        }
        catch (Exception e){
            System.out.println("SOME EXCEPTION OCCURRED");
        }
        
    }
    
    public void register(){
        String email;
        System.out.println("ENTER EMAIL: ");
        email = scanner.nextLine();
        String password;
        System.out.println("ENTER PASSWORD: ");
        password = scanner.nextLine();
        System.out.println("CHOOSE\n1.VIP (PRIORITY ORDER)\n2.REGULAR");
        int choice = scanner.nextInt();
        if(choice==1){
            Vip vip = new Vip(email, password);
            addUser(vip, "VIP");
        }
        else if(choice==2){
            Regular regular = new Regular(email, password);
            addUser(regular, "REGULAR");
        }
        System.out.println("-----REGISTERED-----");
        loadUsers();
    }
    
    public void login(){
        int choice;
        while(true){
            System.out.println("1.LOGIN AS CUSTOMER\n2.LOGIN AS ADMIN");
            choice = scanner.nextInt();
            scanner.nextLine();
            if(choice==-1){
                return;
            }
            else if(choice==1){
                if(!loginCustomer()){
                    System.out.println("INVALID LOGIN");
                }
                else{
                    System.out.println("LOGIN SUCCESSFUL");
                    customerMenu();
                }
            }
            else if(choice==2){
                if(!loginAdmin()){
                    System.out.println("INVALID LOGIN");
                }
                else{
                    System.out.println("LOGIN SUCCESSFUL");
                    adminMenu();
                }
            }
            else{
                System.out.println("ENTER A VALID OPTION");
            }
        }
    }
    
    
    public boolean loginCustomer(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("ENTER EMAIL");
        String email = scanner.nextLine();
        System.out.println("ENTER PASSWORD");
        String password = scanner.nextLine();
        Customer customer = regularCustomers.get(email);
        if(customer!=null && customer.getPassword().equals(password)){
            user = regularCustomers.get(email);
            return true;
        }
        customer = vipCustomers.get(email);
        if(customer!=null && customer.getPassword().equals(password)){
            System.out.println("*****VIP MEMBER*****");
            user = vipCustomers.get(email);
            return true;
        }
        return false;
    }
    
    public boolean loginAdmin(){
        System.out.println("ENTER EMAIL");
        String email = scanner.nextLine();
        System.out.println("ENTER PASSWORD");
        String password = scanner.nextLine();
        if(adminEmail.equals(email) && adminPassword.equals(password)){
            user = admin;
            return true;
        }
        return false;
    }
    
    public void customerMenu(){
        Customer customer = (Customer) user;
        int choice;
        while(true){
            System.out.println("---------------PLEASE CHOOSE---------------");
            if(customer instanceof Regular) System.out.println("1.BROWSE MENU\n2.VIEW CART\n3.ORDER MANAGEMENT\n4.LEAVE A REVIEW");
            else System.out.println("1.BROWSE MENU\n2.VIEW CART\n3.TRACK ORDER\n4.LEAVE A REVIEW");
            choice = scanner.nextInt();
            scanner.nextLine();
            if(choice==-1) break;
            switch (choice){
                case 1:
                    int internalChoice;
                    while(true){
                        System.out.println("1.VIEW ALL ITEMS\n2.SEARCH ITEMS\n3.FILTER BY CATEGORY\n4.SORT BY PRICE");
                        internalChoice = scanner.nextInt();
                        if(internalChoice==-1) break;
                        switch (internalChoice){
                            case 1:
                                customer.viewItems();
                                break;
                            case 2:
                                customer.searchItems();
                                break;
                            case 3:
                                customer.filterCategory();
                                break;
                            case 4:
                                customer.sortPrice();
                                break;
                            default:
                                System.out.println("INVALID INPUT");
                        }
                    }
                case 2:
                    while(true){
                        System.out.println("1.ADD ITEM\n2.MODIFY QUANTITY\n3.REMOVE ITEMS\n4.VIEW TOTAL\n5.CHECKOUT");
                        internalChoice = scanner.nextInt();
                        if(internalChoice==-1) break;
                        switch (internalChoice){
                            case 1:
                                customer.addOrder();
                                break;
                            case 2:
                                customer.modifyQuantity();
                                break;
                            case 3:
                                customer.removeOrder();
                                break;
                            case 4:
                                customer.viewTotal();
                                break;
                            case 5:
                                customer.checkout();
                                break;
                            default:
                                System.out.println("INVALID INPUT");
                        }
                    }
                    break;
                case 3:
                    while(true){
                        System.out.println("1.VIEW ORDER STATUS\n2.CANCEL ORDER\n3.ORDER HISTORY");
                        internalChoice = scanner.nextInt();
                        if(internalChoice==-1) break;
                        switch (internalChoice){
                            case 1:
                                customer.viewOrderStatus();
                                break;
                            case 2:
                                customer.cancelOrder();
                                break;
                            case 3:
                                customer.viewOldOrders();
                                break;
                            default:
                                System.out.println("INVALID INPUT");
                        }
                    }
                    break;
                case 4:
                    while(true){
                        System.out.println("1.PROVIDE REVIEW\n2.VIEW REVIEWS");
                        choice = scanner.nextInt();
                        scanner.nextLine();
                        if(choice==-1) break;
                        if(choice==1) customer.addReview();
                        else if(choice==2) customer.viewReviews();
                        else System.out.println("INVALID INPUT");
                    }
                    break;
                default:
                    System.out.println("INVALID INPUT");
            }
        }
    }
    
    public void adminMenu() {
        Admin admin = (Admin) user;
        int choice;
        while (true) {
            System.out.println("---------------PLEASE CHOOSE---------------");
            System.out.println("1.MENU MANAGEMENT\n2.ORDER MANAGEMENT\n3.REPORT GENERATION");
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == -1) break;
            switch (choice) {
                case 1:
                    while (true) {
                        System.out.println("---------------PLEASE CHOOSE---------------");
                        System.out.println("1.VIEW ITEMS\n2.ADD NEW ITEM\n3.UPDATE EXISTING ITEMS\n4.REMOVE ITEM");
                        choice = scanner.nextInt();
                        scanner.nextLine();
                        if (choice == -1) break;
                        switch (choice) {
                            case 1:
                                admin.viewItems();
                                break;
                            case 2:
                                admin.addItem();
                                break;
                            case 3:
                                admin.updateItem();
                                break;
                            case 4:
                                admin.removeItem();
                                break;
                            default:
                                System.out.println("INVALID CHOICE");
                        }
                    }
                    break;
                case 2:
                    while (true) {
                        System.out.println("---------------PLEASE CHOOSE---------------");
                        System.out.println("1.VIEW PENDING ORDERS\n2.UPDATE ORDER STATUS\n3.PROCESS REFUNDS");
                        choice = scanner.nextInt();
                        scanner.nextLine();
                        if (choice == -1) break;
                        switch (choice) {
                            case 1:
                                admin.viewOrders();
                                break;
                            case 2:
                                admin.updateStatus();
                                break;
                            case 3:
                                admin.processRefunds();
                                break;
                            default:
                                System.out.println("INVALID CHOICE");
                        }
                    }
                    break;
                case 3:
                    admin.reportGeneration();
                    break;
                default:
                    System.out.println("INVALID CHOICE");
                    break;
                    
            }
        }
    }
    
    
    public void login(boolean forTest, Scanner scanner){
        int choice;
        System.out.println("1.LOGIN AS CUSTOMER\n2.LOGIN AS ADMIN");
        choice = scanner.nextInt();
        scanner.next();
        if(choice==-1){
            return;
        }
        if(choice==1){
            if(!loginCustomer(scanner)){
                System.out.println("INVALID LOGIN");
            }
            else{
                System.out.println("LOGIN SUCCESSFUL");
            }
        }
        else{
            System.out.println("ENTER A VALID OPTION");
        }
    }
    
    public boolean loginCustomer(Scanner scanner){
        System.out.println("ENTER EMAIL");
        String email = scanner.nextLine();
        System.out.println("ENTER PASSWORD");
        String password = scanner.nextLine();
        Customer customer = regularCustomers.get(email);
        if(customer!=null && customer.getPassword().equals(password)){
            user = regularCustomers.get(email);
            return true;
        }
        customer = vipCustomers.get(email);
        if(customer!=null && customer.getPassword().equals(password)){
            System.out.println("*****VIP MEMBER*****");
            user = vipCustomers.get(email);
            return true;
        }
        return false;
    }
    
}
