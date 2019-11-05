package Model;

public class Job {

    private String jobname,jobdetail,minimumcharge,jobimage,availability;
    private String jobid;

    public Job(String jobid, String jobname, String jobdetail, String minimumcharge, String jobimage, String availability) {
        this.jobid=jobid;
        this.jobname = jobname;
        this.jobdetail = jobdetail;
        this.minimumcharge = minimumcharge;
        this.jobimage = jobimage;
        this.availability = availability;
    }


    public Job(String jobid) {
        this.jobid = jobid;
    }

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }

    public String getJobdetail() {
        return jobdetail;
    }

    public void setJobdetail(String jobdetail) {
        this.jobdetail = jobdetail;
    }

    public String getMinimumcharge() {
        return minimumcharge;
    }

    public void setMinimumcharge(String minimumcharge) {
        this.minimumcharge = minimumcharge;
    }

    public String getJobimage() {
        return jobimage;
    }

    public void setJobimage(String jobimage) {
        this.jobimage = jobimage;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
