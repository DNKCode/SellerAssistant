package kz.alser.stepanov.semen.sellerassistant.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import kz.alser.stepanov.semen.sellerassistant.Models.Language;

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
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(languages.get(position).getLanguageName());

        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,  ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(languages.get(position).getLanguageName());

        return label;
    }
}
