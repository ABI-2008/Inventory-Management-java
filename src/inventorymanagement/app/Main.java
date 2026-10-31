package inventorymanagement.app;

import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && "--console".equalsIgnoreCase(args[0])) {
            InventoryApp.main(args);
            return;
        }

        Application.launch(JavaFXInventoryApp.class, args);
    }
}
