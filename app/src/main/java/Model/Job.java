package Model;

public class Job {

    private String jobname,jobdetail,minimumcharge,jobimage;

    public Job(String jobname, String jobdetail, String minimumcharge, String jobimage) {
        this.jobname = jobname;
        this.jobdetail = jobdetail;
        this.minimumcharge = minimumcharge;
        this.jobimage = jobimage;
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


}
