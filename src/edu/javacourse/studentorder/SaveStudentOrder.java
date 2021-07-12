package edu.javacourse.studentorder;

import edu.javacourse.studentorder.domain.Adult;
import edu.javacourse.studentorder.domain.Person;
import edu.javacourse.studentorder.domain.StudentOrder;

public class SaveStudentOrder {
    public static void main(String[] args) {
//        StudentOrder so = new StudentOrder();
        buildStudentOrder();
//        long ans = saveStudentOrder(so);
//        System.out.println(ans);
//
//        schedulerStudentOrder();
//        financeStudentOrder();
    }

    static long saveStudentOrder(StudentOrder studentOrder) {
        long answer = 199;
        System.out.println("saveStudentOrder: ");

        return answer;
    }

    static StudentOrder buildStudentOrder() {
        StudentOrder so = new StudentOrder();
        Adult husband = new Adult();

//        Person p = new Person();

        husband.setGivenName("Andriy");
        husband.setSurName("Pertov");
        husband.setPassportNumber("23345");
        so.setHusband(husband);

        String ans = husband.getPersonString();
        System.out.println(ans);

        return so;
    }

    static void schedulerStudentOrder() {
        System.out.println("Scheduler Order is running");
    }

    static void financeStudentOrder() {
        System.out.println("Finance order is running");
    }
}
