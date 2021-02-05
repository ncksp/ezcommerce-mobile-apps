package com.ezcommerce.config;

public enum DatabaseConfig {
    DATABASE_NAME("EzCommerce"),
    DATABASE_VERSION("1");

    public String value;
    DatabaseConfig(String value) {
        this.value = value;
    }
}
