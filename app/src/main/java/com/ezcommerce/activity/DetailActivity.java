package com.ezcommerce.activity;

import androidx.annotation.RequiresApi;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ezcommerce.R;
import com.ezcommerce.client.API;
import com.ezcommerce.client.Route;
import com.ezcommerce.config.ProductIntentConfig;
import com.ezcommerce.config.QueryParams;
import com.ezcommerce.database.Database;
import com.ezcommerce.model.Cart;
import com.ezcommerce.model.Product;
import com.ezcommerce.model.ResponseJSON;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends Activity {
    private ProgressDialog progressDialog;
    private Product currentProduct;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        super.setActionBar(getSupportActionBar().getCustomView());
        super.connectDB(getApplicationContext());

        progressDialog = new ProgressDialog(DetailActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        int productId = getIntent().getIntExtra(ProductIntentConfig.PRODUCT_ID.toString(), 0);
        Route route = API.getInstance().create(Route.class);

        Call<ResponseJSON> call = route.getProduct(productId, QueryParams.NIM.value, QueryParams.NAME.value);
        call.enqueue(new Callback<ResponseJSON>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ResponseJSON> call, Response<ResponseJSON> response) {
                if(response.body() == null) finish();
                if(response.body().getProducts().size() < 1){
                    Toast.makeText(DetailActivity.this,"Product not found", Toast.LENGTH_SHORT).show();
                    finish();
                }
                currentProduct = response.body().getProducts().get(0);
                setUserLoginName(response.body().getNama());
                generateClickedActionBarElement();
                generateProduct();
                db = DetailActivity.super.getDb();
                findViewById(R.id.buy_button).setOnClickListener(btn ->{ addToCart(); });
                progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<ResponseJSON> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DetailActivity.this,"Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void addToCart() {
        try {
            int exist = db.getCart(currentProduct.getId());
            int qty = exist != -1 ? exist + 1 : 1;
            Cart cart = new Cart(currentProduct.getId(), currentProduct.getName(), currentProduct.getPrice(),
                    currentProduct.getAuthor(), currentProduct.getImg(), qty);
            db.store(cart);

            Toast.makeText(DetailActivity.this,"Item added to cart", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.d("ERROR BUY", e.getMessage());
            e.printStackTrace();
            Toast.makeText(DetailActivity.this,"Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
        }
    }

    private void generateProduct() {
        ImageView imageView = findViewById(R.id.img_item_detail);
        TextView productName = findViewById(R.id.product_name_detail);
        TextView productPrice = findViewById(R.id.product_price_detail);
        TextView productDesc = findViewById(R.id.product_desc_detail);
        Picasso.get().load(currentProduct.getImg()).into(imageView);
        productName.setText(currentProduct.getName());
        productPrice.setText(String.valueOf(currentProduct.getPrice()));
        productDesc.setText(currentProduct.getDescription());
    }
}