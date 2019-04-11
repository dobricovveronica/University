<%@ page import="com.amsoftgroup.model.Discipline" %>
<%@ page import="java.util.Set" %>
<%@ page import="com.amsoftgroup.model.Student" %>
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
<h2>Add Mark to student <br> <%=student.getFirstName()%> <%=student.getLastName()%>
    (Group: <%=student.getGroup().getName()%>)</h2>
<form method="post" action="student" id="markForm">
    <div class="form-group row">
        <input type="hidden" name="action" value="MARK">
        <input type="hidden" name="studentId" value="<%=student.getId()%>">
        <div class="col-2">
            <label class="col-sm-2 col-form-label">Discipline:</label>
        </div>
        <div class="col-3">
            <select name="discipline" id="discipline" class="form-control" onchange="teacherSelect(this)" required>
                <option selected>Select discipline</option>
                <% Set<Discipline> disciplines = (Set<Discipline>) request.getAttribute("disciplines");
                    for (Discipline discipline : disciplines) {
                %>
                <option value="<%=discipline.getId()%>"><%=discipline.getTitle()%>
                </option>
                <%}%>
            </select>
        </div>
    </div>
    <div class="form-group row">
        <div class="col-2">
            <label class="col-sm-2 col-form-label">Profesor:</label>
        </div>
        <div class="col-3">
            <select name="teacher" id="teacher" class="form-control">
                <script>
                    function teacherSelect(discipline) {
                        var dis = discipline.value;
                        var teach = document.getElementById("teacher");
                        <% for (Discipline discipline : student.getDisciplines()){%>
                        if (dis == <%=discipline.getId()%>) {
                            teach.options[0] = new Option("<%=discipline.getTeacher().getFirstName()%> " + " " + "<%=discipline.getTeacher().getLastName()%>", <%=discipline.getTeacher().getId()%>);
                        }
                        <%}%>
                    }
                </script>
            </select>
        </div>
    </div>
    <div class="form-group row">
        <div class="col-2">
            <label class="col-sm-2 col-form-label">Mark:</label>
        </div>
        <div class="col-3">
            <input type="text" class="form-control" placeholder="Mark" name="mark" required>
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
            $('#markForm').ajaxForm(function () {
                // alert("Thank you for your comment!");
                window.opener.location.reload();
                window.self.close();
            });
        });
    </script>
</form>
</body>
</html>
