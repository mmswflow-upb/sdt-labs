package Ex_3;

public class SocialNetworkDemo {

    public static void main(String[] args) {

        User user1 = new User("User1");
        User user2 = new User("User2");
        User user3 = new User("User3");
        User user4 = new User("User4");
        User user5 = new User("User5");


        user1.addFollower(user2);
        user1.addFollower(user3);

        user2.addFollower(user1);
        user2.addFollower(user5);

        user3.addFollower(user1);
        user3.addFollower(user2);
        user3.addFollower(user4);

        user5.addFollower(user3);
        user5.addFollower(user4);


        Thread t1 = new Thread(user1);
        Thread t2 = new Thread(user2);
        Thread t3 = new Thread(user3);
        Thread t4 = new Thread(user4);
        Thread t5 = new Thread(user5);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        // Wait for all threads to finish, so program doesn't exit early
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Simulation finished.");
    }
}
