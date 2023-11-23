package Lib.Response;

import Client.Model.ArticlePanier;
import Lib.Interface.IResponse;

import java.util.List;

public class ResponseGetArticles implements IResponse {
    private final List<ArticlePanier> articles;

    private boolean isFound = false;

    public ResponseGetArticles() {articles = null;}

    public ResponseGetArticles(List<ArticlePanier> articles, boolean isFound){
        this.articles=articles;
        this.isFound=isFound;
    }

    public List<ArticlePanier> getArticles(){return articles;}
    public boolean isFound(){return isFound;}
}
