package kz.alser.stepanov.semen.sellerassistant.API;

import kz.alser.stepanov.semen.sellerassistant.Models.ProductResponse;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by semen.stepanov on 09.09.2016.
 */
public interface ProductsAPI
{
    @GET ("/api/api.php?action=get_products")
    Observable<ProductResponse> loadProducts();
}
