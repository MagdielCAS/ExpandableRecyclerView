package lander.expandable;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tonicartos.superslim.GridSLM;
import com.tonicartos.superslim.LinearSLM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by magdi on 02/10/2016.
 */
public class ContentAdapter extends RecyclerView.Adapter{


    private static final int VIEW_TYPE_HEADER = 0;

    private static final int VIEW_TYPE_CONTENT = 1;


    private List<Object> dataSet;

    public ContentAdapter(List<Object> dataSet) {
        this.dataSet = dataSet;
        System.out.println(this.dataSet.size());
    }

    @Override
    public int getItemViewType(int position) {
        return dataSet.get(position) instanceof StickyHeader ? VIEW_TYPE_HEADER : VIEW_TYPE_CONTENT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if(viewType == VIEW_TYPE_HEADER){
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.header_item, parent, false);
            vh = new HeaderViewHolder(v);
        }else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.content_item, parent, false);
            vh = new ContentViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        System.out.println(position);
        if(holder instanceof ContentViewHolder){
            String item = (String) dataSet.get(position);
            ((ContentViewHolder)holder).content.setText(Html.fromHtml(item));
        }else {
            StickyHeader item = (StickyHeader) dataSet.get(position);
            ((HeaderViewHolder)holder).textView.setText(item.getHeader());
         //   final GridSLM.LayoutParams lp = GridSLM.LayoutParams.from(holder.itemView.getLayoutParams());
          //  lp.setFirstPosition(item.getSectionFirstPosition());
          //  holder.itemView.setLayoutParams(lp);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        public TextView content;

        public ContentViewHolder(View v) {
            super(v);
            content = (TextView) v.findViewById(R.id.content_text);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public HeaderViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.header_text);
        }
    }
}