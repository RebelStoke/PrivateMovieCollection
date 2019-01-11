package privatemoviecollection.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import privatemoviecollection.be.Category;
import privatemoviecollection.be.Movie;

public class MovieDAO {

    List<Movie> listMovies;
    private final ConnectionProvider cp;
    private final CategoryDAO cdao;
    public MovieDAO() throws IOException {
        listMovies = new ArrayList<>();
        cp = new ConnectionProvider();
        cdao = new CategoryDAO();
    }

    public Movie addMovie(Movie movie) throws SQLException {
        System.out.println(movie);
        listMovies.add(movie);
        return movie;
    }

    public void removeMovie(Movie movie) throws SQLException {
        listMovies.remove(movie);
    }

    public int getHighestIDofMovies() {
        
        int ArraySize = listMovies.size();
        if(ArraySize==0) return 1;
        
        return listMovies.get(ArraySize-1).getId()+1;
    }

    public List getAllMoviesFromDatabase() throws SQLException {
        List<Category> listCategories = new ArrayList<>();
        try (Connection con = cp.getConnection()) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Movies");
            while (rs.next()) {
                String name = rs.getString("name");
                float rating = rs.getFloat("rating");
                float personalrating = rs.getFloat("personalrating");
                String filelink = rs.getString("filelink");
                int id = rs.getInt("id");
                listCategories = cdao.getCategoryByID(id);
                Movie movie = new Movie(name, rating, personalrating, filelink,id);
                for (Category listCategory : listCategories) {
                    movie.addCategory(listCategory);
                }
                listMovies.add(movie);
            }
        }
       return listMovies;
    }

    public void saveMoviesInDatabase() throws SQLException {
        int ArraySize = listMovies.size();
        try (Connection con = cp.getConnection()) {
            Statement statement = con.createStatement();
            String delCatMovies = "DELETE FROM CatMovies";
            String delMovies = "DELETE FROM Movies";
            statement.execute(delCatMovies);
            statement.execute(delMovies);
            for (int i = 0; i < ArraySize; i++) {
                Movie actualMovie = listMovies.get(i); 
                System.out.println(actualMovie.getId());
                String sql = "INSERT INTO Movies VALUES(?,?,?,?,?)";
                PreparedStatement ppst = con.prepareStatement(sql);
                ppst.setString(1, actualMovie.getName());
                ppst.setFloat(2, actualMovie.getRating());
                ppst.setFloat(3, actualMovie.getPersonalrating());
                ppst.setString(4, actualMovie.getFilelink());
                ppst.setInt(5, actualMovie.getLastview());
                ppst.execute();
                
                int categoriesCount = actualMovie.getCategories().size();
                for(int j=0; j<categoriesCount; j++){
                ObservableList ob = actualMovie.getCategories();
                ArrayList<Category> foo = new ArrayList<Category>(ob);
                int catID = foo.get(j).getId();
                String sql2 = "INSERT INTO CatMovies VALUES(?,?)";
                PreparedStatement ppst2 = con.prepareStatement(sql2);
                ppst2.setInt(1, catID);
                ppst2.setInt(2, actualMovie.getId()+1);
                ppst2.execute();
                }
            }
        }

    }
    
}