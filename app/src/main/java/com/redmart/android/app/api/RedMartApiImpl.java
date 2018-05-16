package com.redmart.android.app.api;


import com.redmart.android.responseModels.productDetails.ProductDetailsResponse;
import com.redmart.android.responseModels.productList.ProductListResponse;
import com.redmart.android.utils.RedMartConstants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by ramindu.weeraman on 27/3/18.
 */

public class RedMartApiImpl implements RedMartApi{
    private RedMartApi api;
    public RedMartApiImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RedMartConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        this.api = retrofit.create(RedMartApi.class);
    }


    @Override
    public Observable<ProductListResponse> getProductList(String pageCount, String pageSize) {
        return this.api.getProductList(pageCount, pageSize);
    }

    @Override
    public Observable<ProductDetailsResponse> getProductDetails(String productId) {
        return this.api.getProductDetails(productId);
    }
}
