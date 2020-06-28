package com.devrogar.utility.enums;

import com.devrogar.utility.config.SystemProperties;

/**
 * Property key enums, which are used by {@link SystemProperties}
 * 
 * @author Rohit Garlapati
 *
 */
public enum PropertyKey {

	APP_SERVER_PORT("server.port"), 
	ITEM_ID("graph.files.parent-item-id"),
	CLIENT_ID("graph.app.client-id"),
	CLIENT_SECRET("graph.app.client-secret"),
	SCOPES("graph.app.scopes"),
	REDIRECT_URI("graph.app.redirect-uri");
	
	private String key;
	
	private PropertyKey(String key) {
		this.key = key;
	}

	public String key() {
		return key;
	}
	
}
