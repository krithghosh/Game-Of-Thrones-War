package com.got.krith.gameofthrones.network;

import com.got.krith.gameofthrones.model.Battle;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by krith on 04/03/17.
 */

public interface RestApiService {

    @GET("/lookup/war")
    Observable<List<Battle>> getBattles();
}
