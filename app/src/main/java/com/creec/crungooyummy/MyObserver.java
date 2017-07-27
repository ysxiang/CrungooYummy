package com.creec.crungooyummy;

import android.app.Fragment;

import java.util.List;
import java.util.Observable;

public interface MyObserver {
    void notifyStatusChange(int status);
}
