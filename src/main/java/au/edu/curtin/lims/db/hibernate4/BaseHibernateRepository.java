/*
 * Copyright Curtin University, 2015.
 */
package au.edu.curtin.lims.db.hibernate4;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import au.edu.curtin.lims.db.IRepository;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class BaseHibernateRepository<T> implements IRepository<T> {
    protected SessionFactory sessionFactory;
    
    private Class<? extends T> classType;
 
    public BaseHibernateRepository(Class<T> classType, SessionFactory sessionFactory) {
        this.classType = classType;
        this.sessionFactory = sessionFactory;
    }
    
    private Criteria createCriteria(Criterion... criterionArray) {
        Criteria criteria = this.criteria();
        
        for (Criterion criterion : criterionArray) {
            criteria.add(criterion);            
        }
        
        return criteria; 
    }
    
    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }
    
    private Criteria criteria() {
        return this.currentSession()
                .createCriteria(this.classType);
    }
    
    public long count() {
        return findAll().size(); 
    }

    public List<T> findRecent(int count) {
        return castList(
                this.classType,
                criteria()
                    .setMaxResults(count)
                    .list());
    }

    public T findById(Integer id) {
        return this.classType.cast(this.currentSession().get(this.classType, id));
    }
    
    public void delete(Integer id) {
        this.currentSession().delete(findById(id));
    }
    
    public List<T> findAll() {
        return castList(this.classType, criteria().list()); 
    }

    public List<T> findByCriteria(Criterion... criterionArray) {
        return castList(this.classType, this.createCriteria(criterionArray).list());
    }
    
    public T findUniqueByCriteria(Criterion... criterionArray) throws Exception {
        List<T> list = castList(this.classType, this.createCriteria(criterionArray).setMaxResults(2).list());
        
        switch (list.size()) {
        case 0:
            return null;
        case 1:
            return list.get(0); 
        default:
            throw new Exception("Query did not return unique value.");
        }
    }
    
    protected static <T> List<T> castList(Class<? extends T> classType, Collection<?> c) {
        List<T> r = new ArrayList<T>(c.size());
        for (Object o: c) {
            r.add(classType.cast(o));
        }
        
        return r;
    }
}

