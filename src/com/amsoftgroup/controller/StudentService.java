package com.amsoftgroup.controller;

import com.amsoftgroup.dao.*;
import com.amsoftgroup.model.*;
import com.amsoftgroup.utilitys.DataBaseConnection;

import java.sql.Connection;
import java.util.ArrayList;
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

    public StudentService() {
        Connection connection = DataBaseConnection.initializeDatabaseConnection();
        this.disciplineDao = new DisciplineDao(connection);
        this.groupDao = new GroupDao(connection);
        this.studentDao = new StudentDao(connection);
        this.phoneTypeDao = new PhoneTypeDao(connection);
        this.teacherDao = new TeacherDao(connection);
        this.libraryAbonamentDao = new LibraryAbonamentDao(connection);
        this.markDao = new MarkDao(connection);

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

    //    public void deleteStudent(){return studentDao.delete();}
    public Student getStudentById(Long id) {
        return studentDao.getStudentById(id);
    }

//    public LibraryAbonament getAbonamentById(Long id){return libraryAbonamentDao.getAbonamentById(id);}

//    public Mark getMarkByStudentId(Long id){return markDao.getMarkByStudentId(id);}
//
//    public void  addMarkByStudentId(Mark mark, Long id){markDao.insert(mark, id);}
//
//    public void  addNewStudent(Student student){ studentDao.insert(student);}

    //    public void deleteStudent(ArrayList list){
//        Long id;
//       for (int i=0; i<list.size(); i++){
//           id = Long.parseLong(String.valueOf(list.get(i)));
//           studentDao.delete(id);
//       }
//
//        }
    public void deleteStudent(Long id) {
        studentDao.delete(id);
        }
}
