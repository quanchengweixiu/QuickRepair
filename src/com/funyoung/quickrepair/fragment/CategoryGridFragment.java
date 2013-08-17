package com.funyoung.quickrepair.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import com.funyoung.qcwx.R;
import com.funyoung.quickrepair.MainActivity;

/**
 * Created by yangfeng on 13-8-10.
 */

/**
 * Fragment that appears in the "content_frame", shows a planet
 */
public  class CategoryGridFragment extends BaseFragment {
    private View rootView;

    public CategoryGridFragment() {
        // Empty constructor required for fragment subclasses
    }

    private static Integer[] images = {
            R.drawable.ic_classify_airconditioning,
            R.drawable.ic_classify_heater,
            R.drawable.ic_classify_closestool,
            R.drawable.ic_classify_electric,
            R.drawable.ic_classify_pipeline,
            R.drawable.ic_classify_appliance,
            R.drawable.ic_classify_house,
            R.drawable.ic_classify_furniture,
            R.drawable.ic_classify_more
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_category, container, false);

        ArrayList<HashMap<String, Object>> itemData = new ArrayList<HashMap<String, Object>>();
        final String[] labels = getResources().getStringArray(R.array.qp_category_label_array);
        for (int i = 0; i < 9; i++) {
            HashMap<String, Object> itemUnit = new HashMap<String, Object>();
            itemUnit.put("img", images[i]);
            itemUnit.put("label", labels[i]);
            itemData.add(itemUnit);
        }

        GridView gridView = (GridView) rootView.findViewById(R.id.ptr_gridview);
//        ListAdapter adapter = new ArrayAdapter<String>(getActivity(),
//                R.layout.gridview_item_category,
//                labels);
        SimpleAdapter adapter = new SimpleAdapter(getActivity(),
                itemData,
                R.layout.gridview_item_category,
                new String[] { "img", "label" },
                new int[] { R.id.img, R.id.label });

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ((MainActivity)getActivity()).startPost(i, labels[i]);
            }
        });

        return rootView;
    }
}

