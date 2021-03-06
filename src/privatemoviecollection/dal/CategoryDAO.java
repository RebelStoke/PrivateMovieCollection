package privatemoviecollection.dal;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import privatemoviecollection.be.Category;



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
      
       public Category addCategory(Category category)
    {
        listCategories.add(category);
        return category;
    }
    
    public void removeCategory(Category category)
    {
        listCategories.remove(category);
    }

    public void getAllCategoriesFromDatabase() throws DALException
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
        } catch (SQLException ex)
        {
            throw new DALException(ex);
        }
        System.out.println(listCategories.size());
    }
    
    public List<Category> getCategories(){
        return listCategories;
    }
    
    public void saveCategoriesInDatabase() throws DALException {
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
        } catch (SQLException ex)
        {
            throw new DALException(ex);
        }

    }
    
    
    public List getCategoryByID(int id) throws DALException {
        List<Category> categories = new ArrayList<>();
        try (Connection con = cp.getConnection()) {
            Statement statement = con.createStatement();
            String sql = "SELECT Movies.name,rating,personalrating,filelink,lastview,CatMovies.id AS CatMovieID, CatMovies.CategoryID, CatMovies.MovieID,Categories.name AS CategoryName FROM Movies INNER JOIN CatMovies ON Movies.id=CatMovies.MovieID INNER JOIN Categories ON CategoryID=Categories.id WHERE Movies.id=?";       
            PreparedStatement ppst = con.prepareStatement(sql);
            ppst.setInt(1, id);
            ResultSet rs = ppst.executeQuery();
            
            while (rs.next())
            {  
               String categoryName = rs.getString("CategoryName");
                for(int i = 0; i<listCategories.size(); i++){
                    if(categoryName.equals(listCategories.get(i).getName()))
                       categories.add(listCategories.get(i));
                }
                }
            } catch (SQLException ex)
        {
            throw new DALException(ex);
        }
        
        return categories;
    }
    }

