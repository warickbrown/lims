/*
 * Copyright Curtin University, 2015.
 */
// DO NOT MODIFY THIS FILE BY HAND. IT WAS GENERATED BY generate_orm_files.py
package au.edu.curtin.lims.db.hibernate4;

import javax.inject.Inject;

import au.edu.curtin.lims.db.IAnalysisTypeRepository;
import au.edu.curtin.lims.domain.AnalysisType;

import org.hibernate.SessionFactory;

import org.springframework.stereotype.Repository;

@Repository
public class HibernateAnalysisTypeRepository extends BaseHibernateRepository<AnalysisType> implements IAnalysisTypeRepository {
    @Inject
    public HibernateAnalysisTypeRepository(SessionFactory sessionFactory) {
        super(AnalysisType.class, sessionFactory);
    }

    public AnalysisType save(AnalysisType analysisType) {
        this.currentSession().save(analysisType);
        return analysisType;
//        return new AnalysisType(
//            (Integer) id,
//            analysisType.getAnalysisTypeName()
//);
    }
}