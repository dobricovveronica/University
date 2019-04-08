package com.amsoftgroup.controller;

import com.amsoftgroup.model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.time.LocalDate;
import java.util.Set;

import static java.util.Objects.isNull;

@WebServlet("/student")
@MultipartConfig
public class StudentServlet extends HttpServlet {
    private StudentService studentService;

    public StudentServlet() {
        this.studentService = StudentService.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        RequestDispatcher requestDispatcher;

        switch (action) {
            case "MARK":
                Mark mark = (Mark) request.getAttribute("mark");
                studentService.addMarkByStudentId(mark);
                break;
            case "LIBRARY_ABONAMENT":
               LibraryAbonament libraryAbonament = new LibraryAbonament();
                libraryAbonament.setId(Long.parseLong(request.getParameter("abonamentId")));
                libraryAbonament.setStatus(request.getParameter("status"));
                libraryAbonament.setStartDate(LocalDate.parse(String.valueOf(request.getParameter("start_date"))));
                libraryAbonament.setEndDate(LocalDate.parse(String.valueOf(request.getParameter("end_date"))));
                studentService.updateLibraryAbonamentByStudentId(libraryAbonament);
                break;
            case "EDIT":
                Student student = (Student) request.getAttribute("student");
                if (student.getId() != 0) {
                    studentService.updateStudent(student);
                } else {
                    studentService.addNewStudent(student);
                }
                break;
            case "DELETE":
                String[] studentsId;
                studentsId = request.getParameterValues("check[]");
                studentService.deleteStudent(studentsId);
                break;
            case "SEARCH":
                StudentFilter studentFilter = (StudentFilter) request.getAttribute("studentFilter");

                Set<Student> students = studentService.searchStudents(studentFilter);
                request.setAttribute("students", students);
                Set<Group> groups = studentService.getAllGroups();
                request.setAttribute("groups", groups);
                Set<Discipline> disciplines = studentService.getAllDisciplines();
                request.setAttribute("disciplines", disciplines);
                requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/views/list.jsp");
                requestDispatcher.forward(request, response);
                break;
            case "RESET":
                response.sendRedirect("/student/");
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
                disciplines = studentService.getAllDisciplines();
                req.setAttribute("disciplines", disciplines);

                requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/views/list.jsp");
                requestDispatcher.forward(req, resp);
                break;
            case "EDIT":
                studentId = Long.parseLong(req.getParameter("studentId"));
                if (!isNull(studentId) && studentId != 0) {
                    student = studentService.getStudentById(studentId);
                    req.setAttribute("student", student);
                }
                req.setAttribute("studentId", studentId);
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

        }
    }
}
