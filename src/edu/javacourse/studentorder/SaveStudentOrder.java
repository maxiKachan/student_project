package edu.javacourse.studentorder;

import edu.javacourse.studentorder.domain.Address;
import edu.javacourse.studentorder.domain.Adult;
import edu.javacourse.studentorder.domain.Child;
import edu.javacourse.studentorder.domain.StudentOrder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

public class SaveStudentOrder {
    public static void main(String[] args) throws Exception{
//        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jc_student",
                "postgres", "root");

        Statement stat = con.createStatement();
        ResultSet rs = stat.executeQuery("SELECT * FROM jc_street");
        while (rs.next()){
            System.out.println(rs.getLong(1) + " : " + rs.getString(2));
        }
        //buildStudentOrder(10);
    }

    static long saveStudentOrder(StudentOrder studentOrder) {
        long answer = 199;
        System.out.println("saveStudentOrder: ");

        return answer;
    }

    static StudentOrder buildStudentOrder(long id) {
        StudentOrder so = new StudentOrder();
        so.setStudentOrderID(id);
        so.setMarriageCertificateId("" + (12345600 + id));
        so.setMarriageDate(LocalDate.of(2016,7,4));
        so.setMarriageOffice("Отдел ЗАГС");

        Address address = new Address("195000", "Заневский пр.", "12", "142");

        //Муж
        Adult husband = new Adult("Петров", "Виктор", "Сергеевич",
                LocalDate.of(1997,8,24));
        husband.setPassportSerial("" + (1000 + id));
        husband.setPassportNumber("" + (100000 + id));
        husband.setIssueDate(LocalDate.of(2017, 9 , 15));
        husband.setIssueDepartment("Отдел милиции №" + id);
        husband.setStudentID("" + (100000 + id));
        husband.setAddress(address);
        //Жена
        Adult wife = new Adult("Петрова", "Вероника", "Алексеевна",
                 LocalDate.of(1998,3,12));
        wife.setPassportSerial("" + (2000 + id));
        wife.setPassportNumber("" + (200000 + id));
        wife.setIssueDate(LocalDate.of(2018, 4 , 5));
        wife.setIssueDepartment("Отдел милиции №" + id);
        wife.setStudentID("" + (200000 + id));
        wife.setAddress(address);
        //Ребенок
        Child child1 = new Child("Петрова", "Ирина", "Викторовна",
                 LocalDate.of(2018,6,29));
        child1.setCertificateNumber("" + (300000 + id));
        child1.setIssueDate(LocalDate.of(2018,7,19));
        child1.setIssueDepartment("Отдел ЗАГС №" + id);
        child1.setAddress(address);
        //Ребенок
        Child child2 = new Child("Петров", "Евгений", "Викторович",
                LocalDate.of(2018,6,29));
        child2.setCertificateNumber("" + (400000 + id));
        child2.setIssueDate(LocalDate.of(2018,7,19));
        child2.setIssueDepartment("Отдел ЗАГС №" + id);
        child2.setAddress(address);

        so.setHusband(husband);
        so.setWife(wife);
        so.addChild(child1);
        so.addChild(child2);

        return so;
    }

    static void printStudentOrder(StudentOrder so){
        System.out.println(so.getStudentOrderID());
    }

}
