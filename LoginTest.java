import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
    private Controller controller;
    
    @BeforeEach
    void setUp() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("usersTest.txt"))) {
            bw.write("VIP,vipUser,vipPass");
            bw.newLine();
            bw.write("REGULAR,regularUser,regularPass");
            bw.newLine();
        } catch (IOException e) {
            fail("Failed to set up test environment: " + e.getMessage());
        }
        
        controller = new Controller();
    }
    
    @Test
    void wrongPasswordTest() {
        ByteArrayInputStream in = new ByteArrayInputStream("1\nvipUser\nwrongpass".getBytes());
        System.setIn(in);
        
        Scanner testScanner = new Scanner(System.in);
        controller.login(true, testScanner);
        
        assertNull(controller.user, "Failed test\nwrong password");
    }
    
    @Test
    void wrongUsernameTest(){
        ByteArrayInputStream in = new ByteArrayInputStream("1\nunkownuser\nvipPass".getBytes());
        System.setIn(in);
        
        Scanner testScanner = new Scanner(System.in);
        controller.login(true, testScanner);
        
        assertNull(controller.user, "Failed test\nwrong username");
    }
    
    @Test
    void wrongBothTest(){
        ByteArrayInputStream in = new ByteArrayInputStream("1\nunkownuser\nwrongpass".getBytes());
        System.setIn(in);
        
        Scanner testScanner = new Scanner(System.in);
        controller.login(true, testScanner);
        
        assertNull(controller.user, "Failed test\nboth wrong");
    }
    
    

}
