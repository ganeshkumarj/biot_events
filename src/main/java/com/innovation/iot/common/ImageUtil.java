/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innovation.iot.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 *
 * @author BSINDIA
 */
public class ImageUtil {
    
    private static final ResourceBundle bundle = ResourceBundle.getBundle("Admin");
    private static final ImageUtil instance = new ImageUtil();
    
    public static ImageUtil getInstance() {
		return instance;
	}
    
    public String handleImage(String base64Coded,String userId) {
		String fileName = null;
		if (base64Coded != null && !"".equals(base64Coded)) {
			//String imageType = base64Coded.substring(0, base64Coded.indexOf(";base64"));
			//imageType = imageType.substring(imageType.indexOf("/") + 1);
			//String imageContent = base64Coded.substring(base64Coded.indexOf(",") + 1);
			//String id = UUID.randomUUID().toString();
			fileName = "users/" + userId + "." + "jpeg";
			try (FileOutputStream fos = new FileOutputStream(getImageStorageLocation() + fileName);) {
				fos.write(new sun.misc.BASE64Decoder().decodeBuffer(base64Coded));
				fos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		} 
		return fileName;
	}
    
    private String getImageStorageLocation() {
		return bundle.getString("message.upload.directory");
	}
    
}
