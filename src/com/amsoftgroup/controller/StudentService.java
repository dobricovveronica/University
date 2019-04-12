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
    private AverageDao averageDao;
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
        this.averageDao = new AverageDao(connection);
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


        for (Phone phone : student.getPhones()) {
            phone.setId(phoneDao.insertPhone(phone));
            phoneDao.addPhoneToPerson(phone, student);

        }
        studentDao.insertStudent(student);
        for (Discipline discipline : student.getDisciplines()) {
            Average average = new Average();
            average.setDiscipline(discipline);
            average.setStudent(student);
            averageDao.insertAverage(average);
            disciplineDao.addDisciplineToStudent(student, discipline);
        }

    }

    public void updateStudent(Student student) {

        addressDao.updateAddress(student.getAddresses());
        personDao.updatePerson(student);
        Set<Phone> oldPhones = phoneDao.getPhonesById(student.getId());
        Set<Phone> phones = new HashSet<>();
        List<Long> oldPhonesId = new ArrayList<>();
        List<Long> newPhonesId = new ArrayList<>();

        if (student.getPhones() != null) {
            for (Phone phone : student.getPhones()) {
                if (oldPhones.size() != 0) {
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
                } else {
                    if (phone.getId() == 0) {
                        phone.setId(phoneDao.insertPhone(phone));
                        phoneDao.addPhoneToPerson(phone, student);
                    }
                }
                newPhonesId.add(phone.getId());
            }
            List<Long> diferencePhone = new ArrayList<>(oldPhonesId);
            diferencePhone.removeAll(newPhonesId);
            for (Long ph : diferencePhone) {
                Phone phon = new Phone();
                phon.setId(ph);
                phoneDao.deletePhone(phon);
            }
        }else {
            for (Phone phone : oldPhones){
                phoneDao.deletePhone(phone);
            }
        }

        Set<Discipline> oldDisciplines = disciplineDao.getDisciplineById(student.getId());
        List<Long> oldDisciplineId = new ArrayList<>();
        List<Long> newDisciplineId = new ArrayList<>();

        if (student.getDisciplines() != null) {
            if (oldDisciplines.size() != 0){
                for (Discipline oldDiscipline : oldDisciplines){
                    oldDisciplineId.add(oldDiscipline.getId());
                    oldDiscipline.setAverage(averageDao.getAverageByDiscipline(oldDiscipline.getId(), student.getId()));
                }
            }
            for (Discipline discipline : student.getDisciplines()){
                newDisciplineId.add(discipline.getId());
            }
            List<Long> diferenceDiscipline = new ArrayList<>(oldDisciplineId);
            diferenceDiscipline.removeAll(newDisciplineId);
            for (Long disc : diferenceDiscipline){
                Discipline dis = new Discipline();
                dis.setId(disc);
                disciplineDao.deleteDisciplineToStudent(student, dis);
            }
            List<Long> diference = new ArrayList<>(newDisciplineId);
            diference.removeAll(oldDisciplineId);
            for (Long d : diference){
                Discipline dd = new Discipline();
                dd.setId(d);
                Average average = new Average();
                average.setDiscipline(dd);
                average.setStudent(student);
                averageDao.insertAverage(average);
                disciplineDao.addDisciplineToStudent(student, dd);
            }
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
            Student student = new Student();
            student = studentDao.getStudentById(id);
            studentDao.deleteStudent(student.getId());
            personDao.deletePerson(student.getId());
            libraryAbonamentDao.deleteLibraryAbonament(student.getLibraryAbonament().getId());
            addressDao.deleteAddress(student.getAddresses().getId());
        }
    }
}
