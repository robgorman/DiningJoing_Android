package com.ranchosoftware.diningjoint.Model;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;

import com.sandiegorestaurantsandbars.diningjoint.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by rob on 6/26/15.
 */
public class Cuisine extends Content {

  private static String TAG = City.class.getSimpleName();

  public Cuisine(Context context) {

    XmlResourceParser parser = context.getResources().getXml(R.xml.cuisine);

    int eventType = -1;
    while (eventType != XmlPullParser.END_DOCUMENT) {
      try {
        if (eventType == XmlPullParser.START_TAG) {
          String nodeName = parser.getName();
          if (nodeName.equals("option")) {
            String value = parser.getAttributeValue(null, "value").trim();
            String city = parser.nextText().trim();
            ContentItem nextItem = new ContentItem(city, value);
            content.add(nextItem);
          }

        }
        eventType = parser.next();
      } catch (XmlPullParserException e) {
        Log.d(TAG, e.getMessage());
      } catch (IOException e) {
        Log.d(TAG, e.getMessage());
      }
    }

  }
}