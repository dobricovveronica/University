package com.amsoftgroup.dao;

import com.amsoftgroup.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class StudentFilterDao {
    private Connection connection;
    private StudentDao studentDao;


    public StudentFilterDao(Connection connection) {
        this.connection = connection;
        this.studentDao = new StudentDao(connection);
    }

    public Set<Student> searchStudents(StudentFilter studentFilter) {
        String sql = "select P.id as pid, " +
                "P.first_name, " +
                "P.last_name, " +
                "P.date_of_birth, " +
                "P.gender, P.picture, " +
                "P.mail, " +
                "A.id   as aid, " +
                "A.country, " +
                "A.city, " +
                "A.address, " +
                "LA.id  as laid, " +
                "LA.status, " +
                "LA.start_date, " +
                "LA.end_date, " +
                "G.id   as gid, " +
                "G.name as gname, " +
                "D.id, " +
                "D.title, " +
                "DtoS.student_id, " +
                "DtoS.discipline_id " +
                "from university.students as S " +
                "inner join university.persons as P on P.id = S.id " +
                "left join university.addresses as A on P.address_id = A.id " +
                "left join university.library_abonaments as LA on P.library_abonament_id = LA.id " +
                "left join university.groups as G on G.id = S.group_id " +
                "left join university.disciplines_to_students as DtoS on S.id = DtoS.student_id " +
                "left join university.disciplines as D on D.id = DtoS.discipline_id ";
        //                "and (P.date_of_birth = ? or P.date_of_birth between ? and ?) " +

        Set<Student> students = new HashSet<>();
        Student student = new Student();
        sql += queryBuilder(studentFilter);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                student.setId(Long.parseLong(rs.getString("pid")));
                student = studentDao.builStudent(rs, student);
                students.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public String queryBuilder(StudentFilter studentFilter) {
        StringBuilder builder = new StringBuilder();
        if (studentFilter != null) {
            builder.append(" where ");
            int count = 0;
            String prefix = " and ";
            if (studentFilter.getName() != null) {
                String s1 = "(P.first_name LIKE '" + studentFilter.getName() + "%' or P.last_name LIKE '" + studentFilter.getName() + "%')";
                builder.append(count > 0 ? prefix + s1 : s1);
                count++;
            }
            if (studentFilter.getStudentAddress() != null) {
                String s2 = "(A.city LIKE '"+ studentFilter.getStudentAddress() +"%' or A.country LIKE '"+ studentFilter.getStudentAddress() +"%' or A.address LIKE '"+ studentFilter.getStudentAddress() +"%')";
                builder.append(count > 0 ? prefix + s2 : s2);
                count++;
            }
//            statement.setDate(3, Date.valueOf(start_date));
//            statement.setDate(4, Date.valueOf(start_date));
//            statement.setDate(5,  Date.valueOf(end_date));
            if (studentFilter.getGroupId() != null) {
                String s3 = " G.id = '" + studentFilter.getGroupId() + "'";
                builder.append(count > 0 ? prefix + s3 : s3);
                count++;
            }
            if (studentFilter.getGender() != null) {
                String s4 = " P.gender = '" + studentFilter.getGender() + "'";
                builder.append(count > 0 ? prefix + s4 : s4);
                count++;
            }
            if (studentFilter.getDisciplineId() != null) {
                String s5 = " D.id = '" + studentFilter.getDisciplineId() + "'";
                builder.append(count > 0 ? prefix + s5 : s5);
                count++;
            }
            if (studentFilter.getDisciplineTitle() != null) {
                String s6 = " D.title LIKE '" + studentFilter.getDisciplineTitle() + "%'";
                builder.append(count > 0 ? prefix + s6 : s6);
                count++;
            }
        }

        return builder.toString();
    }
}

