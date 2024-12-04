package com.example.tiffincart.DAO;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "consultant_bookings")
public class ConsultantBooking {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String consultantName;
    private String consultantDetails;
    private String userName;
    private String email;
    private String meetingDate;

    public ConsultantBooking(String consultantName, String consultantDetails, String userName, String email, String meetingDate) {
        this.consultantName = consultantName;
        this.consultantDetails = consultantDetails;
        this.userName = userName;
        this.email = email;
        this.meetingDate = meetingDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConsultantName() {
        return consultantName;
    }

    public String getConsultantDetails() {
        return consultantDetails;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getMeetingDate() {
        return meetingDate;
    }
}
