# Android_Labs
# Лабораторная работа №7. Сервисы и Broadcast Receivers.

## Цели
Получить практические навыки разработки сервисов (started и bound) и Broadcast Receivers.

## Задачи
### Задача 1. Started сервис для скачивания изображения
В [лабораторной работе №6](../06/TASK.md) был разработан код, скачивающий картинку из интернета. На основе этого кода разработайте started service, скачивающий файл из интернета. URL изображения для скачивания должен передаваться в Intent. Убедитесь (и опишите доказательство в отчете), что код для скачивания исполняется не в UI потоке

Добавьте в разработанный сервис функцию отправки broadcast сообщения по завершении скачивания. Сообщение (Intent) должен содержать путь к скачанному файлу.

## Решение

Сервис – это некая задача, которая работает в фоне и не использует UI
Существует два типа service :
1. Наиболее распространенными являются локальные службы (также можно найти термин started или unbound Services), где действие, которое запускает службу, и сама служба принадлежат одному и тому же приложению.
1. Служба также может быть запущена компонентом, принадлежащим другому приложению, в этом случае это удаленная служба (также называется boundservice).

```csharp
public class ImageDownloadService extends JobIntentService {
    static final int JOB_ID = 1000;

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        String url = intent.getStringExtra("url");
        Log.d("thread", Thread.currentThread().getName());
        if (url == null) {
            sendBroadcast("Error");
        } else {
            try {
                String path = download(url);
                sendBroadcast(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, ImageDownloadService.class, JOB_ID, work);
    }

    public String download(String url) throws IOException {
        Bitmap mIcon11 = null;
        String path = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
            path = save(mIcon11, "test"+ (int) (Math.random() * 1000));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return path;
    }

    public String save(Bitmap bitmap, String name) {
        FileOutputStream Stream;
        try {
            Stream = this.getApplicationContext().openFileOutput(name, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, Stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return getApplicationContext().getFileStreamPath(name).getAbsolutePath();
    }

    public void sendBroadcast(String message) {
        sendBroadcast(new Intent("broadcast").putExtra("Message", message));
    }
}
```
### Задача 2. Broadcast Receiver
Разработайте два приложения: первое приложение содержит 1 activity с 1 кнопкой, при нажатии на которую запускается сервис по скачиванию файла. Второе приложение содержит 1 broadcast receiver и 1 activity. Broadcast receiver по получении сообщения из сервиса инициирует отображение *пути* к изображению в `TextView` в Activity.

## Решение
 пишем 2 приложения, первое скачивает сообщение, второе пишет путь изображения в textView
 
 ```csharp
 public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    BroadcastReceiver br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.d("MainActivity", "1");
         br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message= intent.getStringExtra("Message");
                binding.textView.setText(message);
            }
        };
        registerReceiver(br, new IntentFilter("broadcast"));
        binding.button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("thread", Thread.currentThread().getName());
                        Intent intent = new Intent(MainActivity.this, ImageDownloadService.class).putExtra("url", "https://wallbox.ru/wallpapers/preview/201432/8b74eb4d1393499.jpg");
                        ImageDownloadService.enqueueWork(MainActivity.this,intent);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(br);
    }
}
 ```
 
 ```csharp
 public class MainActivity2 extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.d("MainActivity2", "2");
        binding.button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("thread", Thread.currentThread().getName());
                        Intent intent = new Intent(MainActivity2.this, ImageDownloadService.class).putExtra("url", "https://wallbox.ru/wallpapers/preview/201432/8b74eb4d1393499.jpg");
                        ImageDownloadService.enqueueWork(MainActivity2.this,intent);
                    }
                });
    }

}
 ```
 ### Задача 3. Bound Service для скачивания изображения
