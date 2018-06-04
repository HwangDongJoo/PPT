package ppt.ppt.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ppt.ppt.service.api.APIServerService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dj_hwang on 2017. 9. 20..
 */

public class MarketChartFragment extends Fragment {

    private APIServerService apiServerService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_market, container, false);
        GraphView chart = (GraphView)v.findViewById(R.id.marketChart);
        Bundle bundle = getArguments();
        int position = bundle.getInt("market");
        String[] market = new String[]{"KOSPI:IND", "KOSDAQ:IND"};

        /*LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPointArr);
        chart.addSeries(series);*/
        setPriceList(chart, market[position]);

        return v;
    }

    private void setPriceList(GraphView lineChart, String market){
        final GraphView chart = lineChart;
        apiServerService = APIServerService.retrofit.create(APIServerService.class);
        Call<Object> call = apiServerService.getPrice(market, "1_DAY");
        Log.e("ppt", market);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                DataPoint[] arrDataPoint;
                LineGraphSeries<DataPoint> series;
                Gson gson = new Gson();
                String jsonString = gson.toJson(response.body());
                //Log.d("ppt", jsonString);

                try {
                    JSONArray jsonArray = new JSONArray(jsonString);
                    JSONObject jsonObject = new JSONObject(jsonArray.getString(0));
                    JSONArray priceArray = new JSONArray(jsonObject.getString("price"));
                    arrDataPoint = new DataPoint[priceArray.length()];
                    for(int i=0; i<priceArray.length(); i++){
                        JSONObject temp = new JSONObject(priceArray.getString(i));
                        arrDataPoint[i] = new DataPoint(i+1, temp.getDouble("value"));
                    }
                    series = new LineGraphSeries<DataPoint>(arrDataPoint);
                    chart.addSeries(series);
                } catch (JSONException e){
                    Log.e("ppt", e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("ppt", "접속실패");
            }
        });


    }
}
