package com.augmentum.ams.model.asset;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.augmentum.ams.model.base.BaseModel;
import com.augmentum.ams.model.enumeration.ProcessTypeEnum;

/**
 * @description divide the customers into group.
 * @time Dec 11, 2013 16:03:42 PM
 * @author John.Li
 *
 */
@Entity
@Table(name = "customer_group")
@Indexed(index = "customer_group")
@Analyzer(impl = IKAnalyzer.class)
public class CustomerGroup extends BaseModel {

    private static final long serialVersionUID = -4884926963993885339L;

    /**
     *  The name of the group
     */
    @Column(name = "group_name", length = 128, nullable = false)
    @Field(name = "groupName", index = Index.TOKENIZED, store = Store.YES)
    private String groupName;
    
    /**
     *  The description of the group
     */
    @Column(name = "description", length = 1000)
    @Field(name = "description", index = Index.TOKENIZED, store = Store.YES)
    private String description;
    
    /**
     *  The process type of the group
     */
    @Column(name = "process_type", length = 128, nullable = false)
    @Enumerated(value = EnumType.STRING)
    @Field(name = "processType", index = Index.UN_TOKENIZED, store = Store.YES)
    private ProcessTypeEnum processType;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ProcessTypeEnum getProcessType() {
        return processType;
    }

    public void setProcessType(ProcessTypeEnum processType) {
        this.processType = processType;
    }
    
}
