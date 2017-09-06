package com.jjjx.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjjx.R;
import com.jjjx.data.response.UserProfileResponse;

import java.util.ArrayList;


/**
 * Created by AMing on 17/9/6.
 * Company RongCloud
 */
public class ProfileFindFragment extends android.support.v4.app.Fragment {


    public static ProfileFindFragment newInstance(ArrayList<UserProfileResponse.ParaEntity.DiscoverInfoEntity> discoverInfoEntityList) {
        ProfileFindFragment findFragment = new ProfileFindFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("find", discoverInfoEntityList);
        findFragment.setArguments(bundle);
        return findFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            ArrayList<UserProfileResponse.ParaEntity.DiscoverInfoEntity> discoverInfoEntityList = bundle.getParcelableArrayList("find");
            if (discoverInfoEntityList != null && discoverInfoEntityList.size() > 0) {
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_find, container, false);

        return view;
    }
}
