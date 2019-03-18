<%@ page import="com.amsoftgroup.model.LibraryAbonament" %>
<%@ page import="java.util.Set" %>
<%@ page import="com.amsoftgroup.model.Student" %><%--
  Created by IntelliJ IDEA.
  User: vdobricov
  Date: 3/14/2019
  Time: 3:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Library Abonament</title>
    <style>
        <%@include file="/WEB-INF/views/css/bootstrap.min.css" %>
    </style>
</head>
<body>
<%Student student = (Student) request.getAttribute("student"); %>
<h1> <%=student.getFirstName()%> <%=student.getLastName()%> - student abonament</h1>
<form>
    <%java.text.DateFormat df = new java.text.SimpleDateFormat("MM/dd/yyyy"); %>
    <div class="form-group row">
        <div class="col-1">
            <label class="col-sm-2 col-form-label">Status:</label>
        </div>
        <div class="col-3">
            <select class="form-control">
                <option selected><%=student.getLibraryAbonament().getStatus()%></option>
                <% Set<LibraryAbonament> libraryAbonaments = (Set<LibraryAbonament>) request.getAttribute("libraryAbonaments");
                    for (LibraryAbonament libraryAbonament : libraryAbonaments) {
                %>
                <option><%=libraryAbonament.getStatus()%>
                </option>
                <%}%>
            </select>

        </div>
    </div>
    <div class="form-group row">
        <div class="col-1">
            <label class="col-sm-2 col-form-label">StartDate:</label>
        </div>
        <div class="col-3">
            <input type="date" class="form-control" placeholder="" value="<%=student.getLibraryAbonament().getStartDate()%>">
        </div>
    </div>
    <div class="form-group row">
        <div class="col-1">
            <label class="col-sm-2 col-form-label">EndDate:</label>
        </div>
        <div class="col-3">
            <input type="date" class="form-control" placeholder="End date" value="<%=student.getLibraryAbonament().getEndDate()%>">
        </div>
    </div>
    <div class="form-row">
        <div class="col-2"></div>
        <div class="col-2">
            <button type="button" class="btn btn-secondary btm-sm">Save</button>

            <button type="button" class="btn btn-secondary btm-sm">Cancel</button>
        </div>
    </div>
</form>
</body>
</html>
