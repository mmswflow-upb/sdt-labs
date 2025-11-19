
public class Client extends Thread {
    private int clientId;
    private ConnectionsPool pool;

    public Client(int clientId, ConnectionsPool pool) {
        this.clientId = clientId;
        this.pool = pool;
    }

    @Override
    public void run() {
        while (true){
            try {
                DBConnection connection = pool.acquireConnection(clientId);

                //Delay to simulate work with connection in case client acquires it
                Thread.sleep((long) (Math.random() * 4000));

                if (connection != null) {
                    System.out.println("Client " + clientId + " running queries on connection " + connection.getId());
                    pool.releaseConnection(connection);
                    Thread.sleep((long) (Math.random() * 2000));

                } else {
                    System.out.println("Client " + clientId + " could not acquire a connection.");
                }

            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        
    }
}