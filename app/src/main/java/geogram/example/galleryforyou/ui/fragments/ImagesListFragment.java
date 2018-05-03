package geogram.example.galleryforyou.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import geogram.example.galleryforyou.R;

/**
 * Created by geogr on 03.05.2018.
 */

public class ImagesListFragment extends Fragment{
    @BindView(R.id.imageList)
    RecyclerView listView;
    @BindView(R.id.textCount)
    TextView textCount;
    @BindView(R.id.refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    private String type;
    private final int gridColumn = 2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setRetainInstance(true);//устанавливаем сохранение фрагмента

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), gridColumn);
        listView.setLayoutManager(layoutManager);//устанавливаем макет мэнеджер для RecyclerView
        type = getArguments().getString("type");
    }
}
