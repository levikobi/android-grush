package com.comas.grush.ui.product;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.comas.grush.R;
import com.comas.grush.model.Product;
import com.comas.grush.ui.gallery.GalleryFragmentDirections;
import com.comas.grush.ui.gallery.GalleryViewModel;
import com.comas.grush.ui.home.HomeFragmentDirections;
import com.comas.grush.ui.home.HomeViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {

    private final ProductListViewModel mViewModel;

    private final LayoutInflater mInflater;

    public ProductListAdapter(Context context, ProductListViewModel viewModel) {
        mInflater = LayoutInflater.from(context);
        mViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(viewModel.getClass());
    }

    @NonNull
    @Override
    public ProductListAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.productlist_item, parent, false);
        return new ProductViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.ProductViewHolder holder, int position) {
        Product mCurrent = mViewModel.getProductList().getValue().get(position);
        if (mCurrent.getImage() != null) {
            Picasso.get().load(mCurrent.getImage()).placeholder(R.drawable.ic_menu_gallery).into(holder.productItemImage);
        }
        holder.productItemText.setText(mCurrent.getName());
    }

    @Override
    public int getItemCount() {
        if (mViewModel.getProductList() == null) return 0;
        List<Product> products = mViewModel.getProductList().getValue();
        return products != null ? products.size() : 0;
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView productItemText;
        public final ImageView productItemImage;
        final ProductListAdapter mAdapter;

        public ProductViewHolder(@NonNull View itemView, ProductListAdapter adapter) {
            super(itemView);
            productItemText = itemView.findViewById(R.id.productlist_item_text);
            productItemImage = itemView.findViewById(R.id.productlist_item_image);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String productId = mViewModel.getProductList().getValue().get(getLayoutPosition()).getId();
            if (mViewModel instanceof HomeViewModel) {
                Navigation.findNavController(v)
                        .navigate(HomeFragmentDirections.actionHomeToProductDetails(productId));
            }
            if (mViewModel instanceof GalleryViewModel) {
                Navigation.findNavController(v)
                        .navigate(GalleryFragmentDirections.actionGalleryToProductDetails(productId));
            }
        }
    }
}
