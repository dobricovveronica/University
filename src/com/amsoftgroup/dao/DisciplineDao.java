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

    public Set<Discipline> getDiscipline() {
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
                discipline.getTeacher().setId(Long.parseLong(rs.getString("teacher_id")));

                disciplines.add(discipline);
                System.out.println(rs.getLong("id") + " " + rs.getString("title") + " " + rs.getLong("teacher_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return disciplines;
    }

    public void deleteDiscipline(Discipline discipline) {
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

    public void updateDiscipline(Discipline discipline) {
        String sql = "UPDATE university.disciplines SET title=?, teacher_id=? where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, discipline.getTitle());
            statement.setLong(2, discipline.getTeacher().getId());
            statement.setLong(3, discipline.getId());
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertDiscipline(Discipline discipline) {
        String sql = "INSERT INTO university.disciplines VALUES(?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, discipline.getTitle());
            statement.setLong(2, discipline.getTeacher().getId());
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
        String sql = "select distinct  " +
                "D.id as did, D.title, P.id, T.id as tid " +
                "from university.persons as P " +
                "left join university.students as S on S.id = P.id " +
                "left join university.disciplines_to_students as DS on S.id = DS.student_id " +
                "left join university.disciplines as D on DS.discipline_id = D.id " +
                "left join university.teachers as T on T.id = D.teacher_id " +
                "where P.id = ? ";
        Set<Discipline> disciplines = new HashSet<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, studentId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Discipline discipline = new Discipline();
                discipline.setId(Long.parseLong(rs.getString("did")));
                discipline.setTitle(rs.getString("title"));

                Teacher teacher = new Teacher();
                teacher.setId(Long.parseLong(rs.getString("tid")));

                discipline.setTeacher(new TeacherDao(connection).getTeacherById(teacher.getId()));
                Set<Mark> marks = new MarkDao(connection).getMarkByDisciplineId(discipline.getId(), studentId);
                discipline.setMarks((HashSet<Mark>) marks);

                Average average = new AverageDao(connection).getAverageByDiscipline(discipline.getId(), studentId);
                discipline.setAverage(average);
                disciplines.add(discipline);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return disciplines;
    }
    public void addDisciplineToStudent(Student student, Discipline discipline) {
        String sql = "INSERT INTO university.disciplines_to_students(student_id, discipline_id) VALUES(?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, student.getId());
            statement.setLong(2, discipline.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
