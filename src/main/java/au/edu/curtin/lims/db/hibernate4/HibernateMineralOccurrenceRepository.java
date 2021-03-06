/*
 * Copyright Curtin University, 2015.
 */
// DO NOT MODIFY THIS FILE BY HAND. IT WAS GENERATED BY generate_orm_files.py
package au.edu.curtin.lims.db.hibernate4;

import javax.inject.Inject;

import au.edu.curtin.lims.db.IMineralOccurrenceRepository;
import au.edu.curtin.lims.domain.MineralOccurrence;

import org.hibernate.SessionFactory;

import org.springframework.stereotype.Repository;

@Repository
public class HibernateMineralOccurrenceRepository extends BaseHibernateRepository<MineralOccurrence> implements IMineralOccurrenceRepository {
    @Inject
    public HibernateMineralOccurrenceRepository(SessionFactory sessionFactory) {
        super(MineralOccurrence.class, sessionFactory);
    }

    public MineralOccurrence save(MineralOccurrence mineralOccurrence) {
        this.currentSession().save(mineralOccurrence);
        return mineralOccurrence;
//        return new MineralOccurrence(
//            (Integer) id,
//            mineralOccurrence.getMindifFieldId(),
//            mineralOccurrence.getMineralId(),
//            mineralOccurrence.getMineralPixelCount()
//);
    }
}
