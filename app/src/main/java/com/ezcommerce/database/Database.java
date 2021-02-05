package com.ezcommerce.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ezcommerce.config.CartTableConfig;
import com.ezcommerce.config.DatabaseConfig;
import com.ezcommerce.model.Cart;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static Database database;

    private Database(Context context){
        super(context, DatabaseConfig.DATABASE_NAME.value, null, Integer.parseInt(DatabaseConfig.DATABASE_VERSION.value));
    }

    public static Database getInstance(Context context){
        if(database == null) database = new Database(context);
        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CartTableConfig.CREATE_TABLE.value);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CartTableConfig.DROP_TABLE.value);
        onCreate(db);
    }
    private ContentValues generateValues(Cart cart){
        ContentValues values = new ContentValues();
        values.put(CartTableConfig.ID.value, cart.getId());
        values.put(CartTableConfig.PRODUCT_NAME.value, cart.getName());
        values.put(CartTableConfig.PRODUCT_PRICE.value, cart.getPrice());
        values.put(CartTableConfig.PRODUCT_AUTHOR.value, cart.getAuthor());
        values.put(CartTableConfig.PRODUCT_IMAGE.value, cart.getImg());
        values.put(CartTableConfig.QTY.value, cart.getQty());

        return  values;
    }
    public void store(Cart cart){
        if(cart.getQty() > 1){
            update(cart);
            return;
        }
        SQLiteDatabase db  = getWritableDatabase();
        ContentValues values = generateValues(cart);
        db.insert(CartTableConfig.TABLE_NAME.value, null, values);
        db.close();
    }

    public int getCart(int productId){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(CartTableConfig.TABLE_NAME.value, new String[] { CartTableConfig.ID.value,
                        CartTableConfig.QTY.value}, CartTableConfig.ID.value + "=?",
                new String[] { String.valueOf(productId) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getCount() <= 0 ? -1 : cursor.getInt(1);
    }

    public List<Cart> carts(){
        List<Cart> carts = new ArrayList<Cart>();
        String selectQuery = "SELECT  * FROM " + CartTableConfig.TABLE_NAME.value;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (!cursor.moveToFirst())
            return null;

        do {
            Cart cart =  new Cart(cursor.getInt(0), cursor.getString(1)
            ,cursor.getFloat(2), cursor.getString(3), cursor.getString(4),
                    cursor.getInt(5));

            carts.add(cart);
        } while (cursor.moveToNext());

        return carts;
    }

    public int update(Cart cart){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = generateValues(cart);

        // updating row
        return db.update(CartTableConfig.TABLE_NAME.value, values, CartTableConfig.ID.value + " = ?",
                new String[] { String.valueOf(cart.getId()) });
    }

    public void destroy(int productId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CartTableConfig.TABLE_NAME.value, CartTableConfig.ID.value + " = ?",
                new String[] {String.valueOf(productId)});
        db.close();
    }

    public void clear(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ CartTableConfig.TABLE_NAME.value);
    }
}