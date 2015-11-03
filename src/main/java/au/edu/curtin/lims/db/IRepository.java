/*
 * Copyright Curtin University, 2015.
 */
package au.edu.curtin.lims.db;

import java.util.List;

import org.hibernate.criterion.Criterion;

public interface IRepository<T> {
    long count();

    List<T> findRecent(int count);

    T findById(Integer id);

    T save(T object);

    List<T> findAll();
   
    List<T> findByCriteria(Criterion... criterionArray);
    
    // TODO: Use specialised exception.
    T findUniqueByCriteria(Criterion... criterionArray) throws Exception;

    void delete(Integer id);
}
