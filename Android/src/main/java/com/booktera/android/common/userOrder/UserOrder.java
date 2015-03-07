package com.booktera.android.common.userOrder;

import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.booktera.android.BookteraApplication;
import com.booktera.android.R;
import com.booktera.android.common.bookBlock.BookBlock;
import com.booktera.android.common.utils.CurrencyFormatter;
import com.booktera.android.common.utils.Utils;
import com.booktera.androidclientproxy.lib.enums.TransactionType;
import com.booktera.androidclientproxy.lib.enums.UserOrderStatus;
import com.booktera.androidclientproxy.lib.models.ProductModels.InBookBlockPVM;
import com.booktera.androidclientproxy.lib.models.UserOrderPLVM;

import java.util.List;

/**
 * Created by Norbert on 2015.03.06..
 */
public class UserOrder
{
    private static final String tag = UserOrder.class.toString();
    private static final Resources r = BookteraApplication.getAppResources();

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
        public LinearLayout booksContainer;
        public LinearLayout exchangeBooksContainer;
        public LinearLayout statusPanel;
        public LinearLayout feedbackPanel;
        public LinearLayout root;
        public LinearLayout exchangeBooksPanel;

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
            booksContainer = (LinearLayout) view.findViewById(R.id.userOrder_books_container);
            exchangeBooksContainer = (LinearLayout) view.findViewById(R.id.userOrder_exchangeBooks_container);
            statusPanel = (LinearLayout) view.findViewById(R.id.userOrder_status_panel);
            feedbackPanel = (LinearLayout) view.findViewById(R.id.userOrder_feedbackPanel);
            root = (LinearLayout) view.findViewById(R.id.userOrder_root);
            exchangeBooksPanel = (LinearLayout) view.findViewById(R.id.userOrder_exchangeBooksPanel);
        }
    }

    //endregion

    private UserOrderPLVM plvm;
    private ViewHolder vh;
    private View userOrderView;
    private FragmentActivity activity;

    //private CtxMenuClickListeners ctxMenuClickListeners = new CtxMenuClickListeners();

    public UserOrder(UserOrderPLVM plvm, View userOrderView, FragmentActivity activity)
    {
        this(plvm, new UserOrder.ViewHolder(userOrderView), userOrderView, activity);
    }
    public UserOrder(UserOrderPLVM plvm, ViewHolder vh, View userOrderView, FragmentActivity activity)
    {
        this.plvm = plvm;
        this.vh = vh;
        this.userOrderView = userOrderView;
        this.activity = activity;
    }

    public void fill()
    {
        // Shortcuts, aliases
        UserOrderPLVM.UserOrderVM uo = plvm.getUserOrder();
        TransactionType tt = plvm.getTransactionType();
        String format;

        // Calc
        int sumCustomerFee = (int) (uo.getSumBookPrice() * uo.getCustomersFeePercent() / 100.0);
        int sumVendorFee = (int) (uo.getSumBookPrice() * uo.getVendorsFeePercent() / 100.0);
        int sumCustomerOrderAmount = sumCustomerFee + uo.getSumBookPrice();

        // --

        // Color
        setOrderColor(uo, tt);

        // sumCustomerOrderAmount
        vh.sumCustomerOrderAmount.setText(CurrencyFormatter.Instance.format(sumCustomerOrderAmount));

        // sumCustomerFee
        format = r.getString(R.string.userOrder_sumCustomerFee_format);
        vh.sumCustomerFee.setText(String.format(format, sumCustomerFee));

        // customersFeePercent
        format = r.getString(R.string.userOrder_customersFeePercent_format);
        vh.customersFeePercent.setText(String.format(format, uo.getCustomersFeePercent()));

        // Title of cart/transaction
        if (!Utils.isNullOrEmpty(uo.getVendorName()))
        {
            vh.vendorName.setText(uo.getVendorName());
            vh.customerName.setVisibility(View.GONE);
        }
        else
        {
            vh.customerName.setText(uo.getCustomerName());
            vh.vendorName.setVisibility(View.GONE);
        }

        // Status
        if (isStatusPanelVisible(uo.getStatus()))
        {
            vh.statusPanel.setVisibility(View.VISIBLE);
            vh.status.setText(uo.getStatus().toDescriptionString());
        }
        else
        {
            vh.statusPanel.setVisibility(View.GONE);
        }

        // Feedback (in finished transactions)
        if (uo.getStatus() == UserOrderStatus.Finished)
        {
            if (tt == TransactionType.EarlierBuys)
            {
                vh.feedbackCustomer_label.setText(r.getString(R.string.Your_feedback_));
                vh.feedbackVendor_label.setText(r.getString(R.string.The_vendors_feedback_));
            }
            else if (tt == TransactionType.InProgressSells)
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

        // Products in cart
        if (!plvm.getProducts().isEmpty())
            inflateBookBlocks(plvm.getProducts(), /*isExchange*/false, vh.booksContainer);

        // Products in exchange cart
        if (!plvm.getExchangeProducts().isEmpty())
        {
            vh.exchangeBooksPanel.setVisibility(View.VISIBLE);
            inflateBookBlocks(plvm.getExchangeProducts(), /*isExchange*/true, vh.exchangeBooksContainer);
        }
    }
    private void inflateBookBlocks(List<InBookBlockPVM> products, boolean isExchange, ViewGroup container)
    {
        container.removeAllViews();

        for (InBookBlockPVM inBookBlockPVM : products)
        {
            LayoutInflater inflater = LayoutInflater.from(activity);
            View bookBlockView = inflater.inflate(R.layout.row_book_block, null);
            BookBlock bookBlock = new BookBlock(new BookBlock.CtorArgs(
                inBookBlockPVM, /*ViewHolder*/null, bookBlockView, activity, isExchange,
                plvm.getUserOrder(), /*isAddToExchangeCartPossible*/ false
            ));
            bookBlock.fill();
            bookBlock.setupContextMenu();

            container.addView(bookBlockView);
        }
    }
    private void setOrderColor(UserOrderPLVM.UserOrderVM userOrder, TransactionType transactionType)
    {
        int colorId;

        switch (userOrder.getStatus())
        {
            case Cart:
                if (transactionType == TransactionType.Carts)
                    colorId = R.color.Green;
                else //if (transactionType == TransactionType.InCartsByOthers)
                    colorId = R.color.Gray;
                break;

            case BuyedWaiting:
                if (transactionType == TransactionType.InProgressSells)
                    colorId = R.color.Green;
                else //if (transactionType == TransactionType.InProgressBuys)
                    colorId = R.color.Gray;
                break;

            case BuyedExchangeOffered:
                if (transactionType == TransactionType.InProgressBuys)
                    colorId = R.color.Green;
                else //if (transactionType == TransactionType.InProgressSells)
                    colorId = R.color.Gray;
                break;

            case FinalizedCash:
            case FinalizedExchange:
                colorId = R.color.Green;
                break;

            case Finished:
                colorId = CalcFinishedColor(userOrder);
                break;

            case CartDeleting:
            case Nincs:
            default:
                colorId = R.color.Gray;
                break;
        }

        vh.root.setBackgroundColor(r.getColor(colorId));
    }

    private static int CalcFinishedColor(UserOrderPLVM.UserOrderVM userOrder)
    {
        Boolean vendorOk = userOrder.getVendorFeedbackedSuccessful();
        Boolean customerOk = userOrder.getCustomerFeedbackedSuccessful();

        if (vendorOk == null)
        {
            if (customerOk == null)
                return R.color.Gray;
            else if (customerOk)
                return R.color.UserOrder_HalfGreen;
            else //if (!customerOk)
                return R.color.Red;
        }
        else if (vendorOk)
        {
            if (customerOk == null)
                return R.color.UserOrder_HalfGreen;
            else if (customerOk)
                return R.color.UserOrder_LightGreen;
            else //if (!customerOk)
                return R.color.Red;
        }
        else //if (!vendorOk)
        {
            return R.color.Red;
        }
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
                return false;

            // dummies
            case CartDeleting:
            case Nincs:
            default:
                return false;
        }
    }
    //todo ctx menu for transactions
    public void setupContextMenu()
    {
        userOrderView.setOnCreateContextMenuListener((menu, v, menuInfo) ->
        {
            // -- Inflating
            MenuInflater inflater = new MenuInflater(activity);
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
