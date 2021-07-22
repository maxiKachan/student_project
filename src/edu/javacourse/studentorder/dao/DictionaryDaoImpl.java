package edu.javacourse.studentorder.dao;

import edu.javacourse.studentorder.config.Config;
import edu.javacourse.studentorder.domain.CountryArea;
import edu.javacourse.studentorder.domain.PassportOffice;
import edu.javacourse.studentorder.domain.RegisterOffice;
import edu.javacourse.studentorder.domain.Street;
import edu.javacourse.studentorder.exception.DaoException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DictionaryDaoImpl implements DictionaryDao {
    private static final String GET_STREET = "SELECT street_code, street_name FROM" +
            " jc_street WHERE UPPER(street_name) LIKE UPPER(?)";
    private static final String GET_PASSPORT = "SELECT * FROM" +
            " jc_passport_office WHERE p_office_area_id = ?";
    private static final String GET_REGISTER = "SELECT * FROM" +
            " jc_register_office WHERE r_office_area_id = ?";
    private static final String GET_AREA = "SELECT * FROM" +
            " jc_country_struct WHERE area_id LIKE ? AND area_id <> ?";

    @Override
    public List<Street> findStreets(String pattern) throws DaoException {
        List<Street> result = new LinkedList<>();
        try (Connection con = getConnection();
             PreparedStatement stat = con.prepareStatement(GET_STREET)) {

            stat.setString(1, "%" + pattern + "%");
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                Street str = new Street(rs.getLong("street_code"), rs.getString("street_name"));
                result.add(str);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return result;
    }

    @Override
    public List<PassportOffice> findPassportOffices(String areaId) throws DaoException {
        List<PassportOffice> result = new LinkedList<>();
        try (Connection con = getConnection();
             PreparedStatement stat = con.prepareStatement(GET_PASSPORT)) {

            stat.setString(1, areaId);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                PassportOffice po = new PassportOffice(
                        rs.getLong("p_office_id"),
                        rs.getString("p_office_area_id"),
                        rs.getString("p_office_name"));
                result.add(po);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return result;
    }

    @Override
    public List<RegisterOffice> findRegisterOffice(String areaId) throws DaoException {
        List<RegisterOffice> result = new LinkedList<>();
        try (Connection con = getConnection();
             PreparedStatement stat = con.prepareStatement(GET_REGISTER)) {

            stat.setString(1, areaId);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                RegisterOffice ro = new RegisterOffice(
                        rs.getLong("r_office_id"),
                        rs.getString("r_office_area_id"),
                        rs.getString("r_office_name"));
                result.add(ro);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return result;
    }

    @Override
    public List<CountryArea> findAreas(String areaId) throws DaoException {
        List<CountryArea> result = new LinkedList<>();

        try (Connection con = getConnection();
             PreparedStatement stat = con.prepareStatement(GET_AREA)) {

            String param1 = buildParam(areaId);
            String param2 = areaId;

            stat.setString(1, param1);
            stat.setString(2, param2);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                CountryArea ca = new CountryArea(
                        rs.getString("area_id"),
                        rs.getString("area_name"));
                result.add(ca);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

        return result;
    }

    private String buildParam(String areaId) throws SQLException{
        if (areaId == null || areaId.trim().isEmpty()){
            return "__0000000000";
        } else if (areaId.endsWith("00000000")){
            return areaId.substring(0,2) + "___0000000";
        } else if (areaId.endsWith("0000000")){
            return areaId.substring(0,5) + "___0000";
        } else if (areaId.endsWith("0000")){
            return areaId.substring(0,8) + "____";
        }
        throw new SQLException("Invalid parameter 'areaId'");
    }

    private Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection(Config.getProperty(Config.DB_URL),
                Config.getProperty(Config.DB_LOGIN),
                Config.getProperty(Config.DB_PASSWORD));
        return con;
    }
}
