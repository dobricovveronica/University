package com.amsoftgroup.dao;

import com.amsoftgroup.model.*;
import com.amsoftgroup.dao.*;


import java.sql.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class StudentDao {

    private Connection connection;

    public StudentDao(Connection connection) {
        this.connection = connection;
    }

    public Set<Student> getStudent() {
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

    public void deleteStudent(Long id) {
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

    public void updateStudent(Student student) {
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

    public void insertStudent(Student student) {
        String sql = "insert into university.students(group_id, id) values(?, ?) ";

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

    public Set<Student> searchStudents(String name, String studentAddress, Date start_date, Date end_date, Long discipline_id, String discipline_title, Long group_id, String gender) {
        String sql = "select P.id, " +
                "P.first_name, " +
                "P.last_name, " +
                "P.date_of_birth, " +
                "P.gender, " +
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
                "left join university.disciplines as D on D.id = DtoS.discipline_id " +
                "where (P.first_name = ? or P.first_name = ?) " +
                "and (A.city = ? or A.country = ? or A.address = ?) " +
                "and (P.date_of_birth = ? or P.date_of_birth between ? and ?) " +
                "and G.id = ? " +
                "and P.gender = ? " +
                "and (D.id = ? or D.title = ?)";
        Set<Student> students = new HashSet<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, studentAddress);
            statement.setDate(3, (java.sql.Date) start_date);
            statement.setDate(4, (java.sql.Date) start_date);
            statement.setDate(5, (java.sql.Date) end_date);
            statement.setLong(6, group_id);
            statement.setString(7, gender);
            statement.setLong(8, discipline_id);
            statement.setString(9, discipline_title);
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

}
