package com.innovation.iot.domain;

import java.sql.Timestamp;

public class DailyLog {

	private int id;
	private User user;
	private Timestamp firstIn;
	private Timestamp lastOut;
        private Long grossMinutes;
         
	public DailyLog(int id, String user, Timestamp firstIn, Timestamp lastOut) {
		this.id = id;
		this.user = new User( user );
		this.firstIn = firstIn;
		this.lastOut = lastOut;
                this.grossMinutes = calculateMinutes();
                
	}
        private Long calculateMinutes(){
            Long minutes =0l;
            if(firstIn != null &&  lastOut != null){
                long firstInMilliSeconds = firstIn.getTime();
                long lastInMilliSeconds = lastOut.getTime();
                minutes =  (lastInMilliSeconds - firstInMilliSeconds)/(60*1000);
            }
            return minutes;
        }
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getFirstIn() {
		return firstIn;
	}

	public void setFirstIn(Timestamp firstIn) {
		this.firstIn = firstIn;
	}

	public Timestamp getLastOut() {
		return lastOut;
	}

	public void setLastOut(Timestamp lastOut) {
		this.lastOut = lastOut;
	}
        
        public Long getTotalMinutes() {
        return grossMinutes;
        }

        public void setTotalMinutes(Long totalMinutes) {
            this.grossMinutes = totalMinutes;
        }
           

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DailyLog [id=");
		builder.append(id);
		builder.append(", user=");
		builder.append(user);
		builder.append(", firstIn=");
		builder.append(firstIn);
		builder.append(", lastOut=");
		builder.append(lastOut);
		builder.append("]");
		return builder.toString();
	}
}
