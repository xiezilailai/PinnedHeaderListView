package com.example.xiezi.expandablelistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NewListView expandableListView;
    private List<String> groupArray;
    private List<List<String>> childArray;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expandableListView = (NewListView) findViewById(R.id.expand_listView);

        groupArray = new ArrayList<>();
        childArray = new ArrayList<>();

        groupArray.add("第一行");
        groupArray.add("第二行");
        groupArray.add("第三行");
        groupArray.add("第四行");
        groupArray.add("第五行");
        groupArray.add("第六行");
        groupArray.add("第七行");
        groupArray.add("第八行");
        groupArray.add("第九行");
        groupArray.add("第十行");
        groupArray.add("第十一行");

        List<String> tmp = new ArrayList<>();
        tmp.add("第一条");
        tmp.add("第二条");
        tmp.add("第三条");
        tmp.add("第四条");
        tmp.add("第五条");

        for (int i = 0; i < groupArray.size(); i++) {
            childArray.add(tmp);
        }

        ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter(groupArray,childArray,this,expandableListView);
        expandableListView.setAdapter(expandableListAdapter);

    }





}
