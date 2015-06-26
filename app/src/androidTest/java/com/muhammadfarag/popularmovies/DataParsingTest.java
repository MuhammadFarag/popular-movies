package com.muhammadfarag.popularmovies;

import android.test.AndroidTestCase;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Project: Popular Movies
 * Created by muhammad on 26/06/15.
 */
public class DataParsingTest extends AndroidTestCase{


    public void testCanRetrieveUpperLevelFields() throws JSONException {
        String testData = getContext().getString(R.string.server_example_payload);
        JSONObject jsonTestData = new JSONObject(testData);
        jsonTestData.getJSONArray("results");
        jsonTestData.getInt("page");
        jsonTestData.getInt("total_pages");
        jsonTestData.getInt("total_results");
    }

}
