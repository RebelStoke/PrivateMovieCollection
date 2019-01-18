package privatemoviecollection.bll;

import java.util.List;
import privatemoviecollection.be.Category;
import privatemoviecollection.be.Movie;

public interface PMCLogicFacade
{
    public Movie addMovie(String name, float rating, String path, float personalPath, int id);
    
    public void removeMovie(Movie movie);
    
    public List getAllMoviesFromDatabase() throws BLLException;
    
    public List getAllCategoriesFromDatabase() throws BLLException;
    
    public void setCategory(Movie movie, Category category);
    
    public void addPersonalRating(float personalRating, Movie movie);
    
    public void removePersonalRating(Movie movie);
    
    public void saveMoviesInDatabase() throws BLLException;
    
    public List getMovies();
    
    public List getCategories();
    
    public int getHighestIDofMovies();
}
