# Android_Labs
# Лабораторная работа №6. Многопоточные Android приложения.

## Цели
Получить практические навыки разработки многопоточных приложений:
1. Организация обработки длительных операций в background (worker) thread:
    * Запуск фоновой операции (coroutine/asynctask/thread)
    * Остановка фоновой операции (coroutine/asynctask/thread)
1. Публикация данных из background (worker) thread в main (ui) thread.

Освоить 3 основные группы API для разработки многопоточных приложений:
1. Kotlin Coroutines
1. AsyncTask
1. Java Threads

## Задачи
### Задача 1. Альтернативные решения задачи "не секундомер" из Лаб. 2
Используйте приложение "не секундомер", получившееся в результате выполнениня [Лабораторной работы №2](../02/TASK.md). Разработайте несколько альтернативных приложений "не секундомер", отличающихся друг от друга организацией многопоточной работы. Опишите все известные Вам решения.

### 1. Threads

### Решение
 добавим прерывание потоку при остановке с помощью intrrupt() в код из lab2
 
 ```csharp
 class SimpleThread : AppCompatActivity() {
    var secondsElapsed: Int = 0
    var work = true
    var backgroundThread: Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("FirstActivity::Create()")

    }

    override fun onResume() {
        super.onResume()
        println("FirstActivity::onResume()")

        backgroundThread = Thread {
            while (!Thread.currentThread().isInterrupted) {
                Thread.sleep(1000)
                textSecondsElapsed.post {
                    textSecondsElapsed.text = "Seconds elapsed: " + secondsElapsed++
                }
                if (!work) {
                    println("Thread interrupted")
                    Thread.currentThread().interrupt()
                }
            }
        }
        backgroundThread!!.start()
        work = true

    }

    override fun onPause() {
        super.onPause()
        println("FirstActivity::onPause()")

        work = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("seconds", secondsElapsed)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        secondsElapsed = savedInstanceState.getInt("seconds")
        textSecondsElapsed.post {
            textSecondsElapsed.text = "Seconds elapsed: $secondsElapsed"
        }
    }
    override fun onStart() {
        super.onStart()
        println("FirstActivity::onStart()")
    }

    override fun onStop() {
        super.onStop()
        println("FirstActivity::onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        println("FirstActivity::onRestart()")

    }

    override fun onDestroy() {
        super.onDestroy()
        println("FirstActivity::onDestroy()")
    }
}
 ```
 
### AsyncTask
AsyncTask позволяет аккуратно и легко выполнять операции параллельно потоку пользовательского интерфейса

`onPreExecute()` запускается с самого начала выполнения, еще до начала работы. Таким образом, он используется для инициализации различных элементов, которые должны быть инициализированы.

`doInBackground` именно в этом методе должна выполняться фоновая работа.

`onPostExecute` выполняется после doInBackground (не срабатывает в случае, если AsyncTask был отменен), имеет доступ к UI

```csharp
class AsyncTask : AppCompatActivity() {
    var secondsElapsed: Int = 0
    private lateinit var state: SharedPreferences
    private lateinit var timerAsyncTask: TimerAsyncTask


     inner class TimerAsyncTask : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            while (!isCancelled) {
                TimeUnit.SECONDS.sleep(1)
                publishProgress()
            }
            return null
        }

        override fun onProgressUpdate(vararg values: Void?) {
            super.onProgressUpdate(*values)
            textSecondsElapsed.text = "Seconds elapsed: " + secondsElapsed++
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        state = applicationContext.getSharedPreferences(
            "state",
            MODE_PRIVATE
        )
    }


    override fun onStart() {
        super.onStart()
        if (state.contains("seconds")) {
            secondsElapsed = state.getInt("seconds", 0)
            textSecondsElapsed.post {
                textSecondsElapsed.text = "Seconds elapsed: " + secondsElapsed++
            }
        }
    }

    override fun onResume() {
        timerAsyncTask = TimerAsyncTask()
        timerAsyncTask.execute()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        state.edit().putInt("seconds", secondsElapsed).apply()
    }

}
```
### Coroutines
Корутина: блок обработки для выполнения неблокирующего асинхронного кода. В принципе, Корутина “облегченный поток".

