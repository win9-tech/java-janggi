import controller.JanggiController;
import view.InputView;

public class Main {
    public static void main(String[] args) {
        JanggiController janggiController = new JanggiController(new InputView());
        janggiController.run();
    }
}
