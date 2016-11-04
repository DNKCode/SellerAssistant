package kz.alser.stepanov.semen.sellerassistant.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kz.alser.stepanov.semen.sellerassistant.Activities.OrdersActivity;
import kz.alser.stepanov.semen.sellerassistant.Adapters.SpinnerLanguageAdapter;
import kz.alser.stepanov.semen.sellerassistant.Models.Language;
import kz.alser.stepanov.semen.sellerassistant.Models.Orders;
import kz.alser.stepanov.semen.sellerassistant.R;
import rx.Observable;
import rx.Subscription;

public class SettingsMainFragment
        extends Fragment
{
    private OnFragmentInteractionListener mListener;
    private ProgressDialog pd;
    private SpinnerLanguageAdapter spinnerAdapter;

    public SettingsMainFragment ()
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
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        AppCompatSpinner spinner = (AppCompatSpinner) view.findViewById(R.id.settings_language);
        spinnerAdapter = new SpinnerLanguageAdapter(getActivity(), android.R.layout.simple_spinner_item, GetLanguages());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected (AdapterView<?> parent, View view, int position, long id)
            {
                Language language = spinnerAdapter.getItem(position);
                Toast.makeText(getActivity(), language.getLanguageId(), Toast.LENGTH_SHORT);
            }

            @Override
            public void onNothingSelected (AdapterView<?> parent)
            {

            }
        } );

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

    @NonNull
    private List<Language> GetLanguages()
    {
        showLoadingView("Загружаем языки");
        List<Language> items = new ArrayList<>();
        final Subscription subscription = GetLanguagesWithObservable()
                .subscribe(
                        isOk -> items.add(isOk),
                        throwable -> hideLoadingViewWithError(throwable.getMessage()),
                        () -> hideLoadingView()
                );

        subscription.unsubscribe();

        return items;
    }

    @NonNull
    private Observable<Language> GetLanguagesWithObservable()
    {
        return Observable.create(subscriber -> {
            List<Language> languages = Language.listAll(Language.class);

            for (Language language : languages)
            {
                subscriber.onNext(language);
            }

            subscriber.onCompleted();
            subscriber.unsubscribe();
        });
    }

    protected void showLoadingView(String messageText) {
        pd = new ProgressDialog(getActivity());
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
            Toast.makeText(getActivity(), String.format(getString(R.string.error_message_load_categories_and_products), errorMessage), Toast.LENGTH_SHORT).show();

        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }
}
