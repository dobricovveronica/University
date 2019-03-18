package com.amsoftgroup;

import com.amsoftgroup.dao.*;
import com.amsoftgroup.utilitys.DataBaseConnection;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();

        TeacherDao teacherDAO = new TeacherDao(dataBaseConnection.initializeDatabaseConnection());
//        teacherDAO.insert(t);


    }
}
