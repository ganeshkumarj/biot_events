/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovation.iot.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author BSINDIA
 */
public class DateUtil {
    
    private static final String format = "MM-dd-yyyy";
    
    public static String getDateString(){
        Date myDate = new Date();
        return new SimpleDateFormat(format).format(myDate);
    }
    
}