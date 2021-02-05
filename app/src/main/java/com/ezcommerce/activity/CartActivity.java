package com.ezcommerce.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.ezcommerce.R;
import com.ezcommerce.adapter.CartAdapter;
import com.ezcommerce.config.TaxConfig;
import com.ezcommerce.database.Database;
import com.ezcommerce.model.Cart;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

public class CartActivity extends Activity {
    private DecimalFormat df = new DecimalFormat("0.00");
    private float subTotal = 0, tax = 0;
    private List<Cart> carts;
    private CartAdapter cartAdapter;
    private TextView subTotalText, taxText, totalText;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        super.setActionBar(getSupportActionBar().getCustomView());
        super.connectDB(getApplicationContext());

        subTotalText = findViewById(R.id.subtotal_cart);
        taxText = findViewById(R.id.taxes_cart);
        totalText = findViewById(R.id.total_cart);

        db = super.getDb();

        carts = db.carts();
        generateList(carts);
    }

    private void initTotalPayment() {
        subTotalText.setText("$ "+ df.format(subTotal));
        taxText.setText("$ "+ df.format(tax));
        totalText.setText("$ "+ df.format(subTotal + tax));
    }

    private void generateList(List<Cart> carts){
        RecyclerView recyclerView = findViewById(R.id.cart_list);
        cartAdapter = new CartAdapter(this, carts);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public void onLayoutCompleted(final RecyclerView.State state) {
                super.onLayoutCompleted(state);
                initTotalPayment();
                initButtonClicked();
            }
        };
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cartAdapter);
    }

    private void initButtonClicked(){
        findViewById(R.id.cancel_button_cart).setOnClickListener(btn -> {
            finish();
        });

        findViewById(R.id.checkout_button_cart).setOnClickListener(btn -> {
            if(carts == null){
                Toast.makeText(CartActivity.this,"Cart Empty!", Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(CartActivity.this,"Checkout Success", Toast.LENGTH_LONG).show();
            this.checkOut();
        });
    }

    private void refreshList(){
        carts = db.carts();

        cartAdapter.updateCarts(carts);
    }
    public void updateSubTotal(float price){
        this.tax += (price * TaxConfig.TAX.value / 100);
        this.subTotal += price;
    }

    public void updateSubTotal(){
        initTotalPayment();
    }

    public void removeItem(int id){
        db.destroy(id);
        this.refreshList();
    }

    public void updateQty(int index, int newQty){
        carts.get(index).setQty(newQty);
        db.update(carts.get(index));
    }

    private void checkOut(){
        db.clear();
        recreate();
        this.refreshList();
    }
}