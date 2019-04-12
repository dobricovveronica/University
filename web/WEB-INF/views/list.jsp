<%@ page import="java.util.Set" %>
<%@ page import="java.util.Base64" %>
<%@ page import="com.amsoftgroup.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student Management</title>
    <style>
        <%@include file="/WEB-INF/views/css/bootstrap.min.css" %>
    </style>
    <style>
        <%@include file="/WEB-INF/views/css/style.css" %>
    </style>
    <script>

        function activateInput(actionName) {
            var el1 = document.getElementById("search");
            var el2 = document.getElementById("reset");
            var el3 = document.getElementById("delete");
            el1.disabled = true;
            el2.disabled = true;
            el3.disabled = true;
            if (actionName == "search") {
                el1.disabled = false;
            }
            if (actionName == "reset") {
                el2.disabled = false;
            }
            if (actionName == "delete") {
                el3.disabled = false;
            }
        }
        function confSubmit(actionName) {
            if (confirm("Are you want to delete student(s)?")) {
                activateInput(actionName);
            }
        }
        function checkAll(source) {
            var checkboxes = document.querySelectorAll('input[type="checkbox"]');
            for (var i = 0; i < checkboxes.length; i++) {
                if (checkboxes[i] != source)
                    checkboxes[i].checked = source.checked;
            }
        }

    </script>
</head>

<body>
<h1>List Students</h1>

<form method="post">
    <% StudentFilter studentFilter = (StudentFilter) request.getAttribute("studentFilter");

    %>
    <div class="form-group row">
        <div class="col-1">
            <label class="col-sm-2 col-form-label"> Name:</label>
        </div>
        <div class="col-2">
            <input type="text" class="form-control" name="name" placeholder="Partial name"
                   value="<%=(studentFilter != null && studentFilter.getName() != null ? studentFilter.getName() :"")%>">
        </div>
        <div class="col-1">
            <label class="col-3 col-form-label">Date:</label>
        </div>
        <div class="col-2">
            <input type="date" class="form-control" name="startDate" placeholder="Start Date"
                   value="<%=(studentFilter != null && studentFilter.getStartDate() != null ? studentFilter.getStartDate() : "")%>">
        </div>
        <div class="col-2">
            <input type="date" class="form-control" name="endDate" placeholder="End Date"
                   value="<%=(studentFilter != null && studentFilter.getEndDate() != null ? studentFilter.getEndDate() : "")%>">
        </div>
    </div>
    <div class="form-row">
        <div class="col-1">
            <label class="col-sm-2 col-form-label">Address:</label>
        </div>
        <div class="col-2">
            <input type="text" class="form-control" name="studentAddress" placeholder="Partial address"
                   value="<%=(studentFilter != null && studentFilter.getStudentAddress() != null ? studentFilter.getStudentAddress() : "")%>">
        </div>
        <div class="col-1">
            <label class="col-sm-2 col-form-label">Discipline:</label>
        </div>
        <div class="col-2">
            <select id="inputState" class="form-control" name="discipline" placeholder="Select discipline">
                <option <%=(studentFilter != null && studentFilter.getDisciplineId() != null) ? "hidden" : ""%> selected
                                                                                                                value="">
                    Select discipline
                </option>
                <% Set<Discipline> disciplines = (Set<Discipline>) request.getAttribute("disciplines");
                    for (Discipline discipline : disciplines) {
                %>
                <option <%=(studentFilter != null && studentFilter.getDisciplineId() != null && studentFilter.getDisciplineId().equals(String.valueOf(discipline.getId())) ? "selected" : "")%>
                        value="<%=discipline.getId()%>"><%=discipline.getTitle()%>
                </option>
                <%}%>
            </select>
        </div>
        <div class="col-2">
            <input type="text" class="form-control" placeholder="Discipline name" name="disciplineTitle"
                   value="<%=(studentFilter != null && studentFilter.getDisciplineTitle() != null ? studentFilter.getDisciplineTitle(): "")%>">
        </div>
    </div>
    <br>
    <div class="form-row">
        <div class="col-1">
            <label class="col-sm-2 col-form-label">Group:</label>
        </div>
        <div class="col-2">
            <select class="form-control" name="group" placeholder="Select group">
                <option  <%=(studentFilter != null && studentFilter.getGroupId() != null ? "hidden" : "")%> selected
                                                                                                            value="">
                    Select group
                </option>
                <% Set<Group> groups = (Set<Group>) request.getAttribute("groups");
                    for (Group group : groups) {
                %>
                <option <%=(studentFilter != null && studentFilter.getGroupId() != null && studentFilter.getGroupId().equals(String.valueOf(group.getId())) ? "selected" : "")%>
                        value="<%=group.getId()%>"><%=group.getName()%>
                </option>
                <%}%>
            </select>
        </div>
        <div class="col-1">
            <label class="col-sm-2 col-form-label">Average:</label>
        </div>
        <div class="col-2">
            <input type="text" class="form-control" name="average" placeholder="Student average"
                   value="<%=(studentFilter != null && studentFilter.getAverage() != null ? studentFilter.getAverage() : "")%>">
        </div>
    </div>
    <br>
    <div class="form-row">
        <div class="col-1">
            <label class="col-sm-2 col-form-label">Gender:</label>
        </div>
        <div class="col-2">
            <div class="form-check form-check-inline">
                <input class="form-check-input col-2" type="radio" name="gender"
                       value="M" <%=(studentFilter != null && studentFilter.getGender() != null && studentFilter.getGender().equals("M")) ? "checked" : ""%>>
                Male<br>
                <input class="form-check-input col-2" type="radio" name="gender"
                       value="F" <%=(studentFilter != null && studentFilter.getGender() != null && studentFilter.getGender().equals("F")) ? "checked" : ""%>>
                Female<br>
                <input class="form-check-input col-2" type="radio" name="gender"
                       value="" <%=(studentFilter != null && studentFilter.getGender() != null && (studentFilter.getGender().equals("M") || studentFilter.getGender().equals("F"))) ? "" : "checked"%>>
                All<br>
            </div>
        </div>
        <div class="col-3"></div>
        <div class="col-1">
            <input type="hidden" id="search" name="action" value="SEARCH" disabled>
            <button type="submit" class="btn btn-secondary btm-sm" name="search" onclick="activateInput(name)">Search
            </button>
        </div>
        <div class="col">
            <input type="hidden" id="reset" name="action" value="RESET" disabled>
            <button type="submit" class="btn btn-secondary btm-sm" name="reset" onclick="activateInput(name)">Reset
            </button>
        </div>
    </div>
