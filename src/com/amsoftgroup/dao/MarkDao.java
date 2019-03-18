package com.amsoftgroup.dao;

import com.amsoftgroup.model.Discipline;
import com.amsoftgroup.model.Mark;
import com.amsoftgroup.model.Student;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class MarkDao {

    private Connection connection;

    public MarkDao(Connection connection) {
        this.connection = connection;
    }

    public Set<Mark> get() {
        String sql = "SELECT * FROM university.marks ";
        Set<Mark> marks = new HashSet<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Mark mark = new Mark();
                mark.setId(Long.parseLong(rs.getString("id")));
                mark.getStudent().setId(Long.parseLong(rs.getString("student_id")));
                mark.getDiscipline().setId(Long.parseLong(rs.getString("discipline_id")));
                mark.getTeacher().setId(Long.parseLong(rs.getString("teacher_id")));
                mark.setValue(Long.parseLong(rs.getString("value")));
                mark.setCreateData(LocalDate.parse(String.valueOf(rs.getDate("create_data"))));
                marks.add(mark);
                System.out.println(rs.getLong("id") + " " + rs.getString("title")+ " " + rs.getLong("teacher_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return marks;
    }

    public void delete(Mark mark) {
        String sql = "DELETE FROM university.marks where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, mark.getId());
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Mark mark) {
        String sql = "UPDATE university.marks SET student_id=?, discipline_id=?, teacher_id=?, value=?, create_data=? where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, mark.getStudent().getId());
            statement.setLong(2, mark.getDiscipline().getId());
            statement.setLong(3, mark.getTeacher().getId());
            statement.setDouble(4, mark.getValue());
            statement.setDate(5, Date.valueOf(mark.getCreateData()));
            statement.setLong(6,mark.getId());
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Mark mark, Long id) {
        String sql = "INSERT INTO university.marks VALUES(?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.setLong(2, mark.getDiscipline().getId());
            statement.setLong(3, mark.getTeacher().getId());
            statement.setDouble(4, mark.getValue());
            statement.setDate(5, Date.valueOf(mark.getCreateData()));
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Mark getMarkByStudentId(Long id) {
        String sql = "SELECT * FROM university.marks where marks.id = ?";
        Mark mark = new Mark();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();
                statement.setLong(1,id);

                mark.getStudent().setId(Long.parseLong(rs.getString("student_id")));
                mark.getDiscipline().setId(Long.parseLong(rs.getString("discipline_id")));
                mark.getTeacher().setId(Long.parseLong(rs.getString("teacher_id")));
                mark.setValue(Long.parseLong(rs.getString("value")));
                mark.setCreateData(LocalDate.parse(String.valueOf(rs.getDate("create_data"))));
                System.out.println(rs.getLong("id") + " " + rs.getString("title")+ " " + rs.getLong("teacher_id"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mark;
    }

}
