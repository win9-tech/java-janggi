import domain.*;
import domain.piece.Piece;
import repository.GameRepository;
import repository.GameStatus;
import view.ConsoleView;

import java.util.List;
import java.util.Map;

import static constant.ErrorMessage.GAME_NOT_FOUND;

public class JanggiRunner {

    private final ConsoleView consoleView;
    private final GameRepository gameRepository;

    public JanggiRunner(ConsoleView consoleView, GameRepository gameRepository) {
        this.consoleView = consoleView;
        this.gameRepository = gameRepository;
    }

    public void run() {
        Game game = loadGame();
        playGame(game);
    }

    private Game loadGame() {
        while (true) {
            try {
                return selectGame();
            } catch (IllegalArgumentException e) {
                consoleView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Game selectGame() {
        int option = readOption();
        if (option == 1) {
            return createNewGame();
        }
        return loadExistingGame();
    }

    private Game createNewGame() {
        Game game = new Game(gameRepository.getNextId(), new Turn(Side.CHO), readChoFormation(), readHanFormation());
        consoleView.printBoardStatus(game.getId(), game.getBoard(), game.calculateScore());
        return game;
    }

    private Game loadExistingGame() {
        GameStatus gameStatus = gameRepository.findBoard(consoleView.readGameId())
                .orElseThrow(() -> new IllegalArgumentException(GAME_NOT_FOUND));
        Game game = new Game(gameStatus.getGameId(), gameStatus.getTurn(), gameStatus.getBoard());
        consoleView.printBoardStatus(game.getId(), game.getBoard(), game.calculateScore());
        return game;
    }

    private int readOption() {
        while (true) {
            try {
                return consoleView.readOption();
            } catch (IllegalArgumentException e) {
                consoleView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void playGame(Game game) {
        boolean isRunning = true;
        gameRepository.saveBoard(game.getId(), game.getTurn(), game.getBoard());
        while (isRunning) {
            consoleView.printCurrentTurn(game.getTurn());
            TurnAction action = readTurnAction();
            isRunning = executeAction(game.getTurn(), game, action);
        }
    }

    private boolean executeAction(Turn turn, Game game, TurnAction action) {
        if (action == TurnAction.PASS) {
            turn.next();
            consoleView.printBoardStatus(game.getId(), game.getBoard(), game.calculateScore());
            return true;
        }
        if (action == TurnAction.JUDGE) {
            consoleView.printResultByScore(game.calculateScore());
            gameRepository.deleteBoard(game.getId());
            return false;
        }
        return move(game, turn);
    }

    private boolean move(Game game, Turn turn) {
        try {
            Position sourcePosition = readSourcePosition();
            List<Position> availableTargets = findAvailableTarget(game, turn, sourcePosition);
            Position targetPosition = readTargetPosition();
            Piece captured = moveToTarget(game, sourcePosition, targetPosition, availableTargets);
            return handleMoveResult(game, turn, captured);
        } catch (IllegalArgumentException e) {
            consoleView.printErrorMessage(e.getMessage());
            return true;
        }
    }

    private boolean handleMoveResult(Game game, Turn turn, Piece captured) {
        if (captured.isKing()) {
            consoleView.printWinner(turn.current());
            gameRepository.deleteBoard(game.getId());
            return false;
        }
        afterMove(game.getId(), game.getTurn(), game.getBoard());
        return true;
    }

    private List<Position> findAvailableTarget(Game game, Turn turn, Position sourcePosition) {
        List<Position> availableTargets = game.findPath(sourcePosition, turn);
        consoleView.printAvailablePath(game.getId(), availableTargets, game.getBoard(), game.calculateScore());
        return availableTargets;
    }

    private Piece moveToTarget(Game game, Position sourcePosition, Position targetPosition, List<Position> availableTargets) {
        Piece captured = game.movePiece(sourcePosition, targetPosition, availableTargets);
        consoleView.printBoardStatus(game.getId(), game.getBoard(), game.calculateScore());
        return captured;
    }

    private void afterMove(Long id, Turn turn, Map<Position, Piece> board) {
        turn.next();
        gameRepository.saveBoard(id, turn, board);
    }

    private Position readSourcePosition() {
        while (true) {
            try {
                return consoleView.readSourcePosition();
            } catch (IllegalArgumentException e) {
                consoleView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Position readTargetPosition() {
        while (true) {
            try {
                return consoleView.readTargetPosition();
            } catch (IllegalArgumentException e) {
                consoleView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Formation readChoFormation() {
        while (true) {
            try {
                return consoleView.readChoFormation();
            } catch (IllegalArgumentException e) {
                consoleView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Formation readHanFormation() {
        while (true) {
            try {
                return consoleView.readHanFormation();
            } catch (IllegalArgumentException e) {
                consoleView.printErrorMessage(e.getMessage());
            }
        }
    }

    private TurnAction readTurnAction() {
        while (true) {
            try {
                return consoleView.readTurnAction();
            } catch (IllegalArgumentException e) {
                consoleView.printErrorMessage(e.getMessage());
            }
        }
    }
}
