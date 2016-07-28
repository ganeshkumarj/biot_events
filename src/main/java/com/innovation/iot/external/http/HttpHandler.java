package com.innovation.iot.external.http;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpHandler {

	private static final Logger LOG = Logger.getLogger(HttpHandler.class);

	public String invoke(ExecutionContext context) {
		String response = "";
		if (HttpAction.GET.equals(context.action())) {
			response = getInvoker(context);
		} else if (HttpAction.POST.equals(context.action())) {
			response = postInvoker(context);
		}
		LOG.debug("<invoke> response from external app" + response);
		return response;
	}

	private String getInvoker(ExecutionContext context) {
		String response = "";
		try (CloseableHttpClient httpClient = HttpClients.createDefault();) {
			HttpGet get = new HttpGet(encodeUrl(constructUrl(context)));
			response = httpClient.execute(get, handleResponse());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	private String constructUrl(ExecutionContext context) {
		StringBuilder baseUrl = new StringBuilder(context.url());
		baseUrl.append("?");
		List<NameValuePair> pairs = context.parameters();
		for (NameValuePair pair : pairs) {
			baseUrl.append(pair.getName()).append("=").append(pair.getValue()).append("&");
		}
		String url = baseUrl.substring(0, baseUrl.length() - 1);
		LOG.debug("Gamification URL to hit" + url);
		return url;
	}

	private URI encodeUrl(String urlString) {
		URI uri = null;
		try {
			URL url = new URL(urlString);
			uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOG.debug("Gamification URI to hit" + uri.toString());
		return uri;
	}

	private String postInvoker(ExecutionContext context) {
		String response = "";
		try (CloseableHttpClient httpClient = HttpClients.createDefault();) {
			HttpPost post = new HttpPost(context.url());
			post.setHeader("Content-Type", "application/x-www-form-urlencoded");
			post.setEntity(new UrlEncodedFormEntity(context.parameters(), HTTP.UTF_8));
			response = httpClient.execute(post, handleResponse());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	private ResponseHandler<String> handleResponse() {
		return new ResponseHandler<String>() {
			private String result;

			@Override
			public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
				int status = response.getStatusLine().getStatusCode();
				System.out.println("Status code from response" + status);
				if (status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					result = EntityUtils.toString(entity);
					System.out.println("Response" + result);
				}
				return result;
			}
		};
	}

	public interface ExecutionContext {
		String url();

		List<NameValuePair> parameters();

		HttpAction action();
	}

	public enum HttpAction {
		POST, GET
	}

}
