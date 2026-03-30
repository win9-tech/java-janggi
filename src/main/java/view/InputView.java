package view;

import domain.Formation;

import java.util.Scanner;

public class InputView {

    private static final String READ_FORMATION_MESSAGE = "[%s] 포진을 선택해주세요.";
    private static final String INVALID_OPTION_RANGE = "번호는 1~4 사이의 숫자여야 합니다.";

    private final Scanner scanner = new Scanner(System.in);

    public Formation readChoFormation() {
        System.out.printf(READ_FORMATION_MESSAGE, "초");
        System.out.println();
        return parseFormation(readFormation());
    }

    public Formation readHanFormation() {
        System.out.printf(READ_FORMATION_MESSAGE, "한");
        System.out.println();
        return parseFormation(readFormation());
    }

    private Formation parseFormation(String input) {
        int index = parseIndex(input);
        if (index < 1 || index > Formation.values().length) {
            throw new IllegalArgumentException(INVALID_OPTION_RANGE);
        }
        return Formation.values()[index - 1];
    }

    private int parseIndex(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_OPTION_RANGE);
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

    private String readFormation() {
        System.out.println("""
                1. 상마마상
                2. 마상상마
                3. 상마상마
                4. 마상마상
                """);
        return scanner.nextLine();
    }
}
