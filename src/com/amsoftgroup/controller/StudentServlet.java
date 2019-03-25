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
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.isNull;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {
    private StudentService studentService;

    public StudentServlet() {
        this.studentService = StudentService.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");

        Person person = new Person();
        Student student = new Student();
        Address address = new Address();
        LibraryAbonament libraryAbonament = new LibraryAbonament();
        Mark mark = new Mark();
        Group group = new Group();
        Discipline discipline = new Discipline();
        Teacher teacher = new Teacher();


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
                break;
            case "LIBRARY_ABONAMENT":
                libraryAbonament.setId(Long.parseLong(request.getParameter("abonamentId")));
                libraryAbonament.setStatus(request.getParameter("status"));
                libraryAbonament.setStartDate(LocalDate.parse(String.valueOf(request.getParameter("start_date"))));
                libraryAbonament.setEndDate(LocalDate.parse(String.valueOf(request.getParameter("end_date"))));
                studentService.updateLibraryAbonamentByStudentId(libraryAbonament);
                break;
            case "ADD_STUDENT, EDIT":

                address.setCountry(request.getParameter("country"));
                address.setCity(request.getParameter("city"));
                address.setAddress(request.getParameter("address"));

                person.setFirstName(request.getParameter("first_name"));
                person.setLastName(request.getParameter("last_name"));
                person.setDateOfBirth(LocalDate.parse(String.valueOf(request.getParameter("date_of_birth"))));
                person.setGender(request.getParameter("gender").charAt(0));
                person.setMail(request.getParameter("mail"));
                group.setId(Long.parseLong(request.getParameter("group")));
//
                Set<Phone> phones = new HashSet<>();
                Phone phone = new Phone();
                PhoneType phoneType = new PhoneType();
                phoneType.setId(Long.parseLong(request.getParameter("phone_type")));
                phone.setPhoneType(phoneType);
                phone.setValue(request.getParameter("phone"));
                phones.add(phone);

                person.setPhones(phones);
//                person.setAddresses(address);
//                studentService.addNewStudent(person, group);


                if (person.getId() != 0) {
                    address.setId(Long.parseLong(request.getParameter("addressId")));
                    person.setId(Long.parseLong(request.getParameter("studentId")));
                    person.setAddresses(address);
                    studentService.updateStudent(student);
                } else {

                    person.setAddresses(address);
                    studentService.addNewStudent(student);
                }
//                requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/views/addStudent.jsp");
//                requestDispatcher requestDispatcher.forward(request, response);
                break;
            case "DELETE":
                String[] studentsId;
                studentsId = request.getParameterValues("check[]");
                studentService.deleteStudent(studentsId);
                break;
            case "SEARCH":

                String name = request.getParameter("name");
                String studentAddress = request.getParameter("studentAddress");
//                LocalDate startDate = LocalDate.parse(String.valueOf(request.getParameter("startDate")));
//                LocalDate endDate = LocalDate.parse(String.valueOf(request.getParameter("endDate")));
                Long disciplineId = Long.parseLong(request.getParameter("discipline"));
                String disciplineTitle = request.getParameter("disciplineTitle");
                Long groupId = Long.parseLong(request.getParameter("group"));
                String gender = request.getParameter("gender");

                studentService.searchStudents(name, studentAddress, disciplineId, disciplineTitle, groupId, gender);

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
        Set<Student> students;
        Set<Group> groups;
        Set<PhoneType> phoneTypes;
        Set<Discipline> disciplines;
        Set<LibraryAbonament> libraryAbonaments;
        Student student;
        Long studentId;
        switch (action) {
            case "LIST":

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
                if (!isNull(studentId)){
                student = studentService.getStudentById(studentId);
                req.setAttribute("student", student);}
                req.setAttribute("studentId", studentId);
                groups = studentService.getAllGroups();
                req.setAttribute("groups", groups);
                disciplines = studentService.getDisciplineById(studentId);
                req.setAttribute("disciplines", disciplines);
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

        }
    }
}
