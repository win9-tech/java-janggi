package controller;

import domain.*;
import domain.piece.Piece;
import repository.JdbcRepository;
import view.InputView;
import view.OutputView;

import java.util.List;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;
    private final JdbcRepository jdbcRepository;

    public JanggiController(InputView inputView, OutputView outputView, JdbcRepository jdbcRepository) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.jdbcRepository = jdbcRepository;
    }

    public void run() {
        BoardStatus boardStatus = loadBoard();
        playGame(boardStatus);
    }

    private void playGame(BoardStatus boardStatus) {
        Board board = new Board(boardStatus.getBoard());
        board.assignId(boardStatus.getGameId());
        Turn turn = boardStatus.getTurn();
        boolean isRunning = true;
        jdbcRepository.saveBoard(board.getId(), turn, board.getBoard());
        while (isRunning) {
            outputView.printCurrentTurn(turn);
            TurnAction action = readTurnAction();
            isRunning = executeAction(turn, board, action);
        }
    }

    private boolean executeAction(Turn turn, Board board, TurnAction action) {
        if (action == TurnAction.PASS) {
            turn.next();
            outputView.printBoardStatus(board.getId(), board.getBoard(), board.calculateScore());
            return true;
        }
        if (action == TurnAction.JUDGE) {
            outputView.printResultByScore(board.calculateScore());
            return false;
        }
        return move(board, turn);
    }

    private boolean move(Board board, Turn turn) {
        try {
            Position sourcePosition = readSourcePosition();
            List<Position> availableTargets = board.findPath(sourcePosition, turn);
            outputView.printAvailablePath(board.getId(), availableTargets, board.getBoard(), board.calculateScore());
            Position targetPosition = readTargetPosition();
            Piece captured = board.movePiece(sourcePosition, targetPosition, availableTargets);
            outputView.printBoardStatus(board.getId(), board.getBoard(), board.calculateScore());

            if (captured.isKing()) {
                outputView.printWinner(turn.current());
                return false;
            }
            jdbcRepository.saveBoard(board.getId(), turn, board.getBoard());
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

    private BoardStatus loadBoard() {
        String input = inputView.readOption();
        if(input.equals("1")) {
            Formation choFormation = readChoFormation();
            Formation hanFormation = readHanFormation();
            Board board = new Board(choFormation, hanFormation);
            Long gameId = jdbcRepository.getNextId();
            board.assignId(gameId);
            outputView.printBoardStatus(board.getId(), board.getBoard(), board.calculateScore());
            return BoardStatus.of(gameId, board.getBoard(), new Turn(Side.CHO));
        }
        String id = inputView.readGameId();
        BoardStatus boardStatus = jdbcRepository.findBoard(id);
        Board board = new Board(boardStatus.getBoard());
        board.assignId(boardStatus.getGameId());
        outputView.printBoardStatus(board.getId(), board.getBoard(), board.calculateScore());
        return boardStatus;
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
