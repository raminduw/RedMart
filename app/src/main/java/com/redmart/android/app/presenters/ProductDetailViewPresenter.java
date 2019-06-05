package com.redmart.android.app.presenters;

import android.support.annotation.NonNull;

import com.redmart.android.R;
import com.redmart.android.app.api.RedMartApiImpl;
import com.redmart.android.app.views.ProductDetailsView;
import com.redmart.android.responsemodels.productDetails.ProductDetailsResponse;
import com.redmart.android.uimodels.ProductDetailUIModel;
import com.redmart.android.uimodels.ViewModelCreator;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Created by ramindu.weeraman on 29/3/18.
 */


public class ProductDetailViewPresenter{
    private ProductDetailsView productDetailsView;
    private RedMartApiImpl redMartApi;
    @NonNull
    private Scheduler backgroundScheduler;
    @NonNull
    private Scheduler mainScheduler;
    @NonNull
    private ViewModelCreator viewModelCreator;

    private CompositeDisposable compositeDisposable;

    public void setViewModelCreator(ViewModelCreator viewModelCreator) {
        this.viewModelCreator = viewModelCreator;
    }

    public ProductDetailViewPresenter(ProductDetailsView productDetailsView, RedMartApiImpl redMartApi,
                                         Scheduler mainScheduler, Scheduler backgroundScheduler) {
        this.redMartApi = redMartApi;
        this.productDetailsView = productDetailsView;
        this.backgroundScheduler = backgroundScheduler;
        this.mainScheduler = mainScheduler;
        this.compositeDisposable =  new CompositeDisposable();
    }


    public void showDetails(String productId) {
        if (productId == null || productId.isEmpty()) {
            productDetailsView.showErrorMsg(R.string.err_productDetailsNotFound);
        } else {
            productDetailsView.showMainProgressBar();
            redMartApi.getProductDetails(productId).
                    subscribeOn(backgroundScheduler).
                    map(new Function<ProductDetailsResponse, ProductDetailUIModel>() {
                        @Override
                        public ProductDetailUIModel apply(ProductDetailsResponse productDetailsResponse) {
                            return viewModelCreator.getProductDetailViewModel(productDetailsResponse);
                        }
                    })
                    .observeOn(mainScheduler)
                    .subscribe(new Observer<ProductDetailUIModel>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            compositeDisposable.add(d);
                        }

                        @Override
                        public void onNext(ProductDetailUIModel productDetailUIModel) {
                            if (productDetailUIModel == null) {
                                productDetailsView.showErrorMsg(R.string.err_productDetailsNotFound);
                            } else {
                                productDetailsView.showProductDetails(productDetailUIModel);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            productDetailsView.showErrorMsg(R.string.err_productDetailsNotFound);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        }
    }


    public void onAddToCartClicked() {
        productDetailsView.showErrorMsg(R.string.err_addToCartPending);
    }

    public void clearDisposable(){
        if(!compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
        }
    }


}