</form>
<br>
<form method="post" id="list" action="student">
    <table id="ViewStudents" class="table table-hover">
        <thead>
        <%
            Set<Student> students = (Set<Student>) request.getAttribute("students");
        %>
        <tr>
            <th scope="col">ID</th>
            <td><input type="checkbox" onclick="checkAll(this)" onchange="document.getElementById('deleteButton').disabled = false;"></td>
            <th scope="col">Photo</th>
            <th scope="col">Name</th>
            <th scope="col">Birth Day</th>
            <th scope="col">Gender</th>
            <th scope="col">Address</th>
            <th scope="col">Phone</th>
            <th scope="col">Library Abonament</th>
            <th scope="col">Group</th>
            <th scope="col">Disciplines</th>
            <th scope="col">Action</th>
        </tr>
        </thead>
        <%
            for (Student student : students) {
        %>
        <tbody>
        <tr>
            <td>
                <%=student.getId()%>
            </td>
            <td><input type="checkbox" name="check[]" id="myCheck" value="<%=student.getId()%>" onchange="document.getElementById('deleteButton').disabled = false;"
                       onclick="idChecked(value)"></td>
            <%
                byte[] img = student.getPicture();
                String imageBase64 = "";
                if (img != null) {
                    imageBase64 = new String(Base64.getEncoder().encode(img));
                }
            %>
            <td><img src="data:image/jpeg;base64, <%=imageBase64%>" width="50" height="50" alt="image">
            </td>
            <td><%=student.getFirstName() + " " + student.getLastName()%>
            </td>
            <td><%=student.getDateOfBirth()%>
            </td>
            <td><%=student.getGender()%>
            </td>
            <td><%=student.getAddresses().getCity() + " " + student.getAddresses().getAddress()%>
            </td>
            <td><%
                if (student.getPhones() != null){
                for (Phone phone : student.getPhones()) {%>
                <%=phone.getPhoneType().getName()%>:
                <%=phone.getValue()%><br>
                <%}}%>
            </td>
            <td><a href="#" style="color: #221fff;"
                   onclick="window.open('?action=LIBRARY_ABONAMENT&studentId='+ <%=student.getId()%>,'MyWindow', 'width=900, height=400');
                           return false;"><%="Status: " + student.getLibraryAbonament().getStatus()%>
            </a><br>
                <p <%=(!student.getLibraryAbonament().getStatus().equals("Active")) ? "hidden" : ""%>>
                    From: <%=student.getLibraryAbonament().getStartDate()%><br>
                    To: <%=student.getLibraryAbonament().getEndDate()%>
                </p>
            </td>
            <td><%=student.getGroup().getName()%>
            </td>
            <td><%
                if (student.getDisciplines() != null){
                for (Discipline discipline : student.getDisciplines()) {%>
                <%=discipline.getTitle()%>: <%=discipline.getAverage().getValue()%><br>
                <%}}%>
            </td>
            <td>
                <button type="submit" class="btn btn-success" name="edit"
                        onclick="window.open('?action=EDIT&studentId='+ <%=student.getId()%>,'MyWindow' ,'width=1200, height=700'); return false;">
                    Edit
                </button>
                <button type="submit" class="btn btn-primary" name="mark"
                        onclick="window.open('?action=MARK&studentId='+ <%=student.getId()%>,'MyWindow' ,'width=900, height=400'); return false;">
                    Add Mark
                </button>
            </td>
        </tr>
        <%}%>
        </tbody>
    </table>

    <button type="submit" class="btn btn-secondary btm-sm" name="add_student"
            onclick="window.open('?action=EDIT&studentId=0','MyWindow' ,400,400); return false;">Add New
    </button>
    <input type="hidden" id="delete" name="action" value="DELETE" disabled>
    <button type="submit" class="btn btn-secondary btm-sm" name="delete" id="deleteButton" onclick="confSubmit(name)" disabled>Delete
    </button>
</form>
</body>
</html>
