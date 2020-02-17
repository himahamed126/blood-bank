package com.example.bloodbank.data.api;

import com.example.bloodbank.data.model.General.GenaralResponse;
import com.example.bloodbank.data.model.articles.Articles;
import com.example.bloodbank.data.model.donations.Donations;
import com.example.bloodbank.data.model.login.Login;
import com.example.bloodbank.data.model.notificationSettings.NotificationSettings;
import com.example.bloodbank.data.model.notifications.Notification;
import com.example.bloodbank.data.model.postToggle.PostToggle;
import com.example.bloodbank.data.model.restPassword.RestPassword;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServices {

    @POST("signup")
    @FormUrlEncoded
    Call<Login> signup(@Field("name") String name,
                       @Field("email") String email,
                       @Field("birth_date") String birth_date,
                       @Field("city_id") int city_id,
                       @Field("phone") String phone,
                       @Field("donation_last_date") String donation_last_date,
                       @Field("password") String password,
                       @Field("password_confirmation") String password_confirmation,
                       @Field("blood_type_id") int blood_type_id);

    @POST("login")
    @FormUrlEncoded
    Call<Login> login(@Field("phone") String phone,
                      @Field("password") String password);

    @GET("governorates")
    Call<GenaralResponse> getGovernorates();

    @GET("cities")
    Call<GenaralResponse> getCities(@Query("governorate_id") int governorate_id);

    @GET("blood-types")
    Call<GenaralResponse> getbloodTypes();

    @POST("reset-password")
    @FormUrlEncoded
    Call<RestPassword> resetPassword(@Field("phone") String phone);

    @POST("new-password")
    @FormUrlEncoded
    Call<RestPassword> newPassword(@Field("password") String password,
                                   @Field("password_confirmation") String password_confirmation,
                                   @Field("pin_code") String pin_code,
                                   @Field("phone") String phone);

    @GET("posts")
    Call<Articles> getArticles(@Query("api_token") String api_token,
                               @Query("page") int page);

    @POST("post-toggle-favourite")
    @FormUrlEncoded
    Call<PostToggle> postToggleFavourite(@Field("post_id") int post_id,
                                         @Field("api_token") String api_token);

    @GET("posts")
    Call<Articles> getArticlesWithFilter(@Query("api_token") String api_token,
                                         @Query("page") int page,
                                         @Query("keyword") String keyword,
                                         @Query("category_id") int category_id);

    @GET("categories")
    Call<GenaralResponse> getCategories();

    @GET("donation-requests")
    Call<Donations> getAllDonation(@Query("api_token") String api_token,
                                   @Query("page") int page);

    @GET("donation-requests")
    Call<Donations> getAllDonationWithFilter(@Query("api_token") String api_token,
                                             @Query("blood_type_id") int blood_type_id,
                                             @Query("city_id") int city_id,
                                             @Query("page") int page);

    @POST("donation-request/create")
    @FormUrlEncoded
    Call<Donations> addDonationRequset(@Field("api_token") String api_token,
                                       @Field("patient_name") String patient_name,
                                       @Field("patient_age") String patient_age,
                                       @Field("blood_type_id") int blood_type_id,
                                       @Field("bags_num") String bags_num,
                                       @Field("hospital_name") String hospital_name,
                                       @Field("hospital_address") String hospital_address,
                                       @Field("city_id") int city_id,
                                       @Field("phone") String phone,
                                       @Field("notes") String notes,
                                       @Field("latitude") String latitude,
                                       @Field("longitude") String longitude);

    @GET("my-favourites")
    Call<Articles> getFavoriteArticles(@Query("api_token") String api_token,
                                       @Query("page") int page);

    @GET("notifications")
    Call<Notification> getNotifications(@Query("api_token") String api_token,
                                        @Query("page") int page);

    @POST("notifications-settings")
    @FormUrlEncoded
    Call<NotificationSettings> getNotificationSettings(@Field("api_token") String api_token);

    @POST("notifications-settings")
    @FormUrlEncoded
    Call<NotificationSettings> changeNotificationSettings(@Field("api_token") String api_token,
                                                          @Field("governorates[]") List<Integer> governorates,
                                                          @Field("blood_types[]") List<Integer> blood_types);
}
