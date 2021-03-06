package com.amsoftgroup.dao;

import com.amsoftgroup.model.Discipline;
import com.amsoftgroup.model.Person;
import com.amsoftgroup.model.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class TeacherDao {

    private Connection connection;

    public TeacherDao(Connection connection) {
        this.connection = connection;
    }

    public Set<Teacher> getTeacher() {
        String sql = "SELECT * FROM university.teachers ";
        Set<Teacher> teachers = new HashSet<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(Long.parseLong(rs.getString("id")));
                teacher.setSalary(rs.getDouble("salary"));
                teachers.add(teacher);
                System.out.println(rs.getLong("id") + " " + rs.getDouble("salary"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    public void deleteTeacher(Teacher teacher) {
        String sql = "DELETE FROM university.teachers where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, teacher.getId());
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTeacher(Teacher teacher) {
        String sql = "UPDATE university.teachers SET salary=? where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, teacher.getSalary());
            statement.setLong(2, teacher.getId());
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertTeacher(Teacher teacher) {
        String sql = "INSERT INTO university.teachers VALUES(?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, teacher.getSalary());
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Set<Teacher> getAllTeacher() {
        String sql = "Select P.id, P.first_name, P.last_name, T.id as tid from university.persons as P " +
                "inner join university.teachers as T on T.id = P.id  ";
        Set<Teacher> teachers = new HashSet<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(Long.parseLong(rs.getString("tid")));
                teacher.setFirstName(rs.getString("first_name"));
                teacher.setLastName(rs.getString("last_name"));
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    public Teacher getTeacherById(Long teacherId) {
        String sql = "Select P.id, P.first_name, P.last_name, T.id as tid " +
                "from university.persons as P " +
                "inner join university.teachers as T on T.id = P.id " +
                "where T.id = ?";
        Teacher teacher = new Teacher();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, teacherId);
            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                teacher.setId(Long.parseLong(rs.getString("tid")));
                teacher.setFirstName(rs.getString("first_name"));
                teacher.setLastName(rs.getString("last_name"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacher;
    }

}
