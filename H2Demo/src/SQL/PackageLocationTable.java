package SQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PackageLocationTable {

    public static void createPackageLocationTable(Connection conn){
        try {
            String query = "CREATE TABLE IF NOT EXISTS packagelocations(packageId INT, location VARCHAR(255),deliveryTime VARCHAR(255))";

            /**
             * Create a query and execute
             */
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addPackageLocation(Connection conn, int packageID, String location, String deliveryTime){

        /**
         * SQL insert statement
         */
        String query = String.format("INSERT INTO packageLocations "
                        + "VALUES(%d, \'%s\', \'%s\');",
                packageID, location, deliveryTime );
        try {
            /**
             * create and execute the query
             */
            //System.out.println(query);
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static String getTrackingHistory(int packageId, Connection conn) {
        ArrayList<PackageLocation> locations = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM packageLocations WHERE packageId = " + packageId;
            ResultSet results = stmt.executeQuery(query);

            while (results.next()) {
                locations.add(new PackageLocation(results.getInt(1), results.getString(2),
                        results.getString(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        locations.sort((packageLocation1, packageLocation2) -> packageLocation1.compareTo(packageLocation2));
        StringBuilder s = new StringBuilder("Tracking History:\n");

        for (PackageLocation p : locations) {
            s.append("Package checked in at " + p.getLocation() + " on " + p.getTimestamp() + ".\n");
            s.append("Package sent into transit.\n");
        }

        return s.toString();
    }
}
