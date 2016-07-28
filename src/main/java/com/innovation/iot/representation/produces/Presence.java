package com.innovation.iot.representation.produces;

import com.innovation.iot.domain.CurrentLocation;
import com.innovation.iot.domain.DailyLog;
import com.innovation.iot.representation.Status;
import java.util.List;

public class Presence {

	private Status status;
	private DailyLog log;
        private List<CurrentLocation> currentLocation;
        private Long netMinutes;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public DailyLog getLog() {
		return log;
	}

	public void setLog(DailyLog log) {
		this.log = log;
	}
        
        public List<CurrentLocation> getCurrentLocation() {
            return currentLocation;
        }

        public void setCurrentLocation(List<CurrentLocation> currentLocation) {
            this.currentLocation = currentLocation;
        }
        
        public Long getNetMinutes() {
            return netMinutes;
        }

        public void setNetMinutes(Long netMinutes) {
         this.netMinutes = netMinutes;
        }

        @Override
        public String toString() {
            return "Presence{" + "status=" + status + ", log=" + log + ", currentLocation=" + currentLocation + ", netMinutes=" + netMinutes + '}';
        }

    }
