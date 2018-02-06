/**
 * 
 */
package gen.rep.services;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import gen.rep.config.AlmConstants;
import gen.rep.model.Defect;
import gen.rep.model.DefectModel;
import gen.rep.model.GetResponseWrapper;

/**
 * @author Vimal V
 *
 */
@Service
public class AlmServices {
	// Below variables are related to session management(Cookies used).
	private static String cookie;

	/**
	 * Alm Authentication
	 */
	public static void authenticate() {
		String uri = getApi(AlmConstants.ALM_SIGN_IN_RESOURCE);
		System.out.println(uri);
		Map<String, String> authHeader = new HashMap<>();
		authHeader.put("Authorization", "Basic "+ getAuthorizationHeader());
		GetResponseWrapper resp = executeGetRequest(uri, authHeader, null);
		if(resp.getStatus() == HttpStatus.SC_OK) {
			StringBuffer bf = new StringBuffer();
			for(Header cookie : resp.getCookieHeaders()) {
				bf.append(cookie.getValue()).append(";");
			}
			cookie = bf.toString();
		}
		//

	}

	
	/**
	 * Return defects. Should be authenticated before calling this method
	 * Defects are filtered based on owner and fields (eg : Give user name as owner based on which defects will be filtered)
	 * More fields can be added based on requirement. Check API definitions to see other field definition
	 * @return
	 */
	public static List<Defect> getDefects() {
		String uri = getApi(AlmConstants.ALM_DEFECTS_RESOURCE);
		Map<String, String> params = new HashMap<>();
		params.put("query", "\"owner = 'user.name' || owner = 'user.name2'\"");
		params.put("fields", "priority,owner,id,name,severity,status");
		GetResponseWrapper resp = executeGetRequest(uri, null, params);
		if (resp.getStatus() == HttpStatus.SC_OK) {
			try {

				ObjectMapper mapper = new ObjectMapper();
				DefectModel defects = mapper.readValue(resp.getResponseString(), DefectModel.class);
				return defects.getData();

			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Return resouce URI
	 * @param resource
	 * @return
	 */
	private static String getApi(String resource) {
		return AlmConstants.ALM_ENDPOINT + resource;
	}

	
	/**
	 * Execute Get Request
	 * @param uri
	 * @param headers
	 * @param params
	 * @return
	 */
	private static GetResponseWrapper executeGetRequest(String uri, Map<String, String> headers, Map<String, String> params) {
		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
			
			URIBuilder builder = new URIBuilder(uri);
			if(params != null) {
				for (Map.Entry<String, String> p : params.entrySet()) {
					builder.setParameter(p.getKey(), p.getValue());
				}
			}
			HttpGet getRequest = new HttpGet(builder.build());
			if (cookie != null) {
				getRequest.addHeader("Cookie", cookie);
			}
			if (headers != null) {
				for (Map.Entry<String, String> h : headers.entrySet()) {
					getRequest.addHeader(h.getKey(), h.getValue());
				}
			}			
			HttpResponse response = client.execute(getRequest);
			
			//Response to be set to wrapper object
			GetResponseWrapper respWrp = new GetResponseWrapper();
			respWrp.setCookieHeaders(response.getHeaders("Set-Cookie"));
			respWrp.setStatus(response.getStatusLine().getStatusCode());
			respWrp.setResponseString(EntityUtils.toString(response.getEntity(), "UTF-8"));
			
			return respWrp;
		} catch (Exception ex) {
			System.out.println("Error Occured in GET request");
			ex.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 
	 * @return
	 */
	private static String getAuthorizationHeader() {
		return Base64.getEncoder().encodeToString((AlmConstants.ALM_USER+":"+AlmConstants.ALM_PASSWORD).getBytes());
	}
	
}
