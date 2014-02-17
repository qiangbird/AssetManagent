package com.augmentum.ams.dao.asset;

import java.util.List;
import java.util.Map;

import com.augmentum.ams.dao.base.BaseDao;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.web.vo.asset.ExportVo;

public interface AssetDao extends BaseDao<Asset> {
	/**
	 * 
	 * @description Find all assets
	 * @author Jay.He
	 * @time Nov 8, 2013 3:40:53 PM
	 * @return
	 */
	public List<Asset> findAllAssets();

	/**
	 * 
	 * @description Get the number of asset
	 * @author Jay.He
	 * @time Dec 9, 2013 11:09:44 AM
	 * @return
	 */
	public int getAllAssetCount();

	/**
	 * 
	 * @description Get asset by given id
	 * @author Jay.He
	 * @time Nov 25, 2013 12:16:42 PM
	 * @param id
	 * @return
	 */
	public Asset getAssetById(String id);

	/**
	 * Find the assets by barCode
	 * 
	 * @param auditBarCode
	 * @return
	 */
	public List<Asset> findAssetByBarCode(String auditBarCode);

	/**
	 * Find the assets by ids for export
	 * 
	 * @param assetIdArr
	 * @return
	 */
	public List<ExportVo> findAssetsByIdsForExport(String[] assetIds);

	/**
	 * @author John.Li
	 * @param contents
	 */
	public Asset getByAssetId(String contents);

	/**
	 * @author Geoffrey.Zhao
	 * @return
	 */
	public Map<String, Integer> getAssetCountForIT(User user);

	/**
	 * @author Geoffrey.Zhao
	 * @return
	 */
	public Map<String, Integer> getAssetCountForManager(User user,
			List<Customer> customers);

	/**
	 * @author Geoffrey.Zhao
	 * @return
	 */
	public List<Asset> findIdleAssetForPanel(List<Customer> customers);
	
	/**
	 * @author Geoffrey.Zhao
	 * @return
	 */
	List<Asset> findWarrantyExpiredAssetForPanel();
	
}
