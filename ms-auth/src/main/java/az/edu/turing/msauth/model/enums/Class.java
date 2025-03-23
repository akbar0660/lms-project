package az.edu.turing.msauth.model.enums;

public enum Class {
    BACKEND("BACKEND"),
    FRONTEND("FRONTEND"),
    UX_UI("UX/UI"),
    DATA_SCIENCE("DATA SCIENCE"),
    PROJECT_MANAGEMENT("PROJECT MANAGEMENT"),;

    private final String displayName;

    Class(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

