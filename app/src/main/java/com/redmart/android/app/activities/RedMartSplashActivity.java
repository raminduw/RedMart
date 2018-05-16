package com.redmart.android.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.redmart.android.BaseActivity;

/**
 * Created by ramindu.weeraman on 27/3/18.
 */

public class RedMartSplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(activity, RedMartProductListActivity.class));
        finish();
    }
}
