package com.redmart.android.app.presenters;

import android.support.annotation.NonNull;

import com.redmart.android.R;
import com.redmart.android.app.api.RedMartApiImpl;
import com.redmart.android.app.views.ProductDetailsView;
import com.redmart.android.responseModels.productDetails.ProductDetailsResponse;
import com.redmart.android.viewmodels.ProductDetailViewModel;
import com.redmart.android.viewmodels.ViewModelCreator;

import rx.Observer;
import rx.Scheduler;
import rx.functions.Func1;
/**
 * Created by ramindu.weeraman on 29/3/18.
 */


public class ProductDetailViewPresenter {
    private ProductDetailsView productDetailsView;
    private RedMartApiImpl redMartApi;
    @NonNull
    private Scheduler backgroundScheduler;
    @NonNull
    private Scheduler mainScheduler;
    @NonNull
    private ViewModelCreator viewModelCreator;

    public void setViewModelCreator(ViewModelCreator viewModelCreator) {
        this.viewModelCreator = viewModelCreator;
    }



    public ProductDetailViewPresenter(ProductDetailsView productDetailsView , RedMartApiImpl redMartApi, Scheduler mainScheduler, Scheduler backgroundScheduler) {
       this.redMartApi=redMartApi;
        this.productDetailsView = productDetailsView;
        this.backgroundScheduler = backgroundScheduler;
        this.mainScheduler = mainScheduler;
    }


    public void showDetails(String productId) {
        if(productId==null || productId.isEmpty()){
            productDetailsView.showErrorMsg(R.string.err_productDetailsNotFound);
        }else{
            productDetailsView.showMainProgressBar();
            redMartApi.getProductDetails(productId).
                    subscribeOn(backgroundScheduler).
                    map(new Func1<ProductDetailsResponse, ProductDetailViewModel>() {
                        @Override
                        public ProductDetailViewModel call(ProductDetailsResponse productDetailsResponse) {
                          //  ViewModelCreator viewModelCreator = new ViewModelCreator();
                            return viewModelCreator.getProductDetailViewModel(productDetailsResponse);

                        }
                    })
                    .observeOn(mainScheduler)
                    .subscribe(new Observer<ProductDetailViewModel>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            productDetailsView.showErrorMsg(R.string.err_productDetailsNotFound);
                        }

                        @Override
                        public void onNext(ProductDetailViewModel productDetailViewModel) {
                            if (productDetailViewModel == null) {
                                productDetailsView.showErrorMsg(R.string.err_productDetailsNotFound);
                            } else {
                                productDetailsView.showProductDetails(productDetailViewModel);
                            }
                        }
                    });

        }


    }


    public void onAddToCartClicked() {
        productDetailsView.showErrorMsg(R.string.err_addToCartPending);
    }


}
