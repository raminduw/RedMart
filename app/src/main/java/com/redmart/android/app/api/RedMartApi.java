package com.redmart.android.app.api;


import com.redmart.android.responseModels.productDetails.ProductDetailsResponse;
import com.redmart.android.responseModels.productList.ProductListResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ramindu.weeraman on 27/3/18.
 */

public interface RedMartApi {

    @GET("/v1.6.0/catalog/search")
    Observable<ProductListResponse> getProductList(@Query("page") String pageCount, @Query("pageSize") String pageSize);


    @GET("/v1.6.0/catalog/products/{product_id}")
    Observable<ProductDetailsResponse> getProductDetails(@Path("product_id") String productId);
}
