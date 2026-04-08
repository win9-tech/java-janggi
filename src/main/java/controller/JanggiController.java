package controller;

import domain.*;
import domain.piece.Piece;
import repository.JdbcRepository;
import view.ConsoleView;

import java.util.List;
import java.util.Map;

public class JanggiController {

    private final ConsoleView consoleView;
    private final JdbcRepository jdbcRepository;

    public JanggiController(ConsoleView consoleView, JdbcRepository jdbcRepository) {
        this.consoleView = consoleView;
        this.jdbcRepository = jdbcRepository;
    }

    public void run() {
        Game game = loadGame();
        playGame(game);
    }

    private void playGame(Game game) {
        boolean isRunning = true;
        jdbcRepository.saveBoard(game.getId(), game.getTurn(), game.getBoard());
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

            if (validateGameFinished(turn, captured)) {
                return false;
            }
            afterMove(game.getId(), game.getTurn(), game.getBoard());
            return true;
        } catch (IllegalArgumentException e) {
            consoleView.printErrorMessage(e.getMessage());
            return true;
        }
    }

    private void afterMove(Long id, Turn turn, Map<Position, Piece> board) {
        turn.next();
        jdbcRepository.saveBoard(id, turn, board);
    }

    private boolean validateGameFinished(Turn turn, Piece captured) {
        if(captured.isKing()) {
            consoleView.printWinner(turn.current());
            return true;
        }
        return false;
    }

    private Piece moveToTarget(Game game, Position sourcePosition, Position targetPosition, List<Position> availableTargets) {
        Piece captured = game.movePiece(sourcePosition, targetPosition, availableTargets);
        consoleView.printBoardStatus(game.getId(), game.getBoard(), game.calculateScore());
        return captured;
    }

    private List<Position> findAvailableTarget(Game game, Turn turn, Position sourcePosition) {
        List<Position> availableTargets = game.findPath(sourcePosition, turn);
        consoleView.printAvailablePath(game.getId(), availableTargets, game.getBoard(), game.calculateScore());
        return availableTargets;
    }

    private Game loadGame() {
        String input = consoleView.readOption();
        if(input.equals("1")) {
            Game game = new Game(jdbcRepository.getNextId(), new Turn(Side.CHO), readChoFormation(), readHanFormation());
            consoleView.printBoardStatus(game.getId(), game.getBoard(), game.calculateScore());
            return game;
        }
        GameStatus gameStatus = jdbcRepository.findBoard(consoleView.readGameId());
        Game game = new Game(gameStatus.getGameId(), gameStatus.getTurn(), gameStatus.getBoard());
        consoleView.printBoardStatus(game.getId(), game.getBoard(), game.calculateScore());
        return game;
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
