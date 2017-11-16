package docler.pingapp.dao;

import java.io.Serializable;

public class TracertObj implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5803483061170934008L;

	private String hostName;

	private StringBuffer traceRoute;

	private String onTime;

	private String errorMessage;
	
	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public StringBuffer getTraceRoute() {
		return traceRoute;
	}

	public void setTraceRoute(StringBuffer traceRoute) {
		this.traceRoute = traceRoute;
	}

	public String getOnTime() {
		return onTime;
	}

	public void setOnTime(String onTime) {
		this.onTime = onTime;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	

}
