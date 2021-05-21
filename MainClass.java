
import java.io.IOException;
import services.PersonalFinance;

public class MainClass {
    
    public static void main(String[] args) {

        try {
            ConsoleUI myUI = new ConsoleUI(new PersonalFinance());
            myUI.mainMenu();
        }
        catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
