package com.augmentum.ams.service.asset.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.asset.MachineDao;
import com.augmentum.ams.model.asset.Machine;
import com.augmentum.ams.service.asset.MachineService;

@Service("machineService")
public class MachineServiceImpl implements MachineService {

    @Autowired
    private MachineDao machineDao;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.augmentum.ams.service.asset.MachineService#findMachines(java.lang
     * .String, java.lang.String)
     */
    @SuppressWarnings("unused")
    @Override
    public List<Machine> findMachines(String conditions, String keyword) {
        Session session = machineDao.getHibernateTemplate().getSessionFactory().openSession();
        FullTextSession fts = Search.getFullTextSession(session);

        QueryBuilder qb = fts.getSearchFactory().buildQueryBuilder().forEntity(Machine.class).get();
        org.apache.lucene.search.Query lq = qb.keyword().onFields(conditions).matching(keyword)
                .createQuery();
        org.hibernate.Query hq = fts.createFullTextQuery(lq, Machine.class);

        @SuppressWarnings("unchecked")
        List<Machine> machines = hq.list();
        return machines;
    }

    @Override
    public void saveMachine(Machine machine) {
        machineDao.save(machine);
    }

    @Override
    public Machine getMachineById(String id) {
        return machineDao.get(Machine.class, id);
    }

    @Override
    public void updateMachine(Machine machine) {
        machineDao.update(machine);
    }

    @Override
    public Machine getByAssetId(String assetId) {
        
        return machineDao.getByAssetId(assetId);
    }

}
