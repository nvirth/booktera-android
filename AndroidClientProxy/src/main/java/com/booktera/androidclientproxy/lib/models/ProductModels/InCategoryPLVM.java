package com.booktera.androidclientproxy.lib.models.ProductModels;

public class InCategoryPLVM extends BookBlockPLVM
{
    private CategoryVM Category;
    public CategoryVM getCategory()
    {
        return Category;
    }

    public void setCategory(CategoryVM value)
    {
        Category = value;
    }

    public static class CategoryVM
    {
        private String DisplayName;
        public String getDisplayName()
        {
            return DisplayName;
        }

        public void setDisplayName(String value)
        {
            DisplayName = value;
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

        private String FullPath;
        public String getFullPath()
        {
            return FullPath;
        }

        public void setFullPath(String value)
        {
            FullPath = value;
        }

    }

}


