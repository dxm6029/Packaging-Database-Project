public class PostalWorker {

    String name;
    String location;
    int workerID;

    public PostalWorker(String name, String location, int workerID){
        this.name = name;
        this.location = location;
        this.workerID = workerID;
    }

    public PostalWorker(String[] data){
        this.name = data[0];
        this.location = data[1];
        this.workerID = Integer.parseInt(data[2]);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getWorkerID() {
        return workerID;
    }

    public void setWorkerID(int workerID) {
        this.workerID = workerID;
    }
}
