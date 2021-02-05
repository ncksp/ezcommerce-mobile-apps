package com.ezcommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezcommerce.R;
import com.ezcommerce.model.Cart;
import com.ezcommerce.viewholder.CartViewHolder;
import com.ezcommerce.viewholder.ProductViewHolder;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder>{
    private List<Cart> carts;
    private Context context;

    public CartAdapter(Context context, List<Cart> carts){
        this.context = context;
        this.carts = carts;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_product_item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.setContent(context, carts.get(position), position);
    }

    @Override
    public int getItemCount() {
        return carts != null ? carts.size(): 0;
    }

    public void updateCarts(List<Cart> newProducts){
        this.carts = newProducts;
        notifyDataSetChanged();
    }
}
