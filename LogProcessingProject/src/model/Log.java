package model;

public class Log {
	private String ip;
	private String email;
	private String http;
	private String url;

	public Log(String ip, String email, String http, String url) {
		this.ip = ip;
		this.email = email;
		this.http = http;
		this.url = url;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Log that = (Log) obj;
		if (this.ip == null && that.ip != null) {
			return false;
		}
		if (this.email == null && that.email != null) {
			return false;
		}
		if (this.http == null && that.http != null) {
			return false;
		}
		if (this.url == null && that.url != null) {
			return false;
		}
		if (this.ip.equals(that.ip) && this.email.equals(that.email) && this.http.equals(that.http)
				&& this.url.equals(that.url)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hashCode = 1;
		hashCode = prime * hashCode + ((ip == null) ? 0 : ip.hashCode());
		hashCode = prime * hashCode + ((email == null) ? 0 : email.hashCode());
		hashCode = prime * hashCode + ((http == null) ? 0 : http.hashCode());
		hashCode = prime * hashCode + ((url == null) ? 0 : url.hashCode());
		return hashCode;
	}

	@Override
	public String toString() {
		return ip + " " + email + " " + http + " " + url;
	}

	public String getLog() {
		return ip + " " + email + " " + http + " " + url;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHttp() {
		return http;
	}

	public void setHttp(String http) {
		this.http = http;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
