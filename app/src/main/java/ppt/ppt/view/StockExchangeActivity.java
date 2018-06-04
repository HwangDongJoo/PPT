package ppt.ppt.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class StockExchangeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_exchange);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent it = null;

        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            // TODO 01 나중에 로그인 상태인지 아닌지 확인 후 로그인 또는 로그아웃으로 변경해야함
            it = new Intent(this, LoginActivity.class);
            startActivity(it);

        } else if (id == R.id.nav_home) {

            it = new Intent(this, MainActivity.class);
            startActivity(it);
            finish();

        } else if (id == R.id.nav_company_search) {

            it = new Intent(this, CompanySearchActivity.class);
            startActivity(it);
            finish();

        } else if (id == R.id.nav_having_stock) {

            it = new Intent(this, HavingStockActivity.class);
            startActivity(it);
            finish();

        } else if (id == R.id.nav_interesting_stock) {

            it = new Intent(this, InterestingStockActivity.class);
            startActivity(it);
            finish();

        } else if (id == R.id.nav_own_analysis) {

            it = new Intent(this, OwnAnalysisActivity.class);
            startActivity(it);
            finish();

        } else if (id == R.id.nav_stock_exchange) {



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
