package com.redmart.android.app.presenters;

import com.redmart.android.R;
import com.redmart.android.app.api.RedMartApi;
import com.redmart.android.app.views.ProductDetailsView;
import com.redmart.android.responsemodels.productDetails.ProductDetailsResponse;
import com.redmart.android.uimodels.ProductDetailUIModel;
import com.redmart.android.uimodels.UIModelCreator;
import com.redmart.android.utils.disposable.DisposableManager;
import com.redmart.android.utils.scheduler.SchedulerProvider;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Created by ramindu.weeraman on 29/3/18.
 */


public class ProductDetailViewPresenter{
    private ProductDetailsView productDetailsView;
    private RedMartApi redMartApi;
    private SchedulerProvider schedulerProvider;
    private UIModelCreator viewModelCreator;
    private DisposableManager disposableManager;

    public ProductDetailViewPresenter(ProductDetailsView productDetailsView, RedMartApi redMartApi,
                                      SchedulerProvider schedulerProvider, UIModelCreator viewModelCreator, DisposableManager disposableManager) {
        this.redMartApi = redMartApi;
        this.productDetailsView = productDetailsView;
        this.schedulerProvider = schedulerProvider;
        this.viewModelCreator = viewModelCreator;
        this.disposableManager = disposableManager;
    }


    public void showDetails(String productId) {
        if (productId == null || productId.isEmpty()) {
            productDetailsView.showErrorMsg(R.string.err_productDetailsNotFound);
        } else {
            productDetailsView.showMainProgressBar();
            redMartApi.getProductDetails(productId).
                    subscribeOn(schedulerProvider.getBackgroundScheduler()).
                    map(new Function<ProductDetailsResponse, ProductDetailUIModel>() {
                        @Override
                        public ProductDetailUIModel apply(ProductDetailsResponse productDetailsResponse) {
                            return viewModelCreator.getProductDetailViewModel(productDetailsResponse);
                        }
                    })
                    .observeOn(schedulerProvider.getMainScheduler())
                    .subscribe(new Observer<ProductDetailUIModel>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposableManager.add(d);
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


}
