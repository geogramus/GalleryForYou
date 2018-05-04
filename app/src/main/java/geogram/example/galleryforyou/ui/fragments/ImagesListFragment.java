package geogram.example.galleryforyou.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import geogram.example.galleryforyou.R;
import geogram.example.galleryforyou.adapters.ImageListAdapter;
import geogram.example.galleryforyou.mvp.presenters.ImageListPresenter;
import geogram.example.galleryforyou.mvp.views.ImageListView;
import geogram.example.galleryforyou.rest.DataSource;
import geogram.example.galleryforyou.rest.models.Item;
import geogram.example.galleryforyou.ui.activityes.SingleImageActivity;

/**
 * Created by geogr on 03.05.2018.
 */

public class ImagesListFragment extends Fragment implements ImageListView, SwipeRefreshLayout.OnRefreshListener, View.OnTouchListener {
    @BindView(R.id.imageList)
    RecyclerView listView;
    @BindView(R.id.textCount)
    TextView textCount;
    @BindView(R.id.refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    private ImageListPresenter presenter;
    private ImageListAdapter adapter;
    private final int startOffset = 0;
    private boolean loadStart = false;
    private String type;
    private int total = 0;
    private final int gridColumn = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_main, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("key", "key");
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setRetainInstance(true);//устанавливаем сохранение фрагмента

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), gridColumn);
        listView.setLayoutManager(layoutManager);//устанавливаем макет мэнеджер для RecyclerView
        type = getArguments().getString("type");
        if (savedInstanceState == null) {
            //проверяем фрагмент: первый ли этого его запуск, при перевороте экрана или перекрытии
            //в onSaveInstanceState помещаем ключ чтобы при восстановлении фрагментам выполнить
            // необходимый сценарий восстановления фрагмента, а не его пересоздание
            DataSource dataSource=new DataSource();
            presenter = new ImageListPresenter(this, dataSource);
            adapter = new ImageListAdapter();
            listView.setAdapter(adapter);
            if (getInternetConnection()) {//проверяем интернет соединение
                presenter.deleteAllItemsFromDatabase(type);
                presenter.getPosts(startOffset, type);
                //получаем новый список картинок с сервера
                loadStart = true;
            } else {
                Toast.makeText(getContext(), getString(R.string.check_internet_connetction), Toast.LENGTH_LONG).show();
                //предупреждаем пользователя огб отсутствии интернета
                presenter.getItemsFromBD(type);
            }

        } else {
            listView.setAdapter(adapter);//утсанавливаем адаптер и количество картинок при перевороте экрана
            textCount.setText(String.format("%s %s", String.valueOf(total), getString(R.string.photoString)));
        }
        swipeRefreshLayout.setOnRefreshListener(this);//добавляем слушатель обновления списка
        listView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {//добавляем слушатель прокрутки
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = layoutManager.getItemCount();
                int visibleItemCount = layoutManager.getChildCount();
                int firstVisibleItems = layoutManager.findFirstVisibleItemPosition();
                if (!loadStart) {// проверяем не загружается ли сейчас предыдущий запрос
                    if ((visibleItemCount + firstVisibleItems) >= totalItemCount &&
                            getInternetConnection()) { //проверяем позиции прокрученных элементов
                        loadStart = true;
                        presenter.getPosts(totalItemCount, type);// получаем новые элементы с сервера
                    }
                }
            }
        });

        listView.setOnTouchListener(this);

    }

    @Override
    public void onRefresh() {
        if (getInternetConnection()) {//проверяем интернет соединение
            presenter.deleteAllItemsFromDatabase(type);
            adapter.clear();
            loadStart = true;
            presenter.getPosts(startOffset, type);//получаем список из интернета
        } else {
            Toast.makeText(getContext(), getString(R.string.check_internet_connetction), Toast.LENGTH_LONG).show();
            swipeRefreshLayout.setRefreshing(false);//выдаем ошибку о отсутствии интернета
        }
    }


    @Override
    public void addNewItems(List<Item> items, int total) {//добавление новых картинок в список при удачном результате запроса
        this.total = total;
        adapter.addAll(items);
        textCount.setText(String.format("%s %s", String.valueOf(total), getString(R.string.photoString)));
        //назначаем общее количество картинок
        swipeRefreshLayout.setRefreshing(false);
        loadStart = false;
    }

    @Override
    public void error() {
        Toast.makeText(getContext(), getString(R.string.load_error), Toast.LENGTH_LONG).show();
        //ошибка загрузки изображений с сервера
        swipeRefreshLayout.setRefreshing(false);
        loadStart = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listView.setAdapter(null);
        listView.setLayoutManager(null);
        listView = null;
        adapter = null;
        presenter.onStop();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    final GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
        //определяем детектор жестов
        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            int position = listView.getChildLayoutPosition(listView.findChildViewUnder(e.getX(), e.getY()));
            if (!(position == -1)) {
                Item item = adapter.getItem(position);//получаем картинку из адаптера по позиции в списке
                Intent intent = new Intent(getActivity(), SingleImageActivity.class);
                intent.putExtra("imageFile", item.getFile());
                intent.putExtra("name", item.getName());
                intent.putExtra("position", ++position);
                intent.putExtra("total", total);
                startActivity(intent); //Запускаем активность с определенной картинкой
            }
            return super.onSingleTapConfirmed(e);
        }
    });

    public boolean getInternetConnection() { //проверка интернет соединения
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = null;
        if (cm != null) {
            wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        }
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        if (cm != null) {
            wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        }
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        if (cm != null) {
            wifiInfo = cm.getActiveNetworkInfo();
        }
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
