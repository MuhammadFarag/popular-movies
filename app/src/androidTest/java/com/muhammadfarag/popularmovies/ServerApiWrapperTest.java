package com.muhammadfarag.popularmovies;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: Popular Movies
 * Created by muhammad on 03/07/15.
 */
public class ServerApiWrapperTest extends AndroidTestCase {

    public void testGivenTotalNumberOfMoviesAndRequiredMoviesPerPageCaclculateNumberOfPages(){
        int totalNumberOfMovies = 1002;
        int requiredPageSize = 40;
        int expected = 26;
        int actual = totalNumberOfMovies/requiredPageSize;
        if(totalNumberOfMovies%requiredPageSize != 0){
            actual += 1;
        }
        assertEquals("Number of pages calculated is not correct", expected, actual);
    }

    public void testGivenRequiredPageSizeReturnListOfMoviesOfTheSameSize() throws Exception{
        int requiredPageSize = 40;
        List<Movie> movies = new ArrayList<>();
        MovieDatabaseServerConnector connector = new MovieDatabaseServerConnector(getContext());
        String data1 = connector.getPage(1);
        String data2 = connector.getPage(2);
        DataParser parser1 = new DataParser(data1);
        DataParser parser2 = new DataParser(data2);
        int numberOfResultsInCurrentPage = parser1.getMovies().size() + parser2.getMovies().size();

        assertEquals("Number of movies returned in list", requiredPageSize, numberOfResultsInCurrentPage);
    }
}
