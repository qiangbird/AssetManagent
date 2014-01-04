package com.augmentum.ams.model.asset;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.augmentum.ams.model.base.BaseModel;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 9:00:23 AM
 */
@Entity
@Table(name = "purchase_item")
public class PurchaseItem extends BaseModel {

	private static final long serialVersionUID = 6400358103058827620L;

	/**
	 * The PO number of a request
	 */
	@Column(name = "po_no", length = 32, nullable = false)
	private String poNo;

	/**
	 * The process type of PMS
	 */
	@Column(name = "process_type", length = 32, nullable = false)
	private String processType;

	/**
	 * The name of customer which bought the items
	 */
	@Column(name = "customer_name", length = 128, nullable = false)
	private String customerName;

	/**
	 * The code of customer which bought the items
	 */
	@Column(name = "customer_code", length = 32, nullable = false)
	private String customerCode;

	/**
	 * The name of project which used the item
	 */
	@Column(name = "project_name", length = 64)
	private String projectName;

	/**
	 * The code of project which used the item
	 */
	@Column(name = "project_code", length = 32)
	private String projectCode;


	@Column(name = "vendor_name", length = 128, nullable = false)
	private String vendorName;


	@Column(name = "item_name", length = 64, nullable = false)
	private String itemName;

	/**
	 * If the name of item is wrong, he/she can fill real name of the item
	 */
	@Column(name = "item_real_name", length = 64)
	private String itemRealName;

	@Column(name = "item_description", length = 512)
	private String itemDescription;

	/**
	 * The data site of applied site
	 */
	@Column(name = "data_site", length = 32, nullable = false)
	private String dataSite;

	@Column(name = "entity_site", length = 32, nullable = false)
	private String entitySite;

	/**
	 * The date when item deliveried
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "delivery_date")
	private Date deliveryDate;

	/**
	 * The final quantity of item
	 */
	@Column(name = "fina_quantity", nullable = false)
	private int finalQuantity;

	/**
	 * The quantity of this item are saved in asset table
	 */
	@Column(name = "used_auantity")
	private int usedQuantity;

	/**
	 * Whether this purchase item is saved in asset table
	 */
	@Column(name = "is_used")
	private boolean isUsed;

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemRealName() {
		return itemRealName;
	}

	public void setItemRealName(String itemRealName) {
		this.itemRealName = itemRealName;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getDataSite() {
		return dataSite;
	}

	public void setDataSite(String dataSite) {
		this.dataSite = dataSite;
	}

	public String getEntitySite() {
		return entitySite;
	}

	public void setEntitySite(String entitySite) {
		this.entitySite = entitySite;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public int getFinalQuantity() {
		return finalQuantity;
	}

	public void setFinalQuantity(int finalQuantity) {
		this.finalQuantity = finalQuantity;
	}

	public int getUsedQuantity() {
		return usedQuantity;
	}

	public void setUsedQuantity(int usedQuantity) {
		this.usedQuantity = usedQuantity;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

}
