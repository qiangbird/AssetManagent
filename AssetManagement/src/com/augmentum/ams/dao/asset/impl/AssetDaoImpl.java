package com.augmentum.ams.dao.asset.impl;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.augmentum.ams.dao.asset.AssetDao;
import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.model.asset.Asset;

@Repository("assetDao")
public class AssetDaoImpl extends BaseDaoImpl<Asset> implements AssetDao {

    @Override
    public int getAllAssetCount() {
        return super.getTotalCount(Asset.class);
    }

    @Override
    public Asset getAssetById(String id) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Asset.class);
        detachedCriteria.createAlias("user", "user", JoinType.LEFT.ordinal());
        detachedCriteria.createAlias("project", "project", JoinType.LEFT.ordinal());
        detachedCriteria.createAlias("location", "location", JoinType.LEFT.ordinal());
        detachedCriteria.createAlias("customer", "customer", JoinType.LEFT.ordinal());

        detachedCriteria.add(Restrictions.eq("id", id));
        detachedCriteria.add(Restrictions.eq("isExpired", false));
        List<Asset> allAssets = super.findByCriteria(detachedCriteria);
        return allAssets.size() == 0 ? null : allAssets.get(0);
    }

    public void deleteAssetById(String id) {
        String hql = "from Asset where id = ?";
        Asset asset = super.getUnique(hql, id);
        super.delete(asset);
    }

    @Override
    public List<Asset> findAllAssets() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Asset.class);

        // foreign key can be null, so use left join way
        detachedCriteria.createAlias("user", "user", JoinType.LEFT.ordinal())
                .createAlias("project", "project", JoinType.LEFT.ordinal())
                .createAlias("location", "location", JoinType.LEFT.ordinal())
                .createAlias("customer", "customer", JoinType.LEFT.ordinal());

        detachedCriteria.add(Restrictions.eq("isExpired", Boolean.FALSE));
        List<Asset> allAssets = super.findByCriteria(detachedCriteria);
        return allAssets;
    }

    @Override
    public List<Asset> findAssetByBarCode(String auditBarCode) {
        
        String hql = "from Asset a where a.isExpired = ? and a.barCode = ?";
        
        return find(hql, Boolean.FALSE, auditBarCode);
    }
}
