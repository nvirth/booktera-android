package com.booktera.android.common.userOrder;

import android.content.Context;
import android.content.res.Resources;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.booktera.android.BookteraApplication;
import com.booktera.android.R;
import com.booktera.android.common.utils.CurrencyFormatter;
import com.booktera.android.common.utils.Utils;
import com.booktera.androidclientproxy.lib.enums.TransactionType;
import com.booktera.androidclientproxy.lib.enums.UserOrderStatus;
import com.booktera.androidclientproxy.lib.models.UserOrderPLVM;
import com.booktera.androidclientproxy.lib.utils.Action_1;
import com.booktera.androidclientproxy.lib.utils.Func_1;

/**
 * Created by Norbert on 2015.03.06..
 */
//todo UserOrderArrayAdapter
public class UserOrder
{
    private static final String tag = UserOrder.class.toString();

    //region ViewHolder

    public static class ViewHolder
    {
        public TextView vendorName;
        public TextView customerName;
        public TextView sumCustomerOrderAmount;
        public TextView sumCustomerFee;
        public TextView customersFeePercent;
        public TextView status;
        public TextView feedbackCustomer_label;
        public TextView feedbackCustomer_value;
        public TextView feedbackVendor_label;
        public TextView feedbackVendor_value;
        public LinearLayout books_fragmentContainer;
        public LinearLayout exchangeBooks_fragmentContainer;
        public LinearLayout statusPanel;
        public LinearLayout feedbackPanel;

        public ViewHolder(View view)
        {
            vendorName = (TextView) view.findViewById(R.id.userOrder_vendorName);
            customerName = (TextView) view.findViewById(R.id.userOrder_customerName);
            sumCustomerOrderAmount = (TextView) view.findViewById(R.id.userOrder_sumCustomerOrderAmount);
            sumCustomerFee = (TextView) view.findViewById(R.id.userOrder_sumCustomerFee);
            customersFeePercent = (TextView) view.findViewById(R.id.userOrder_customersFeePercent);
            status = (TextView) view.findViewById(R.id.userOrder_status);
            feedbackCustomer_label = (TextView) view.findViewById(R.id.userOrder_feedbackCustomer_label);
            feedbackCustomer_value = (TextView) view.findViewById(R.id.userOrder_feedbackCustomer_value);
            feedbackVendor_label = (TextView) view.findViewById(R.id.userOrder_feedbackVendor_label);
            feedbackVendor_value = (TextView) view.findViewById(R.id.userOrder_feedbackVendor_value);
            books_fragmentContainer = (LinearLayout) view.findViewById(R.id.userOrder_books_fragmentContainer);
            exchangeBooks_fragmentContainer = (LinearLayout) view.findViewById(R.id.userOrder_exchangeBooks_fragmentContainer);
            statusPanel = (LinearLayout) view.findViewById(R.id.userOrder_status_panel);
            feedbackPanel = (LinearLayout) view.findViewById(R.id.userOrder_feedbackPanel);
        }
    }

    //endregion

    private UserOrderPLVM plvm;
    private ViewHolder vh;
    private View bookBlockView;
    private Context context;

    //private CtxMenuClickListeners ctxMenuClickListeners = new CtxMenuClickListeners();

    public UserOrder(UserOrderPLVM plvm, View bookBlockView, Context context)
    {
        this(plvm, new UserOrder.ViewHolder(bookBlockView), bookBlockView, context);
    }
    public UserOrder(UserOrderPLVM plvm, ViewHolder vh, View bookBlockView, Context context)
    {
        this.plvm = plvm;
        this.vh = vh;
        this.bookBlockView = bookBlockView;
        this.context = context;
    }

