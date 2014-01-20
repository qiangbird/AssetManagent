package com.augmentum.ams.dao.asset.impl;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.criteria.JoinType;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.asset.AssetDao;
import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.web.vo.asset.ExportVo;

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

    @Override
    public List<ExportVo> findAssetsByIdsForExport(final String[] assetIds) {
        
        final StringBuffer sql = new StringBuffer();
        sql.append("SELECT asset.id as assetId, asset.asset_name as assetName, asset.manufacturer as manufacturer, asset.type as type, asset.bar_code as barCode, asset.keeper as keeper, " + 
                " asset.series_no as seriesNo, asset.po_no as poNo, asset.owner_ship as ownerShip, asset.fixed as fixed, asset.status as status, asset.check_in_time as checkInTime, asset.entity as entity, " +  
                " asset.check_out_time as checkOutTime, asset.warranty_time as warrantyTime, asset.vendor as vendor, asset.memo as memo, machine.sub_type as machineSubtype,  " + 
                " machine.specification as machineSpecification, machine.address as machineAddress, machine.configuration as machineConfiguration, monitor.size as monitorSize,  " + 
                " monitor.detail as monitorDetail, device.configuration as deviceConfiguration, deviceSubtype.subtype_name as deviceSubtypeName, software.version as softwareVersion,  " + 
                " software.license_key as softwareLicenseKey, software.software_expired_time as softwareExpiredTime, software.max_use_num as softwareMaxUseNum,  " + 
                " software.additional_info as softwareAdditionalInfo, otherAsset.detail as otherAssetDetail, user.user_name as userName, project.project_name as projectName, " + 
                " location.site as locationSite, location.room as locationRoom, customer.customer_name as customerName  " + 
                " FROM " + 
                " asset asset left join project project on (asset.project_id=project.id) " + 
                " left join location location on (asset.location_id=location.id) " + 
                " left join user user on (asset.user_id=user.id) " + 
                " left join customer customer on (asset.customer_id= customer.id) " + 
                " left join machine machine on (asset.id=machine.asset_id) " + 
                " left join monitor monitor on (asset.id=monitor.asset_id)  " + 
                " left join device device on (asset.id=device.asset_id) " + 
                " left join software software on (asset.software_id=software.id) " + 
                " left join other_assets otherAsset on (asset.id=otherAsset.asset_id) " + 
                " left join device_subtype deviceSubtype on device.device_subtype_id = deviceSubtype.id " + 
                " where " +
                " asset.id in (");
        
        for(int i = 0; i < assetIds.length; i++){
            if(i == assetIds.length -1){
                sql.append("'");
                sql.append(assetIds[i]);
                sql.append("'");
            }else{
                sql.append("'");
                sql.append(assetIds[i]);
                sql.append("',");
            }
        }
        sql.append(") group by asset.id");
        
        @SuppressWarnings({"rawtypes", "unchecked" })
        List<ExportVo> list = (List<ExportVo>) getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                List<ExportVo> list = (List<ExportVo>) session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(ExportVo.class)).list();
                       
                return list;
            }
        });
        
        return list;
    }
}
