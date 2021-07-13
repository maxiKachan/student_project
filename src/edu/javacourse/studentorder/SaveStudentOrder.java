package edu.javacourse.studentorder;

import edu.javacourse.studentorder.domain.Adult;
import edu.javacourse.studentorder.domain.StudentOrder;

public class SaveStudentOrder {
    public static void main(String[] args) {
        buildStudentOrder(10);
    }

    static long saveStudentOrder(StudentOrder studentOrder) {
        long answer = 199;
        System.out.println("saveStudentOrder: ");

        return answer;
    }

    static StudentOrder buildStudentOrder(long id) {
        StudentOrder so = new StudentOrder();
        so.setStudentOrderID(id);

        StudentOrder so1 = so;

        printStudentOrder(so1);

        return so;
    }

    static void printStudentOrder(StudentOrder so){
        System.out.println(so.getStudentOrderID());
    }

    static void schedulerStudentOrder() {
        System.out.println("Scheduler Order is running");
    }

    static void financeStudentOrder() {
        System.out.println("Finance order is running");
    }
}
