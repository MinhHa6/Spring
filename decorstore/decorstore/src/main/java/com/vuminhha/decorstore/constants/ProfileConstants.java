package com.vuminhha.decorstore.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ProfileConstants {
    // Views
    public static final String PROFILE_VIEW = "users/profile";

    // Redirects
    public static final String REDIRECT_LOGIN = "redirect:/login";
    public static final String REDIRECT_PROFILE_SUCCESS = "redirect:/profile?success=true";
    public static final String REDIRECT_PROFILE_PASSWORD_CHANGED = "redirect:/profile?passwordChanged=true";
    public static final String REDIRECT_HOME_PROFILE_ERROR = "redirect:/home?error=profile_load_failed";

    // Model attributes
    public static final String USER_ATTRIBUTE = "user";
    public static final String CUSTOMER_ATTRIBUTE = "customer";
    public static final String ERROR_ATTRIBUTE = "error";
    public static final String PASSWORD_ERROR = "passwordError";

    // Upload
    public static final String UPLOAD_DIR = "src/main/resources/static/uploads/";
}
