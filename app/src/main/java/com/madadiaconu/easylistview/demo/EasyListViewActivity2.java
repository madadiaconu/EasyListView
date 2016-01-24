package com.madadiaconu.easylistview.demo;

import android.app.Activity;
import android.os.Bundle;

import com.madadiaconu.easylistview.R;
import com.madadiaconu.easylistview.bl.EasyListView;
import com.madadiaconu.easylistview.exceptions.InvalidModelClassException;
import com.madadiaconu.easylistview.exceptions.InvalidXmlException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diaconu Madalina on 24.01.2016.
 */
public class EasyListViewActivity2 extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Object> test2List = new ArrayList<>();
        test2List.add(new ModelClassExemple2("prettyString1",R.drawable.logout));
        test2List.add(new ModelClassExemple2("a",R.drawable.user));
        test2List.add(new ModelClassExemple2("b",R.drawable.pass));

        EasyListView easyListView = (EasyListView)findViewById(R.id.easyListView);
        try {
            easyListView.init(R.layout.view_item_listview2,test2List);
        } catch (InvalidModelClassException | InvalidXmlException e) {
            e.printStackTrace();
        }
    }
}
