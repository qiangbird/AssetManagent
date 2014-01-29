package com.augmentum.ams.dao.asset.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.JoinType;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.asset.AssetDao;
import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.util.Constant;
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
        sql.append("SELECT asset.asset_id as assetId, asset.asset_name as assetName, asset.manufacturer as manufacturer, asset.type as type, asset.bar_code as barCode, asset.keeper as keeper, " + 
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

    @Override
    public Asset getByAssetId(String assetId) {
        
        String hql = "from Asset where assetId = ?";
        
        return super.getUnique(hql, assetId);
    }
    
    // TODO move to util class
    /**
     * get map<AssetTypeName, AssetCount> based on the result of execute hql (List)
     * @author Geoffrey.Zhao
     * @param list
     * @return
     */
    private Map<String, Integer> getAssetTypeCountList(List list) {
    	Map<String, Integer> result = new HashMap<String, Integer>();
    	
    	if (0 == list.size()) {
			for (int i = 0; i < Constant.ASSET_TYPE_ARRAY.length; i++) {
				result.put(Constant.ASSET_TYPE_ARRAY[i], 0);
			}
		} else {
			
			for (int i = 0; i < Constant.ASSET_TYPE_ARRAY.length; i++) {
				
				loop: for (int j = 0; j < list.size(); j++) {
					
					Object[] obj = (Object[])list.get(j);
					if (Constant.ASSET_TYPE_ARRAY[i].equals(obj[0])) {
						result.put(Constant.ASSET_TYPE_ARRAY[i], Integer.valueOf((obj[1]).toString()));
						break loop;
					}
					if (j == list.size() - 1) {
						result.put(Constant.ASSET_TYPE_ARRAY[i] ,0);
					}
				}
			}
		}
    	return result;
    }

	@Override
	public Map<String, Integer> getAssetCountForIT(User user) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		String hql = "";
		Map<String, Integer> result = new HashMap<String, Integer>();
		
		// all assets
		hql = "SELECT COUNT(*) FROM Asset WHERE isExpired = false";
		map.put("allAssetTotal", getCountByHql(hql));
		
		hql = "SELECT type, COUNT(*) FROM Asset WHERE isExpired = false GROUP BY type ORDER BY type";
		result = getAssetTypeCountList(getHibernateTemplate().find(hql));
		
		map.put("allAssetDeviceCount", result.get("DEVICE"));
		map.put("allAssetMachineCount", result.get("MACHINE"));
		map.put("allAssetMonitorCount", result.get("MONITOR"));
		map.put("allAssetOtherAssetsCount", result.get("OTHERASSETS"));
		map.put("allAssetSoftwareCount", result.get("SOFTWARE"));
		
		// my assets
		hql = "SELECT COUNT(*) FROM Asset WHERE isExpired = false AND user.userId = ?";
		map.put("myAssetTotal", getCountByHql(hql, user.getUserId()));
		
		hql = "SELECT type, COUNT(*) FROM Asset WHERE isExpired = false AND user.userId = ? GROUP BY type ORDER BY type";
		result = getAssetTypeCountList(getHibernateTemplate().find(hql, user.getUserId()));
		
		map.put("myAssetDeviceCount", result.get("DEVICE"));
		map.put("myAssetMachineCount", result.get("MACHINE"));
		map.put("myAssetMonitorCount", result.get("MONITOR"));
		map.put("myAssetOtherAssetsCount", result.get("OTHERASSETS"));
		map.put("myAssetSoftwareCount", result.get("SOFTWARE"));
		
		// fixed assets
		hql = "SELECT COUNT(*) FROM Asset WHERE isExpired = false AND fixed = ?";
		map.put("fixedAssetTotal", getCountByHql(hql, Boolean.TRUE));
		
		hql = "SELECT type, COUNT(*) FROM Asset WHERE isExpired = false AND fixed = true GROUP BY type ORDER BY type";
		result = getAssetTypeCountList(getHibernateTemplate().find(hql));
		
		map.put("fixedAssetDeviceCount", result.get("DEVICE"));
		map.put("fixedAssetMachineCount", result.get("MACHINE"));
		map.put("fixedAssetMonitorCount", result.get("MONITOR"));
		map.put("fixedAssetOtherAssetsCount", result.get("OTHERASSETS"));
		map.put("fixedAssetSoftwareCount", result.get("SOFTWARE"));
		
		// IT: available assets
		hql = "SELECT COUNT(*) FROM Asset WHERE isExpired = false AND status = ?";
		map.put("allAvailableAssetTotal", getCountByHql(hql, "AVAILABLE"));
		
		hql = "SELECT type, COUNT(*) FROM Asset WHERE isExpired = false AND status = 'AVAILABLE' GROUP BY type ORDER BY type";
		result = getAssetTypeCountList(getHibernateTemplate().find(hql));
		
		map.put("allAvailableDeviceCount", result.get("DEVICE"));
		map.put("allAvailableMachineCount", result.get("MACHINE"));
		map.put("allAvailableMonitorCount", result.get("MONITOR"));
		map.put("allAvailableOtherAssetsCount", result.get("OTHERASSETS"));
		map.put("allAvailableSoftwareCount", result.get("SOFTWARE"));
		
		// IT: idle assets
		hql = "SELECT COUNT(*) FROM Asset WHERE isExpired = false AND status = ?";
		map.put("allIdleAssetTotal", getCountByHql(hql, "IDLE"));
		
		hql = "SELECT type, COUNT(*) FROM Asset WHERE isExpired = false AND status = 'IDLE' GROUP BY type ORDER BY type";
		result = getAssetTypeCountList(getHibernateTemplate().find(hql));
		
		map.put("allIdleSoftwareCount", result.get("SOFTWARE"));
		map.put("allIdleDeviceCount", result.get("DEVICE"));
		map.put("allIdleMachineCount", result.get("MACHINE"));
		map.put("allIdleMonitorCount", result.get("MONITOR"));
		map.put("allIdleOtherAssetsCount", result.get("OTHERASSETS"));
		
		// IT: in use assets
		hql = "SELECT COUNT(*) FROM Asset WHERE isExpired = false AND status = ?";
		map.put("allInUseAssetTotal", getCountByHql(hql, "IN_USE"));
		
		hql = "SELECT type, COUNT(*) FROM Asset WHERE isExpired = false AND status = 'IN_USE' GROUP BY type ORDER BY type";
		result = getAssetTypeCountList(getHibernateTemplate().find(hql));
		
		map.put("allInUseSoftwareCount", result.get("SOFTWARE"));
		map.put("allInUseDeviceCount", result.get("DEVICE"));
		map.put("allInUseMachineCount", result.get("MACHINE"));
		map.put("allInUseMonitorCount", result.get("MONITOR"));
		map.put("allInUseOtherAssetsCount", result.get("OTHERASSETS"));
		
		// IT: returned assets
		hql = "SELECT COUNT(*) FROM Asset WHERE isExpired = false AND status = ?";
		map.put("allReturnedAssetTotal", getCountByHql(hql, "RETURNED"));
		
		hql = "SELECT type, COUNT(*) FROM Asset WHERE isExpired = false AND status = 'RETURNED' GROUP BY type ORDER BY type";
		result = getAssetTypeCountList(getHibernateTemplate().find(hql));
		
		map.put("allReturnedSoftwareCount", result.get("SOFTWARE"));
		map.put("allReturnedDeviceCount", result.get("DEVICE"));
		map.put("allReturnedMachineCount", result.get("MACHINE"));
		map.put("allReturnedMonitorCount", result.get("MONITOR"));
		map.put("allReturnedOtherAssetsCount", result.get("OTHERASSETS"));
		
		// IT: borrowed assets
		hql = "SELECT COUNT(*) FROM Asset WHERE isExpired = false AND status = ?";
		map.put("allBorrowedAssetTotal", getCountByHql(hql, "BORROWED"));
		
		hql = "SELECT type, COUNT(*) FROM Asset WHERE isExpired = false AND status = 'BORROWED' GROUP BY type ORDER BY type";
		result = getAssetTypeCountList(getHibernateTemplate().find(hql));
		
		map.put("allBorrowedSoftwareCount", result.get("SOFTWARE"));
		map.put("allBorrowedDeviceCount", result.get("DEVICE"));
		map.put("allBorrowedMachineCount", result.get("MACHINE"));
		map.put("allBorrowedMonitorCount", result.get("MONITOR"));
		map.put("allBorrowedOtherAssetsCount", result.get("OTHERASSETS"));
		
		// IT: broken assets
		hql = "SELECT COUNT(*) FROM Asset WHERE isExpired = false AND status = ?";
		map.put("allBrokenAssetTotal", getCountByHql(hql, "BROKEN"));
		
		hql = "SELECT type, COUNT(*) FROM Asset WHERE isExpired = false AND status = 'BROKEN' GROUP BY type ORDER BY type";
		result = getAssetTypeCountList(getHibernateTemplate().find(hql));
		
		map.put("allBrokenSoftwareCount", result.get("SOFTWARE"));
		map.put("allBrokenDeviceCount", result.get("DEVICE"));
		map.put("allBrokenMachineCount", result.get("MACHINE"));
		map.put("allBrokenMonitorCount", result.get("MONITOR"));
		map.put("allBrokenOtherAssetsCount", result.get("OTHERASSETS"));
		
		// IT: write_off assets
		hql = "SELECT COUNT(*) FROM Asset WHERE isExpired = false AND status = ?";
		map.put("allWriteOffAssetTotal", getCountByHql(hql, "WRITE_OFF"));
		
		hql = "SELECT type, COUNT(*) FROM Asset WHERE isExpired = false AND status = 'WRITE_OFF' GROUP BY type ORDER BY type";
		result = getAssetTypeCountList(getHibernateTemplate().find(hql));
		
		map.put("allWriteOffSoftwareCount", result.get("SOFTWARE"));
		map.put("allWriteOffDeviceCount", result.get("DEVICE"));
		map.put("allWriteOffMachineCount", result.get("MACHINE"));
		map.put("allWriteOffMonitorCount", result.get("MONITOR"));
		map.put("allWriteOffOtherAssetsCount", result.get("OTHERASSETS"));
		
		return map;
	}

	@Override
	public Map<String, Integer> getAssetCountForManager(User user, List<Customer> customers) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		String hql = "";
		Map<String, Integer> result = new HashMap<String, Integer>();
		String[] customerIds = new String[customers.size()];
		
		for (int i = 0; i < customers.size(); i++) {
			customerIds[i] = customers.get(i).getId();
		}
		
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Query query = null;
		
		// all customer asset
		hql = "SELECT COUNT(*) FROM Asset WHERE isExpired = false AND customer.id in (:customerIds)";
		query = session.createQuery(hql).setParameterList("customerIds", customerIds);
		map.put("allCustomersAssetTotal", Integer.valueOf(query.list().get(0).toString()));
		
		hql = "SELECT type, COUNT(*) FROM Asset WHERE isExpired = false AND customer.id in (:customerIds) GROUP By type ORDER BY type";
		query = session.createQuery(hql).setParameterList("customerIds", customerIds);
		result = getAssetTypeCountList(query.list());
		
		map.put("allCustomersSoftwareCount", result.get("SOFTWARE"));
		map.put("allCustomersDeviceCount", result.get("DEVICE"));
		map.put("allCustomersMachineCount", result.get("MACHINE"));
		map.put("allCustomersMonitorCount", result.get("MONITOR"));
		map.put("allCustomersOtherAssetsCount", result.get("OTHERASSETS"));
		
		// each customer asset
		for (Customer customer : customers) {
			
			hql = "SELECT COUNT(*) FROM Asset WHERE isExpired = false AND customer.id = ?";
			map.put(customer.getCustomerName() + "AssetTotal", getCountByHql(hql, customer.getId()));
			
			hql = "SELECT type, COUNT(*) FROM Asset WHERE isExpired = false AND customer.id = ? GROUP By type ORDER BY type";
			result = getAssetTypeCountList(getHibernateTemplate().find(hql, customer.getId()));
			
			map.put(customer.getCustomerName() + "SoftwareCount", result.get("SOFTWARE"));
			map.put(customer.getCustomerName() + "DeviceCount", result.get("DEVICE"));
			map.put(customer.getCustomerName() + "MachineCount", result.get("MACHINE"));
			map.put(customer.getCustomerName() + "MonitorCount", result.get("MONITOR"));
			map.put(customer.getCustomerName() + "OtherAssetsCount", result.get("OTHERASSETS"));
		}
		
		// my assets
		hql = "SELECT COUNT(*) FROM Asset WHERE isExpired = false AND user.userId = ?";
		map.put("myAssetTotal", getCountByHql(hql, user.getUserId()));
		
		hql = "SELECT type, COUNT(*) FROM Asset WHERE isExpired = false AND user.userId = ? GROUP BY type ORDER BY type";
		result = getAssetTypeCountList(getHibernateTemplate().find(hql, user.getUserId()));
		
		map.put("myAssetDeviceCount", result.get("DEVICE"));
		map.put("myAssetMachineCount", result.get("MACHINE"));
		map.put("myAssetMonitorCount", result.get("MONITOR"));
		map.put("myAssetOtherAssetsCount", result.get("OTHERASSETS"));
		map.put("myAssetSoftwareCount", result.get("SOFTWARE"));
		
		// Manager: all customers available assets
		hql = "SELECT COUNT(*) FROM Asset WHERE isExpired = false AND status = 'AVAILABLE' AND customer.id in (:customerIds)";
		query = session.createQuery(hql).setParameterList("customerIds", customerIds);
		map.put("allCustomersAvailableAssetTotal", Integer.valueOf(query.list().get(0).toString()));
		
		hql = "SELECT type, COUNT(*) FROM Asset WHERE isExpired = false AND status = 'AVAILABLE' AND customer.id in (:customerIds) GROUP BY type ORDER BY type";
		query = session.createQuery(hql).setParameterList("customerIds", customerIds);
		result = getAssetTypeCountList(query.list());
		
		map.put("allCustomersAvailableDeviceCount", result.get("DEVICE"));
		map.put("allCustomersAvailableMachineCount", result.get("MACHINE"));
		map.put("allCustomersAvailableMonitorCount", result.get("MONITOR"));
		map.put("allCustomersAvailableOtherAssetsCount", result.get("OTHERASSETS"));
		map.put("allCustomersAvailableSoftwareCount", result.get("SOFTWARE"));
		
		// Manager: all customers idle assets
		hql = "SELECT COUNT(*) FROM Asset WHERE isExpired = false AND status = 'IDLE' AND customer.id in (:customerIds)";
		query = session.createQuery(hql).setParameterList("customerIds", customerIds);
		map.put("allCustomersIdleAssetTotal", Integer.valueOf(query.list().get(0).toString()));
		
		hql = "SELECT type, COUNT(*) FROM Asset WHERE isExpired = false AND status = 'IDLE' AND customer.id in (:customerIds) GROUP BY type ORDER BY type";
		query = session.createQuery(hql).setParameterList("customerIds", customerIds);
		result = getAssetTypeCountList(query.list());
		
		map.put("allCustomersIdleSoftwareCount", result.get("SOFTWARE"));
		map.put("allCustomersIdleDeviceCount", result.get("DEVICE"));
		map.put("allCustomersIdleMachineCount", result.get("MACHINE"));
		map.put("allCustomersIdleMonitorCount", result.get("MONITOR"));
		map.put("allCustomersIdleOtherAssetsCount", result.get("OTHERASSETS"));
		
		// Manager: in use assets
		hql = "SELECT COUNT(*) FROM Asset WHERE isExpired = false AND status = 'IN_USE' AND customer.id in (:customerIds)";
		query = session.createQuery(hql).setParameterList("customerIds", customerIds);
		map.put("allCustomersInUseAssetTotal", Integer.valueOf(query.list().get(0).toString()));
		
		hql = "SELECT type, COUNT(*) FROM Asset WHERE isExpired = false AND status = 'IN_USE' AND customer.id in (:customerIds) GROUP BY type ORDER BY type";
		query = session.createQuery(hql).setParameterList("customerIds", customerIds);
		result = getAssetTypeCountList(query.list());
		
		map.put("allCustomersInUseSoftwareCount", result.get("SOFTWARE"));
		map.put("allCustomersInUseDeviceCount", result.get("DEVICE"));
		map.put("allCustomersInUseMachineCount", result.get("MACHINE"));
		map.put("allCustomersInUseMonitorCount", result.get("MONITOR"));
		map.put("allCustomersInUseOtherAssetsCount", result.get("OTHERASSETS"));
		
		return map;
	}
	
	
}
