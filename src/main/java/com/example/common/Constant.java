package com.example.common;

public class Constant {

	public interface MESSAGE {
		public static String CREATE_USER_SUCCESS = "Create User Success.";
		public static String CREATE_USER_FAIL = "Create User Fail! Please contact your Admin...";

		public static String INFO_UPDATE_ERROR = "The current password or new password is empty";
		public static String PASSWORD_DID_NOT_MATCH = "Passwords do not match";
		public static String UPDATE_SUCCESS = "Update success";
		public static String PASSWORD_INCORRECT = "Password is incorrect";
	}

	public interface PAGING {
		public static int SIZE_ON_PAGE = 10;
	}

	public interface FACEBOOK {
		static final String CLIENT_ID = "client_id";
		static final String REDIRECT_URI = "redirect_uri";
		static final String CLIENT_SECRET = "client_secret";
		static final String CODE_RESPONSE = "code";
		static final String ALL_PARAMETERS_TO_GET_INFO = "&debug=all&fields=id,name,friends,email&format=json&method=get&pretty=0&suppress_http_code=1";
	}
	
	public interface ROLES {
		public static String ROLE_ADMIN = "ADMIN";
		public static String ROLE_USER = "USER";
	}
	
	public interface UNIT_TEST {
		public static String USER_TEST = "user";
		public static String PASSWORD_TEST = "12345678a@";
	}
}
