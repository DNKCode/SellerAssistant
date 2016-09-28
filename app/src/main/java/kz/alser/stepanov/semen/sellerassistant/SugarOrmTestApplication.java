package kz.alser.stepanov.semen.sellerassistant;

import android.content.res.Configuration;
import com.orm.SugarApp;
import com.orm.SugarContext;

import kz.alser.stepanov.semen.sellerassistant.Models.Cart;
import kz.alser.stepanov.semen.sellerassistant.Models.Category;
import kz.alser.stepanov.semen.sellerassistant.Models.Product;

/**
 * Created by semen.stepanov on 09.09.2016.
 */
public class SugarOrmTestApplication extends SugarApp
{
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(getApplicationContext());
        Product.findById(Product.class, (long) 1);
        Category.findById(Category.class, (long) 1);
        Cart.findById(Cart.class, (long) 1);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}