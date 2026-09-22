package com.ayush.framework.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Singleton ConfigReader — loads config.properties once and provides
 * typed getters throughout the framework.
 */
public class ConfigReader {

    private static ConfigReader instance;
    private final Properties properties;

    private ConfigReader() {
        properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream(
                "src/test/resources/config.properties"
            );
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("config.properties not found", e);
        }
    }

    public static ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    public String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) throw new RuntimeException("Property not found: " + key);
        return value.trim();
    }

    public int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    // ── Convenience getters ───────────────────────────────────────
    public String getBaseUrl()        { return get("base.url"); }
    public String getApiBaseUrl()     { return get("api.base.url"); }
    public String getBrowser()        { return get("browser"); }
    public boolean isHeadless()       { return getBoolean("headless"); }
    public int getImplicitWait()      { return getInt("implicit.wait"); }
    public int getExplicitWait()      { return getInt("explicit.wait"); }
    public String getValidUsername()  { return get("valid.username"); }
    public String getValidPassword()  { return get("valid.password"); }
    public String getLockedUsername() { return get("locked.username"); }
}
