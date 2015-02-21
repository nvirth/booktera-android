package com.booktera.androidclientproxy.lib.models.EntityFramework;

public class Category
{
    // -- Ctor
    public Category(boolean withDefaults) throws Exception
    {
        if (withDefaults)
            setIsParent(false);
    }

    // -- Props

    private int ID;
    public int getID()
    {
        return ID;
    }

    public void setID(int value)
    {
        ID = value;
    }

    private Integer ParentCategoryID;
    public Integer getParentCategoryID()
    {
        return ParentCategoryID;
    }

    public void setParentCategoryID(Integer value)
    {
        ParentCategoryID = value;
    }

    private String DisplayName;
    public String getDisplayName()
    {
        return DisplayName;
    }

    public void setDisplayName(String value)
    {
        DisplayName = value;
    }

    private String FullPath;
    public String getFullPath()
    {
        return FullPath;
    }

    public void setFullPath(String value)
    {
        FullPath = value;
    }

    private String FriendlyUrl;
    public String getFriendlyUrl()
    {
        return FriendlyUrl;
    }

    public void setFriendlyUrl(String value)
    {
        FriendlyUrl = value;
    }

    private String Sort;
    public String getSort()
    {
        return Sort;
    }

    public void setSort(String value)
    {
        Sort = value;
    }

    private boolean IsParent;
    public boolean getIsParent()
    {
        return IsParent;
    }

    public void setIsParent(boolean value)
    {
        IsParent = value;
    }

    private Category ParentCategory;
    public Category getParentCategory()
    {
        return ParentCategory;
    }

    public void setParentCategory(Category value)
    {
        ParentCategory = value;
    }
}


