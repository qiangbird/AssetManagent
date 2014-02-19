package com.augmentum.ams.model.asset;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.search.annotations.Analyzer;
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
 * @time Sep 12, 2013 9:06:05 AM
 */
@Entity
@Table(name = "transfer_log")
@Indexed(index = "transfer_log")
@Analyzer(impl = IKAnalyzer.class)
public class TransferLog extends BaseModel {

    private static final long serialVersionUID = 2991532139550684606L;

    /**
     * Each time the asset transferred
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @Field(name = "time", index = Index.UN_TOKENIZED, store = Store.YES)
    @DateBridge(resolution = Resolution.SECOND)
    private Date time;

    @Column(nullable = false, length = 64)
    @Field(name = "action", index = Index.UN_TOKENIZED, store = Store.YES)
    @FieldBridge(impl = ConvertStringToLowerCase.class)
    private String action;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @IndexedEmbedded(depth = 1)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id")
    @IndexedEmbedded(depth = 1)
    private Asset asset;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }
}
