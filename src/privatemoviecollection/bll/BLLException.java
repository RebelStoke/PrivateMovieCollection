/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.bll;

/**
 *
 * @author leopo
 */
public class BLLException extends Exception
{
    public BLLException(String message)
    {
        super(message);
    }
    
    public BLLException(Exception ex)
    {
        super(ex.getMessage());
    }
    
    @Override
    public String getMessage()
    {
        return super.getMessage();
    }
}
