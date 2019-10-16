package Model;

public class Booking {
    private String bookid, jobname,jobtime,jobdate,jobproblem,userid,confirmation,completed;


    public Booking(String bookid, String confirmation) {
        this.bookid = bookid;
        this.confirmation = confirmation;
    }

    public Booking(String bookid, String jobname, String jobtime, String jobdate, String jobproblem, String userid, String confirmation, String completed) {
        this.bookid = bookid;
        this.jobname = jobname;
        this.jobtime = jobtime;
        this.jobdate = jobdate;
        this.jobproblem = jobproblem;
        this.userid = userid;
        this.confirmation = confirmation;
        this.completed = completed;
    }

    public Booking(String jobtime, String jobdate, String jobproblem, String bookid) {
        this.jobtime = jobtime;
        this.jobdate = jobdate;
        this.jobproblem = jobproblem;
        this.bookid = bookid;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public Booking(String userid) {
        this.userid = userid;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }

    public String getJobtime() {
        return jobtime;
    }

    public void setJobtime(String jobtime) {
        this.jobtime = jobtime;
    }

    public String getJobdate() {
        return jobdate;
    }

    public void setJobdate(String jobdate) {
        this.jobdate = jobdate;
    }

    public String getJobproblem() {
        return jobproblem;
    }

    public void setJobproblem(String jobproblem) {
        this.jobproblem = jobproblem;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
