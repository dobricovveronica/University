package com.amsoftgroup.dao;

import com.amsoftgroup.model.LibraryAbonament;

import javax.xml.crypto.Data;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class LibraryAbonamentDao {

    private Connection connection;

    public LibraryAbonamentDao(Connection connection) {
        this.connection = connection;
    }

    public Set<LibraryAbonament> get() {
        String sql = "SELECT * FROM university.library_abonaments ";
        Set<LibraryAbonament> libraryAbonaments = new HashSet<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                LibraryAbonament libraryAbonament = new LibraryAbonament();
                libraryAbonament.setId(Long.parseLong(rs.getString("id")));
                libraryAbonament.setStatus(rs.getString("status"));
                libraryAbonament.setStartDate(LocalDate.parse(String.valueOf(rs.getDate("start_date"))));
                libraryAbonament.setEndDate(LocalDate.parse(String.valueOf(rs.getDate("end_date"))));
                libraryAbonaments.add(libraryAbonament);
                System.out.println(rs.getLong("id") + " " + rs.getString("status") + " " + rs.getDate("start_date") + " " + rs.getDate("end_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libraryAbonaments;
    }

    public void delete(LibraryAbonament libraryAbonament) {
        String sql = "DELETE FROM university.library_abonaments where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, libraryAbonament.getId());
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(LibraryAbonament libraryAbonament) {
        String sql = "UPDATE university.library_abonaments SET status=?, start_date=?, end_date=? where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, libraryAbonament.getStatus());
            statement.setDate(2, java.sql.Date.valueOf(libraryAbonament.getStartDate()));
            statement.setDate(3, Date.valueOf(libraryAbonament.getEndDate()));
            statement.setLong(4, libraryAbonament.getId());
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public void insert() {
//        String sql = "INSERT INTO university.library_abonaments VALUES(?, ?, ?)";
//
//        try {
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setString(1, "suspended");
//            statement.setDate(2, Date.valueOf("1.03.2018"));
//            statement.setDate(3, Date.valueOf("1.01.2019"));
//            System.out.println(statement.toString());
//            statement.execute();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public void insert() {
        String sql = "INSERT INTO university.library_abonaments(status) VALUES(?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "None");
//            statement.setDate(2, Date.valueOf(libraryAbonament.getStartDate()));
//            statement.setDate(3, Date.valueOf(libraryAbonament.getEndDate()));
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Set<LibraryAbonament> getAllAbonaments() {
        String sql = "SELECT DISTINCT library_abonaments.status FROM university.library_abonaments order by library_abonaments.status asc";
        Set<LibraryAbonament> libraryAbonaments = new HashSet<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                LibraryAbonament libraryAbonament = new LibraryAbonament();
//                libraryAbonament.setId(Long.parseLong(rs.getString("laid")));
                libraryAbonament.setStatus(rs.getString("status"));
                libraryAbonaments.add(libraryAbonament);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libraryAbonaments;
    }

    public LibraryAbonament getAbonamentById(Long id) {
        String sql = "SELECT * FROM university.library_abonaments where library_abonaments.id = ?";
        LibraryAbonament libraryAbonament = new LibraryAbonament();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            System.out.println(statement.toString());
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
                libraryAbonament.setId(Long.parseLong(rs.getString("id")));
                libraryAbonament.setStatus(rs.getString("status"));
                libraryAbonament.setStartDate(LocalDate.parse(String.valueOf(rs.getDate("start_date"))));
                libraryAbonament.setEndDate(LocalDate.parse(String.valueOf(rs.getDate("end_date"))));
                System.out.println(rs.getLong("id") + " " + rs.getString("status") + " " + rs.getDate("start_date") + " " + rs.getDate("end_date"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libraryAbonament;
    }

}
