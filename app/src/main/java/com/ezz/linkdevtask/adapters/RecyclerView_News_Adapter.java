package com.ezz.linkdevtask.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ezz.linkdevtask.constants.Constants;
import com.ezz.linkdevtask.listeners.NewSelectedListener;
import com.ezz.linkdevtask.app.MyApplication;
import com.ezz.linkdevtask.models.AllNews;
import com.ezz.linkdevtask.listeners.NewsClickListener;
import com.ezz.linkdevtask.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by A.Ezz on 12/6/2016.
 */

public class RecyclerView_News_Adapter extends RecyclerView.Adapter<RecyclerView_News_Adapter.MyViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<AllNews> arrayList;
    private NewSelectedListener selectedListener;

    public RecyclerView_News_Adapter(Context context, ArrayList<AllNews> arrayList,NewSelectedListener selectedListener) {
        if(context!=null)
        {
            this.context = context;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(arrayList!=null)
        {
            this.arrayList = arrayList;
        }
        if(selectedListener!=null)
        {
            this.selectedListener = selectedListener;
        }
    }

    public void setArrayList(ArrayList<AllNews> arrayList) {
        if(arrayList!=null)
        {
            this.arrayList = arrayList;
            notifyDataSetChanged();
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.new_item,null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final AllNews news = arrayList.get(position);
        String NewsTitle = news.getNewsTitle();
        String NewsDate  = news.getPostDate();
        String NewsLikes = news.getLikes();
        String NewsViews = news.getNumofViews();
        String NewsImageUrl = news.getImageUrl();
        String NewsType = news.getNewsType();
        if(NewsType.equals(Constants.articalelabel))
        {
            holder.imgNewType.setImageResource(R.drawable.article_label);
        }else if(NewsType.equals(Constants.videolabel))
        {
            holder.imgNewType.setImageResource(R.drawable.video_label);
        }
        Picasso.with(context).load(NewsImageUrl).fit().placeholder(R.drawable.news_image_placeholder).into(holder.imgnew);
        holder.txtNewTitle.setText(NewsTitle);
        SharedPreferences force_pref = PreferenceManager
                .getDefaultSharedPreferences(context);
        String language = force_pref.getString(MyApplication.FORCE_LOCAL, "");
        if (language.equals(Constants.arabic))
        {
            holder.txtNewViews.setText(context.getString(R.string.views)+" "+NewsViews);
        }
        else
        {
            holder.txtNewViews.setText(NewsViews+" "+context.getString(R.string.views));
        }
        holder.txtNewLikes.setText(context.getString(R.string.likes)+" ("+NewsLikes+")");
        holder.txtNewDate.setText(NewsDate);
        holder.setItemClickListener(new NewsClickListener() {
            @Override
            public void OnItemClick(int pos) {
                if(selectedListener!=null) {
                    selectedListener.NewSelected(news);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        CircleImageView imgnew;
        TextView txtNewTitle,txtNewDate,txtNewLikes,txtNewViews;
        ImageView imgNewType;
        NewsClickListener itemClickListener;
        public MyViewHolder(View itemView) {
            super(itemView);
            imgnew = (CircleImageView) itemView.findViewById(R.id.imgnew);

            txtNewTitle = (TextView) itemView.findViewById(R.id.txtNewTitle);

            txtNewDate = (TextView) itemView.findViewById(R.id.txtNewDate);

            txtNewLikes = (TextView) itemView.findViewById(R.id.txtNewLikes);

            txtNewViews = (TextView) itemView.findViewById(R.id.txtNewViews);

            imgNewType = (ImageView) itemView.findViewById(R.id.imgNewType);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(NewsClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.OnItemClick(this.getLayoutPosition());
        }
    }
}
