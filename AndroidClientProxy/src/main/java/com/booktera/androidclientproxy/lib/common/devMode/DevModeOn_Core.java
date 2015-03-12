package com.booktera.androidclientproxy.lib.common.devMode;

import android.content.res.Resources;
import android.util.Log;
import com.booktera.androidclientproxy.lib.proxy.Request;
import com.booktera.androidclientproxy.lib.proxy.base.RestServiceClientBase;

import java.io.InputStream;
import java.net.URI;

class DevModeOn_Core implements IDevMode
{
    protected final String tag = ((Object) this).getClass().toString();

    @Override
    public <T> boolean applyMockData(Request<T> r, RestServiceClientBase rscb)
    {
        Log.d(tag, "Applying mock data for " + r.requestUrl);

        try
        {
            // Shortcuts/aliases
            Resources resources = RestServiceClientBase.getResources();

            URI uri = new URI(r.requestUrl);
            String path = uri.getPath(); //e.g.: "/EntityManagers/ProductManagerService.svc/GetMainHighlighteds"
            String regex = "^.*/(\\w+)Service\\.svc/(\\w+)$";
            String resName = path.replaceFirst(regex, "$1_$2").toLowerCase();
            resName += applyMockData_specialPostFix(uri);

            int rawMockResId = resources.getIdentifier(resName, "raw", rscb.getResourcePackageName());
            InputStream is = resources.openRawResource(rawMockResId);

            rscb.handleResponse(r, is);
            return true;
        }
        catch (Exception e)
        {
            Log.e(tag, "Couldn't apply mock data", e);
            return false;
        }
    }

    private String applyMockData_specialPostFix(URI uri)
    {
        // TransactionManagerClient's GET fns would conflict other way
        // Their format is e.g. like this:
        // (Carts)
        // "GetUsersCartsVM?vendorId=NUM&customerId="
        // (InCartsByOthers)
        // "GetUsersCartsVM?vendorId=   &customerId=NUM"

        String[] specialCases = new String[]{
            "GetUsersCartsVM",
            "GetUsersInProgressOrdersVM",
            "GetUsersFinishedTransactionsVM"
        };

        boolean isSpecialCase = false;
        for (String specialCase : specialCases)
            if (uri.getPath().contains(specialCase))
                isSpecialCase = true;

        String res = "";
        if (isSpecialCase)
        {
            for (String keyValuePair : uri.getQuery().split("&"))
            {
                String[] split = keyValuePair.split("=");
                String key = split[0];
                boolean hasValue = split.length > 1;

                if (hasValue)
                    res += "_" + key.toLowerCase();
            }
        }

        return res;
    }
}
