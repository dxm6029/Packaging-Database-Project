package SQL;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class PackageLocation implements Comparable<PackageLocation>{

    int packageID;
    String location;
    String timestamp;

    public PackageLocation(int packageID, String location, String timestamp){
        this.packageID = packageID;
        this.location = location;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "PackageLocation{" +
                "packageID=" + packageID +
                ", location='" + location + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getPackageID() {
        return packageID;
    }

    public void setPackageID(int packageID) {
        this.packageID = packageID;
    }

    @Override
    public int compareTo(PackageLocation other) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        try {
            Date thisDate = dateFormat.parse(timestamp);
            Date otherDate = dateFormat.parse(other.getTimestamp());

            return thisDate.compareTo(otherDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return -100;
    }
}
