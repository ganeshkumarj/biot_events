package com.innovation.iot.representation.consumes;

public class Register {

	private String userCode;
	private String userDeviceId;
	private String deviceId;
        private String userName;
        private String password;
        private String imgByte64Code; 

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserDeviceId() {
		return userDeviceId;
	}

	public void setUserDeviceId(String userDeviceId) {
		this.userDeviceId = userDeviceId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
        
        public String getImgByte64Code() {
            return imgByte64Code;
        }

        public void setImgByte64Code(String imgByte64Code) {
            this.imgByte64Code = imgByte64Code;
        }

        @Override
        public String toString() {
            return "Register{" + "userCode=" + userCode + ", userDeviceId=" + userDeviceId + ", deviceId=" + deviceId + ", userName=" + userName + ", password=" + password + ", imgByte64Code=" + imgByte64Code + '}';
        }
}
