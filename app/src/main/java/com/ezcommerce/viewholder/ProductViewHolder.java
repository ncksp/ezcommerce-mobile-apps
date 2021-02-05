package com.ezcommerce.viewholder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezcommerce.activity.DetailActivity;
import com.ezcommerce.R;
import com.ezcommerce.config.ProductIntentConfig;
import com.ezcommerce.model.Product;
import com.squareup.picasso.Picasso;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    private final ImageView image;
    private final TextView name, price;
    private final RelativeLayout productItem;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        productItem = itemView.findViewById(R.id.product_item);
        image = itemView.findViewById(R.id.img_item);
        name = itemView.findViewById(R.id.tv_product_name);
        price = itemView.findViewById(R.id.tv_product_price);
    }

    public void setContent(Context context, Product product){
        name.setText(product.getName());
        price.setText("$"+String.valueOf(product.getPrice()));
        Picasso.get().load(product.getImg()).into(image);

        productItem.setOnClickListener(item -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(ProductIntentConfig.PRODUCT_ID.toString(), product.getId());
            context.startActivity(intent);
        });
    }
}
