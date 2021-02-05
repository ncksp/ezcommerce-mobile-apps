package com.ezcommerce.viewholder;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezcommerce.R;
import com.ezcommerce.activity.CartActivity;
import com.ezcommerce.model.Cart;
import com.squareup.picasso.Picasso;

public class CartViewHolder extends RecyclerView.ViewHolder{

    private final ImageView image;
    private final TextView name, price, author, qty;
    private final Button dec, inc;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.product_img_cart);
        name = itemView.findViewById(R.id.product_name_cart);
        price = itemView.findViewById(R.id.product_price_cart);
        author = itemView.findViewById(R.id.product_author_cart);
        qty = itemView.findViewById(R.id.qty_item_cart);
        dec = itemView.findViewById(R.id.decrement_qty_cart);
        inc = itemView.findViewById(R.id.increment_qty_cart);
    }

    public void setContent(Context context, Cart cart, int index){
        Picasso.get().load(cart.getImg()).into(image);
        name.setText(cart.getName());
        price.setText("$"+String.valueOf(cart.getPrice()));
        author.setText(cart.getAuthor());
        qty.setText(cart.getQty()+"");
        CartActivity cartActivity = (CartActivity) context;
        cartActivity.updateSubTotal(cart.getPrice() * cart.getQty());

        initChangedQty(context, cart, index);
    }

    private void initChangedQty(Context context, Cart cart, int index) {
        CartActivity cartActivity = (CartActivity) context;
        dec.setOnClickListener(btn -> {
            cartActivity.updateSubTotal(-cart.getPrice());
            if(cart.getQty() < 2)
                cartActivity.removeItem(cart.getId());
            else
                cartActivity.updateQty(index, cart.getQty() - 1);

            qty.setText(cart.getQty()+ "");
            cartActivity.updateSubTotal();
        });

        inc.setOnClickListener(btn -> {
            cartActivity.updateSubTotal(cart.getPrice());
            cartActivity.updateQty(index, cart.getQty() + 1);

            qty.setText(cart.getQty()+ "");
            cartActivity.updateSubTotal();
        });
    }
}
