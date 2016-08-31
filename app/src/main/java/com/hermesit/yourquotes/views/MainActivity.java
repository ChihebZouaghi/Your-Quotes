package com.hermesit.yourquotes.views;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hermesit.yourquotes.parse.ParseJsonQuotes;
import com.hermesit.yourquotes.R;
import com.hermesit.yourquotes.rvadapter.RVAdapter;
import com.hermesit.yourquotes.singleton.MotQuoteText;
import com.hermesit.yourquotes.singleton.VolleySingleton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RVAdapter adapter;
    private LinearLayoutManager linearLayoutManager;


    private List<MotQuoteText> data;

    public static final String KEYQUOTE = "one_quote";
    public static final String DTAG = "quote_dialog";
    public static final String ABOUTDTAG = "quote_dialog";

    public static final String MYURL = "http://quotesondesign.com/wp-json/posts?filter[orderby]=rand&filter[posts_per_page]=20";

    private boolean firstCall = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recyclerView = (RecyclerView) findViewById(R.id.myRV);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(
                this,
                recyclerView,
                new ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        callDialogFragment(data.get(position).getMotQuote());
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        callDialogFragment(data.get(position).getMotQuote());
                    }
                }
        ));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                callDialogFragment("");


            }
        });


        if (isOnline()) {
            getData(MYURL);
        }
        else {
            View view = findViewById(R.id.idCoorLayoutInMain);
            Snackbar.make(view, "Please check your network connection and reload", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            // Toast.makeText(this,"Please check your network connection and try again",Toast.LENGTH_LONG).show();
        }

    }

    protected boolean isOnline() {
        ConnectivityManager manager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    private void callDialogFragment(String quote) {
        FragmentManager manager = getSupportFragmentManager();

        Bundle bundle = new Bundle();
        bundle.putString(KEYQUOTE, quote);

        QuoteDialog quoteDialog = new QuoteDialog();
        quoteDialog.setArguments(bundle);

        quoteDialog.show(manager, DTAG);
    }


    private void getData(String url) {
        RequestQueue queue = VolleySingleton.getInstance().getRequestQueue();

        StringRequest request = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // data = ParseXMLQuotes.parseQuotes(response);
                        data = ParseJsonQuotes.parseQuotes(response);

                        adapter = new RVAdapter(getBaseContext(), data);
                        recyclerView.setAdapter(adapter);
                    }
                },new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse (VolleyError error){

            }
        }

        );

        queue.add(request);

    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {


            this.clickListener = clickListener;

            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }


        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {


        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public static interface ClickListener {

        public void onClick(View view, int position);

        public void onLongClick(View view, int position);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            FragmentManager manager = getSupportFragmentManager();

            AboutDialog aboutDialog = new AboutDialog();

            aboutDialog.show(manager, ABOUTDTAG);
            return true;
        }

        if (id == R.id.action_refresh) {
            if (isOnline())
                getData(MYURL);
            else {
                View view = findViewById(R.id.idCoorLayoutInMain);
                Snackbar.make(view, "Please check your network connection and reload", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                // Toast.makeText(this,"Please check your network connection and try again",Toast.LENGTH_LONG).show();
            }

            return true;
        }


        return super.onOptionsItemSelected(item);
    }


}
