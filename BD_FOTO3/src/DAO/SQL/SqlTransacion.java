package DAO.SQL;

import DAO.DAOException;

/**
 *
 * @author Headcruser87
 */
public interface SqlTransacion 
{
    void begin() throws DAOException;

     void commit() throws  DAOException;
 
     void rollback() throws DAOException;
    
}
