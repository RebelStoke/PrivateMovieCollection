/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.dal;

/**
 *
 * @author leopo
 */
public class DALException extends Exception
{
    public DALException(String message)
    {
        super(message);
    }
    
    public DALException(Exception ex)
    {
        super(ex.getMessage());
    }
    
    @Override
    public String getMessage()
    {
        return super.getMessage();
    }
}
