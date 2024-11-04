import java.util.*;

public abstract class Customer extends User{
    protected Scanner scanner = new Scanner(System.in);
    protected ArrayList<Order> cart = new ArrayList<>();
    protected ArrayList<Order> placedOrders = new ArrayList<>();
    protected ArrayList<Order> oldOrders = new ArrayList<>();
    
    public Customer(String email, String password){
        super(email, password);
    }
    
    
    public abstract int getPriority();
    public abstract void setPriority(int priority);
    
    public void viewCart(){
        int i=1;
        for(Order order : cart){
            System.out.println(i + ".ITEM: " + order.getItem().getName() + " || Quantity: " + order.getQuantity());
            i++;
        }
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
    
    private Item checkWord(String keyword, Item item){
        //check keyword for name
        if(item.getName().equals(keyword)){
            return item;
        }
        
        //check keyword for categories
        if(item.getCategory().equals(keyword)){
            return item;
        }
        return null;
    } //Only checking whole word
    
    public void searchItems(){
        ArrayList<Item> items = Item.items;
        Set<Item> itemSet = new HashSet<>();
        String keyword;
        while(true){
            itemSet.clear();
            System.out.println("ENTER THE KEYWORD:");
            keyword = scanner.nextLine();
            if(keyword.equals("-1")) return;
            for(Item item : items){
                if(checkWord(keyword, item)!=null){
                    itemSet.add(item);
                }
            }
            if(itemSet.isEmpty()){
                System.out.println();
                System.out.println("-----NO MATCHES FOUND-----");
                System.out.println();
            }
            else {
                for (Item item : itemSet) {
                    item.printCNPA();
                    System.out.println();
                }
            }
        }
    }
    
    private Item checkCategory(String keyword, Item item){
        //check keyword for categories
        if(item.getCategory().equals(keyword)){
            return item;
        }
        return null;
    }
    
    public void filterCategory(){
        ArrayList<Item> items = Item.items;
        Set<Item> itemSet = new HashSet<>();
        String keyword;
        while(true){
            itemSet.clear();
            System.out.println("ENTER THE CATEGORY:");
            keyword = scanner.nextLine();
            if(keyword.equals("-1")) return;
            for(Item item : items){
                if(checkCategory(keyword, item)!=null){
                    itemSet.add(item);
                }
            }
            if(itemSet.isEmpty()){
                System.out.println();
                System.out.println("-----NO MATCHES FOUND-----");
                System.out.println();
            }
            else {
                for (Item item : itemSet) {
                    item.printCNPA();
                    System.out.println();
                }
            }
        }
    }
    
    public void sortPrice(){
        ArrayList<Item> items = new ArrayList<>(Item.items);
        String choice;
        while(true){
            System.out.println("WRITE 'A' FOR ASCENDING OR 'D' FOR DESCENDING");
            choice = scanner.nextLine();
            if(choice.equals("-1")) break;
            if(choice.equals("A")){ //for ascending order
                items.sort(Comparator.comparingInt(Item::getPrice));
                for(Item item : items){
                    item.printPCNA();
                    System.out.println();
                }
            }
            if(choice.equals("D")){ //for descending order
                items.sort(Comparator.comparingInt(Item::getPrice).reversed());
                for(Item item : items){
                    item.printPCNA();
                    System.out.println();
                }
            }
            
            
        }
    }
    
    
    
    public void addOrder(){
        viewItems();
        ArrayList<Item> items = Item.items;
        int choice=0, quantity=1;
        while(true){
            System.out.println("ENTER THE ITEM NUMBER");
            choice = scanner.nextInt();
            scanner.nextLine();
            if(choice==-1) break;
            choice--;
            System.out.println("ENTER QUANTITY");
            quantity = scanner.nextInt();
            scanner.nextLine();
            String specialRequest="";
            System.out.println("TYPE SPECIAL REQUEST\n-1 IF NONE");
            specialRequest = scanner.nextLine();
            if(specialRequest.equals("-1")) cart.add(new Order(this, items.get(choice),  quantity));
            else cart.add(new Order(this, items.get(choice),  quantity, specialRequest));
        }
        System.out.println("ADDED ITEMS");
        for(Order order : cart){
            System.out.println("ITEM: " + order.getItem().getName() + " || Quantity: " + order.getQuantity());
        }
    }
    
    public void modifyQuantity(){
        int choice, i=1, quantity;
        System.out.println("-----CURRENT CART ITEMS-----");
        for(Order order : cart){
            System.out.println(i + ".ITEM: " + order.getItem().getName() + " || Quantity: " + order.getQuantity());
            i++;
        }
        while(true){
            System.out.println("ENTER THE ITEM NUMBER TO MODIFY");
            choice = scanner.nextInt();
            scanner.nextLine();
            if(choice==-1) break;
            choice--;
            System.out.println("ENTER THE QUANTITY");
            quantity = scanner.nextInt();
            scanner.nextLine();
            cart.get(choice).setQuantity(quantity);
            System.out.println("MODIFIED");
        }
        System.out.println("-----FINAL CART ITEMS-----");
        viewCart();
    }
    
    public void removeOrder(){
        System.out.println("-----CURRENT ITEMS IN CART-----");
        viewCart();
        int choice;
        while(true){
            System.out.println("ENTER ELEMENT NUMBER TO REMOVE");
            choice = scanner.nextInt();
            scanner.nextLine();
            if(choice==-1) break;
            choice--;
            cart.remove(choice);
            System.out.println("REMOVED");
        }
        System.out.println("FINAL CART ITEMS");
        viewCart();
    }
    
    public void viewTotal(){
        System.out.println("-----FINAL BILL-----");
        int totalAmount=0;
        for(Order order : cart){
            System.out.println("NAME: " + order.getItem().getName() + " || QUANTITY: " + order.getQuantity() +
                    " || PRICE: " + order.getItem().getPrice() + " || TOTAL: " + order.getItem().getPrice()*order.getQuantity());
            totalAmount+=order.getItem().getPrice()*order.getQuantity();
        }
        System.out.println("---------------------------");
        System.out.println("FINAL AMOUNT : " + totalAmount);
        System.out.println("---------------------------");
    }
    
    public abstract void checkout();
    
    
    
    public void viewOrderStatus(){
        if(placedOrders.isEmpty()){
            System.out.println("NO CURRENTLY PLACED ORDER");
            return;
        }
        int i=1;
        System.out.println("-----PLACED ORDERS-----");
        for(Order order : placedOrders){
            System.out.println(i + ".ITEM: " + order.getItem().getName() + " || QUANTITY: " + order.getQuantity() + " || STATUS: " + order.getOrderStatus());
            i++;
        }
    }
    
    public void cancelOrder(){
        System.out.println("ORDERS THAT ARE NOW BEING PROCESSED CAN NOT BE CANCELLED");
        viewOrderStatus();
        int choice;
        while(true){
            System.out.println("ENTER THE ITEM NUMBER TO CANCEL");
            choice = scanner.nextInt();
            scanner.nextLine();
            if(choice==-1) return;
            choice--;
            if(!placedOrders.get(choice).getOrderStatus().equals("BOOKED")){
                System.out.println("CAN NOT BE CANCELLED NOW SORRY");
                continue;
            }
            placedOrders.get(choice).setOrderStatus("CANCELLED");
            oldOrders.add(placedOrders.get(choice));
            System.out.println("CANCELLED");
        }
        
    }
    
    public void oldOrderAgain(Item item){
        int quantity;
        System.out.println("ENTER QUANTITY");
        quantity = scanner.nextInt();
        scanner.nextLine();
        cart.add(new Order(this, item, quantity));
        System.out.println("ADDED TO CART");
    }
    
    public void viewOldOrders(){
        if(oldOrders.isEmpty()){
            System.out.println("NO HISTORY");
            return;
        }
        int i=1, choice;
        for(Order order : oldOrders){
            System.out.println(i + ". ITEM: " + order.getItem().getName() + " || STATUS: " + order.getOrderStatus());
            System.out.println("ORDERED TIME: " + order.getTimestamp());
            i++;
        }
        System.out.println("TO RE-ORDER ENTER THE CHOICE");
        choice = scanner.nextInt();
        scanner.nextLine();
        if(choice==-1) return;
        choice--;
        oldOrderAgain(oldOrders.get(choice).getItem());
    }
    
    
    public void addReview(){
        String item = "";
        System.out.println("ENTER ITEM NAME");
        item = scanner.nextLine();
        item = item.toLowerCase();
        System.out.println("ENTER YOUR REVIEW");
        String review = "";
        review = scanner.nextLine();
        if(Reviews.reviewMap.containsKey(item)){
            Reviews.reviewMap.get(item).add(review);
        }
        else{
            ArrayList<String> reviews = new ArrayList<>();
            reviews.add(review);
            Reviews.reviewMap.put(item, reviews);
        }
    }
    
    public void viewReviews(){
        String item = "";
        System.out.println("ENTER ITEM NAME TO SEE REVIEWS FOR");
        item = scanner.nextLine();
        item = item.toLowerCase();
        if(!Reviews.reviewMap.containsKey(item)){
            System.out.println("NO REVIEW");
            return;
        }
        for(String s : Reviews.reviewMap.get(item)){
            System.out.println(s);
        }
        System.out.println();
    }
    
    
    
    public abstract void showType();
    
}
