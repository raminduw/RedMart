package com.redmart.android.app.api;


import com.redmart.android.responsemodels.productDetails.ProductDetailsResponse;
import com.redmart.android.responsemodels.productList.ProductListResponse;
import com.redmart.android.utils.RedMartConstants;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ramindu.weeraman on 27/3/18.
 */

public class RedMartApiImpl implements RedMartApi{
    private RedMartApi api;
    public RedMartApiImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RedMartConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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
