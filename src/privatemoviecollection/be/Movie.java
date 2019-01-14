package privatemoviecollection.be;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Movie
{
    private String name;
    private String categoriesAsString = "";
    private float rating;
    private String filelink;
    private int lastview;
    private float personalrating;
    private int id;
    private ObservableList<Category> categories = FXCollections.observableArrayList();
    
    public Movie(String name, float rating, float personalrating, String filelink,int id)
    {
        this.name = name;
        this.rating = rating;
        this.personalrating = personalrating;
        this.filelink = filelink;
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public float getRating()
    {
        return rating;
    }

    public void setRating(float rating)
    {
        this.rating = rating;
    }

    public String getFilelink()
    {
        return filelink;
    }

    public void setFilelink(String filelink)
    {
        this.filelink = filelink;
    }

    public int getLastview()
    {
        return lastview;
    }

    public void setLastview(int lastview)
    {
        this.lastview = lastview;
    }

    public float getPersonalrating()
    {
        return personalrating;
    }

    public void setPersonalrating(float personalrating)
    {
        this.personalrating = personalrating;
    }
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
    
    public void addCategory(Category category)
    {
        this.categories.add(category);
        this.categoriesAsString += category.getName()+"|";
    }
    public String getCategoriesAsString(){
        return categoriesAsString;
    }
    public void removeCategory(Category category)
    {
        this.categories.remove(category);
    }

    public ObservableList<Category> getCategories()
    {
        return categories;
    }
    
    
    public boolean hasCategory(String quote)
    {
        for (Category category : categories)
        {
            if (category.getName().equals(quote))
            {
                return true;
            }
        }
        return false;
    }
}
