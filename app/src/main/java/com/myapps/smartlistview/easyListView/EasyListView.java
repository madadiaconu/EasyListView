package com.myapps.smartlistview.easyListView;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diaconu Madalina on 22.01.2016.
 */
public class EasyListView extends ListView {

    private static final String ID = "id";

    private Context context;
    private List<Widget> widgets;

    public EasyListView(Context context) {
        super(context);
        this.context = context;
    }

    public void init(int layoutId, List<Object> objects) {
        this.widgets = new ArrayList<>();
        try {
            getWidgetsFromLayoutXML((Activity) context, layoutId);
            String className = objects.get(0).getClass().getName();
            checkClassRequirements(className);
            EasyListViewAdapter easyListViewAdapter = new EasyListViewAdapter(context,layoutId,objects,this.widgets);
            this.setAdapter(easyListViewAdapter);
        } catch (XmlPullParserException | IOException | NoSuchFieldException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getWidgetsFromLayoutXML(Activity activity, int layoutId)
            throws XmlPullParserException, IOException {
        Resources res = activity.getResources();
        XmlResourceParser xmlResourceParser = res.getXml(layoutId);
        xmlResourceParser.next();
        int eventType = xmlResourceParser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                this.widgets.add(buildWidget(xmlResourceParser));
            }
            eventType = xmlResourceParser.next();
        }
    }

    private Widget buildWidget(XmlResourceParser xmlResourceParser){
        Widget result = null;
        String widgetType = xmlResourceParser.getName();
        for (int i = 0; i < xmlResourceParser.getAttributeCount(); i++) {
            String attributeName = xmlResourceParser.getAttributeName(i);
            String attributeValue = xmlResourceParser.getAttributeValue(i);
            if (attributeName.equals(ID)) {
                int idInt = Integer.valueOf(attributeValue.substring(1));
                String id = getResources().getResourceName(Integer.valueOf(attributeValue.substring(1)));
                result = new Widget(idInt, id.split("/")[1], widgetType);
                break;
            }
        }
        return result;
    }

    private void checkClassRequirements(String className) throws ClassNotFoundException, NoSuchFieldException {
        Class clazz = Class.forName(className);
        for (Widget w: this.widgets) {
            clazz.getField(w.getId());
        }
    }

}
