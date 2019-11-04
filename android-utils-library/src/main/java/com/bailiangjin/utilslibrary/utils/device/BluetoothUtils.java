package com.bailiangjin.utilslibrary.utils.device;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Build;

import com.bailiangjin.utilslibrary.utils.app.AppUtils;

/**
 * 蓝牙相关工具类
 */
public class BluetoothUtils {

    public static boolean isBluetoothEnabled() {

        BluetoothAdapter bluetoothAdapter = getBluetoothAdapter();
        return null != bluetoothAdapter && bluetoothAdapter.isEnabled();
    }

    public static boolean toggleBluetooth(boolean isOn) {
        if (isOn) {
            return enableBluetooth();
        } else {
            return disableBluetooth();
        }

    }

    public static boolean enableBluetooth() {

        BluetoothAdapter bluetoothAdapter = getBluetoothAdapter();
        return bluetoothAdapter.enable();
    }

    public static boolean disableBluetooth() {

        BluetoothAdapter bluetoothAdapter = getBluetoothAdapter();
        return bluetoothAdapter.disable();
    }

    private static BluetoothAdapter getBluetoothAdapter() {
        BluetoothAdapter bluetoothAdapter;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            BluetoothManager bluetoothManager = (BluetoothManager) AppUtils.getContext()
                    .getSystemService(Context.BLUETOOTH_SERVICE);
            bluetoothAdapter = bluetoothManager.getAdapter();

        } else {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        return bluetoothAdapter;
    }

}
