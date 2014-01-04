package com.augmentum.ams.model.asset;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.augmentum.ams.model.base.BaseModel;
import com.augmentum.ams.model.user.UserCustomColumn;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 8:54:07 AM
 */
@Entity
@Table(name = "custom_column")
public class CustomizedColumn extends BaseModel {

    private static final long serialVersionUID = -7715705068888116905L;

    /**
     * All the table names which have a list page.
     */
    @Column(name = "category_type", length = 32, nullable = true)
    private String categoryType;

	/**
     * The enName of column
     */
    @Column(name = "en_name", length = 32, nullable = true)
    private String enName;

    /**
     * The zhName of column
     */
    @Column(name = "zh_name", length = 32, nullable = true)
    private String zhName;

    /**
     * Which column is display default
     */
    @Column(name = "is_must_show")
    private boolean isMustShow = true;

    /**
     * The sortName of a column
     */
    @Column(name = "sort_name", length = 32, nullable = true)
    private String sortName;

    /**
     * The default sequence of column in sudoku
     */
    @Column(length = 6, nullable = true)
    private int sequence;

    /**
     * The width of column in sudoku
     */
    @Column(length = 8, nullable = true)
    private int width;
    
    /**
     * The type of column in sudoku
     */
    @Column(name = "column_type", length = 8, nullable = false)
    private String columnType;
    
    /**
     * The real table of column in database
     */
    @Column(name = "real_Table", length = 20, nullable = false)
    private String realTable;
    
    /**
     * The real search column in database
     */
    @Column(name = "search_column", length = 80)
    private String searchColumn;
    
    @OneToMany(mappedBy = "customizedColumn")
    private List<UserCustomColumn> userCustomColumn = new ArrayList<UserCustomColumn>();

    public String getSearchColumn() {
        return searchColumn;
    }

    public void setSearchColumn(String searchColumn) {
        this.searchColumn = searchColumn;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getZhName() {
        return zhName;
    }

    public void setZhName(String zhName) {
        this.zhName = zhName;
    }

    public boolean getIsMustShow() {
        return isMustShow;
    }

    public void setIsMustShow(boolean isMustShow) {
        this.isMustShow = isMustShow;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public List<UserCustomColumn> getUserCustomColumns() {
        return userCustomColumn;
    }

    public void setUserCustomColumns(List<UserCustomColumn> userCustomColumn) {
        this.userCustomColumn = userCustomColumn;
    }
    
    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }
    
    public String getRealTable() {
		return realTable;
	}

	public void setRealTable(String realTable) {
		this.realTable = realTable;
	}

}
