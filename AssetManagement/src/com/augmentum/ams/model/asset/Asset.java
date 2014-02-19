package com.augmentum.ams.model.asset;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.augmentum.ams.model.base.BaseModel;
import com.augmentum.ams.model.base.ConvertStringToLowerCase;
import com.augmentum.ams.model.user.User;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 8:53:14 AM
 */
@Entity
@Table(name = "asset")
@Indexed(index = "asset")
@Analyzer(impl = IKAnalyzer.class)
public class Asset extends BaseModel {

    private static final long serialVersionUID = -8135576877013861132L;

    @Column(name = "asset_id", length = 128, nullable = false)
    @Field(name = "assetId", index = Index.UN_TOKENIZED, store = Store.YES)
    @FieldBridge(impl = ConvertStringToLowerCase.class)
    private String assetId;

    @Column(name = "asset_name", length = 128, nullable = false)
    @Field(name = "assetName", index = Index.TOKENIZED, store = Store.YES)
    private String assetName;

    @Column(length = 128)
    @Field(name = "manufacturer", index = Index.TOKENIZED, store = Store.YES)
    private String manufacturer;

    /** The type of the asset. please refer to @TypeEnum */
    @Column(name = "type", length = 32, nullable = false)
    @Field(name = "type", index = Index.UN_TOKENIZED, store = Store.YES)
    @FieldBridge(impl = ConvertStringToLowerCase.class)
    private String type;

    @Column(length = 64, name = "bar_code")
    @Field(name = "barCode", index = Index.UN_TOKENIZED, store = Store.YES)
    private String barCode;

    @Column(length = 256, name = "series_no")
    @Field(name = "seriesNo", index = Index.TOKENIZED, store = Store.YES)
    private String seriesNo;

    /**
     * The PO number used to refer to the purchase order. When ownership is
     * Augmentum, poNo must be filled.
     */
    @Column(length = 128, name = "po_no")
    @Field(name = "poNo", index = Index.UN_TOKENIZED, store = Store.YES)
    private String poNo;

    @Column(length = 256, name = "photo_path")
    @Field(name = "photoPath", index = Index.UN_TOKENIZED, store = Store.NO)
    private String photoPath;

    /**
     * Ownership: Augmentum, or customer name
     */
    @Column(length = 255, name = "owner_ship")
    @Field(name = "ownerShip", index = Index.TOKENIZED, store = Store.YES)
    private String ownerShip;

    /** The asset is fixed or not */
    @Column(name = "fixed")
    @Field(name = "fixed", index = Index.UN_TOKENIZED, store = Store.YES)
    private boolean fixed;

    /** The status of asset, please refer to @StatusEnum */
    @Column(length = 32, nullable = false)
    @Field(name = "status", index = Index.UN_TOKENIZED, store = Store.YES)
    @FieldBridge(impl = ConvertStringToLowerCase.class)
    private String status;

    /** The description of asset. */
    @Column(name = "memo", length = 1024)
    @Field(name = "memo", index = Index.TOKENIZED, store = Store.YES)
    private String memo;

    /** The time when the assets checked in to company. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "check_in_time")
    @Field(name = "checkInTime", index = Index.UN_TOKENIZED, store = Store.YES)
    @DateBridge(resolution = Resolution.SECOND)
    private Date checkInTime;

    /** The time when the assets checked out from company. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "check_out_time")
    @Field(name = "checkOutTime", index = Index.UN_TOKENIZED, store = Store.YES)
    @DateBridge(resolution = Resolution.SECOND)
    private Date checkOutTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "warranty_time")
    @Field(name = "warrantyTime", index = Index.UN_TOKENIZED, store = Store.YES)
    @DateBridge(resolution = Resolution.SECOND)
    private Date warrantyTime;

    @Column(name = "vendor")
    @Field(name = "vendor", index = Index.TOKENIZED, store = Store.YES)
    private String vendor;

    @Fetch(FetchMode.JOIN)
    @OneToMany(mappedBy = "asset", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<TransferLog> transferLogs = new HashSet<TransferLog>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "software_id")
    @IndexedEmbedded(depth = 1)
    private Software software;

    @OneToOne(fetch = FetchType.LAZY,mappedBy="asset")
    @ContainedIn
    private Machine machine;

    @OneToOne(fetch = FetchType.LAZY,mappedBy="asset")
    @ContainedIn
    private Monitor monitor;

    @OneToOne(fetch = FetchType.LAZY,mappedBy="asset")
    @ContainedIn
    private Device device;

    @OneToOne(fetch = FetchType.LAZY,mappedBy="asset")
    @ContainedIn
    private OtherAssets otherAssets;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    @IndexedEmbedded(depth = 1)
    private Location location;

    @Column(name = "entity")
    @Field(name = "entity", index = Index.TOKENIZED, store = Store.YES)
    private String entity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @IndexedEmbedded(depth = 1)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @IndexedEmbedded(depth = 1)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @IndexedEmbedded(depth = 1)
    private User user;

    @Column(name = "keeper", length = 2000)
    @Field(name = "keeper", index = Index.TOKENIZED, store = Store.YES)
    private String keeper;

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getSeriesNo() {
        return seriesNo;
    }

    public void setSeriesNo(String seriesNo) {
        this.seriesNo = seriesNo;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getOwnerShip() {
        return ownerShip;
    }

    public void setOwnerShip(String ownerShip) {
        this.ownerShip = ownerShip;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Date getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Date checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public Date getWarrantyTime() {
        return warrantyTime;
    }

    public void setWarrantyTime(Date warrantyTime) {
        this.warrantyTime = warrantyTime;
    }

    // public List<TransferLog> getTransferLogs() {
    // return transferLogs;
    // }
    //
    // public void setTransferLogs(List<TransferLog> transferLogs) {
    // this.transferLogs = transferLogs;
    // }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Software getSoftware() {
        return software;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public Monitor getMonitor() {
        return monitor;
    }

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public OtherAssets getOtherAssets() {
        return otherAssets;
    }

    public void setOtherAssets(OtherAssets otherAssets) {
        this.otherAssets = otherAssets;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getKeeper() {
        return keeper;
    }

    public void setKeeper(String keeper) {
        this.keeper = keeper;
    }

    public Set<TransferLog> getTransferLogs() {
        return transferLogs;
    }

    public void setTransferLogs(Set<TransferLog> transferLogs) {
        this.transferLogs = transferLogs;
    }

/*    public Set<Audit> getAudits() {
        return audits;
    }

    public void setAudits(Set<Audit> audits) {
        this.audits = audits;
    }

    public Set<Inconsistent> getInconsistents() {
        return inconsistents;
    }

    public void setInconsistents(Set<Inconsistent> inconsistents) {
        this.inconsistents = inconsistents;
    }*/

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

}
