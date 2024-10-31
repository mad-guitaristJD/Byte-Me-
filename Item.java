import java.util.ArrayList;

public class Item {
    public static ArrayList<Item> items = new ArrayList<>();
    private String name;
    private int price;
    private boolean availability;
    private String category;
    
    
    
    public Item(String category, String name, int price){
        this.category = category;
        this.name = name;
        this.price = price;
        this.availability = true; //considering whenever new item is created it's available
    }
    
    
    
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setPrice(int price) {
        this.price = price;
    }
    
    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public int getPrice() {
        return price;
    }
    
    public boolean isAvailable() {
        return availability;
    }
    
    public String getCategory() {
        return category;
    }
    
    public String getName() {
        return name;
    }
    
    public void printCNPA(){
        System.out.print("CATEGORY: " + category + " || NAME: " + name + " || PRICE: " + price + " || AVAILABLE: ");
        if(availability) System.out.print("YES :)");
        else System.out.print("NO :(");
    }
    
    public void printPCNA(){
        System.out.print("PRICE: " + price + " || CATEGORY: " + category + " || NAME: " + name + " || AVAILABLE: ");
        if(availability) System.out.print("YES :)");
        else System.out.print("NO :(");
    }
    
    
}
