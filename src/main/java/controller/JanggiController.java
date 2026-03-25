package controller;

import domain.Formation;
import view.InputView;

public class JanggiController {

    private final InputView inputView;

    public JanggiController(InputView inputView) {
        this.inputView = inputView;
    }

    public void run() {
        Formation Choformation = readChoFormation();
        Formation Hanformation = readHanFormation();
    }

    private Formation readChoFormation() {
        while(true) {
            try {
                String choFormation = inputView.readChoFormation();
                return Formation.from(choFormation);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Formation readHanFormation() {
        while(true) {
            try {
                String hanFormation = inputView.readHanFormation();
                return Formation.from(hanFormation);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