Сделайте разработанный сервис одновременно bound И started: переопределите метод `onBind`. Из тела метода возвращайте `IBinder`, полученный из класса [`Messenger`](https://developer.android.com/guide/components/bound-services?hl=ru#Messenger). Убедитесь (доказательство опишите в отчете), что код скачивания файла исполняется не в UI потоке.

Измените способ запуска сервиса в первом приложении: вместо `startService` используйте `bindService`. При нажатии на кнопку отправляйте сообщение [`Message`](https://developer.android.com/reference/android/os/Message.html?hl=ru), используя класс `Messenger`, полученный из интерфейса `IBinder` в методе [`onServiceConnected`](https://developer.android.com/reference/android/content/ServiceConnection.html?hl=ru#onServiceConnected(android.content.ComponentName,%20android.os.IBinder)).

Добавьте в первое приложение `TextView`, а в сервис отправку [обратного](https://developer.android.com/reference/android/os/Message.html?hl=ru#replyTo) сообщения с местоположением скачанного файла. При получении сообщения от сервиса приложение должно отобразить путь к файлу на экране.

Обратите внимание, что разработанный сервис должен быть одновременно bound И started. Если получен интент через механизм started service, то сервис скачивает файл и отправляет broadcast (started service не знает своих клиентов и не предназначен для двухсторонней коммуникации). Если получен message через механизм bound service, то скачивается файл и результат отправляется тому клиенту, который запросил этот файл (т.к. bound service знает всех своих клиентов и может им отвечать).

## Решение
с предыдущей работы, добавим ещё одну кнопку, первая будет запускать через startService, вторая через bindService.

класс DownloadAsyncTask , в котором и будем выполнять операцию скачивания

вместо onHandleWork переписываем onStartCommand, в котором возвращаем START_NOT_STICKY – сервис не будет перезапущен после того, как был убит системой

```csharp
public class ImageDownloadServiceTask3 extends Service {
    static final int JOB_ID = 1000;
    String url;
    Messenger messenger;

    @Override
    public IBinder onBind(@NonNull Intent intent) {
        messenger = new Messenger(new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 1) {
                    new DownloadAsyncTask(msg.replyTo).execute(msg.getData().getString("url", url));
                }
                super.handleMessage(msg);
            }
        });
        return messenger.getBinder();
    }

    class DownloadAsyncTask extends AsyncTask<String, Void, String> {

        private Messenger receiver;

        DownloadAsyncTask(Messenger receiver) {
            this.receiver = receiver;
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d("thread", Thread.currentThread().getName());
            String urls = strings[0];
            String path = null;
            try {
                path = download(urls);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return path;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Message message = Message.obtain(null, 1);
            Bundle data = new Bundle();
            data.putString("answer", s);
            message.setData(data);
            try {
                if (receiver != null) {
                    receiver.send(message);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String url = intent.getStringExtra("url");
        if (url == null) {
            sendBroadcast("Error");
            stopSelf(startId);
        } else {
            try {
                sendBroadcast(new DownloadAsyncTask(null).execute(url).get());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
            return START_NOT_STICKY;
    }

    public String download(String url) throws IOException {
        Bitmap mIcon11 = null;
        String path = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
            path = save(mIcon11, "test" + (int) (Math.random() * 1000));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return path;
    }

    public String save(Bitmap bitmap, String name) {
        FileOutputStream Stream;
        try {
            Stream = this.getApplicationContext().openFileOutput(name, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, Stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return getApplicationContext().getFileStreamPath(name).getAbsolutePath();
    }

    public void sendBroadcast(String message) {
        sendBroadcast(new Intent("broadcast").putExtra("Message", message));
    }
}
```
```csharp
public class MainActivity3 extends AppCompatActivity {
    ActivityMainBinding binding;
    BroadcastReceiver br;
    Messenger boundServiceMessenger = null;
    private Boolean connected = false;
    Messenger messenger = new Messenger(new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                binding.textView.setText(msg.getData().getString("answer"));
            }
            super.handleMessage(msg);
        }
    });
    private ServiceConnection ServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            boundServiceMessenger = new Messenger(service);
            connected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            boundServiceMessenger = null;
            connected = false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.d("MainActivity3", "3");
        binding.button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Message message = Message.obtain(null, 1);
                        Bundle data = new Bundle();
                        data.putString("url", "https://wallbox.ru/wallpapers/preview/201432/8b74eb4d1393499.jpg");
                        message.replyTo=messenger;
                        message.setData(data);
                        try {
                            boundServiceMessenger.send(message);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                });
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message= intent.getStringExtra("Message");
                binding.textView.setText(message);
            }
        };
        registerReceiver(br, new IntentFilter("broadcast"));
        binding.button2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("thread", Thread.currentThread().getName());
                        Intent intent = new Intent(MainActivity3.this, ImageDownloadServiceTask3.class).putExtra("url", "https://wallbox.ru/wallpapers/preview/201432/8b74eb4d1393499.jpg");
                        startService(intent);
                    }
                }
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(MainActivity3.this, ImageDownloadServiceTask3.class);
        bindService(intent, ServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(connected){
            unbindService(ServiceConnection);
            connected=false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(br);
    }
}
```

# Выводы
