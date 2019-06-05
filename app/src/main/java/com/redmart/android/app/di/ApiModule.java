package com.redmart.android.app.di;

import com.redmart.android.app.api.RedMartApi;
import com.redmart.android.utils.RedMartConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {
    @Provides
    @Singleton
    RedMartApi providesApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RedMartConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(RedMartApi.class);

    }
}
