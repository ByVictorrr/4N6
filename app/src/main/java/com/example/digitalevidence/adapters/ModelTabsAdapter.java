package com.example.digitalevidence.adapters;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.digitalevidence.fragments.CompactFragment;
import com.example.digitalevidence.fragments.DetailedFragment;
import com.example.digitalevidence.R;

public class ModelTabsAdapter extends FragmentPagerAdapter {
    @StringRes
    private static final int[] TAB_TITLES =
            new int[] { R.string.tab_compact, R.string.tab_detailed };

    private final Context mContext;

    public ModelTabsAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return CompactFragment.newInstance();
            case 1:
                return DetailedFragment.newInstance();
        }
        return CompactFragment.newInstance();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}