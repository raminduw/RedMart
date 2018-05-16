package com.redmart.android.viewmodels;


import com.redmart.android.responseModels.productList.Product;

/**
 * Created by ramindu.weeraman on 29/3/18.
 */

public class ProductViewModel {

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

    public ProductViewModel(int cartCounter, Product product) {
        this.cartCounter = cartCounter;
        this.product = product;
    }

    public ProductViewModel() {

    }
}
