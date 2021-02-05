package com.ezcommerce.config;

public enum CartTableConfig {
    TABLE_NAME("cart"),
    ID("id"),
    PRODUCT_NAME("product_name"),
    PRODUCT_PRICE("product_price"),
    PRODUCT_AUTHOR("product_author"),
    PRODUCT_IMAGE("product_image"),
    QTY("qty"),


    CREATE_TABLE("CREATE TABLE " + TABLE_NAME.value + "(" +
            ID.value + " INTEGER PRIMARY KEY," +
            PRODUCT_NAME.value + " TEXT," +
            PRODUCT_PRICE.value + " REAL," +
            PRODUCT_AUTHOR.value + " TEXT," +
            PRODUCT_IMAGE.value + " TEXT," +
            QTY.value + " INTEGER" +
            ")"),

    DROP_TABLE("DROP TABLE IF EXISTS " + TABLE_NAME.value);

    public String value;
    CartTableConfig(String value) {
        this.value = value;
    }
}
