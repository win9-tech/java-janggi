import repository.MongoDBRepository;
import view.ConsoleView;
import view.InputView;
import view.OutputView;

public class Main {
    public static void main(String[] args) {
        JanggiRunner janggiRunner = new JanggiRunner(new ConsoleView(new InputView(), new OutputView()), new MongoDBRepository());
        janggiRunner.run();
    }
}
