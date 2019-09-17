package com.voyager.boorna.webservices;



import androidx.annotation.Nullable;

import com.voyager.boorna.activity.login.model.UserDetails;
import com.voyager.boorna.services.model.DriverDetails;
import com.voyager.boorna.services.model.DriverLocDetails;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServices {
    //@GET("driver/getDriver/")
    // Call<MainClass> doGetUserList(@Query("page") String page);
    //http://10.1.1.18/sayara/user/booking/--pickup_loc: 9.731235,76.355463 -- user_id 89
    @FormUrlEncoded
    @POST("authentication/Login")
    Observable<UserDetails> loginUser(@Nullable @Field("user_email") String userName,
                                         @Nullable @Field("pass_word") String password,
                                         @Nullable @Field("fcm") String fcmToken
                                      );
    @FormUrlEncoded
    @POST("driverLocationSave")
    Call<DriverDetails> driverProfileStatus(@Nullable @Field("driver_id") int driverID,
                                                  @Nullable @Field("vehicle_id") int vehicleId,
                                                  @Nullable @Field("level_code") String level_code,
                                                  @Nullable @Field("lat") double driverLatitude,
                                                  @Nullable @Field("long") double driverLongitude,
                                                  @Nullable @Field("datetime") String dateTime);

    /*   @GET("listings/4/0")
       Call<MainList> doGetHouseList();
       @GET("sliders")
       Call<ArrayList<Banner>> doGetbannerlist();
       @GET("locations/2/0")
       Call<ArrayList<LocItems>> doGetLocList();
       @GET("listings/{id}")
       Call<HomeDetails> doGetHomeDetails(@Path("id") int id);*/
   /* @FormUrlEncoded
    @POST("home")
    Call<MainList> doGetDetails(@Nullable @Field("user_id") Integer userID);*/
   /* @FormUrlEncoded
    @POST("home")
    Observable<MainList> doGetDetails1(@Nullable @Field("user_id") Integer userID);

    *//*Here limit is the total list count to be fetched
    * and offset is the pointer form which data fetching should start form *//**//*
    @GET("listings/{limit}/{offset}")
    Call<MainList> updateHouseList(@Path("limit") int limit, @Path("offset") int offset);*//*


    @GET("listings/{limit}/{offset}")
    Observable<MainList> updateHouseList(@Path("limit") int limit, @Path("offset") int offset);

    @FormUrlEncoded
    @POST("propertyLike")
    Observable<LikeUnLike> propertyLike(@Nullable @Field("user_id") Integer userID,
                                        @Nullable @Field("property_id") Integer propertyID);

    @FormUrlEncoded
    @POST("propertyUnlike")
    Observable<LikeUnLike> propertyUnlike(@Nullable @Field("user_id") Integer email,
                                          @Nullable @Field("property_id") Integer papropertyIDsswd);


    @FormUrlEncoded
    @POST("listings/{propertyId}")
    Observable<HomeDetails> doGetHomeDetails(@Path("propertyId") int propertyId,
                                             @Nullable @Field("user_id") Integer userID);

    @FormUrlEncoded
    @POST("calenderRates")
    Observable<PriceDetails> getPriceDetails(@Nullable @Field("property_id") Integer propertyId,
                                             @Nullable @Field("guests_num") Integer guestNo,
                                             @Nullable @Field("checkin") String checkin,
                                             @Nullable @Field("checkout") String checkout,
                                             @Nullable @Field("user_id") Integer userID);

    @FormUrlEncoded
    @POST("listings/{limit}/{offset}/{typeId}")
    Observable<TypedDetail> doGetTypedDetails(@Path("limit") int limit,
                                              @Path("offset") int offset,
                                              @Path("typeId") int typeId,
                                              @Nullable @Field("user_id") Integer userID);

    @FormUrlEncoded
    @POST("locationwise")
    Observable<ArrayList<LocDetails>> getLocDetails(@Nullable @Field("user_id") Integer userID,
                                                    @Nullable @Field("location") String locName);

    @FormUrlEncoded
    @POST("LikedListings")
    Observable<ArrayList<FavDetail>> getFavDetails(@Nullable @Field("user_id") Integer userID);


    @FormUrlEncoded
    @POST("mobileLogin")
    public Observable<UserDetails> loginNormalUser(@Nullable @Field("email") String email,
                                                   @Nullable @Field("password") String passwd,
                                                   @Nullable @Field("login_type") String type,
                                                   @Nullable @Field("fcm") String fireBaseToken);

    @FormUrlEncoded
    @POST("mobileLogin")
    public Observable<UserDetails> loginGoogleUser(@Nullable @Field("email") String email,
                                                   @Nullable @Field("login_type") String type,
                                                   @Nullable @Field("profile_image") String imageP,
                                                   @Nullable @Field("phonenumber") String mobNo,
                                                   @Nullable @Field("userName") String userName,
                                                   @Nullable @Field("googleId") String googleId,
                                                   @Nullable @Field("fcm") String fireBaseToken);

    @FormUrlEncoded
    @POST("mobileLogin")
    public Observable<UserDetails> loginFBUser(@Nullable @Field("email") String email,
                                               @Nullable @Field("login_type") String type,
                                               @Nullable @Field("profile_image") String imageP,
                                               @Nullable @Field("phonenumber") String mobNo,
                                               @Nullable @Field("userName") String userName,
                                               @Nullable @Field("token") String fireBaseToken);

    @FormUrlEncoded
    @POST("createUser")
    public Observable<UserDetails> registerUser(@Nullable @Field("first_name") String fname,
                                                @Nullable @Field("last_name") String lname,
                                                @Nullable @Field("password") String password,
                                                @Nullable @Field("email") String email,
                                                @Nullable @Field("phone_num") String phone,
                                                @Nullable @Field("date_of_birth") String dob,
                                                @Nullable @Field("login_type") String type);


    @FormUrlEncoded
    @POST("createUser")
    public Observable<UserDetails> updateProfile(@Nullable @Field("first_name") String fname,
                                                 @Nullable @Field("last_name") String lname,
                                                 @Nullable @Field("user_id") int userId);

    @FormUrlEncoded
    @POST("myProfile")
    public Observable<UserPropertyDetails> getPropertiesDetail(@Nullable @Field("user_id") int userId);*/
/*
    @GET("webservice/getOffertypes")
    Call<OfferList> doGetUserList();

    @FormUrlEncoded
    @POST("register/")
    public Call<UserDetail> registerUser(@Nullable @Field("name") String name,
                                          @Nullable @Field("password") String password,
                                          @Nullable @Field("email") String email,
                                          @Nullable @Field("phone") String phone,
                                          @Nullable @Field("country") String country,
                                          @Nullable @Field("city") String city);
    @FormUrlEncoded
    @POST("login/")
    public Call<UserDetail> loginUser(@Nullable @Field("email") String email,
                                       @Nullable @Field("password") String passwd,
                                       @Nullable @Field("token") String fireBaseToken);

    @FormUrlEncoded
    @POST("updateProfile/")
    Call<UserDetail> updateProfile(@Nullable @Field("name") String name,
                                    @Nullable @Field("userID") int id,
                                    @Nullable @Field("password") String password,
                                    @Nullable @Field("phone") String phone,
                                    @Nullable @Field("country") String country,
                                    @Nullable @Field("city") String city);
    @FormUrlEncoded
    @POST("updateProfile/")
    Call<UserDetail> updateProfilePass(@Nullable @Field("password") String name,
                                        @Nullable @Field("user_id") int id);
    @FormUrlEncoded
    @POST("updateProfile/")
    Call<UserDetail> updateProfileName(@Nullable @Field("name") String name,
                                        @Nullable @Field("user_id") int id,
                                        @Nullable @Field("password") String password,
                                        @Nullable @Field("city") String city);
    @FormUrlEncoded
    @POST("updateProfile/")
    Call<UserDetail> updateProfilePhno(@Nullable @Field("phone") String name,
                                        @Nullable @Field("user_id") int id);
    @FormUrlEncoded
    @POST("updateProfile/")
    Call<UserDetail> updateProfileCity(@Nullable @Field("city") String name,
                                        @Nullable @Field("user_id") int id);
    @FormUrlEncoded
    @POST("tripHistory/")
    Call<List<TripDetails>> getTripHistory(@Field("user_id") int userId,
                                           @Nullable @Field("page") int page);

    @FormUrlEncoded
    @POST("driverProfile/")
    Call<DTDModel> getDriverProfileDetail(@Nullable @Field("driver_id") int userId,
                                          @Nullable @Field("user_id") int tripId);
    @FormUrlEncoded
    @POST("cancelTrip/")
    Call<EndTrip> stopStartUpTrip(@Nullable @Field("user_id") int userId,
                                  @Nullable @Field("trip_id") int tripId);
    @FormUrlEncoded
    @POST("cancelTrip/")
    Call<EndTrip> endOnGoingTrip(@Nullable @Field("user_id") int userId,
                                 @Nullable @Field("trip_id") int tripId);


    @FormUrlEncoded
    @POST("logout/")
    Call<UserDetail> logOut(@Nullable @Field("user_id") int userId,
                             @Nullable @Field("logout") int value);

    @POST("FCMUpdateServlet")
    public Call<UserDetail> updateFCMId(@Nullable @Field("user_id") int userId,
                                         @Nullable @Field("token") String token);
    @FormUrlEncoded
    @POST("confirmTrip/")
    public Call<TripResponse> startTrip(@Nullable @Field("user_id") int userId,
                                        @Nullable @Field("user_name") String userName,
                                        @Nullable @Field("pickup_loc") String nameStartLoc,
                                        @Nullable @Field("pickup_address") String nameStart,
                                        @Nullable @Field("drop_loc") String nameEndLoc,
                                        @Nullable @Field("drop_address") String nameEnd,
                                        @Nullable @Field("distance") String distanceKm,
                                        @Nullable @Field("amount") String costFairSet,
                                        @Nullable @Field("car_id") String driveCarId,
                                        @Nullable @Field("pay_type") String paymentType);

    @Multipart
    @POST("updateProfile/")
    public Call<UserDetail> uploadProfileImg(@Part MultipartBody.Part profileImg, @Part("user_id") RequestBody userID);

    @GET("place/autocomplete/json?")
    Call<PlacesResults> getPlaceSearch(@Query("input") String input, @Query("types") String types, @Query("key") String key);
    //https://developers.google.com/maps/documentation/directions/intro
    @GET("directions/json?")
    Call<GetPaths> getTripRoute(@Query("origin") String source, @Query("destination") String destination, @Query("key") String key);
    @GET("place/details/json?")
    Call<PlaceDetail> getPlaceDetail(@Query("placeid") String source, @Query("key") String key);
    @GET("directions/json?")
    public Call<GetPaths> getPaths(@Query("origin") String origin, @Query("destination") String dest, @Query("sensor") Boolean sensor, @Query("key") String key);
*/


}
