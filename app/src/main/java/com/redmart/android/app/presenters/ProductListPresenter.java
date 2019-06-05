package com.redmart.android.app.presenters;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.redmart.android.R;
import com.redmart.android.app.activities.RedMartProductDetailsActivity;
import com.redmart.android.app.api.RedMartApi;
import com.redmart.android.app.views.ProductListView;
import com.redmart.android.responsemodels.productList.Product;
import com.redmart.android.responsemodels.productList.ProductListResponse;
import com.redmart.android.uimodels.ProductItemUIModel;
import com.redmart.android.uimodels.UIModelCreator;
import com.redmart.android.utils.RedMartConstants;
import com.redmart.android.utils.disposable.DisposableManager;
import com.redmart.android.utils.scheduler.SchedulerProvider;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Created by ramindu.weeraman on 29/3/18.
 */


public class ProductListPresenter {

    private ProductListView productListView;
    private int pageCounter;
    public boolean isLastPage;
    private RedMartApi redMartApi;
    private SchedulerProvider schedulerProvider;
    private UIModelCreator viewModelCreator;
    private DisposableManager disposableManager;

    public void setPageCounter(int pageCounter) {
        this.pageCounter = pageCounter;
    }

    public ProductListPresenter(ProductListView productListView, RedMartApi redMartApi, RecyclerView recyclerView,
                                SchedulerProvider schedulerProvider, UIModelCreator viewModelCreator,
                                DisposableManager disposableManager
                                ) {
        this.redMartApi = redMartApi;
        this.productListView = productListView;
        pageCounter = 0;
        isLastPage = false;
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener);
        this.schedulerProvider = schedulerProvider;
        this.viewModelCreator = viewModelCreator;
        this.disposableManager=disposableManager;

    }


    public void getProductList() {

        if (pageCounter == 0) {
            productListView.showMainProgressBar();
        } else {
            productListView.showFooterLoader();
        }

        redMartApi.getProductList(String.valueOf(pageCounter), String.valueOf(RedMartConstants.LIST_ITEMS_PER_ONCE)).
                subscribeOn(schedulerProvider.getBackgroundScheduler()).
                map(new Function<ProductListResponse, List<ProductItemUIModel>>() {
                    @Override
                    public List<ProductItemUIModel> apply(ProductListResponse productListResponse) {
                        return viewModelCreator.getProductViewModelList(productListResponse);
                    }
                })
                .observeOn(schedulerProvider.getMainScheduler())
                .subscribe(new Observer<List<ProductItemUIModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposableManager.add(d);
                    }

                    @Override
                    public void onNext(List<ProductItemUIModel> productItemUIModels) {
                        if (productItemUIModels.size() > 0) {
                            isLastPage = false;
                        } else {
                            isLastPage = true;
                        }
                        productListView.updateRecyclerView(productItemUIModels);
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLastPage = true;
                        productListView.showErrorMsg(R.string.err_productListNotFound);
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }


    public void loadProducts() {
        pageCounter++;
        getProductList();
    }

    public void onProductClicked(Activity activity, Product product) {

        Intent intent = new Intent(activity, RedMartProductDetailsActivity.class);
        intent.putExtra("productId", String.valueOf(product.getId()));
        productListView.showProductDetailView(intent);
    }


    public RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = productListView.getLayoutManager().getChildCount();
            int totalItemCount = productListView.getLayoutManager().getItemCount();
            int firstVisibleItemPosition = productListView.getLayoutManager().findFirstVisibleItemPosition();

            if (!productListView.isLoading() && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= productListView.getPageCount()) {
                    loadProducts();
                }
            }
        }
    };



}
