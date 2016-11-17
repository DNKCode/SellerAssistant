package kz.alser.stepanov.semen.sellerassistant.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;
import java.util.List;

import kz.alser.stepanov.semen.sellerassistant.Models.Category;
import kz.alser.stepanov.semen.sellerassistant.Models.Language;
import kz.alser.stepanov.semen.sellerassistant.Models.Orders;
import kz.alser.stepanov.semen.sellerassistant.Models.OrdersProduct;
import kz.alser.stepanov.semen.sellerassistant.Models.OrdersStatus;
import kz.alser.stepanov.semen.sellerassistant.Models.Product;
import kz.alser.stepanov.semen.sellerassistant.R;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import static kz.alser.stepanov.semen.sellerassistant.Services.GetDataFromServer.ReloadLanguagesFromServer;
import static kz.alser.stepanov.semen.sellerassistant.Services.GetDataFromServer.ReloadProductsFromServer;
import static kz.alser.stepanov.semen.sellerassistant.Services.GetDataFromServer.ReloadCategoriesFromServer;
import static kz.alser.stepanov.semen.sellerassistant.Services.GetDataFromServer.ReloadOrdersFromServer;
import static kz.alser.stepanov.semen.sellerassistant.Services.GetDataFromServer.ReloadOrderProductsFromServer;
import static kz.alser.stepanov.semen.sellerassistant.Services.GetDataFromServer.ReloadOrderStatusesFromServer;

public class SettingsDictionaryFragment extends Fragment
{
    private OnFragmentInteractionListener mListener;
    private Button btnReloadLanguages, btnReloadProducts, btnReloadCategories, btnReloadOrders;
    private ProgressDialog pd;

