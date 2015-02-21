package com.booktera.androidclientproxy.lib.models.ProductModels;

import com.booktera.androidclientproxy.lib.models.Paging;

import java.util.List;

public class BookRowPLVM   
{
    private List<ProductVM> ProductsInGroup;
    public List<ProductVM> getProductsInGroup() {
        return ProductsInGroup;
    }

    public void setProductsInGroup(List<ProductVM> value) {
        ProductsInGroup = value;
    }

    private ProductGroupVM ProductGroup;
    public ProductGroupVM getProductGroup() {
        return ProductGroup;
    }

    public void setProductGroup(ProductGroupVM value) {
        ProductGroup = value;
    }

    private Paging Paging;
    public Paging getPaging() {
        return Paging;
    }

    public void setPaging(Paging value) {
        Paging = value;
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

        private String Language;
        public String getLanguage() {
            return Language;
        }

        public void setLanguage(String value) {
            Language = value;
        }

        private String Description;
        public String getDescription() {
            return Description;
        }

        public void setDescription(String value) {
            Description = value;
        }

        private int PublishYear;
        public int getPublishYear() {
            return PublishYear;
        }

        public void setPublishYear(int value) {
            PublishYear = value;
        }

        private int PageNumber;
        public int getPageNumber() {
            return PageNumber;
        }

        public void setPageNumber(int value) {
            PageNumber = value;
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

        private int Edition;
        public int getEdition() {
            return Edition;
        }

        public void setEdition(int value) {
            Edition = value;
        }

        private boolean IsBook;
        public boolean getIsBook() {
            return IsBook;
        }

        public void setIsBook(boolean value) {
            IsBook = value;
        }

        private boolean IsAudio;
        public boolean getIsAudio() {
            return IsAudio;
        }

        public void setIsAudio(boolean value) {
            IsAudio = value;
        }

        private boolean IsVideo;
        public boolean getIsVideo() {
            return IsVideo;
        }

        public void setIsVideo(boolean value) {
            IsVideo = value;
        }

        private boolean IsUsed;
        public boolean getIsUsed() {
            return IsUsed;
        }

        public void setIsUsed(boolean value) {
            IsUsed = value;
        }

        private boolean IsDownloadable;
        public boolean getIsDownloadable() {
            return IsDownloadable;
        }

        public void setIsDownloadable(boolean value) {
            IsDownloadable = value;
        }

        private List<String> Images;
        public List<String> getImages() {
            return Images;
        }

        public void setImages(List<String> value) {
            Images = value;
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

        private String Description;
        public String getDescription() {
            return Description;
        }

        public void setDescription(String value) {
            Description = value;
        }

        private String PriceString;
        public String getPriceString() {
            return PriceString;
        }

        public void setPriceString(String value) {
            PriceString = value;
        }

        private int SumOfActiveProductsInGroup;
        public int getSumOfActiveProductsInGroup() {
            return SumOfActiveProductsInGroup;
        }

        public void setSumOfActiveProductsInGroup(int value) {
            SumOfActiveProductsInGroup = value;
        }

        private int SumOfPassiveProductsInGroup;
        public int getSumOfPassiveProductsInGroup() {
            return SumOfPassiveProductsInGroup;
        }

        public void setSumOfPassiveProductsInGroup(int value) {
            SumOfPassiveProductsInGroup = value;
        }

        private int SumOfBuys;
        public int getSumOfBuys() {
            return SumOfBuys;
        }

        public void setSumOfBuys(int value) {
            SumOfBuys = value;
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


