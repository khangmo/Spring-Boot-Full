package com.example.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import com.example.common.Constant;

public abstract class AbstractController {

	@Value("${spring.social.facebook.appId}")
	protected String facebookAppId;

	@Value("${spring.social.facebook.appSecret}")
	protected String facebookAppSecret;

	@Value("${environments.developer.facebook.access-token}")
	protected String facebookUrl;

	@Value("${environments.developer.facebook.request-url}")
	protected String requestUrl;

	@Value("${environments.root.url}")
	protected String rootUrl;
	
	@Value("${environments.developer.facebook.me}")
	protected String aboutMeOnfacebook;
	
	public Log log = LogFactory.getLog(this.getClass());
	
	public String getUser() {
		String name;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			name = auth.getName();
		} else {
			name = "";
		}
		return name;
	}

	public Collection<String> getAuthoritys() {
		Collection<String> roles = new ArrayList<String>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
			for (GrantedAuthority grantd : authorities) {
				roles.add(grantd.toString());
			}
		}
		return roles;
	}

	public void requestFacebook(String code, Model model) throws Exception {
		String url = new StringBuffer(facebookUrl).append("?").append(Constant.FACEBOOK.CLIENT_ID).append("=")
				.append(facebookAppId).append("&").append(Constant.FACEBOOK.REDIRECT_URI).append("=").append(rootUrl).append("oauth/facebook").append("&")
				.append(Constant.FACEBOOK.CLIENT_SECRET).append("=").append(facebookAppSecret).append("&")
				.append(Constant.FACEBOOK.CODE_RESPONSE).append("=").append(code).toString();
		
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        
        String getInfoUrl = new StringBuffer(aboutMeOnfacebook).append("?").append(EntityUtils.toString(httpEntity))
        		.append(Constant.FACEBOOK.ALL_PARAMETERS_TO_GET_INFO).toString();
        
        URL urlInfo = new URL(getInfoUrl);
        URLConnection urlConnection = urlInfo.openConnection();
        HttpURLConnection connection = null;
        if (urlConnection instanceof HttpURLConnection) {
            connection = (HttpURLConnection) urlConnection;
        } else {
            log.error("Please enter an HTTP URL.");
            throw new Exception();
        }
        
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuffer responseProfile = new StringBuffer();
        String current;
        while ((current = in.readLine()) != null) {
            responseProfile.append(current);
        }
        JSONObject json = new JSONObject(responseProfile.toString());   
        model.addAttribute("fullname", json.get("name"));
        model.addAttribute("picture", "https://graph.facebook.com/" + json.get("id") + "/picture?return_ssl_resources=1");
        model.addAttribute("gmail", json.get("email"));
	}
}
