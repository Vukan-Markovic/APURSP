package vukan.com.apursp.models;

public class ReportUser {
    private String reporterUserID;
    private String reportedUserID;

    public ReportUser() {
    }

    public ReportUser(String reporterUserID, String reportedUserID) {
        this.reporterUserID = reporterUserID;
        this.reportedUserID = reportedUserID;
    }

    public String getReporterUserID() {
        return reporterUserID;
    }

    public void setReporterUserID(String reporterUserID) {
        this.reporterUserID = reporterUserID;
    }

    public String getReportedUserID() {
        return reportedUserID;
    }

    public void setReportedUserID(String reportedUserID) {
        this.reportedUserID = reportedUserID;
    }
}