package ppt.ppt.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ppt.ppt.controller.market.MarketTabPagerAdapter;
import ppt.ppt.controller.news.NewsListAdapter;
import ppt.ppt.controller.news.NewsTabPagerAdapter;
import ppt.ppt.service.bit.BitServerService;
import ppt.ppt.vo.NewsVO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //private TabHost marketTab, categoryTab;
    //private NetworkService networkService;
    private BitServerService bitServerService;
    private TabLayout marketTab, newsTab;
    private ViewPager marketViewPager, newsViewPager;
    private ListView headlineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initView();
        setHeadlineNewsList(headlineList);
        setNewsTab();
        setMarketTab();
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
        Intent it = null;

        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            // TODO 01 나중에 로그인 상태인지 아닌지 확인 후 로그인 또는 로그아웃으로 변경해야함
            it = new Intent(this, LoginActivity.class);
            startActivity(it);

        } else if (id == R.id.nav_home) {



        } else if (id == R.id.nav_company_search) {

            it = new Intent(this, CompanySearchActivity.class);
            startActivity(it);

        } else if (id == R.id.nav_having_stock) {

            it = new Intent(this, HavingStockActivity.class);
            startActivity(it);

        } else if (id == R.id.nav_interesting_stock) {

            it = new Intent(this, InterestingStockActivity.class);
            startActivity(it);

        } else if (id == R.id.nav_own_analysis) {

            it = new Intent(this, OwnAnalysisActivity.class);
            startActivity(it);

        } else if (id == R.id.nav_stock_exchange) {

            it = new Intent(this, StockExchangeActivity.class);
            startActivity(it);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private void setHeadlineNewsList(final ListView listView){
        bitServerService = BitServerService.retrofit.create(BitServerService.class);
        final NewsListAdapter adapter = new NewsListAdapter(getApplicationContext());

        Call<Object> test = bitServerService.getHeadlineNews();
        test.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if(response.isSuccessful()){
                    //Toast.makeText(getApplicationContext(), "접속성공", Toast.LENGTH_LONG).show();
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(response.body());
                    //Log.i("ppy", jsonString);
                    try{
                        JSONArray jsonArray = new JSONArray(jsonString);
                        for(int i=0; i<jsonArray.length(); i++) {
                            JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
                            NewsVO newsVO = new NewsVO();
                            newsVO.setNewsCode(jsonObject.getString("newsCode"));
                            String title = jsonObject.getString("title");
                            if(title.length() > 17) title = title.substring(0,17)+"...";
                            newsVO.setTitle(title);
                            newsVO.setLink(jsonObject.getString("link"));
                            adapter.addItem(newsVO);
                        }
                        listView.setAdapter(adapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                //Toast.makeText(getApplicationContext(), adapter.getLink(i), Toast.LENGTH_LONG).show();
                                Intent it = new Intent(getApplicationContext(), WebviewActivity.class);
                                it.putExtra("link", adapter.getLink(i));
                                startActivity(it);
                            }
                        });

                    } catch (JSONException e) {
                        Log.e("aaa", e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("ppt", "headline 접속 실패");
            }

        });

    }

    public void initView(){
        marketTab = (TabLayout)findViewById(R.id.main_marketTab);
        newsTab = (TabLayout)findViewById(R.id.main_newsTab);
        marketViewPager = (ViewPager)findViewById(R.id.main_marketViewPager);
        newsViewPager = (ViewPager)findViewById(R.id.main_newsViewPager);

        headlineList = (ListView)findViewById(R.id.main_list_headline);
    }

    public void setMarketTab(){
        marketTab.addTab(marketTab.newTab().setText("KOSPI"));
        marketTab.addTab(marketTab.newTab().setText("KOSDAQ"));
        marketTab.setTabGravity(TabLayout.GRAVITY_FILL);

        MarketTabPagerAdapter marketTabPagerAdapter = new MarketTabPagerAdapter(getSupportFragmentManager(),
                marketTab.getTabCount());

        marketViewPager.setAdapter(marketTabPagerAdapter);
        marketViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(marketTab));

        marketTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                marketViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void setNewsTab(){
        newsTab.addTab(newsTab.newTab().setText("사회"));
        newsTab.addTab(newsTab.newTab().setText("정치"));
        newsTab.addTab(newsTab.newTab().setText("경제"));
        newsTab.addTab(newsTab.newTab().setText("국제"));
        newsTab.addTab(newsTab.newTab().setText("문화"));
        newsTab.addTab(newsTab.newTab().setText("연예"));
        newsTab.addTab(newsTab.newTab().setText("IT"));
        newsTab.setTabGravity(TabLayout.GRAVITY_FILL);

        NewsTabPagerAdapter newsTabPagerAdapter = new NewsTabPagerAdapter(getSupportFragmentManager(),
                newsTab.getTabCount());

        newsViewPager.setAdapter(newsTabPagerAdapter);
        newsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(newsTab));

        newsTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                newsViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
