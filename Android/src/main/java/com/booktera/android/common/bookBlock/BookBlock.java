package com.booktera.android.common.bookBlock;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.booktera.android.BookteraApplication;
import com.booktera.android.R;
import com.booktera.android.activities.ProductGroupDetailsActivity;
import com.booktera.android.common.Constants;
import com.booktera.android.common.CtxMenuBase;
import com.booktera.android.common.Helpers;
import com.booktera.android.common.UserData;
import com.booktera.android.common.utils.Utils;
import com.booktera.androidclientproxy.lib.enums.UserOrderStatus;
import com.booktera.androidclientproxy.lib.models.ProductModels.InBookBlockPVM;
import com.booktera.androidclientproxy.lib.models.UserOrderPLVM;
import com.booktera.androidclientproxy.lib.proxy.Services;

/**
 * Created by Norbert on 2015.02.10..
 */
public class BookBlock extends CtxMenuBase
{
    private static final String tag = BookBlock.class.toString();
    private static final Resources r = BookteraApplication.getAppResources();

    //region ViewHolder

    public static class ViewHolder
    {
        TextView userName;
        ImageView bookImage;
        TextView quantity;
        TextView isDownloadable;
        TextView price;
        TextView title;
        TextView subTitle;
        TextView authorNames;

        public ViewHolder(View view)
        {
            this.userName = (TextView) view.findViewById(R.id.userName);
            this.bookImage = (ImageView) view.findViewById(R.id.bookImage);
            this.quantity = (TextView) view.findViewById(R.id.quantity);
            this.isDownloadable = (TextView) view.findViewById(R.id.isDownloadable);
            this.price = (TextView) view.findViewById(R.id.price);
            this.title = (TextView) view.findViewById(R.id.title);
            this.subTitle = (TextView) view.findViewById(R.id.subTitle);
            this.authorNames = (TextView) view.findViewById(R.id.authorNames);
        }
    }

    //endregion

    private InBookBlockPVM vm;
    private UserOrderPLVM.UserOrderVM userOrderVm;
    private boolean isExchangeProduct;
    private int userOrderId_forExchange;
    private ViewHolder vh;
    private View bookBlockView;
    private Activity activity;
    private CtxMenuClickListeners ctxMenuClickListeners = new CtxMenuClickListeners();

    //region Ctor
    public static class CtorArgs
    {
        public CtorArgs(InBookBlockPVM vm, View bookBlockView, Activity activity)
        {
            this(vm, /*ViewHolder*/null, bookBlockView, activity);
        }
        public CtorArgs(InBookBlockPVM vm, ViewHolder vh, View bookBlockView, Activity activity)
        {
            this(vm, vh, bookBlockView, activity, /*isExchangeProduct*/ false, /*userOrderVm*/null,/*userOrderId_forExchange*/ -1);
        }
        public CtorArgs(InBookBlockPVM vm, View bookBlockView, Activity activity, boolean isExchange)
        {
            this(vm, /*ViewHolder*/null, bookBlockView, activity, isExchange, /*userOrderVm*/null, /*userOrderId_forExchange*/ -1);
        }
        public CtorArgs(
            InBookBlockPVM vm,
            ViewHolder vh,
            View bookBlockView,
            Activity activity,
            boolean isExchange,
            UserOrderPLVM.UserOrderVM userOrderVm,
            int userOrderId_forExchange
        )
        {
            this.isExchange = isExchange;
            this.vm = vm;
            this.vh = vh;
            this.bookBlockView = bookBlockView;
            this.activity = activity;
            this.userOrderVm = userOrderVm;
            this.userOrderId_forExchange = userOrderId_forExchange;

            // Both of them couldn't be set
            if ((userOrderVm != null) && (userOrderId_forExchange > 0))
                Utils.error("BookBlock instantiation error: (userOrderVm != null) ^ (userOrderId_forExchange > 0)", tag);
        }

        public boolean isExchange;
        public InBookBlockPVM vm;
        public ViewHolder vh;
        public View bookBlockView;
        public Activity activity;
        public UserOrderPLVM.UserOrderVM userOrderVm;
        public int userOrderId_forExchange;
    }

