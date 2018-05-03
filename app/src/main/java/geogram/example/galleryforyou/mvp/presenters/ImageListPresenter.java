package geogram.example.galleryforyou.mvp.presenters;

import java.util.List;

import javax.inject.Inject;

import geogram.example.galleryforyou.MyApplication;
import geogram.example.galleryforyou.mvp.views.ImageListView;
import geogram.example.galleryforyou.rest.api.ImageService;
import geogram.example.galleryforyou.rest.models.Item;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by geogr on 03.05.2018.
 */

public class ImageListPresenter {


    private ImageListView fragment;
    private final String itemtype ="itemtype";
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

                            fragment.addNewItems(list, total);
                        }
                    }
                }, throwable -> fragment.error());

    }



    public void unregister() {
        fragment = null;
    }
}