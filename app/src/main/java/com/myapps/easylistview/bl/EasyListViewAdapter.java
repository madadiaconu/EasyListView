package com.myapps.easylistview.bl;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diaconu Madalina on 23.01.2016.
 */
public class EasyListViewAdapter extends ArrayAdapter<Object> {

    private Context context;
    private int layout;
    private List<Object> objects;
    private List<Widget> widgets;

    public EasyListViewAdapter(Context context, int resource, List<Object> objects, List<Widget> widgets) {
        super(context, resource, objects);
        this.context = context;
        this.layout = resource;
        this.objects = objects;
        this.widgets = widgets;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;
        if (convertView == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layout, parent, false);

            viewHolder = new ViewHolderItem();
            viewHolder.viewList = new ArrayList<>();
            for (Widget w: this.widgets) {
                View v = convertView.findViewById(w.getIdInt());
                viewHolder.viewList.add(v);
            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        setRow(position,viewHolder);
        return convertView;
    }

    static class ViewHolderItem {
        List<View> viewList;
    }

    private void setRow(int currentObjectPosition, ViewHolderItem viewHolder){
        Object currentObject = objects.get(currentObjectPosition);
        for (int i=0; i<this.widgets.size();i++) {
            Widget widget = this.widgets.get(i);
            View view = viewHolder.viewList.get(i);
            setRowElement(widget,view,currentObject);
        }
    }

    private void setRowElement(Widget widget, View view, Object currentObject){
        try {
            Field field = currentObject.getClass().getDeclaredField(widget.getId());
            field.setAccessible(true);
            if (view instanceof TextView) {
                String fieldValue = field.get(currentObject).toString();
                ((TextView) view).setText(fieldValue);
            } else if (view instanceof ImageView) {
                int fieldValue = field.getInt(currentObject);
                ((ImageView) view).setImageResource(fieldValue);
            } else {
                int fieldValue = field.getInt(currentObject);
                view.setBackgroundColor(fieldValue);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
