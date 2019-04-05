package com.amsoftgroup.controller;

import com.amsoftgroup.dao.*;
import com.amsoftgroup.model.*;
import com.amsoftgroup.utilitys.DataBaseConnection;

import java.sql.Connection;
import java.util.*;

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
    private StudentFilterDao studentFilterDao;


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
        this.studentFilterDao = new StudentFilterDao(connection);

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

    public Set<Discipline> getDisciplineById(Long id) {
        return disciplineDao.getDisciplineById(id);
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

    public Student getStudentById(Long id) {
        return studentDao.getStudentById(id);
    }

    public void addNewStudent(Student student) {

        Address address = new Address();
        address.setId(addressDao.insertAddress(student.getAddresses()));
        student.setAddresses(address);
        LibraryAbonament libraryAbonament = new LibraryAbonament();
        libraryAbonament.setId(libraryAbonamentDao.insertLibraryAbonament(student.getLibraryAbonament()));
        student.setLibraryAbonament(libraryAbonament);
        student.setId(personDao.insertPerson(student));

        Set<Phone> phones = new HashSet<>();
        for (Phone phone : student.getPhones()) {
            phone.setId(phoneDao.insertPhone(phone));
            phoneDao.addPhoneToPerson(phone, student);
            phones.add(phone);
        }

        studentDao.insertStudent(student);
        disciplineDao.addDisciplineToStudent(student);
    }

    public void updateStudent(Student student) {

        addressDao.updateAddress(student.getAddresses());
        personDao.updatePerson(student);
        Set<Phone> oldPhones = phoneDao.getPhonesById(student.getId());
        Set<Phone> phones = new HashSet<>();
        List<Long> oldPhonesId = new ArrayList<>();
        List<Long> newPhonesId = new ArrayList<>();
        for (Phone phone : student.getPhones()) {
            for (Phone oldPhone : oldPhones) {
                if (phone.getId() == 0) {
                    phone.setId(phoneDao.insertPhone(phone));
                    phoneDao.addPhoneToPerson(phone, student);
                }
                if (phone.getId() == oldPhone.getId()) {
                    phoneDao.updatePhone(phone);
                }
                phones.add(phone);
                oldPhonesId.add(oldPhone.getId());
            }
            newPhonesId.add(phone.getId());
        }
        List<Long> diff = new ArrayList<>(oldPhonesId);
        diff.removeAll(newPhonesId);
        for (Long ph : diff) {
            Phone phon = new Phone();
            phon.setId(ph);
            phoneDao.deletePhone(phon);
        }

        studentDao.updateStudent(student);
    }

    public void addMarkByStudentId(Mark mark) {
        markDao.insertMark(mark);
    }

    public void updateLibraryAbonamentByStudentId(LibraryAbonament libraryAbonament) {
        libraryAbonamentDao.updateLibraryAbonament(libraryAbonament);
    }

    public Set<Student> searchStudents(StudentFilter studentFilter) {
        return studentFilterDao.searchStudents(studentFilter);
    }

    public void deleteStudent(String[] list) {
        Long id;
        for (int i = 0; i < list.length; i++) {
            id = Long.parseLong(list[i]);
            studentDao.deleteStudent(id);
        }
    }
}
