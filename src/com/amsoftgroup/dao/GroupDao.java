package com.amsoftgroup.dao;

import com.amsoftgroup.model.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class GroupDao {

    private Connection connection;

    public GroupDao(Connection connection) {
        this.connection = connection;
    }

    public Set<Group> get() {
        String sql = "SELECT * FROM university.groups ";
        Set<Group> groups = new HashSet<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Group group = new Group();
                group.setId(Long.parseLong(rs.getString("id")));
                group.setName(rs.getString("name"));
                groups.add(group);
                System.out.println(rs.getLong("id") + " " + rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

    public void delete(Group group) {
        String sql = "DELETE FROM university.groups where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, group.getId());
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Group group) {
        String sql = "UPDATE university.groups SET name=? where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, group.getName());
            statement.setLong(2, group.getId());
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Group group) {
        String sql = "INSERT INTO university.groups VALUES(?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, group.getName());
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Set<Group> getAllGroups() {
        String sql = "SELECT DISTINCT * FROM university.groups ORDER BY groups.name ASC ";
        Set<Group> groups = new HashSet<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Group group = new Group();
                group.setId(Long.parseLong(rs.getString("id")));
                group.setName(rs.getString("name"));
                groups.add(group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

}
