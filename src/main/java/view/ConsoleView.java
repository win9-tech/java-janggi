package view;

import domain.*;
import domain.piece.Piece;

import java.util.List;
import java.util.Map;

public class ConsoleView {

    private final InputView inputView;
    private final OutputView outputView;

    public ConsoleView(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public String readOption() {
        return inputView.readOption();
    }

    public void printBoardStatus(Long id, Map<Position, Piece> board, ScoreStatus scoreStatus) {
        outputView.printBoardStatus(id, board, scoreStatus);
    }

    public String readGameId() {
        return inputView.readGameId();
    }

    public Formation readChoFormation() {
        return inputView.readChoFormation();
    }

    public Formation readHanFormation() {
        return inputView.readHanFormation();
    }

    public void printErrorMessage(String message) {
        outputView.printErrorMessage(message);
    }

    public int readSourceXPosition() {
        return inputView.readTargetXPosition();
    }

    public int readSourceYPosition() {
        return inputView.readTargetXPosition();
    }

    public int readTargetXPosition() {
        return inputView.readTargetXPosition();
    }

    public int readTargetYPosition() {
        return inputView.readTargetYPosition();
    }

    public TurnAction readTurnAction() {
        return inputView.readTurnAction();
    }

    public void printAvailablePath(Long id, List<Position> availableTargets, Map<Position, Piece> board, ScoreStatus scoreStatus) {
        outputView.printAvailablePath(id, availableTargets, board, scoreStatus);
    }

    public void printWinner(Side current) {
        outputView.printWinner(current);
    }

    public void printCurrentTurn(Turn turn) {
        outputView.printCurrentTurn(turn);
    }

    public void printResultByScore(ScoreStatus scoreStatus) {
        outputView.printResultByScore(scoreStatus);
    }
}
