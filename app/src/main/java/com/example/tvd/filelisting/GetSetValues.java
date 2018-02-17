package com.example.tvd.filelisting;

/**
 * Created by TVD on 2/15/2018.
 */

public class GetSetValues {
    private String filename;
    private Integer filecount;
    private String ftpfilename;
    private boolean isSelected;

    public Integer getFilecount() {
        return filecount;
    }

    public void setFilecount(Integer filecount) {
        this.filecount = filecount;
    }

    public String getFtpfilename() {
        return ftpfilename;
    }

    public void setFtpfilename(String ftpfilename) {
        this.ftpfilename = ftpfilename;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getFilename() {
        return filename;


    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
