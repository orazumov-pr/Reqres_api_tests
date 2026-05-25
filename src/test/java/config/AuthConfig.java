package config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class AuthConfig {
    private static final Config config = ConfigFactory.load();
    private static final AuthConfig instance = new AuthConfig();

    private final String authEmail;
    private final String authPassword;
    private final String authUndefinedEmail;
    private final String authUndefinedPassword;

    private AuthConfig() {
        this.authEmail = config.getString("auth.email");
        this.authPassword = config.getString("auth.password");
        this.authUndefinedEmail = config.getString("auth.undefinedEmail");
        this.authUndefinedPassword = config.getString("auth.undefinedPassword");
    }

    public static AuthConfig getInstance() {
        return instance;
    }

    public String authEmail() {
        return authEmail;
    }

    public String authPassword() {
        return authPassword;
    }

    public String authUndefinedEmail() {
        return authUndefinedEmail;
    }

    public String authUndefinedPassword() {
        return authUndefinedPassword;
    }
}