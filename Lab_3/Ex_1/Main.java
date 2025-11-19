package Ex_1;

public class Main {
    public static void main(String[] args) {
        // Initialize connection pool with max 5 connections
        ConnectionsPool pool = ConnectionsPool.getInstance(5);

        // Create and start 8 client threads
        for (int i = 1; i <= 8; i++) {
            Client client = new Client(i, pool);
            client.start();
        }
    }
}
