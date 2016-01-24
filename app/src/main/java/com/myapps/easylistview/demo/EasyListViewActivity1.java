package com.myapps.easylistview.demo;

import android.app.Activity;
import android.os.Bundle;

import com.myapps.easylistview.R;
import com.myapps.easylistview.bl.EasyListView;
import com.myapps.easylistview.exceptions.InvalidModelClassException;
import com.myapps.easylistview.exceptions.InvalidXmlException;

import java.util.ArrayList;
import java.util.List;


public class EasyListViewActivity1 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Object> modelClassList = new ArrayList<>();
        modelClassList.add(new ModelClassExemple1("prettyString1", "prettyString2", "prettyString3"));
        modelClassList.add(new ModelClassExemple1("a", "aa", "aaa"));
        modelClassList.add(new ModelClassExemple1("b", "bb", "bbb"));

        EasyListView easyListView = (EasyListView)findViewById(R.id.easyListView);
        try {
            easyListView.init(R.layout.view_item_listview,modelClassList);
        } catch (InvalidModelClassException | InvalidXmlException e) {
            e.printStackTrace();
        }
    }
}
