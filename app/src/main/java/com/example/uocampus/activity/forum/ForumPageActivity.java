package com.example.uocampus.activity.forum;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.uocampus.R;
import com.example.uocampus.dao.ForumDao;
import com.example.uocampus.dao.impl.ForumDaoImpl;
import com.example.uocampus.model.PostModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForumPageActivity extends ListActivity {

    private static final String TAG = ForumPageActivity.class.getSimpleName();
    public List<PostModel> postList;
    public List<Map<String, String>> itemList;
    private Button btnBack;
    private ForumDao forumDao = new ForumDaoImpl(ForumPageActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_list);
        btnBack = findViewById(R.id.list_back);

        postList = forumDao.getPosts();
        itemList = new ArrayList<>();

        for(PostModel post : postList) {
            Map<String, String> map = new HashMap<>();
            map.put("username", post.getUsername());
            map.put("time", post.getTime());
            map.put("title", post.getTitle());
            map.put("content", post.getPost_content());
            itemList.add(map);
        }

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(ForumPageActivity.this, EntryPageActivity.class);
            startActivity(intent);
        });

        SimpleAdapter adapter = new SimpleAdapter(this
                , itemList
                , R.layout.single_post
                ,new String[]{"username","time","title","content"}
                ,new int[]{R.id.id,R.id.date,R.id.title,R.id.content});

        ListView listView = this.findViewById(android.R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Map<String, String> map = (Map<String, String>) parent.getItemAtPosition(position);
            String content = map.get("content");
            Log.i(TAG,"Item clicked "+ content);
            share(content);
//                onShareClick(sr);
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