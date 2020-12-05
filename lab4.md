# Лабораторная работа №4. RecyclerView.
# Цели
* Ознакомиться с принципами работы adapter-based views.
* Получить практические навки разработки адаптеров для view
## Задачи
* Знакомсотво с библиотекой (unit test).
Ознакомиться со strict mode библиотеки, проиллюстрировав его работу unit-тестом.
* Знакомство с RecyclerView.
Написать Android приложение, которое выводит все записи из bibtex файла на экран, используя предложенную библиотеку и RecyclerView.
* Бесконечный список.
Сделать список из предыдущей задачи бесконечным: после последнего элемента все записи повторяются, начиная с первой.

### Задача 1. Знакомсотво с библиотекой (unit test)
Ознакомьтесь со strict mode библиотеки, проиллюстрировав его работу unit-тестом.
1. Напишите несколько тестов с помощью которых вы можете сами себя проверить, что правильно поняли, как работает флаг `BibConfig#strict`(в `BibDatabaseTest.java`).

#### Решение
в библиотеке есть два режима (**normal** и **strict**), в **strict mode** невозможно перейти дальше **maxValid**, для прохождения теста нужно было либо перейти к (maxvalid-1), либо перехватить исключение
#### strictModeThrowsException()
```csharp
 @Test
  public void strictModeThrowsException1() {
    BibConfig cfg = database.getCfg();
    cfg.strict = true;
    cfg.maxValid = 21;
    BibEntry first = database.getEntry(0);
    for (int i = 0; i < cfg.maxValid - 1; i++) {
      BibEntry unused = database.getEntry(0);
      Assert.assertNotNull("Should not throw any exception @" + i, first.getType());
    }
  }

  @Test
  public void strictModeThrowsException2() {
    BibConfig cfg = database.getCfg();
    cfg.strict = true;
    BibEntry first = database.getEntry(0);
    for (int i = 0; i < cfg.maxValid+1; i++) {
      BibEntry unused = database.getEntry(0);
      try {
        Assert.assertNotNull("Should not throw any exception @" + i, first.getType());
      } catch (IllegalStateException e) {
        System.out.println("Exception caught => " + e.getMessage());
      }
    }
```
2. Напишите несколько тестов с помощью которых вы можете сами себя проверить, что правильно поняли, как работает флаг `BibConfig#shuffle` (в `BibDatabaseTest.java`).

`Shuffle()` перемешивает записи, для проверки работы, был создан создан файл shuffle.bib, содержащий записи, потом был создан две database (lastDatabase и newDatabase), их перемешивали через `shuffle` и сравнили их **title**, если они разные, то `shuffle` хорошо работает.
```csharp
  @Test
  public void shuffleFlag() throws IOException {
    BibConfig cfgLast = lastDatabase.getCfg();
    cfgLast.shuffle = true;

    boolean verify = false;

    for (int i = 0; i < 10; i++) {
      try (InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream("/articles.bib"))) {
        newDatabase = new BibDatabase(reader);
      }
      BibConfig cfgNew = newDatabase.getCfg();
      cfgNew.shuffle = true;
      for (int j = 0; j < lastDatabase.size(); j++) {
        if(!lastDatabase.getEntry(0).getField(Keys.TITLE)
                .equals(newDatabase.getEntry(0).getField(Keys.TITLE))) {
          verify = true;
        }
      }
      assertTrue(verify);
    }
  } 
```
### Задача 2. Знакомство с RecyclerView.
Напишите Android приложение, которое выводит все записи из bibtex файла на экран, используя предложенную библиотеку и `RecyclerView`.

#### Решение
после импорта jar file , библиотека была подключена к project

`settings.gradle`
```
include ':biblib'
```
Чтобы добавить dependency в проект, укажили  конфигурацию dependency, такую как implementation, в блоке dependencies файла build.gradle.
`build.gradle.`
```
dependencies {
    implementation project (':biblib')
```

#### **что такое RecyclerView ?**

RecyclerView позволяет легко и эффективно отображать большие наборы данных.
Достойный преемник ListView и GridView, RecyclerView является более эффективным шаблоном и позволяет настраивать больше, чем эти предшественники.

Для работы требуется три обязательных компонента при реализации :

