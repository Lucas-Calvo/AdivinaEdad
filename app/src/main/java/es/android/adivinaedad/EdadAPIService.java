package es.android.adivinaedad;

import android.text.Editable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EdadAPIService {

    @GET("/")
    Call<EdadEstimada> getEdad(@Query("name") Editable nombre);
}
