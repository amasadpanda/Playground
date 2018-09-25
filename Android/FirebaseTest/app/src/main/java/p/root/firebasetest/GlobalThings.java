package p.root.firebasetest;

import android.app.Application;
import android.content.Context;

/**
 * Created by root on 3/29/18.
 */

public class GlobalThings extends Application{

    private static Context context;

    @Override
    public void onCreate() {

        super.onCreate();
        GlobalThings.context = getApplicationContext();
    }

    public static Context getAppContext()
    {
        return context;
    }

}
