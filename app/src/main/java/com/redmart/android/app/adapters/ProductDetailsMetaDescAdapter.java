package com.redmart.android.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.redmart.android.R;
import com.redmart.android.responsemodels.productList.Primary;
import com.redmart.android.uicomponents.RedMartTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by ramindu.weeraman on 29/3/18.
 */

public class ProductDetailsMetaDescAdapter extends RecyclerView.Adapter<ProductDetailsMetaDescAdapter.ProductDescriptionViewHolder> {
    private List<Primary> descriptionDataList;
    private Context context;

    public ProductDetailsMetaDescAdapter(Activity activity, List<Primary> descriptionDataList) {
        this.descriptionDataList = descriptionDataList;
        this.context = activity;

    }


    @Override
    public ProductDescriptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_details_meta_desc, parent, false);
        return new ProductDescriptionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProductDescriptionViewHolder holder, final int position) {

        final Primary descriptionData = descriptionDataList.get(position);

        holder.titleTextView.setText(descriptionData.getName());
        holder.descTextView.setText(descriptionData.getContent());

    }


    @Override
    public int getItemCount() {
        return descriptionDataList.size();
    }

    class ProductDescriptionViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.titleTextView)
        RedMartTextView titleTextView;
        @BindView(R.id.descTextView)
        RedMartTextView descTextView;

        ProductDescriptionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

