<%@ page import="com.amsoftgroup.model.Discipline" %>
<%@ page import="java.util.Set" %>
<%@ page import="com.amsoftgroup.model.Group" %>
<%@ page import="com.amsoftgroup.model.PhoneType" %>
<%@ page import="com.amsoftgroup.model.Student" %><%--
  Created by IntelliJ IDEA.
  User: vdobricov
  Date: 3/14/2019
  Time: 8:35 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add / Edit Student</title>
    <style>
        <%@include file="/WEB-INF/views/css/bootstrap.min.css" %>
        <%@include file="/WEB-INF/views/css/style.css" %>
    </style>
</head>
<body>
<h1>Add / Edit Student</h1>
<form method="post">
    <%Student student = (Student) request.getAttribute("student"); %>

    <div class="form-group row">
        <div class="col-1">
            <input type="hidden" name="studentId" value="<%=student.getId()%>">
            <label class="col-sm-2 col-form-label">FirstName:</label>
        </div>
        <div class="col-3">
            <input type="text" class="form-control" placeholder="First name" name="first_name"
                   value="<%=student.getFirstName()%>">
        </div>
        <div class="col-1"></div>
        <div class="col-1">
            <label class="col-sm-2 col-form-label">Group:</label>
        </div>
        <div class="col-2">
            <select name="group" class="form-control">
                <option selected value="<%=student.getGroup().getId()%>"><%=student.getGroup().getName()%>
                </option>
                <% Set<Group> groups = (Set<Group>) request.getAttribute("groups");
                    for (Group group : groups) {
                %>
                <option value="<%=group.getId()%>"><%=group.getName()%></option>
                <%}%>
            </select>
        </div>
    </div>
    <div>
        <div class="form-group row">
            <div class="col-1">
                <label class="col-sm-2 col-form-label">LastName:</label>
            </div>
            <div class="col-3">
                <input type="text" class="form-control" placeholder="Last name" name="last_name"
                       value="<%=student.getLastName()%>">
            </div>
        </div>
        <div class="form-group row">
            <div class="col-1">
                <label class="col-3 col-form-label">Date:</label>
            </div>
            <div class="col-3">
                <input type="date" class="form-control" name="date_of_birth" placeholder="Date of birth"
                       value="<%=student.getDateOfBirth()%>">
            </div>
        </div>
        <div class="form-row">
            <div class="col-1">
                <label class="col-sm-2 col-form-label">Gender:</label>
            </div>
            <div class="col-2">
                <div class="form-check form-check-inline">
                    <input class="form-check-input col-4" type="radio" name="gender" value="M"> Male<br>
                    <input class="form-check-input col-4" type="radio" name="gender" value="F"> Female<br>
                </div>
            </div>
        </div>
        <div class="form-row">
            <input type="hidden" name="addressId" value="<%=student.getAddresses().getId()%>">
            <div class="col-1">
                <label class="col-sm-2 col-form-label">Country:</label>
            </div>
            <div class="col-3">
                <input type="text" class="form-control" placeholder="Country" name="country"
                       value="<%=student.getAddresses().getCountry()%>">
            </div>
        </div>
        <br>
        <div class="form-row">
            <div class="col-1">
                <label class="col-sm-2 col-form-label">City:</label>
            </div>
            <div class="col-3">
                <input type="text" class="form-control" placeholder="City" name="city"
                       value="<%=student.getAddresses().getCity()%>">
            </div>
        </div>
        <br>
        <div class="form-row">
            <div class="col-1">
                <label class="col-sm-2 col-form-label">Address:</label>
            </div>
            <div class="col-3">
                <input type="text" class="form-control" placeholder="Address" name="address"
                       value="<%=student.getAddresses().getAddress()%>">
            </div>
        </div>
        <br>
        <div class="form-row">
            <div class="col-1">
                <label class="col-sm-2 col-form-label">Phone(s):</label>
            </div>
            <div class="col-1">
                <select class="form-control" name="phone_type">
                    <option selected>All</option>

                    <% Set<PhoneType> phoneTypes = (Set<PhoneType>) request.getAttribute("phoneTypes");
                        for (PhoneType phoneType : phoneTypes) {
                    %>
                    <option value="<%=phoneType.getId()%>"><%=phoneType.getName()%>
                    </option>
                    <%}%>
                </select>
            </div>
            <div class="col-2">
                <input type="text" class="form-control" placeholder="Phone number" name="phone" value="">
            </div>
            <div class="col-1.5">
                <button type="button" class="btn btn-success">Add</button>
                <button type="button" class="btn btn-danger">Delete</button>
            </div>
            <div class="col-2">
                <input type="text" class="form-control" placeholder="Picture File" name="image">
            </div>
            <div class="col-1">
                <button type="button" class="btn btn-secondary btm-sm">Browse</button>
            </div>
        </div>
        <br>
    </div>
    <div class="form-row">
        <div class="col-6"></div>
        <div class="col-2">
            <button type="submit" class="btn btn-secondary btm-sm">Save</button>

            <button type="button" onclick="window.close()"  class="btn btn-secondary btm-sm">Cancel</button>
        </div>
    </div>
</form>
</body>
</html>
