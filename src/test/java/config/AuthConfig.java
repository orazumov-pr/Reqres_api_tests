package config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public interface AuthConfig {
    Config config = ConfigFactory.load();

    default String authEmail() {
        return config.getString("auth.email");
    }

    default String authPassword() {
        return config.getString("auth.password");
    }

    default String authUndefinedEmail() {
        return config.getString("auth.undefinedEmail");
    }

    default String authUndefinedPassword() {
        return config.getString("auth.undefinedPassword");
    }
}
