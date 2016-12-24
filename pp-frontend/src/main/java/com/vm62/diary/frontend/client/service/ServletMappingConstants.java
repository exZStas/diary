package com.vm62.diary.frontend.client.service;

/**
 *
 * Contains servlet names, url-mappings, relative-paths and servlet param-names used
 * to declare and make calls to various servlets in GWT client application.
 * These includes GWT-RPC service interfaces and servlet implementations,
 * login servlets, file upload servlets and so on.
 */
public abstract class ServletMappingConstants {

	/** relative path to GWT RPC service URL */
	public final static String GWT_MODULE = "frontend";

	// user service
	public final static String USER_PROFILE_SERVICE_NAME = "UserService";
	public final static String USER_PROFILE_SERVICE_RELATIVE_PATH = "userService";
	public final static String USER_PROFILE_SERVICE_URL = "/" + GWT_MODULE + "/" + USER_PROFILE_SERVICE_RELATIVE_PATH;

	//login service
	public final static String LOGIN_SERVICE_NAME = "LoginService";
	public final static String LOGIN_SERVICE_RELATIVE_PATH = "loginService";
	public final static String LOGIN_SERVICE_URL = "/" + GWT_MODULE + "/" + LOGIN_SERVICE_RELATIVE_PATH;

	//event service
	public final static String EVENT_SERVICE_NAME = "EventService";
	public final static String EVENT_SERVICE_RELATIVE_PATH = "eventService";
	public final static String EVENT_SERVICE_URL = "/" + GWT_MODULE + "/" + EVENT_SERVICE_RELATIVE_PATH;
}
