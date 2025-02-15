package SpringTest.ds_2024.entity;
import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int notificationId;

    @Column
    private String message;

    @Column
    private LocalDate notificationDate;

    public Notification(String message, LocalDate notificationDate) {
        this.message = message;
        this.notificationDate = notificationDate;
    }

    public Notification() {
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(LocalDate notificationDate) {
        this.notificationDate = notificationDate;
    }

    // Method to send a notification
    public void sendNotification() {
        System.out.println("Sending Notification:");
        System.out.println("ID: " + notificationId);
        System.out.println("Message: " + message);
        System.out.println("Date: " + notificationDate);
    }

    @Override
    public String toString() {
        return "NotificationService{" +
                "notificationId=" + notificationId +
                ", message='" + message + '\'' +
                ", notificationDate=" + notificationDate +
                '}';
    }
}
