package com.myapps.easylistview.bl;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.widget.ListView;

import com.myapps.easylistview.exceptions.InvalidModelClassException;
import com.myapps.easylistview.exceptions.InvalidXmlException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diaconu Madalina on 22.01.2016.
 */
public class EasyListView extends ListView {

    private Context context;
    private List<Widget> widgets;

    public EasyListView(Context context) {
        super(context);
        this.context = context;
    }

    public EasyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public EasyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public EasyListView(Context context, int layout, List<Object> objects) {
        super(context);
        this.context = context;
    }

    public void init(int layoutId, List<Object> objects) throws InvalidModelClassException, InvalidXmlException {
        this.widgets = new ArrayList<>();
        getWidgetsFromLayoutXML((Activity) context, layoutId);
        String className = objects.get(0).getClass().getName();
        checkClassRequirements(className);
        EasyListViewAdapter easyListViewAdapter = new EasyListViewAdapter(context, layoutId, objects, this.widgets);
        this.setAdapter(easyListViewAdapter);
    }

    private void getWidgetsFromLayoutXML(Activity activity, int layoutId)
            throws InvalidXmlException {
        try {
            Resources res = activity.getResources();
            XmlResourceParser xmlResourceParser = res.getXml(layoutId);
            xmlResourceParser.next();
            int eventType = xmlResourceParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    addWidgetToList(xmlResourceParser);
                }
                eventType = xmlResourceParser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            throw new InvalidXmlException();
        }
    }

    private void addWidgetToList(XmlResourceParser xmlResourceParser){
        String widgetType = xmlResourceParser.getName();
        for (int i = 0; i < xmlResourceParser.getAttributeCount(); i++) {
            String attributeName = xmlResourceParser.getAttributeName(i);
            String attributeValue = xmlResourceParser.getAttributeValue(i);
            if (attributeName.equals("id")) {
                int idInt = Integer.valueOf(attributeValue.substring(1));
                String id = getResources().getResourceName(Integer.valueOf(attributeValue.substring(1)));
                this.widgets.add(new Widget(idInt, id.split("/")[1], widgetType));
            }
        }
    }

    private void checkClassRequirements(String className) throws InvalidModelClassException {
        try {
            Class clazz = Class.forName(className);
            for (Widget w : this.widgets) {
                clazz.getField(w.getId());
            }
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            throw new InvalidModelClassException();
        }
    }

}
