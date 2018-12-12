package com.moh.clinicalguideline.core;

import com.moh.clinicalguideline.helper.BaseModel;

import java.util.UUID;

public class AlgorithmDescription implements BaseModel {
    private int Id;
    private String Title;
    private String Description;
    private boolean HasDescription;
    private boolean HasTitle;
    private boolean IsCondition;
    private String NodeTypeCode;
    private UUID rowguid;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getNodeTypeCode() {
        return NodeTypeCode;
    }

    public void setNodeTypeCode(String nodeTypeCode) {
        NodeTypeCode = nodeTypeCode;
    }

    public UUID getRowguid() {
        return rowguid;
    }

    public void setRowguid(UUID rowguid) {
        this.rowguid = rowguid;
    }

    public boolean getIsCondition() {
        return IsCondition;
    }

    public void setIsCondition(boolean condition) {
        IsCondition = condition;
    }

    @Override
    public String getFilterrableText() {
        return getTitle();
    }

    public boolean getHasDescription() {
        return HasDescription;
    }

    public void setHasDescription(boolean hasDescription) {
        HasDescription = hasDescription;
    }

    public boolean getHasTitle() {
        return HasTitle;
    }

    public void setHasTitle(boolean hasTitle) {
        HasTitle = hasTitle;
    }
}
