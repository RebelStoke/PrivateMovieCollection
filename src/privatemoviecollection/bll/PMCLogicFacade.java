package privatemoviecollection.bll;

import privatemoviecollection.be.Category;
import privatemoviecollection.be.Movie;

public interface PMCLogicFacade
{
    public void addMovie(String name, float rating, String path, float personalPath);
    
    public void removeMovie(Movie movie);
    
    public void addCategory(Category category);
    
    public void removeCategory(Category category);
    
    public void setCategory(Movie movie, Category category);
    
    public Movie searchMovie(String quote);
    
    public void addPersonalRating(float personalRating, Movie movie);
    
    public void removePersonalRating(Movie movie);
}
