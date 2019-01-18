package privatemoviecollection.bll;

import java.io.IOException;
import java.util.List;
import privatemoviecollection.be.Category;
import privatemoviecollection.be.Movie;
import privatemoviecollection.dal.CategoryDAO;
import privatemoviecollection.dal.DALException;
import privatemoviecollection.dal.MovieDAO;

public final class PMCManager implements PMCLogicFacade {

    private final MovieDAO mdao;
    private CategoryDAO cdao;
    private List<Movie> movies;
    private List<Category> categories;

    public PMCManager() throws IOException, DALException {
        mdao = new MovieDAO();
        cdao = cdao.getInstance();
        cdao.getAllCategoriesFromDatabase();
        setCategories(cdao.getCategories());
        mdao.getAllMoviesFromDatabase();
        setMovies(mdao.getMovies());
        

    }

    @Override
    public Movie addMovie(String name, float rating, String path, float personalPath, int id) {
        Movie movie = new Movie(name, rating, personalPath, path,id);
        return mdao.addMovie(movie);
    }

    @Override
    public void removeMovie(Movie movie) {
        mdao.removeMovie(movie);
    }

    @Override
    public List<Movie> getAllMoviesFromDatabase() throws BLLException {
        try
        {
            mdao.getAllMoviesFromDatabase();
        } catch (DALException ex)
        {
            throw new BLLException(ex);
        }
        return null;
    }

    @Override
    public List<Category> getAllCategoriesFromDatabase() throws BLLException {
        try
        {
            cdao.getAllCategoriesFromDatabase();
        } catch (DALException ex)
        {
            throw new BLLException(ex);
        }
        return null;
    }

    @Override
    public void setCategory(Movie movie, Category category) {
        movie.addCategory(category);
    }

    @Override
    public void addPersonalRating(float personalRating, Movie movie) {
        movie.setPersonalrating(personalRating);
    }

    @Override
    public void removePersonalRating(Movie movie) {
        movie.setPersonalrating(0);
    }

    @Override
    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
    @Override
    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public void saveMoviesInDatabase() throws BLLException {
        try
        {
            mdao.saveMoviesInDatabase();
        } catch (DALException ex)
        {
            throw new BLLException(ex);
        }
    }
    @Override
    public int getHighestIDofMovies(){
    return mdao.getHighestIDofMovies();
    }

}
