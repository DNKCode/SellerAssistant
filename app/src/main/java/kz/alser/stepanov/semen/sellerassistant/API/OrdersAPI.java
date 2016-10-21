package kz.alser.stepanov.semen.sellerassistant.API;

import kz.alser.stepanov.semen.sellerassistant.Models.OrdersProductsResponse;
import kz.alser.stepanov.semen.sellerassistant.Models.OrdersResponse;
import kz.alser.stepanov.semen.sellerassistant.Models.OrdersStatusesResponse;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by semen.stepanov on 21.10.2016.
 */

public interface OrdersAPI
{
    @GET ("/api/api.php?action=get_orders")
    Observable<OrdersResponse> loadOrders();

    @GET ("/api/api.php?action=get_orders_statuses")
    Observable<OrdersStatusesResponse> loadOrdersStatuses();

    @GET ("/api/api.php?action=get_orders_products")
    Observable<OrdersProductsResponse> loadOrdersProducts();
}