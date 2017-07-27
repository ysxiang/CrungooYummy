package com.creec.crungooyummy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;

public class MyAdapter extends BaseAdapter {
    private int position;
    private LayoutInflater mInflater;   // 得到一个LayoutInfalter对象用来导入布局
    private JSONArray data;

    public MyAdapter(Context context, JSONArray arr) {  // 构造函数
        this.mInflater = LayoutInflater.from(context);
        data = arr;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int getCount() { // ListView的长度，在此适配器中所代表的数据集中的条目数
        return data.size();
    }

    @Override
    public Object getItem(int position) {   // 获取数据集中与指定索引对应的数据项
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {   // 取在列表中与指定索引对应的行id
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { // 调用getView()方法，根据长度逐一绘制ListView的每一行，也不能取到高 宽 解决方法：measure
        // 还是需要判断convertView是否为null

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_info_list_item, null);
        }

        TextView title = (TextView) convertView.findViewById(R.id.name);
        title.setText(data.getJSONObject(position).getString("name"));
        // convertView.setTag(title);

        return convertView;    // 这样写不是很好 因为要return 已经操作后的view
    }
}
