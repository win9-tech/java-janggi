import controller.JanggiController;
import repository.MongoDBRepository;
import view.ConsoleView;
import view.InputView;
import view.OutputView;

public class Main {
    public static void main(String[] args) {
        JanggiController janggiController = new JanggiController(new ConsoleView(new InputView(), new OutputView()), new MongoDBRepository());
        janggiController.run();
    }
}
