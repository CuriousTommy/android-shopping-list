package com.example.assignment6.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.assignment6.data.room_items.Category;
import com.example.assignment6.data.room_items.CategoryDao;
import com.example.assignment6.data.room_items.ShoppingItem;
import com.example.assignment6.data.room_items.ShoppingItemDao;
import com.example.assignment6.data.room_items.ShoppingSession;
import com.example.assignment6.data.room_items.ShoppingSessionDao;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class ShoppingContentProvider extends ContentProvider {
    public static final String AUTHORITY = "com.assignment6.shopping";
    public static final Uri URI_SHOPPINGSESSION_ITEM = Uri.parse(
            "content://"+AUTHORITY+"/"+ ShoppingSession.TABLE_NAME+"/item");
    public static final Uri URI_SHOPPINGSESSION_CATEGORY = Uri.parse(
            "content://"+AUTHORITY+"/"+ ShoppingSession.TABLE_NAME+"/category");
    public static final Uri URI_SHOPPINGITEM_PARENTID = Uri.parse(
            "content://"+AUTHORITY+"/"+ShoppingItem.TABLE_NAME+"/parent_id");
    public static final Uri URI_SHOPPINGITEM_ITEM = Uri.parse(
            "content://"+AUTHORITY+"/"+ShoppingItem.TABLE_NAME+"/item");
    public static final Uri URI_SHOPPINGITEM_SUMMARY_COST = Uri.parse(
            "content://"+AUTHORITY+"/"+ShoppingItem.TABLE_NAME+"/summary/cost");
    public static final Uri URI_CATEGORY_ITEM = Uri.parse(
            "content://"+AUTHORITY+"/"+Category.TABLE_NAME+"/item");

    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int CODE_SHOPPINGSESSION_ITEMS = 1;
    private static final int CODE_SHOPPINGSESSION_ITEM_ID = 2;
    private static final int CODE_SHOPPINGSESSION_CATEGORY_ID = 3;
    private static final int CODE_SHOPPINGITEM_ITEMS = 4;
    private static final int CODE_SHOPPINGITEM_ITEM_ID = 5;
    private static final int CODE_SHOPPINGITEM_PARENTID_ID = 6;
    private static final int CODE_SHOPPINGITEM_SUMMARY_COST = 7;
    private static final int CODE_CATEGORY_ITEMS = 8;
    private static final int CODE_CATEGORY_ITEM_ID = 9;

    private static ShoppingSessionDao mShoppingSessionDao;
    private static ShoppingItemDao mShoppingItemDao;
    private static CategoryDao mCategoryDao;


    static {
        MATCHER.addURI(AUTHORITY, ShoppingSession.TABLE_NAME+"/item", CODE_SHOPPINGSESSION_ITEMS);
        MATCHER.addURI(AUTHORITY, ShoppingSession.TABLE_NAME+"/item/#", CODE_SHOPPINGSESSION_ITEM_ID);
        MATCHER.addURI(AUTHORITY, ShoppingSession.TABLE_NAME+"/category/#", CODE_SHOPPINGSESSION_CATEGORY_ID);
        MATCHER.addURI(AUTHORITY, ShoppingItem.TABLE_NAME+"/item", CODE_SHOPPINGITEM_ITEMS);
        MATCHER.addURI(AUTHORITY, ShoppingItem.TABLE_NAME+"/item/#", CODE_SHOPPINGITEM_ITEM_ID);
        MATCHER.addURI(AUTHORITY, ShoppingItem.TABLE_NAME+"/parent_id/#", CODE_SHOPPINGITEM_PARENTID_ID);
        MATCHER.addURI(AUTHORITY, ShoppingItem.TABLE_NAME+"/summary/cost/#", CODE_SHOPPINGITEM_SUMMARY_COST);
        MATCHER.addURI(AUTHORITY, Category.TABLE_NAME+"/item", CODE_CATEGORY_ITEMS);
        MATCHER.addURI(AUTHORITY, Category.TABLE_NAME+"/item/#", CODE_CATEGORY_ITEM_ID);
    }


    public ShoppingContentProvider() {

    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        if (context != null) {
            ShoppingDatabase database = generateShoppingSessionDatabase(context);
            if (mShoppingSessionDao == null) {
                mShoppingSessionDao = database.shoppingSessionDao();
            }

            if (mShoppingItemDao == null) {
                mShoppingItemDao = database.shoppingItemDao();
            }

            if (mCategoryDao == null) {
                mCategoryDao = database.categoryDao();
            }
        }


        return false;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final int code = MATCHER.match(uri);

        try {
            return new DeleteAsyncTask().execute(code, uri).get();
        } catch (ExecutionException | InterruptedException ignored) {}

        return -1;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int code = MATCHER.match(uri);

        try {
            return new InsertAsyncTask().execute(code, uri, values).get();
        } catch (InterruptedException|ExecutionException ignored) {}

        return null;
    }




    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final int code = MATCHER.match(uri);

        try {
            return new QueryAsyncTask().execute(code, uri).get();
        } catch (ExecutionException | InterruptedException ignored) {}

        return null;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        final int code = MATCHER.match(uri);

        try {
            return new UpdateAsyncTask().execute(code, uri, values).get();
        } catch (ExecutionException | InterruptedException ignored) {}

        return -1;
    }


    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    //
    // Private Member Functions
    //


    private ShoppingDatabase generateShoppingSessionDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), ShoppingDatabase.class, "default_shopping_session")
                .addCallback(callback)
                .build();
    }

    private RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            Executors.newSingleThreadExecutor().execute(
                    new Runnable() {
                        @Override
                        public void run() {
                            CategoryDao dao = Room.databaseBuilder(getContext(), ShoppingDatabase.class, "default_shopping_session")
                                    .build().categoryDao();

                            dao.insert(new Category("None"));
                            dao.insert(new Category("School"));
                            dao.insert(new Category("Work"));
                            dao.insert(new Category("Food"));
                            dao.insert(new Category("Entertainment"));
                        }
                    }
            );
        }
    };

    //
    // Private Multithreading Functions
    //


    private static class QueryAsyncTask extends AsyncTask<Object, Void, Cursor> {
        @Override
        protected Cursor doInBackground(Object... objects) {
            int code = (Integer) objects[0];
            Uri uri = (Uri) objects[1];

            long id;

            switch (code) {
                case CODE_SHOPPINGSESSION_ITEMS:
                    return mShoppingSessionDao.selectAll();
                case CODE_SHOPPINGSESSION_ITEM_ID:
                    id = ContentUris.parseId(uri);
                    return mShoppingSessionDao.selectByID(id);

                case CODE_SHOPPINGITEM_PARENTID_ID:
                    id = ContentUris.parseId(uri);
                    return mShoppingItemDao.selectSection(id);

                case CODE_CATEGORY_ITEMS:
                    return mCategoryDao.selectAll();

                case CODE_SHOPPINGSESSION_CATEGORY_ID:
                    id = ContentUris.parseId(uri);
                    return mShoppingSessionDao.selectByCategoryID(id);
                case CODE_SHOPPINGITEM_SUMMARY_COST:
                    id = ContentUris.parseId(uri);
                    return mShoppingItemDao.totalCostFromSection(id);
            }

            return null;
        }
    }

    private static class InsertAsyncTask extends AsyncTask<Object, Void, Uri> {

        @Override
        protected Uri doInBackground(Object... objects) {
            int code = (Integer) objects[0];
            Uri uri = (Uri) objects[1];
            ContentValues values = (ContentValues) objects[2];

            long id;

            switch (code) {
                case CODE_SHOPPINGSESSION_ITEMS:
                    ShoppingSession shopping_session = ShoppingSession.createShoppingSession(values);
                    id = mShoppingSessionDao.insert(shopping_session);
                    break;
                case CODE_SHOPPINGITEM_ITEMS:
                    ShoppingItem shopping_item = new ShoppingItem(values);
                    id = mShoppingItemDao.insert(shopping_item);
                    break;
                case CODE_CATEGORY_ITEMS:
                    Category category = new Category(values);
                    id = mCategoryDao.insert(category);
                default:
                    id = -1;

            }

            if (id != -1) {
                return ContentUris.withAppendedId(uri, id);
            } else {
                return null;
            }

        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Object, Void, Integer> {
        @Override
        protected Integer doInBackground(Object... objects) {
            int code = (Integer) objects[0];
            Uri uri = (Uri) objects[1];

            long id = ContentUris.parseId(uri);

            switch (code) {
                case CODE_SHOPPINGSESSION_ITEM_ID:
                    mShoppingSessionDao.deleteUsingID(id);
                    return 1;
                case CODE_SHOPPINGITEM_ITEM_ID:
                    mShoppingItemDao.deleteUsingID(id);
                    return 1;
                case CODE_SHOPPINGITEM_PARENTID_ID:
                    return mShoppingItemDao.deleteSectionUsingParentID(id);
                case CODE_CATEGORY_ITEM_ID:
                    mCategoryDao.deleteUsingID(id);
                    return 1;
            }

            return -1;
        }
    }

    protected static class UpdateAsyncTask extends AsyncTask<Object, Void, Integer> {

        @Override
        protected Integer doInBackground(Object... objects) {
            final int code = (Integer) objects[0];
            final Uri uri = (Uri) objects[1];
            final ContentValues values = (ContentValues) objects[2];

            switch (code) {
                case CODE_SHOPPINGSESSION_ITEMS:
                    mShoppingSessionDao.update(new ShoppingSession(values));
                    return 1;
                case CODE_SHOPPINGITEM_ITEMS:
                    mShoppingItemDao.update(new ShoppingItem(values));
                    return 1;
                case CODE_CATEGORY_ITEMS:
                    mCategoryDao.update(new Category(values));
                    return 1;
            }

            return -1;
        }
    }
}
