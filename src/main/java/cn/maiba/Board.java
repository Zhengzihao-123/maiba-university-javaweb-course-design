package cn.maiba;

import java.sql.Timestamp;

public class Board extends MyTableItem {
    public static final String TABLE_NAME = "t_board";
    
    String name;
    String description;
    Integer moderatorId;
    Timestamp createTime;
    int sortOrder;
    
    public Board() {
        name = "";
        description = "";
        moderatorId = null;
        createTime = new Timestamp(System.currentTimeMillis());
        sortOrder = 0;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Integer getModeratorId() {
        return moderatorId;
    }
    
    public void setModeratorId(Integer moderatorId) {
        this.moderatorId = moderatorId;
    }
    
    public Timestamp getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
    
    public int getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
