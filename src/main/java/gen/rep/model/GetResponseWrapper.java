package gen.rep.model;

import org.apache.http.Header;
import org.apache.http.HttpStatus;

public class GetResponseWrapper {
	int status ;
	Header[] cookieHeaders ;
	String responseString;
	

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	
	public Header[] getCookieHeaders() {
		return cookieHeaders;
	}
	public void setCookieHeaders(Header[] cookieHeaders) {
		this.cookieHeaders = cookieHeaders;
	}
	
	public String getResponseString() {
		return responseString;
	}
	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}
	
	
}
