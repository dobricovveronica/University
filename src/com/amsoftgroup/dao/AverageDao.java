package com.amsoftgroup.dao;

import com.amsoftgroup.model.Average;
import com.amsoftgroup.model.Discipline;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class AverageDao {

    private Connection connection;

    public AverageDao(Connection connection) {
        this.connection = connection;
    }

    public Set<Average> getAverage() {
        String sql = "SELECT * FROM university.averages ";
        Set<Average> averages = new HashSet<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Average average = new Average();
                average.setId(Long.parseLong("id"));
                average.getStudent().setId(Long.parseLong("student_id"));
                average.getDiscipline().setId(Long.parseLong("discipline_id"));
                average.setValue(rs.getDouble("value"));
                averages.add(average);
                System.out.println(rs.getLong("id") + " " + rs.getLong("student_id") + " " + rs.getLong("discipline_id") + " " + rs.getString("value"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return averages;
    }

    public void deleteAverage(Average average) {
        String sql = "DELETE FROM university.averages where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, average.getId());
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAverage(Average average) {
        String sql = "UPDATE university.averages SET  value=? where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, average.getValue());
            statement.setLong(2, average.getId());
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertAverage(Average average) {
        String sql = "INSERT INTO university.averages VALUES(?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, average.getStudent().getId());
            statement.setLong(2, average.getDiscipline().getId());
            statement.setDouble(3, 0);
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Average getAverageByDiscipline(Long disciplineId, Long studentId) {
        String sql = "SELECT * FROM university.averages where student_id = ? and discipline_id = ?";
//        Set<Average> averages = new HashSet<>();
        Average average = new Average();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, studentId);
            statement.setLong(2, disciplineId);
            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                average.setValue(rs.getDouble("value"));
                System.out.println(rs.getLong("id") + " " + rs.getLong("student_id") + " " + rs.getLong("discipline_id") + " " + rs.getString("value"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return average;
    }

}