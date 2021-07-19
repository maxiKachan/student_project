package edu.javacourse.studentorder.validator;

import edu.javacourse.studentorder.domain.register.AnswerCitiRegister;
import edu.javacourse.studentorder.domain.Child;
import edu.javacourse.studentorder.domain.register.CityRegisterCheckerResponse;
import edu.javacourse.studentorder.domain.StudentOrder;
import edu.javacourse.studentorder.exception.CityRegisterException;
import edu.javacourse.studentorder.validator.register.CityRegisterChecker;
import edu.javacourse.studentorder.validator.register.FakeCityRegisterChecker;

import java.util.Iterator;
import java.util.List;

public class CityRegisterValidator {

    private CityRegisterChecker personChecker;

    public CityRegisterValidator() {
        personChecker = new FakeCityRegisterChecker();
    }

    public AnswerCitiRegister checkCitiRegister(StudentOrder so) {
        try {
            CityRegisterCheckerResponse hans = personChecker.checkPerson(so.getHusband());
            CityRegisterCheckerResponse wans = personChecker.checkPerson(so.getWife());
            CityRegisterCheckerResponse cans;
            List<Child> children = so.getChildren();

            for (int i = 0; i < children.size(); i++) {
                cans = personChecker.checkPerson(children.get(i));
            }

            for (Iterator<Child> iterator = children.iterator(); iterator.hasNext();){
                Child child = iterator.next();
                cans = personChecker.checkPerson(child);
            }

            for (Child child : children){
                cans = personChecker.checkPerson(child);
            }
        } catch (CityRegisterException e) {
            e.printStackTrace(System.out);
        }

        AnswerCitiRegister ans = new AnswerCitiRegister();
        return ans;
    }
}
