package privatemoviecollection.bll;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import privatemoviecollection.be.CatMovie;
import privatemoviecollection.be.Category;
import privatemoviecollection.be.Movie;
import privatemoviecollection.dal.CategoryDAO;
import privatemoviecollection.dal.MovieDAO;

public final class PMCManager implements PMCLogicFacade {

    private final MovieDAO mdao;
    private final CategoryDAO cdao;
    private List<Movie> movies;
    private List<Category> categories;
    private List<CatMovie> categoriesOfMovie;

    public PMCManager() throws IOException, SQLException {
        mdao = new MovieDAO();
        cdao = new CategoryDAO();
        setMovies(mdao.getAllMoviesFromDatabase());
        setCategories(cdao.getAllCategoriesFromDatabase());
        setCategoriesOfMovie(mdao.getAllMovieCategoriesFromDatabse());
    }

    @Override
    public void addMovie(String name, float rating, String path, float personalPath, int id) {
        Movie movie = new Movie(name, rating, personalPath, path,id);
        try {
            mdao.addMovie(movie);
        } catch (SQLException ex) {
            Logger.getLogger(PMCManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void removeMovie(Movie movie) {
        try {
            mdao.removeMovie(movie);
        } catch (SQLException ex) {
            Logger.getLogger(PMCManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List getAllMoviesFromDatabase() {
        try {
            mdao.getAllMoviesFromDatabase();
        } catch (SQLException ex) {
            Logger.getLogger(PMCManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void addCategory(Category category) {
        categories.add(category);
    }

    @Override
    public void removeCategory(Category category) {
        categories.remove(category);
    }

    @Override
    public List getAllCategoriesFromDatabase() {
        try {
            cdao.getAllCategoriesFromDatabase();
        } catch (SQLException ex) {
            Logger.getLogger(PMCManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void setCategory(Movie movie, Category category) {
        movie.addCategory(category);
    }

    @Override
    public Movie searchMovie(String quote) {
        Movie movie = null;
        for (Movie movy : movies) {
            if (movy.getName().contains(quote)) {
                movie = movy;
            } else if (movy.hasCategory(quote)) {
                movie = movy;
            }
        }
        return movie;
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public void saveMoviesInDatabase() {
        try {
            mdao.saveMoviesInDatabase();
        } catch (SQLException ex) {
            Logger.getLogger(PMCManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public int getHighestIDofMovies(){
    return mdao.getHighestIDofMovies();
    }

    public void setCategoriesOfMovie(List<CatMovie> categoriesOfMovie) {
        this.categoriesOfMovie = categoriesOfMovie;
    }

}
