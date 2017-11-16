package docler.pingapp.dao;

import java.io.Serializable;

public class ICMPObj implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -256706004120508214L;

	private String hostName;

	private String pingOnTime;

	private String packetsSent;

	private String packetsRecieved;

	private String lostPackets;

	private String maxTripTime;

	private String minTripTime;

	private String avgTripTime;

	private StringBuffer overAllData;

	private String errorMessage;

	public String getPingOnTime() {
		return pingOnTime;
	}

	public void setPingOnTime(String pingOnTime) {
		this.pingOnTime = pingOnTime;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPacketsSent() {
		return packetsSent;
	}

	public void setPacketsSent(String packetsSent) {
		this.packetsSent = packetsSent;
	}

	public String getPacketsRecieved() {
		return packetsRecieved;
	}

	public void setPacketsRecieved(String packetsRecieved) {
		this.packetsRecieved = packetsRecieved;
	}

	public StringBuffer getOverAllData() {
		return overAllData;
	}

	public void setOverAllData(StringBuffer overAllData) {
		this.overAllData = overAllData;
	}

	public String getMaxTripTime() {
		return maxTripTime;
	}

	public void setMaxTripTime(String maxTripTime) {
		this.maxTripTime = maxTripTime;
	}

	public String getMinTripTime() {
		return minTripTime;
	}

	public void setMinTripTime(String minTripTime) {
		this.minTripTime = minTripTime;
	}

	public String getAvgTripTime() {
		return avgTripTime;
	}

	public void setAvgTripTime(String avgTripTime) {
		this.avgTripTime = avgTripTime;
	}

	public String getLostPackets() {
		return lostPackets;
	}

	public void setLostPackets(String lostPackets) {
		this.lostPackets = lostPackets;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
