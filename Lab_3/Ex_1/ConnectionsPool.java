package Ex_1;

import java.util.ArrayList;

public class ConnectionsPool {
    private static ConnectionsPool instance;
    private ArrayList<DBConnection> connections;
    private int maxConnections;

    private ConnectionsPool(int maxConnections) {
        this.connections = new ArrayList<>();
        this.maxConnections = maxConnections;
    }

    public static synchronized ConnectionsPool getInstance(int maxConnections) {
        if (instance == null) {
            instance = new ConnectionsPool(maxConnections);
        }
        return instance;
    }

    public synchronized DBConnection acquireConnection(int clientId) {
        if (maxConnections > 0) {
            maxConnections--;
            DBConnection newConnection = new DBConnection(5-maxConnections);
            newConnection.setClientId(clientId);
            System.out.println("New connection " + newConnection.getId() + " created for client " + clientId + ".");
            connections.add(newConnection);
            return newConnection;
        }
        
        DBConnection availableConnection = null;
        for (DBConnection connection : connections) {
            if (!connection.isInUse()) {
                availableConnection = connection;
                break;
            }
        }
        if (availableConnection != null) {
            availableConnection.setClientId(clientId);
            System.out.println("Connection " + availableConnection.getId() + " acquired from pool by client " + clientId + ".");
        }
        return availableConnection;
    }

    public synchronized void releaseConnection(DBConnection connection) {
        System.out.println("Connection " + connection.getId() + " released back to pool by client " + connection.getClientId() + ".");
        connection.setClientId(-1);
    }
}