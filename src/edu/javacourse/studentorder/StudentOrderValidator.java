package edu.javacourse.studentorder;

import edu.javacourse.studentorder.domain.*;
import edu.javacourse.studentorder.mail.MailSender;
import edu.javacourse.studentorder.validator.ChildrenValidator;
import edu.javacourse.studentorder.validator.CityRegisterValidator;
import edu.javacourse.studentorder.validator.StudentValidator;
import edu.javacourse.studentorder.validator.WeddingValidator;

public class StudentOrderValidator {
    private CityRegisterValidator citiRegisterVal;
    private WeddingValidator weddingVal;
    private ChildrenValidator childrenVal;
    private StudentValidator studentVal;
    private MailSender mailSender;

    public StudentOrderValidator() {
        citiRegisterVal = new CityRegisterValidator();
        weddingVal = new WeddingValidator();
        childrenVal = new ChildrenValidator();
        studentVal = new StudentValidator();
        mailSender = new MailSender();
    }

    public static void main(String[] args) {
        StudentOrderValidator sov = new StudentOrderValidator();
        sov.checkAll();
    }

    public void checkAll() {
        StudentOrder [] soArray = readStudentOrders();

//        for(int i = 0; i < soArray.length; i++){
//            System.out.println();
//            checkOneOrder(soArray[i]);
//        }

            for (StudentOrder so : soArray){
                System.out.println();
                checkOneOrder(so);
            }
    }

    public void checkOneOrder(StudentOrder so){
        AnswerCitiRegister citiAnswer = checkCitiRegister(so);

        AnswerWedding wedAnswer = checkWedding(so);
        AnswerChildren childrenAnswer = checkChildren(so);
        AnswerStudent studAnswer = checkStudent(so);

        sendMail(so);
    }

    public StudentOrder[] readStudentOrders() {
        StudentOrder[] soArray = new StudentOrder[3];

        for (int i = 0; i < soArray.length; i++) {
            soArray[i] = SaveStudentOrder.buildStudentOrder(i);
        }

        StudentOrder so = new StudentOrder();

        return soArray;
    }

    public AnswerCitiRegister checkCitiRegister(StudentOrder so) {
        return citiRegisterVal.checkCitiRegister(so);
    }

    public AnswerWedding checkWedding(StudentOrder so) {
        return weddingVal.checkWedding(so);
    }

    public AnswerChildren checkChildren(StudentOrder so) {
        return childrenVal.checkChildren(so);
    }

    public AnswerStudent checkStudent(StudentOrder so) {
        return studentVal.checkStudent(so);
    }

    public void sendMail(StudentOrder so) {
        mailSender.sendMail(so);
    }
}
