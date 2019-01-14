package privatemoviecollection.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.sun.javafx.scene.control.skin.VirtualFlow;
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
import privatemoviecollection.be.Category;
import privatemoviecollection.be.Movie;



public class CategoryDAO
{  
    List<Category> listCategories;
    private final ConnectionProvider cp;
    private static CategoryDAO instance;
    
    public CategoryDAO() throws IOException
    {
        cp = new ConnectionProvider();
        listCategories = new ArrayList<>();
    }
    
    public static CategoryDAO getInstance() throws IOException 
    {
        if (instance == null)
        {
            
                instance = new CategoryDAO();
            
        }
        return instance;
    }
      
       public Category addCategory(Category category) throws SQLException
    {
        listCategories.add(category);
        return category;
    }
    
    public void removeCategory(Category category) throws SQLException
    {
        listCategories.remove(category);
    }

    public void getAllCategoriesFromDatabase() throws SQLException
    {
      
        try (Connection con = cp.getConnection())
        {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Categories;");
            while (rs.next())
            {
                String name = rs.getString("name");
                int id = rs.getInt("id");
                Category category = new Category(id,name);
                listCategories.add(category);
            }
        }
        System.out.println(listCategories.size());
    }
    
    public List<Category> getCategories(){
        return listCategories;
    }
    
    public void saveCategoriesInDatabase() throws SQLException {
        int ArraySize = listCategories.size();
        try (Connection con = cp.getConnection()) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("DELETE FROM Categories");
            for (int i = 0; i < ArraySize; i++) {
                Category actualCategory = listCategories.get(i);
                String sql = "INSERT INTO Movies VALUES(?,?)";
                PreparedStatement ppst = con.prepareStatement(sql);
                ppst.setInt(1, actualCategory.getId());
                ppst.setString(2, actualCategory.getName());
                ppst.execute();
            }
        }

    }
    
    
    public List getCategoryByID(int id) throws SQLServerException, SQLException{
        List<Category> categories = new ArrayList<>();
        try (Connection con = cp.getConnection()) {
            Statement statement = con.createStatement();
            String sql = "SELECT Movies.name,rating,personalrating,filelink,lastview,CatMovies.id AS CatMovieID, CatMovies.CategoryID, CatMovies.MovieID,Categories.name AS CategoryName FROM Movies INNER JOIN CatMovies ON Movies.id=CatMovies.MovieID INNER JOIN Categories ON CategoryID=Categories.id WHERE Movies.id=?";       
            PreparedStatement ppst = con.prepareStatement(sql);
            ppst.setInt(1, id);
            ResultSet rs = ppst.executeQuery();
            
            while (rs.next())
            {
                System.out.println("1");
               String categoryName = rs.getString("CategoryName");
                System.out.println(categoryName);
                for(int i = 0; i<listCategories.size(); i++){
                    if(categoryName.equals(listCategories.get(i).getName()))
                       categories.add(listCategories.get(i));
                }
                }
            }
        
        return categories;
    }
    
    
    
    
    
    
    }

