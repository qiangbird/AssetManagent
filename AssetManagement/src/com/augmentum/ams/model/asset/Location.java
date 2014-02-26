package com.augmentum.ams.model.asset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.augmentum.ams.model.base.BaseModel;
import com.augmentum.ams.model.base.ConvertStringToLowerCase;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 8:58:20 AM
 */
@Entity
@Table(name = "location")
@Indexed(index = "location")
@Analyzer(impl = IKAnalyzer.class)
public class Location extends BaseModel implements Serializable{

    private static final long serialVersionUID = -6417746800353665624L;

    /**
     * When display we should use: Site + Room, e.g: Augmetnum Beijing-17201
     */
    @Column(name = "site", nullable = false, length = 32)
    @Field(name = "site", index = Index.TOKENIZED, store = Store.YES)
    @FieldBridge(impl = ConvertStringToLowerCase.class)
    private String site;

    /**
     * When display we should use: Site + Room, e.g: Augmetnum Beijing-17201
     */
    @Column(name = "room", nullable = false, length = 32)
    @Field(name = "room", index = Index.UN_TOKENIZED, store = Store.YES)
    private String room;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

}
