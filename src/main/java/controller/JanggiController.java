package controller;

import domain.*;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Board board = initBoard();
        processMove(board);
    }

    private void processMove(Board board) {
        Turn turn = new Turn(Side.CHO);
        while (true) {
            outputView.printCurrentTurn(turn);
            Position sourcePosition = readSourcePosition();
            Position targetPosition = readTargetPosition();
            try {
                board.movePiece(turn, sourcePosition, targetPosition);
                turn.next();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
            outputView.printBoardStatus(board.getBoard());
        }
    }

    private Position readTargetPosition() {
        while (true) {
            try {
                int x = inputView.readTargetXPosition();
                int y = inputView.readTargetYPosition();
                return Position.of(x,y);
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
                return Position.of(x,y);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Board initBoard() {
        Formation choformation = readChoFormation();
        Formation hanformation = readHanFormation();
        Board board = new Board(choformation, hanformation);
        outputView.printBoardStatus(board.getBoard());
        return board;
    }

    private Formation readChoFormation() {
        while(true) {
            try {
                String choFormation = inputView.readChoFormation();
                return Formation.from(choFormation);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Formation readHanFormation() {
        while(true) {
            try {
                String hanFormation = inputView.readHanFormation();
                return Formation.from(hanFormation);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