В onResume запускаем таймер, который каждую секунду будет обновляться счётчик времени.
CoroutineScope - область действия корутины, GlobalScope = CoroutineScope без привязки к Job. Dispatcher определяет какой поток будет использоваться корутиной:
.launch запускает корутину

```csharp
class Coroutines : AppCompatActivity() {
    var secondsElapsed: Int = 0
    private lateinit var state: SharedPreferences
    private lateinit var timer: Job
    private val Scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        state = applicationContext.getSharedPreferences(
            "state",
            MODE_PRIVATE
        )
    }

    override fun onStart() {
        super.onStart()
        if (state.contains("seconds")) {
            secondsElapsed = state.getInt("seconds", 0)
            textSecondsElapsed.post {
                textSecondsElapsed.text = "Seconds elapsed: " + secondsElapsed++
            }
        }
    }

    override fun onResume() {
        timer = Scope.launch {
            while (true) {
                delay(1000)
                textSecondsElapsed.text = "Seconds elapsed: " + secondsElapsed++
            }
        }
        super.onResume()
    }

    override fun onPause() {
        timer.cancel()
        super.onPause()
    }

}
```
### Задача 2. Загрузка картинки в фоновом потоке (AsyncTask) 
Создайте приложение, которое скачивает картинку из интернета и размещает ее в `ImaveView` в `Activity`. За основу возьмите [код со StackOverflow](https://stackoverflow.com/a/9288544).

### Решение
добавим кнопку, при нажатии на которую будет скачиваться картинка.

```csharp
public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DownloadImageTask(binding.ImageView)
                                .execute("https://wallbox.ru/wallpapers/preview/201432/8b74eb4d1393499.jpg");
                    }
                });
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            binding.ImageView.setImageBitmap(result);
            binding.button.setVisibility(View.INVISIBLE);
        }
    }
}
```

### Задача 3. Загрузка картинки в фоновом потоке (Kotlin Coroutines) 
Перепишите предыдущее приложение с использованием Kotlin Coroutines.

### Решение
Диспетчеры.IO - Этот диспетчер оптимизирован для выполнения дискового или сетевого ввода-вывода вне основного потока. Примеры включают использование компонента Room, чтение или запись в файлы, а также выполнение любых сетевых операций.

```csharp
class Coroutine : AppCompatActivity() {
    private val Scope = CoroutineScope(Dispatchers.Main)
    private fun DownloadImageTask(url: String): Bitmap? {
        var bitmap: Bitmap? = null

        try {
            val inputStream = URL(url).openStream()
            bitmap = BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            Log.e("Error", e.message.orEmpty())
            e.printStackTrace()
        }

        return bitmap
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            binding.button.visibility = View.INVISIBLE

            Scope.launch(Dispatchers.IO) {
                val image = DownloadImageTask("https://prodigits.co.uk/thumbs/wallpapers/p2ls/fun/34/b2b2870312587092.jpg")
                launch(Dispatchers.Main) {
                    binding.ImageView.setImageBitmap(image)
                }
            }
        }
    }
}
```

### Задача 4. Использование сторонних библиотек 
Многие "стандартные" задачи имеют "стандартные" решения. Задача скачивания изображения в фоне возникает настолько часто, что уже сравнительно давно решение этой задачи занимает всего лишь несколько строчек. Убедитесь в этом на примере одной (любой) библиотеки [Glide](https://github.com/bumptech/glide#how-do-i-use-glide), [picasso](https://square.github.io/picasso/) или [fresco](https://frescolib.org/docs/index.html).

### Решение
Будем использовать Picasso

```csharp
public class PicassoLib extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Picasso.with(PicassoLib.this).load("https://nakleykiavto.ru/upload/iblock/62d/62d7552304d1115bdf0f6d2f5c47457b.png").into(binding.ImageView);
                        binding.button.setVisibility(View.INVISIBLE);
                    }
                });
    }

}
```

# Вывод
В этом Lab6 мы узнали о работе в фоновом режиме с потоками и несколько простых правил: 
1. Никогда не блокируйте поток пользовательского интерфейса (UI).
1. Не манипулировать стандартными представлениями вне потока пользовательского интерфейса...
