package com.amsoftgroup.controller;

import com.amsoftgroup.dao.*;
import com.amsoftgroup.model.*;
import com.amsoftgroup.utilitys.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

public class StudentService {
    private static StudentService studentService;
    private DisciplineDao disciplineDao;
    private GroupDao groupDao;
    private StudentDao studentDao;
    private PhoneTypeDao phoneTypeDao;
    private TeacherDao teacherDao;
    private LibraryAbonamentDao libraryAbonamentDao;
    private MarkDao markDao;
    private AddressDao addressDao;
    private PhoneDao phoneDao;

    public StudentService() {
        Connection connection = DataBaseConnection.initializeDatabaseConnection();
        this.disciplineDao = new DisciplineDao(connection);
        this.groupDao = new GroupDao(connection);
        this.studentDao = new StudentDao(connection);
        this.phoneTypeDao = new PhoneTypeDao(connection);
        this.teacherDao = new TeacherDao(connection);
        this.libraryAbonamentDao = new LibraryAbonamentDao(connection);
        this.markDao = new MarkDao(connection);
        this.addressDao = new AddressDao(connection);
        this.phoneDao = new PhoneDao(connection);

    }

    public static StudentService getInstance() {
        if (studentService == null) {
            studentService = new StudentService();
        }
        return studentService;
    }

    public Set<Group> getAllGroups() {
       return groupDao.getAllGroups();
    }

    public Set<Discipline> getAllDisciplines() {
        return disciplineDao.getAllDisciplines();
    }

    public Set<Student> getAllStudents() {
        return studentDao.getAllStudents();
    }

    public Set<PhoneType> getAllPhoneTypes() {
        return phoneTypeDao.getAllPhoneTypes();
    }

    public Set<Teacher> getAllTeacher() {
        return teacherDao.getAllTeacher();
    }

    public Set<LibraryAbonament> getAllAbonaments() {
        return libraryAbonamentDao.getAllAbonaments();
    }

//    public void studentUpdate() { return; studentDao.update();}

    public Student getStudentById(Long id) {
        return studentDao.getStudentById(id);
    }

    public void addNewStudent(String country, String city, String address, Long type_id, String value, String first_name,
                              String last_name, LocalDate date_of_birth, Long group_id, String gender) {

        addressDao.insert(country, city, address);
        libraryAbonamentDao.insert();
//        phoneDao.insert(phone);
//        studentDao.insert(first_name, last_name, date_of_birth, group_id, gender);

    }

//    public Mark getMarkByStudentId(Long id){return markDao.getMarkByStudentId(id);}
//
//    public void  addMarkByStudentId(Mark mark, Long id){markDao.insert(mark, id);}
//

    public void deleteStudent(String[] list) {
        Long id;
        for (int i = 0; i < list.length; i++) {
            id = Long.parseLong(list[i]);
            studentDao.delete(id);
        }

    }
//    public void deleteStudent(Long id) {
//        studentDao.delete(id);
//        }

}
