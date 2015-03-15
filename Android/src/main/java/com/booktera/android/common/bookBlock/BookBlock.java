package com.booktera.android.common.bookBlock;

import android.app.Activity;
import android.content.Intent;
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
import com.booktera.android.common.models.TransactionVM;
import com.booktera.android.common.utils.Utils;
import com.booktera.androidclientproxy.lib.enums.UserOrderStatus;
import com.booktera.androidclientproxy.lib.models.ProductModels.InBookBlockPVM;
import com.booktera.androidclientproxy.lib.models.UserOrderPLVM;
import com.booktera.androidclientproxy.lib.proxy.Services;
import com.booktera.androidclientproxy.lib.utils.Action;
import com.booktera.androidclientproxy.lib.utils.Action_1;
import com.booktera.androidclientproxy.lib.utils.Action_4;
import org.apache.http.HttpResponse;

/**
 * Created by Norbert on 2015.02.10..
 */
public class BookBlock extends CtxMenuBase
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
    private UserOrderPLVM userOrder;
    private boolean isExchangeProduct;
    private int userOrderId_forExchange;
    private ViewHolder vh;
    private View bookBlockView;
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
            this(vm, vh, bookBlockView, activity, /*isExchangeProduct*/ false, /*userOrder*/null,/*userOrderId_forExchange*/ -1);
        }
        public CtorArgs(InBookBlockPVM vm, View bookBlockView, Activity activity, boolean isExchange)
        {
            this(vm, /*ViewHolder*/null, bookBlockView, activity, isExchange, /*userOrder*/null, /*userOrderId_forExchange*/ -1);
        }
        public CtorArgs(
            InBookBlockPVM vm,
            ViewHolder vh,
            View bookBlockView,
            Activity activity,
            boolean isExchange,
            UserOrderPLVM userOrder,
            int userOrderId_forExchange
        )
        {
            this.isExchange = isExchange;
            this.vm = vm;
            this.vh = vh;
            this.bookBlockView = bookBlockView;
            this.activity = activity;
            this.userOrder = userOrder;
            this.userOrderId_forExchange = userOrderId_forExchange;

            // Both of them couldn't be set
            if ((userOrder != null) && (userOrderId_forExchange > 0))
                Utils.error("BookBlock instantiation error: (userOrder != null) ^ (userOrderId_forExchange > 0)", tag);
        }

        public boolean isExchange;
        public InBookBlockPVM vm;
        public ViewHolder vh;
        public View bookBlockView;
        public Activity activity;
        public UserOrderPLVM userOrder;
        public int userOrderId_forExchange;
    }

    public BookBlock(CtorArgs args)
    {
        super(args.activity);

        this.vm = args.vm;
        this.bookBlockView = args.bookBlockView;
        this.isExchangeProduct = args.isExchange;
        this.userOrder = args.userOrder;
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

            UserOrderPLVM.UserOrderVM userOrderVm = null;
            boolean isInUserOrder = userOrder != null;
            if (isInUserOrder)
            {
                userOrderVm = userOrder.getUserOrder();
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
                Services.TransactionManager.addProductToCart(
                    vm.getProduct().getID(),
                    () /*success*/ -> activity.runOnUiThread(() -> {
                        Utils.showToast(r.getString(R.string.addToCart_successMsg),/*isLong*/ true);
                        // Refresh cached data
                        decrementQuantity();
                        TransactionVM.Instance.invalidateCartsCache(); // (unnecessary at this time, 2015.03.14)
                        // Refresh view
                        fill();
                    }),
                    httpResponse /*failure*/ -> activity.runOnUiThread(() -> {
                        if (httpResponse == null)
                        {
                            Utils.showToast(r.getString(R.string.default_connection_error_msg));
                            return;
                        }

                        String _title = r.getString(R.string.Error_);
                        String _errMsg = vm.getProduct().getIsDownloadable()
                            ? r.getString(R.string.addToCart_failureMsg_electronicBook)
                            : r.getString(R.string.addToCart_failureMsg_general);

                        Utils.alert(activity, _title, _errMsg);
                    }));

                return true;
            };
        }

        public MenuItem.OnMenuItemClickListener addToExchangeCart()
        {
            return item -> {
                Services.TransactionManager.addExchangeProduct(
                    vm.getProduct().getID(),
                    userOrderId_forExchange,
                    () /*success*/ -> activity.runOnUiThread(() -> {
                        Utils.showToast(r.getString(R.string.addToExchangeCart_successMsg),/*isLong*/ true);
                        // Refresh cached data
                        decrementQuantity();
                        TransactionVM.Instance.invalidateInProgressSellsCache();
                        // Refresh view
                        fill();
                    }),
                    httpResponse /*failure*/ -> activity.runOnUiThread(() -> {
                        if (httpResponse == null)
                        {
                            Utils.showToast(r.getString(R.string.default_connection_error_msg));
                            return;
                        }

                        String _title = r.getString(R.string.Error_);
                        String _errMsg = vm.getProduct().getIsDownloadable()
                            ? r.getString(R.string.addToExchangeCart_failureMsg_electronicBook)
                            : r.getString(R.string.addToExchangeCart_failureMsg_general);

                        Utils.alert(activity, _title, _errMsg);
                    }));

                return true;
            };
        }

        public MenuItem.OnMenuItemClickListener removeFromCart()
        {
            return item -> {
                removeFromCart_core();
                return true;
            };
        }
        private void removeFromCart_core()
        {
            handleCtxClick_core(
                vm.getProduct().getProductInOrderId(),
                String.format(r.getString(R.string.removeFromCart_alertMsgFormat), vm.getProductGroup().getTitle()),
                r.getString(R.string.removeFromCart_successMsg),
                r.getString(R.string.removeFromCart_failureMsg),
                Services.TransactionManager::removeProductFromCart,
                () -> /*refreshViewAfterSuccess*/{
                    userOrder.getProducts().remove(vm);
                    if (userOrder.getProducts().isEmpty())
                    {
                        // Remove the transaction, because it's empty
                        TransactionVM.Instance.getCarts().remove(userOrder);
                        TransactionVM.Instance.onCartBecameEmpty(userOrder);
                    }
                    else
                    {
                        refreshQuantity(/*newQuantity*/ 0,/*refreshSummary*/ true);
                        TransactionVM.Instance.onBookRemovedFromCart(userOrder);
                    }
                }
            );
        }

        public MenuItem.OnMenuItemClickListener removeFromExchangeCart()
        {
            return item -> {
                removeFromExchangeCart_core();
                return true;
            };
        }
        private void removeFromExchangeCart_core()
        {
            handleCtxClick_core(
                vm.getProduct().getProductInOrderId(),
                String.format(r.getString(R.string.removeFromExchangeCart_alertMsgFormat), vm.getProductGroup().getTitle()),
                r.getString(R.string.removeFromExchangeCart_successMsg),
                r.getString(R.string.removeFromExchangeCart_failureMsg),
                Services.TransactionManager::removeExchangeProduct,
                () -> /*refreshViewAfterSuccess*/{
                    userOrder.getExchangeProducts().remove(vm);
                    TransactionVM.Instance.onBookRemovedFromExchangeCart(userOrder);
                }
            );
        }

        public MenuItem.OnMenuItemClickListener changeQuantityInCart()
        {
            return item -> {
                changeQuantity("", /*isExchange*/ false);
                return true;
            };
        }

        public MenuItem.OnMenuItemClickListener changeQuantityInExchangeCart()
        {
            return item -> {
                changeQuantity("", /*isExchange*/ true);
                return true;
            };
        }

        private void changeQuantity(String errorMsg, boolean isExchange)
        {
            errorMsg = Utils.ifNull(errorMsg, "");
            String body = String.format(r.getString(R.string.changeQuantity_promptBodyMsgFormat),
                errorMsg, vm.getProductGroup().getTitle());

            Utils.prompt(
                activity,
                r.getString(R.string.changeQuantity_titleMsg),
                body,
                /*isNumeric*/ true,
                /*negativeClick*/ null,
                (_1, _2, userInputStr) -> /*positiveClick*/{
                    Integer newQuantity = Utils.tryParse(userInputStr);
                    if (newQuantity == null)
                    {
                        changeQuantity(r.getString(R.string.changeQuantity_errorMsg_NaN), isExchange);
                        return;
                    }
                    if (newQuantity < 0)
                    {
                        changeQuantity(r.getString(R.string.changeQuantity_errorMsg_Negative), isExchange);
                        return;
                    }
                    if (newQuantity == 0)
                    {
                        if (isExchange)
                            removeFromExchangeCart_core();
                        else
                            removeFromCart_core();
                        return;
                    }
                    if (newQuantity == vm.getProduct().getHowMany())
                    {
                        Utils.showToast(r.getString(R.string.changeQuantity_notChangedMsg),/*isLong*/true);
                        return;
                    }
                    if (vm.getProduct().getIsDownloadable())// newQuantity==0 already handled
                    {
                        Utils.showToast(r.getString(R.string.changeQuantity_notChangedMsg_Electronic),/*isLong*/true);
                        return;
                    }

                    Action_4<Integer, Integer, Action, Action_1<HttpResponse>> updateService =
                        isExchange
                            ? Services.TransactionManager::updateExchangeProduct
                            : Services.TransactionManager::updateProductInCart;

                    updateService.run(
                        vm.getProduct().getProductInOrderId(),
                        newQuantity,
                        () -> /*success*/ activity.runOnUiThread(() -> {
                            Utils.showToast(r.getString(R.string.changeQuantity_successMsg),/*isLong*/true);
                            refreshQuantity(newQuantity, !isExchange);
                        }),
                        httpResponse -> /*failure*/ activity.runOnUiThread(() -> {
                            if (httpResponse == null)
                                Utils.showToast(r.getString(R.string.default_connection_error_msg));
                            else
                                Utils.alert(activity, r.getString(R.string.Error_), r.getString(R.string.changeQuantity_errorMsg));
                        })
                    );
                }
            );
        }

        /**
         * Not in cart
         */
        private void decrementQuantity()
        {
            // Electronic product's quantity is always 1
            if (!vm.getProduct().getIsDownloadable())
                vm.getProduct().setHowMany(vm.getProduct().getHowMany() - 1);
        }

        /**
         * Refreshes the quantity of this BookBlock.
         * Used in cart/exchange_cart
         *
         * @param refreshSummary If true, refreshes the UserOrder's summary.
         *                       Set it true if the BookBlock is in cart
         */
        private void refreshQuantity(int newQuantity, boolean refreshSummary)
        {
            // Get out all non-numeric characters, then parse it
            Integer price = Integer.valueOf(vm.getProduct().getPriceString().replaceAll("\\D", ""));

            int oldQuantity = vm.getProduct().getHowMany();

            if (!vm.getProduct().getIsDownloadable()) // Electronic product's quantity is always 1
                vm.getProduct().setHowMany(newQuantity);

            if (refreshSummary)
            {
                int oldSum = userOrder.getUserOrder().getSumBookPrice();
                int newSum = oldSum + (newQuantity - oldQuantity) * price;
                userOrder.getUserOrder().setSumBookPrice(newSum);

                TransactionVM.Instance.onSummaryChanged(userOrder);
            }

            // Refresh the view
            fill();
        }
    }
}
