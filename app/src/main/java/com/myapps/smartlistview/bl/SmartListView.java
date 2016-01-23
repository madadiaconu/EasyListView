package com.myapps.smartlistview.bl;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;

import com.myapps.smartlistview.model.Widget;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diaconu Madalina on 22.01.2016.
 */
public class SmartListView extends ListView {

    private Context context;
    private List<Widget> widgets;

    public SmartListView(Context context) {
        super(context);
        this.context = context;
    }

    public SmartListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public SmartListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public SmartListView(Context context, int layout, List<Object> objects) {
        super(context);
        this.context = context;
    }

    public void init(int layoutId, List<Object> objects) {
        this.widgets = new ArrayList<>();
        try {
            getWidgetsFromLayoutXML((Activity) context, layoutId);
            String className = objects.get(0).getClass().getName();
            checkClassRequirements(className);
            SmartListAdapter smartListAdapter = new SmartListAdapter(context,layoutId,objects,this.widgets);
            this.setAdapter(smartListAdapter);
        } catch (XmlPullParserException | IOException | NoSuchFieldException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getWidgetsFromLayoutXML(Activity activity, int layoutId)
            throws XmlPullParserException, IOException {
        Resources res = activity.getResources();
        XmlResourceParser xpp = res.getXml(layoutId);
        xpp.next();
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                String widgetType = xpp.getName();
                for (int i = 0; i < xpp.getAttributeCount(); i++) {
                    String attributeName = xpp.getAttributeName(i);
                    String attributeValue = xpp.getAttributeValue(i);
                    if (attributeName.equals("id")) {
                        int idInt = Integer.valueOf(attributeValue.substring(1));
                        String id = getResources().getResourceName(Integer.valueOf(attributeValue.substring(1)));
                        this.widgets.add(new Widget(idInt, id.split("/")[1], widgetType));
                        break;
                    }

                }
            }
            eventType = xpp.next();
        }
    }

    private void checkClassRequirements(String className) throws ClassNotFoundException, NoSuchFieldException {
        Class clazz = Class.forName(className);
        for (Widget w: this.widgets) {
            clazz.getField(w.getId());
        }
    }

}
