package edu.javacourse.studentorder.validator;

import edu.javacourse.studentorder.domain.AnswerCitiRegister;
import edu.javacourse.studentorder.domain.StudentOrder;

public class CityRegisterValidator {

    public String hostName;
    protected int port;
    String login;
    String password;

    private CityRegisterChecker personChecker;

    public CityRegisterValidator() {
        personChecker = new RealCityRegisterChecker();
    }

    public AnswerCitiRegister checkCitiRegister(StudentOrder so){
        personChecker.checkPerson(so.getHusband());
        personChecker.checkPerson(so.getWife());
        personChecker.checkPerson(so.getChild());


        AnswerCitiRegister ans = new AnswerCitiRegister();

        return ans;
    }
}
