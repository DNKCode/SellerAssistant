package kz.alser.stepanov.semen.sellerassistant;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.orm.query.Condition;
import com.orm.query.Select;
import java.util.ArrayList;
import java.util.List;

import kz.alser.stepanov.semen.sellerassistant.API.CategoriesAPI;
import kz.alser.stepanov.semen.sellerassistant.API.ProductsAPI;
import kz.alser.stepanov.semen.sellerassistant.Adapters.CartAdapter;
import kz.alser.stepanov.semen.sellerassistant.Models.Cart;
import kz.alser.stepanov.semen.sellerassistant.Models.Category;
import kz.alser.stepanov.semen.sellerassistant.Models.CategoryResponse;
import kz.alser.stepanov.semen.sellerassistant.Models.Items4Selection;
import kz.alser.stepanov.semen.sellerassistant.Models.Product;
import kz.alser.stepanov.semen.sellerassistant.Models.ProductResponse;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity
        extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    private Observable<String> myObservable;
    private Subscriber<String> mySubscriber;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private Dialog dialog;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeContainer;
    private Paint p = new Paint();
    private ItemsAdapter adapter;

    private RecyclerView CartRecyclerView;
    private SwipeRefreshLayout CartSwipeContainer;
    private CartAdapter cart;


    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CartRecyclerView = (RecyclerView) findViewById(R.id.rvCart);
        CartRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        CartRecyclerView.setLayoutManager(llm);
        CartSwipeContainer = (SwipeRefreshLayout) findViewById(R.id.refreshCartSwipeView);
        CartSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //
            }
        });
        CartSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        initCartSwipe();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v)
            {
                dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setContentView(R.layout.categories_container);

                final Button buttonCancel = (Button) dialog.findViewById(R.id.btn_close_categories_dialog);
                buttonCancel.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dialog.dismiss();
                    }
                });

                recyclerView = (RecyclerView) dialog.findViewById(R.id.rv);
                recyclerView.setHasFixedSize(true);
                //recyclerView.setItemAnimator(new SlideInUpAnimator());

                LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(llm);

                List<Items4Selection> items = GetItemsByParentId(0);
                adapter = new ItemsAdapter(items);
                adapter.setOnItemClickListener(new ItemsAdapter.OnItemClickListener()
                {
                    @Override
                    public void onItemClick (Items4Selection item)
                    {
                        Click2DialogItems(item);
                    }

                    @Override
                    public void onItemLongClick (Items4Selection item)
                    {
                        Click2DialogItems(item);
                    }
                });

                recyclerView.setAdapter(adapter);

                swipeContainer = (SwipeRefreshLayout) dialog.findViewById(R.id.freshSwipeView);

                swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        //Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        //swipeContainer.setRefreshing(false);
                        ReloadCategoriesFromServer();
                        ReloadProductsFromServer();
                    }
                });

                swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);

                initSwipe();

                dialog.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        try
        {
            myObservable = Observable.create(new Observable.OnSubscribe<String>()
            {
                @Override
                public void call (Subscriber<? super String> sub)
                {
                    sub.onNext("Hello, world!");
                    sub.onCompleted();
                }
            });

            mySubscriber = new Subscriber<String>()
            {
                @Override
                public void onNext (String s)
                {
                    System.out.println(s);
                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCompleted ()
                {
                }

                @Override
                public void onError (Throwable e)
                {

                }
            };
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        //Button btn = (Button) findViewById(R.id.btnTestLambda);
        //btn.setOnClickListener(e -> Toast.makeText(this, "Hello", Toast.LENGTH_LONG).show());

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onBackPressed ()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            try
            {
                //myObservable.subscribe(mySubscriber);

                /*Observable.just("1", "2")
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                System.out.println(s);
                                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                            }
                        });*/

                Observable.just("Hello, world!")
                        .map(s -> s + " -Dan")
                        .map(s -> s.hashCode())
                        .map(i -> Integer.toString(i))
                        .subscribe(s -> Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show());

                query("Hello, world!")
                        .flatMap(urls -> Observable.from(urls))
                        .subscribe(url -> System.out.println(url));
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

            return true;
        }

        else if (id == R.id.menu_load)
        {
            Intent refresh = new Intent(this, MyListActivity.class);
            startActivity(refresh);
        }

        return super.onOptionsItemSelected(item);
    }

    private Observable<List<String>> query (String text)
    {
        try
        {
            List<String> result = new ArrayList<String>();

            result.add("test");
            result.add("test2");

            return Observable.just(result);
            /*return Observable.create(new Observable.OnSubscribe<List<String>>()
            {
                @Override
                public void call (Subscriber<? super List<String>> subscriber)
                {
                    try
                    {
                        List<String> result = new ArrayList<String>();

                        result.add("test");
                        result.add("test2");

                        //subscriber.onNext(result);    // Pass on the data to subscriber
                        //subscriber.onCompleted();     // Signal about the completion subscriber
                    }
                    catch (Exception e)
                    {
                        //subscriber.onError(e);        // Signal about the error to subscriber
                    }
                }
            });*/
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings ("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected (MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera)
        {
            // Handle the camera action
        }
        else if (id == R.id.nav_gallery)
        {

        }
        else if (id == R.id.nav_slideshow)
        {

        }
        else if (id == R.id.nav_manage)
        {

        }
        else if (id == R.id.nav_share)
        {

        }
        else if (id == R.id.nav_send)
        {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart ()
    {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://kz.alser.stepanov.semen.sellerassistant/http/host/path"));
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop ()
    {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://kz.alser.stepanov.semen.sellerassistant/http/host/path"));
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private void initSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT){
                    adapter.removeItem(position);
                } else {
                    //removeView();
                    //edit_position = position;
                    //alertDialog.setTitle("Edit Country");
                    //et_country.setText(countries.get(position));
                    //alertDialog.show();
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Resources res = getResources();
                Bitmap icon;
                icon = BitmapFactory.decodeResource(res, R.mipmap.nofoto);

                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX > 0){
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        //icon = BitmapFactory.decodeResource(res, R.drawable.ic_menu_camera);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        //icon = BitmapFactory.decodeResource(res, R.drawable.ic_menu_share);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void initCartSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                adapter.removeItem(position);
                adapter.notifyDataSetChanged();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(CartRecyclerView);
    }

    private List<Items4Selection> GetItemsByParentId(int itemId)
    {
        List<Items4Selection> items = new ArrayList<Items4Selection>();

        try
        {
            List<Category> categories = Category.find(Category.class, "CATEGORY_PARENT_ID = ?", String.valueOf(itemId));
            List<Product> products = Product.find(Product.class, "CATEGORY_ID = ?", String.valueOf(itemId));

            for (Category category : categories)
            {
                Items4Selection item = new Items4Selection(
                        Integer.valueOf(category.getCategoryId()),
                        Integer.valueOf(category.getCategoryParentId()),
                        category.getCategoryDescription(),
                        Integer.valueOf(category.getCategoryId()),
                        category.getCategoryImagePath(),
                        category.getCategoryName(),
                        0,
                        0,
                        category.getCategorySortOrder(),
                        Items4Selection.Item_Type.CATEGORY
                );

                items.add(item);
            }

            for (Product product : products)
            {
                Items4Selection item = new Items4Selection(
                        Integer.valueOf(product.getCategoryId()),
                        0,
                        product.getProductDescription (),
                        Integer.valueOf(product.getProductId()),
                        product.getProductImagePath (),
                        product.getProductName(),
                        Double.valueOf(product.getProductPrice()),
                        Integer.valueOf(product.getProductQuantity()),
                        product.getProductSortOrder(),
                        Items4Selection.Item_Type.PRODUCT
                );

                items.add(item);
            }

            if (itemId != 0)
            {
                String itemParentId = Select
                        .from(Category.class)
                        .where(Condition.prop("CATEGORY_ID").eq(String.valueOf(itemId)))
                        .first()
                        .getCategoryParentId();

                Items4Selection item = new Items4Selection(
                        Integer.valueOf(itemParentId),
                        Integer.valueOf(itemParentId),
                        "",
                        Integer.valueOf(itemParentId),
                        "",
                        "НАЗАД",
                        0,
                        0,
                        "9999",
                        Items4Selection.Item_Type.BACK_MENU
                );

                items.add(item);
            }
        }
        catch (Exception ex)
        {

        }

        return items;
    }

    public void ReloadCategoriesFromServer()
    {
        try
        {
            swipeContainer.setRefreshing(true);
            Category.deleteAll(Category.class);

            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(getString(R.string.base_url))
                    .build();

            CategoriesAPI categoriesAPI = retrofit.create(CategoriesAPI.class);
            Observable<CategoryResponse> responseObservable = categoriesAPI.loadCategoriesNew();

            responseObservable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(r -> {

                        if (r.getRspCode() != 0)
                        {
                            Toast.makeText(MainActivity.this, String.format("Возникла ошибка при обновлении категорий: %s", r.getRspMessage()), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        List<Category> categories = r.getCategories();

                        for (Category category : categories)
                        {
                            category.save();
                        }

                        List<Items4Selection> items = GetItemsByParentId(0);
                        adapter.reloadCategories(items);

                        swipeContainer.setRefreshing(false);

                    }, throwable -> {
                        swipeContainer.setRefreshing(false);
                        Toast.makeText(MainActivity.this, String.format("Возникла ошибка при обновлении категорий: %s", throwable.getMessage()), Toast.LENGTH_SHORT).show();
                        throwable.printStackTrace();
                    });
        }
        catch (Exception ex)
        {
            swipeContainer.setRefreshing(false);
            Toast.makeText(MainActivity.this, String.format("Возникла ошибка при обновлении категорий: %s", ex.getMessage()), Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }

    public void ReloadProductsFromServer()
    {
        try
        {
            swipeContainer.setRefreshing(true);
            Product.deleteAll(Product.class);

            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(getString(R.string.base_url))
                    .build();

            ProductsAPI productsAPI = retrofit.create(ProductsAPI.class);
            Observable<ProductResponse> productsResponse = productsAPI.loadProducts();

            productsResponse.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(r -> {

                        if (r.getRspCode() != 0)
                        {
                            Toast.makeText(MainActivity.this, String.format("Возникла ошибка при обновлении продуктов: %s", r.getRspMessage()), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        List<Product> products = r.getProducts();
                        for (Product product : products)
                        {
                            product.save();
                        }

                        List<Items4Selection> items = GetItemsByParentId(0);
                        adapter.reloadCategories(items);

                        swipeContainer.setRefreshing(false);

                    }, throwable -> {
                        swipeContainer.setRefreshing(false);
                        Toast.makeText(MainActivity.this, String.format("Возникла ошибка при обновлении продуктов: %s", throwable.getMessage()), Toast.LENGTH_SHORT).show();
                        throwable.printStackTrace();
                    });
        }
        catch (Exception ex)
        {
            swipeContainer.setRefreshing(false);
            Toast.makeText(MainActivity.this, String.format("Возникла ошибка при обновлении продуктов: %s", ex.getMessage()), Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }

    public void Click2DialogItems(Items4Selection item)
    {
        swipeContainer.setRefreshing(true);

        if (item != null)
        {
            if (item.getItemType() == Items4Selection.Item_Type.PRODUCT)
            {
                AddItem2Cart(item);
                swipeContainer.setRefreshing(false);

                if (dialog != null)
                    dialog.dismiss();

                return;
            }

            List<Items4Selection> items = GetItemsByParentId(item.getCategoryId());

            if (items.size() > 0)
            {
                adapter.reloadCategories(items);
            }
        }

        swipeContainer.setRefreshing(false);
    }

    public void AddItem2Cart(Items4Selection item)
    {
        CartSwipeContainer.setRefreshing(true);

        if (item != null)
        {
            Cart newItem = new Cart();
            newItem.setBeginPrice(item.getItemPrice());
            newItem.setEndPrice(item.getItemPrice());
            newItem.setQuantity(item.getItemQuantity());
            newItem.setCategoryId(item.getCategoryId());
            newItem.setProductDescription(item.getItemDescription());
            newItem.setProductId(item.getItemId());
            newItem.setProductImagePath(item.getItemImagePath());
            newItem.setProductName(item.getItemName());

            if (cart == null || cart.getItemCount() == 0)
            {
                List<Cart> cart = new ArrayList<Cart>();
                cart.add(newItem);

                initCart(cart);
            }
            else
            {
                cart.addItem(newItem);
            }
        }

        CartSwipeContainer.setRefreshing(false);
    }

    public void initCart(List<Cart> cartItems)
    {
        cart = new CartAdapter(cartItems);
        cart.setOnItemClickListener(new CartAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick (Cart cartItem)
            {

            }

            @Override
            public void onItemLongClick (Cart cartItem)
            {

            }
        });
        CartRecyclerView.setAdapter(cart);
    }
}