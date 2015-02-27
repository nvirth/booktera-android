package com.booktera.android.common.models;

import com.booktera.android.common.Constants;
import com.booktera.android.common.models.base.MapCache;
import com.booktera.android.common.utils.Utils;
import com.booktera.androidclientproxy.lib.enums.BookBlockType;
import com.booktera.androidclientproxy.lib.models.ProductModels.BookBlockPLVM;
import com.booktera.androidclientproxy.lib.models.ProductModels.BookRowPLVM;
import com.booktera.androidclientproxy.lib.models.ProductModels.InBookBlockPVM;

import java.util.ArrayList;

/**
 * Created by Norbert on 2015.02.09..
 */
public class ProductGroupDetailsVM extends MapCache<String, Object>
{
    public static ProductGroupDetailsVM Instance = new ProductGroupDetailsVM();
    protected ProductGroupDetailsVM()
    {
    }

    // --

    private static enum KeyType
    {
        Details, Books, Statistics
    }

    private static final String _END_DETAILS = "_###_END_DETAILS";
    private static final String _END_BOOKS = "_###_END_BOOKS";
    private static final String _END_STATISTICS = "_###_END_STATISTICS";

    private String calculateKeyFor(String productGroupFU, KeyType keyType)
    {
        switch (keyType)
        {
            case Details:
                return productGroupFU + _END_DETAILS;
            case Books:
                return productGroupFU + _END_BOOKS;
            case Statistics:
                return productGroupFU + _END_STATISTICS;
            default:
                Utils.error("Unknown KeyType: " + keyType, tag);
                return null; // unreachable
        }
    }

    // --

    private BookBlockPLVM convertBooks(BookRowPLVM pgDetails)
    {
        ArrayList<InBookBlockPVM> books = new ArrayList<>(pgDetails.getProductsInGroup().size());

        for (BookRowPLVM.ProductVM productVM : pgDetails.getProductsInGroup())
        {
            InBookBlockPVM inBookBlockPVM = new InBookBlockPVM();
            InBookBlockPVM.ProductVM pVM = new InBookBlockPVM.ProductVM();
            InBookBlockPVM.ProductGroupVM pgVM = new InBookBlockPVM.ProductGroupVM();
            inBookBlockPVM.setProduct(pVM);
            inBookBlockPVM.setProductGroup(pgVM);
            books.add(inBookBlockPVM);

            pVM.setID(productVM.getID());
            pVM.setPriceString(productVM.getPriceString());
            pVM.setUserName(productVM.getUserName());
            pVM.setUserFriendlyUrl(productVM.getUserFriendlyUrl());
            pVM.setHowMany(productVM.getHowMany());
            pVM.setDescription("MOCK non empty string");

            String image = productVM.getImages() != null ? productVM.getImages().get(0) : "";
            image = image.startsWith(Constants.DEFAULT_IMAGE_NAME_START) ? "" : image;

            pgVM.setImageUrl(image);
            pgVM.setTitle("");
            pgVM.setSubTitle(productVM.getDescription());
            pgVM.setAuthorNames("");
        }

        BookBlockPLVM bookBlockPLVM = new BookBlockPLVM();
        bookBlockPLVM.setPaging(pgDetails.getPaging());
        bookBlockPLVM.setProducts(books);
        bookBlockPLVM.setBookBlockType(BookBlockType.ProductGroup);

        return bookBlockPLVM;
    }

    private InBookBlockPVM convertProductGroup(BookRowPLVM pgDetails)
    {
        BookRowPLVM.ProductGroupVM pg = pgDetails.getProductGroup();

        InBookBlockPVM inBookBlockPVM = new InBookBlockPVM();
        InBookBlockPVM.ProductVM pVM = new InBookBlockPVM.ProductVM();
        InBookBlockPVM.ProductGroupVM pgVM = new InBookBlockPVM.ProductGroupVM();
        inBookBlockPVM.setProduct(pVM);
        inBookBlockPVM.setProductGroup(pgVM);

        pVM.setPriceString(pg.getPriceString());
        pVM.setHowMany(-1);

        pgVM.setTitle(pg.getTitle());
        pgVM.setSubTitle(pg.getSubTitle());
        pgVM.setAuthorNames(pg.getAuthorNames());
        pgVM.setImageUrl(pg.getImageUrl());
        pgVM.setDescription(pg.getDescription());

        return inBookBlockPVM;
    }

    public void setProductGroupDetails(String productGroupFU, BookRowPLVM pgDetails)
    {
        setValue(productGroupFU, pgDetails);

        BookBlockPLVM bookBlocks = convertBooks(pgDetails);
        setValue(calculateKeyFor(productGroupFU, KeyType.Books), bookBlocks);

        InBookBlockPVM details = convertProductGroup(pgDetails);
        setValue(calculateKeyFor(productGroupFU, KeyType.Details), details);
    }

    public BookRowPLVM getProductGroupDetails(String productGroupFU)
    {
        return (BookRowPLVM) getValue(productGroupFU);
    }
    public BookBlockPLVM getBooks(String productGroupFU)
    {
        return (BookBlockPLVM) getValue(calculateKeyFor(productGroupFU, KeyType.Books));
    }
    public InBookBlockPVM getDetails(String productGroupFU)
    {
        return (InBookBlockPVM) getValue(calculateKeyFor(productGroupFU, KeyType.Details));
    }
    public BookRowPLVM.ProductGroupVM getStatistics(String productGroupFU)
    {
        BookRowPLVM productGroupDetails = getProductGroupDetails(productGroupFU);
        return productGroupDetails == null ? null : productGroupDetails.getProductGroup();
    }
}
