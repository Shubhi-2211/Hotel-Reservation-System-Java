public class Applicationstarter {

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            try {
                MainMenu.start();
                running = false;
            } catch (Exception e) {
                System.out.println("Something went wrong. Returning to menu...");
            }
        }
        System.out.println("Application closed. Goodbye!");
    }
}
