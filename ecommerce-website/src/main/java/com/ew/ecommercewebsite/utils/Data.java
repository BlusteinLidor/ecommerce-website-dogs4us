package com.ew.ecommercewebsite.utils;

import java.util.List;

/**
 * Utility class containing constant values used throughout the application.
 */
public class Data {

    /**
     * Number of minutes in one hour
     */
    public static final int MINUTES_IN_HOUR = 60;
    /**
     * Number of seconds in one minute
     */
    public static final int SECONDS_IN_MINUTE = 60;
    /**
     * Number of milliseconds in one second
     */
    public static final int SECOND = 1000;
    /**
     * Token expiration time in hours
     */
    public static final int EXPIRATION_10_HOURS = 10;
    /**
     * Index position of the token in the authorization header
     */
    public static final int AUTH_HEADER_TOKEN_INDEX = 7;
    /**
     * Directory path for storing images
     */
    public static final String IMG_DIR = "assets/img";
    /**
     * List of available product sizes
     */
    public static final List<String> SIZE_LIST = List.of("small", "medium", "large");
    /**
     * List of available product colors
     */
    public static final List<String> COLOR_LIST = List.of("black", "white", "brown", "red", "blue", "green");
}
