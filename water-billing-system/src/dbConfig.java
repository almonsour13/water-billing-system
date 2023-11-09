import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class dbConfig {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/water-billing-system";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = (Connection) DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(dbConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }
    public void executeQuery(String query) {
        try (Connection con = getConnection();
             Statement st = con.createStatement()) {
            st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ObservableList<Consumer> getConsumers() throws SQLException {
        ObservableList<Consumer> consumers = FXCollections.observableArrayList();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            String query = "SELECT c.cID, c.cFName, c.cMName, c.cLName, c.cSuffix, c.cEmailAd, c.cContactNo, "
                         + "ad.country, ad.region, ad.province, ad.municipality, ad.baranggay, ad.purok, ad.postalCode, m.meterNumber, c.cStatus " +
                           "FROM conscessionaries c " +
                           "JOIN address ad ON c.cID = ad.cID " +
                           "JOIN meter m ON c.cID = m.cID "+
                           "ORDER BY c.cID;";
            ResultSet rs = statement.executeQuery(query);
            int no = 1;
            while (rs.next()) {
                String status = "";
                if(rs.getInt("cStatus")== 1){
                    status = "Active";
                }else{
                    status = "Inactive";
                }
                if(rs.getInt("cStatus")!= 2){
                    consumers.add(
                     new Consumer(no,
                            rs.getInt("cID"), rs.getString("cFName"), 
                            rs.getString("cMName"), rs.getString("cLName"), 
                            rs.getString("cSuffix"), rs.getString("cContactNo"),
                            rs.getString("cEmailAd"), rs.getString("meterNumber"),
                            rs.getString("country"), rs.getString("region"), 
                            rs.getString("province"), rs.getString("municipality"),
                            rs.getString("baranggay"),rs.getString("purok"), 
                            rs.getString("postalCode"),status)
                    );
                    
                    no++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Re-throw the exception
        }
        return consumers;
    }
    public void insertAddConsumer(
        String fName, String mName, String lName, 
        String suffix, String contactNo, String emailAd, 
        String meterNo, String country, String region,
        String province, String muni, 
        String brgy,String purok, String postalCode
    ) throws SQLException {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);

            // Insert data into the conscessionaries table
            String insertConscessionariesSQL = "INSERT INTO conscessionaries (aID, cFName, cMName, cLName, cSuffix, cContactNo, cEmailAd, cStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement conscessionariesStatement = connection.prepareStatement(insertConscessionariesSQL, Statement.RETURN_GENERATED_KEYS)) {
                conscessionariesStatement.setInt(1, 0);
                conscessionariesStatement.setString(2, fName);
                conscessionariesStatement.setString(3, mName);
                conscessionariesStatement.setString(4, lName);
                conscessionariesStatement.setString(5, suffix);
                conscessionariesStatement.setString(6, contactNo);
                conscessionariesStatement.setString(7, emailAd);
                conscessionariesStatement.setInt(8, 1); // Assuming 1 is the value for cStatus

                int rowsAffected = conscessionariesStatement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Inserting consumer failed, no rows affected.");
                }

                // Retrieve the generated cID
                ResultSet generatedKeys = conscessionariesStatement.getGeneratedKeys();
                int cID = 0;
                if (generatedKeys.next()) {
                    cID = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Inserting consumer failed, no generated key obtained.");
                }
                
                String insertMeterNumber = "INSERT INTO meter (cID, meterNumber, installationDate, meterStatus) VALUES(?,?,?,?)";
                try (PreparedStatement meterStatement = connection.prepareStatement(insertMeterNumber)) {
                    meterStatement.setInt(1, cID);
                    meterStatement.setString(2, meterNo);
                    meterStatement.setDate(3, new Date(2023, 9,03));
                    meterStatement.setInt(4, 1);

                    meterStatement.executeUpdate();
                }
                // Insert data into the address table
                String insertAddressSQL = "INSERT INTO address (cID, country, region, province, municipality, baranggay, purok, postalCode) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement addressStatement = connection.prepareStatement(insertAddressSQL)) {
                    addressStatement.setInt(1, cID);
                    addressStatement.setString(2, country);
                    addressStatement.setString(3, region);
                    addressStatement.setString(4, province);
                    addressStatement.setString(5, muni);
                    addressStatement.setString(6, brgy);
                    addressStatement.setString(7, purok);
                    addressStatement.setString(8, postalCode);

                    addressStatement.executeUpdate();
                }
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    public Consumer getSelectedConsumerByID(int id) throws SQLException{
        Consumer consumer = null;

        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT c.cID, c.cFName, c.cMName, c.cLName, c.cSuffix, c.cEmailAd, c.cContactNo, "
                        + "ad.country, ad.region, ad.province, ad.municipality, ad.baranggay, ad.purok, ad.postalCode, m.meterNumber, c.cStatus " +
                        "FROM conscessionaries c " +
                        "JOIN address ad ON c.cID = ad.cID " +
                        "JOIN meter m ON c.cID = m.cID " +
                        "WHERE c.cID = ?");) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                consumer = new Consumer(1,
                        rs.getInt("cID"), rs.getString("cFName"), 
                        rs.getString("cMName"), rs.getString("cLName"), 
                        rs.getString("cSuffix"), rs.getString("cContactNo"),
                        rs.getString("cEmailAd"), rs.getString("meterNumber"),
                        rs.getString("country"), rs.getString("region"), 
                        rs.getString("province"), rs.getString("municipality"),
                        rs.getString("baranggay"),rs.getString("purok"), 
                        rs.getString("postalCode"),rs.getString("cStatus"));
            }
        }
        return consumer;
    }

    public void updateConsumer(int id,
        String fName, String mName, String lName, 
        String suffix, String contactNo, String emailAd, 
        String meterNo, String country, String region,
        String province, String muni, 
        String brgy, String purok, String postalCode
    ) throws SQLException {
        try (Connection connection = getConnection()) {
            
            String updateConsumerSQL = "UPDATE conscessionaries c SET "
                    + "c.cFName = ?, "
                    + "c.cMName = ?, "
                    + "c.cLName = ?, "
                    + "c.cSuffix = ?, "
                    + "c.cContactNo = ?, "
                    + "c.cEmailAd = ? "
                    + "WHERE c.cID = ?";
            
            try (PreparedStatement updateStatement = connection.prepareStatement(updateConsumerSQL)) {
                updateStatement.setString(1, fName);
                updateStatement.setString(2, mName);
                updateStatement.setString(3, lName);
                updateStatement.setString(4, suffix);
                updateStatement.setString(5, contactNo);
                updateStatement.setString(6, emailAd);
                updateStatement.setInt(7, id);
                updateStatement.executeUpdate();
            }
            
            String updateConsumerAddressSQL = "UPDATE address a SET "
                    + "a.country = ?, "
                    + "a.region = ?, "
                    + "a.province = ?, "
                    + "a.municipality = ?, "
                    + "a.baranggay = ?, "
                    + "a.purok = ?, "
                    + "a.postalCode = ? "
                    + "WHERE a.cID = ?";
            
            try (PreparedStatement updateAddressStatement = connection.prepareStatement(updateConsumerAddressSQL)) {
                updateAddressStatement.setString(1, country);
                updateAddressStatement.setString(2, region);
                updateAddressStatement.setString(3, province);
                updateAddressStatement.setString(4, muni);
                updateAddressStatement.setString(5, brgy);
                updateAddressStatement.setString(6, purok);
                updateAddressStatement.setString(7, postalCode);
                updateAddressStatement.setInt(8, id);
                updateAddressStatement.executeUpdate();
            }
            
            String updateConsumerMeterNoSQL = "UPDATE meter m SET "
                    + "m.meterNumber = ? "
                    + "WHERE m.cID = ?";
            
            try (PreparedStatement updateMeterStatement = connection.prepareStatement(updateConsumerMeterNoSQL)) {
                updateMeterStatement.setString(1, meterNo);
                updateMeterStatement.setInt(2, id);
                updateMeterStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    
    public void deleteConsumer(int id) throws SQLException {
    try (Connection connection = getConnection();
         Statement statement = connection.createStatement()) {

        String query = "";  
        
        String checkID = "SELECT c.cID, c.cStatus FROM conscessionaries c WHERE c.cID = '" + id + "'";
        ResultSet conscessionaryID = statement.executeQuery(checkID);
       
        if (conscessionaryID.next()) {
            // The conscessionary with the provided cStatus value exists
            int currentStatus = conscessionaryID.getInt("cStatus");
            query = "UPDATE conscessionaries c SET c.cStatus = " + 2 + " WHERE c.cID = " + id;
        }
        if (!query.isEmpty()) {
            // Execute the query only if it's not empty
            statement.executeUpdate(query);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    }
}
    
    public void deactivateConsumer(int id) throws SQLException {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            String query = "";  
            String checkID = "SELECT c.cID, c.cStatus FROM conscessionaries c WHERE c.cID = '" + id + "'";
            ResultSet conscessionaryID = statement.executeQuery(checkID);

            if (conscessionaryID.next()) {
                int status = conscessionaryID.getInt("cStatus");
                status = status == 0 ? 1 : 0;
                query = "UPDATE conscessionaries c SET c.cStatus = " + status+ " WHERE c.cID = " + id;
            } 

            if (!query.isEmpty()) {
                statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }


   
}

