package com.amsoftgroup.dao;

import com.amsoftgroup.model.PhoneType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class PhoneTypeDao {

    private Connection connection;

    public PhoneTypeDao(Connection connection) {
        this.connection = connection;
    }

    public Set<PhoneType> get() {
        String sql = "SELECT * FROM university.phone_types ";
        Set<PhoneType> phoneTypes = new HashSet<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                PhoneType phoneType = new PhoneType();
                phoneType.setId(Long.parseLong(rs.getString("id")));
                phoneType.setName(rs.getString("name"));
                phoneTypes.add(phoneType);
                System.out.println(rs.getLong("id") + " " + rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phoneTypes;
    }

    public void delete(PhoneType phoneType) {
        String sql = "DELETE FROM university.phone_types where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, phoneType.getId());
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(PhoneType phoneType) {
        String sql = "UPDATE university.phone_types SET name=? where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, phoneType.getName());
            statement.setLong(2, phoneType.getId());
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(PhoneType phoneType) {
        String sql = "INSERT INTO university.phone_types VALUES(?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, phoneType.getName());
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Set<PhoneType> getAllPhoneTypes() {
        String sql = "SELECT DISTINCT * FROM university.phone_types ORDER BY phone_types.name ASC ";
        Set<PhoneType> phoneTypes = new HashSet<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                PhoneType phoneType = new PhoneType();
                phoneType.setId(Long.parseLong(rs.getString("id")));
                phoneType.setName(rs.getString("name"));
                phoneTypes.add(phoneType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phoneTypes;
    }
}
