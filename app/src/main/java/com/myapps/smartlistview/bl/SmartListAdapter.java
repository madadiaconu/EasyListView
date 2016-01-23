package com.myapps.smartlistview.bl;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapps.smartlistview.model.Widget;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Diaconu Madalina on 23.01.2016.
 */
public class SmartListAdapter extends ArrayAdapter<Object> {

    private Context context;
    private int layout;
    private List<Object> objects;
    private List<Widget> widgets;

    public SmartListAdapter(Context context, int resource, List<Object> objects, List<Widget> widgets) {
        super(context, resource, objects);
        this.context = context;
        this.layout = resource;
        this.objects = objects;
        this.widgets = widgets;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = LayoutInflater.from(context).inflate(layout, null);

        Object currentObject = objects.get(position);
        for (Widget w: this.widgets){
            View v = rowView.findViewById(w.getIdInt());
            try {
                Field field = currentObject.getClass().getDeclaredField(w.getId());
                field.setAccessible(true);
                if (v instanceof TextView){
                    String fieldValue = field.get(currentObject).toString();
                    ((TextView)v).setText(fieldValue);
                } else if (v instanceof ImageView){
                    int fieldValue = field.getInt(currentObject);
                    ((ImageView)v).setImageResource(fieldValue);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return rowView;
    }
}
