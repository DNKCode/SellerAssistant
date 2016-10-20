package kz.alser.stepanov.semen.sellerassistant.Activities;

//region Imports area

import android.app.ProgressDialog;
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
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
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
import kz.alser.stepanov.semen.sellerassistant.Adapters.ItemsAdapter;
import kz.alser.stepanov.semen.sellerassistant.Models.Cart;
import kz.alser.stepanov.semen.sellerassistant.Models.Category;
import kz.alser.stepanov.semen.sellerassistant.Models.CategoryResponse;
import kz.alser.stepanov.semen.sellerassistant.Models.Items4Selection;
import kz.alser.stepanov.semen.sellerassistant.Models.Product;
import kz.alser.stepanov.semen.sellerassistant.Models.ProductResponse;
import kz.alser.stepanov.semen.sellerassistant.R;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

//endregion

public class MainActivity
        extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    //region Variables declaring
    //private Observable<String> myObservable;
    //private Subscriber<String> mySubscriber;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private AlertDialog dialog;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeContainer;
    private Paint p = new Paint();
    private ItemsAdapter adapter;

    private RecyclerView CartRecyclerView;
    private SwipeRefreshLayout CartSwipeContainer;
    private CartAdapter cart;

    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fabAddClient, fabAdd2Basket, fabClearBasket;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;

    private TextView cartTotalText;
    private ProgressDialog pd;

    protected DrawerLayout mDrawer;
    //endregion

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
        CartSwipeContainer.setOnRefreshListener(() -> initCart(GetCartItems()));

        CartSwipeContainer.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        initCartSwipe();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fabAddClient = (FloatingActionButton)findViewById(R.id.fabAddClient);
        fabAdd2Basket = (FloatingActionButton)findViewById(R.id.fabAdd2Basket);
        fabClearBasket = (FloatingActionButton)findViewById(R.id.fabClearBasket);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);

        fab.setOnClickListener(v -> animateFAB());

        fabAdd2Basket.setOnClickListener(v -> {
            showLoadingView(getString(R.string.dialog_wait_load_items_from_db));
            List<Items4Selection> items = new ArrayList<>();
            GetItemsByParentIdWithObservable(0)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            isOk -> items.add(isOk),
                            throwable -> hideLoadingViewWithError(throwable.getMessage()),
                            () -> {
                                showCategoriesDialog(items);
                                hideLoadingView();
                            }
                    );
        });

        fabClearBasket.setOnClickListener(e -> {
            showLoadingView(getString(R.string.dialog_wait_clearing_basket));
            EraseCart()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            isOk -> { },
                            throwable -> hideLoadingViewWithError(throwable.getMessage()),
                            () -> {
                                hideLoadingView();
                                animateFAB();
                                cart.reloadCart(GetCartItems());
                                SetCartSum();
                            }
                    );
        });

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        cartTotalText = (TextView) findViewById(R.id.cartTotal);
        initCart(GetCartItems());

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //region Key press events

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

    //endregion

    //region Menu method overloads

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
            Intent settings = new Intent();
            settings.setClass(this, SettingsActivity.class);
            startActivity(settings);
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

    //endregion

    //region Google Services overloads

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

    //endregion

    //region Init swipes

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
                showLoadingView(getString(R.string.dialog_wait_delete_element_from_basket));
                DeleteElementFromCart(position)
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
                        );

                //Cart c = cart.GetCartItem(position);
                //c.delete();
                //cart.reloadCart(GetCartItems());
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(CartRecyclerView);
    }

    //endregion

    //region Get data from db

    private List<Items4Selection> GetItemsByParentId(int itemId)
    {
        List<Items4Selection> items = new ArrayList<>();

        try
        {
            final Subscription subscription = Observable.concat(GetCategoriesByIdWithObservable(itemId), GetProductsByIdWithObservable(itemId), GetBackMenuWithObservable(itemId))
                    //GetCategoriesByIdWithObservable(itemId)
                    //.subscribeOn(Schedulers.io())
                    //.observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            isOk -> items.add(isOk),
                            throwable -> Toast.makeText(MainActivity.this, String.format("Возникла ошибка при загрузке товаров и категорий из БД: %s", throwable.getMessage()), Toast.LENGTH_SHORT).show(),
                            () -> { });

            subscription.unsubscribe();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return items;
    }

    @NonNull
    private Observable<Items4Selection> GetCategoriesByIdWithObservable(int itemId)
    {
        return Observable.create(subscriber -> {
            try
            {
                List<Category> categories = Category.find(Category.class, "CATEGORY_PARENT_ID = ? and CATEGORY_LANGUAGE_ID = ?", String.valueOf(itemId), "1");

                for (Category category : categories)
                {
                    Items4Selection item = new Items4Selection(Integer.valueOf(category.getCategoryId()), Integer.valueOf(category.getCategoryParentId()), category.getCategoryDescription(), Integer.valueOf(category.getCategoryId()), category.getCategoryImagePath(), category.getCategoryName(), 0, 0, category.getCategorySortOrder(), Items4Selection.Item_Type.CATEGORY);
                    subscriber.onNext(item);
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

            subscriber.onCompleted();

        });
    }

    @NonNull
    private Observable<Items4Selection> GetProductsByIdWithObservable(int itemId)
    {
        return Observable.create(subscriber -> {
            try
            {
                List<Product> products = Product.find(Product.class, "CATEGORY_ID = ? AND LANGUAGE_ID = ?", String.valueOf(itemId), "1");

                for (Product product : products)
                {
                    Items4Selection item = new Items4Selection(Integer.valueOf(product.getCategoryId()), 0, product.getProductDescription(), Integer.valueOf(product.getProductId()), product.getProductImagePath(), product.getProductName(), Double.valueOf(product.getProductPrice()), Integer.valueOf(product.getProductQuantity()), product.getProductSortOrder(), Items4Selection.Item_Type.PRODUCT);
                    subscriber.onNext(item);
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

            subscriber.onCompleted();
        });
    }

    @NonNull
    private Observable<Items4Selection> GetBackMenuWithObservable(int itemId)
    {
        return Observable.create(subscriber -> {

            try
            {
                if (itemId != 0)
                {
                    String itemParentId = Select.from(Category.class).where(Condition.prop("CATEGORY_ID").eq(String.valueOf(itemId))).first().getCategoryParentId();
                    Items4Selection item = new Items4Selection(Integer.valueOf(itemParentId), Integer.valueOf(itemParentId), "", Integer.valueOf(itemParentId), "", getString(R.string.items_back_menu_text), 0, 0, "9999", Items4Selection.Item_Type.BACK_MENU);
                    subscriber.onNext(item);
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

            subscriber.onCompleted();
        });
    }

    @NonNull
    private Observable<Items4Selection> GetItemsByParentIdWithObservable(int itemId)
    {
        try
        {
            return Observable.concat(GetCategoriesByIdWithObservable(itemId), GetProductsByIdWithObservable(itemId), GetBackMenuWithObservable(itemId));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }

        /*List<Items4Selection> items = new ArrayList<Items4Selection>();

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
                        getString(R.string.items_back_menu_text),
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

        return items;*/
    }

    //endregion

    //region Reload Data from service

    public void ReloadCategoriesFromServer()
    {
        try
        {
            swipeContainer.setRefreshing(true);

            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(getString(R.string.base_url))
                    .build();

            CategoriesAPI categoriesAPI = retrofit.create(CategoriesAPI.class);
            Observable<CategoryResponse> responseObservable = categoriesAPI.loadCategoriesNew();

            responseObservable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .retry(3)
                    .subscribe(r -> {

                        if (r.getRspCode() != 0)
                        {
                            Toast.makeText(MainActivity.this, String.format("Возникла ошибка при обновлении категорий: %s", r.getRspMessage()), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Category.deleteAll(Category.class);
                        List<Category> categories = r.getCategories();

                        for (Category category : categories)
                        {
                            category.save();
                        }

                        List<Items4Selection> items = GetItemsByParentId(0);
                        adapter.reloadCategories(items);

                        //swipeContainer.setRefreshing(false);

                    }, throwable -> {
                        swipeContainer.setRefreshing(false);
                        Toast.makeText(MainActivity.this, String.format("Возникла ошибка при обновлении категорий: %s", throwable.getMessage()), Toast.LENGTH_SHORT).show();
                        throwable.printStackTrace();
                    },
                    () -> swipeContainer.setRefreshing(false));
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

            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(getString(R.string.base_url))
                    .build();

            ProductsAPI productsAPI = retrofit.create(ProductsAPI.class);
            Observable<ProductResponse> productsResponse = productsAPI.loadProducts();

            productsResponse.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .retry(3)
                    .subscribe(r -> {

                        if (r.getRspCode() != 0)
                        {
                            Toast.makeText(MainActivity.this, String.format("Возникла ошибка при обновлении продуктов: %s", r.getRspMessage()), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Product.deleteAll(Product.class);
                        List<Product> products = r.getProducts();
                        for (Product product : products)
                        {
                            product.save();
                        }

                        List<Items4Selection> items = GetItemsByParentId(0);
                        adapter.reloadCategories(items);

                        //swipeContainer.setRefreshing(false);

                    }, throwable -> {
                        swipeContainer.setRefreshing(false);
                        Toast.makeText(MainActivity.this, String.format("Возникла ошибка при обновлении продуктов: %s", throwable.getMessage()), Toast.LENGTH_SHORT).show();
                        throwable.printStackTrace();
                    },
                    () -> swipeContainer.setRefreshing(false));
        }
        catch (Exception ex)
        {
            swipeContainer.setRefreshing(false);
            Toast.makeText(MainActivity.this, String.format("Возникла ошибка при обновлении продуктов: %s", ex.getMessage()), Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }

    //endregion

    private void Click2DialogItems(Items4Selection item)
    {
        if (item != null)
        {
            if (item.getItemType() == Items4Selection.Item_Type.PRODUCT)
            {
                AddItem2Cart(item).subscribe(
                        isOK -> {},
                        throwable -> Toast.makeText(MainActivity.this, String.format("Возникла ошибка при добавлении товара в корзину: %s", throwable.getMessage()), Toast.LENGTH_SHORT).show(),
                        () -> {
                            cart.reloadCart(GetCartItems());
                            SetCartSum();
                        }
                );

                hideCategoriesDialog();

                return;
            }

            showLoadingView(getString(R.string.dialog_wait_load_items_from_db));
            List<Items4Selection> items = new ArrayList<>();

            GetItemsByParentIdWithObservable(item.getCategoryId())
                    .onBackpressureDrop()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError(i -> {
                        i.printStackTrace();
                    })
                    .subscribe(
                            isOk -> items.add(isOk),
                            throwable -> hideLoadingViewWithError(throwable.getMessage()),
                            () -> {
                                hideLoadingView();

                                if (items.size() > 0)
                                {
                                    adapter.reloadCategories(items);
                                }
                            }
                    );

            /*
            List<Items4Selection> items = GetItemsByParentId(item.getCategoryId());

            if (items.size() > 0)
            {
                adapter.reloadCategories(items);
            }*/
        }
    }

    //region Add to cart with RxJava

    @NonNull
    private Observable<Items4Selection> AddItem2Cart(Items4Selection item)
    {
        return Observable.just(item)
                .doOnNext(i -> showLoadingView(getString(R.string.dialog_wait_add_to_cart_message)))
                .doOnNext(i -> {
                    if (i != null)
                    {
                        List<Cart> cartItems = Cart.find(Cart.class, "PRODUCT_ID = ?", String.valueOf(i.getItemId()));

                        if (cartItems == null || cartItems.size() == 0)
                        {
                            Cart newItem = new Cart();
                            newItem.setBeginPrice(i.getItemPrice());
                            newItem.setEndPrice(i.getItemPrice());
                            newItem.setQuantity(1);
                            newItem.setCategoryId(i.getCategoryId());
                            newItem.setProductDescription(i.getItemDescription());
                            newItem.setProductId(i.getItemId());
                            newItem.setProductImagePath(i.getItemImagePath());
                            newItem.setProductName(i.getItemName());
                            newItem.save();
                        }
                        else
                        {
                            Cart updItem = cartItems.get(0);

                            int quantityInCart = updItem.getQuantity();
                            quantityInCart++;
                            updItem.setQuantity(quantityInCart);
                            updItem.save();
                        }
                    }
                })
                .doOnNext(i -> {
                    animateFAB();
                    hideLoadingView();
                });
    }

    //endregion

    @NonNull
    private Observable<Cart> GetCartItemsWithObservable()
    {
        return Observable.create(subscriber -> {
            List<Cart> items = Cart.listAll(Cart.class);

            for (Cart cart : items)
            {
                subscriber.onNext(cart);
            }

            subscriber.onCompleted();
        });
    }

    @NonNull
    private List<Cart> GetCartItems()
    {
        CartSwipeContainer.setRefreshing(true);
        List<Cart> items = new ArrayList<>();
        final Subscription subscription = GetCartItemsWithObservable()
                //.subscribeOn(Schedulers.newThread())
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        isOk -> items.add(isOk),
                        throwable -> hideLoadingViewWithError(throwable.getMessage()),
                        () -> {
                            if (cart ==  null || cart.getItemCount() == 0)
                                initCart(items);

                            SetCartSum();
                            CartSwipeContainer.setRefreshing(false);
                        }
                );

        subscription.unsubscribe();

        return items;

        /*
        CartSwipeContainer.setRefreshing(true);
        List<Cart> items = new ArrayList<>();

        try
        {
            items = Cart.listAll(Cart.class);

            if (cart ==  null || cart.getItemCount() == 0)
                initCart(items);

            SetCartSum();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        CartSwipeContainer.setRefreshing(false);
        return items;*/
    }

    //region Show and Hide dialogs

    private void hideCategoriesDialog()
    {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void showCategoriesDialog(List<Items4Selection> items)
    {
        if (items.size() == 0)
        {
            Toast.makeText(MainActivity.this, "Нет доступных товаров и категорий по запрашиваемым параметрам", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View categoriesLayout = inflater.inflate(R.layout.categories_container, null);
        adb.setView(categoriesLayout);

        dialog = adb.create();

        //dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        //dialog.setContentView(R.layout.categories_container);

        final Button buttonCancel = (Button) categoriesLayout.findViewById(R.id.btn_close_categories_dialog);
        buttonCancel.setOnClickListener(vv -> {
            animateFAB();
            dialog.dismiss();
        });

        recyclerView = (RecyclerView) categoriesLayout.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        //recyclerView.setItemAnimator(new SlideInUpAnimator());

        //LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
        //llm.setOrientation(LinearLayoutManager.VERTICAL);
        //recyclerView.setLayoutManager(llm);
        int rowCount = GetRowCountForItemsView();

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), rowCount);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ItemsAdapter(items);
        //adapter.reloadCategories(items);

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

        swipeContainer = (SwipeRefreshLayout) categoriesLayout.findViewById(R.id.freshSwipeView);
        swipeContainer.setOnRefreshListener(() ->
        {
            ReloadCategoriesFromServer();
            ReloadProductsFromServer();
        });

        swipeContainer.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        initSwipe();

        dialog.show();
        dialog.getWindow().setLayout(getResources().getSystem().getDisplayMetrics().widthPixels - 50, getResources().getSystem().getDisplayMetrics().heightPixels - 50);
    }

    //endregion

    //region Other methods

    protected void showLoadingView(String messageText) {
        //pd = ProgressDialog.show(this, "", getString(R.string.dialog_wait_add_to_cart_message), true, false);
        pd = new ProgressDialog(MainActivity.this);
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

        if (CartSwipeContainer != null && CartSwipeContainer.isRefreshing()) {
            CartSwipeContainer.setRefreshing(false);
        }
    }

    protected void hideLoadingViewWithError(String errorMessage) {
        if (!errorMessage.isEmpty())
            Toast.makeText(MainActivity.this, String.format("Возникла ошибка при загрузки товаров и категорий: %s", errorMessage), Toast.LENGTH_SHORT).show();

        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }

        if (CartSwipeContainer != null && CartSwipeContainer.isRefreshing()) {
            CartSwipeContainer.setRefreshing(false);
        }
    }

    private void initCart(List<Cart> cartItems)
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

    private int GetRowCountForItemsView()
    {
        int displayWidth = 0;

        Resources res = this.getResources();
        displayWidth = Math.round(res.getSystem().getDisplayMetrics().widthPixels / 600);

        return displayWidth;
    }

    private void animateFAB()
    {
        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fabAddClient.startAnimation(fab_close);
            fabAdd2Basket.startAnimation(fab_close);
            fabClearBasket.startAnimation(fab_close);

            fabAddClient.setClickable(false);
            fabAdd2Basket.setClickable(false);
            fabClearBasket.setClickable(false);

            isFabOpen = false;
        }
        else
        {
            fab.startAnimation(rotate_forward);
            fabAddClient.startAnimation(fab_open);
            fabAdd2Basket.startAnimation(fab_open);
            fabClearBasket.startAnimation(fab_open);

            fabAddClient.setClickable(true);
            fabAdd2Basket.setClickable(true);
            fabClearBasket.setClickable(true);

            isFabOpen = true;
        }
    }

    @NonNull
    private Observable<Integer> EraseCart()
    {
        return Observable.create(subscriber -> {
            TruncateCartTable();
            subscriber.onNext(0);
            subscriber.onCompleted();
            subscriber.unsubscribe();
        });
    }

    @NonNull
    private Observable<Boolean> DeleteElementFromCart(int position)
    {
        return Observable.create(subscriber -> {
            Cart c = cart.GetCartItem(position);
            subscriber.onNext(c.delete());
            subscriber.onCompleted();
            subscriber.unsubscribe();
        });
    }

    private void TruncateCartTable()
    {
        Cart.deleteAll(Cart.class);
    }

    private Double CalculateCartSum()
    {
        return cart.GetTotalSum();
    }

    private void SetCartSum()
    {
        Double cartTotalSum = CalculateCartSum();
        String cartTotalTextIn = getString(R.string.cart_total_no_items);

        if (cartTotalSum > 0)
            cartTotalTextIn = String.format("%.2f", cartTotalSum);

        cartTotalText.setText(cartTotalTextIn);
    }

    //endregion

    //region Testing methods

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

    //endregion
}