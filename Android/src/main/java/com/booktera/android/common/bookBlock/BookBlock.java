package com.booktera.android.common.bookBlock;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.booktera.android.BookteraApplication;
import com.booktera.android.R;
import com.booktera.android.activities.UsersProductsActivity;
import com.booktera.android.common.Constants;
import com.booktera.android.common.UserData;
import com.booktera.android.common.utils.Utils;
import com.booktera.android.fragments.bookBlock.UsersProductsFragment;
import com.booktera.androidclientproxy.lib.enums.UserOrderStatus;
import com.booktera.androidclientproxy.lib.models.ProductModels.InBookBlockPVM;
import com.booktera.androidclientproxy.lib.models.UserOrderPLVM;

/**
 * Created by Norbert on 2015.02.10..
 */
public class BookBlock
{
    private static final String tag = BookBlock.class.toString();

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
    private ViewHolder vh;
    private View bookBlockView;
    private Context context;
    //TODO this shouldn't be here: dataHolder in BookBlock
    private BookBlockDataHolder.Base dataHolder;

    private CtxMenuClickListeners ctxMenuClickListeners = new CtxMenuClickListeners();

    public BookBlock(InBookBlockPVM vm, View bookBlockView, Context context, BookBlockDataHolder.Base dataHolder)
    {
        this.vm = vm;
        this.bookBlockView = bookBlockView;
        this.context = context;
        this.dataHolder = dataHolder;

        this.vh = new BookBlock.ViewHolder(bookBlockView);
    }

    public void fill()
    {
        //TODO check the if-s, when corresponding view will exist

        // E.g. by ProductGroup, we don't have UserName. In this case, we have to tighten the
        // cover at the BookBlockImage's bottom
        if (vm.getProduct().getUserName() != null)
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

    public void setupContextMenu()
    {
        bookBlockView.setOnCreateContextMenuListener((menu, v, menuInfo) ->
        {
            // -- Inflating
            MenuInflater inflater = new MenuInflater(context);
            inflater.inflate(R.menu.ctx_bookblock, menu);

            // -- Flags
            String titleFormat;
            boolean isQuantity_gt_0 = vm.getProduct().getHowMany() > 0;
            boolean isUserAuthenticated = UserData.Instace.isAuthenticated();
            boolean isProduct = !Utils.isNullOrEmpty(vm.getProduct().getDescription());
            String productOwnerName = Utils.ifNull(vm.getProduct().getUserName(), "").toLowerCase();
            boolean isOwnBook = productOwnerName.equals(UserData.Instace.getUserNameLowerCase());

            UserOrderPLVM.UserOrderVM userOrderVm = null;
            boolean isAddToExchangeCartPossible = false;
            boolean isInCart = false;
            boolean isExchangeProduct = false;
            boolean existsCustomerName = false;
            boolean existsVendorName = false;
            BookBlockDataHolder.UserOrder userOrderPLVM = dataHolder instanceof BookBlockDataHolder.UserOrder
                ? (BookBlockDataHolder.UserOrder) dataHolder
                : null;
            if (userOrderPLVM != null)
            {
                isAddToExchangeCartPossible = userOrderPLVM.isAddToExchangeCartPossible;
                isExchangeProduct = vm == userOrderPLVM.exchangeProducts.get(userOrderPLVM.actualPosition);

                userOrderVm = userOrderPLVM.userOrder;
                if (userOrderVm != null)
                {
                    isInCart = userOrderVm.getStatus() == UserOrderStatus.Cart;
                    existsCustomerName = !Utils.isNullOrEmpty(userOrderVm.getCustomerName());
                    existsVendorName = !Utils.isNullOrEmpty(userOrderVm.getVendorName());
                }
            }
            boolean isInUserOrder = userOrderVm != null;

            //-- MenuItems

            //UsersProducts
            if (!Utils.isNullOrEmpty(vh.userName.getText()))
            {
                MenuItem ctxUsersProducts = menu.findItem(R.id.bookBlockCtx_gotoUsersProducts);
                titleFormat = ctxUsersProducts.getTitle().toString();
                ctxUsersProducts.setTitle(String.format(titleFormat, vh.userName.getText()));
                enable(ctxUsersProducts);
            }

            //ProductGroup
            if (!Utils.isNullOrEmpty(vh.title.getText()))
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
            if (isQuantity_gt_0 && isUserAuthenticated && isProduct && !isInUserOrder && isAddToExchangeCartPossible)
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

    public void enable(@IdRes int menuItemID, ContextMenu menu)
    {
        enable(menu.findItem(menuItemID));
    }
    public void enable(MenuItem menuItem)
    {
        menuItem.setVisible(true);
        menuItem.setEnabled(true);
        menuItem.setOnMenuItemClickListener(fetchClickListener(menuItem));
    }
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

    class CtxMenuClickListeners
    {
        public MenuItem.OnMenuItemClickListener gotoUsersProducts()
        {
            return item -> {
                Intent intent = new Intent(BookteraApplication.getAppContext(), UsersProductsActivity.class);
                intent.putExtra(Constants.PARAM_USER_FU, vm.getProduct().getUserFriendlyUrl());
                //TODO check if it's ok, or should we use the fragment/activity instance instead of this 'context'
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
                return true;
            };
        }

        public MenuItem.OnMenuItemClickListener gotoProductGroup()
        {
            return item -> {
                Utils.showToast("ctx_gotoProductGroup is not implemented yet");
                return true;
            };
        }

        public MenuItem.OnMenuItemClickListener addToCart()
        {
            return item -> {
                Utils.showToast("ctx_addToCart is not implemented yet");
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

    }
}
