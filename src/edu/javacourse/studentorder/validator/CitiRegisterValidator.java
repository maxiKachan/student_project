package edu.javacourse.studentorder.validator;

import edu.javacourse.studentorder.domain.AnswerCitiRegister;
import edu.javacourse.studentorder.domain.StudentOrder;

public class CitiRegisterValidator {

    public String hostName;
    String login;
    String password;

    public AnswerCitiRegister checkCitiRegister(StudentOrder so){
        System.out.println("Citi register is running: " + hostName + ", " + login + ", " + password);
        AnswerCitiRegister ans = new AnswerCitiRegister();
        ans.success = false;

        return ans;
    }
}
