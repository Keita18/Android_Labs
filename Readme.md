
# Лабораторная работа №3. Lifecycle компоненты. Навигация в приложении.  
## Цели  
* Ознакомиться с методом обработки жизненного цикла activity/fragment при помощи Lifecycle-Aware компонентов    
* Изучить основные возможности навигации внутри приложения: созданиие новых activity, navigation graph  
  
## Задачи  
### Задача 1. Обработка жизненного цикла с помощью Lifecycle-Aware компонентов  
#### Задание  
 Ознакомьтесь с Lifecycle-AwareComponents по документации и выполните codelabs.
 
 **androidx.lifecycle** - предоставляет классы и интерфейсы, позволяющие создавать компоненты с учетом жизненного цикла, которые могут автоматически корректировать свое поведение в зависимости от текущего состояния жизненного цикла действия или фрагмента.
 
 **LifecycleOwner** - это интерфейс, реализованный классами AppCompatActivity и Fragment. Мы можем подписать другие компоненты на объекты-владельцы, которые реализуют данный интерфейс, чтобы наблюдать за изменениями в жизненном цикле владельца.
 
 **LiveData** - позволяет наблюдать за изменениями данных в нескольких компонентах приложения, не создавая явных жёстких путей зависимости между ними. Он учитывает сложные жизненные циклы компонентов приложения, включая действия, фрагменты, службы или любой LifecycleOwner, определённый в нашем приложении. LiveData управляет подписками наблюдателей, приостанавливая подписки на остановленные объекты LifecycleOwner и отменяя подписки на завершённые объекты LifecycleOwner.
 
 ### Задача 2. Навигация (startActivityForResult)
#### Задание
Реализуйте навигацию между экранами одного приложения согласно изображению ниже с помощью `Activity`, `Intent` и метода `startActivityForResult`.
![эскиз](activities.svg)
Во всех вариантах Activity 'About' должна быть доступна из любой другой Activity одним из способов согласно варианту.

