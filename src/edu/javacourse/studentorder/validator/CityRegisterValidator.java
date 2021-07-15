package edu.javacourse.studentorder.validator;

import edu.javacourse.studentorder.domain.AnswerCitiRegister;
import edu.javacourse.studentorder.domain.CityRegisterCheckerResponse;
import edu.javacourse.studentorder.domain.StudentOrder;
import edu.javacourse.studentorder.exception.CityRegisterException;
import edu.javacourse.studentorder.validator.register.CityRegisterChecker;
import edu.javacourse.studentorder.validator.register.FakeCityRegisterChecker;
import edu.javacourse.studentorder.validator.register.RealCityRegisterChecker;

public class CityRegisterValidator {

    private CityRegisterChecker personChecker;

    public CityRegisterValidator() {
        personChecker = new FakeCityRegisterChecker();
    }

    public AnswerCitiRegister checkCitiRegister(StudentOrder so){
        try{
            CityRegisterCheckerResponse hans = personChecker.checkPerson(so.getHusband());
            CityRegisterCheckerResponse wans = personChecker.checkPerson(so.getWife());
            CityRegisterCheckerResponse cans = personChecker.checkPerson(so.getChild());
        } catch (CityRegisterException e){
            e.printStackTrace(System.out);
        }

        AnswerCitiRegister ans = new AnswerCitiRegister();
        return ans;
    }
}
