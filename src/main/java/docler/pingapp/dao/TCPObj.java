package docler.pingapp.dao;

import java.io.Serializable;

public class TCPObj {
	
	private String hostName;
	
	private String url;

	private String responseTime;

	private String responseCode;

	private String errorMessage;
	
	private String tcptime;
	
	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getTcptime() {
		return tcptime;
	}

	public void setTcptime(String tcptime) {
		this.tcptime = tcptime;
	}

	
}

