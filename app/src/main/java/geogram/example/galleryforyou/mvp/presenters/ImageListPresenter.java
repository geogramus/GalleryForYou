package geogram.example.galleryforyou.mvp.presenters;

import java.util.List;

import geogram.example.galleryforyou.mvp.views.ImageListView;
import geogram.example.galleryforyou.rest.DataSourceContract;

import geogram.example.galleryforyou.rest.models.Item;

import io.reactivex.disposables.Disposable;


/**
 * Created by geogr on 03.05.2018.
 */

public class ImageListPresenter {

    private ImageListView fragment;
    private DataSourceContract dataSource;
    private Disposable dispossable;

    public ImageListPresenter(ImageListView fragment, DataSourceContract dataSource) {
        this.dataSource = dataSource;
        this.fragment = fragment;
    }


    public void onStop() {
        if (dispossable != null && !dispossable.isDisposed()) {
            dispossable.dispose();
        }
    }

    public void getPosts(int offset, String type) {

        dispossable = dataSource.getPosts(offset, type, new DataSourceContract.CallbackInternet() {
            @Override
            public void onSucces(List<Item> list, int total) {
                onSuccesGettingItems(list, total);
            }

            @Override
            public void onFaild(Throwable e) {
                onErrorGettingItems();
            }
        });

    }

    public void deleteAllItemsFromDatabase(String type) {
        dataSource.deletAllItemsFromDatabase(type);
    }


    public void getItemsFromBD(String type) {
        List<Item> list = dataSource.getItemsFromBD(type);
        if (list.size() != 0)
            onSuccesGettingItems(list, list.size());

    }

    public void onSuccesGettingItems(List<Item> list, int total) {
        fragment.addNewItems(list, total);
    }

    public void onErrorGettingItems() {
        fragment.error();
    }
}