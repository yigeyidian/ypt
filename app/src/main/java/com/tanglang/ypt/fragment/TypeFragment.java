package com.tanglang.ypt.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.tanglang.ypt.R;
import com.tanglang.ypt.activity.TypeListActivity;

public class TypeFragment extends Fragment {
    private static int[] typeImageIds = {
            R.mipmap.ic_drug_typ_home_chest,
            R.mipmap.ic_drug_type_cold,
            R.mipmap.ic_drug_typ_vitamin,
            R.mipmap.ic_drug_type_chronic_illness,
            R.mipmap.ic_drug_type_woman,
            R.mipmap.ic_drug_type_man,
            R.mipmap.ic_drug_type_children,
    };

    public static String[] typeNameStrs = {"家庭药箱", "感冒用药", "维生素/钙", "慢性病药",
            "妇科用药", "男性用药", "儿童用药"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.type_gridview);
        GridViewAdapter adapter = new GridViewAdapter();
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), TypeListActivity.class);
                intent.putExtra("typename", typeNameStrs[position]);
                startActivity(intent);
            }
        });

        return view;
    }

    class GridViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return typeNameStrs.length;
        }

        @Override
        public Object getItem(int position) {
            return typeNameStrs[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getContext(), R.layout.typegrifitem, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            TextView textView = (TextView) view.findViewById(R.id.text);
            imageView.setImageResource(typeImageIds[position]);
            textView.setText(typeNameStrs[position]);
            return view;
        }
    }
}
