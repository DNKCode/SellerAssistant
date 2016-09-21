package kz.alser.stepanov.semen.sellerassistant.Services;

import android.content.res.Resources;
import java.util.List;
import kz.alser.stepanov.semen.sellerassistant.API.CategoriesAPI;
import kz.alser.stepanov.semen.sellerassistant.Models.Category;
import kz.alser.stepanov.semen.sellerassistant.Models.CategoryResponse;
import kz.alser.stepanov.semen.sellerassistant.Models.ResponseResult;
import kz.alser.stepanov.semen.sellerassistant.R;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by semen.stepanov on 15.09.2016.
 */
public class GetDataFromServer
{
    public GetDataFromServer()
    {
    }

    public static ResponseResult GetCategoriesFromServer()
    {
        ResponseResult response = new ResponseResult();

        try
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Resources.getSystem().getString(R.string.base_url))
                    .build();

            CategoriesAPI categoriesAPI = retrofit.create(CategoriesAPI.class);
            Observable<CategoryResponse> responseObservable = categoriesAPI.loadCategoriesNew();

            responseObservable
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(r -> {

                        if (r.getRspCode() != 0)
                        {
                            //Toast.makeText(context.getApplicationContext(), String.format("Возникла ошибка при обновлении категорий: %s", r.getRspMessage()), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        List<Category> categories = r.getCategories();

                        for (Category category : categories)
                        {
                            category.save();
                        }

                    }, throwable -> {
                        throwable.printStackTrace();
                    });
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            response.setRspCode(1);
            response.setRspMessage(ex.getMessage());
        }

        return response;
    }
}
