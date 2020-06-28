package com.devrogar.utility.msft.graph.service;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devrogar.utility.config.SystemProperties;
import com.devrogar.utility.enums.PropertyKey;
import com.microsoft.graph.auth.AuthConstants;
import com.microsoft.graph.auth.confidentialClient.AuthorizationCodeProvider;
import com.microsoft.graph.auth.enums.NationalCloud;
import com.microsoft.graph.authentication.IAuthenticationProvider;
import com.microsoft.graph.concurrency.ICallback;
import com.microsoft.graph.core.ClientException;
import com.microsoft.graph.models.extensions.Drive;
import com.microsoft.graph.models.extensions.IGraphServiceClient;
import com.microsoft.graph.requests.extensions.GraphServiceClient;

/**
 * Microsoft Graph Service APIs - Onedrive file upload
 * 
 * @author Rohit G
 *
 */
@Service
public class GraphService {

	private static final Logger logger = LoggerFactory.getLogger(GraphService.class);

	@Autowired
	private SystemProperties systemProperties;

	//private static final String GRAPH_DEFAULT_SCOPE = "https://graph.microsoft.com/.default";

	private IGraphServiceClient graphClient;

	private IAuthenticationProvider createAuthenticationProvider(String authorization_code) {
		AuthorizationCodeProvider authProvider = new AuthorizationCodeProvider(
				systemProperties.get(PropertyKey.CLIENT_ID),
				Arrays.asList(""),
				authorization_code,
				systemProperties.get(PropertyKey.REDIRECT_URI),
				NationalCloud.Global,
				AuthConstants.Tenants.Consumers,
				systemProperties.get(PropertyKey.CLIENT_SECRET));
		return authProvider;
	}

	public String createGraphClient(String authorization_code) {
		try {
			this.graphClient =  GraphServiceClient
					.builder()
					.authenticationProvider(createAuthenticationProvider(authorization_code))
					.buildClient();
		} catch (Exception e) {
			logger.error("Exception occurred while creating graph client, {}", e.getMessage());
			return "Failed";
		}
		return "Success";
	}

	public void test() {
		this.graphClient
		.me()
		.drive()
		.buildRequest()
		.get(new ICallback<Drive>() {
			@Override
			public void success(final Drive result) {
				System.out.println("Found Drive " + result.id);
			}
			@Override
			public void failure(ClientException ex) {
				logger.error("error, {}",ex.getMessage());
			}
		});
	}

	public IGraphServiceClient getGraphClient() {
		return graphClient;
	}

}
