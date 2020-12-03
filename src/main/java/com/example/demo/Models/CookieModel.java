package com.example.demo.Models;

import java.util.Date;

public class CookieModel {
    private int cookieID;
    private String cookieValue;
    private Date createdAt;

    public CookieModel(int cookieID, String cookieValue, Date createdAt) {
        this.cookieID = cookieID;
        this.cookieValue = cookieValue;
        this.createdAt = createdAt;
    }

    public int getCookieID() {
        return cookieID;
    }

    public String getCookieValue() {
        return cookieValue;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
