package ppt.ppt.controller.news;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ppt.ppt.view.R;
import ppt.ppt.vo.NewsVO;

/**
 * Created by dj_hwang on 2017. 9. 22..
 */

public class NewsListAdapter extends BaseAdapter {
    private Context context = null;
    private ArrayList<NewsVO> newsDataList = new ArrayList<>();

    public NewsListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return newsDataList.size();
    }

    @Override
    public NewsVO getItem(int i) {
        return newsDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        context = viewGroup.getContext();

        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.news_list, viewGroup, false);
        }

        TextView titleTextView = (TextView)view.findViewById(R.id.newsTitle);
        titleTextView.setText(newsDataList.get(i).getTitle());

        return view;
    }

    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }

    /**
     * 리스트에 추가하는 메소드
     * @param news
     */
    public void addItem(NewsVO news){
        newsDataList.add(news);
    }

    /**
     * 뉴스기사의 링크를 반환함
     * @param position
     * @return
     */
    public String getLink(int position){
        return newsDataList.get(position).getLink();
    }
}
