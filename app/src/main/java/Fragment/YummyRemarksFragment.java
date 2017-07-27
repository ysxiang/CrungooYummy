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

public class YummyRemarksFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2;

    private MyObserver myObserver;

    public void setMyObserver(MyObserver myObserver) {
        this.myObserver = myObserver;
    }

    static {
        ARG_PARAM2 = "param2";
    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private YummyListActivity mActivity;
    private JSONArray jsonArray;
    private MyAdapter mAdapter;
    private int mPosition;

    public YummyRemarksFragment() {
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
        View view = inflater.inflate(R.layout.fragment_remarks_layout, null);

        // mPosition = mActivity.getmSelectedPos();

        TextView allRemarks = (TextView) view.findViewById(R.id.all_remarks_restaurant);
        TextView veryGoodRemarks = (TextView) view.findViewById(R.id.very_good_remarks_restaurant);
        TextView goodRemarks = (TextView) view.findViewById(R.id.good_remarks_restaurant);
        TextView commonRemarks = (TextView) view.findViewById(R.id.common_remarks_restaurant);
        TextView badRemarks = (TextView) view.findViewById(R.id.bad_remarks_restaurant);
        TextView veryBadRemarks = (TextView) view.findViewById(R.id.very_bad_remarks_restaurant);

        if (mPosition != -1) {
            allRemarks.setText(jsonArray.getJSONObject(mPosition).getString("all_remarks"));
            veryGoodRemarks.setText(jsonArray.getJSONObject(mPosition).getString("very_good_remarks"));
            goodRemarks.setText(jsonArray.getJSONObject(mPosition).getString("good_remarks"));
            commonRemarks.setText(jsonArray.getJSONObject(mPosition).getString("common_remarks"));
            badRemarks.setText(jsonArray.getJSONObject(mPosition).getString("bad_remarks"));
            veryBadRemarks.setText(jsonArray.getJSONObject(mPosition).getString("very_bad_remarks"));

        } else {
            // do nothing
        }

        return view;

    }
    @Override
    public void onStart() {
        super.onStart();
        myObserver.notifyStatusChange(1);
    }
}
