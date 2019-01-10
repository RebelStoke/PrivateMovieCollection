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
import java.util.logging.Level;
import java.util.logging.Logger;
import privatemoviecollection.be.Movie;

public class MovieDAO {

    List<Movie> listMovies;
    private final ConnectionProvider cp;

    public MovieDAO() throws IOException {
        listMovies = new ArrayList<>();
        cp = new ConnectionProvider();
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
        else
        return listMovies.get(ArraySize).getId()+1;
    }

    public List getAllMovies() throws SQLException {
        
        try (Connection con = cp.getConnection()) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Movies");
            while (rs.next()) {
                String name = rs.getString("name");
                float rating = rs.getFloat("rating");
                float personalrating = rs.getFloat("personalrating");
                String filelink = rs.getString("filelink");
                int lastview = rs.getInt("lastview");
                int id = rs.getInt("id");
                Movie movie = new Movie(name, rating, personalrating, filelink, id);
                listMovies.add(movie);
            }
        }
       return listMovies;
    }

    public void saveMovies() throws SQLException {
        int ArraySize = listMovies.size();
        try (Connection con = cp.getConnection()) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("DELETE FROM Movies");
            for (int i = 0; i < ArraySize; i++) {
                Movie actualMovie = listMovies.get(i);
                String sql = "INSERT INTO Movies VALUES(?,?,?,?,?)";
                PreparedStatement ppst = con.prepareStatement(sql);
                ppst.setString(1, actualMovie.getName());
                ppst.setFloat(2, actualMovie.getRating());
                ppst.setString(3, actualMovie.getFilelink());
                ppst.setInt(4, actualMovie.getLastview());
                ppst.setInt(5, actualMovie.getId());
                ppst.execute();
            }
        }

    }
}