package kz.alser.stepanov.semen.sellerassistant.Activities;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import kz.alser.stepanov.semen.sellerassistant.Adapters.OrdersAdapter;
import kz.alser.stepanov.semen.sellerassistant.Models.Orders;
import kz.alser.stepanov.semen.sellerassistant.R;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import static kz.alser.stepanov.semen.sellerassistant.Services.GetDataFromServer.ReloadOrdersFromServer;

public class OrdersActivity extends AppCompatActivity
{
    private RecyclerView OrdersRecyclerView;
    private SwipeRefreshLayout OrdersSwipeContainer;
    private OrdersAdapter ordersAdapter;
    private ProgressDialog pd;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        if (getSupportActionBar() == null)
        {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        OrdersRecyclerView = (RecyclerView) findViewById(R.id.rvOrders);
        OrdersRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        OrdersRecyclerView.setLayoutManager(llm);

        OrdersSwipeContainer = (SwipeRefreshLayout) findViewById(R.id.refreshOrdersSwipeView);
        OrdersSwipeContainer.setOnRefreshListener(() -> ReloadAllActiveOrdersFromServer());

        OrdersSwipeContainer.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        initOrdersSwipe();
        initOrders(GetOrderItems());
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.orders_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initOrdersSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                showLoadingView(getString(R.string.dialog_wait_delete_element_from_basket));
                /*DeleteElementFromCart(position)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                isOk -> { },
                                throwable -> hideLoadingViewWithError(throwable.getMessage()),
                                () -> {
                                    List<Cart> cartItem = new ArrayList<>();
                                    GetCartItemsWithObservable()
                                            .subscribeOn(Schedulers.newThread())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(
                                                    isOK -> cartItem.add(isOK),
                                                    throwable -> hideLoadingViewWithError(throwable.getMessage()),
                                                    () -> {
                                                        hideLoadingView();
                                                        cart.reloadCart(cartItem);
                                                        SetCartSum();
                                                    }
                                            );
                                }
                        );*/
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(OrdersRecyclerView);
    }

    private void initOrders(List<Orders> orders)
    {
        ordersAdapter = new OrdersAdapter(orders);
        ordersAdapter.setOnItemClickListener(new OrdersAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick (Orders ordersItems)
            {

            }

            @Override
            public void onItemLongClick (Orders ordersItems)
            {

            }
        });
        OrdersRecyclerView.setAdapter(ordersAdapter);
    }

    @NonNull
    private Observable<Orders> GetOrderItemsWithObservable()
    {
        return Observable.create(subscriber -> {
            List<Orders> items = Orders.listAll(Orders.class);

            for (Orders orders : items)
            {
                subscriber.onNext(orders);
            }

            subscriber.onCompleted();
            subscriber.unsubscribe();
        });
    }

    @NonNull
    private List<Orders> GetOrderItems()
    {
        OrdersSwipeContainer.setRefreshing(true);
        List<Orders> items = new ArrayList<>();
        final Subscription subscription = GetOrderItemsWithObservable()
                .subscribe(
                        isOk -> items.add(isOk),
                        throwable -> hideLoadingViewWithError(throwable.getMessage()),
                        () -> {
                            if (ordersAdapter ==  null || ordersAdapter.getItemCount() == 0)
                                initOrders(items);

                            OrdersSwipeContainer.setRefreshing(false);
                        }
                );

        subscription.unsubscribe();

        return items;
    }

    protected void showLoadingView(String messageText) {
        pd = new ProgressDialog(OrdersActivity.this);
        pd.setMessage(messageText);
        pd.setIndeterminate(true);
        pd.setTitle(getString(R.string.dialog_wait_title));
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();
    }

    protected void hideLoadingView() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }

        if (OrdersSwipeContainer != null && OrdersSwipeContainer.isRefreshing()) {
            OrdersSwipeContainer.setRefreshing(false);
        }
    }

    protected void hideLoadingViewWithError(String errorMessage) {
        if (!errorMessage.isEmpty())
            Toast.makeText(OrdersActivity.this, String.format(getString(R.string.error_message_load_categories_and_products), errorMessage), Toast.LENGTH_SHORT).show();

        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }

        if (OrdersSwipeContainer != null && OrdersSwipeContainer.isRefreshing()) {
            OrdersSwipeContainer.setRefreshing(false);
        }
    }

    protected void ReloadAllActiveOrdersFromServer()
    {
        showLoadingView(getString(R.string.dialog_wait_reload_orders_from_server));
        ReloadOrdersFromServer(OrdersActivity.this)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(3)
                .subscribe(r -> {
                            if (r.getRspCode() != 0)
                            {
                                hideLoadingViewWithError(r.getRspMessage());
                                return;
                            }

                            Orders.deleteAll(Orders.class);
                            List<Orders> orders = r.getOrders();
                            for (Orders order : orders)
                            {
                                order.save();
                            }

                            if (ordersAdapter ==  null || ordersAdapter.getItemCount() == 0)
                                initOrders(orders);
                            else
                                ordersAdapter.reloadOrders(orders);
                        }, throwable -> {
                            hideLoadingViewWithError(throwable.getMessage());
                            throwable.printStackTrace();
                        },
                        () -> hideLoadingView());
    }
}
