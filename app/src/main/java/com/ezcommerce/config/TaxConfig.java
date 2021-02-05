package com.ezcommerce.config;

public enum TaxConfig {
    TAX(10);

    public float value;
    TaxConfig(float value) {
        this.value = value;
    }
}
