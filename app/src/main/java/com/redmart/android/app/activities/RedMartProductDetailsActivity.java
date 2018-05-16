package com.redmart.android.app.activities;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.redmart.android.BaseActivity;
import com.redmart.android.R;
import com.redmart.android.app.adapters.ProdDetailViewImageGalleryAdapter;
import com.redmart.android.app.adapters.ProductDetailsMetaDescAdapter;
import com.redmart.android.app.api.RedMartApiImpl;
import com.redmart.android.app.presenters.ProductDetailViewPresenter;
import com.redmart.android.viewmodels.ViewModelCreator;
import com.redmart.android.app.views.ProductDetailsView;
import com.redmart.android.responseModels.productList.Image;
import com.redmart.android.responseModels.productList.Primary;
import com.redmart.android.uicomponents.RedMartTextView;
import com.redmart.android.viewmodels.ProductDetailViewModel;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ramindu.weeraman on 29/3/18.
 */

public class RedMartProductDetailsActivity extends BaseActivity implements ProductDetailsView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imageViewPager)
    ViewPager imageViewPager;
    @BindView(R.id.indicator)
    CirclePageIndicator indicator;
    @BindView(R.id.productTitleTextView)
    RedMartTextView productTitleTextView;
    @BindView(R.id.sizeDetailsTextView)
    RedMartTextView sizeDetailsTextView;
    @BindView(R.id.priceMainTextView)
    RedMartTextView priceMainTextView;
    @BindView(R.id.priceOriginalTextView)
    RedMartTextView priceOriginalTextView;
    @BindView(R.id.promoTextView)
    RedMartTextView promoTextView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.prodDetailsContainer)
    ScrollView prodDetailsContainer;
    @BindView(R.id.errorTextView)
    RedMartTextView errorTextView;


    private ProductDetailViewPresenter productDetailViewPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String productId = getIntent().getStringExtra("productId");

        // recycler view layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        productDetailViewPresenter = new ProductDetailViewPresenter(this,new RedMartApiImpl(),AndroidSchedulers.mainThread(),Schedulers.io());
        productDetailViewPresenter.setViewModelCreator(new ViewModelCreator());
        productDetailViewPresenter.showDetails(productId);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showProductDetails(ProductDetailViewModel productDetailViewModel) {
        progressBar.setVisibility(View.GONE);
        prodDetailsContainer.setVisibility(View.VISIBLE);

        getSupportActionBar().setTitle(productDetailViewModel.getProductName());
        productTitleTextView.setText(productDetailViewModel.getProductName());
        if (productDetailViewModel.getProductDetails() != null) {
            sizeDetailsTextView.setText(productDetailViewModel.getProductDetails());
        }
        sizeDetailsTextView.setVisibility(productDetailViewModel.getProductDetails() == null ? View.GONE : View.VISIBLE);


        if (productDetailViewModel.getOriginalAmount() != null) {
            priceOriginalTextView.setText(productDetailViewModel.getOriginalAmount());
            priceOriginalTextView.setPaintFlags(priceOriginalTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (productDetailViewModel.getSavingMsg() != null) {
            promoTextView.setText(productDetailViewModel.getSavingMsg());
        }

        priceMainTextView.setText(productDetailViewModel.getCurrentAmount());
        priceOriginalTextView.setVisibility(productDetailViewModel.getCurrentAmount() == null ? View.GONE : View.VISIBLE);
        promoTextView.setVisibility(productDetailViewModel.getSavingMsg() == null ? View.GONE : View.VISIBLE);

        showProductImages(productDetailViewModel.getImages());
        showProductMetaDetails(productDetailViewModel.getMetaDetailsList());
    }

    private void showProductImages(List<Image> images){
        imageViewPager.setAdapter(new ProdDetailViewImageGalleryAdapter(activity, images));
        indicator.setViewPager(imageViewPager);
        indicator.setVisibility(images.size() > 1 ? View.VISIBLE : View.GONE);
    }

    private void showProductMetaDetails(List<Primary> metaDetailsList){
        recyclerView.setAdapter(new ProductDetailsMetaDescAdapter(activity, metaDetailsList));
    }

    @OnClick(R.id.addToCartTextView)
    public void onAddToCartTextViewClick() {
        productDetailViewPresenter.onAddToCartClicked();
    }

    @Override
    public void showMainProgressBar() {
        prodDetailsContainer.setVisibility(View.GONE);
        errorTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorMsg(int stringResourceId) {
        progressBar.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
        errorTextView.setText(stringResourceId);
    }

}
