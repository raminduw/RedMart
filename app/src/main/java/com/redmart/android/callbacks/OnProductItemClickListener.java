package com.redmart.android.callbacks;


import com.redmart.android.responsemodels.productList.Product;

/**
 * Created by ramindu.weeraman on 28/3/18.
 */
public interface OnProductItemClickListener {

    void onProductClicked(Product product);

    void onCartCounterChanged(Product product, int count, boolean isIncreased);


}
