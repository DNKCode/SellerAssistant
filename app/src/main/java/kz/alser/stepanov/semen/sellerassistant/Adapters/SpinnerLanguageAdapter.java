package kz.alser.stepanov.semen.sellerassistant.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kz.alser.stepanov.semen.sellerassistant.Helpers.Helpers;
import kz.alser.stepanov.semen.sellerassistant.Models.Language;
import kz.alser.stepanov.semen.sellerassistant.R;

/**
 * Created by semen.stepanov on 24.10.2016.
 */

public class SpinnerLanguageAdapter extends ArrayAdapter<Language>
{
    private Context context;
    private List<Language> languages;

    public SpinnerLanguageAdapter(Context context, int textViewResourceId, List<Language> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.languages = values;
    }

    public int getCount(){
        return languages.size();
    }

    public Language getItem(int position){
        return languages.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(languages.get(position).getLanguageName());

        return label;*/

        Language language = languages.get(position);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.languages_spinner, parent, false);

        TextView languageName = (TextView) convertView.findViewById(R.id.language_name);
        ImageView languageIcon = (ImageView) convertView.findViewById(R.id.language_photo);
        languageName.setText(language.getLanguageName());
        languageIcon.setImageResource(R.mipmap.nofoto);

        try
        {
            String imagePath = language.getLanguageImagePath();

            if (Helpers.isNullOrEmpty(imagePath))
            {
                languageIcon.setImageResource(R.mipmap.nofoto);
            }
            else
            {
                Resources res = getContext().getResources();
                String urlImg = String.format("%s/%s/%s/%s", res.getString(R.string.base_url), res.getString(R.string.image_url), res.getString(R.string.image_flags_url),imagePath);
                Picasso.with(getContext()).load(urlImg).resize(50, 50).centerInside().into(languageIcon);
            }
        }
        catch (Exception ex)
        {
            languageIcon.setImageResource(R.mipmap.nofoto);
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView,  ViewGroup parent) {
        /*TextView label = new TextView(context);
        label.setTextColor(Color.RED);
        label.setText(languages.get(position).getLanguageName());

        return label;*/

        Language language = languages.get(position);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.languages_spinner, parent, false);

        TextView languageName = (TextView) convertView.findViewById(R.id.language_name);
        ImageView languageIcon = (ImageView) convertView.findViewById(R.id.language_photo);
        languageName.setText(language.getLanguageName());
        languageIcon.setImageResource(R.mipmap.nofoto);

        try
        {
            String imagePath = language.getLanguageImagePath();

            if (Helpers.isNullOrEmpty(imagePath))
            {
                languageIcon.setImageResource(R.mipmap.nofoto);
            }
            else
            {
                Resources res = getContext().getResources();
                String urlImg = String.format("%s/%s/%s/%s", res.getString(R.string.base_url), res.getString(R.string.image_url), res.getString(R.string.image_flags_url),imagePath);
                Picasso.with(getContext()).load(urlImg).resize(50, 50).centerInside().into(languageIcon);
            }
        }
        catch (Exception ex)
        {
            languageIcon.setImageResource(R.mipmap.nofoto);
        }

        return convertView;
    }
}
