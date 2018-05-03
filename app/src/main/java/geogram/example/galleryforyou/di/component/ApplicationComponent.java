package geogram.example.galleryforyou.di.component;

import javax.inject.Singleton;

import dagger.Component;
import geogram.example.galleryforyou.adapters.ImageListAdapter;
import geogram.example.galleryforyou.adapters.MainPagerAdapter;
import geogram.example.galleryforyou.di.modules.ApplicationModule;
import geogram.example.galleryforyou.di.modules.RestModule;
import geogram.example.galleryforyou.mvp.presenters.ImageListPresenter;

/**
 * Created by geogr on 03.05.2018.
 */

@Singleton
@Component(modules = {ApplicationModule.class, RestModule.class })
public interface ApplicationComponent {
    void inject(ImageListPresenter presenter);
    void inject(MainPagerAdapter adapter);
    void inject(ImageListAdapter adapter);
}