package com.example.bloodbank.data.api

import com.example.bloodbank.data.model.articles.Articles
import com.example.bloodbank.data.model.contactUs.ContactUs
import com.example.bloodbank.data.model.donations.Donations
import com.example.bloodbank.data.model.general.GeneralResponse
import com.example.bloodbank.data.model.login.Login
import com.example.bloodbank.data.model.notificationSettings.NotificationSettings
import com.example.bloodbank.data.model.notifications.Notification
import com.example.bloodbank.data.model.postToggle.PostToggle
import com.example.bloodbank.data.model.restPassword.RestPassword
import io.reactivex.Observable
import retrofit2.http.*

interface ApiServices {
    @POST("signup")
    @FormUrlEncoded
    fun signup(@Field("name") name: String,
               @Field("email") email: String,
               @Field("birth_date") birth_date: String,
               @Field("city_id") city_id: Int,
               @Field("phone") phone: String,
               @Field("donation_last_date") donation_last_date: String,
               @Field("password") password: String,
               @Field("password_confirmation") password_confirmation: String,
               @Field("blood_type_id") blood_type_id: Int): Observable<Login>

    @POST("login")
    @FormUrlEncoded
    fun login(@Field("phone") phone: String,
              @Field("password") password: String): Observable<Login>


    @get:GET("governorates")
    val governorates: Observable<GeneralResponse>

    @GET("cities")
    fun getCities(@Query("governorate_id") governorate_id: Int): Observable<GeneralResponse>

    @GET("blood-types")
    fun getbloodTypes(): Observable<GeneralResponse>

    @POST("reset-password")
    @FormUrlEncoded
    fun resetPassword(@Field("phone") phone: String): Observable<RestPassword>

    @POST("new-password")
    @FormUrlEncoded
    fun newPassword(@Field("password") password: String,
                    @Field("password_confirmation") password_confirmation: String,
                    @Field("pin_code") pin_code: String,
                    @Field("phone") phone: String): Observable<RestPassword>

    @GET("posts")
    fun getArticles(@Query("api_token") api_token: String,
                    @Query("page") page: Int): Observable<Articles>

    @POST("post-toggle-favourite")
    @FormUrlEncoded
    fun postToggleFavourite(@Field("post_id") post_id: Int,
                            @Field("api_token") api_token: String): Observable<PostToggle>

    @GET("posts")
    fun getArticlesWithFilter(@Query("api_token") api_token: String,
                              @Query("page") page: Int,
                              @Query("keyword") keyword: String,
                              @Query("category_id") category_id: Int): Observable<Articles>

    @get:GET("categories")
    val categories: Observable<GeneralResponse>

    @GET("donation-requests")
    fun getAllDonation(@Query("api_token") api_token: String,
                       @Query("page") page: Int): Observable<Donations>

    @GET("donation-requests")
    fun getAllDonationWithFilter(@Query("api_token") api_token: String,
                                 @Query("blood_type_id") blood_type_id: Int,
                                 @Query("city_id") city_id: Int,
                                 @Query("page") page: Int): Observable<Donations>

    @POST("donation-request/create")
    @FormUrlEncoded
    fun addDonationRequset(@Field("api_token") api_token: String,
                           @Field("patient_name") patient_name: String,
                           @Field("patient_age") patient_age: String,
                           @Field("blood_type_id") blood_type_id: Int,
                           @Field("bags_num") bags_num: String,
                           @Field("hospital_name") hospital_name: String,
                           @Field("hospital_address") hospital_address: String,
                           @Field("city_id") city_id: Int,
                           @Field("phone") phone: String,
                           @Field("notes") notes: String,
                           @Field("latitude") latitude: String,
                           @Field("longitude") longitude: String): Observable<Donations>

    @GET("my-favourites")
    fun getFavoriteArticles(@Query("api_token") api_token: String,
                            @Query("page") page: Int): Observable<Articles>

    @GET("notifications")
    fun getNotifications(@Query("api_token") api_token: String,
                         @Query("page") page: Int): Observable<Notification>

    @POST("notifications-settings")
    @FormUrlEncoded
    fun getNotificationSettings(@Field("api_token") api_token: String): Observable<NotificationSettings>

    @POST("notifications-settings")
    @FormUrlEncoded
    fun changeNotificationSettings(@Field("api_token") api_token: String,
                                   @Field("governorates[]") governorates: List<Int>,
                                   @Field("blood_types[]") blood_types: List<Int>): Observable<NotificationSettings>

    @POST("signup-token")
    @FormUrlEncoded
    fun registerNotificationToken(@Field("token") token: String,
                                  @Field("api_token") api_token: String,
                                  @Field("type") type: String): Observable<GeneralResponse>

    @POST("remove-token")
    @FormUrlEncoded
    fun removeNotificationToken(@Field("token") token: String,
                                @Field("api_token") api_token: String): Observable<GeneralResponse>

    @POST("remove-token")
    @FormUrlEncoded
    fun contactUs(@Field("api_token") api_token: String,
                  @Field("title") title: String,
                  @Field("message") message: String): Observable<ContactUs>

    @POST("profile")
    @FormUrlEncoded
    fun editProfileData(@Field("name") name: String,
                        @Field("email") email: String,
                        @Field("birth_date") birth_date: String,
                        @Field("city_id") city_id: Int,
                        @Field("phone") phone: String,
                        @Field("donation_last_date") donation_last_date: String,
                        @Field("password") password: String,
                        @Field("password_confirmation") password_confirmation: String,
                        @Field("blood_type_id") blood_type_id: Int,
                        @Field("api_token") api_token: String): Observable<Login>
}