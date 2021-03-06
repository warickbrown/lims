/*
 * Copyright Curtin University, 2015.
 */
// DO NOT MODIFY THIS FILE BY HAND. IT WAS GENERATED BY generate_orm_files.py
package au.edu.curtin.lims.db.hibernate4;

import javax.inject.Inject;

import au.edu.curtin.lims.db.IAnalysisCapabilityRepository;
import au.edu.curtin.lims.domain.AnalysisCapability;

import org.hibernate.SessionFactory;

import org.springframework.stereotype.Repository;

@Repository
public class HibernateAnalysisCapabilityRepository extends BaseHibernateRepository<AnalysisCapability> implements IAnalysisCapabilityRepository {
    @Inject
    public HibernateAnalysisCapabilityRepository(SessionFactory sessionFactory) {
        super(AnalysisCapability.class, sessionFactory);
    }

    public AnalysisCapability save(AnalysisCapability analysisCapability) {
        this.currentSession().save(analysisCapability);
        return analysisCapability;
//        return new AnalysisCapability(
//            (Integer) id,
//            analysisCapability.getInstrumentId(),
//            analysisCapability.getAnalysisTypeId(),
//            analysisCapability.getResultProcessorTypeId(),
//            analysisCapability.getResultProvisionTypeId(),
//            analysisCapability.getResultTypeId(),
//            analysisCapability.getResultProcessorName(),
//            analysisCapability.getVersion(),
//            analysisCapability.getResultProcessorValue()
//);
    }
}
