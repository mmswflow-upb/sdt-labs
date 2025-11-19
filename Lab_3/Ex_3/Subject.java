package Ex_3;

public interface Subject {
    void addFollower(Observer o);
    void removeFollower(Observer o);
    void notifyFollowers(String status);
}
