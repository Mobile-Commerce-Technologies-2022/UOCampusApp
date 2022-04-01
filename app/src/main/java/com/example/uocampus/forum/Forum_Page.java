package com.example.uocampus.forum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.net.Uri;

import com.example.uocampus.MainActivity;
import com.example.uocampus.R;

public class Forum_Page extends ListActivity {

    private static final String TAG = Forum_Page.class.getSimpleName();
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
                Object sharable = map.get("content");
                Log.i(TAG,"Item clicked "+ sharable);
                String sr = (String)sharable;
                share(sr);
//                onShareClick(sr);
            }

        });

    }
    public void share(String sr) {

        Intent S = new Intent(Intent.ACTION_SEND);
        Uri SU = Uri.parse("android.resource://com.example.uocampus/"+ sr);
        S.setType("String/text");
        S.putExtra(Intent.EXTRA_STREAM, SU);
        startActivity(Intent.createChooser(S, "Share post using"));
    }

//    public void onShareClick(String str) {
//        Resources resources = getResources();
//
//        Intent emailIntent = new Intent();
//        emailIntent.setAction(Intent.ACTION_SEND);
//        // Native email client doesn't currently support HTML, but it doesn't hurt to try in case they fix it
//        emailIntent.putExtra(Intent.EXTRA_TEXT, str);
//        emailIntent.setType("message/rfc822");
//
//        PackageManager pm = getPackageManager();
//        Intent sendIntent = new Intent(Intent.ACTION_SEND);
//        sendIntent.setType("text/plain");

//        Intent openInChooser = Intent.createChooser(emailIntent, str);
//
//        List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
//        List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();
//        for (int i = 0; i < resInfo.size(); i++) {
//            // Extract the label, append it, and repackage it in a LabeledIntent
//            ResolveInfo ri = resInfo.get(i);
//            String packageName = ri.activityInfo.packageName;
//            if(packageName.contains("android.email")) {
//                emailIntent.setPackage(packageName);
//            } else if(packageName.contains("twitter") || packageName.contains("facebook") || packageName.contains("mms") || packageName.contains("android.gm")) {
//                Intent intent = new Intent();
//                intent.setComponent(new ComponentName(packageName, ri.activityInfo.name));
//                intent.setAction(Intent.ACTION_SEND);
//                intent.setType("text/plain");
//                if(packageName.contains("twitter")) {
//                    intent.putExtra(Intent.EXTRA_TEXT, str);
//                } else if(packageName.contains("facebook")) {
//                    // Warning: Facebook IGNORES our text. They say "These fields are intended for users to express themselves. Pre-filling these fields erodes the authenticity of the user voice."
//                    // One workaround is to use the Facebook SDK to post, but that doesn't allow the user to choose how they want to share. We can also make a custom landing page, and the link
//                    // will show the <meta content ="..."> text from that page with our link in Facebook.
//                    intent.putExtra(Intent.EXTRA_TEXT, str);
//                } else if(packageName.contains("mms")) {
//                    intent.putExtra(Intent.EXTRA_TEXT,str);
//                } else if(packageName.contains("android.gm")) {
//                    intent.putExtra(Intent.EXTRA_TEXT, str);
//                    intent.setType("message/rfc822");
//                }
//
//                intentList.add(new LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon));
//            }
//        }
//
//        // convert intentList to array
//        LabeledIntent[] extraIntents = intentList.toArray( new LabeledIntent[ intentList.size() ]);
//
//        openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
//        startActivity(openInChooser);
//    }
}