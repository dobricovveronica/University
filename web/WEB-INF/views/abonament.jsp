<%@ page import="com.amsoftgroup.model.LibraryAbonament" %>
<%@ page import="java.util.Set" %>
<%@ page import="com.amsoftgroup.model.Student" %>
<%@ page import="static java.util.Objects.isNull" %><%--
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
    <script>
        function exit() {
           form.submit();
           window.close();
        }
        function inactive() {

            if (document.getElementsByName("status").values() == "None"){
                document.getElementsByName("start_date").disabled = true;
                document.getElementsByName("end_date").disabled = true;
            }
        }
    </script>
</head>
<body>
<%Student student = (Student) request.getAttribute("student"); %>
<h1> <%=student.getFirstName()%> <%=student.getLastName()%> - student abonament</h1>

<form method="post" onsubmit="return exit()">
    <%java.text.DateFormat df = new java.text.SimpleDateFormat("MM/dd/yyyy"); %>
    <input type="hidden" name="abonamentId" value="<%=student.getLibraryAbonament().getId()%>">
    <div class="form-group row">
        <div class="col-1">
            <label class="col-sm-2 col-form-label">Status:</label>
        </div>
        <div class="col-3">
            <select name="status" class="form-control" onchange="inactive()">
                <option selected><%=student.getLibraryAbonament().getStatus()%></option>
                <% Set<LibraryAbonament> libraryAbonaments = (Set<LibraryAbonament>) request.getAttribute("libraryAbonaments");
                    for (LibraryAbonament libraryAbonament : libraryAbonaments) {
                %>
                <option value="<%=libraryAbonament.getStatus()%>"><%=libraryAbonament.getStatus()%>
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
            <input type="date" class="form-control" name="start_date" value="<%=((student.getLibraryAbonament().getStartDate() != null)?student.getLibraryAbonament().getStartDate():"Select date")%>">
        </div>
    </div>
    <div class="form-group row">
        <div class="col-1">
            <label class="col-sm-2 col-form-label">EndDate:</label>
        </div>
        <div class="col-3">
            <input type="date" class="form-control" name="end_date" value="<%=((student.getLibraryAbonament().getEndDate() != null)?student.getLibraryAbonament().getEndDate():"Select date")%>">
        </div>
    </div>
    <div class="form-row">
        <div class="col-2"></div>
        <div class="col-2">
            <button type="submit"  class="btn btn-secondary btm-sm">Save</button>

            <button type="button" onclick="window.close()" class="btn btn-secondary btm-sm">Cancel</button>
        </div>
    </div>
</form>
</body>
</html>
