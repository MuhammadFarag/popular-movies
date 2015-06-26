package com.muhammadfarag.popularmovies;

import android.test.AndroidTestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Project: Popular Movies
 * Created by muhammad on 26/06/15.
 */
public class DataParsingTest extends AndroidTestCase {

    DataParser parser;
    @Override
    public void setUp() throws Exception {
        super.setUp();
        parser = new DataParser(getContext().getString(R.string.server_example_payload));
    }

    public void testCanRetrievePageNumber() throws JSONException {
        int pageNumber = parser.getCurrentPageNumber();
        assertEquals("Page number", 1, pageNumber);
    }

    public void testCanRetrieveTotalPages() throws JSONException {
        int pageNumber = parser.getTotalNumberOfPages();
        assertEquals("Total number of pages", 11584, pageNumber);
    }

    public void testCanRetrieveTotalResults() throws JSONException {
        int pageNumber = parser.getTotalNumberOfResults();
        assertEquals("total number of results", 231676, pageNumber);
    }

    public void testCheckSizeOfRetrievedResults() throws JSONException {
        int pageNumber = parser.getNumberOfResultsInCurrentPage();
        assertEquals("Number of results per page", 20, pageNumber);
    }

    public void testRetrievalOfFirstMovieData() throws JSONException {
        String testData = getContext().getString(R.string.server_example_payload);
        JSONObject jsonTestData = new JSONObject(testData);
        JSONArray results = jsonTestData.getJSONArray("results");
        JSONObject firstMovieJson = results.getJSONObject(0);
        assertEquals("Original Title", "Jurassic World", firstMovieJson.getString("original_title"));
        assertEquals("Poster path", "/uXZYawqUsChGSj54wcuBtEdUJbh.jpg", firstMovieJson.getString("poster_path"));
        assertEquals("Plot synopsis", "Twenty-two years after the events of Jurassic Park, Isla Nublar now features a fully functioning dinosaur theme park, Jurassic World, as originally envisioned by John Hammond.", firstMovieJson.getString("overview"));
        assertEquals("User Rating", 7d, firstMovieJson.getDouble("vote_average"));
        assertEquals("Release Date", "2015-06-12", firstMovieJson.getString("release_date"));
    }

    class DataParser{
        private String testData;
        private final JSONObject jsonTestData;

        public DataParser(String testData) throws JSONException {
            this.testData = testData;
            jsonTestData = new JSONObject(testData);
        }

        public int getCurrentPageNumber() throws JSONException {
            return jsonTestData.getInt("page");
        }


        public int getTotalNumberOfPages() throws JSONException {
            return jsonTestData.getInt("total_pages");
        }

        public int getTotalNumberOfResults() throws JSONException {
            return jsonTestData.getInt("total_results");
        }

        public int getNumberOfResultsInCurrentPage() throws JSONException {
            JSONArray results = jsonTestData.getJSONArray("results");
            return results.length();
        }
    }
}
