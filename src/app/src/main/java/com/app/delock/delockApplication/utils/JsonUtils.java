package com.app.delock.delockApplication.utils;

import android.net.Uri;

import com.app.delock.delockApplication.item.Item;
import com.thejuki.kformmaster.helper.FormBuildHelper;
import com.thejuki.kformmaster.model.BaseFormElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Marky on 30/04/2018.
 */

public class JsonUtils {

    public static JSONObject collectDataIntoJSON(FormBuildHelper formBuilder) {
        //Gather up JSON data
        final ArrayList<BaseFormElement<?>> elements = formBuilder.getElements();

        String title = elements.get(1).getValueAsString();
        double depositAmount = Double.valueOf(elements.get(2).getValueAsString());
        double price = Double.valueOf(elements.get(3).getValueAsString());
        String desc = elements.get(4).getValueAsString();

        JSONObject json = new JSONObject();
        try {
            json.put("Title", title);
            json.put("Deposit", depositAmount);
            json.put("Price", price);
            json.put("Description", desc);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