    public BookBlock(CtorArgs args)
    {
        this.vm = args.vm;
        this.bookBlockView = args.bookBlockView;
        this.activity = args.activity;
        this.isExchangeProduct = args.isExchange;
        this.userOrderVm = args.userOrderVm;
        this.userOrderId_forExchange = args.userOrderId_forExchange;
        this.vh = args.vh != null
            ? args.vh
            : new ViewHolder(bookBlockView);
    }
    //endregion

    public void fill()
    {
        // E.g. by ProductGroup, we don't have UserName. In this case, we have to tighten the
        // cover at the BookBlockImage's bottom
        if (!TextUtils.isEmpty(vm.getProduct().getUserName()))
            vh.userName.setText(vm.getProduct().getUserName());
        else
            vh.userName.setVisibility(View.GONE);

        if (vm.getProduct().getHowMany() != -1)
            vh.quantity.setText(vm.getProduct().getHowMany() + " db");
        else
            vh.quantity.setVisibility(View.GONE);

        if (!vm.getProduct().getIsDownloadable())
            vh.isDownloadable.setVisibility(View.GONE);

        vh.price.setText(vm.getProduct().getPriceString());
        vh.title.setText(vm.getProductGroup().getTitle());
        vh.subTitle.setText(vm.getProductGroup().getSubTitle());
        vh.authorNames.setText(vm.getProductGroup().getAuthorNames());

        Utils.setProductImage(vm, vh.bookImage);
    }

    @Override
    public void setupContextMenu()
    {
        bookBlockView.setOnCreateContextMenuListener((menu, v, menuInfo) ->
        {
            // -- Inflating
            MenuInflater inflater = new MenuInflater(activity);
            inflater.inflate(R.menu.ctx_bookblock, menu);

            // -- Flags
            String titleFormat;
            boolean isQuantity_gt_0 = vm.getProduct().getHowMany() > 0;
            boolean isUserAuthenticated = UserData.Instace.isAuthenticated();
            boolean isProduct = !TextUtils.isEmpty(vm.getProduct().getDescription());
            boolean isAddToExchangeCartPossible = userOrderId_forExchange > 0;
            String productOwnerName = Utils.ifNull(vm.getProduct().getUserName(), "").toLowerCase();
            boolean isOwnBook = productOwnerName.equals(UserData.Instace.getUserNameLowerCase());

            boolean isInCart = false;
            boolean existsCustomerName = false;
            boolean existsVendorName = false;

            boolean isInUserOrder = userOrderVm != null;
            if (isInUserOrder)
            {
                isInCart = userOrderVm.getStatus() == UserOrderStatus.Cart;
                existsCustomerName = !TextUtils.isEmpty(userOrderVm.getCustomerName());
                existsVendorName = !TextUtils.isEmpty(userOrderVm.getVendorName());
            }

            //-- MenuItems

            //UsersProducts
            if (!TextUtils.isEmpty(vh.userName.getText()))
            {
                MenuItem ctxUsersProducts = menu.findItem(R.id.bookBlockCtx_gotoUsersProducts);
                titleFormat = ctxUsersProducts.getTitle().toString();
                ctxUsersProducts.setTitle(String.format(titleFormat, vh.userName.getText()));
                enable(ctxUsersProducts);
            }

            //ProductGroup
            if (!TextUtils.isEmpty(vh.title.getText()))
            {
                MenuItem ctxProductGroup = menu.findItem(R.id.bookBlockCtx_gotoProductGroup);
                titleFormat = ctxProductGroup.getTitle().toString();
                ctxProductGroup.setTitle(String.format(titleFormat, vh.title.getText()));
                enable(ctxProductGroup);
            }

            //AddToCart
            if (isQuantity_gt_0 && isUserAuthenticated && isProduct && !isOwnBook && !isInUserOrder)
                enable(R.id.bookBlockCtx_addToCart, menu);

            //AddToExchangeCart
            if (isAddToExchangeCartPossible && isQuantity_gt_0 && isUserAuthenticated && isProduct && !isInUserOrder)
                enable(R.id.bookBlockCtx_addToExchangeCart, menu);

            //RemoveFromCart, ChangeQuantity (in cart)
            if (isInCart && !existsCustomerName && existsVendorName) // isOwnCartItem
            {
                enable(R.id.bookBlockCtx_removeFromCart, menu);
                enable(R.id.bookBlockCtx_changeQuantityInCart, menu);
            }

            //RemoveFromExchangeCart, ChangeQuantity (in exchange cart)
            if (isExchangeProduct && isInUserOrder && userOrderVm.getStatus() == UserOrderStatus.BuyedWaiting)
            {
                enable(R.id.bookBlockCtx_removeFromExchangeCart, menu);
                enable(R.id.bookBlockCtx_changeQuantityInExchangeCart, menu);
            }
        });
    }

