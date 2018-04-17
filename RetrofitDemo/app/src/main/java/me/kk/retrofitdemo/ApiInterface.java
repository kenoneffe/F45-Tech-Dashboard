package me.kk.retrofitdemo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("/ReadContacts.php/")
    Call<List<Contact>> getContacts();
}
