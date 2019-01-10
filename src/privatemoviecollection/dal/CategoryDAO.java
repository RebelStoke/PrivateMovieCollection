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
import privatemoviecollection.be.Movie;

public class CategoryDAO
{
    List<Category> listCategories;
    private final ConnectionProvider cp;
    
    public CategoryDAO() throws IOException
    {
        cp = new ConnectionProvider();
        listCategories = new ArrayList<>();
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

    public List<Category> getAllCategories() throws SQLException
    {
        List<Category> categories = new ArrayList<>();
        try (Connection con = cp.getConnection())
        {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Categories;");
            while (rs.next())
            {
                String name = rs.getString("name");
                int id = rs.getInt("id");
                Category category = new Category(id,name);
                categories.add(category);
            }
        }
        return categories;
    }
    
    public void saveMovies() throws SQLException {
        int ArraySize = listCategories.size();
        try (Connection con = cp.getConnection()) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("DELETE FROM Movies");
            for (int i = 0; i < ArraySize; i++) {
                Category actualCategory = listCategories.get(i);
                String sql = "INSERT INTO Movies VALUES(?,?,?,?,?)";
                PreparedStatement ppst = con.prepareStatement(sql);
                ppst.setInt(1, actualCategory.getId());
                ppst.setString(2, actualCategory.getName());
                ppst.execute();
            }
        }

    }
    
    
    
    
    
    
    
    
    }

