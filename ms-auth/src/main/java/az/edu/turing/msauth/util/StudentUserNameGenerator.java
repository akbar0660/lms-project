package az.edu.turing.msauth.util;

import az.edu.turing.msauth.model.enums.Class;
import az.edu.turing.msauth.model.request.StudentRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class StudentUserNameGenerator {

    private static int count = 1;

    public static String generateUserName(StudentRequest request) {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
        String date = LocalDate.now().format(formatter);
        String abvOfClass = "";
        if (request.getClassType() == Class.BACKEND) {
            abvOfClass = "JBE";
        } else if (request.getClassType() == Class.UX_UI) {
            abvOfClass = "UXUI";
        } else if (request.getClassType() == Class.DATA_SCIENCE) {
            abvOfClass = "DS";
        } else if (request.getClassType() == Class.FRONTEND) {
            abvOfClass = "JFE";
        } else {
            abvOfClass = "PM";
        }

        return sb.append(abvOfClass).append(date).append("#").append(count++).toString();
    }
}
