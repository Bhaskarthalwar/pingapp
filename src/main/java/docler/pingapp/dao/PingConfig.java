package docler.pingapp.dao;

public class PingConfig {

	private String hostNameX;
	private String hostNameY;
	private String urlX;
	private String urlY;
	private int icmpPingDelay;
	private int tcpPingDelay;
	private int tcpMaxResponseTime;
	private int tracertDelay;
	private String reportUrl;
	private int threadPoolSize;
	private String icmplogFile;
	private String tcplogFile;
	private String tracelogFile;

	public String getHostNameX() {
		return hostNameX;
	}

	public void setHostNameX(String hostNameX) {
		this.hostNameX = hostNameX;
	}

	public String getHostNameY() {
		return hostNameY;
	}

	public void setHostNameY(String hostNameY) {
		this.hostNameY = hostNameY;
	}

	public String getUrlX() {
		return urlX;
	}

	public void setUrlX(String urlX) {
		this.urlX = urlX;
	}

	public String getUrlY() {
		return urlY;
	}

	public void setUrlY(String urlY) {
		this.urlY = urlY;
	}

	public int getIcmpPingDelay() {
		return icmpPingDelay;
	}

	public void setIcmpPingDelay(int icmpPingDelay) {
		this.icmpPingDelay = icmpPingDelay;
	}

	public int getTcpPingDelay() {
		return tcpPingDelay;
	}

	public void setTcpPingDelay(int tcpPingDelay) {
		this.tcpPingDelay = tcpPingDelay;
	}

	public int getTcpMaxResponseTime() {
		return tcpMaxResponseTime;
	}

	public void setTcpMaxResponseTime(int tcpMaxResponseTime) {
		this.tcpMaxResponseTime = tcpMaxResponseTime;
	}

	public int getTracertDelay() {
		return tracertDelay;
	}

	public void setTracertDelay(int tracertDelay) {
		this.tracertDelay = tracertDelay;
	}

	public String getReportUrl() {
		return reportUrl;
	}

	public void setReportUrl(String reportUrl) {
		this.reportUrl = reportUrl;
	}

	public int getThreadPoolSize() {
		return threadPoolSize;
	}

	public void setThreadPoolSize(int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	public String getIcmplogFile() {
		return icmplogFile;
	}

	public void setIcmplogFile(String icmplogFile) {
		this.icmplogFile = icmplogFile;
	}

	public String getTcplogFile() {
		return tcplogFile;
	}

	public void setTcplogFile(String tcplogFile) {
		this.tcplogFile = tcplogFile;
	}

	public String getTracelogFile() {
		return tracelogFile;
	}

	public void setTracelogFile(String tracelogFile) {
		this.tracelogFile = tracelogFile;
	}

	
}
