package com.booktera.androidclientproxy.lib.models.ProductModels;

public class InBookBlockPVM   
{
    private ProductVM Product;
    public ProductVM getProduct() {
        return Product;
    }

    public void setProduct(ProductVM value) {
        Product = value;
    }

    private ProductGroupVM ProductGroup;
    public ProductGroupVM getProductGroup() {
        return ProductGroup;
    }

    public void setProductGroup(ProductGroupVM value) {
        ProductGroup = value;
    }

    public static class ProductVM   
    {
        private int ID;
        public int getID() {
            return ID;
        }

        public void setID(int value) {
            ID = value;
        }

        private String ImageUrl;
        public String getImageUrl() {
            return ImageUrl;
        }

        public void setImageUrl(String value) {
            ImageUrl = value;
        }

        private String PriceString;
        public String getPriceString() {
            return PriceString;
        }

        public void setPriceString(String value) {
            PriceString = value;
        }

        private int HowMany;
        public int getHowMany() {
            return HowMany;
        }

        public void setHowMany(int value) {
            HowMany = value;
        }

        private String UserName;
        public String getUserName() {
            return UserName;
        }

        public void setUserName(String value) {
            UserName = value;
        }

        private String UserFriendlyUrl;
        public String getUserFriendlyUrl() {
            return UserFriendlyUrl;
        }

        public void setUserFriendlyUrl(String value) {
            UserFriendlyUrl = value;
        }

        private String Description;
        public String getDescription() {
            return Description;
        }

        public void setDescription(String value) {
            Description = value;
        }

        private boolean IsDownloadable;
        public boolean getIsDownloadable() {
            return IsDownloadable;
        }

        public void setIsDownloadable(boolean value) {
            IsDownloadable = value;
        }

        private int ProductInOrderId;
        public int getProductInOrderId() {
            return ProductInOrderId;
        }

        public void setProductInOrderId(int value) {
            ProductInOrderId = value;
        }
    
    }

    public static class ProductGroupVM   
    {
        private String Title;
        public String getTitle() {
            return Title;
        }

        public void setTitle(String value) {
            Title = value;
        }

        private String SubTitle;
        public String getSubTitle() {
            return SubTitle;
        }

        public void setSubTitle(String value) {
            SubTitle = value;
        }

        private String FriendlyUrl;
        public String getFriendlyUrl() {
            return FriendlyUrl;
        }

        public void setFriendlyUrl(String value) {
            FriendlyUrl = value;
        }

        private String ImageUrl;
        public String getImageUrl() {
            return ImageUrl;
        }

        public void setImageUrl(String value) {
            ImageUrl = value;
        }

        private int SumOfRatings;
        public int getSumOfRatings() {
            return SumOfRatings;
        }

        public void setSumOfRatings(int value) {
            SumOfRatings = value;
        }

        private int RatingPoints;
        public int getRatingPoints() {
            return RatingPoints;
        }

        public void setRatingPoints(int value) {
            RatingPoints = value;
        }

        private String AuthorNames;
        public String getAuthorNames() {
            return AuthorNames;
        }

        public void setAuthorNames(String value) {
            AuthorNames = value;
        }

        private String PublisherName;
        public String getPublisherName() {
            return PublisherName;
        }

        public void setPublisherName(String value) {
            PublisherName = value;
        }

        private String Description;
        public String getDescription() {
            return Description;
        }

        public void setDescription(String value) {
            Description = value;
        }

        private boolean IsCheckedByAdmin;
        public boolean getIsCheckedByAdmin() {
            return IsCheckedByAdmin;
        }

        public void setIsCheckedByAdmin(boolean value) {
            IsCheckedByAdmin = value;
        }

        private String CategoryFullPath;
        public String getCategoryFullPath() {
            return CategoryFullPath;
        }

        public void setCategoryFullPath(String value) {
            CategoryFullPath = value;
        }

        private String CategoryFriendlyUrl;
        public String getCategoryFriendlyUrl() {
            return CategoryFriendlyUrl;
        }

        public void setCategoryFriendlyUrl(String value) {
            CategoryFriendlyUrl = value;
        }
    
    }

}


