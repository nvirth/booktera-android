package com.booktera.androidclientproxy.lib.proxy;


import com.booktera.androidclientproxy.lib.proxy.clients.interfaces.*;
import com.booktera.androidclientproxy.lib.proxy.clients.*;

public class Services
{
    public static final IAuthenticationClient Authentication = new AuthenticationClient();

    public static final IProductManagerClient ProductManager = new ProductManagerClient();
    public static final IProductGroupManagerClient ProductGroupManager = new ProductGroupManagerClient();
//    public static final ICategoryManager CategoryManager = new CategoryManagerClientSafe();
    public static final IUserProfileManagerClient UserProfileManager = new UserProfileManagerClient();
    public static final IRegistrationClient Registration = new RegistrationClient();
    public static final ITransactionManagerClient TransactionManager = new TransactionManagerClient();
}


