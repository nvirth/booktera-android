package com.booktera.android.common.userOrder;

import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.booktera.android.BookteraApplication;
import com.booktera.android.R;
import com.booktera.android.common.CtxMenuBase;
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
public class UserOrder extends CtxMenuBase
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

    private CtxMenuClickListeners ctxMenuClickListeners = new CtxMenuClickListeners();

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

    //region fill -helpers
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
    //endregion

    @Override
    public void setupContextMenu()
    {
        userOrderView.setOnCreateContextMenuListener((menu, v, menuInfo) ->
        {
            // -- Inflating
            MenuInflater inflater = new MenuInflater(activity);
            inflater.inflate(R.menu.ctx_bookblock, menu);

            // -- Shortcuts
            UserOrderPLVM.UserOrderVM uo = plvm.getUserOrder();
            TransactionType tt = plvm.getTransactionType();
            String titleFormat;

            // -- Flags, Data
            String customerOrVendorName = Utils.ifNullOrEmpty(uo.getVendorName(), uo.getCustomerName());
            boolean isCart = uo.getStatus() == UserOrderStatus.Cart;
            boolean existsCustomerName = !Utils.isNullOrEmpty(uo.getCustomerName());
            boolean existsVendorName = !Utils.isNullOrEmpty(uo.getVendorName());
            boolean isOwnCart = isCart && existsCustomerName && !existsVendorName;
            boolean existExchangeProducts = !plvm.getExchangeProducts().isEmpty();
            boolean isStatus_BuyedExchangeOffered = uo.getStatus() == UserOrderStatus.BuyedExchangeOffered;
            boolean isFeedbackEnabled = isFeedbackEnabled(uo, tt);
            boolean isInProgressSell_buyedWaiting = uo.getStatus() == UserOrderStatus.BuyedWaiting
                && tt == TransactionType.InProgressSells;

            // -- Ctx menu items
            //UsersProducts (always visible)
            MenuItem ctxUsersProducts = menu.findItem(R.id.userOrderCtx_gotoUsersProducts);
            titleFormat = ctxUsersProducts.getTitle().toString();
            ctxUsersProducts.setTitle(String.format(titleFormat, customerOrVendorName));
            enable(ctxUsersProducts);

            //RemoveThisCart
            if (isOwnCart)
            {
                MenuItem ctxRemoveThisCart = menu.findItem(R.id.userOrderCtx_removeThisCart);
                titleFormat = ctxRemoveThisCart.getTitle().toString();
                ctxRemoveThisCart.setTitle(String.format(titleFormat, customerOrVendorName));
                enable(ctxRemoveThisCart);
            }

            //RemoveAllCarts
            if (isOwnCart)
            {
                MenuItem ctxRemoveAllCarts = menu.findItem(R.id.userOrderCtx_removeAllCarts);
                enable(ctxRemoveAllCarts);
            }

            //SendOrder
            if (isOwnCart)
            {
                MenuItem ctxSendOrder = menu.findItem(R.id.userOrderCtx_sendOrder);
                titleFormat = ctxSendOrder.getTitle().toString();
                ctxSendOrder.setTitle(String.format(titleFormat, customerOrVendorName));
                enable(ctxSendOrder);
            }

            //AddExchangeProduct
            if (isInProgressSell_buyedWaiting)
            {
                MenuItem ctxAddExchangeProduct = menu.findItem(R.id.userOrderCtx_addExchangeProduct);
                enable(ctxAddExchangeProduct);
            }

            //RemoveExchangeCart
            if (isInProgressSell_buyedWaiting && existExchangeProducts)
            {
                MenuItem ctxRemoveExchangeCart = menu.findItem(R.id.userOrderCtx_removeExchangeCart);
                enable(ctxRemoveExchangeCart);
            }

            //SendExchangeOffer
            if (isInProgressSell_buyedWaiting && existExchangeProducts)
            {
                MenuItem ctxSendExchangeOffer = menu.findItem(R.id.userOrderCtx_sendExchangeOffer);
                enable(ctxSendExchangeOffer);
            }

            //FinalizeOrder_WithoutExchange
            if (isInProgressSell_buyedWaiting)
            {
                MenuItem ctxFinalizeOrder_WithoutExchange = menu.findItem(R.id.userOrderCtx_finalizeOrder_WithoutExchange);
                enable(ctxFinalizeOrder_WithoutExchange);
            }

            //FinalizeOrder_AcceptExchange
            if (isStatus_BuyedExchangeOffered)
            {
                MenuItem ctxFinalizeOrder_AcceptExchange = menu.findItem(R.id.userOrderCtx_finalizeOrder_AcceptExchange);
                enable(ctxFinalizeOrder_AcceptExchange);
            }

            //FinalizeOrder_DenyExchange
            if (isStatus_BuyedExchangeOffered)
            {
                MenuItem ctxFinalizeOrder_DenyExchange = menu.findItem(R.id.userOrderCtx_finalizeOrder_DenyExchange);
                enable(ctxFinalizeOrder_DenyExchange);
            }

            //CloseOrder_Successful
            if (isFeedbackEnabled)
            {
                MenuItem ctxCloseOrder_Successful = menu.findItem(R.id.userOrderCtx_closeOrder_Successful);
                enable(ctxCloseOrder_Successful);
            }

            //CloseOrder_Unsuccessful
            if (isFeedbackEnabled)
            {
                MenuItem ctxCloseOrder_Unsuccessful = menu.findItem(R.id.userOrderCtx_closeOrder_Unsuccessful);
                enable(ctxCloseOrder_Unsuccessful);
            }
        });
    }

    //region Context menu -helpers
    public boolean isFeedbackEnabled(UserOrderPLVM.UserOrderVM uo, TransactionType tt)
    {
        // InProgress
        if (uo.getStatus() == UserOrderStatus.FinalizedCash
            || uo.getStatus() == UserOrderStatus.FinalizedExchange)
            return true;

        // Finished
        if (uo.getStatus() == UserOrderStatus.Finished)
        {
            // My buy, and I have not feedbacked yet
            if (tt == TransactionType.EarlierBuys
                && !uo.getCustomerFeedbackedSuccessful())
                return true;

            // My sell, and I have not feedbacked yet
            if (tt == TransactionType.EarlierSells
                && !uo.getVendorFeedbackedSuccessful())
                return true;
        }

        return false;
    }

    @Override
    public MenuItem.OnMenuItemClickListener fetchClickListener(MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.userOrderCtx_gotoUsersProducts:
                return ctxMenuClickListeners.gotoUsersProducts();
            case R.id.userOrderCtx_removeThisCart:
                return ctxMenuClickListeners.removeThisCart();
            case R.id.userOrderCtx_removeAllCarts:
                return ctxMenuClickListeners.removeAllCarts();
            case R.id.userOrderCtx_sendOrder:
                return ctxMenuClickListeners.sendOrder();
            case R.id.userOrderCtx_addExchangeProduct:
                return ctxMenuClickListeners.addExchangeProduct();
            case R.id.userOrderCtx_removeExchangeCart:
                return ctxMenuClickListeners.removeExchangeCart();
            case R.id.userOrderCtx_sendExchangeOffer:
                return ctxMenuClickListeners.sendExchangeOffer();
            case R.id.userOrderCtx_finalizeOrder_WithoutExchange:
                return ctxMenuClickListeners.finalizeOrder_WithoutExchange();
            case R.id.userOrderCtx_finalizeOrder_AcceptExchange:
                return ctxMenuClickListeners.finalizeOrder_AcceptExchange();
            case R.id.userOrderCtx_finalizeOrder_DenyExchange:
                return ctxMenuClickListeners.finalizeOrder_DenyExchange();
            case R.id.userOrderCtx_closeOrder_Successful:
                return ctxMenuClickListeners.closeOrder_Successful();
            case R.id.userOrderCtx_closeOrder_Unsuccessful:
                return ctxMenuClickListeners.closeOrder_Unsuccessful();
            default:
                Utils.error("Unrecognised MenuItemID: " + menuItem.getItemId(), tag);
                return null; // unreachable
        }
    }

    //todo implement UserOrder CtxMenuClickListeners
    class CtxMenuClickListeners
    {
                public MenuItem.OnMenuItemClickListener gotoUsersProducts()
        {
            return item -> {
                Utils.showToast("ctx_gotoUsersProducts is not implemented yet");
                return true;
            };
        }

        public MenuItem.OnMenuItemClickListener removeThisCart()
        {
            return item -> {
                Utils.showToast("ctx_removeThisCart is not implemented yet");
                return true;
            };
        }

        public MenuItem.OnMenuItemClickListener removeAllCarts()
        {
            return item -> {
                Utils.showToast("ctx_removeAllCarts is not implemented yet");
                return true;
            };
        }

        public MenuItem.OnMenuItemClickListener sendOrder()
        {
            return item -> {
                Utils.showToast("ctx_sendOrder is not implemented yet");
                return true;
            };
        }

        public MenuItem.OnMenuItemClickListener addExchangeProduct()
        {
            return item -> {
                Utils.showToast("ctx_addExchangeProduct not implemented yet");
                return true;
            };
        }

        public MenuItem.OnMenuItemClickListener removeExchangeCart()
        {
            return item -> {
                Utils.showToast("ctx_removeExchangeCart not implemented yet");
                return true;
            };
        }

        public MenuItem.OnMenuItemClickListener sendExchangeOffer()
        {
            return item -> {
                Utils.showToast("ctx_sendExchangeOffer is not implemented yet");
                return true;
            };
        }

        public MenuItem.OnMenuItemClickListener finalizeOrder_WithoutExchange()
        {
            return item -> {
                Utils.showToast("ctx_finalizeOrder_WithoutExchange is not implemented yet");
                return true;
            };
        }

        public MenuItem.OnMenuItemClickListener finalizeOrder_AcceptExchange()
        {
            return item -> {
                Utils.showToast("ctx_finalizeOrder_AcceptExchange is not implemented yet");
                return true;
            };
        }

        public MenuItem.OnMenuItemClickListener finalizeOrder_DenyExchange()
        {
            return item -> {
                Utils.showToast("ctx_finalizeOrder_DenyExchange is not implemented yet");
                return true;
            };
        }

        public MenuItem.OnMenuItemClickListener closeOrder_Successful()
        {
            return item -> {
                Utils.showToast("ctx_closeOrder_Successful is not implemented yet");
                return true;
            };
        }

        public MenuItem.OnMenuItemClickListener closeOrder_Unsuccessful()
        {
            return item -> {
                Utils.showToast("ctx_closeOrder_Unsuccessful is not implemented yet");
                return true;
            };
        }
    }
    //endregion
}
