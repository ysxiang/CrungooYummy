package Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.creec.crungooyummy.MyAdapter;
import com.creec.crungooyummy.R;
import com.creec.crungooyummy.YummyListActivity;

public class YummyListFragment extends Fragment {
    private YummyListActivity mActivity;    // 因为用到YummyListActivity
    private MyAdapter mAdapter;
    private ListView mListView;

    private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() { // 匿名对象
        @Override
        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
            // mActivity.setmSelectedPos(position);
            YummyDetailFragment f = new YummyDetailFragment();
            f.setPos(position);
            mActivity.launchFragment(f);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (YummyListActivity) getActivity();  // 在onCreate强制转换成YummyListActivity 注意使用 在onCreate里面初始化 高耦合
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {   // 在所有的onCreate和onCreateView都无法取到高 宽，都是0.在画完之后才能取到/解决办法：measure
        View view = inflater.inflate(R.layout.fragment_yummy_list, null);
        mListView = (ListView) view.findViewById(R.id.yummy_list);
        // 横屏时会析构 所以放在onCreateView中，这里不允许转屏
        JSONArray jsonArray = mActivity.getJsonArray();  // 把YummyListActivity的jsonArray改成public
        mAdapter = new MyAdapter(getActivity(), jsonArray); // 不能把fragment当context传进去，因为类型不对，使用（getActivity().getApplicationContext()）取得fragment的context
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(listener);
        return view;    // 让系统知道
    }
}
