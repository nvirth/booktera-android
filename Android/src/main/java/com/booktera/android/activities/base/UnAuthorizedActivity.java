package com.booktera.android.activities.base;

/**
 * This is just a marker for this kind of Activity is not an AuthorizedActivity. <br />
 * Or this is just a proxy for ActionBarActivity. <br />
 * <br />
 * Type hierarchy:
 <pre>
 BaseActivity
    ActionBarActivity
        UnAuthorizedActivity
 </pre>
 */
public abstract class UnAuthorizedActivity extends ActionBarActivity
{

}
