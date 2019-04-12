package com.amsoftgroup.dao;

import com.amsoftgroup.model.Person;
import com.amsoftgroup.model.Phone;
import com.amsoftgroup.model.PhoneType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class PhoneDao {

    private Connection connection;

    public PhoneDao(Connection connection) {
        this.connection = connection;
    }

    public Set<Phone> getPhone() {
        String sql = "SELECT * FROM university.phones ";
        Set<Phone> phones = new HashSet<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Phone phone = new Phone();
                phone.setId(Long.parseLong(rs.getString("id")));
                phone.getPhoneType().setId(Long.parseLong(rs.getString("type_id")));
                phone.setValue(rs.getString("value"));
                phones.add(phone);
                System.out.println(rs.getLong("id") + " " + rs.getLong("type_id") + " " + rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phones;
    }

    public void deletePhone(Phone phone) {
        String sql = "DELETE FROM university.phones where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, phone.getId());
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePhone(Phone phone) {
        String sql = "UPDATE university.phones SET type_id = ?, value=? where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, phone.getPhoneType().getId());
            statement.setString(2, phone.getValue());
            statement.setLong(3, phone.getId());
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Long insertPhone(Phone phone) {
        Long phone_id = 0L;
        String sql = "INSERT INTO university.phones VALUES(?, ?) returning id ";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
//            PreparedStatement statement1 = connection.prepareStatement(condition);
            statement.setLong(1, phone.getPhoneType().getId());
            statement.setString(2, phone.getValue());
            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();
            rs.next();
            phone_id = rs.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phone_id;
    }

    public Set<Phone> getPhonesById(Long studentId) {
        String sql = "select P.id, PH.id as phid, PH.value as phvalue, PT.id as ptid, PT.name as ptname " +
                "from university.persons as P " +
                "       left join university.persons_to_phones as PP on P.id = PP.person_id " +
                "       left join university.phones as PH on PP.phone_id = PH.id " +
                "       left join university.phone_types as PT on PH.type_id = PT.id  " +
                "where P.id = ? ";

        Set<Phone> phones = new HashSet<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            System.out.println(statement.toString());
            statement.setLong(1, studentId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Phone phone = new Phone();
                if (rs.getString("phid") != null) {
                    phone.setId(Long.parseLong(rs.getString("phid")));
                    phone.setValue(rs.getString("phvalue"));
                    PhoneType phoneType = new PhoneType();
                    phoneType.setId(Long.parseLong(rs.getString("ptid")));
                    phoneType.setName(rs.getString("ptname"));
                    phone.setPhoneType(phoneType);
                    phones.add(phone);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phones;
    }

    public void addPhoneToPerson(Phone phone, Person person) {
        String sql = "INSERT INTO university.persons_to_phones(person_id, phone_id) VALUES(?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, person.getId());
            statement.setLong(2, phone.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

