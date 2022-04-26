package dev.simplesolution.generateqr.doa;

import java.io.Serializable;

public class TrackedEntity  implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private String displayName;
	private String value;
	private String trackedEntityInstance;
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getDisplayName() {
		return displayName;
	}
	public String getValue() {
		return value;
	}
	public String getTrackedEntityInstance() {
		return trackedEntityInstance;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setTrackedEntityInstance(String trackedEntityInstance) {
		this.trackedEntityInstance = trackedEntityInstance;
	}
	
	@Override
	public String toString() {
		return "TrackedEntity [displayName=" + displayName + ", value=" + value + ", trackedEntityInstance="
				+ trackedEntityInstance + "]";
	}
	

}
