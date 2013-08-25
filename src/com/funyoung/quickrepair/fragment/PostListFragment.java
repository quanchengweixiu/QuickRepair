
package com.funyoung.quickrepair.fragment;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.funyoung.qcwx.R;
import com.funyoung.quickrepair.MainActivity;
import com.funyoung.quickrepair.model.User;
import com.funyoung.quickrepair.transport.BillingClient;
import com.funyoung.quickrepair.utils.PerformanceUtils;

import java.util.HashMap;

import baidumapsdk.demo.DemoApplication;
import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshAttacher;

public class PostListFragment extends ListFragment implements
        PullToRefreshAttacher.OnRefreshListener {
    private static final String TAG = "PostListFragment";
    private static final long SIMULATED_REFRESH_LENGTH = 5000;

    private User mUser;

    private AsyncTask<Void, Void, String> mPreTask;

    @Override
    public void onStart() {
        super.onStart();

        if (null == mUser) {
            mUser = ((DemoApplication)getActivity().getApplication()).getLoginUser();
            if (mUser == null) {
                Log.e(TAG, "initViews, error with null user found");
            }
        }

        /**
         * Get ListView and give it an adapter to display the sample items
         */
        ListView listView = getListView();
        ListAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
                ITEMS);
        listView.setAdapter(adapter);

        /**
         * Here we create a PullToRefreshAttacher manually without an Options instance.
         * PullToRefreshAttacher will manually create one using default values.
         */
//            mPullToRefreshAttacher = PullToRefreshAttacher.get(getActivity());
        mPullToRefreshAttacher = ((MainActivity) getActivity())
                .getPullToRefreshAttacher();
        // Set the Refreshable View to be the ListView and the refresh listener to be this.
        mPullToRefreshAttacher.addRefreshableView(listView, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mPreTask) {
            mPreTask.cancel(true);
            mPreTask = null;
        }
    }

    private void performPreTask() {
        if (null == mPreTask) {
            mPreTask = new AsyncTask<Void, Void, String>() {
                boolean mResult = false;
                long startTime;
                @Override
                protected void onPreExecute() {
                    mResult = false;
                    startTime = System.currentTimeMillis();
                }

                @Override
                protected String doInBackground(Void... voids) {
                    try {
                        final HashMap<String, String> filter = null;
                        mResult = BillingClient.listBill(getActivity(), mUser, filter);
                        return "listBill succeed by " + getCurrentUserName();
                    } catch (Exception e) {
                        return "listBill exception " + e.getMessage();
                    }
                }

                @Override
                protected void onPostExecute(String result) {
                    final long diff = PerformanceUtils.showTimeDiff(startTime, System.currentTimeMillis());
                    PerformanceUtils.showToast(getActivity(), result, diff);

                    if (mResult) {
                        Toast.makeText(getActivity(), R.string.post_result_succeed, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), R.string.post_result_fail, Toast.LENGTH_SHORT).show();
                    }
                }
            };
        }
        mPreTask.execute();
    }

    private String getCurrentUserName() {
        if (null == mUser) return "null";
        return mUser.getNickName();
    }

    private String getContent(View hostView) {
        if (null != hostView) {
            TextView textView = (TextView)hostView.findViewById(R.id.tv_content);
            if (null != textView) {
                return textView.getText().toString();
            }
        }
        return "";
    }

    private static String[] ITEMS = {"Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam",
            "Abondance", "Ackawi", "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu",
            "Airag", "Airedale", "Aisy Cendre", "Allgauer Emmentaler", "Abbaye de Belloc",
            "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi", "Acorn", "Adelost",
            "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler"};

    private PullToRefreshAttacher mPullToRefreshAttacher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
//
//        if (null == mUser) {
//            mUser = ((DemoApplication)getActivity().getApplication()).getLoginUser();
//            if (mUser == null) {
//                Log.e(TAG, "initViews, error with null user found");
//            }
//        }
//
//        /**
//         * Get ListView and give it an adapter to display the sample items
//         */
//        ListView listView = getListView();
//        ListAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
//                ITEMS);
//        listView.setAdapter(adapter);
//
//        /**
//         * Here we create a PullToRefreshAttacher manually without an Options instance.
//         * PullToRefreshAttacher will manually create one using default values.
//         */
////            mPullToRefreshAttacher = PullToRefreshAttacher.get(getActivity());
//        mPullToRefreshAttacher = ((MainActivity) getActivity())
//                .getPullToRefreshAttacher();
//        // Set the Refreshable View to be the ListView and the refresh listener to be this.
//        mPullToRefreshAttacher.addRefreshableView(listView, this);
        return view;
    }

    @Override
    public void onRefreshStarted(View view) {
        /**
         * Simulate Refresh with 4 seconds sleep
         */
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(SIMULATED_REFRESH_LENGTH);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);

                // Notify PullToRefreshAttacher that the refresh has finished
                mPullToRefreshAttacher.setRefreshComplete();
            }
        }.execute();
    }
}