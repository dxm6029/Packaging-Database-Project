package SQL;

import java.sql.Connection;
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
        String query = String.format("INSERT INTO packages "
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
}
