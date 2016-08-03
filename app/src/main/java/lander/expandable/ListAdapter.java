package lander.expandable;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import java.util.List;

/**
 * Created by magdi on 01/08/2016.
 */
public class ListAdapter extends ExpandableRecyclerAdapter<ListAdapter.HeaderViewHolder, ListAdapter.BodyViewHolder> {

    private LayoutInflater mInflater;


    public ListAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public HeaderViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.list_item_head,viewGroup,false);
        return new HeaderViewHolder(view);
    }

    @Override
    public BodyViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.list_item_body,viewGroup,false);
        return new BodyViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(HeaderViewHolder headerViewHolder, int i, Object o) {
        HeaderParent parent = (HeaderParent) o;
        headerViewHolder.titulo.setText(parent.getTitle());
    }

    @Override
    public void onBindChildViewHolder(BodyViewHolder bodyViewHolder, int i, Object o) {
        BodyContent child = (BodyContent) o;
        bodyViewHolder.content.setText(child.getContent());
    }

    public static class HeaderViewHolder extends ParentViewHolder{
        public TextView titulo;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.titulo);
        }
    }
    public static class BodyViewHolder extends ChildViewHolder {
        public TextView content;
        public Button lerMais;
        public BodyViewHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.corpo);
            lerMais = (Button) itemView.findViewById(R.id.lerMais);

            lerMais.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.getContext().startActivity(new Intent(v.getContext(),DetailActivity.class));
                }
            });
        }
    }
}

