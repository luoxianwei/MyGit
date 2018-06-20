package com.yeuyt.mygit.model.net.service;

import com.yeuyt.mygit.model.entity.GankResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GankService {
    @GET("{page_size}/{page}")
    Observable<GankResponse> getGankData(@Path("page_size") int pageSize, @Path("page") int page);
}
