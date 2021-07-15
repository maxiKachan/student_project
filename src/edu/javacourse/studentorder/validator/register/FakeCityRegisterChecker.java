package edu.javacourse.studentorder.validator.register;

import edu.javacourse.studentorder.domain.Adult;
import edu.javacourse.studentorder.domain.Child;
import edu.javacourse.studentorder.domain.CityRegisterCheckerResponse;
import edu.javacourse.studentorder.domain.Person;
import edu.javacourse.studentorder.exception.CityRegisterException;
import edu.javacourse.studentorder.validator.register.CityRegisterChecker;

public class FakeCityRegisterChecker implements CityRegisterChecker {

    public static final String GOOD_1 = "1000";
    public static final String GOOD_2 = "2000";
    public static final String BAD_1 = "1001";
    public static final String BAD_2 = "2001";
    public static final String ERROR_1 = "1002";
    public static final String ERROR_2 = "2002";

    public CityRegisterCheckerResponse checkPerson(Person person) throws CityRegisterException {
        CityRegisterCheckerResponse res = new CityRegisterCheckerResponse();

        if (person instanceof Adult){
            Adult a = (Adult) person;
            if (a.getPassportSerial().equals(GOOD_1) || a.getPassportSerial().equals(GOOD_2)){
                res.setExisting(true);
                res.setTemporal(false);
            }
            if (a.getPassportSerial().equals(BAD_1) || a.getPassportSerial().equals(BAD_2)){
                res.setExisting(false);
            }
            if (a.getPassportSerial().equals(ERROR_1) || a.getPassportSerial().equals(ERROR_2)){
                CityRegisterException exception = new CityRegisterException("Fake ERROR");
                throw exception;
            }
        }

        if (person instanceof Child){
            res.setExisting(true);
            res.setTemporal(true);
        }

        System.out.println(res);

        return res;
    }
}
