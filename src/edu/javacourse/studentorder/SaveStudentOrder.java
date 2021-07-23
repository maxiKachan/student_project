package edu.javacourse.studentorder;

import edu.javacourse.studentorder.dao.DictionaryDaoImpl;
import edu.javacourse.studentorder.dao.StudentOrderDaoImpl;
import edu.javacourse.studentorder.domain.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

public class SaveStudentOrder {
    public static void main(String[] args) throws Exception{
//        List<Street> d = new DictionaryDaoImpl().findStreets("про");
//        for (Street s : d){
//            System.out.println(s.getStreetName());
//        }
//        List<PassportOffice> po = new DictionaryDaoImpl().findPassportOffices("010020000000");
//        for (PassportOffice p : po){
//            System.out.println(p.getOfficeName());
//        }
//        List<RegisterOffice> ro = new DictionaryDaoImpl().findRegisterOffice("010010000000");
//        for (RegisterOffice r : ro){
//            System.out.println(r.getOfficeName());
//        }
//        List<CountryArea> ca1 = new DictionaryDaoImpl().findAreas("");
//        for (CountryArea c : ca1){
//            System.out.println(c.getAreaId() + " : " +c.getAreaName());
//        }
//        System.out.println("---->");
//        List<CountryArea> ca2 = new DictionaryDaoImpl().findAreas("020000000000");
//        for (CountryArea c : ca2) {
//            System.out.println(c.getAreaId() + " : " + c.getAreaName());
//        }
//        System.out.println("---->");
//        List<CountryArea> ca3 = new DictionaryDaoImpl().findAreas("020010000000");
//        for (CountryArea c : ca3) {
//            System.out.println(c.getAreaId() + " : " + c.getAreaName());
//        }
//        System.out.println("---->");
//        List<CountryArea> ca4 = new DictionaryDaoImpl().findAreas("020010010000");
//        for (CountryArea c : ca4) {
//            System.out.println(c.getAreaId() + " : " + c.getAreaName());
//        }
        StudentOrder so = buildStudentOrder(10);
        StudentOrderDaoImpl studentOrderDao = new StudentOrderDaoImpl();
        Long id = studentOrderDao.saveStudentOrder(so);
        System.out.println(id);
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
        RegisterOffice ro1 = new RegisterOffice(1L,"","");
        so.setMarriageOffice(ro1);

        Street street = new Street(1L, "First Street");

        Address address = new Address("195000", street, "12", "142");

        //Муж
        Adult husband = new Adult("Петров", "Виктор", "Сергеевич",
                LocalDate.of(1997,8,24));
        husband.setPassportSerial("" + (1000 + id));
        husband.setPassportNumber("" + (100000 + id));
        husband.setIssueDate(LocalDate.of(2017, 9 , 15));
        PassportOffice po1 = new PassportOffice(1L, "", "");
        husband.setPassportOffice(po1);
        husband.setStudentID("" + (100000 + id));
        husband.setAddress(address);
        //Жена
        Adult wife = new Adult("Петрова", "Вероника", "Алексеевна",
                 LocalDate.of(1998,3,12));
        wife.setPassportSerial("" + (2000 + id));
        wife.setPassportNumber("" + (200000 + id));
        wife.setIssueDate(LocalDate.of(2018, 4 , 5));
        PassportOffice po2 = new PassportOffice(2L, "", "");
        wife.setPassportOffice(po2);
        wife.setStudentID("" + (200000 + id));
        wife.setAddress(address);
        //Ребенок
        Child child1 = new Child("Петрова", "Ирина", "Викторовна",
                 LocalDate.of(2018,6,29));
        child1.setCertificateNumber("" + (300000 + id));
        child1.setIssueDate(LocalDate.of(2018,7,19));
        RegisterOffice ro2 = new RegisterOffice(2L,"","");
        child1.setIssueDepartment(ro2);
        child1.setAddress(address);
        //Ребенок
        Child child2 = new Child("Петров", "Евгений", "Викторович",
                LocalDate.of(2018,6,29));
        child2.setCertificateNumber("" + (400000 + id));
        child2.setIssueDate(LocalDate.of(2018,7,19));
        RegisterOffice ro3 = new RegisterOffice(3L,"","");
        child2.setIssueDepartment(ro3);
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
