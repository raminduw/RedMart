package com.redmart.android.app.views;

import com.redmart.android.uimodels.ProductDetailUIModel;

/**
 * Created by ramindu.weeraman on 29/3/18.
 */

public interface ProductDetailsView {

    void showMainProgressBar();

    void showErrorMsg(int stringResourceId);

    void showProductDetails(ProductDetailUIModel productDetailViewModel);


}
