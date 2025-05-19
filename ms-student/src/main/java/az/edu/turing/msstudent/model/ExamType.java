package az.edu.turing.msstudent.model;

public enum ExamType {
    QUIZ("Quiz"),
    MODULE("Module"),
    FINAL("FINAL"),
    MIDTERM("Midterm"),
    OTHER("Other");

    private String displayName;

    ExamType(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName(){return displayName;}
}
