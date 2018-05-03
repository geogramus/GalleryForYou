package geogram.example.galleryforyou.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import geogram.example.galleryforyou.MyApplication;
import geogram.example.galleryforyou.R;
import geogram.example.galleryforyou.rest.models.Item;

/**
 * Created by geogr on 03.05.2018.
 */

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageListViewHolder> {
    //адаптер списка картинок
    private List<Item> images = new ArrayList<>();
    @Inject
    Picasso builder;

    public ImageListAdapter() {
        MyApplication.getsApplicationComponent().inject(this);
    }

    @Override
    public ImageListAdapter.ImageListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, null));
    }

    @Override
    public void onBindViewHolder(ImageListAdapter.ImageListViewHolder holder, int position) {
        builder.load(images.get(position).getPreview())
                .fit().centerCrop()
                .placeholder(R.drawable.ic_image_icon)
                .error(R.drawable.fail)
                .into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        if(images==null)
            return 0;
        return images.size();
    }

    public void addAll(List<Item> list) {
        images.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        images.clear();
        notifyDataSetChanged();
    }
    public Item getItem(int position){
        return images.get(position);
    }

    static class ImageListViewHolder extends RecyclerView.ViewHolder {
        //        @BindView(R.id.imageViewItem)
        ImageView imageView;

        ImageListViewHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(this, itemView);
            imageView=itemView.findViewById(R.id.imageViewItem);
        }
    }
}