    public SettingsDictionaryFragment ()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_settings_dictionary, container, false);

        btnReloadLanguages = (Button) view.findViewById(R.id.btnUpdateLanguages);
        btnReloadLanguages.setOnClickListener(click -> ReloadLanguages());

        btnReloadProducts = (Button) view.findViewById(R.id.btnUpdateProducts);
        btnReloadProducts.setOnClickListener(click -> ReloadProducts());

        btnReloadCategories = (Button) view.findViewById(R.id.btnUpdateCategories);
        btnReloadCategories.setOnClickListener(click -> ReloadCategories());

        btnReloadOrders = (Button) view.findViewById(R.id.btnUpdateOrders);
        btnReloadOrders.setOnClickListener(click -> ReloadOrdersAll());

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed (Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach (Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
            //InputMethodManager imm =  (InputMethodManager) getActivity().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
        else
        {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach ()
    {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener
    {
        void onFragmentInteraction (Uri uri);
    }

    protected void showLoadingView(String messageText) {
        pd = new ProgressDialog(getContext());
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
    }

    protected void hideLoadingViewWithError(String errorMessage) {
        if (!errorMessage.isEmpty())
            Toast.makeText(getContext(), String.format("Возникла ошибка при загрузки языковых настроек: %s", errorMessage), Toast.LENGTH_SHORT).show();

        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }

    protected void ReloadLanguages()
    {
        showLoadingView("Обновление языковых настроек");
        ReloadLanguagesFromServer(this.getContext())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(3)
                .subscribe(r -> {
                            if (r.getRspCode() != 0)
                            {
                                hideLoadingViewWithError(r.getRspMessage());
                                return;
                            }

                            Language.deleteAll(Language.class);
                            List<Language> languages = r.getLanguages();
                            for (Language language : languages)
                            {
                                language.save();
                            }
                        }, throwable -> {
                            hideLoadingViewWithError(throwable.getMessage());
                            throwable.printStackTrace();
                        },
                        () -> hideLoadingView());
    }

    protected void ReloadProducts()
    {
        showLoadingView("Обновление товаров");
        ReloadProductsFromServer(this.getContext())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(3)
                .subscribe(r -> {
                            if (r.getRspCode() != 0)
                            {
                                hideLoadingViewWithError(r.getRspMessage());
                                return;
                            }

                            Product.deleteAll(Product.class);
                            List<Product> products = r.getProducts();
                            for (Product product : products)
                            {
                                product.save();
                            }
                        }, throwable -> {
                            hideLoadingViewWithError(throwable.getMessage());
                            throwable.printStackTrace();
                        },
                        () -> hideLoadingView());
    }

    protected void ReloadCategories()
    {
        showLoadingView("Обновление категорий");
        ReloadCategoriesFromServer(this.getContext())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(3)
                .subscribe(r -> {
                            if (r.getRspCode() != 0)
                            {
                                hideLoadingViewWithError(r.getRspMessage());
                                return;
                            }

                            Category.deleteAll(Category.class);
                            List<Category> categories = r.getCategories();
                            for (Category category : categories)
                            {
                                category.save();
                            }
                        }, throwable -> {
                            hideLoadingViewWithError(throwable.getMessage());
                            throwable.printStackTrace();
                        },
                        () -> hideLoadingView());
    }

    protected void ReloadOrders()
    {
        showLoadingView("Обновление заказов");
        ReloadOrdersFromServer(this.getContext())
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
                        }, throwable -> {
                            hideLoadingViewWithError(throwable.getMessage());
                            throwable.printStackTrace();
                        },
                        () -> hideLoadingView());
    }

    protected void ReloadOrderProducts()
    {
        showLoadingView("Обновление товаров в заказах");
        ReloadOrderProductsFromServer(this.getContext())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(3)
                .subscribe(r -> {
                            if (r.getRspCode() != 0)
                            {
                                hideLoadingViewWithError(r.getRspMessage());
                                return;
                            }

                            OrdersProduct.deleteAll(OrdersProduct.class);
                            List<OrdersProduct> ordersProducts = r.getOrdersProducts();
                            for (OrdersProduct ordersProduct : ordersProducts)
                            {
                                ordersProduct.save();
                            }
                        }, throwable -> {
                            hideLoadingViewWithError(throwable.getMessage());
                            throwable.printStackTrace();
                        },
                        () -> hideLoadingView());
    }

    protected void ReloadOrderStatuses()
    {
        showLoadingView("Обновление товаров в заказах");
        ReloadOrderStatusesFromServer(this.getContext())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(3)
                .subscribe(r -> {
                            if (r.getRspCode() != 0)
                            {
                                hideLoadingViewWithError(r.getRspMessage());
                                return;
                            }

                            OrdersStatus.deleteAll(OrdersStatus.class);
                            List<OrdersStatus> ordersStatuses = r.getOrdersStatuses();
                            for (OrdersStatus ordersStatus : ordersStatuses)
                            {
                                ordersStatus.save();
                            }
                        }, throwable -> {
                            hideLoadingViewWithError(throwable.getMessage());
                            throwable.printStackTrace();
                        },
                        () -> hideLoadingView());
    }

    protected void ReloadOrdersAll()
    {
        showLoadingView("Обновление заказов");

        Observable.create(subscriber -> {

            ReloadOrdersFromServer(this.getContext())
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
                            }, throwable -> throwable.printStackTrace());

            ReloadOrderProductsFromServer(this.getContext())
                    .subscribe(r -> {
                                if (r.getRspCode() != 0)
                                {
                                    hideLoadingViewWithError(r.getRspMessage());
                                    return;
                                }

                                OrdersProduct.deleteAll(OrdersProduct.class);
                                List<OrdersProduct> ordersProducts = r.getOrdersProducts();
                                for (OrdersProduct ordersProduct : ordersProducts)
                                {
                                    ordersProduct.save();
                                }
                            }, throwable -> throwable.printStackTrace());

            ReloadOrderStatusesFromServer(this.getContext())
                    .subscribe(r -> {
                                if (r.getRspCode() != 0)
                                {
                                    hideLoadingViewWithError(r.getRspMessage());
                                    return;
                                }

                                OrdersStatus.deleteAll(OrdersStatus.class);
                                List<OrdersStatus> ordersStatuses = r.getOrdersStatuses();
                                for (OrdersStatus ordersStatus : ordersStatuses)
                                {
                                    ordersStatus.save();
                                }
                            }, throwable -> throwable.printStackTrace());

            subscriber.onNext(0);
            subscriber.onCompleted();
            subscriber.unsubscribe();
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(3)
                .subscribe(
                        isOK -> { },
                        throwable -> {
                            hideLoadingViewWithError(throwable.getMessage());
                            throwable.printStackTrace();
                        },
                        () -> hideLoadingView());
    }
}
