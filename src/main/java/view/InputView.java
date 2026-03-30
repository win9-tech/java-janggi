package view;

import domain.Formation;
import domain.TurnAction;

import java.util.Scanner;

public class InputView {

    private static final String READ_FORMATION_MESSAGE = "[%s] 포진을 선택해주세요.";
    private static final String INVALID_FORMATION_RANGE = "번호는 1~4 사이의 숫자여야 합니다.";
    private static final String INVALID_TURN_ACTION_RANGE = "1 또는 2를 입력해주세요.";

    private final Scanner scanner = new Scanner(System.in);

    public Formation readChoFormation() {
        System.out.printf(READ_FORMATION_MESSAGE, "초");
        System.out.println();
        return parseFormation(readFormationMenu());
    }

    public Formation readHanFormation() {
        System.out.printf(READ_FORMATION_MESSAGE, "한");
        System.out.println();
        return parseFormation(readFormationMenu());
    }

    public TurnAction readTurnAction() {
        System.out.println("""
                1. 기물 이동
                2. 한수쉼
                """);
        String input = scanner.nextLine();
        return parseTurnAction(input);
    }

    private Formation parseFormation(String input) {
        int index = parseFormationIndex(input);
        return Formation.values()[index - 1];
    }

    private TurnAction parseTurnAction(String input) {
        int index = parseTurnActionIndex(input);
        return TurnAction.values()[index - 1];
    }

    private int parseFormationIndex(String input) {
        try {
            int index = Integer.parseInt(input);
            if (index < 1 || index > Formation.values().length) {
                throw new IllegalArgumentException(INVALID_FORMATION_RANGE);
            }
            return index;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_FORMATION_RANGE);
        }
    }

    private int parseTurnActionIndex(String input) {
        try {
            int index = Integer.parseInt(input);
            if (index < 1 || index > TurnAction.values().length) {
                throw new IllegalArgumentException(INVALID_TURN_ACTION_RANGE);
            }
            return index;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_TURN_ACTION_RANGE);
        }
    }

    public int readSourceXPosition() {
        System.out.println();
        System.out.println("움직일 기물의 x 좌표를 입력해주세요. (x 범위 1 ~ 9)");
        return scanner.nextInt();
    }

    public int readSourceYPosition() {
        System.out.println("움직일 기물의 y 좌표를 입력해주세요. (y 범위 1 ~ 10)");
        return scanner.nextInt();
    }

    public int readTargetXPosition() {
        System.out.println();
        System.out.println("목적지 x 좌표를 입력해주세요. (x 범위 1 ~ 9)");
        return scanner.nextInt();
    }

    public int readTargetYPosition() {
        System.out.println("목적지 y 좌표를 입력해주세요. (y 범위 1 ~ 10)");
        return scanner.nextInt();
    }

    private String readFormationMenu() {
        System.out.println("""
                1. 상마마상
                2. 마상상마
                3. 상마상마
                4. 마상마상
                """);
        return scanner.nextLine();
    }
}
