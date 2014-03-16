package ch.arnab.simplelauncher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Helper class to look for interesting changes to the installed apps
 * so that the loader can be updated.
 *
 * @Credit http://developer.android.com/reference/android/content/AsyncTaskLoader.html
 */
public class PackageIntentReceiver extends BroadcastReceiver {

    final AppsLoader mLoader;

    public PackageIntentReceiver(AppsLoader loader) {
        mLoader = loader;

        IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        filter.addDataScheme("package");
        mLoader.getContext().registerReceiver(this, filter);

        // Register for events related to sdcard installation.
        IntentFilter sdFilter = new IntentFilter();
        sdFilter.addAction(Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE);
        sdFilter.addAction(Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE);
        mLoader.getContext().registerReceiver(this, sdFilter);
    }

    @Override public void onReceive(Context context, Intent intent) {
        // Tell the loader about the change.
        mLoader.onContentChanged();
    }

}
