package com.bmincey.inet;

import java.io.Serializable;
import java.util.Date;

public class Status implements Serializable {

    public static final String INTERNET = "INTERNET";
    public static final String NETWORK = "NETWORK";
    public static final String UP = "UP";
    public static final String DOWN = "DOWN";

    private String networkType;
    private String status;
    private Date dateTime;

    /**
     *
     */
    public Status() {

    }

    /**
     *
     * @param networkType
     * @param status
     * @param dateTime
     */
    public Status(final String networkType, final String status, final Date dateTime) {
        this.setNetworkType(networkType);
        this.setStatus(status);
        this.setDateTime(dateTime);

    }

    /**
     *
     * @return
     */
    public String getNetworkType() {
        return networkType;
    }

    /**
     *
     * @param networkType
     */
    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    /**
     *
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public Date getDateTime() {
        return dateTime;
    }

    /**
     *
     * @param dateTime
     */
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Status{" +
                "networkType='" + networkType + '\'' +
                ", status='" + status + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }

    public static void main(String[] args) {
        Status status = new Status(Status.NETWORK,Status.UP,new java.util.Date());
        System.out.println(status);
    }
}
