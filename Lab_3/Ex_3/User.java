package Ex_3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class User implements Observer, Subject, Runnable {

    private final String username;
    private final List<Observer> followers = new ArrayList<>();
    private final Random random = new Random();

    private static final String[] STATUS_TEMPLATES = {
        "Deploying a new microservice today.",
        "Refactoring legacy code, one commit at a time.",
        "Pushing a hotfix to production.",
        "Shipping a new feature this sprint.",
        "Debugging a tricky race condition.",
        "Pair programming on a core service.",
        "Learning more about distributed systems.",
        "Optimizing query performance on our API.",
        "Writing tests before touching the code.",
        "Reviewing pull requests with the team.",
        "Experimenting with a new tech stack.",
        "Migrating services to the cloud.",
        "Automating another boring manual task.",
        "Improving our CI/CD pipeline.",
        "Documenting today’s architecture decisions."
    };


    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }


    @Override
    public void addFollower(Observer o) {
        if (o == null) return;
        if (!followers.contains(o)) {
            followers.add(o);
        }
    }

    @Override
    public void removeFollower(Observer o) {
        followers.remove(o);
    }

    @Override
    public void notifyFollowers(String status) {
        for (Observer follower : followers) {
            follower.update(this, status);
        }
    }


    @Override
    public void update(User source, String status) {
        System.out.println(this.username + " was notified that " +
                source.getUsername() + " posted the status '" +
                status + "'");
    }


    public void postStatus(String status) {
        System.out.println(username + " posted: '" + status + "'");
        notifyFollowers(status);
    }


    @Override
    public void run() {
        int posts = 5; // how many statuses the user will post
        for (int i = 0; i < posts; i++) {
            String status = STATUS_TEMPLATES[random.nextInt(STATUS_TEMPLATES.length)];
            postStatus(status);
            try {
                Thread.sleep(500 + random.nextInt(1500));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}

