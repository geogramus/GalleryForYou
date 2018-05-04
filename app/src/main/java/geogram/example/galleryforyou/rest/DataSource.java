package geogram.example.galleryforyou.rest;

import java.util.List;

import javax.inject.Inject;

import geogram.example.galleryforyou.MyApplication;
import geogram.example.galleryforyou.rest.api.ImageService;
import geogram.example.galleryforyou.rest.models.GeneralModel;
import geogram.example.galleryforyou.rest.models.Item;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by geogr on 05.05.2018.
 */

public class DataSource implements DataSourceContract {
    private final String itemtype = "itemtype";
    @Inject
    ImageService service;

    public DataSource() {
        MyApplication.getsApplicationComponent().inject(this);
    }

    @Override
    public DisposableObserver<GeneralModel> getPosts(int offset, String type, final CallbackInternet callbackInternet) {
        return service.get_posts(type, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<GeneralModel>() {
                                   @Override
                                   public void onNext(GeneralModel generalModel) {
                                       if (generalModel != null) {
                                           callbackInternet.onSucces(generalModel.getEmbedded().getItems(), generalModel.getEmbedded().getTotal());
                                           if (generalModel.getEmbedded().getItems().size() != 0)
                                               addItemsToDB(generalModel.getEmbedded().getItems(), type);
                                       }

                                   }

                                   @Override
                                   public void onError(Throwable e) {
                                       callbackInternet.onFaild(e);
                                   }

                                   @Override
                                   public void onComplete() {

                                   }
                               }

                );
    }

    @Override
    public void deletAllItemsFromDatabase(String type) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Item> realmList = realm.where(Item.class).equalTo(itemtype, type)
                .findAll();
        realm.executeTransaction(realm1 -> realmList.deleteAllFromRealm());
    }

    @Override
    public List<Item> getItemsFromBD(String type) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Item> realmList = realm.where(Item.class).equalTo(itemtype, type)
                .findAll();
        return realm.copyFromRealm(realmList);
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
}