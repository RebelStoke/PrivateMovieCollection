package privatemoviecollection.bll;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import privatemoviecollection.be.Category;
import privatemoviecollection.be.Movie;
import privatemoviecollection.dal.CategoryDAO;
import privatemoviecollection.dal.MovieDAO;

public final class PMCManager implements PMCLogicFacade
{
    private final MovieDAO mdao;
    private final CategoryDAO cdao;
    private List<Movie> movies;
    private List<Category> categories;
    
    public PMCManager() throws IOException, SQLException
    {
        mdao = new MovieDAO();
        cdao = new CategoryDAO();
        setMovies(mdao.getAllMovies());
        setCategories(cdao.getAllCategories());
    }
    
    @Override
    public void addMovie(String name, float rating, String path, float personalPath)
    {
        int newId = mdao.getHighestIDofMovies();
        Movie movie = new Movie(name, rating, personalPath, path, newId);
        try {
            mdao.addMovie(movie);
        } catch (SQLException ex) {
            Logger.getLogger(PMCManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void removeMovie(Movie movie)
    {
        try
        {
            mdao.removeMovie(movie);
        } catch (SQLException ex)
        {
            Logger.getLogger(PMCManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addCategory(Category category)
    {
        categories.add(category);
    }

    @Override
    public void removeCategory(Category category)
    {
        categories.remove(category);
    }

    @Override
    public void setCategory(Movie movie, Category category)
    {
        movie.addCategory(category);
    }

    @Override
    public Movie searchMovie(String quote)
    {
        Movie movie = null;
        for (Movie movy : movies)
        {
            if (movy.getName().contains(quote))
            {
                movie = movy;
            } else if (movy.hasCategory(quote))
            {
                movie = movy;
            }
        }
        return movie;
    }

    @Override
    public void addPersonalRating(float personalRating, Movie movie)
    {
        movie.setPersonalrating(personalRating);
    }

    @Override
    public void removePersonalRating(Movie movie)
    {
       movie.setPersonalrating(0);
    }

    public List<Movie> getMovies()
    {
        return movies;
    }

    public void setMovies(List<Movie> movies)
    {
        this.movies = movies;
    }

    public List<Category> getCategories()
    {
        return categories;
    }

    public void setCategories(List<Category> categories)
    {
        this.categories = categories;
    }
    
}
