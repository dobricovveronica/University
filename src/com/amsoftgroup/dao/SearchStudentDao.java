package com.amsoftgroup.dao;

import com.amsoftgroup.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class SearchStudentDao {
    private Connection connection;

    public SearchStudentDao(Connection connection) {
        this.connection = connection;
    }

    public Set<Student> searchStudents(SearchStudent searchStudent) {
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
                "where (P.first_name = ? or P.last_name = ?) " +
                "and (A.city = ? or A.country = ? or A.address = ?) " +
//                "and (P.date_of_birth = ? or P.date_of_birth between ? and ?) " +
                "and G.id = ? " +
                "and P.gender = ? " +
                "and (D.id = ? or D.title = ?)";
        Set<Student> students = new HashSet<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, searchStudent.getName());
            statement.setString(2, searchStudent.getName());
            statement.setString(3, searchStudent.getStudentAddress());
            statement.setString(4, searchStudent.getStudentAddress());
            statement.setString(5, searchStudent.getStudentAddress());
//            statement.setDate(3, Date.valueOf(start_date));
//            statement.setDate(4, Date.valueOf(start_date));
//            statement.setDate(5,  Date.valueOf(end_date));
            if (searchStudent.getGroupId() != null){
            statement.setString(6, searchStudent.getGroupId());}
            statement.setString(7, searchStudent.getGender());
            if (searchStudent.getDisciplineId() != null){
            statement.setString(8, searchStudent.getDisciplineId());}
            statement.setString(9, searchStudent.getDisciplineTitle());
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
