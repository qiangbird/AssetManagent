package com.augmentum.ams.model.asset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.augmentum.ams.model.base.BaseModel;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 8:56:42 AM
 */
@Entity
@Table(name = "customer")
@Indexed(index = "customer")
@Analyzer(impl = IKAnalyzer.class)
@JsonIgnoreProperties(value={"assets"})    
public class Customer extends BaseModel implements Serializable {

	private static final long serialVersionUID = -1485320091445990928L;

	@Column(name = "customer_name", length = 128, nullable = false)
	@Field(name = "customerName", index = Index.TOKENIZED, store = Store.YES)
	private String customerName;

	@Column(name = "customer_code", length = 32)
	@Field(name = "customerCode", index = Index.UN_TOKENIZED, store = Store.YES)
	private String customerCode;
	
    /**
     *  The group code of the group which the customer in
     *  If the groupCode is null, the default process is 
     *  not_share
     */
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "groupId")
    private CustomerGroup customerGroup;


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer",cascade =CascadeType.PERSIST)
	@ContainedIn
	private List<Asset> assets = new ArrayList<Asset>();

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

    public CustomerGroup getCustomerGroup() {
        return customerGroup;
    }

    public void setCustomerGroup(CustomerGroup customerGroup) {
        this.customerGroup = customerGroup;
    }

	public List<Asset> getAssets() {
		return assets;
	}

	public void setAssets(List<Asset> assets) {
		this.assets = assets;
	}

	@Override
	public String toString() {
		return "Customer [customerName=" + customerName + ", customerCode="
				+ customerCode + "]";
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((customerCode == null) ? 0 : customerCode.hashCode());
        result = prime * result + ((customerName == null) ? 0 : customerName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Customer other = (Customer) obj;
        if (customerCode == null) {
            if (other.customerCode != null)
                return false;
        } else if (!customerCode.equals(other.customerCode))
            return false;
        if (customerName == null) {
            if (other.customerName != null)
                return false;
        } else if (!customerName.equals(other.customerName))
            return false;
        return true;
    }
	
}
