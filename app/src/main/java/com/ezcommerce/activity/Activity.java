package com.ezcommerce.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ezcommerce.R;
import com.ezcommerce.activity.CartActivity;
import com.ezcommerce.database.Database;

public class Activity extends AppCompatActivity {
    private View actionBar;
    private Database db;
    void setUserLoginName(String name){
        String[] split = name.split(" ");
        TextView nameTxt = actionBar.findViewById(R.id.user_name);
        nameTxt.setText(split[0] == null ? "" : split[0]);
    }

    void generateClickedActionBarElement(){
        actionBar.findViewById(R.id.cart_button).setOnClickListener(view ->
                startActivity(new Intent(this, CartActivity.class))
        );
    }

    public void setActionBar(View actionBar) {
        this.actionBar = actionBar;
    }

    void connectDB(Context context){
        if(db != null) return;

        db = Database.getInstance(this);
    }

    public Database getDb() {
        return db;
    }

    public void stopDb(){
        db.close();
    }
}
