/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovation.iot.domain;

import java.sql.Timestamp;

/**
 *
 * @author BSINDIA
 */

public class CurrentLocation {
    
    private int id;
    private String userCode;
    private Timestamp checkIn;
    private Timestamp checkOut;
    private Long totalMinutes;
    
    public CurrentLocation(int id, String userCode, Timestamp checkIn, Timestamp checkOut) {
        this.id = id;
        this.userCode = userCode;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalMinutes = calculateMinutes();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Timestamp getCheckIn() {
        return checkIn;
    }
 
    public void setCheckIn(Timestamp checkIn) {
        this.checkIn = checkIn;
    }

    public Timestamp getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Timestamp checkOut) {
        this.checkOut = checkOut;
    }
    
    private Long calculateMinutes(){
            Long minutes =0l;
            if(checkIn != null &&  checkOut != null){
                long checkInMilliSeconds = checkIn.getTime();
                long checkOutMilliSeconds = checkOut.getTime();
                minutes =  (checkOutMilliSeconds - checkInMilliSeconds)/(60*1000);
            }
            return minutes;
        }
    
    public Long getTotalMinutes() {
        return totalMinutes;
    }

    @Override
    public String toString() {
        return "CurrentLocation{" + "id=" + id + ", userCode=" + userCode + ", checkIn=" + checkIn + ", checkOut=" + checkOut + ", totalMinutes=" + totalMinutes + '}';
    }
}


