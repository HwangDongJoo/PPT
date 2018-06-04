package ppt.ppt.controller.company;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;

import java.util.ArrayList;

import ppt.ppt.view.R;
import ppt.ppt.vo.CompanyVO;

/**
 * Created by dj_hwang on 2017. 9. 25..
 */

public class CompanyListAdapter extends BaseAdapter implements Filterable{
    private Context context = null;
    private ArrayList<CompanyVO> companyList = new ArrayList<>();
    private ArrayList<CompanyVO> filteredCompanyList = companyList;
    Filter listFilter;
    //private int resource;

    /*public CompanyListAdapter(Context context, int resource) {
        this.context = context;
        this.resource = resource;
    }*/

    public CompanyListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return companyList.size();
    }

    @Override
    public CompanyVO getItem(int i) {
        return companyList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.news_list, viewGroup, false);
        }

        TextView companyTitle = (TextView)view.findViewById(R.id.newsTitle);
        StringBuffer sb = new StringBuffer();
        sb.append(companyList.get(i).getComName())
                .append("(")
                .append(companyList.get(i).getComCode())
                .append(")");
        companyTitle.setText(sb.toString());

        return view;
    }

    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }

    public void addItem(CompanyVO companyVO){
        companyList.add(companyVO);
    }

    public String getCompanyName(int position){
        return companyList.get(position).getComName();
    }

    @Override
    public Filter getFilter() {
        if(listFilter == null){
            listFilter = new ListFilter();
        }
        return listFilter;
    }

    private class ListFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();

            if(charSequence == null || charSequence.length() == 0){
                results.values = companyList;
                results.count = companyList.size();
            } else {
                ArrayList<CompanyVO> itemList = new ArrayList<>();
                for(CompanyVO item : itemList){
                    if(item.getComName().toUpperCase().contains(charSequence.toString().toUpperCase())){
                        itemList.add(item);
                    }
                    results.values = itemList;
                    results.count = itemList.size();
                }
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            filteredCompanyList = (ArrayList<CompanyVO>) filterResults.values;

            if(filterResults.count > 0){
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
