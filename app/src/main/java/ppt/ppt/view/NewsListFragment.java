package ppt.ppt.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ppt.ppt.controller.news.NewsListAdapter;
import ppt.ppt.service.bit.BitServerService;
import ppt.ppt.vo.NewsVO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dj_hwang on 2017. 9. 20..
 */

public class NewsListFragment extends Fragment {

    //private NetworkService networkService;
    private BitServerService bitServerService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tab_news, container, false);

        Bundle bundle = getArguments();
        String[] category = new String[]{"society", "politics", "economic", "foreign", "culture", "entertain", "digital"};

        int p = bundle.getInt("position");
        Log.e("ppt", category[p]);
        ListView newsList = v.findViewById(R.id.newsList);

        setAdapter(newsList, category[p]);
        /*AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), newsListAdapter.getLink(i), Toast.LENGTH_LONG).show();
            }
        };*/

        //newsList.setAdapter(newsListAdapter);
        //newsList.setOnItemClickListener(onItemClickListener);

        return v;
    }

    /**
     * 뉴스정보를 가지고 있는 json파일을 받고 그 정보들을 adapter에 담아 리턴한다
     * @param category
     * @return
     */
    private void setAdapter(final ListView listView, String category){

        bitServerService = BitServerService.retrofit.create(BitServerService.class);
        //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
        final NewsListAdapter newsListAdapter = new NewsListAdapter(getContext());

        Call<Object> getList = bitServerService.getCategoryNews(category, 5);
        getList.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Gson gson = new Gson();
                String jsonString = gson.toJson(response.body());
                Log.d("ppt", jsonString);
                try {
                    JSONArray jsonArray = new JSONArray(jsonString);
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
                        NewsVO newsVO = new NewsVO();
                        String title = jsonObject.getString("title");
                        if(title.length() > 17) title = title.substring(0,17) + "...";
                        newsVO.setNewsCode(jsonObject.getString("newsCode"));
                        newsVO.setTitle(title);
                        newsVO.setLink(jsonObject.getString("link"));
                        //String title = jsonObject.getString("title");
                        //adapter.add(title);
                        Log.d("newsVO", newsVO.getTitle());
                        newsListAdapter.addItem(newsVO);
                    }
                    listView.setAdapter(newsListAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent it = new Intent(getContext(), WebviewActivity.class);
                            it.putExtra("link", newsListAdapter.getLink(i));
                            startActivity(it);
                        }
                    });

                } catch (JSONException e) {
                    Log.e("ppt", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("ppt", "category 접속실패");
            }
        });

    }
}
