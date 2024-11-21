import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class NotAvailableTest {
    private Controller controller;
    @BeforeEach
    void setup(){
        controller = new Controller();
    }
    
    @Test
    void notAvailable(){
        Vip vip = controller.getVipCustomers().get("vipUser");
        String simulatedInput = "2\n1\n-1\n";
        ByteArrayInputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);
        Scanner testScanner = new Scanner(System.in);
        vip.addOrder(testScanner);
        assert(vip.cart.isEmpty());
    }
}
