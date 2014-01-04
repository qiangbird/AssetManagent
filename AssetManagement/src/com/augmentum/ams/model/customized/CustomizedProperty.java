package com.augmentum.ams.model.customized;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.base.BaseModel;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 9:38:53 AM
 */
@Entity
@Table(name = "customized_property")
public class CustomizedProperty extends BaseModel {

	private static final long serialVersionUID = 8000116725378774798L;

	/**
	 * The value of self defined property
	 */
	@Column(length = 256)
	private String value;

	@ManyToOne
	@JoinColumn(name = "asset_id", nullable = false)
	private Asset asset;

	@ManyToOne
	@JoinColumn(name = "property_template_id", nullable = false)
	private PropertyTemplate propertyTemplate;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public PropertyTemplate getPropertyTemplate() {
		return propertyTemplate;
	}

	public void setPropertyTemplate(PropertyTemplate propertyTemplate) {
		this.propertyTemplate = propertyTemplate;
	}

}
