package com.example.uocampus.forum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.example.uocampus.R;

public class Forum_Page extends ListActivity {
    public DBControl loader;
    public List<Submit_Post_Func> list = new ArrayList<>();
    public List listitem = new ArrayList();
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_list);
        back = findViewById(R.id.list_back);
        loader = new DBControl(this,"TEST_1");
        loader.getData("TEST_1",list);

        for(int i = 0; i < loader.getTotal(); i++){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("ID", list.get(i).getHostID());
            map.put("time",list.get(i).getTime());
            map.put("title",list.get(i).getTitle());
            map.put("content",list.get(i).getPost_content());
            listitem.add(map);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Forum_Page.this, Entry_page.class);
                startActivity(intent);
            }
        });;

        SimpleAdapter adpt = new SimpleAdapter(this
                , listitem
                , R.layout.single_post
                ,new String[]{"ID","time","title","content"}
                ,new int[]{R.id.id,R.id.date,R.id.title,R.id.content});

        ListView listView = (ListView) this.findViewById(android.R.id.list);
        listView.setAdapter(adpt);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
            }
        });

    }
}