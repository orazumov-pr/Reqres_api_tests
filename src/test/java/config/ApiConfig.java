package config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public interface ApiConfig {
    Config config = ConfigFactory.load();

    default String baseUrl() {
        return config.getString("api.baseUrl");
    }

    default String basePath() {
        return config.getString("api.basePath");
    }
}