    public void fill()
    {
        // Shortcuts, aliases
        UserOrderPLVM.UserOrderVM uo = plvm.getUserOrder();
        TransactionType tt = plvm.getTransactionType();
        Resources r = BookteraApplication.getAppResources();
        String format;

        // Calc
        int sumCustomerFee = (int) (uo.getSumBookPrice() * uo.getCustomersFeePercent() / 100.0);
        int sumVendorFee = (int) (uo.getSumBookPrice() * uo.getVendorsFeePercent() / 100.0);
        int sumCustomerOrderAmount = sumCustomerFee + uo.getSumBookPrice();

        // Only one of these has value at a time
        if (!Utils.isNullOrEmpty(uo.getVendorName()))
            vh.vendorName.setText(uo.getVendorName());
        else
            vh.customerName.setText(uo.getCustomerName());

        vh.sumCustomerOrderAmount.setText(CurrencyFormatter.Instance.format(sumCustomerOrderAmount));
        format = r.getString(R.string.userOrder_sumCustomerFee_format);
        vh.sumCustomerFee.setText(String.format(format, sumCustomerFee));
        format = r.getString(R.string.userOrder_customersFeePercent_format);
        vh.customersFeePercent.setText(String.format(format, uo.getCustomersFeePercent()));

        if (isStatusPanelVisible(uo.getStatus()))
        {
            vh.statusPanel.setVisibility(View.VISIBLE);
            vh.status.setText(uo.getStatus().toDescriptionString());
        }
        else
        {
            vh.statusPanel.setVisibility(View.GONE);
        }

        if (uo.getStatus() == UserOrderStatus.Finished)
        {
            if (tt == TransactionType.FinishedOrderOwn)
            {
                vh.feedbackCustomer_label.setText(r.getString(R.string.Your_feedback_));
                vh.feedbackVendor_label.setText(r.getString(R.string.The_vendors_feedback_));

            }
            else if (tt == TransactionType.FinishedOrderOthers)
            {
                vh.feedbackCustomer_label.setText(r.getString(R.string.The_customers_feedback_));
                vh.feedbackVendor_label.setText(r.getString(R.string.Your_feedback_));
            }

            vh.feedbackCustomer_value.setText(feedbackToStr(uo.getCustomerFeedbackedSuccessful()));
            vh.feedbackVendor_value.setText(feedbackToStr(uo.getVendorFeedbackedSuccessful()));

            vh.feedbackPanel.setVisibility(View.VISIBLE);
        }
        else
        {
            vh.feedbackPanel.setVisibility(View.GONE);
        }
//todo I stopped here
//vh.books_fragmentContainer;
//vh.exchangeBooks_fragmentContainer;
    }
    private static String feedbackToStr(Boolean isFeedbackSuccessful)
    {
        if (isFeedbackSuccessful == null)
            return "-";
        if (isFeedbackSuccessful)
            return "Sikeres";
        else
            return "Sikertelen";
    }
    private boolean isStatusPanelVisible(UserOrderStatus userOrderStatus)
    {
        switch (userOrderStatus)
        {
            case BuyedWaiting:
            case BuyedExchangeOffered:
            case FinalizedExchange:
            case FinalizedCash:
                return true;

            case Cart:
            case Finished:
                // dummies
            case CartDeleting:
            case Nincs:
            default:
                return false;
        }
    }

