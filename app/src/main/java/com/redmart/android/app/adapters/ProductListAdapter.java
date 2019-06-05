package com.redmart.android.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.redmart.android.R;
import com.redmart.android.callbacks.OnProductItemClickListener;
import com.redmart.android.utils.RedMartConstants;
import com.redmart.android.utils.Utils;
import com.redmart.android.uimodels.ProductItemUIModel;
import com.redmart.android.uicomponents.RedMartTextView;
import com.redmart.android.uicomponents.RedMartSquareImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by ramindu.weeraman on 28/3/18.
 */

public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int LOADING_VIEW = 1;
    private static final int ITEM_VIEW = 2;
    private List<ProductItemUIModel> productViewModels;
    private Context context;
    private OnProductItemClickListener productClickListener;
    private boolean isLoadingAdded;

    public ProductListAdapter(Activity activity, List<ProductItemUIModel> productViewModels, OnProductItemClickListener productClickListener) {
        this.productViewModels = productViewModels;
        this.context = activity;
        this.productClickListener = productClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LOADING_VIEW) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer_product_list_loader, null);
            return new ProgressBarFooterViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_list, null);
            return new ProductListViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holderObject, final int position) {

        if (holderObject instanceof ProductListViewHolder) {
            final ProductListViewHolder holder = (ProductListViewHolder) holderObject;
            final ProductItemUIModel productData = productViewModels.get(position);

            Utils.getInstance().loadThisImage(context, RedMartConstants.getImageUrl(productData.getProduct().getImg().getName()), holder.productImageView);
            holder.productTitleTextView.setText(productData.getProduct().getTitle());

            if (productData.getProduct().getPricing() != null) {
                holder.priceMainTextView.setVisibility(View.VISIBLE);
                if (productData.getProduct().getPricing().getPromoPrice() != null && productData.getProduct().getPricing().getPromoPrice() > 0) {
                    holder.priceOriginalTextView.setVisibility(View.VISIBLE);
                    holder.priceMainTextView.setText(Utils.getInstance().formatAmountToString(productData.getProduct().getPricing().getPromoPrice()));
                    holder.priceOriginalTextView.setText(Utils.getInstance().formatAmountToString(productData.getProduct().getPricing().getPrice()));
                    holder.priceOriginalTextView.setPaintFlags(holder.priceOriginalTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    holder.priceOriginalTextView.setVisibility(View.GONE);
                    holder.priceMainTextView.setText(Utils.getInstance().formatAmountToString(productData.getProduct().getPricing().getPrice()));
                }

            } else {
                holder.priceMainTextView.setVisibility(View.GONE);
            }

            if (productData.getProduct().getMeasure() != null && productData.getProduct().getMeasure().getWtOrVol() != null) {
                holder.sizeDetailsTextView.setText(productData.getProduct().getMeasure().getWtOrVol());
            }

            if (productData.getCartCounter() == 0) {
                holder.addToCartTextView.setVisibility(View.VISIBLE);
                holder.addToCartBottomLayout.setVisibility(View.GONE);
                holder.productMainCardView.setCardBackgroundColor(Color.WHITE);
            } else {
                holder.addToCartTextView.setVisibility(View.GONE);
                holder.addToCartBottomLayout.setVisibility(View.VISIBLE);
                holder.cartCountTextView.setText(String.valueOf(productData.getCartCounter()));
                holder.productMainCardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            }


            holder.cartMinusTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (productData.getCartCounter()>=0) {
                        productData.setCartCounter(productData.getCartCounter() - 1);
                        productClickListener.onCartCounterChanged(productData.getProduct(), productData.getCartCounter(), false);
                        notifyItemChanged(position);
                    }
                }
            });

            holder.cartAddTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productData.setCartCounter(productData.getCartCounter() + 1);
                    productClickListener.onCartCounterChanged(productData.getProduct(), productData.getCartCounter(), true);
                    notifyItemChanged(position);
                }
            });


            holder.addToCartTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productData.setCartCounter(1);
                    productClickListener.onCartCounterChanged(productData.getProduct(), 1, true);
                    notifyItemChanged(position);
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productClickListener.onProductClicked(productData.getProduct());
                }
            });
        } else {
            ProgressBarFooterViewHolder holder = (ProgressBarFooterViewHolder) holderObject;
            holder.progressBar.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return (position == productViewModels.size() - 1 && isLoadingAdded) ? LOADING_VIEW : ITEM_VIEW;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        productViewModels.add(new ProductItemUIModel());
        notifyItemInserted(productViewModels.size() - 1);
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = productViewModels.size() - 1;
        ProductItemUIModel item = productViewModels.get(position);

        if (item != null) {
            productViewModels.remove(position);
            notifyItemRemoved(position);
        }
    }

    public List<ProductItemUIModel> getProductViewModels() {
        return productViewModels;
    }

    @Override
    public int getItemCount() {
        return productViewModels.size();
    }

    class ProductListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.productImageView)
        RedMartSquareImageView productImageView;
        @BindView(R.id.productMainCardView)
        CardView productMainCardView;
        @BindView(R.id.productTitleTextView)
        RedMartTextView productTitleTextView;
        @BindView(R.id.sizeDetailsTextView)
        RedMartTextView sizeDetailsTextView;
        @BindView(R.id.priceMainTextView)
        RedMartTextView priceMainTextView;
        @BindView(R.id.priceOriginalTextView)
        RedMartTextView priceOriginalTextView;
        @BindView(R.id.cartMinusTextView)
        ImageView cartMinusTextView;
        @BindView(R.id.cartCountTextView)
        RedMartTextView cartCountTextView;
        @BindView(R.id.cartAddTextView)
        ImageView cartAddTextView;
        @BindView(R.id.addToCartBottomLayout)
        RelativeLayout addToCartBottomLayout;
        @BindView(R.id.addToCartTextView)
        RedMartTextView addToCartTextView;

        ProductListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class ProgressBarFooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.progressBar)
        ProgressBar progressBar;

        ProgressBarFooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

