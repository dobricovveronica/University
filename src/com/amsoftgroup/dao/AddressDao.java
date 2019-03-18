
package com.amsoftgroup.dao;

        import com.amsoftgroup.model.Address;
        import com.amsoftgroup.model.Group;

        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.util.HashSet;
        import java.util.Set;

public class AddressDao {

    private Connection connection;

    public AddressDao(Connection connection) {
        this.connection = connection;
    }

    public Set<Address> get() {
        String sql = "SELECT * FROM university.addresses ";
        Set<Address> addresses = new HashSet<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Address address = new Address();
                address.setId(Long.parseLong(rs.getString("id")));
                address.setCountry(rs.getString("country"));
                address.setCity(rs.getString("city"));
                address.setAddress(rs.getString("address"));
                addresses.add(address);
                System.out.println(rs.getLong("id") + " " + rs.getString("country") + " " + rs.getString("city") + " " + rs.getString("address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addresses;
    }

    public void delete(Address address) {
        String sql = "DELETE FROM university.addresses where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, address.getId());
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Address address) {
        String sql = "UPDATE university.addresses SET country=?, city=?, address=? where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, address.getCountry());
            statement.setString(2, address.getCity());
            statement.setString(3, address.getAddress());
            statement.setLong(4, address.getId());
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Address address) {
        String sql = "INSERT INTO university.addresses VALUES(?,?,?) returning id";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, address.getCountry());
            statement.setString(2, address.getCity());
            statement.setString(3, address.getAddress());
            System.out.println(statement.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
