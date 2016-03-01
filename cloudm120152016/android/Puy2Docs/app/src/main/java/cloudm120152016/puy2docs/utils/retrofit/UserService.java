package cloudm120152016.puy2docs.utils.retrofit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cloudm120152016.puy2docs.models.Friend;
import cloudm120152016.puy2docs.models.Item;
import cloudm120152016.puy2docs.models.Token;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface UserService {
    /***********************************************/
    // Authentication
    /***********************************************/
    // Sign up
    @FormUrlEncoded
    @POST("/auth/")
    Call<ResponseBody> join(@Field("mail") String email,
                            @Field("login") String username,
                            @Field("pwd") String password,
                            @Field("lname") String last_name,
                            @Field("fname") String first_name,
                            @Field("birthday") String birthday);

    // Get token
    @FormUrlEncoded
    @POST("/token/")
    Call<Token> login(@Field("username") String username, @Field("password") String password);

    /***********************************************/
    // Folders & files
    /***********************************************/

    // Get root contenant
    @GET("/tree/")
    Call<ArrayList<Item>> getRoot();

    // Add a folder on root
    @FormUrlEncoded
    @POST("/tree/")
    Call<ResponseBody> putRoot(@Field("name") String name);

    // Get folder contenant
    @GET("/tree/{id_tree}/")
    Call<ArrayList<Item>> getFolder(@Path("id_tree") String id);

    // Create a folder in a folder
    @FormUrlEncoded
    @POST("/tree/{id_tree}/")
    Call<ResponseBody> putFolder(@Path("id_tree") String id, @Field("name") String name);

    //@DELETE("/supp/folder/{id_folder}/")
    @HTTP(method = "DELETE", path = "/supp/folder/{id_folder}/", hasBody = true)
    Call<ResponseBody> deleteFolder(@Path("id_folder") String id);

    // Get a document by id
    //@GET("/document/{id_document}")
    //Call<ResponseBody> getDocument(@Path("id_document") String id);

    // Create a document in root folder
    @Multipart
    @POST("/document/")
    Call<ResponseBody> putDocument(@PartMap() Map<String, RequestBody> file);
    //Call<ResponseBody> putDocumentAtRoot(@Part("file; filename=\"pp.png") RequestBody file);

    // Put a document in a fodler by id
    @Multipart
    @POST("/document/{id_document}/")
    Call<ResponseBody> putDocument(@Path("id_document") String id, @PartMap() Map<String, RequestBody> file);

    @DELETE("/supp/document/{id_document}/")
    Call<ResponseBody> deleteDocument(@Path("id_document") String id);

    // Update an existing file (On va pas l'utiliser car on ne gère pas ce cas)
    @Multipart
    @POST("/update/{id_document}/")
    Call<ResponseBody> updateFile(@Path("id_document") String id, @Part("file") RequestBody file, @Field("message") String message);

    /***********************************************/
    // Other features
    /***********************************************/

    // Get list of friends
    @GET("/friends/")
    Call<List<Friend>> friends();

    @POST("/friend/")
    Call<ResponseBody> addFriend();

}
