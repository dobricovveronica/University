package com.amsoftgroup.dao;

import com.amsoftgroup.model.Address;
import com.amsoftgroup.model.LibraryAbonament;
import com.amsoftgroup.model.Person;
import com.amsoftgroup.model.Phone;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class PersonDao {

    private Connection connection;

    public PersonDao(Connection connection) {
        this.connection = connection;
    }

    public Set<Person> getPerson() {
        String sql = "SELECT * FROM university.persons ";
        Set<Person> persons = new HashSet<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Person person = new Person();
                person.setId(Long.parseLong(rs.getString("id")));
                person.setFirstName(rs.getString("first_name"));
                person.setLastName(rs.getString("last_name"));
                person.setDateOfBirth(LocalDate.parse(String.valueOf(rs.getDate("date_of_birth"))));
                person.setGender(rs.getString("gender").charAt(0));
                person.setPicture(rs.getBytes("picture"));
                person.setMail(rs.getString("mail"));
//                person.getAddresses().setId(Long.parseLong(rs.getString("address_id")));
//                person.getLibraryAbonament().setId(Long.parseLong(rs.getString("library_abonament_id")));
                persons.add(person);
                System.out.println(rs.getLong("id") + " " + rs.getString("first_name") + " " + rs.getString("last_name") + " " + rs.getString("date_of_birth") + " " + rs.getString("gender"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }

    public void deletePerson(Person person) {
        String sql = "DELETE FROM university.persons where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, person.getId());
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePerson(Person person) {
        String sql = "UPDATE university.persons SET first_name=?, last_name=?, date_of_birth=?, gender=?, picture=?, mail=?, address_id=?, library_abonament_id=? where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setDate(3, Date.valueOf(person.getDateOfBirth()));
            statement.setString(4, String.valueOf(person.getGender()));
            statement.setBytes(5, person.getPicture());
            statement.setString(6, person.getMail());
            statement.setLong(7, person.getAddresses().getId());
            statement.setLong(8, person.getLibraryAbonament().getId());
            statement.setLong(9, person.getId());
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //    public void insert() {
//        String sql = "INSERT INTO university.persons VALUES(?)";
//
//        try {
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setString(1, "IS31Z");
//            System.out.println(statement.toString());
//            statement.execute();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    public Long insertPerson(Person person) {
        String sql = "INSERT INTO university.persons VALUES(?, ?, ?, ?, ?, ?, ?, ?) returning id";
        Long person_id = 0L;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setDate(3, Date.valueOf(person.getDateOfBirth()));
            statement.setString(4, String.valueOf(person.getGender()));
            statement.setBytes(5, person.getPicture());
            statement.setString(6, person.getMail());
            statement.setLong(7, person.getAddresses().getId());
            statement.setLong(8, person.getLibraryAbonament().getId());
            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();
            rs.next();
            person_id = rs.getLong(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  person_id;
    }

    public Set<Person> getAllPersons() {
        String sql = "select P.id, P.first_name,\n" +
                "       P.last_name,\n" +
                "       P.date_of_birth,\n" +
                "       P.gender,\n" +
                "       P.mail,\n" +
                "       A.id as aid, A.city,\n" +
                "       A.address,\n" +
                "       LA.id as laid, LA.status,\n" +
                "       PT.id, PT.name,\n" +
                "       PH.id, PH.value\n" +
                "from university.persons as P\n" +
                "       left join university.persons_to_phones as PP on P.id = PP.person_id\n" +
                "       left join university.phones as PH on PP.phone_id = PH.id\n" +
                "       left join university.phone_types as PT on PH.type_id = PT.id\n" +
                "       left join university.addresses as A on P.address_id = A.id\n" +
                "       left join university.library_abonaments as LA on P.library_abonament_id = LA.id";
        Set<Person> persons = new HashSet<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Person person = new Person();
                person.setId(Long.parseLong(rs.getString("id")));
                person.setFirstName(rs.getString("first_name"));
                person.setLastName(rs.getString("last_name"));
                person.setDateOfBirth(LocalDate.parse(String.valueOf(rs.getDate("date_of_birth"))));
                person.setGender(rs.getString("gender").charAt(0));
//                person.setPicture(rs.getBytes("picture"));
                person.setMail(rs.getString("mail"));
                Address address = new Address();
                address.setId(Long.parseLong(rs.getString("aid")));
                address.setAddress(rs.getString("address"));
                address.setCity(rs.getString("city"));
                person.setAddresses(address);
                LibraryAbonament libraryAbonament = new LibraryAbonament();
                libraryAbonament.setId(Long.parseLong(rs.getString("laid")));
                libraryAbonament.setStatus(rs.getString("status"));
                person.setLibraryAbonament(libraryAbonament);
//                address.setCountry(rs.getString("country"));
//                person.getAddresses().setCity(rs.getString("addresses.city"));
//                person.getAddresses().setAddress(rs.getString("addresses.address"));

//                person.getLibraryAbonament().setStatus(rs.getString("library_abonaments.status"));
//                person.getAddresses().setId(Long.parseLong(rs.getString("address_id")));
//                person.getLibraryAbonament().setId(Long.parseLong(rs.getString("library_abonament_id")));
                persons.add(person);
//                System.out.println(rs.getLong("id") + " " + rs.getString("first_name") + " " + rs.getString("last_name") + " " + rs.getString("date_of_birth") + " " + rs.getString("gender"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }
}
