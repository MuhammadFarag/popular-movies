package com.muhammadfarag.popularmovies;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: Popular Movies
 * Created by muhammad on 03/07/15.
 */
public class ServerApiWrapperTest extends AndroidTestCase {

    private static final int DEFAULT_SERVER_PAGE_SIZE = 20;

    public void testGivenTotalNumberOfMoviesAndRequiredMoviesPerPageCaclculateNumberOfPages() {
        int totalNumberOfMovies = 1002;
        int requiredPageSize = 40;
        int expected = 26;
        int actual = totalNumberOfMovies / requiredPageSize;
        if (totalNumberOfMovies % requiredPageSize != 0) {
            actual += 1;
        }
        assertEquals("Number of pages calculated is not correct", expected, actual);
    }

    public void testGivenRequiredPageSizeOf40ReturnListOfMoviesOfTheSameSizeForFirstPage() throws Exception {
        int requiredPageSize = 40;
        int page = 1;
        MovieDatabaseServerConnector connector = new MovieDatabaseServerConnector(getContext());
        String data1 = connector.getPage(page);
        String data2 = connector.getPage(page + 1);
        DataParser parser1 = new DataParser(data1);
        DataParser parser2 = new DataParser(data2);
        List<Movie> movies = new ArrayList<>();
        movies.addAll(parser1.getMovies());
        movies.addAll(parser2.getMovies());
        int numberOfResultsInCurrentPage = movies.size();

        assertEquals("Number of movies returned in list", requiredPageSize, numberOfResultsInCurrentPage);
    }

    public void testGivenRequiredPageSizeOf60ReturnListOfMoviesOfTheSameSizeForThirdPage() throws Exception {
        int requiredPageSize = 60;
        MovieDatabaseServerConnector connector = new MovieDatabaseServerConnector(getContext());
        String data1 = connector.getPage(7);
        String data2 = connector.getPage(8);
        String data3 = connector.getPage(9);
        DataParser parser1 = new DataParser(data1);
        DataParser parser2 = new DataParser(data2);
        DataParser parser3 = new DataParser(data3);
        int numberOfResultsInCurrentPage = parser1.getMovies().size() + parser2.getMovies().size() + parser3.getMovies().size();

        assertEquals("Number of movies returned in list", requiredPageSize, numberOfResultsInCurrentPage);
    }

    public void testGivenRequiredPageSizeCalculateRequiredServerPagesBoundsExample1() {
        int requiredPageSize = 60;
        int pageNumber = 3;

        int numberOfServerPagesPerResult = requiredPageSize / DEFAULT_SERVER_PAGE_SIZE;
        int firstRequiredPage = (pageNumber - 1) * numberOfServerPagesPerResult + 1;
        int lastRequiredPage = pageNumber * numberOfServerPagesPerResult;

        assertEquals("First Required Page", 7, firstRequiredPage);
        assertEquals("Last Required Page", 9, lastRequiredPage);
    }


    public void testGivenRequiredPageSizeCalculateRequiredServerPagesBoundsExample2() {
        int requiredPageSize = 40;
        int pageNumber = 5;

        int numberOfServerPagesPerResult = requiredPageSize / DEFAULT_SERVER_PAGE_SIZE;
        int firstRequiredPage = (pageNumber - 1) * numberOfServerPagesPerResult + 1;
        int lastRequiredPage = pageNumber * numberOfServerPagesPerResult;

        assertEquals("First Required Page", 9, firstRequiredPage);
        assertEquals("Last Required Page", 10, lastRequiredPage);
    }
}
