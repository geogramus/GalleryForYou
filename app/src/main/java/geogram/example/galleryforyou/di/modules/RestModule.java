package geogram.example.galleryforyou.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import geogram.example.galleryforyou.rest.RestClient;
import geogram.example.galleryforyou.rest.api.ImageService;

/**
 * Created by geogr on 03.05.2018.
 */


@Module
public class RestModule {
    private RestClient mRestClient;

    public RestModule() {
        mRestClient = new RestClient();
    }

    @Singleton
    @Provides
    public RestClient providesRestClient() {
        return mRestClient;
    }

    @Singleton
    @Provides
    public ImageService getImages() {
        return mRestClient.createService(ImageService.class);
    }

}
