import java.io.IOException;

public class App {
    public static void main(String[] args) {
        boolean validMode = false;
        while (!validMode) {
            System.out.print("Mode ? [c|d]: ");
            char mode;
            try {
                mode = (char) System.in.read();
            } catch (IOException e) {
                mode = '!';
            }

            switch (mode) {
                case 'c':
                    System.out.println("Encoding...");
                    validMode = true;
                    break;
                case 'd':
                    System.out.println("Decoding...");
                    validMode = true;
                    break;
                default:
                    System.out.println("Wrong Mode..");
                    break;
            }
        }
    }
}
