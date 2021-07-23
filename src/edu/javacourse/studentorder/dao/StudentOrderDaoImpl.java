package edu.javacourse.studentorder.dao;

import edu.javacourse.studentorder.config.Config;
import edu.javacourse.studentorder.domain.*;
import edu.javacourse.studentorder.exception.DaoException;

import java.sql.*;
import java.time.LocalDateTime;

public class StudentOrderDaoImpl implements StudentOrderDao{

    public static final String INSERT_ORDER = "INSERT INTO jc_student_order(" +
            "student_order_status, student_order_date, h_sur_name, h_given_name,"  +
    "h_patronymic,h_date_of_birth, h_passport_serial, h_passport_number, h_passport_date,h_passport_office_id," +
    "h_post_index,h_street_code, h_building, h_extension, h_apartment, w_sur_name, w_given_name, w_patronymic," +
    "w_date_of_birth,w_passport_serial, w_passport_number, w_passport_date, w_passport_office_id, w_post_index," +
    "w_street_code, w_building, w_extension, w_apartment, certificate_id, register_office_id, marriage_date) " +
    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    @Override
    public Long saveStudentOrder(StudentOrder so) throws DaoException {
        Long result = -1L;

        try (Connection con = getConnection();
             PreparedStatement stat = con.prepareStatement(INSERT_ORDER, new String[] {"student_order_id"})){
            //Header
            stat.setInt(1, StudentOrderStatus.START.ordinal());
            stat.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));

            //Husband and Wife
            setParamsForAdult(stat, 3, so.getHusband());
            setParamsForAdult(stat,16, so.getWife());

            //Marriage
            stat.setString(29, so.getMarriageCertificateId());
            stat.setLong(30, so.getMarriageOffice().getOfficeId());
            stat.setDate(31, java.sql.Date.valueOf(so.getMarriageDate()));

            stat.executeUpdate();

            ResultSet gkRs = stat.getGeneratedKeys();
            if (gkRs.next()){
                result = gkRs.getLong(1);
            }
            gkRs.close();

        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return result;
    }
    //TODO refactoring - make one method
    private Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection(Config.getProperty(Config.DB_URL),
                Config.getProperty(Config.DB_LOGIN),
                Config.getProperty(Config.DB_PASSWORD));
        return con;
    }

    private void setParamsForAdult(PreparedStatement stat, int start, Adult adult) throws SQLException{
        stat.setString(start,adult.getSurName());
        stat.setString(start+1, adult.getGivenName());
        stat.setString(start+2, adult.getPatronymic());
        stat.setDate(start+3, java.sql.Date.valueOf(adult.getDayOfBirth()));
        stat.setString(start+4, adult.getPassportSerial());
        stat.setString(start+5, adult.getPassportNumber());
        stat.setDate(start+6, java.sql.Date.valueOf(adult.getIssueDate()));
        stat.setLong(start+7, adult.getPassportOffice().getOfficeId());
        Address address = adult.getAddress();
        stat.setString(start+8, address.getPostCode());
        stat.setLong(start+9, address.getStreet().getStreetCode());
        stat.setString(start+10, address.getBuilding());
        stat.setString(start+11, address.getExtension());
        stat.setString(start+12, address.getApartment());
    }
}
