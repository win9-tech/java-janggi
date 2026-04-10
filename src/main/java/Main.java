import repository.MongoDBRepository;
import view.ConsoleView;
import view.InputView;
import view.OutputView;

public class Main {
    public static void main(String[] args) {
        try (MongoDBRepository repository = new MongoDBRepository()) {
            JanggiRunner janggiRunner = new JanggiRunner(new ConsoleView(new InputView(), new OutputView()), repository);
            janggiRunner.run();
        }
    }
}
