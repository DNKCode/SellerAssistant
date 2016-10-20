package kz.alser.stepanov.semen.sellerassistant.Activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import kz.alser.stepanov.semen.sellerassistant.API.CategoriesAPI;
import kz.alser.stepanov.semen.sellerassistant.API.ProductsAPI;
import kz.alser.stepanov.semen.sellerassistant.Models.Category;
import kz.alser.stepanov.semen.sellerassistant.Models.CategoryResponse;
import kz.alser.stepanov.semen.sellerassistant.Models.Product;
import kz.alser.stepanov.semen.sellerassistant.Models.ProductResponse;
import kz.alser.stepanov.semen.sellerassistant.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyListActivity
        extends ListActivity
        implements Callback<CategoryResponse>
{
    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        setProgressBarIndeterminateVisibility(true);
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ustyle.kz")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // prepare call in Retrofit 2.0
        CategoriesAPI categoriesAPI = retrofit.create(CategoriesAPI.class);

        Call<CategoryResponse> call = categoriesAPI.loadCategories();
        //asynchronous call
        call.enqueue(this);

        // synchronous call would be with execute, in this case you
        // would have to perform this outside the main thread
        // call.execute()

        // to cancel a running request
        // call.cancel();
        // calls can only be used once but you can easily clone them
        //Call<StackOverflowQuestions> c = call.clone();
        //c.enqueue(this);

        return true;
    }

    public void CallRetrofit()
    {
        try
        {
            setProgressBarIndeterminateVisibility(true);
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ustyle.kz")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            // prepare call in Retrofit 2.0
            CategoriesAPI categoriesAPI = retrofit.create(CategoriesAPI.class);

            Call<CategoryResponse> call = categoriesAPI.loadCategories();
            //asynchronous call
            call.enqueue(this);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void CallRetrofitNew()
    {
        try
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://ustyle.kz")
                    .build();

            CategoriesAPI categoriesAPI = retrofit.create(CategoriesAPI.class);
            Observable<CategoryResponse> responseObservable = categoriesAPI.loadCategoriesNew();

            responseObservable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(r -> {

                        if (r.getRspCode() != 0)
                        {
                            Toast.makeText(MyListActivity.this, String.format("Возникла ошибка при обновлении категорий: %s", r.getRspMessage()), Toast.LENGTH_SHORT).show();
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
        }
    }

    public void GetAllProducts()
    {
        try
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://ustyle.kz")
                    .build();

            ProductsAPI productsAPI = retrofit.create(ProductsAPI.class);
            Observable<ProductResponse> productsResponse = productsAPI.loadProducts();

            productsResponse.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(r -> {

                        if (r.getRspCode() != 0)
                        {
                            Toast.makeText(MyListActivity.this, String.format("Возникла ошибка при обновлении категорий: %s", r.getRspMessage()), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        List<Product> products = r.getProducts();
                        for (Product product : products)
                        {
                            product.save();
                        }

                    }, throwable -> {
                        throwable.printStackTrace();
                    });
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_list);

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        ArrayAdapter<Category> arrayAdapter =
                new ArrayAdapter<Category>(this,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        new ArrayList<Category>());
        setListAdapter(arrayAdapter);
        setProgressBarIndeterminateVisibility(true);
        setProgressBarVisibility(true);

        if (Category.count(Category.class) == 0) {
            //CallRetrofit();
            CallRetrofitNew();
        }

        if (Product.count(Product.class) == 0)
        {
            GetAllProducts();
        }
    }

    @Override
    public void onResponse (Call<CategoryResponse> call, Response<CategoryResponse> response)
    {
        int rspcode = 0;
        String rspmes = "";
        List<Category> categoryList = new ArrayList<Category>();

        try
        {
            setProgressBarIndeterminateVisibility(false);
            ArrayAdapter<Category> adapter = (ArrayAdapter<Category>) getListAdapter();
            adapter.clear();

            if (response.body().getRspCode() != 0)
            {
                rspcode = response.body().getRspCode();
                rspmes = response.body().getRspMessage();
                categoryList = response.body().getCategories();

                if (rspmes.isEmpty())
                    rspmes = "empty";

                if (categoryList == null)
                    categoryList = new ArrayList<Category>();
            }

            adapter.addAll(categoryList);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();

            //Toast.makeText(MyListActivity.this, rspmes, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure (Call<CategoryResponse> call, Throwable t)
    {
        Toast.makeText(MyListActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }
}
