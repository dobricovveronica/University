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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="http://malsup.github.com/jquery.form.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.js"></script>
    <script>

        function inactive() {
            var el = document.getElementById("statusId").value;
            var sD = document.getElementById("startDate");
            var eD = document.getElementById("endDate");
            var sV = sD.value;
            if (el === "None") {
                sD.disabled = true;
                eD.disabled = true;
                sD.value = '';
                eD.value = '';
            } else {
                sD.disabled = false;
                eD.disabled = false;
                sD.value = sV;
            }
        }
    </script>
</head>
<body>
<%Student student = (Student) request.getAttribute("student"); %>
<h1><%=student.getFirstName()%> <%=student.getLastName()%> - student abonament</h1>

<form method="post" action="student" id="updateLibraryAbonament">
    <%java.text.DateFormat df = new java.text.SimpleDateFormat("MM/dd/yyyy"); %>
    <input type="hidden" name="action" value="LIBRARY_ABONAMENT">
    <input type="hidden" name="abonamentId" value="<%=student.getLibraryAbonament().getId()%>">
    <div class="form-group row">
        <div class="col-2">
            <label class="col-sm-2 col-form-label">Status:</label>
        </div>
        <div class="col-3">
            <select name="status" id="statusId" class="form-control" onchange="inactive()" required>
                <% Set<LibraryAbonament> libraryAbonaments = (Set<LibraryAbonament>) request.getAttribute("libraryAbonaments");
                    for (LibraryAbonament libraryAbonament : libraryAbonaments) {
                %>
                <option <%=((student.getLibraryAbonament().getStatus().equals(libraryAbonament.getStatus())) ? "selected" : "") %>
                        value="<%=libraryAbonament.getStatus()%>"><%=libraryAbonament.getStatus()%>
                </option>
                <%}%>
            </select>
        </div>
    </div>
    <div class="form-group row">
        <div class="col-2">
            <label class="col-sm-2 col-form-label">StartDate:</label>
        </div>
        <div class="col-3">
            <input type="date" class="form-control" name="start_date" id="startDate"
                   value="<%=((student.getLibraryAbonament().getStartDate() != null)?student.getLibraryAbonament().getStartDate():"Select date")%>">
        </div>
    </div>
    <div class="form-group row">
        <div class="col-2">
            <label class="col-sm-2 col-form-label">EndDate:</label>
        </div>
        <div class="col-3">
            <input type="date" class="form-control" name="end_date" id="endDate"
                   value="<%=((student.getLibraryAbonament().getEndDate() != null)?student.getLibraryAbonament().getEndDate():"Select date")%>">
        </div>
    </div>
    <div class="form-row">
        <div class="col-2"></div>
        <div class="col-2">
            <button type="submit" class="btn btn-secondary btm-sm">Save</button>

            <button type="button" onclick="window.close()" class="btn btn-secondary btm-sm">Cancel</button>
        </div>
    </div>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.js"></script>
    <script src="http://malsup.github.com/jquery.form.js"></script>
    <script>
        $(document).ready(function () {
            $('#updateLibraryAbonament').ajaxForm(function () {
                // alert("Thank you for your comment!");
                window.opener.location.reload();
                window.self.close();
            });
        });
    </script>
</form>
</body>
</html>
