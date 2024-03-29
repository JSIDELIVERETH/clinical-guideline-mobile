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
    private int Page;
    private String NodeTypeCode;
    private int ChildCount;
    private Integer  FirstChildNodeId;
    private UUID rowguid;
    //private String FullTitle;

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
      //  FullTitle = title;
        String[] splited = title.split("/");
        if(splited.length > 2)
            Title = splited[0]+"/"+splited[1];
        else
            Title=title;
    }

//    public String getFullTitle()
//    {
//        return FullTitle;
//    }

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

    public int getChildCount() {
        return ChildCount;
    }

    public Boolean getIsSingle() {
        //has only one and not both
        return getTitle().isEmpty() || getDescription().isEmpty();
    }

    public void setChildCount(int childCount) {
        ChildCount = childCount;
    }

    public Integer getFirstChildNodeId() {
        return FirstChildNodeId;
    }

    public void setFirstChildNodeId(Integer firstChildNodeId) {
        FirstChildNodeId = firstChildNodeId;
    }

    public int getPage() {
        return Page;
    }

    public void setPage(int page) {
        Page = page;
    }

    public boolean isSymptom(){
        return NodeCode.isSymptomOrCare(getNodeTypeCode());
    }
    public boolean isUrgent(){
        return NodeCode.isUrgent(getNodeTypeCode());
    }
    public boolean isNonUrgent(){
        return NodeCode.isNonUrgent(getNodeTypeCode());
    }
    public boolean isAlgorithm(){
        return NodeCode.isAlgorithm(getNodeTypeCode());
    }
    public boolean isSkipable(){
        return (!HasDescription && ChildCount <= 1 && FirstChildNodeId != null);
    }
}
