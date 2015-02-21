package com.booktera.android.common.bookBlock;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.booktera.android.R;
import com.booktera.android.common.UserData;
import com.booktera.android.common.utils.Utils;
import com.booktera.androidclientproxy.lib.enums.BookBlockType;
import com.booktera.androidclientproxy.lib.enums.UserOrderStatus;
import com.booktera.androidclientproxy.lib.enums.TransactionType;
import com.booktera.androidclientproxy.lib.models.ProductModels.InBookBlockPVM;
import com.booktera.androidclientproxy.lib.models.UserOrderPLVM;

import java.util.List;

/**
 * Created by Norbert on 2015.02.10..
 */
public class BookBlock
{
    public static final String tag = BookBlock.class.toString();

    public static class DataHolder
    {
        static abstract class Base
        {
            public List<InBookBlockPVM> products;
            public int actualPosition;
        }

        static class Normal extends Base
        {
            public BookBlockType bookBlockType;
        }

        static class UserOrder extends Base
        {
            public TransactionType transactionType;
            public UserOrderPLVM.UserOrderVM userOrder;
            public List<InBookBlockPVM> exchangeProducts;


            /**
             * TODO leírást javítani ha majd aktuális lesz
             * This is a helper for the global context menu in App.xaml. This indicates whenever
             * the product can be added to an Exchange cart. This is possible only, if one is
             * at the SearchPage, and went with the flag 'forExchange' true
             */
            public boolean isAddToExchangeCartPossible;
        }
    }

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

    public static void fill(ViewHolder vh, InBookBlockPVM vm)
    {
        //TODO ellenőrizni h az if-ek jók-e, amikor már lesz megfelelő view
        // E.g. by ProductGroup, we don't have UserName. In this case, we have to tighten the
        // cover at the BookBlockImage's bottom
        if (vm.getProduct().getUserName() != null)
        {
            vh.userName.setText(vm.getProduct().getUserName());
            vh.quantity.setText(vm.getProduct().getHowMany() + " db");
        }
        else
        {
            vh.userName.setVisibility(View.GONE);
        }

        vh.price.setText(vm.getProduct().getPriceString());
        vh.title.setText(vm.getProductGroup().getTitle());
        vh.subTitle.setText(vm.getProductGroup().getSubTitle());
        vh.authorNames.setText(vm.getProductGroup().getAuthorNames());

        if (!vm.getProduct().getIsDownloadable())
            vh.isDownloadable.setVisibility(View.INVISIBLE);

        Utils.setProductImage(vm, vh.bookImage);
    }

    public static void setupContextMenu(View bookBlockView, Context context, ViewHolder vh, InBookBlockPVM vm, DataHolder.Base dataHolder)
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
            DataHolder.UserOrder userOrderPLVM = dataHolder instanceof DataHolder.UserOrder
                ? (DataHolder.UserOrder) dataHolder
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

    public static void enable(@IdRes int menuItemID, ContextMenu menu)
    {
        enable(menu.findItem(menuItemID));
    }
    public static void enable(MenuItem menuItem)
    {
        menuItem.setVisible(true);
        menuItem.setEnabled(true);
        menuItem.setOnMenuItemClickListener(fetchClickListener(menuItem));
    }
    public static MenuItem.OnMenuItemClickListener fetchClickListener(MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.bookBlockCtx_gotoUsersProducts:
                return CtxMenuClickListeners.gotoUsersProducts();
            case R.id.bookBlockCtx_gotoProductGroup:
                return CtxMenuClickListeners.gotoProductGroup();
            case R.id.bookBlockCtx_addToCart:
                return CtxMenuClickListeners.addToCart();
            case R.id.bookBlockCtx_addToExchangeCart:
                return CtxMenuClickListeners.addToExchangeCart();
            case R.id.bookBlockCtx_removeFromCart:
                return CtxMenuClickListeners.removeFromCart();
            case R.id.bookBlockCtx_changeQuantityInCart:
                return CtxMenuClickListeners.changeQuantityInCart();
            case R.id.bookBlockCtx_changeQuantityInExchangeCart:
                return CtxMenuClickListeners.changeQuantityInExchangeCart();
            case R.id.bookBlockCtx_removeFromExchangeCart:
                return CtxMenuClickListeners.removeFromExchangeCart();

            default:
                String msg = "Unrecognised MenuItemID: " + menuItem.getItemId();
                RuntimeException e = new RuntimeException(msg);
                Log.e(tag, msg, e);
                throw e;
        }
    }

    static class CtxMenuClickListeners
    {
        public static MenuItem.OnMenuItemClickListener gotoUsersProducts()
        {
            return item -> {
                Utils.showToast("ctx_gotoUsersProducts is not implemented yet");
                return true;
            };
        }

        public static MenuItem.OnMenuItemClickListener gotoProductGroup()
        {
            return item -> {
                Utils.showToast("ctx_gotoProductGroup is not implemented yet");
                return true;
            };
        }

        public static MenuItem.OnMenuItemClickListener addToCart()
        {
            return item -> {
                Utils.showToast("ctx_addToCart is not implemented yet");
                return true;
            };
        }

        public static MenuItem.OnMenuItemClickListener addToExchangeCart()
        {
            return item -> {
                Utils.showToast("ctx_addToExchangeCart is not implemented yet");
                return true;
            };
        }

        public static MenuItem.OnMenuItemClickListener removeFromCart()
        {
            return item -> {
                Utils.showToast("ctx_removeFromCart is not implemented yet");
                return true;
            };
        }

        public static MenuItem.OnMenuItemClickListener changeQuantityInCart()
        {
            return item -> {
                Utils.showToast("ctx_changeQuantityInCart is not implemented yet");
                return true;
            };
        }

        public static MenuItem.OnMenuItemClickListener changeQuantityInExchangeCart()
        {
            return item -> {
                Utils.showToast("ctx_changeQuantityInExchangeCart is not implemented yet");
                return true;
            };
        }

        public static MenuItem.OnMenuItemClickListener removeFromExchangeCart()
        {
            return item -> {
                Utils.showToast("ctx_removeFromExchangeCart is not implemented yet");
                return true;
            };
        }

    }
}