    @Override
    public MenuItem.OnMenuItemClickListener fetchClickListener(MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.bookBlockCtx_gotoUsersProducts:
                return ctxMenuClickListeners.gotoUsersProducts();
            case R.id.bookBlockCtx_gotoProductGroup:
                return ctxMenuClickListeners.gotoProductGroup();
            case R.id.bookBlockCtx_addToCart:
                return ctxMenuClickListeners.addToCart();
            case R.id.bookBlockCtx_addToExchangeCart:
                return ctxMenuClickListeners.addToExchangeCart();
            case R.id.bookBlockCtx_removeFromCart:
                return ctxMenuClickListeners.removeFromCart();
            case R.id.bookBlockCtx_changeQuantityInCart:
                return ctxMenuClickListeners.changeQuantityInCart();
            case R.id.bookBlockCtx_changeQuantityInExchangeCart:
                return ctxMenuClickListeners.changeQuantityInExchangeCart();
            case R.id.bookBlockCtx_removeFromExchangeCart:
                return ctxMenuClickListeners.removeFromExchangeCart();

            default:
                Utils.error("Unrecognised MenuItemID: " + menuItem.getItemId(), tag);
                return null; // unreachable
        }
    }

    //todo implement BookBlock CtxMenuClickListeners
    class CtxMenuClickListeners
    {
        public MenuItem.OnMenuItemClickListener gotoUsersProducts()
        {
            return Helpers.gotoUsersProducts(vm.getProduct().getUserFriendlyUrl(), activity);
        }

        public MenuItem.OnMenuItemClickListener gotoProductGroup()
        {
            return item -> {
                Intent intent = new Intent(BookteraApplication.getAppContext(), ProductGroupDetailsActivity.class);
                intent.putExtra(Constants.PARAM_PRODUCT_GROUP_FU, vm.getProductGroup().getFriendlyUrl());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                activity.startActivity(intent);
                return true;
            };
        }

        public MenuItem.OnMenuItemClickListener addToCart()
        {
            return item -> {
                Services.TransactionManager.addProductToCart(vm.getProduct().getID(),
                    () /*success*/ -> activity.runOnUiThread(() -> {
                        Utils.showToast(r.getString(R.string.addToCart_successMsg),/*isLong*/ true);
                        // Refresh cached data
                        decrementQuantity();
                        // Refresh view
                        fill();
                    }),
                    httpResponse /*failure*/ -> activity.runOnUiThread(() -> {
                        String _errMsg;
                        if (vm.getProduct().getIsDownloadable())
                            _errMsg = r.getString(R.string.addToCart_failureMsg_electronicBook);
                        else
                            _errMsg = r.getString(R.string.addToCart_failureMsg_general);

                        String _title = r.getString(R.string.Error_);
                        Utils.alert(activity, _title, _errMsg);
                    }));

                return true;
            };
        }

        public MenuItem.OnMenuItemClickListener addToExchangeCart()
        {
            return item -> {
                Utils.showToast("ctx_addToExchangeCart is not implemented yet");
                return true;
            };
        }

        public MenuItem.OnMenuItemClickListener removeFromCart()
        {
            return item -> {
                Utils.showToast("ctx_removeFromCart is not implemented yet");
                return true;
            };
        }

        public MenuItem.OnMenuItemClickListener changeQuantityInCart()
        {
            return item -> {
                Utils.showToast("ctx_changeQuantityInCart is not implemented yet");
                return true;
            };
        }

        public MenuItem.OnMenuItemClickListener changeQuantityInExchangeCart()
        {
            return item -> {
                Utils.showToast("ctx_changeQuantityInExchangeCart is not implemented yet");
                return true;
            };
        }

        public MenuItem.OnMenuItemClickListener removeFromExchangeCart()
        {
            return item -> {
                Utils.showToast("ctx_removeFromExchangeCart is not implemented yet");
                return true;
            };
        }

        private void decrementQuantity()
        {
            // Electronic product's qunatity is always 1
            if (!vm.getProduct().getIsDownloadable())
                vm.getProduct().setHowMany(vm.getProduct().getHowMany() - 1);

        }

    }
}
