package com.amsoftgroup.dao;

import com.amsoftgroup.model.*;
import com.amsoftgroup.dao.*;


import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class StudentDao {

    private Connection connection;

    public StudentDao(Connection connection) {
        this.connection = connection;
    }

    public Set<Student> get() {
        String sql = "SELECT * FROM university.students ";
        Set<Student> students = new HashSet<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setId(Long.parseLong(rs.getString("id")));
                student.getGroup().setId(Long.parseLong(rs.getString("group_id")));
                students.add(student);
                System.out.println(rs.getLong("id") + " " + rs.getLong("group_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public void delete(Long id) {
        String sql = "DELETE FROM university.students where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            System.out.println(statement.toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Student student) {
        String sql = "UPDATE university.students SET group_id=? where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, student.getGroup().getId());
            statement.setLong(2, student.getId());
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Student student) {
        String sql = "INSERT INTO university.students VALUES(?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, student.getGroup().getId());
//            statement.setString(1, student.getFirstName());
//            statement.setString(2, student.getLastName());
//            statement.setDate(3, Date.valueOf(student.getDateOfBirth()));
////            statement.setString(4, student.getGender().charAt(0));
//            statement.setString(4, student.getAddresses().getCountry());
//            statement.setString(5, student.getAddresses().getCity());
//            statement.setString(6, student.getAddresses().getAddress());
////            statement.setString(8, student.getPhones());
//            statement.setString(7, student.getGroup().getName());

            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Set<Student> getAllStudents() {
        String sql = "select P.id, " +
                "P.first_name, " +
                "P.last_name, " +
                "P.date_of_birth, " +
                "P.gender, " +
                "P.mail, " +
                "A.id as aid, " +
                "A.country, A.city, " +
                "A.address, " +
                "LA.id as laid, " +
                "LA.status, LA.start_date, LA.end_date, " +
                "G.id as gid, " +
                "G.name as gname " +
                "from university.students as S " +
                "inner join university.persons as P on P.id = S.id " +
                "left join university.addresses as A on P.address_id = A.id " +
                "left join university.library_abonaments as LA on P.library_abonament_id = LA.id " +
                "left join university.groups as G on G.id = S.group_id";
        Set<Student> students = new HashSet<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setId(Long.parseLong(rs.getString("id")));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setDateOfBirth(LocalDate.parse(String.valueOf(rs.getDate("date_of_birth"))));
                student.setGender(rs.getString("gender").charAt(0));
                student.setMail(rs.getString("mail"));

                Address address = new Address();
                address.setId(Long.parseLong(rs.getString("aid")));
                address.setCountry(rs.getString("country"));
                address.setAddress(rs.getString("address"));
                address.setCity(rs.getString("city"));
                student.setAddresses(address);

                LibraryAbonament libraryAbonament = new LibraryAbonament();
                libraryAbonament.setId(Long.parseLong(rs.getString("laid")));
                libraryAbonament.setStatus(rs.getString("status"));
                libraryAbonament.setStartDate(LocalDate.parse(String.valueOf(rs.getDate("start_date"))));
                libraryAbonament.setEndDate(LocalDate.parse(String.valueOf(rs.getDate("end_date"))));
                student.setLibraryAbonament(libraryAbonament);

                student.setPhones(new PhoneDao(connection).getPhonesById(student.getId()));

                Group group = new Group();
                group.setId(Long.parseLong(rs.getString("gid")));
                group.setName(rs.getString("gname"));
                student.setGroup(group);

                student.setDisciplines(new DisciplineDao(connection).getDisciplineById(student.getId()));

                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public Student getStudentById(Long id) {
        String sql = "select  " +
                "P.first_name as pfn, " +
                "P.last_name, " +
                "P.date_of_birth, " +
                "P.gender, " +
                "P.mail, " +
                "A.id as aid, " +
                "A.country, A.city, " +
                "A.address, " +
                "LA.id as laid, " +
                "LA.status, LA.start_date, LA.end_date, " +
                "G.id as gid, " +
                "G.name as gname " +
                "from university.students as S " +
                "inner join university.persons as P on P.id = S.id " +
                "left join university.addresses as A on P.address_id = A.id " +
                "left join university.library_abonaments as LA on P.library_abonament_id = LA.id " +
                "left join university.groups as G on G.id = S.group_id " +
                "where S.id = ?";
        Student student = new Student();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            System.out.println(statement.toString());
            statement.setLong(1, id);
            student.setId(id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                student.setFirstName(rs.getString("pfn"));
                student.setLastName(rs.getString("last_name"));
                student.setDateOfBirth(LocalDate.parse(String.valueOf(rs.getDate("date_of_birth"))));
                student.setGender(rs.getString("gender").charAt(0));
                student.setMail(rs.getString("mail"));

                Address address = new Address();
                address.setId(Long.parseLong(rs.getString("aid")));
                address.setCountry(rs.getString("country"));
                address.setAddress(rs.getString("address"));
                address.setCity(rs.getString("city"));
                student.setAddresses(address);

                LibraryAbonament libraryAbonament = new LibraryAbonament();
                libraryAbonament.setId(Long.parseLong(rs.getString("laid")));
                libraryAbonament.setStatus(rs.getString("status"));
                libraryAbonament.setStartDate(LocalDate.parse(String.valueOf(rs.getDate("start_date"))));
                libraryAbonament.setEndDate(LocalDate.parse(String.valueOf(rs.getDate("end_date"))));
                student.setLibraryAbonament(libraryAbonament);

                student.setPhones(new PhoneDao(connection).getPhonesById(student.getId()));

                Group group = new Group();
                group.setId(Long.parseLong(rs.getString("gid")));
                group.setName(rs.getString("gname"));
                student.setGroup(group);

                student.setDisciplines(new DisciplineDao(connection).getDisciplineById(student.getId()));
        }} catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

}