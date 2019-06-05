package com.redmart.android.app.adapters;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.redmart.android.R;
import com.redmart.android.responsemodels.productList.Image;
import com.redmart.android.utils.RedMartConstants;
import com.redmart.android.utils.Utils;
import com.redmart.android.uicomponents.RedMartSquareImageView;

import java.util.List;

/**
 * Created by ramindu.weeraman on 29/3/18.
 */

public class ProdDetailViewImageGalleryAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Image> imageList;

    public ProdDetailViewImageGalleryAdapter(Context context, List<Image> imageList) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = layoutInflater.inflate(R.layout.item_image_gallery, container, false);
        RedMartSquareImageView imageView = (RedMartSquareImageView) itemView.findViewById(R.id.productImageView);
        Utils.getInstance().loadThisImage(context, RedMartConstants.getImageUrl(imageList.get(position).getName()), imageView);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}
