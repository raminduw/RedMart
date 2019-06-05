package com.redmart.android.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;

import com.redmart.android.BaseActivity;
import com.redmart.android.R;
import com.redmart.android.app.adapters.ProductListAdapter;
import com.redmart.android.app.presenters.ProductListPresenter;
import com.redmart.android.app.views.ProductListView;
import com.redmart.android.callbacks.OnProductItemClickListener;
import com.redmart.android.responsemodels.productList.Product;
import com.redmart.android.uicomponents.RedMartTextView;
import com.redmart.android.uimodels.ProductItemUIModel;
import com.redmart.android.utils.GridItemDecoration;
import com.redmart.android.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ramindu.weeraman on 27/3/18.
 */

public class RedMartProductListActivity extends BaseActivity implements ProductListView, OnProductItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.errorTextView)
    RedMartTextView errorTextView;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private ProductListPresenter productListPresenter;
    private ProductListAdapter productListAdapter;
    private boolean isLoading = false;
    private int pageCount = 0;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);

        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        swipeRefresh.setOnRefreshListener(this);

        gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new GridItemDecoration(3, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
                getResources().getDisplayMetrics()), true));

        productListPresenter = new ProductListPresenter(this,
                redMartApi, recyclerView, schedulerProvider,viewModelCreator,disposableManager);
        productListPresenter.getProductList();

    }


    @Override
    public void showMainProgressBar() {
        Log.d("TAG", "show MainProgressBar");
        isLoading = true;
        recyclerView.setVisibility(View.GONE);
        errorTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void showErrorMsg(int stringResourceId) {
        swipeRefresh.setRefreshing(false);
        if (progressBar.getVisibility() == View.VISIBLE) {
            errorTextView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            errorTextView.setText(getString(stringResourceId));
        } else {
            Utils.getInstance().showLongToast(activity, getString(stringResourceId));
        }
    }


    @Override
    public void updateRecyclerView(List<ProductItemUIModel> productViewModels) {
        swipeRefresh.setRefreshing(false);
        if (productListAdapter == null) {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            productListAdapter = new ProductListAdapter(activity, productViewModels, this);
            recyclerView.setAdapter(productListAdapter);
        } else {
            productListAdapter.removeLoadingFooter();
            productListAdapter.getProductViewModels().addAll(productViewModels);
            productListAdapter.notifyItemRangeInserted(pageCount, productListAdapter.getProductViewModels().size());
        }
        pageCount = productListAdapter.getProductViewModels().size();
        isLoading = false;
    }


    @Override
    public void showFooterLoader() {
        isLoading = true;
        if (productListAdapter != null) {
            productListAdapter.addLoadingFooter();
        }
    }


    @Override
    public void showProductDetailView(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void onProductClicked(Product product) {
        productListPresenter.onProductClicked(activity, product);
    }

    @Override
    public boolean isLoading() {
        return isLoading;
    }

    @Override
    public GridLayoutManager getLayoutManager() {
        return gridLayoutManager;
    }

    @Override
    public int getPageCount() {
        return pageCount;
    }

    @Override
    public void onCartCounterChanged(Product product, int count, boolean isIncreased) {
        //Add to cart logic goes here
    }


    @Override
    public void onRefresh() {
        productListAdapter = null;
        productListPresenter.setPageCounter(0);
        productListPresenter.getProductList();
        //if swipe to refresh ,no need to show progressBar
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
