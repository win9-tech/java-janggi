package view;

import domain.Formation;
import java.util.Scanner;

public class InputView {
    private final String READ_FORMATION_MESSAGE = "[%s] 포진을 선택해주세요.";

    private final Scanner scanner = new Scanner(System.in);

    public String readChoFormation() {
        System.out.printf(READ_FORMATION_MESSAGE, "초");
        System.out.println();
        return readFormation();
    }

    public String readHanFormation() {
        System.out.printf(READ_FORMATION_MESSAGE, "한");
        System.out.println();
        return readFormation();
    }

    private String readFormation() {
        for(Formation formation : Formation.values()) {
            System.out.println(formation.toDisplayString());
        }
        return scanner.nextLine();
    }
}
