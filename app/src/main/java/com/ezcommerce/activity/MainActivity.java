package com.ezcommerce.activity;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.ezcommerce.R;
import com.ezcommerce.adapter.ProductAdapter;
import com.ezcommerce.client.API;
import com.ezcommerce.client.Route;
import com.ezcommerce.config.QueryParams;
import com.ezcommerce.fragment.CategoryFragment;
import com.ezcommerce.model.Product;
import com.ezcommerce.model.ResponseJSON;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {
    private List<Product> currentProducts;
    private ProductAdapter productAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        super.setActionBar(getSupportActionBar().getCustomView());

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        Route route = API.getInstance().create(Route.class);
        Call<ResponseJSON> call = route.getProducts(QueryParams.NIM.value, QueryParams.NAME.value);
        call.enqueue(new Callback<ResponseJSON>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ResponseJSON> call, Response<ResponseJSON> response) {
                if(response.body() == null) return;

                currentProducts = response.body().getProducts();
                generateList(response.body().getProducts());
                setUserLoginName(response.body().getNama());
                generateClickedActionBarElement();
                generateCategory(response.body().getProducts());
                MainActivity.super.connectDB(getApplicationContext());
                progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<ResponseJSON> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this,"Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateList(List<Product> products){
        RecyclerView recyclerView = findViewById(R.id.product_list);
        productAdapter = new ProductAdapter(this, products);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(productAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void generateCategory(List<Product> products){
        List<String> categories = products.stream().map(Product::getCategory).distinct().collect(Collectors.toList());

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.category_frame, new CategoryFragment(categories));
        fragmentTransaction.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateProductList(String category){
        if(category.equals("all")) {
            productAdapter.updateProducts(currentProducts);
            return;
        }
        List<Product> newProduct = this.currentProducts.stream().filter(p ->
            p.getCategory().equals(category)
        ).collect(Collectors.toList());

        productAdapter.updateProducts(newProduct);
    }
}