Во всех вариантах Activity 'About' должна быть доступна из любой другой Activity c способом: [Options Menu](https://developer.android.com/guide/topics/ui/menus#options-menu)
### Решение
Чтобы начать новое Activity, Android предлагает метод `startActivity ()`. 
```
public void startActivity(Intent intent)
```
Этот метод позволяет взаимодействовать с операционной системой Android, запрашивая ее для запуска определенного Activity. Чтобы указать, какую активность запустить, используется конкретный объект: [Intent](https://developer.android.com/reference/android/content/Intent). При вызове метода **startActivity()** внутренний объект Android **ActivityManager** проверяет содержимое объекта **Intent** и запускает соответствующее действие.
 
 метод `finish ()` позволяет просто сказать системе : я закончил с текущей деятельностью, остановите ее и верните меня к предыдущей деятельности.
 
 мы хотим запустить thirdActivity и ожидаем от него результата, который позволит нам узнать, нужно ли закрывать secondActivity, чтобы вернуться к firstActivity. Для этого используется метод `startActivityForResult ()`.
 `startActivityForResult()` позволяет при этом вытащить результат Activity.
 
via menuOptions ActivityAbout была создана которая доступна из всех Activity через Options Menu.

### Задача 3. Навигация (флаги Intent/атрибуты Activity)
#### Задание
Решите предыдущую задачу с помощью Activity, Intent и флагов Intent либо атрибутов Activity.

#### Указания
Не используйте `startActivityForResult` в этом задании.
### Решение

`FLAG_ACTIVITY_CLEAR_TOP` in Android

Это означает следующее: допустим, у нас есть 4 вида Activities, А, В, С и D, и поток
A -> B -> C -> D

и теперь, когда вы находитесь на D, вы хотите запустить действие B (из стека, а не из нового экземпляра), тогда вы можете использовать этот флаг намерения. Кроме того, он удаляет все остальные действия поверх B (здесь C и D).

### Задача 4. Навигация (флаги Intent/атрибуты Activity)
Дополните граф навигации новым(-и) переходом(-ами) с целью демонстрации какого-нибудь (на свое усмотрение) атрибута Activity или флага Intent, который еще не использовался для решения задачи. Поясните пример и работу флага/атрибута.
### Решение
`FLAG_ACTIVITY_NO_HISTORY`. Его работу можно продемонстрировать путём перехода в любую activity и нажатием «назад». При нажатии назад, флаг будет возвращать каждый раз к 1 activity, то есть не сохранять историю передвижения по activity.

Существует также `FLAG_ACTIVITY_REORDER_TO_FRONT`:

"Например, рассмотрим задачу, состоящую из четырех действий: A, B, C, D. Если D вызывает startActivity() с намерением, которое разрешает компонент действия B, то B будет выведен на передний план стека истории с таким результирующим порядком: A, C, D, B. Этот флаг будет проигнорирован, если также указан FLAG_ACTIVITY_CLEAR_TOP."

### Задача 5. Навигация (Fragments, Navigation Graph) 
Решите предыдущую задачу (с расширенным графом) с использованием navigation graph. Все Activity должны быть заменены на фрагменты, кроме Activity 'About', которая должна остаться самостоятельной Activity.

Фрагмент можно представить как часть (или... осколок ! : D) многоразовый наш графический интерфейс. Таким образом, на практике действие будет разделено на один или несколько фрагментов, чтобы создать настраиваемый и, прежде всего, гибкий пользовательский интерфейс

* Fragments-это не Activities. Однако они не могут работать сами по себе и должны быть всегда размещены внутри деятельности.

* Вы будете работать с фрагментами так же, как с Activities : у них есть жизненный цикл (onCreate, onResume и т. д.)...) и макет (fragment_main.xml, например).

* Fragments тесно связаны с Activities, их жизненный цикл тоже. Если действие будет уничтожено (например, вращение экрана), фрагменты также будут уничтожены. Однако вы можете добавлять и удалять фрагменты в горячем действии (без влияния на жизненный цикл действия).

мы создали MainActivity, в которой находится NavController, который помогает переходить между activity.Nav_host_fragment является контейнером для фрагментов и они переключаются внутри него.
Чтобы переключаться между фрагментами нужно заполнить nav_graph, а также указать переходы между фрагментами. 

#### Task2.firstActivity
```csharp
class FirstActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        println("FirstActivity::Create()")

        binding.activityFirstToSecondBtn.setOnClickListener {
            val secondActivityIntent = Intent(this, SecondActivity::class.java)
            startActivity(secondActivityIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about_button -> {
                startActivity(Intent(this, AboutActivity::class.java))
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    override fun onStart() {
        super.onStart()
        println("FirstActivity::onStart()")
    }

    override fun onResume() {
        super.onResume()
        println("FirstActivity::onResume()")
    }

    override fun onPause() {
        super.onPause()
        println("FirstActivity::onPause()")
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
#### Task2.SecondActivity
```csharp
class SecondActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    val THIRD_ACTIVITY_REQUEST_CODE = 42

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        println("SecondActivity::onCreate()")


        binding.activitySecondToFirstBtn.setOnClickListener { finish() }
        binding.activitySecondToThirdBtn.setOnClickListener {

            val thirdActivityIntent = Intent(this, ThirdActivity::class.java)

            startActivityForResult(thirdActivityIntent, THIRD_ACTIVITY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (THIRD_ACTIVITY_REQUEST_CODE == requestCode && Activity.RESULT_OK == resultCode) {

            val backToFirstActivity = data!!.getBooleanExtra(ThirdActivity().EXTRA_BACK_TO_FIRST, false)
            if (backToFirstActivity) finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about_button -> {
                startActivity(Intent(this, AboutActivity::class.java))
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    override fun onStart() {
        super.onStart()
        println("SecondActivity::onStart()")
    }

    override fun onResume() {
        super.onResume()
        println("SecondActivity::onResume()")
    }

    override fun onPause() {
        super.onPause()
        println("SecondActivity::onPause()")
    }

    override fun onStop() {
        super.onStop()
        println("SecondActivity::onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        println("SecondActivity::onRestart()")

    }

    override fun onDestroy() {
        super.onDestroy()
        println("SecondActivity::onDestroy()")
    }
}

```
#### Task2.ThirdActivity
```csharp
class ThirdActivity: AppCompatActivity() {
    private lateinit var binding: ActivityThirdBinding
    var BACK_TO_FIRST = false
    val EXTRA_BACK_TO_FIRST = "EXTRA_BACK_TO_FIRST"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        println("ThirdActivity::onCreate()")

        binding.activityThirdToFirstBtn.setOnClickListener {

            BACK_TO_FIRST = true
            // End the activity
            val intent = Intent()
            intent.putExtra(EXTRA_BACK_TO_FIRST, BACK_TO_FIRST)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        binding.activityThirdToSecondBtn.setOnClickListener { finish() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about_button -> {
                startActivity(Intent(this, AboutActivity::class.java))
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()
        println("ThirdActivity::onStart()")
    }

    override fun onResume() {
        super.onResume()
        println("ThirdActivity::onResume()")
    }

    override fun onPause() {
        super.onPause()
        println("ThirdActivity::onPause()")
    }

    override fun onStop() {
        super.onStop()
        println("ThirdActivity::onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        println("ThirdActivity::onRestart()")

    }

    override fun onDestroy() {
        super.onDestroy()
        println("ThirdActivity::onDestroy()")
    }
}
```
#### Task2.AboutActivity

```csharp
class AboutActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        println("AboutActivity::onCreate()")

    }


    override fun onStart() {
        super.onStart()
        println("AboutActivity::onStart()")
    }

    override fun onResume() {
        super.onResume()
        println("AboutActivity::onResume()")
    }

    override fun onPause() {
        super.onPause()
        println("AboutActivity::onPause()")
    }

    override fun onStop() {
        super.onStop()
        println("AboutActivity::onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        println("AboutActivity::onRestart()")

    }

    override fun onDestroy() {
        super.onDestroy()
        println("AboutActivity::onDestroy()")
    }
}
```

#### Task3.thirdActivity
```csharp
class ThirdActivity: AppCompatActivity() {
    private lateinit var binding: ActivityThirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        println("ThirdActivityTask3::onCreate()")

        binding.activityThirdToFirstBtn.setOnClickListener {

            val firstActivityIntent = Intent(this, FirstActivity::class.java)
            startActivity(firstActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }
        binding.activityThirdToSecondBtn.setOnClickListener { finish() }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about_button -> {
                startActivity(Intent(this, AboutActivity::class.java))
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    override fun onStart() {
        super.onStart()
        println("ThirdActivityTask3::onStart()")
    }

    override fun onResume() {
        super.onResume()
        println("ThirdActivityTask3::onResume()")
    }

    override fun onPause() {
        super.onPause()
        println("ThirdActivityTask3::onPause()")
    }

    override fun onStop() {
        super.onStop()
        println("ThirdActivityTask3::onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        println("ThirdActivityTask3::onRestart()")

    }

    override fun onDestroy() {
        super.onDestroy()
        println("ThirdActivityTask3::onDestroy()")
    }
} 
```

#### Task4.thirdActivity

```csharp
class D: AppCompatActivity() {
    private lateinit var binding: ActivityFourthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("Activity B::onCreate()")
        binding = ActivityFourthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityDToABtn.setOnClickListener {
            startActivity(Intent(this, A::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }

        binding.activityDToBBtn.setOnClickListener{
            startActivity(Intent(this, B::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        }

        binding.activityDToCBtn.setOnClickListener {
            startActivity(Intent(this, D::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY))
        }
    }

    override fun onStart() {
        super.onStart()
        println("Activity D::onStart()")
    }

    override fun onResume() {
        super.onResume()
        println("Activity D::onResume()")
    }

    override fun onPause() {
        super.onPause()
        println("Activity D::onPause()")
    }

    override fun onStop() {
        super.onStop()
        println("Activity D::onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        println("Activity D::onRestart()")

    }

    override fun onDestroy() {
        super.onDestroy()
        println("Activity D::onDestroy()")
    }
} 
```
#### Task5.MainActivity
```csharp
class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about_button -> {
                startActivity(Intent(this, AboutActivity::class.java))
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
```
#### Task5.Fragment1
```csharp
class Fragment1 : Fragment() {
    private lateinit var binding: Fragment1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = Fragment1Binding.inflate(layoutInflater)

        binding.fragment1To2.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_firstFragment_to_secondFragment)
        }
        return binding.root
    }
}
```
#### Task5.Fragment2
```csharp
class Fragment2 : Fragment() {

    private lateinit var binding: Fragment2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = Fragment2Binding.inflate(layoutInflater)
        binding.fragment2To1.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_secondFragment_to_firstFragment)
        }
        binding.fragment2To3.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_secondFragment_to_thirdFragment)
        }
        return binding.root
    }
}
```
#### Task5.Fragment3
```csharp
class Fragment3 : Fragment() {

    private lateinit var binding: Fragment3Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = Fragment3Binding.inflate(layoutInflater)

        binding.fragment3To1.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_thirdFragment_to_firstFragment)
        }
        binding.fragment3To2.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_thirdFragment_to_secondFragment)
        }
        return binding.root
    }
}
```
