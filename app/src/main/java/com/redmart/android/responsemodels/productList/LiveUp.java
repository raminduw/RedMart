
package com.redmart.android.responsemodels.productList;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LiveUp implements Parcelable {

    @SerializedName("promo_price")
    @Expose
    private Double promoPrice;
    @SerializedName("savings")
    @Expose
    private Double savings;
    @SerializedName("savings_amount")
    @Expose
    private Double savingsAmount;
    @SerializedName("savings_type")
    @Expose
    private Long savingsType;
    @SerializedName("savings_text")
    @Expose
    private String savingsText;

    public Double getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(Double promoPrice) {
        this.promoPrice = promoPrice;
    }

    public Double getSavings() {
        return savings;
    }

    public void setSavings(Double savings) {
        this.savings = savings;
    }

    public Double getSavingsAmount() {
        return savingsAmount;
    }

    public void setSavingsAmount(Double savingsAmount) {
        this.savingsAmount = savingsAmount;
    }

    public Long getSavingsType() {
        return savingsType;
    }

    public void setSavingsType(Long savingsType) {
        this.savingsType = savingsType;
    }

    public String getSavingsText() {
        return savingsText;
    }

    public void setSavingsText(String savingsText) {
        this.savingsText = savingsText;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.promoPrice);
        dest.writeValue(this.savings);
        dest.writeValue(this.savingsAmount);
        dest.writeValue(this.savingsType);
        dest.writeString(this.savingsText);
    }

    public LiveUp() {
    }

    protected LiveUp(Parcel in) {
        this.promoPrice = (Double) in.readValue(Double.class.getClassLoader());
        this.savings = (Double) in.readValue(Double.class.getClassLoader());
        this.savingsAmount = (Double) in.readValue(Double.class.getClassLoader());
        this.savingsType = (Long) in.readValue(Long.class.getClassLoader());
        this.savingsText = in.readString();
    }

    public static final Parcelable.Creator<LiveUp> CREATOR = new Parcelable.Creator<LiveUp>() {
        @Override
        public LiveUp createFromParcel(Parcel source) {
            return new LiveUp(source);
        }

        @Override
        public LiveUp[] newArray(int size) {
            return new LiveUp[size];
        }
    };
}
