package ppt.ppt.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ppt.ppt.controller.company.CompanyListAdapter;
import ppt.ppt.service.bit.UserServerService;
import ppt.ppt.vo.CompanyVO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanySearchActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText searchView;
    private ListView searchList;
    private UserServerService userServerService;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        init();

        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);

        //final CompanyListAdapter adapter = new CompanyListAdapter(getApplicationContext());

        Call<String> callCompanyList = userServerService.allCompanyList();
        callCompanyList.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    JSONArray companyArray = new JSONArray(response.body());
                    for(int i=0; i<companyArray.length(); i++){
                        JSONObject companyInfo = new JSONObject(companyArray.get(i).toString());
                        StringBuffer sb = new StringBuffer();
                        sb.append(companyInfo.getString("comName"))
                                .append("(")
                                .append(companyInfo.getString("comCode"))
                                .append(")");

                        arrayAdapter.add(sb.toString());
                        //adapter.addItem(companyVO);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("ppt", "기업 리스트 접속 실패");
                try {
                    Log.e("ppt", t.getMessage());
                } catch (Exception e) {
                    Log.e("ppt", e.getMessage());
                }
            }
        });


        searchList.setAdapter(arrayAdapter);
        searchList.setTextFilterEnabled(true);



        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString();
                if(str.length() > 0){
                    searchList.setFilterText(str);
                } else {
                    searchList.clearTextFilter();
                }
            }
        });

        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String tv = (String)adapterView.getAdapter().getItem(i);
                
                Toast.makeText(getApplicationContext(), tv, Toast.LENGTH_SHORT).show();
            }
        });

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

            it = new Intent(this, LoginActivity.class);
            startActivity(it);

        } else if (id == R.id.nav_home) {

            it = new Intent(this, MainActivity.class);
            startActivity(it);
            finish();

        } else if (id == R.id.nav_company_search) {



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

            it = new Intent(this, StockExchangeActivity.class);
            startActivity(it);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void init(){
        searchView = (EditText) findViewById(R.id.searchView);
        searchList = (ListView)findViewById(R.id.searchList);
        userServerService = UserServerService.retrofit.create(UserServerService.class);
    }
}
