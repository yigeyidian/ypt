package com.tanglang.ypt.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tanglang.ypt.R;
import com.tanglang.ypt.adapter.CommentListAdapter;
import com.tanglang.ypt.bean.DrugDetail;

/**
 * Author： Administrator
 */
public class DrugCommFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drugcomm, container, false);
        ListView listView = (ListView) view.findViewById(R.id.drugcomm_listview);

        DrugDetail drug = (DrugDetail) getArguments().getSerializable("drug");
        CommentListAdapter adapter = new CommentListAdapter(getActivity(), drug.results.Comments);
        listView.setAdapter(adapter);

        return view;
    }
}
