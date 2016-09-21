package kz.alser.stepanov.semen.sellerassistant.API;

import kz.alser.stepanov.semen.sellerassistant.Models.CategoryResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by semen.stepanov on 24.08.2016.
 */

public interface CategoriesAPI {
    //@GET ("/api/api.php?action=get_categories")
    //Call<Categories> loadCategoriesById(@Query ("id") String Ids);

    @GET ("/api/api.php?action=get_categories")
    Call<CategoryResponse> loadCategories();

    @GET ("/api/api.php?action=get_categories")
    Observable<CategoryResponse> loadCategoriesNew();
}
