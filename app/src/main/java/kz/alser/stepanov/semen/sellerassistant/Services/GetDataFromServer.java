package kz.alser.stepanov.semen.sellerassistant.Services;

import android.content.Context;

import kz.alser.stepanov.semen.sellerassistant.API.CategoriesAPI;
import kz.alser.stepanov.semen.sellerassistant.API.LanguagesAPI;
import kz.alser.stepanov.semen.sellerassistant.API.OrdersAPI;
import kz.alser.stepanov.semen.sellerassistant.API.ProductsAPI;
import kz.alser.stepanov.semen.sellerassistant.Models.CategoryResponse;
import kz.alser.stepanov.semen.sellerassistant.Models.LanguagesResponse;
import kz.alser.stepanov.semen.sellerassistant.Models.OrdersProductsResponse;
import kz.alser.stepanov.semen.sellerassistant.Models.OrdersResponse;
import kz.alser.stepanov.semen.sellerassistant.Models.OrdersStatusesResponse;
import kz.alser.stepanov.semen.sellerassistant.Models.ProductResponse;
import kz.alser.stepanov.semen.sellerassistant.R;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by semen.stepanov on 15.09.2016.
 */
public class GetDataFromServer
{
    public static Observable<LanguagesResponse> ReloadLanguagesFromServer(Context context)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(context.getString(R.string.base_url))
                .build();

        LanguagesAPI languagesAPI = retrofit.create(LanguagesAPI.class);
        return languagesAPI.loadLanguages();
    }

    public static Observable<ProductResponse> ReloadProductsFromServer(Context context)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(context.getString(R.string.base_url))
                .build();

        ProductsAPI productsAPI = retrofit.create(ProductsAPI.class);
        return productsAPI.loadProducts();
    }

    public static Observable<CategoryResponse> ReloadCategoriesFromServer(Context context)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(context.getString(R.string.base_url))
                .build();

        CategoriesAPI categoriesAPI = retrofit.create(CategoriesAPI.class);
        return categoriesAPI.loadCategoriesNew();
    }

    public static Observable<OrdersResponse> ReloadOrdersFromServer(Context context)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(context.getString(R.string.base_url))
                .build();

        OrdersAPI ordersAPI = retrofit.create(OrdersAPI.class);
        return ordersAPI.loadOrders();
    }

    public static Observable<OrdersStatusesResponse> ReloadOrderStatusesFromServer(Context context)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(context.getString(R.string.base_url))
                .build();

        OrdersAPI ordersAPI = retrofit.create(OrdersAPI.class);
        return ordersAPI.loadOrdersStatuses();
    }

    public static Observable<OrdersProductsResponse> ReloadOrderProductsFromServer(Context context)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(context.getString(R.string.base_url))
                .build();

        OrdersAPI ordersAPI = retrofit.create(OrdersAPI.class);
        return ordersAPI.loadOrdersProducts();
    }
}
