<%@ page import="java.util.Set" %>
<%@ page import="static java.util.Objects.isNull" %>
<%@ page import="java.util.Base64" %>
<%@ page import="com.amsoftgroup.model.*" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.ArrayList" %><%--
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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <style>
        <%@include file="/WEB-INF/views/css/bootstrap.min.css" %>
        <%@include file="/WEB-INF/views/css/style.css" %>
    </style>
    <script>
        function previewFile() {
            var preview = document.querySelector('img');
            var file = document.querySelector('input[type=file]').files[0];
            var reader = new FileReader();
            reader.addEventListener("load", function () {
                preview.src = reader.result;
                document.getElementById(fileImg).value = reader.result;
            }, false);

            if (file) {
                reader.readAsDataURL(file);
            }
        }

        function duplicate(onePhone) {
            var c = $(onePhone).parent(this).parent(this).clone().appendTo(".allphones");
            c.find('input[type="text"]').val('');
            c.find('input[type="hidden"]').val('');
        }
        function deleteBlock(onePhone) {
            var c = $(onePhone).parent(this).parent(this).remove();
        }
    </script>
</head>
<body>
<h1>Add / Edit Student</h1>
<form method="post" enctype="multipart/form-data">
    <% Student student = new Student();
        long studentId = (Long) request.getAttribute("studentId");
        if (!isNull(studentId) && studentId != 0) {
            student = (Student) request.getAttribute("student");
        }
        Set<PhoneType> phoneTypes = (Set<PhoneType>) request.getAttribute("phoneTypes");
    %>
    <div class="form-group row">
        <div class="col-1">
            <input type="hidden" name="studentId" <%if (studentId!=0){%>value="<%=student.getId()%>"<%}else{%>
                   value=""<%}%>>
            <label class="col-sm-2 col-form-label">FirstName:</label>
        </div>
        <div class="col-3">
            <input type="text" class="form-control" placeholder="First name" name="first_name"
                   value="<%=(studentId!=0)?student.getFirstName():""%>">
        </div>

    </div>
    <div>
        <div class="form-group row">
            <div class="col-1">
                <label class="col-sm-2 col-form-label">LastName:</label>
            </div>
            <div class="col-3">
                <input type="text" class="form-control" placeholder="Last name" name="last_name"
                       value="<%=(studentId!=0)?student.getLastName():""%>">
            </div>

        </div>
        <div class="form-group row">
            <div class="col-1">
                <label class="col-3 col-form-label">Date:</label>
            </div>
            <div class="col-3">
                <input type="date" class="form-control" name="date_of_birth" placeholder="Date of birth"
                       value="<%=(studentId!=0)?student.getDateOfBirth():""%>">
            </div>
        </div>
        <div class="form-row">
            <div class="col-1">
                <label class="col-sm-2 col-form-label">Gender:</label>
            </div>
            <div class="col-2">
                <div class="form-check form-check-inline">
                    <input class="form-check-input col-4" type="radio" name="gender"
                           value="M" <%=(student.getGender()=='M')?"checked":""%>>Male<br>
                    <input class="form-check-input col-4" type="radio" name="gender"
                           value="F" <%=(student.getGender()=='F')?"checked":""%>>Female<br>
                </div>
            </div>
        </div>
        <div class="form-row">
            <input type="hidden" name="addressId" value="<%=(studentId!=0)?student.getAddresses().getId():""%>">
            <div class="col-1">
                <label class="col-sm-2 col-form-label">Country:</label>
            </div>
            <div class="col-3">
                <input type="text" class="form-control" placeholder="Country" name="country"
                       value="<%=(studentId!=0)?student.getAddresses().getCountry():""%>">
            </div>
            <div class="col-1"></div>
            <div class="col-1">
                <label class="col-sm-2 col-form-label">Picture:</label>
            </div>
            <div class="col-3">
                <%
                    byte[] img = student.getPicture();
                    String imageBase64 = "";
                    if (img != null) {
                        imageBase64 = new String(Base64.getEncoder().encode(img));
                    }
                %>
                <img src="data:image/jpeg;base64, <%=imageBase64%>" class="rounded float-left"
                     style="position:absolute; bottom: 50px" height="200" width="178" name="image">
                <input type="file" id="customFile" name="image" onchange="previewFile()" multiple
                       value="data:image/jpeg;base64, <%=imageBase64%>">
                <label class="custom-file-label" placeholder="Picture File" for="customFile" name="image"></label>

            </div>
        </div>
        <br>
        <div class="form-row">
            <div class="col-1">
                <label class="col-sm-2 col-form-label">City:</label>
            </div>
            <div class="col-3">
                <input type="text" class="form-control" placeholder="City" name="city"
                       value="<%=(studentId!=0)?student.getAddresses().getCity():""%>">
            </div>
            <div class="col-1"></div>
            <div class="col-1">
                <label class="col-sm-2 col-form-label">Mail:</label>
            </div>
            <div class="col-3">
                <input type="text" class="form-control" placeholder="Mail" name="mail"
                       value="<%=(studentId!=0)?student.getMail():""%>">
            </div>
        </div>
        <br>
        <div class="form-row">
            <div class="col-1">
                <label class="col-sm-2 col-form-label">Address:</label>
            </div>
            <div class="col-3">
                <input type="text" class="form-control" placeholder="Address" name="address"
                       value="<%=(studentId!=0)?student.getAddresses().getAddress():""%>">
            </div>
            <div class="col-1"></div>
            <div class="col-1">
                <label class="col-sm-2 col-form-label">Group:</label>
            </div>
            <div class="col-3">
                <select name="group" class="form-control">
                    <option <%=(studentId == 0) ? "hidden" : ""%> selected value="">Select group</option>
                    <% Set<Group> groups = (Set<Group>) request.getAttribute("groups");
                        for (Group group : groups) {
                    %>
                    <option <%=(studentId != 0) ? ((student.getGroup().getId() == group.getId()) ? "selected" : "") : ""%>
                            value="<%=group.getId()%>"><%=group.getName()%>
                    </option>
                    <%}%>
                </select>
            </div>
        </div>
        <br>
    </div>
    <div class="allphones">
        <div class="form-row" name="phone">
            <div class="col-1">
                <label class="col-sm-2 col-form-label">Phone(s):</label>
            </div>
            <% Set<Phone> phones = new HashSet<>();
                if (studentId != 0) {
                    phones = (Set<Phone>) student.getPhones();
                }
                ArrayList<Long> phoneTypeId = new ArrayList<>();
                ArrayList<Long> phoneId = new ArrayList<>();
                ArrayList<String> phoneValue = new ArrayList<>();
                int count = 0;
                for (Phone phone : phones) {
                    phoneTypeId.add(phone.getPhoneType().getId());
                    phoneId.add(phone.getId());
                    phoneValue.add(phone.getValue());
                }
            do {%>
            <div class="col-1.5">
                <select class="form-control" name="phoneType[]">
                    <option <%=(studentId == 0) ? "hidden" : ""%> selected>Select phone type</option>
                    <% for (PhoneType phoneType : phoneTypes) {%>
                    <option <%=(studentId != 0) ? ((phoneTypeId.get(count) == phoneType.getId()) ? "selected" : "") : ""%>
                            value="<%=phoneType.getId()%>"><%=phoneType.getName()%>
                    </option>
                    </option>
                    <%}%>
                </select>
            </div>
            <div class="col-1.5">
                <input type="hidden" class="form-control" name="phoneId[]" value="<%=(studentId!=0)?phoneId.get(count):""%>">
                <input type="text" class="form-control" placeholder="Phone number" name="phoneNumber[]" value="<%=(studentId!=0)?phoneValue.get(count):""%>">
            </div>
            <div class="col-1.5" name="buttons">
                <button type="button" class="btn btn-success" onclick="duplicate(this)">Add</button>
                <button type="button" class="btn btn-danger" onclick="deleteBlock(this)">Delete</button>
            </div>
        </div>
        <div class="form-row">
            <div class="col-1">
            </div>
            <%count++;}
                while (count < phoneId.size());%>
        </div>
    </div>
    <br>
    <div class="form-row">
        <div class="col-6"></div>
        <div class="col-2">
            <button type="submit" class="btn btn-secondary btm-sm">Save</button>

            <button type="button" onclick="window.close()" class="btn btn-secondary btm-sm">Cancel</button>
        </div>
    </div>
</form>
</body>
</html>
