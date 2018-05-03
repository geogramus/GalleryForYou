package geogram.example.galleryforyou.mvp.views;

import java.util.List;

import geogram.example.galleryforyou.rest.models.Item;

/**
 * Created by geogr on 03.05.2018.
 */

public interface ImageListView{
    void addNewItems(List<Item> items, int total);
    void error();
}