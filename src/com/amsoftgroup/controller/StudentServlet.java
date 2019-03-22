package com.amsoftgroup.controller;

import com.amsoftgroup.model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
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
        Phone phone = new Phone();
        Mark mark = new Mark();
        Group group = new Group();
        Discipline discipline = new Discipline();
        Teacher teacher = new Teacher();
        PhoneType phoneType = new PhoneType();

        switch (action) {

            case "MARK":
                student.setId(Long.parseLong(request.getParameter("studentId")));
                teacher.setId(Long.parseLong(request.getParameter("teacher")));
                discipline.setId(Long.parseLong(request.getParameter("discipline")));
                mark.setStudent(student);
                mark.setTeacher(teacher);
                mark.setDiscipline(discipline);
                mark.setValue(Double.parseDouble(request.getParameter("mark")));
                studentService.addMarkByStudentId(mark);
//                requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/views/mark.jsp");
//                requestDispatcher.forward(request, response);
                break;
            case "LIBRARY_ABONAMENT":
//                student.setId(Long.parseLong(request.getParameter("student")));
                libraryAbonament.setId(Long.parseLong(request.getParameter("abonamentId")));
                libraryAbonament.setStatus(request.getParameter("status"));
                libraryAbonament.setStartDate(LocalDate.parse(String.valueOf(request.getParameter("start_date"))));
                libraryAbonament.setEndDate(LocalDate.parse(String.valueOf(request.getParameter("end_date"))));
                studentService.updateLibraryAbonamentByStudentId(libraryAbonament);

                break;
            case "EDIT":
                address.setCountry(request.getParameter("country"));
                address.setCity(request.getParameter("city"));
                address.setAddress(request.getParameter("address"));

                person.setFirstName(request.getParameter("first_name"));
                person.setLastName(request.getParameter("last_name"));
                person.setDateOfBirth(LocalDate.parse(String.valueOf(request.getParameter("date_of_birth"))));
                person.setGender(request.getParameter("gender").charAt(0));

//                Set <Phone> phones = new HashSet<>();
//                phoneType.setId(Long.parseLong(request.getParameter("phone_type")));
//                phone.setPhoneType(phoneType);
//                phone.setValue(request.getParameter("phone"));
//                phones.add(phone);

                group.setId(Long.parseLong(request.getParameter("group")));

//                person.setPhones(phones);


                if (person != null) {
                    address.setId(Long.parseLong(request.getParameter("addressId")));
                    person.setId(Long.parseLong(request.getParameter("studentId")));
                    person.setAddresses(address);
                    studentService.updateStudent(person, group);
                } else {
                    libraryAbonament.setStatus("None");
                    person.setAddresses(address);
                    person.setLibraryAbonament(libraryAbonament);
                    studentService.addNewStudent(person, group);
                }
//                requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/views/addStudent.jsp");
//                requestDispatcher requestDispatcher.forward(request, response);
                break;

            case "DELETE":
                String[] studentsId = new String[request.getParameterValues("check[]").length];
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
                disciplines = studentService.getDisciplineById(studentId);
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
