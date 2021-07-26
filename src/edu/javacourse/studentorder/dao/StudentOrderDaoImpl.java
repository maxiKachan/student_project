package edu.javacourse.studentorder.dao;

import edu.javacourse.studentorder.config.Config;
import edu.javacourse.studentorder.domain.*;
import edu.javacourse.studentorder.exception.DaoException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class StudentOrderDaoImpl implements StudentOrderDao {

    public static final String INSERT_ORDER = "INSERT INTO jc_student_order(" +
            "student_order_status, student_order_date, h_sur_name, h_given_name, h_patronymic," +
            " h_date_of_birth, h_passport_serial, h_passport_number, h_passport_date, h_passport_office_id," +
            " h_post_index, h_street_code, h_building, h_extension, h_apartment, h_university_id, h_student_number," +
            " w_sur_name, w_given_name, w_patronymic, w_date_of_birth, w_passport_serial, w_passport_number," +
            " w_passport_date, w_passport_office_id, w_post_index, w_street_code, w_building, w_extension," +
            "w_apartment, w_university_id, w_student_number, certificate_id, register_office_id, marriage_date) " +
            "VALUES (?, ?, ?, ?, ?," +
            " ?, ?, ?, ?, ?," +
            " ?, ?, ?, ?, ?, ?, ?," +
            " ?, ?, ?, ?, ?, ?," +
            " ?, ?, ?, ?, ?, ?," +
            " ?, ?, ?, ?, ?, ?)";

    public static final String INSERT_CHILD = "INSERT INTO jc_student_child(" +
            "student_order_id, c_sur_name, c_given_name, c_patronymic, c_date_of_birth," +
            " c_certificate_number, c_certificate_date, c_register_office_id, c_post_index, c_street_code," +
            " c_building, c_extension, c_apartment) " +
            "VALUES (?, ?, ?, ?, ?," +
            " ?, ?, ?, ?, ?," +
            " ?, ?, ?)";

    public static final String SELECT_ORDERS = "SELECT * FROM jc_student_order WHERE student_order_status = 0" +
            " ORDER BY student_order_date";

    @Override
    public Long saveStudentOrder(StudentOrder so) throws DaoException {
        Long result = -1L;

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT_ORDER, new String[]{"student_order_id"})) {

            con.setAutoCommit(false);
            try {
                //Header
                stmt.setInt(1, StudentOrderStatus.START.ordinal());
                stmt.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));

                //Husband and Wife
                setParamsForAdult(stmt, 3, so.getHusband());
                setParamsForAdult(stmt, 18, so.getWife());

                //Marriage
                stmt.setString(33, so.getMarriageCertificateId());
                stmt.setLong(34, so.getMarriageOffice().getOfficeId());
                stmt.setDate(35, java.sql.Date.valueOf(so.getMarriageDate()));

                stmt.executeUpdate();

                ResultSet gkRs = stmt.getGeneratedKeys();
                if (gkRs.next()) {
                    result = gkRs.getLong(1);
                }
                gkRs.close();

                saveChildren(con, so, result);

                con.commit();
            } catch (SQLException ex) {
                con.rollback();
                throw ex;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return result;
    }

    @Override
    public List<StudentOrder> getStudentOrders() throws DaoException {
        List<StudentOrder> result = new LinkedList<>();
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_ORDERS)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                StudentOrder so = new StudentOrder();
                fillStudentOrder(rs, so);
                fillMarriage(rs, so);
                Adult husband = fillAdult(rs, "h_");
                Adult wife = fillAdult(rs, "w_");
                so.setHusband(husband);
                so.setWife(wife);

                result.add(so);
            }

            rs.close();
        } catch (SQLException e) {
            throw new DaoException();
        }
        return result;
    }

    private Adult fillAdult(ResultSet rs, String pref) throws SQLException {
        Adult adult = new Adult();
        adult.setSurName(rs.getString(pref+"sur_name"));
        adult.setGivenName(rs.getString(pref+"given_name"));
        adult.setPatronymic(rs.getString(pref+"patronymic"));
        adult.setDayOfBirth(rs.getDate(pref+"date_of_birth").toLocalDate());
        adult.setPassportSerial(rs.getString(pref+"passport_serial"));
        adult.setPassportNumber(rs.getString(pref+"passport_number"));
        adult.setIssueDate(rs.getDate(pref+"passport_date").toLocalDate());

        PassportOffice po = new PassportOffice(rs.getLong(pref+"passport_office_id"),
                "", "");
        adult.setPassportOffice(po);

        Address adr = new Address();
        Street st = new Street(rs.getLong(pref+"street_code"), "");
        adr.setStreet(st);
        adr.setPostCode(rs.getString(pref+"post_index"));
        adr.setBuilding(rs.getString(pref+"building"));
        adr.setExtension(rs.getString(pref+"extension"));
        adr.setApartment(rs.getString(pref+"apartment"));
        adult.setAddress(adr);

        University uni = new University(rs.getLong(pref+"university_id"), "");
        adult.setUniversity(uni);
        adult.setStudentID(pref+"student_number");

        return adult;
    }

    private void fillMarriage(ResultSet rs, StudentOrder so) throws SQLException {
        so.setMarriageCertificateId(rs.getString("certificate_id"));
        so.setMarriageDate(rs.getDate("marriage_date").toLocalDate());

        Long roId = rs.getLong("register_office_id");
        RegisterOffice ro = new RegisterOffice(roId, "", "");
        so.setMarriageOffice(ro);
    }

    private void fillStudentOrder(ResultSet rs, StudentOrder so) throws SQLException{
        so.setStudentOrderID(rs.getLong("student_order_id"));
        so.setStudentOrderDate(rs.getTimestamp("student_order_date").toLocalDateTime());
        so.setStudentOrderStatus(StudentOrderStatus.fromValue(rs.getInt("student_order_status")));

    }

    private void saveChildren(Connection con, StudentOrder so, Long result) throws SQLException {
        try (PreparedStatement stmt = con.prepareStatement(INSERT_CHILD)) {
            for (Child child : so.getChildren()) {
                stmt.setLong(1, result);
                setParamsForChild(stmt, child);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    //TODO refactoring - make one method
    private Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection(Config.getProperty(Config.DB_URL),
                Config.getProperty(Config.DB_LOGIN),
                Config.getProperty(Config.DB_PASSWORD));
        return con;
    }

    private void setParamsForAdult(PreparedStatement stmt, int start, Adult adult) throws SQLException {
        setParamsForPerson(stmt, start, adult);
        stmt.setString(start + 4, adult.getPassportSerial());
        stmt.setString(start + 5, adult.getPassportNumber());
        stmt.setDate(start + 6, java.sql.Date.valueOf(adult.getIssueDate()));
        stmt.setLong(start + 7, adult.getPassportOffice().getOfficeId());
        setParamsForAddress(stmt, start + 8, adult);
        stmt.setLong(start + 13, adult.getUniversity().getUniversityId());
        stmt.setString(start + 14, adult.getStudentID());
    }

    private void setParamsForChild(PreparedStatement stmt, Child child) throws SQLException {
        setParamsForPerson(stmt, 2, child);
        stmt.setString(6, child.getCertificateNumber());
        stmt.setDate(7, java.sql.Date.valueOf(child.getIssueDate()));
        stmt.setLong(8, child.getIssueDepartment().getOfficeId());
        setParamsForAddress(stmt, 9, child);
    }

    private void setParamsForPerson(PreparedStatement stmt, int start, Person person) throws SQLException {
        stmt.setString(start, person.getSurName());
        stmt.setString(start + 1, person.getGivenName());
        stmt.setString(start + 2, person.getPatronymic());
        stmt.setDate(start + 3, java.sql.Date.valueOf(person.getDayOfBirth()));
    }

    private void setParamsForAddress(PreparedStatement stmt, int start, Person person) throws SQLException {
        Address address = person.getAddress();
        stmt.setString(start, address.getPostCode());
        stmt.setLong(start + 1, address.getStreet().getStreetCode());
        stmt.setString(start + 2, address.getBuilding());
        stmt.setString(start + 3, address.getExtension());
        stmt.setString(start + 4, address.getApartment());
    }
}