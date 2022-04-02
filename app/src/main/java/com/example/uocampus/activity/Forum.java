package com.example.uocampus.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.text.SimpleDateFormat;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.text.TextUtils.TruncateAt;

import com.example.uocampus.R;

public class Forum extends ListActivity {
    public String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Random rand = new Random();
        int count = 0;
        ArrayList<Integer> ary = new ArrayList<Integer>();
        for(int i = 0; i < 40; i++){
            count = rand.nextInt(10);
            ary.add(count);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());
        str = formatter.format(curDate);

        ArrayAdapter adpt = new aryadpt(this,-1,ary);
        setListAdapter(adpt);
//        getListView().setDivider(null);  // remove android default divider

    }
    private class aryadpt extends ArrayAdapter{
        private ArrayList<Integer> item;
        private LayoutInflater LayoutInflater;
        private ViewHolder viewholder;
        private Context context;

        private class ViewHolder{
            public TextView title;
            public TextView content;
            public TextView date;
        }

        public aryadpt(Context context, int resource, ArrayList<Integer> ary) {
            super(context, resource);
            this.LayoutInflater = android.view.LayoutInflater.from(context);
            this.item = ary;
            this.context = context;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent){
            if(convertView == null){
                convertView = LayoutInflater.inflate(R.layout.item, null);
                TextView title = (TextView) convertView.findViewById(R.id.title);
                TextView content = (TextView) convertView.findViewById(R.id.content);
                TextView date = (TextView) convertView.findViewById(R.id.date);

                //Single post detail can be modified here
                viewholder = new ViewHolder();
                viewholder.title = title;
                viewholder.content = content;
                viewholder.content.setEllipsize(TruncateAt.END);
                viewholder.content.setMaxLines(4);
                date.setText(str);
                viewholder.date = date;

                convertView.setTag(viewholder);

            }else{
                viewholder = (ViewHolder) convertView.getTag();
            }
//            change title later
            viewholder.title.setText("Title here");

        return convertView;
        }
        public int getCount(){
            return item.size();
        }
    }
}