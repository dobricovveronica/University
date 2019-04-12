package com.amsoftgroup.controller;

import com.amsoftgroup.model.*;

import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@WebFilter("/student")
public class StudentServletFilter implements Filter {
    StudentService studentService = new StudentService();

    public StudentServletFilter() {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            String action = req.getParameter("action");
            Student student = new Student();
            Mark mark = new Mark();
            Discipline discipline = new Discipline();
            Teacher teacher = new Teacher();
            if (action != null) {
                switch (action) {
                    case "LIBRARY_ABONAMENT":
                        LibraryAbonament libraryAbonament = new LibraryAbonament();
                        libraryAbonament.setId(Long.parseLong(request.getParameter("abonamentId")));
                        libraryAbonament.setStatus(request.getParameter("status"));
                        if (request.getParameter("start_date") != null) {
                            libraryAbonament.setStartDate(LocalDate.parse(String.valueOf(request.getParameter("start_date"))));
                        }
                        if (request.getParameter("end_date") != null) {
                            libraryAbonament.setEndDate(LocalDate.parse(String.valueOf(request.getParameter("end_date"))));
                        }
                        req.setAttribute("libraryAbonament", libraryAbonament);
                        break;

                    case "MARK":
                        student.setId(Long.parseLong(request.getParameter("studentId")));
                        teacher.setId(Long.parseLong(request.getParameter("teacher")));
                        discipline.setId(Long.parseLong(request.getParameter("discipline")));
                        mark.setStudent(student);
                        mark.setTeacher(teacher);
                        mark.setDiscipline(discipline);
                        mark.setValue(Double.parseDouble(request.getParameter("mark")));
                        req.setAttribute("mark", mark);
                        break;

                    case "SEARCH":
                        StudentFilter studentFilter = new StudentFilter();
                        if (!request.getParameter("name").equals("")) {
                            studentFilter.setName(request.getParameter("name"));
                        }
                        if (!request.getParameter("studentAddress").equals("")) {
                            studentFilter.setStudentAddress(request.getParameter("studentAddress"));
                        }
                        if (!request.getParameter("startDate").equals("")) {
                            studentFilter.setStartDate(String.valueOf(request.getParameter("startDate")));
                        }
                        if (!request.getParameter("endDate").equals("")) {
                            studentFilter.setEndDate(String.valueOf(request.getParameter("endDate")));
                        }
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
                        if (!request.getParameter("average").equals("")) {
                            studentFilter.setAverage(request.getParameter("average"));
                        }
                        req.setAttribute("studentFilter", studentFilter);
                        break;

                    case "EDIT":
                        student = buildStudent(request);
                        req.setAttribute("student", student);
                        break;
                }
            }
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

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

        Part filePart = request.getPart("image");
        if (filePart != null) {
            InputStream inputStream = filePart.getInputStream();
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
            student.setPicture(byteArrayOutputStream.toByteArray());
        }

        group.setId(Long.parseLong(request.getParameter("group")));
        student.setGroup(group);

        Set<Discipline> disciplines = new HashSet<>();
        String[] disciplineId = request.getParameterValues("disciplineId[]");
        for (int i = 0; i < disciplineId.length; i++) {
            Discipline discipline = new Discipline();
            discipline.setId((Long.parseLong(disciplineId[i])));
            disciplines.add(discipline);
        }
        student.setDisciplines(disciplines);

        libraryAbonament.setStatus("None");
        student.setLibraryAbonament(libraryAbonament);

        if (request.getParameterValues("phoneType[]") != null) {
            Set<Phone> phones = new HashSet<>();
            String[] phoneTypeId = request.getParameterValues("phoneType[]");
            String[] phoneId = request.getParameterValues("phoneId[]");
            String[] phoneValue = request.getParameterValues("phoneNumber[]");
            for (int i = 0; i < phoneId.length; i++) {
                Phone phone = new Phone();
                PhoneType phoneType = new PhoneType();
                phoneType.setId(Long.parseLong(phoneTypeId[i]));
                if (!phoneId[i].equals("")) {
                    phone.setId(Long.parseLong(phoneId[i]));
                }
                phone.setValue(phoneValue[i]);
                phone.setPhoneType(phoneType);
                phones.add(phone);
            }
            student.setPhones(phones);
        }
        if (student.getId() != 0) {
            address.setId(Long.parseLong(request.getParameter("addressId")));
            student.setId(Long.parseLong(request.getParameter("studentId")));
            student.setAddresses(address);

        } else {
            student.setAddresses(address);

        }
        return student;
    }

}
