package Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.creec.crungooyummy.MyAdapter;
import com.creec.crungooyummy.MyObserver;
import com.creec.crungooyummy.R;
import com.creec.crungooyummy.YummyListActivity;

public class YummyAddressFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2;

    private MyObserver myObserver;

    static {
        ARG_PARAM2 = "param2";
    }

    public void setMyObserver(MyObserver myObserver) {  // 这里用了多态 二次分派 （也可以用系统给的Observer写）
        this.myObserver = myObserver;
    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private YummyListActivity mActivity;
    private JSONArray jsonArray;
    private MyAdapter mAdapter;
    private int mPosition;

    public YummyAddressFragment() {
        // Required empty public constructor
    }

    public void setPos(int pos) {
        mPosition = pos;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YummyAddressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YummyAddressFragment newInstance(String param1, String param2) {
        YummyAddressFragment fragment = new YummyAddressFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (YummyListActivity) getActivity();
        mAdapter = new MyAdapter(getActivity(), mActivity.getJsonArray());
        jsonArray = mActivity.getJsonArray();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

            inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.fragment_address_layout, null);

            // mPosition = mActivity.getmSelectedPos();

            TextView navigationRestaurant = (TextView) view.findViewById(R.id.navigation_restaurant);
            TextView addressRestaurant = (TextView) view.findViewById(R.id.address_restaurant);
            TextView cityRestaurant = (TextView) view.findViewById(R.id.city_restaurant);
            TextView tagsRestaurant = (TextView) view.findViewById(R.id.tags_restaurant);

        if (mPosition != -1) {
            navigationRestaurant.setText(jsonArray.getJSONObject(mPosition).getString("navigation"));
            addressRestaurant.setText(jsonArray.getJSONObject(mPosition).getString("address"));
            cityRestaurant.setText(jsonArray.getJSONObject(mPosition).getString("city"));
            tagsRestaurant.setText(jsonArray.getJSONObject(mPosition).getString("tags"));


        } else {
            // do nothing
        }
         return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        myObserver.notifyStatusChange(0);
    }
}
