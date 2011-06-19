package org.jboss.seam.reports.openoffice.model;

public class Address {

    private String address;
    private String state;
    private String zip;

    public Address(String address, String state, String zip) {
        super();
        this.address = address;
        this.state = state;
        this.zip = zip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

}
