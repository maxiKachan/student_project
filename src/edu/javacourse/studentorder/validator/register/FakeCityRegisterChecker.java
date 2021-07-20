package edu.javacourse.studentorder.validator.register;

import edu.javacourse.studentorder.domain.Adult;
import edu.javacourse.studentorder.domain.Child;
import edu.javacourse.studentorder.domain.register.CityRegisterResponse;
import edu.javacourse.studentorder.domain.Person;
import edu.javacourse.studentorder.exception.CityRegisterException;
import edu.javacourse.studentorder.exception.TransportException;

public class FakeCityRegisterChecker implements CityRegisterChecker {

    public static final String GOOD_1 = "1000";
    public static final String GOOD_2 = "2000";
    public static final String BAD_1 = "1001";
    public static final String BAD_2 = "2001";
    public static final String ERROR_1 = "1002";
    public static final String ERROR_2 = "2002";
    public static final String ERROR_T_1 = "1003";
    public static final String ERROR_T_2 = "2003";

    public CityRegisterResponse checkPerson(Person person) throws CityRegisterException, TransportException {
        CityRegisterResponse res = new CityRegisterResponse();

        if (person instanceof Adult){
            Adult a = (Adult) person;
            String ps = a.getPassportSerial();
            if (ps.equals(GOOD_1) || ps.equals(GOOD_2)){
                res.setExisting(true);
                res.setTemporal(false);
            }
            if (ps.equals(BAD_1) || ps.equals(BAD_2)){
                res.setExisting(false);
            }
            if (ps.equals(ERROR_1) || ps.equals(ERROR_2)){
                CityRegisterException exception = new CityRegisterException("1", "Fake ERROR" + ps);
                throw exception;
            }
            if (ps.equals(ERROR_T_1) || ps.equals(ERROR_T_2)){
                TransportException exception = new TransportException("Transport ERROR" + ps);
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
