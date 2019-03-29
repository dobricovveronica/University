<%@ page import="com.amsoftgroup.model.Discipline" %>
<%@ page import="com.amsoftgroup.model.Group" %>
<%@ page import="com.amsoftgroup.model.Phone" %>
<%@ page import="com.amsoftgroup.model.Student" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Base64" %>

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
        function refresh() {
            // document.forms["list"].submit();

            form["list"].onsubmit = function () {
                var oldCookie = document.cookie;

                var cookiePoll = setInterval(function () {
                    if (oldCookie != document.cookie) {
                        // stop polling
                        clearInterval(cookiePoll);

                        // assuming a login happened, reload page
                        window.location.reload();
                    }
                }, 1000); // check every second
            }
        }
        function activateInput(name) {
            document.getElementById('search').disabed = true;
            document.getElementById('reset').disabed = true;
            document.getElementById('delete').disabed = true;
            // document.getElementById('delete').setAttribute("disabled", true);
            if (name == "search"){
                document.getElementById('search').disabed = false;
            }if (name == "reset"){
                document.getElementById('reset').disabed = false;
            }if (name == "delete"){
                document.getElementById('delete').disabed = false;
            }
        }
        // <label class="col-sm-2 col-form-label">Gender:</label>Id('delete').onclick = function() {
        //     document.forms.my.reset(); // сбрасываем форму
        //     location.reload(); // перезагружаем страницу
        // }

    </script>
</head>

<body>
<h1>List Students</h1>
<form method="post">
    <div class="form-group row">
        <div class="col-1">
            <label class="col-sm-2 col-form-label"> Name:</label>
        </div>
        <div class="col-2">
            <input type="text" class="form-control" name="name" placeholder="Partial name">
        </div>
        <div class="col-1">
            <label class="col-3 col-form-label">Date:</label>
        </div>
        <div class="col-2">
            <input type="date" class="form-control" name="startDate" placeholder="Start Date">
        </div>
        <div class="col-2">
            <input type="date" class="form-control" name="endDate" placeholder="End Date">
        </div>
    </div>
    <div class="form-row">
        <div class="col-1">
            <label class="col-sm-2 col-form-label">Address:</label>
        </div>
        <div class="col-2">
            <input type="text" class="form-control" name="studentAddress" placeholder="Partial address">
        </div>
        <div class="col-1">
            <label class="col-sm-2 col-form-label">Discipline:</label>
        </div>
        <div class="col-2">
            <select id="inputState" class="form-control" name="discipline">
                <option value="" selected>All</option>
                <% Set<Discipline> disciplines = (Set<Discipline>) request.getAttribute("disciplines");
                    for (Discipline discipline : disciplines) {
                %>
                <option value="<%=discipline.getId()%>"><%=discipline.getTitle()%>
                </option>
                <%}%>
            </select>
        </div>
        <div class="col-2">
            <input type="text" class="form-control" placeholder="" name="disciplineTitle">
        </div>
    </div>
    <br>
    <div class="form-row">
        <div class="col-1">
            <label class="col-sm-2 col-form-label">Group:</label>
        </div>
        <div class="col-2">
            <select class="form-control" name="group">
                <option selected value="">All</option>
                <% Set<Group> groups = (Set<Group>) request.getAttribute("groups");
                    for (Group group : groups) {
                %>
                <option value="<%=group.getId()%>"><%=group.getName()%>
                </option>
                <%}%>
            </select>
        </div>
        <div class="col-1">
            <label class="col-sm-2 col-form-label">Average:</label>
        </div>
        <div class="col-2">
            <input type="text" class="form-control" name="average" placeholder="">
        </div>
    </div>
    <br>
    <div class="form-row">
        <div class="col-1">
            <label class="col-sm-2 col-form-label">Gender:</label>
        </div>
        <div class="col-2">
            <div class="form-check form-check-inline">
                <input class="form-check-input col-2" type="radio" name="gender" value="M"> Male<br>
                <input class="form-check-input col-2" type="radio" name="gender" value="F"> Female<br>
                <input class="form-check-input col-2" type="radio" name="gender" value="" checked> All<br>
            </div>
        </div>
        <div class="col-3"></div>
        <div class="col-1">
            <input type="hidden" id="search" name="action" value="SEARCH" disabled="">
            <button type="submit" class="btn btn-secondary btm-sm" name="search" onclick="activateInput(name)">Search</button>
        </div>
        <div class="col">
            <input type="hidden" id="reset" name="action" value="RESET" disabled="">
            <button type="reset" class="btn btn-secondary btm-sm" name="reset" onclick="activateInput(name)">Reset</button>
        </div>
    </div>
</form>
<br>
<form method="post" id="list">
    <table id="ViewStudents" class="table table-hover">
        <thead>
        <tr>
            <th scope="col">ID</th>
            <td><input type="checkbox" name="allCheck"></td>
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
            Set<Student> students = (Set<Student>) request.getAttribute("students");
            for (Student student : students) {
        %>
        <tbody>
        <tr>
            <td>
                <%=student.getId()%>
            </td>
            <td><input type="checkbox" name="check[]" id="myCheck" value="<%=student.getId()%>"
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
                for (Phone phone : student.getPhones()) {%>
                <%=phone.getPhoneType().getName()%>:
                <%=phone.getValue()%><br>
                <%}%>
            </td>
            <td><a href="#" style="color: #221fff;"
                   onclick="window.open('/student?action=LIBRARY_ABONAMENT&studentId='+ <%=student.getId()%>,'MyWindow', 'width=900, height=400');
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
                for (Discipline discipline : student.getDisciplines()) {%>
                <%=discipline.getTitle()%><br>
                <%}%>
                <%--            <hr>Total Average:--%>
            </td>
            <td>
                <button type="submit" class="btn btn-success" name="edit"
                        onclick="window.open('/student?action=EDIT&studentId='+ <%=student.getId()%>,'MyWindow' ,'width=1200, height=700');">
                    Edit
                </button>
                <button type="submit" class="btn btn-primary" name="mark"
                        onclick="window.open('/student?action=MARK&studentId='+ <%=student.getId()%>,'MyWindow' ,'width=900, height=400');">
                    Add Mark
                </button>
            </td>
        </tr>

        <%}%>

        </tbody>
    </table>

    <button type="submit" class="btn btn-secondary btm-sm" name="add_student"
            onclick="window.open('/student?action=EDIT&studentId=0','MyWindow' ,400,400);">Add New
    </button>
        <input type="hidden" name="action" id="delete" value="DELETE" disabled="">
    <button type="submit" class="btn btn-secondary btm-sm" onchange="activateInput(name)">Delete
    </button>
</form>
<%--<input type="button" value="Reload Page" onClick="document.location.reload(true)">--%>

</body>
</html>
