package geogram.example.galleryforyou.ui.activityes;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import geogram.example.galleryforyou.MyApplication;
import geogram.example.galleryforyou.R;

public class SingleImageActivity extends AppCompatActivity {

    @BindView(R.id.bigImageView)
    ImageView imageView;
    @Inject
    Picasso builder;
    private final String probel = " ";
    private String imageFile;
    private String imageName;
    private final String loadDir = "/galleryforyou";
    public static final int NUMBER_OF_REQUEST = 23401;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_image);
        MyApplication.getsApplicationComponent().inject(this);
        ButterKnife.bind(this);
        imageFile = getIntent().getStringExtra("imageFile");//получаем ссылку на картинку
        imageName = getIntent().getStringExtra("name");//получаем название картинки
        String toolbarTitle = String.valueOf(getIntent().getIntExtra("position", 0)) +
                probel + getString(R.string.from) + probel
                + String.valueOf(getIntent().getIntExtra("total", 0));
        setTitle(toolbarTitle);//устанавливаем название тулбара
        builder.load(imageFile)
                .placeholder(R.drawable.logotip)//картинка заглушки
                .error(R.drawable.fail)//картинка ошибки загрузки картинки
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)//отменяем кэширование
                .into(imageView);//загружаем картинку imageView

        BroadcastReceiver onComplete=new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, getString(R.string.loadedSuccesfully)+loadDir, Toast.LENGTH_SHORT).show();
            }
        };
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public void loadImage(String url) {//скачивание картинке на устройство
        File direct = new File(Environment.getExternalStorageDirectory()
                + loadDir);//получаем папку на для загрузки в нее картинки

        if (!direct.exists()) {//проверяем существование папки
            direct.mkdirs();//выбираем папку
        }

        DownloadManager dManager = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
        //получаем сервис для загрузки изображения
        Uri downloadUri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);
        //создаем запрос, утсанавливаем ссылку для скачивания картинки
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)//устанавливаем увиедомление по завершению загрузки
                .setAllowedNetworkTypes(
                        DownloadManager.Request.NETWORK_WIFI
                                | DownloadManager.Request.NETWORK_MOBILE)//устанавливаем типы интернет соединения для загрузки
                .setAllowedOverRoaming(false)//запрещаем скачивание в роуминге
                .setTitle(imageName)//устанавливаем название файла
                .setDestinationInExternalPublicDir(loadDir, imageName);//устанавливаем папку в которую необходимо поместить скаченный файл

        try {
            dManager.enqueue(request);//запускаем запрос на исполнение в отдельном потоке
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.loadError), Toast.LENGTH_SHORT).show();
            //показываем ошибку загрзки
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_single_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.load_image) {
            if (imageFile != null)//проверяем успешность передачи ссылки
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    int canWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

                    if (canWrite != PackageManager.PERMISSION_GRANTED) {


                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE}, NUMBER_OF_REQUEST);
//                        }
                    } else {
                        loadImage(imageFile);//скачиваем картинку на телефон при нажатии на пункт меню
                    }
                }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case NUMBER_OF_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadImage(imageFile);
                } else {
                    Toast.makeText(this, getString(R.string.errorPermission), Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
