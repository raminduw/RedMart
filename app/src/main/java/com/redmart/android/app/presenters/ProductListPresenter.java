package com.redmart.android.app.presenters;


import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.redmart.android.R;
import com.redmart.android.app.activities.RedMartProductDetailsActivity;
import com.redmart.android.app.api.RedMartApiImpl;
import com.redmart.android.app.views.ProductListView;
import com.redmart.android.responseModels.productList.Product;
import com.redmart.android.responseModels.productList.ProductListResponse;
import com.redmart.android.utils.RedMartConstants;
import com.redmart.android.viewmodels.ProductViewModel;
import com.redmart.android.viewmodels.ViewModelCreator;

import java.util.List;

import rx.Observer;
import rx.Scheduler;
import rx.functions.Func1;

/**
 * Created by ramindu.weeraman on 29/3/18.
 */


public class ProductListPresenter {

    private ProductListView productListView;
    private int pageCounter;
    public boolean isLastPage;
    private RedMartApiImpl redMartApi;
    @NonNull
    private Scheduler backgroundScheduler;
    @NonNull
    private Scheduler mainScheduler;
    @NonNull
    private ViewModelCreator viewModelCreator;


    public void setPageCounter(int pageCounter) {
        this.pageCounter = pageCounter;
    }

    public ProductListPresenter(ProductListView dashboardView, RedMartApiImpl redMartApi, RecyclerView recyclerView, Scheduler mainScheduler, Scheduler backgroundScheduler) {
        this.redMartApi = redMartApi;
        this.productListView = dashboardView;
        pageCounter = 0;
        isLastPage = false;
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener);
        this.mainScheduler = mainScheduler;
        this.backgroundScheduler = backgroundScheduler;
    }

    public void setViewModelCreator(@NonNull ViewModelCreator viewModelCreator) {
        this.viewModelCreator = viewModelCreator;
    }


    public void getProductList() {

        if (pageCounter == 0) {
            productListView.showMainProgressBar();
        } else {
            productListView.showFooterLoader();
        }

        redMartApi.getProductList(String.valueOf(pageCounter), String.valueOf(RedMartConstants.LIST_ITEMS_PER_ONCE)).
                subscribeOn(backgroundScheduler).
                map(new Func1<ProductListResponse, List<ProductViewModel>>() {
                    @Override
                    public List<ProductViewModel> call(ProductListResponse productListResponse) {
                        return viewModelCreator.getProductViewModelList(productListResponse);
                    }
                })
                .observeOn(mainScheduler)
                .subscribe(new Observer<List<ProductViewModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        isLastPage = true;
                        productListView.showErrorMsg(R.string.err_productListNotFound);
                    }

                    @Override
                    public void onNext(List<ProductViewModel> productViewModelList) {
                        if (productViewModelList.size() > 0) {
                            isLastPage = false;
                        } else {
                            isLastPage = true;
                        }
                        productListView.updateRecyclerView(productViewModelList);
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