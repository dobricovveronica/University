package com.amsoftgroup.controller;

import com.amsoftgroup.model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static java.util.Objects.isNull;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {
    private StudentService studentService;

    public StudentServlet() {
        this.studentService = StudentService.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        RequestDispatcher requestDispatcher;

        Person person = new Person();
        Student student = new Student();
        Address address = new Address();
        LibraryAbonament libraryAbonament = new LibraryAbonament();
        Mark mark = new Mark();
        Long studentId;


        switch (action){
//            case "ADD_MARK":
//                studentId = Long.parseLong(request.getParameter("studentId"));
//                student = studentService.getStudentById(studentId);
//
//                requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/views/mark.jsp");
//                requestDispatcher.forward(request, response);
//                break;
            case "ADD_STUDENT":

                address.setCountry(request.getParameter("country"));
                address.setCity(request.getParameter("city"));
                address.setAddress(request.getParameter("address"));

                person.setFirstName(request.getParameter("first_name"));
                person.setLastName(request.getParameter("last_name"));
                person.setDateOfBirth(LocalDate.parse(String.valueOf(request.getParameter("date_of_birth"))));
                person.setGender(request.getParameter("gender").charAt(0));

               student.getGroup().setId(Long.parseLong(request.getParameter("group_id")));

                Long type_id = Long.parseLong(request.getParameter("phone_type"));
                String phone = request.getParameter("phone");

//                studentService.addNewStudent();
                requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/views/addStudent.jsp");
                requestDispatcher.forward(request, response);
                break;

            case "DELETE":
               String[] studentsId = new  String[request.getParameterValues("check[]").length];
                studentsId = request.getParameterValues("check[]");
                studentService.deleteStudent(studentsId);

//                action = "LIST";
//                request.
//                request.setAttribute("action",action);
//                doGet(request,response);

//                requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/views/list.jsp");
//                requestDispatcher.forward(request, response);
                break;

        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");

        if (isNull(action)) {
            action = "LIST";
        }
        RequestDispatcher requestDispatcher;
        Set<Student> students = new HashSet<>();
        Set<Group> groups = new HashSet<>();
        Set<PhoneType> phoneTypes = new HashSet<>();
        Set<Discipline> disciplines = new HashSet<>();
        Set<LibraryAbonament> libraryAbonaments = new HashSet<>();
        Student student = new Student();
        LibraryAbonament libraryAbonament;
        Long studentId;
        switch (action) {
            case "LIST":
                disciplines = studentService.getAllDisciplines();
                req.setAttribute("disciplines", disciplines);
                groups = studentService.getAllGroups();
                req.setAttribute("groups", groups);
                students = studentService.getAllStudents();
                req.setAttribute("students", students);
                phoneTypes = studentService.getAllPhoneTypes();
                req.setAttribute("phoneTypes", phoneTypes);
                disciplines = studentService.getAllDisciplines();
                req.setAttribute("disciplines", disciplines);
                libraryAbonaments = studentService.getAllAbonaments();
                req.setAttribute("libraryAbonaments", libraryAbonaments);
                requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/views/list.jsp");
                requestDispatcher.forward(req, resp);
                break;
            case "EDIT":
                studentId = Long.parseLong(req.getParameter("studentId"));
                student = studentService.getStudentById(studentId);
                req.setAttribute("student", student);
                groups = studentService.getAllGroups();
                req.setAttribute("groups", groups);
                phoneTypes = studentService.getAllPhoneTypes();
                req.setAttribute("phoneTypes", phoneTypes);
                requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/views/student.jsp");
                requestDispatcher.forward(req, resp);
                break;
            case "MARK":
                studentId = Long.parseLong(req.getParameter("studentId"));
                student = studentService.getStudentById(studentId);
                req.setAttribute("student", student);
                disciplines = studentService.getAllDisciplines();
                req.setAttribute("disciplines", disciplines);
                Set<Teacher> teachers = studentService.getAllTeacher();
                req.setAttribute("teachers", teachers);
                requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/views/mark.jsp");
                requestDispatcher.forward(req, resp);
                break;
            case "LIBRARY_ABONAMENT":
                studentId = Long.parseLong(req.getParameter("studentId"));
                student = studentService.getStudentById(studentId);
                req.setAttribute("student", student);
                libraryAbonaments = studentService.getAllAbonaments();
                req.setAttribute("libraryAbonaments", libraryAbonaments);
                requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/views/abonament.jsp");
                requestDispatcher.forward(req, resp);
                break;
            case "ADD_STUDENT":

                groups = studentService.getAllGroups();
                req.setAttribute("groups", groups);
                phoneTypes = studentService.getAllPhoneTypes();
                req.setAttribute("phoneTypes", phoneTypes);
                requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/views/addStudent.jsp");
                requestDispatcher.forward(req, resp);
                break;

        }
    }
}
