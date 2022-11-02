package com.mohammed.mosa.eg.egsim.objects;

import android.view.View;

import java.util.Objects;

public class USSD implements Comparable<USSD> {
    private String code;
    private String job;
    private int image;
    private View.OnClickListener listener;

    public USSD() {
    }

    public USSD(String code, String job, int image, View.OnClickListener listener) {
        this.code =  code != null? code: "";
        this.job = job != null? job: "";
        this.listener = listener;
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public View.OnClickListener getListener() {
        return listener;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        USSD ussd = (USSD) o;
        return image == ussd.image && code.equals(ussd.code) && job.equals(ussd.job) && listener.equals(ussd.listener);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, job, image, listener);
    }

    @Override
    public int compareTo(USSD ussd) {
        int jobCompare = getJob().compareTo(ussd.getJob());
        if(jobCompare != 0)
            return jobCompare;
        else
            return getCode().compareTo(ussd.getCode());
    }
}
