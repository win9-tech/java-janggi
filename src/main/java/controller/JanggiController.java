package controller;

import domain.*;
import domain.piece.Piece;
import view.InputView;
import view.OutputView;

import java.util.List;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Board board = initBoard();
        playGame(board);
    }

    private void playGame(Board board) {
        Turn turn = new Turn(Side.CHO);
        boolean isRunning = true;
        while (isRunning) {
            outputView.printCurrentTurn(turn);
            TurnAction action = readTurnAction();
            isRunning = executeAction(turn, board, action);
        }
    }

    private boolean executeAction(Turn turn, Board board, TurnAction action) {
        if (action == TurnAction.PASS) {
            turn.next();
            outputView.printBoardStatus(board.getBoard());
            return true;
        }
        return move(board, turn);
    }

    private boolean move(Board board, Turn turn) {
        try {
            Position sourcePosition = readSourcePosition();
            List<Position> availableTargets = board.findPath(sourcePosition, turn);
            outputView.printAvailablePath(availableTargets, board.getBoard());
            Position targetPosition = readTargetPosition();
            Piece captured = board.movePiece(sourcePosition, targetPosition, availableTargets);
            outputView.printBoardStatus(board.getBoard());
            if (captured.isKing()) {
                outputView.printWinner(turn.current());
                return false;
            }
            turn.next();
            return true;
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return true;
        }
    }

    private TurnAction readTurnAction() {
        while (true) {
            try {
                return inputView.readTurnAction();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Position readTargetPosition() {
        while (true) {
            try {
                int x = inputView.readTargetXPosition();
                int y = inputView.readTargetYPosition();
                return Position.of(x, y);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Position readSourcePosition() {
        while (true) {
            try {
                int x = inputView.readSourceXPosition();
                int y = inputView.readSourceYPosition();
                return Position.of(x, y);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Board initBoard() {
        Formation choFormation = readChoFormation();
        Formation hanFormation = readHanFormation();
        Board board = new Board(choFormation, hanFormation);
        outputView.printBoardStatus(board.getBoard());
        return board;
    }

    private Formation readChoFormation() {
        while (true) {
            try {
                return inputView.readChoFormation();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Formation readHanFormation() {
        while (true) {
            try {
                return inputView.readHanFormation();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
