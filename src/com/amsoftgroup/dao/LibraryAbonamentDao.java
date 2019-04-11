package com.amsoftgroup.dao;

import com.amsoftgroup.model.LibraryAbonament;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.isNull;

public class LibraryAbonamentDao {

    private Connection connection;

    public LibraryAbonamentDao(Connection connection) {
        this.connection = connection;
    }

    public Set<LibraryAbonament> getLibraryAbonament() {
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

    public void deleteLibraryAbonament(Long libraryAbonamentId) {
        String sql = "DELETE FROM university.library_abonaments where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, libraryAbonamentId);
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateLibraryAbonament(LibraryAbonament libraryAbonament) {
        String sql = "UPDATE university.library_abonaments SET status=?, start_date=?, end_date=? where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, libraryAbonament.getStatus());
            if(!isNull(libraryAbonament.getStartDate())){
                statement.setDate(2, Date.valueOf(libraryAbonament.getStartDate()));}
            else {statement.setDate(2, null);}
            if(!isNull(libraryAbonament.getStartDate())){
                statement.setDate(3, Date.valueOf(libraryAbonament.getEndDate()));}
            else {statement.setDate(3, null);}
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

    public Long insertLibraryAbonament(LibraryAbonament libraryAbonament) {
        String sql = "INSERT INTO university.library_abonaments VALUES(?, ?, ?) returning id";
        Long libraryAbonamentId = 0L;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, libraryAbonament.getStatus());
            if(!isNull(libraryAbonament.getStartDate())){
                statement.setDate(2, Date.valueOf(libraryAbonament.getStartDate()));}
            else {statement.setDate(2, null);}
            if(!isNull(libraryAbonament.getStartDate())){
                statement.setDate(3, Date.valueOf(libraryAbonament.getEndDate()));}
            else {statement.setDate(3, null);}
            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();
            rs.next();
            libraryAbonamentId = rs.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libraryAbonamentId;
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
