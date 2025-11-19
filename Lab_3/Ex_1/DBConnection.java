
public class DBConnection{
    private int id;
    private int clientId;

    public DBConnection(int id) {
        this.id = id;
        this.clientId = -1;
    }

    public synchronized int getId() {
        return id;
    }

    public synchronized boolean isInUse() {
        return clientId != -1;
    }

    public synchronized void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public synchronized int getClientId() {
        return clientId;
    }
}
