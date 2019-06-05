package com.redmart.android.uimodels;


import com.redmart.android.responsemodels.productList.Product;

/**
 * Created by ramindu.weeraman on 29/3/18.
 */

public class ProductItemUIModel {

    public int getCartCounter() {
        return cartCounter;
    }

    public void setCartCounter(int cartCounter) {
        this.cartCounter = cartCounter;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    private int cartCounter = 0;
    private Product product;

    public ProductItemUIModel(int cartCounter, Product product) {
        this.cartCounter = cartCounter;
        this.product = product;
    }

    public ProductItemUIModel() {

    }
}
