package com.amsoftgroup.dao;

import com.amsoftgroup.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class DisciplineDao {

    private Connection connection;

    public DisciplineDao(Connection connection) {
        this.connection = connection;
    }

    public Set<Discipline> get() {
        String sql = "SELECT * FROM university.disciplines ";
        Set<Discipline> disciplines = new HashSet<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Discipline discipline = new Discipline();
                discipline.setId(Long.parseLong(rs.getString("id")));
                discipline.setTitle(rs.getString("title"));
                discipline.getTeachers().setId(Long.parseLong(rs.getString("teacher_id")));
                disciplines.add(discipline);
                System.out.println(rs.getLong("id") + " " + rs.getString("title") + " " + rs.getLong("teacher_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return disciplines;
    }

    public void delete(Discipline discipline) {
        String sql = "DELETE FROM university.disciplines where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, discipline.getId());
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Discipline discipline) {
        String sql = "UPDATE university.disciplines SET title=?, teacher_id=? where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, discipline.getTitle());
            statement.setLong(2, discipline.getTeachers().getId());
            statement.setLong(3, discipline.getId());
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Discipline discipline) {
        String sql = "INSERT INTO university.disciplines VALUES(?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, discipline.getTitle());
            statement.setLong(2, discipline.getTeachers().getId());
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Set<Discipline> getAllDisciplines() {
        String sql = "SELECT DISTINCT * FROM university.disciplines ORDER BY disciplines.title ASC ";
        java.util.Set<Discipline> disciplines = new HashSet<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Discipline discipline = new Discipline();
                discipline.setId(Long.parseLong(rs.getString("id")));
                discipline.setTitle(rs.getString("title"));
                disciplines.add(discipline);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return disciplines;
    }

    public Set<Discipline> getDisciplineById(Long studentId) {
        String sql = "select P.id, " +
                "D.id as did, D.title, " +
                "M.id, M.value " +
                "from university.persons as P " +
                "inner join university.students as S on S.id = P.id " +
                "left join university.disciplines_to_students as DS on S.id = DS.student_id " +
                "left join university.disciplines as D on DS.discipline_id = D.id " +
                "left join university.marks as M on D.id = M.discipline_id " +
                "where P.id = ? ";

        Set<Discipline> disciplines = new HashSet<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            System.out.println(statement.toString());
            statement.setLong(1, studentId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Discipline discipline = new Discipline();
                discipline.setTitle(rs.getString("title"));
                disciplines.add(discipline);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return disciplines;
    }

}
