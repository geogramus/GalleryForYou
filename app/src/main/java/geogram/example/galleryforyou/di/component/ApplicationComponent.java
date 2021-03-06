package geogram.example.galleryforyou.di.component;

import javax.inject.Singleton;

import dagger.Component;
import geogram.example.galleryforyou.adapters.ImageListAdapter;
import geogram.example.galleryforyou.adapters.MainPagerAdapter;
import geogram.example.galleryforyou.di.modules.ApplicationModule;
import geogram.example.galleryforyou.di.modules.RestModule;
import geogram.example.galleryforyou.mvp.presenters.ImageListPresenter;
import geogram.example.galleryforyou.rest.DataSource;
import geogram.example.galleryforyou.ui.activityes.SingleImageActivity;

/**
 * Created by geogr on 03.05.2018.
 */

@Singleton
@Component(modules = {ApplicationModule.class, RestModule.class })
public interface ApplicationComponent {
    void inject(DataSource data);
    void inject(SingleImageActivity activity);
    void inject(ImageListAdapter adapter);
}