package geogram.example.galleryforyou.di.modules;

import android.app.Application;
import android.content.Context;
import android.net.Uri;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.UrlConnectionDownloader;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by geogr on 03.05.2018.
 */

@Module
public class ApplicationModule {
    private Application application;
    private Picasso builder;

    public ApplicationModule(Application application1) {
        this.application = application1;
        this.builder = new Picasso.Builder(application1.getApplicationContext())
                .downloader(new UrlConnectionDownloader(application1.getApplicationContext()){
                    @Override
                    protected HttpURLConnection openConnection(Uri path) throws IOException {
                        HttpURLConnection connection=super.openConnection(path);
                        connection.setRequestProperty("Authorization", "OAuth AQAAAAAl58iKAADLWwMHDJ2qOUS4kgTuqylgprU");
                        return connection;
                    }
                }).build();
    }

    @Singleton
    @Provides
    public Context provideContext() {
        return application;
    }

    @Singleton
    @Provides
    public Picasso providePicasso() {
        return builder;
    }
}