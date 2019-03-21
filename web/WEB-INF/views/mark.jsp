<%@ page import="com.amsoftgroup.model.Discipline" %>
<%@ page import="java.util.Set" %>
<%@ page import="com.amsoftgroup.model.Teacher" %>
<%@ page import="com.amsoftgroup.model.Student" %><%--
  Created by IntelliJ IDEA.
  User: vdobricov
  Date: 3/14/2019
  Time: 2:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Mark</title>
    <style>
        <%@include file="/WEB-INF/views/css/bootstrap.min.css" %>
    </style>
</head>
<body>
<%Student student = (Student) request.getAttribute("student"); %>
<h2>Add Mark to student <br> <%=student.getFirstName()%> <%=student.getLastName()%> (Group: <%=student.getGroup().getName()%>)</h2>
<form method="post">
    <div class="form-group row">
        <div class="col-1">
            <label class="col-sm-2 col-form-label">Discipline:</label>
        </div>
        <div class="col-3">
            <select name="discipline" class="form-control">
                <option selected>All</option>
                <% Set<Discipline> disciplines = (Set<Discipline>) request.getAttribute("disciplines");
                    for (Discipline discipline : disciplines) {
                %>
                <option name="discipline" value="<%=discipline.getId()%>"><%=discipline.getTitle()%>
                </option>
                <%}%>
            </select>

        </div>
    </div>
    <div class="form-group row">
        <div class="col-1">
            <label class="col-sm-2 col-form-label">Profesor:</label>
        </div>
        <div class="col-3">
            <select name="teacher" class="form-control">
                <option selected>All</option>
                <% Set<Teacher> teachers = (Set<Teacher>) request.getAttribute("teachers");
                    for (Teacher teacher : teachers) {
                %>
                <option value="<%=teacher.getId()%>"><%=teacher.getFirstName()%>
                </option>
                <%}%>
            </select>

        </div>
    </div>
    <div class="form-group row">
        <div class="col-1">
            <label class="col-sm-2 col-form-label">Mark:</label>
        </div>
        <div class="col-3">
            <input type="text" class="form-control" placeholder="Mark" name="mark">
        </div>
    </div>
    <div class="form-row">
        <div class="col-2"></div>
        <div class="col-2">
            <button type="submit" class="btn btn-secondary btm-sm" >Save</button>
            <button type="submit" class="btn btn-secondary btm-sm">Cancel</button>
        </div>
    </div>
</form>
</body>
</html>
