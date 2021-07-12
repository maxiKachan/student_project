package edu.javacourse.studentorder;

import edu.javacourse.studentorder.domain.*;
import edu.javacourse.studentorder.mail.MailSender;
import edu.javacourse.studentorder.validator.ChildrenValidator;
import edu.javacourse.studentorder.validator.CitiRegisterValidator;
import edu.javacourse.studentorder.validator.StudentValidator;
import edu.javacourse.studentorder.validator.WeddingValidator;

public class StudentOrderValidator {
    private CitiRegisterValidator citiRegisterVal;
    private WeddingValidator weddingVal;
    private ChildrenValidator childrenVal;
    private StudentValidator studentVal;
    private MailSender mailSender;

    public StudentOrderValidator(){
        citiRegisterVal = new CitiRegisterValidator();
        weddingVal = new WeddingValidator();
        childrenVal = new ChildrenValidator();
        studentVal = new StudentValidator();
        mailSender = new MailSender();
    }

    public static void main(String[] args) {
        StudentOrderValidator sov = new StudentOrderValidator();
        sov.checkAll();
    }

    public void checkAll(){
        StudentOrder so = readStudentOrder();
        while (true) {
            if (so == null) {
                break;
            } else {

                AnswerCitiRegister citiAnswer = checkCitiRegister(so);
                if (!citiAnswer.success){
                    break;
                }
                AnswerWedding wedAnswer = checkWedding(so);
                AnswerChildren childrenAnswer = checkChildren(so);
                AnswerStudent studAnswer = checkStudent(so);

                sendMail(so);
            }
        }
    }

    public StudentOrder readStudentOrder(){
        StudentOrder so = new StudentOrder();

        return so;
    }

    public AnswerCitiRegister checkCitiRegister(StudentOrder so){
        return citiRegisterVal.checkCitiRegister(so);
    }

    public AnswerWedding checkWedding(StudentOrder so){
        return weddingVal.checkWedding(so);
    }

    public AnswerChildren checkChildren(StudentOrder so){
        return childrenVal.checkChildren(so);
    }

    public AnswerStudent checkStudent(StudentOrder so){
        return studentVal.checkStudent(so);
    }

    public void sendMail(StudentOrder so){
        mailSender.sendMail(so);
    }
}