    public void setupContextMenu()
    {
        bookBlockView.setOnCreateContextMenuListener((menu, v, menuInfo) ->
        {
            // -- Inflating
            MenuInflater inflater = new MenuInflater(context);
            inflater.inflate(R.menu.ctx_bookblock, menu);

//            // -- Flags
//            String titleFormat;
//            boolean isQuantity_gt_0 = plvm.getProduct().getHowMany() > 0;
//            boolean isUserAuthenticated = UserData.Instace.isAuthenticated();
//            boolean isProduct = !Utils.isNullOrEmpty(plvm.getProduct().getDescription());
//            String productOwnerName = Utils.ifNull(plvm.getProduct().getUserName(), "").toLowerCase();
//            boolean isOwnBook = productOwnerName.equals(UserData.Instace.getUserNameLowerCase());
//
//            UserOrderPLVM.UserOrderVM userOrderVm = null;
//            boolean isAddToExchangeCartPossible = false;
//            boolean isInCart = false;
//            boolean isExchangeProduct = false;
//            boolean existsCustomerName = false;
//            boolean existsVendorName = false;
//            BookBlockDataHolder.UserOrder userOrderPLVM = dataHolder instanceof BookBlockDataHolder.UserOrder
//                ? (BookBlockDataHolder.UserOrder) dataHolder
//                : null;
//            if (userOrderPLVM != null)
//            {
//                isAddToExchangeCartPossible = userOrderPLVM.isAddToExchangeCartPossible;
//                isExchangeProduct = vm == userOrderPLVM.exchangeProducts.get(userOrderPLVM.actualPosition);
//
//                userOrderVm = userOrderPLVM.userOrder;
//                if (userOrderVm != null)
//                {
//                    isInCart = userOrderVm.getStatus() == UserOrderStatus.Cart;
//                    existsCustomerName = !Utils.isNullOrEmpty(userOrderVm.getCustomerName());
//                    existsVendorName = !Utils.isNullOrEmpty(userOrderVm.getVendorName());
//                }
//            }
//            boolean isInUserOrder = userOrderVm != null;
//
//            //-- MenuItems
//
//            //UsersProducts
//            if (!Utils.isNullOrEmpty(vh.userName.getText()))
//            {
//                MenuItem ctxUsersProducts = menu.findItem(R.id.bookBlockCtx_gotoUsersProducts);
//                titleFormat = ctxUsersProducts.getTitle().toString();
//                ctxUsersProducts.setTitle(String.format(titleFormat, vh.userName.getText()));
//                enable(ctxUsersProducts);
//            }
//
//            //ProductGroup
//            if (!Utils.isNullOrEmpty(vh.title.getText()))
//            {
//                MenuItem ctxProductGroup = menu.findItem(R.id.bookBlockCtx_gotoProductGroup);
//                titleFormat = ctxProductGroup.getTitle().toString();
//                ctxProductGroup.setTitle(String.format(titleFormat, vh.title.getText()));
//                enable(ctxProductGroup);
//            }
//
//            //AddToCart
//            if (isQuantity_gt_0 && isUserAuthenticated && isProduct && !isOwnBook && !isInUserOrder)
//                enable(R.id.bookBlockCtx_addToCart, menu);
//
//            //AddToExchangeCart
//            if (isQuantity_gt_0 && isUserAuthenticated && isProduct && !isInUserOrder && isAddToExchangeCartPossible)
//                enable(R.id.bookBlockCtx_addToExchangeCart, menu);
//
//            //RemoveFromCart, ChangeQuantity (in cart)
//            if (isInCart && !existsCustomerName && existsVendorName) // isOwnCartItem
//            {
//                enable(R.id.bookBlockCtx_removeFromCart, menu);
//                enable(R.id.bookBlockCtx_changeQuantityInCart, menu);
//            }
//
//            //RemoveFromExchangeCart, ChangeQuantity (in exchange cart)
//            if (isExchangeProduct && isInUserOrder && userOrderVm.getStatus() == UserOrderStatus.BuyedWaiting)
//            {
//                enable(R.id.bookBlockCtx_removeFromExchangeCart, menu);
//                enable(R.id.bookBlockCtx_changeQuantityInExchangeCart, menu);
//            }
        });
    }
//
//    public void enable(@IdRes int menuItemID, ContextMenu menu)
//    {
//        enable(menu.findItem(menuItemID));
//    }
//    public void enable(MenuItem menuItem)
//    {
//        menuItem.setVisible(true);
//        menuItem.setEnabled(true);
//        menuItem.setOnMenuItemClickListener(fetchClickListener(menuItem));
//    }
//    public MenuItem.OnMenuItemClickListener fetchClickListener(MenuItem menuItem)
//    {
//        switch (menuItem.getItemId())
//        {
//            case R.id.bookBlockCtx_gotoUsersProducts:
//                return ctxMenuClickListeners.gotoUsersProducts();
//            case R.id.bookBlockCtx_gotoProductGroup:
//                return ctxMenuClickListeners.gotoProductGroup();
//            case R.id.bookBlockCtx_addToCart:
//                return ctxMenuClickListeners.addToCart();
//            case R.id.bookBlockCtx_addToExchangeCart:
//                return ctxMenuClickListeners.addToExchangeCart();
//            case R.id.bookBlockCtx_removeFromCart:
//                return ctxMenuClickListeners.removeFromCart();
//            case R.id.bookBlockCtx_changeQuantityInCart:
//                return ctxMenuClickListeners.changeQuantityInCart();
//            case R.id.bookBlockCtx_changeQuantityInExchangeCart:
//                return ctxMenuClickListeners.changeQuantityInExchangeCart();
//            case R.id.bookBlockCtx_removeFromExchangeCart:
//                return ctxMenuClickListeners.removeFromExchangeCart();
//
//            default:
//                Utils.error("Unrecognised MenuItemID: " + menuItem.getItemId(), tag);
//                return null; // unreachable
//        }
//    }
//
//    class CtxMenuClickListeners
//    {
//        public MenuItem.OnMenuItemClickListener gotoUsersProducts()
//        {
//            return item -> {
//                Intent intent = new Intent(BookteraApplication.getAppContext(), UsersProductsActivity.class);
//                intent.putExtra(Constants.PARAM_USER_FU, plvm.getProduct().getUserFriendlyUrl());
//                //TODO check if it's ok, or should we use the fragment/activity instance instead of this 'context'
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                context.startActivity(intent);
//                return true;
//            };
//        }
//
//        public MenuItem.OnMenuItemClickListener gotoProductGroup()
//        {
//            return item -> {
//                Intent intent = new Intent(BookteraApplication.getAppContext(), ProductGroupDetailsActivity.class);
//                intent.putExtra(Constants.PARAM_PRODUCT_GROUP_FU, plvm.getProductGroup().getFriendlyUrl());
//                //TODO check if it's ok, or should we use the fragment/activity instance instead of this 'context'
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                context.startActivity(intent);
//                return true;
//            };
//        }
//
//        public MenuItem.OnMenuItemClickListener addToCart()
//        {
//            return item -> {
//                Utils.showToast("ctx_addToCart is not implemented yet");
//                return true;
//            };
//        }
//
//        public MenuItem.OnMenuItemClickListener addToExchangeCart()
//        {
//            return item -> {
//                Utils.showToast("ctx_addToExchangeCart is not implemented yet");
//                return true;
//            };
//        }
//
//        public MenuItem.OnMenuItemClickListener removeFromCart()
//        {
//            return item -> {
//                Utils.showToast("ctx_removeFromCart is not implemented yet");
//                return true;
//            };
//        }
//
//        public MenuItem.OnMenuItemClickListener changeQuantityInCart()
//        {
//            return item -> {
//                Utils.showToast("ctx_changeQuantityInCart is not implemented yet");
//                return true;
//            };
//        }
//
//        public MenuItem.OnMenuItemClickListener changeQuantityInExchangeCart()
//        {
//            return item -> {
//                Utils.showToast("ctx_changeQuantityInExchangeCart is not implemented yet");
//                return true;
//            };
//        }
//
//        public MenuItem.OnMenuItemClickListener removeFromExchangeCart()
//        {
//            return item -> {
//                Utils.showToast("ctx_removeFromExchangeCart is not implemented yet");
//                return true;
//            };
//        }

    //}
}
