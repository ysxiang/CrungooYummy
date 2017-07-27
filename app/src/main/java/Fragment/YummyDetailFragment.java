package Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.creec.crungooyummy.FragAdapter;
import com.creec.crungooyummy.MyObserver;
import com.creec.crungooyummy.R;
import com.creec.crungooyummy.YummyListActivity;

import java.util.ArrayList;
import java.util.List;

import static com.creec.crungooyummy.R.id.viewpager;

public class YummyDetailFragment extends Fragment implements MyObserver {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ViewPager mViewPager;
    private List<Fragment> mFragmentsContainer = new ArrayList<>();   // 存放fragments的容器

    private FragAdapter fAdapter = null;   // Fragment适配器

    private YummyAddressFragment mAddressFragment;
    private YummyRemarksFragment mRemarksFragment;

    private View view;  // Fragment的布局

    private int mPosition;

    private YummyListActivity mActivity;
    private JSONArray jsonArray;

    private TextView mName;
    private ImageView mPhoto;

    private TextView addressTag;
    private TextView remarksTag;

    public YummyDetailFragment() {
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
     * @return A new instance of fragment YummyDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YummyDetailFragment newInstance(String param1, String param2) {
        YummyDetailFragment fragment = new YummyDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mActivity = (YummyListActivity) getActivity();
        jsonArray = mActivity.getJsonArray();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_detail_container, null);

        initViews();    // 初始化控件
        initViewPages();

        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initViews() {
        mName = (TextView) view.findViewById(R.id.name_restaurant);
        mPhoto = (ImageView) view.findViewById(R.id.photo_restaurant);

        addressTag = (TextView) view.findViewById(R.id.text_address);
        remarksTag = (TextView) view.findViewById(R.id.text_remarks);

        if (mPosition != -1) {
            mName.setText(jsonArray.getJSONObject(mPosition).getString("name"));
        }
    }

    private void initViewPages() {
        // 把mAddressFragment和mRemarksFragment加到容器里
        mAddressFragment = new YummyAddressFragment();
        mAddressFragment.setPos(mPosition);
        mAddressFragment.setMyObserver(this);

        mRemarksFragment = new YummyRemarksFragment();
        mRemarksFragment.setPos(mPosition);
        mRemarksFragment.setMyObserver(this);

        mFragmentsContainer.add(mAddressFragment);
        mFragmentsContainer.add(mRemarksFragment);

        fAdapter = new FragAdapter(getActivity().getSupportFragmentManager(), mFragmentsContainer);
        mViewPager = (ViewPager) view.findViewById(viewpager);
        mViewPager.setAdapter(fAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                notifyStatusChange(state);
            }
        });
    }

    @Override
    public void notifyStatusChange(int status) {    // MyObserver的函数
        if (status == 0) {
            addressTag.setBackgroundColor(Color.BLUE);
            remarksTag.setBackgroundColor(Color.RED);
        } else if (status == 1) {
            addressTag.setBackgroundColor(Color.RED);
            remarksTag.setBackgroundColor(Color.BLUE);
        }
    }
}

