package kz.alser.stepanov.semen.sellerassistant.Activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import kz.alser.stepanov.semen.sellerassistant.Adapters.SettingsPagerAdapter;
import kz.alser.stepanov.semen.sellerassistant.Fragments.SettingsDictionaryFragment;
import kz.alser.stepanov.semen.sellerassistant.R;
import kz.alser.stepanov.semen.sellerassistant.Fragments.SettingsMainFragment;

public class SettingsActivity extends AppCompatActivity
    implements
        SettingsMainFragment.OnFragmentInteractionListener,
        SettingsDictionaryFragment.OnFragmentInteractionListener
{
    private ViewPager mViewPager;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        mViewPager = (ViewPager) findViewById(R.id.settings_tab_container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.settings_tab);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onFragmentInteraction (Uri uri)
    {

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

    private void setupViewPager(ViewPager viewPager) {
        SettingsPagerAdapter adapter = new SettingsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SettingsMainFragment(), getString(R.string.action_settings));
        adapter.addFragment(new SettingsDictionaryFragment(), getString(R.string.action_dictionaries));
        //adapter.addFragment(new FragmentNotificationSettings(), getString(R.string.labelForNotificationSettings));
        viewPager.setAdapter(adapter);
    }
}
