package geogram.example.galleryforyou.mvp.presenters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import geogram.example.galleryforyou.mvp.views.ImageListView;
import geogram.example.galleryforyou.rest.DataSource;
import geogram.example.galleryforyou.rest.models.Item;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by geogr on 05.05.2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class ImageListPresenterTest {
    @Mock
    DataSource dataSource;
    @Mock
    ImageListView view;
    ImageListPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new ImageListPresenter(view, dataSource);
    }

    @Test
    public void getPostsSuccess() throws Exception {
        List<Item> items = new ArrayList<>();
        int total = 20;
        presenter.onSuccesGettingItems(items, total);
        verify(view).addNewItems(items, total);

    }

    @Test
    public void getPostsFail() throws Exception {
        presenter.onErrorGettingItems();
        verify(view).error();
    }

}