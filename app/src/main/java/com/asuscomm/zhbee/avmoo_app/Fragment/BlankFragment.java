package com.asuscomm.zhbee.avmoo_app.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;




/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    static  final  int MA  = 0;
    static  final  int MAN = 1;
    static  final  int OU = 2;


    int maOrManTYPE;

    private boolean hasCreateView;

    private boolean isFragmentVisible;

    View rootView;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d("BlankFragment", "setUserVisibleHint() -> isVisibleToUser: "  + isVisibleToUser);
        if (rootView == null) {
            return;
        }
        hasCreateView = true;
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            onFragmentVisibleChange(false);
            isFragmentVisible = false;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!hasCreateView && getUserVisibleHint()) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
        }
    }

    private void initVariable() {
        hasCreateView = false;
        isFragmentVisible = false;
    }


    protected void onFragmentVisibleChange(boolean isVisible) {
        Log.d("BlankFragment", "onFragmentVisibleChange -> isVisible: " + isVisible);
    }

}
