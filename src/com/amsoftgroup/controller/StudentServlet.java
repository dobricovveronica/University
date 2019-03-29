package com.amsoftgroup.controller;

import com.amsoftgroup.model.*;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashSet;
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

        Student student = new Student();
        LibraryAbonament libraryAbonament = new LibraryAbonament();
        Mark mark = new Mark();
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
            case "EDIT":
                student = buildStudent(request);
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
                StudentFilter studentFilter = new StudentFilter();
                if (!request.getParameter("name").equals("")) {
                    studentFilter.setName(request.getParameter("name"));
                }
                if (!request.getParameter("studentAddress").equals("")) {
                    studentFilter.setStudentAddress(request.getParameter("studentAddress"));
                }
//                LocalDate startDate = LocalDate.parse(String.valueOf(request.getParameter("startDate")));
//                LocalDate endDate = LocalDate.parse(String.valueOf(request.getParameter("endDate")));
                if (!request.getParameter("discipline").equals("")) {
                    studentFilter.setDisciplineId(request.getParameter("discipline"));
                }
                if (!request.getParameter("disciplineTitle").equals("")) {
                    studentFilter.setDisciplineTitle(request.getParameter("disciplineTitle"));
                }
                if (!request.getParameter("group").equals("")) {
                    studentFilter.setGroupId(request.getParameter("group"));
                }
                if (!request.getParameter("gender").equals("")) {
                    studentFilter.setGender(request.getParameter("gender"));
                }

                Set<Student> students = studentService.searchStudents(studentFilter);
                request.setAttribute("students", students);
                Set<Group>groups = studentService.getAllGroups();
                request.setAttribute("groups", groups);
                Set<Discipline> disciplines = studentService.getAllDisciplines();
                request.setAttribute("disciplines", disciplines);
                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/WEB-INF/views/list.jsp");
                requestDispatcher.forward(request, response);
                break;
            case "RESET":
                response.sendRedirect("/student?action=LIST");
                break;
        }
    }

    public Student buildStudent(HttpServletRequest request) throws IOException, ServletException {
        Student student = new Student();
        Address address = new Address();
        LibraryAbonament libraryAbonament = new LibraryAbonament();
        Group group = new Group();

        student.setId(Long.parseLong(request.getParameter("studentId")));
        address.setCountry(request.getParameter("country"));
        address.setCity(request.getParameter("city"));
        address.setAddress(request.getParameter("address"));

        student.setFirstName(request.getParameter("first_name"));
        student.setLastName(request.getParameter("last_name"));

        student.setDateOfBirth(LocalDate.parse(String.valueOf(request.getParameter("date_of_birth"))));
        student.setGender(request.getParameter("gender").charAt(0));
        student.setMail(request.getParameter("mail"));

        InputStream inputStream = null;
        Part filePart = request.getPart("image");
        if (filePart != null) {
            inputStream = filePart.getInputStream();
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
            student.setPicture(byteArrayOutputStream.toByteArray());
        }

        group.setId(Long.parseLong(request.getParameter("group")));
        student.setGroup(group);

        libraryAbonament.setStatus("None");
        student.setLibraryAbonament(libraryAbonament);
//
        Set<Phone> phones = new HashSet<>();
        Phone phone = new Phone();
        PhoneType phoneType = new PhoneType();
        phoneType.setId(Long.parseLong(request.getParameter("phone_type")));
        phone.setPhoneType(phoneType);
        phone.setValue(request.getParameter("phone"));
        phones.add(phone);

        student.setPhones(phones);

        if (student.getId() != 0) {
            address.setId(Long.parseLong(request.getParameter("addressId")));
            student.setId(Long.parseLong(request.getParameter("studentId")));
            student.setAddresses(address);

        } else {
            student.setAddresses(address);

        }
        return student;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");

        if (isNull(action)) {
            action = "LIST";
        }
        RequestDispatcher requestDispatcher;
        Set<StudentFilter> studentFilters;
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
//                disciplines = studentService.getDisciplineById(studentId);
//                req.setAttribute("disciplines", disciplines);
//                Set<Teacher> teachers = studentService.getAllTeacher();
//                req.setAttribute("teachers", teachers);
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
