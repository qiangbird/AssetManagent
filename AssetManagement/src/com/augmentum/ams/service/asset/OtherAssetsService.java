package com.augmentum.ams.service.asset;

import com.augmentum.ams.model.asset.OtherAssets;

public interface OtherAssetsService {

	/**
	 * 
	 * @description TODO
	 * @author Jay.He
	 * @time Nov 7, 2013 5:42:59 PM
	 * @param otherAssets
	 */
	void saveOtherAssets(OtherAssets otherAssets);
	/**
	 * 
	 * @description TODO
	 * @author Jay.He
	 * @time Nov 28, 2013 10:28:04 AM
	 * @param otherAssets
	 */
	void updateOtherAssets(OtherAssets otherAssets);
	/**
	 * 
	 * @description TODO
	 * @author Jay.He
	 * @time Nov 28, 2013 10:28:34 AM
	 * @param id
	 * @return
	 */
	OtherAssets getOtherAssetsById(String id);
}
