package com.elanic.pulkit.moviesearch.apimodel;

import java.util.ArrayList;

/**
 * Created by pulkit on 26/3/17.
 */


public class Movies {
    private ArrayList<Search> Search;

    private String totalResults;

    private String Response;

    public ArrayList<Search> getSearch() {
        return Search;
    }

    public void setSearch(ArrayList<Search> Search) {
        this.Search = Search;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String Response) {
        this.Response = Response;
    }

    @Override
    public String toString() {
        return "ClassPojo [Search = " + Search + ", totalResults = " + totalResults + ", Response = " + Response + "]";
    }
}

