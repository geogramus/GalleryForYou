package geogram.example.galleryforyou.rest;

import java.util.List;

import geogram.example.galleryforyou.rest.models.GeneralModel;
import geogram.example.galleryforyou.rest.models.Item;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by geogr on 05.05.2018.
 */

public interface DataSourceContract {
    DisposableObserver<GeneralModel> getPosts(int offset, String type, final CallbackInternet callbackInternet);

    void deletAllItemsFromDatabase(String type);

    List<Item> getItemsFromBD(String type);

    interface CallbackInternet {
        void onSucces(List<Item> list, int total);

        void onFaild(Throwable e);
    }
}
