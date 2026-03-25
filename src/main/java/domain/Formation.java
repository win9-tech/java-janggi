package domain;

public enum Formation {

    상마마상("1"),
    마상상마("2"),
    상마상마("3"),
    마상마상("4");

    private final String option;

    Formation(String option) {
        this.option = option;
    }

    public static Formation from(String input) {
        for(Formation formation : Formation.values()) {
            if(formation.option.equals(input)) {
                return formation;
            }
        }
        throw new IllegalArgumentException("[ERROR] 번호는 1~4 사이의 숫자여야 합니다.");
    }

    public String toDisplayString() {
        return option + ". " + name();
    }
}
