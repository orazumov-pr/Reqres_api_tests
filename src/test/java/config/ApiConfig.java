package config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ApiConfig {
    private static final Config config = ConfigFactory.load();
    private static final ApiConfig instance = new ApiConfig();

    private final String baseUrl;
    private final String basePath;

    private ApiConfig() {
        this.baseUrl = config.getString("api.baseUrl");
        this.basePath = config.getString("api.basePath");
    }

    public static ApiConfig getInstance() {
        return instance;
    }

    public String baseUrl() {
        return baseUrl;
    }

    public String basePath() {
        return basePath;
    }
}