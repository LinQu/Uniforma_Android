package id.ac.astra.polytechnic.kelompok1.p5m_new.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;

import androidx.lifecycle.LiveData;

public class NetworkStateLiveData extends LiveData<Boolean> {
    private final ConnectivityManager connectivityManager;
    private ConnectivityManager.NetworkCallback networkCallback;

    public NetworkStateLiveData(Context context) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Override
    protected void onActive() {
        super.onActive();
        registerNetworkCallback();
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        unregisterNetworkCallback();
    }

    public void onPostValue(boolean value){
        postValue(value);
    }

    private void registerNetworkCallback() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            networkCallback = new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(Network network) {
                    postValue(true);
                }

                @Override
                public void onLost(Network network) {
                    postValue(false);
                }
            };
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        }
    }

    private void unregisterNetworkCallback() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && networkCallback != null) {
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }
    }
}