1. Приспособление ([RecyclerView.Adapter](https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html)): привязка (Bind) между представлением RecyclerView и списком данных.

2. Один LayoutManager ( [RecyclerView.LayoutManager](https://developer.android.com/reference/android/support/v7/widget/RecyclerView.LayoutManager.html) ): позволяет правильно расположить все данные в списке.

3. Один ViewHolder ([RecyclerView.ViewHolder](https://developer.android.com/reference/android/support/v7/widget/RecyclerView.ViewHolder.html)) - позволяет визуально представлять элемент списка данных в RecyclerView (одна строка).

#### Реализовать RecyclerView
Теперь давайте изменим layout нашего Activity Mainactivity, чтобы добавить к нему RecyclerView.

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recycler_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</LinearLayout>
```
Здесь мы полностью изменили макет Activity MainActivity. Который теперь будет содержать только RelativeLayout, который сам будет содержать RecyclerView, занимающий все доступное место на экране.

Затем мы создадим XML-макет, представляющий каждый элемент (каждую строку) нашего RecyclerView.

`biblib.xml`
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="16dp">

    <TextView
        android:id="@+id/title"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textColor="@android:color/black"
        android:textSize="20sp"
        tools:text="Title" />

    <TextView
        android:id="@+id/author"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:layout_marginBottom="10dp"
        android:textColor="@android:color/black"
        android:textSize="16sp"
        tools:text="Author" />

    <TextView
        android:id="@+id/JournalAndYear"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textColor="@android:color/black"
        android:textSize="16sp"
        tools:text="Journal and year" />

</LinearLayout>
```
**Теперь напишем MainActivity и Adapter**
в MainActivity определяем нашего LayoutManager
здесь `LinearLayoutManager`, но можно выбрать другой (`GridLayoutManager`, `StaggeredGridLayoutManager`)

`MainActivity`
```
class MainActivity : AppCompatActivity() {
    private var adapter: Adapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val articles = resources.openRawResource(R.raw.articles)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val manager = LinearLayoutManager(this)

        recyclerView.layoutManager = manager

        val itemDecoration: ItemDecoration = DividerItemDecoration(recyclerView.context, manager.orientation)
        recyclerView.addItemDecoration(itemDecoration)

        try {
            adapter = Adapter(articles)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        recyclerView.adapter = adapter
    }
}
```
##### Adapter
в `Adapter` у нас:

`Un ViewHolder` в котором есть, то что будем показывать на экране

`onCreateViewHolder()` Это позволяет нам создать ViewHolder из XML-макета, представляющего каждую строку RecyclerView

`getItemCount()` Это позволяет нам создать ViewHolder из XML-макета, представляющего каждую строку RecyclerView

`onBindViewHolder()` Этот метод вызывается для каждой из видимых строк, отображаемых в нашем RecyclerView.  Обычно здесь обновляется их внешний вид.

```
class Adapter(inputStream: InputStream): RecyclerView.Adapter<Adapter.ViewHolder>() {
    private var database: BibDatabase

    init {
        InputStreamReader(inputStream).use { reader ->
            database = BibDatabase(reader)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val author: TextView = view.findViewById(R.id.author)
        val journal: TextView = view.findViewById(R.id.JournalAndYear)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.biblib, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return database.size()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry: BibEntry = database.getEntry(position)
        holder.title.text = entry.getField(Keys.TITLE)
        holder.author.text = entry.getField(Keys.AUTHOR)
        holder.journal.text = entry.getField(Keys.JOURNAL)
    }
}
```
#### Задача 3. Бесконечный список.
Для получения бесконечного списка сделаем так, чтобы после последней записи шла первая, для этого сделаем так чтобы метод выдающий количество записей выдавал просто большое число и в методе где получаем запись будем брать запись по остатку от деления всего количества записей.

```
    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry: BibEntry = database.getEntry(position % database.size())
        holder.title.text = entry.getField(Keys.TITLE)
        holder.author.text = entry.getField(Keys.AUTHOR)
        holder.journal.text = entry.getField(Keys.JOURNAL)
    }
```
### Выводы
##### приобретенные знания:
1. чтение, запись и тестирование библиотеки
2. создание jar-файла и импортировать его в проект существующего
3. Что такое RecyclerView, как реализовать его в проекте
4. как сделать Бесконечный список.
