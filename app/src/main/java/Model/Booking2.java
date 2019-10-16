package Model;

public class Booking2 {
    private String bookid,completed;

    public Booking2(String bookid, String completed) {
        this.bookid = bookid;
        this.completed = completed;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }
}

