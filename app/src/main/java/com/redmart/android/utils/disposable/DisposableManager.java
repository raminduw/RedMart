package com.redmart.android.utils.disposable;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class DisposableManager {

    private CompositeDisposable compositeDisposable;

    @Inject
    public DisposableManager() {
        compositeDisposable = new CompositeDisposable();
    }

    public void add(Disposable disposable) {
        if (compositeDisposable != null) {
            compositeDisposable.add(disposable);
        }
    }

    public void dispose() {
        if (compositeDisposable != null && compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }


}
