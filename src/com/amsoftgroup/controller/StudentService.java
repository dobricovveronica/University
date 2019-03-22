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
import java.util.HashSet;
import java.util.Set;

public class StudentService {
    private static StudentService studentService;
    private DisciplineDao disciplineDao;
    private GroupDao groupDao;
    private PersonDao personDao;
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
        this.personDao = new PersonDao(connection);
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

    public  Set<Discipline> getDisciplineById(Long id){ return disciplineDao.getDisciplineById(id); }

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

    public Student getStudentById(Long id) {
        return studentDao.getStudentById(id);
    }

    public void addNewStudent(Person person, Group group) {
        Student student = new Student();
        person.getAddresses().setId(addressDao.insertAddress(person.getAddresses()));
        LibraryAbonament libraryAbonament = new LibraryAbonament();
        person.getLibraryAbonament().setId(libraryAbonamentDao.insertLibraryAbonament(person.getLibraryAbonament()));
        person.setId(personDao.insertPerson(person));
//        Set<Phone> phones = new HashSet<>();
//        for (Phone phone : person.getPhones()) {
////            phone.setId(phoneDao.insertPhone(phone, phone_type));
////            person.setId(personDao.insertPerson(person, address, libraryAbonament));
////            phoneDao.addPhoneToPerson(phone.getId(), student.getId());
//            phone.setId(phoneDao.insertPhone(phone));
//            phoneDao.addPhoneToPerson(phone, person);
//        }
        student.setId(person.getId());
        student.getGroup().setId(group.getId());
        studentDao.insertStudent(student);
    }

    public void updateStudent(Person person, Group group) {
        Student student = new Student();
        addressDao.updateAddress(person.getAddresses());
        personDao.updatePerson(person);

//        Set<Phone> phones = new HashSet<>();
//        for (Phone phone : person.getPhones()) {
////            phone.setId(phoneDao.insertPhone(phone, phone_type));
////            person.setId(personDao.insertPerson(person, address, libraryAbonament));
////            phoneDao.addPhoneToPerson(phone.getId(), student.getId());
//            phone.setId(phoneDao.insertPhone(phone));
//            phoneDao.addPhoneToPerson(phone, person);
//        }
        student.setId(person.getId());
        student.getGroup().setId(group.getId());
        studentDao.updateStudent(student);
    }

    public void  addMarkByStudentId(Mark mark){
        markDao.insertMark(mark);}

    public void updateLibraryAbonamentByStudentId(LibraryAbonament libraryAbonament){
        libraryAbonamentDao.updateLibraryAbonament(libraryAbonament);
    }


    public void deleteStudent(String[] list) {
        Long id;
        for (int i = 0; i < list.length; i++) {
            id = Long.parseLong(list[i]);
            studentDao.deleteStudent(id);
        }

    }
//    public void deleteStudent(Long id) {
//        studentDao.delete(id);
//        }

}
