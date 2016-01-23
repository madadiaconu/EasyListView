package com.myapps.smartlistview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.myapps.smartlistview.bl.SmartListView;
import com.myapps.smartlistview.model.Test1;
import com.myapps.smartlistview.model.Test2;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Object> test1List = new ArrayList<>();
        test1List.add(new Test1("prettyString1","prettyString2","prettyString3"));
        test1List.add(new Test1("a","aa","aaa"));
        test1List.add(new Test1("b","bb","bbb"));


        List<Object> test2List = new ArrayList<>();
        test2List.add(new Test2("prettyString1",R.drawable.logout));
        test2List.add(new Test2("a",R.drawable.user));
        test2List.add(new Test2("b",R.drawable.pass));

        SmartListView smartListView = (SmartListView)findViewById(R.id.smartListView);
        Log.d("zzznr",test2List.size()+"");
        smartListView.init(R.layout.view_item_listview2,test2List);
    }
}
