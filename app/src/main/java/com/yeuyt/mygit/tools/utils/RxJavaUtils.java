package com.yeuyt.mygit.tools.utils;



import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;


public class RxJavaUtils {

    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
    public static ObservableTransformer<Response<ResponseBody>, Boolean> checkSuccessCode() {
        return new ObservableTransformer<Response<ResponseBody>, Boolean>() {
            @Override
            public ObservableSource<Boolean> apply(Observable<Response<ResponseBody>> upstream) {
                return upstream.map(new Function<Response<ResponseBody>, Boolean>() {
                    @Override
                    public Boolean apply(Response<ResponseBody> responseBodyResponse) throws Exception {
                        return responseBodyResponse != null && responseBodyResponse.code() == 204;
                    }
                });
            }
        };
    }
}
