package geogram.example.galleryforyou.mvp.presenters;

import java.util.List;

import javax.inject.Inject;

import geogram.example.galleryforyou.MyApplication;
import geogram.example.galleryforyou.mvp.views.ImageListView;
import geogram.example.galleryforyou.rest.api.ImageService;
import geogram.example.galleryforyou.rest.models.Item;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by geogr on 03.05.2018.
 */

public class ImageListPresenter {


    private ImageListView fragment;
    private final String itemtype = "itemtype";

    public ImageListPresenter(ImageListView fragment) {
        MyApplication.getsApplicationComponent().inject(this);
        this.fragment = fragment;
    }

    @Inject
    ImageService service;


    public void getPosts(int offset, String type) {

        service.get_posts(type, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(generalModel -> {
                    if (generalModel != null) {
                        List<Item> list = generalModel.getEmbedded().getItems();
                        int total = generalModel.getEmbedded().getTotal();
                        if (list.size() != 0) {
                            addItemsToDB(list, type);
                            fragment.addNewItems(list, total);
                        }
                    }
                }, throwable -> fragment.error());

    }

    public void deleteAllItemsFromDatabase(String type) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Item> realmList = realm.where(Item.class).equalTo(itemtype, type)
                .findAll();
        realm.executeTransaction(realm1 -> realmList.deleteAllFromRealm());
    }

    private void addItemsToDB(List<Item> items, String type) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Item> realmList = realm.where(Item.class).equalTo(itemtype, type)
                .findAll();
        if (realmList.size() < 20) {
            for (int i = 0; i < items.size(); i++) {
                items.get(i).setItemtype(type);
            }
            realm.executeTransaction(realm1 -> realm1.insert(items));
        }
    }


    public void getItemsFromBD(String type) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Item> realmList = realm.where(Item.class).equalTo(itemtype, type)
                .findAll();
        List<Item> list = realm.copyFromRealm(realmList);
        fragment.addNewItems(list, list.size());
    }

    public void unregister() {
        fragment = null;
    }
}