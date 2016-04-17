package com.chengang.newcheck.ui.fragmentMain;

import android.content.Intent;

/**
 * Created by urcha on 2016/4/16.
 */
public interface BaseFragment {

    void onFragmentActivityResult(int requestCode, int resultCode, Intent data);
}
