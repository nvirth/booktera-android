package com.booktera.androidclientproxy.lib.models;

import com.booktera.androidclientproxy.lib.enums.TransactionType;
import com.booktera.androidclientproxy.lib.enums.UserOrderStatus;
import com.booktera.androidclientproxy.lib.models.ProductModels.InBookBlockPVM;

import java.util.Date;
import java.util.List;

public class UserOrderPLVM
{
    private TransactionType TransactionType;
    public TransactionType getTransactionType()
    {
        return TransactionType;
    }

    public void setTransactionType(TransactionType value)
    {
        TransactionType = value;
    }

    private UserOrderVM UserOrder;
    public UserOrderVM getUserOrder()
    {
        return UserOrder;
    }

    public void setUserOrder(UserOrderVM value)
    {
        UserOrder = value;
    }

    private List<InBookBlockPVM> Products;
    public List<InBookBlockPVM> getProducts()
    {
        return Products;
    }

    public void setProducts(List<InBookBlockPVM> value)
    {
        Products = value;
    }

    private List<InBookBlockPVM> ExchangeProducts;
    public List<InBookBlockPVM> getExchangeProducts()
    {
        return ExchangeProducts;
    }

    public void setExchangeProducts(List<InBookBlockPVM> value)
    {
        ExchangeProducts = value;
    }

    public static class UserOrderVM
    {
        private int ID;
        public int getID()
        {
            return ID;
        }

        public void setID(int value)
        {
            ID = value;
        }

        private Date Date;
        public Date getDate()
        {
            return Date;
        }

        public void setDate(Date value)
        {
            Date = value;
        }

        private int SumBookPrice;
        public int getSumBookPrice()
        {
            return SumBookPrice;
        }

        public void setSumBookPrice(int value)
        {
            SumBookPrice = value;
        }

        private UserOrderStatus Status;
        public UserOrderStatus getStatus()
        {
            return Status;
        }

        public void setStatus(UserOrderStatus value)
        {
            Status = value;
        }

        private int VendorsFeePercent;
        public int getVendorsFeePercent()
        {
            return VendorsFeePercent;
        }

        public void setVendorsFeePercent(int value)
        {
            VendorsFeePercent = value;
        }

        private String VendorName;
        public String getVendorName()
        {
            return VendorName;
        }

        public void setVendorName(String value)
        {
            VendorName = value;
        }

        private String VendorFriendlyUrl;
        public String getVendorFriendlyUrl()
        {
            return VendorFriendlyUrl;
        }

        public void setVendorFriendlyUrl(String value)
        {
            VendorFriendlyUrl = value;
        }

        private String VendorAddress;
        public String getVendorAddress()
        {
            return VendorAddress;
        }

        public void setVendorAddress(String value)
        {
            VendorAddress = value;
        }

        private Integer VendorAddressId;
        public Integer getVendorAddressId()
        {
            return VendorAddressId;
        }

        public void setVendorAddressId(Integer value)
        {
            VendorAddressId = value;
        }

        private int CustomersFeePercent;
        public int getCustomersFeePercent()
        {
            return CustomersFeePercent;
        }

        public void setCustomersFeePercent(int value)
        {
            CustomersFeePercent = value;
        }

        private String CustomerName;
        public String getCustomerName()
        {
            return CustomerName;
        }

        public void setCustomerName(String value)
        {
            CustomerName = value;
        }

        private String CustomerFriendlyUrl;
        public String getCustomerFriendlyUrl()
        {
            return CustomerFriendlyUrl;
        }

        public void setCustomerFriendlyUrl(String value)
        {
            CustomerFriendlyUrl = value;
        }

        private String CustomerAddress;
        public String getCustomerAddress()
        {
            return CustomerAddress;
        }

        public void setCustomerAddress(String value)
        {
            CustomerAddress = value;
        }

        private Integer CustomerAddressId;
        public Integer getCustomerAddressId()
        {
            return CustomerAddressId;
        }

        public void setCustomerAddressId(Integer value)
        {
            CustomerAddressId = value;
        }

        private Boolean CustomerFeedbackedSuccessful;
        public Boolean getCustomerFeedbackedSuccessful()
        {
            return CustomerFeedbackedSuccessful;
        }

        public void setCustomerFeedbackedSuccessful(Boolean value)
        {
            CustomerFeedbackedSuccessful = value;
        }

        private Boolean VendorFeedbackedSuccessful;
        public Boolean getVendorFeedbackedSuccessful()
        {
            return VendorFeedbackedSuccessful;
        }

        public void setVendorFeedbackedSuccessful(Boolean value)
        {
            VendorFeedbackedSuccessful = value;
        }

    }

}


