import java.util.ArrayList;
import java.util.HashMap;

public class Reviews<T>{
    private T feedback;
    public static HashMap<String, ArrayList<String>> reviewMap = new HashMap<>();
    
    public Reviews(T feedback) {
        this.feedback = feedback;
    }
    
}