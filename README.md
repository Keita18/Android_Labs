# Android_Labs
# Лабораторная работа №5. UI Tests.

## Цели
1. Ознакомиться с принципами и получить практические навыки разработки UI тестов для Android приложений.

## Задачи
Предполагается, что все задачи решаются с помощью Instrumentation (Android) tests и Espresso Framework, если иное явно не указано в тексте задания.

### Задача 1. Простейший UI тест
Ознакомьтесь с Espresso Framework: https://developer.android.com/training/testing/espresso. Разработайте приложение, в котором есть одна кнопка (`Button`) и одно текстовое поле (`EditText`). При (первом) нажатии на кнопку текст на кнопке должен меняться.

Напишите Espresso тест, который проверяет, что при повороте экрана содержимое текстового поля (каким бы оно ни было) сохраняется, а надпись на кнопке сбрасывается в исходное состояние. 
### Решение
Для начала напишем само приложение.
`MyActivity`
```csharp
class MyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)

        val button: Button = findViewById(R.id.activity_main_btn)
        button.setOnClickListener{
            button.text = getString(R.string.won)
        }
    }
}
```
`MyActivity.xml`
```csharp
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:orientation="vertical">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginBottom="30dp"
        android:text="type text, click and turn the screen"/>

    <EditText
        android:id="@+id/activity_main_text_input"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginStart="20dp"
        android:layout_marginEnd="20dp"
        android:inputType="textAutoComplete"
        android:hint="Please type text"/>

    <Button
        android:id="@+id/activity_main_btn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:text="Let's play"
        android:padding="30dp"
        android:layout_marginTop="20dp"/>


</LinearLayout>
```
запустим приложене и посмотрим, как оно работает.

До нажатия кнопки

![](https://github.com/Keita18/Android_Labs/blob/lab5/img/Screenshot%202020-12-15%20224549.jpg)

После нажатия на кнопку 

![](https://github.com/Keita18/Android_Labs/blob/lab5/img/Screenshot%202020-12-15%20224654.jpg)

Теперь проверим что произойдёт с текстовым полем при повороте экрана

Экран до поворота

![](https://github.com/Keita18/Android_Labs/blob/lab5/img/Screenshot%202020-12-15%20224807.jpg)

Экран после поворота

![](https://github.com/Keita18/Android_Labs/blob/lab5/img/Screenshot%202020-12-15%20224838.jpg)
#### Espresso Test
```csharp
class MyActivityTest {
    @get:Rule
    val mActivityRule = ActivityScenarioRule(MyActivity::class.java)

    @Test
    fun mainTest() {

        /** проверим, что на кнопке написано "Let's play" */
        onView(withId(R.id.activity_main_btn)).check(matches(withText("Let's play")))

        /** напишем "Jack" в текстовое поле */
        onView(withId(R.id.activity_main_text_input)).perform(typeText("Jack"), closeSoftKeyboard())

        /** проверим, что в текстовое поле написалось "Jack" */
        onView(withId(R.id.activity_main_text_input)).check(matches(withText("Jack")))

        /** нажимаем на кнопку */
        onView(withId(R.id.activity_main_btn)).perform(click())

        /** проверяем изменился ли текст кнопки на "You Won"*/
        onView(withId(R.id.activity_main_btn)).check(matches(withText(R.string.won)))

        /** делаем поворот экрана  */
        mActivityRule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        /** проверим, что на кнопке написано "Let's play" */
        onView(withId(R.id.activity_main_btn)).check(matches(withText("Let's play")))
        
        /** проверим, что в текстовое поле написано "Jack" */
        onView(withId(R.id.activity_main_text_input)).check(matches(withText("Jack")))

    }
}
```
### Задача 2. Тестирование навигации.
Возьмите приложение из Лаб №3 о навигации (любое из решений). Напишите UI тесты, проверяющие навигацию между 4мя исходными Activity/Fragment (1-2-3-About). В отчете опишите, что проверяет каждый тест.

### Решение
напишем тест, в котором проверяется, что мы находимся действительно в той activity которую предполагаем : то есть проверяем наличие\отсутствие кнопок,menu-about ...

```csharp


class Task2Test {
    @get:Rule
    val activityRule = ActivityScenarioRule(FirstActivity::class.java)
    private fun activityChecker(
        butExist1: Matcher<View>, butExist2: Matcher<View>,
        butNot1: Matcher<View>, butNot2: Matcher<View>,
        butNot3: Matcher<View>, isFirst: Boolean = false
    ) {

        onView(butExist1).check(matches(isDisplayed()))
        
        if (!isFirst) onView(butExist2).check(matches(isDisplayed()))
        else onView(butExist2).check(doesNotExist())


        onView(butNot1).check(doesNotExist())
        onView(butNot2).check(doesNotExist())
        onView(butNot3).check(doesNotExist())
        toAboutFromMenu()
    }

    private fun toAboutFromMenu() {
        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)
        onView(ViewMatchers.withText("About")).perform(ViewActions.click())
        pressBack()
    }

    @Test
    fun checkActivities() {

        val but1To2: Matcher<View> = withId(R.id.activity_first_toSecond_btn)
        val but2To1: Matcher<View> = withId(R.id.activity_second_toFirst_btn)
        val but2to3: Matcher<View> = withId(R.id.activity_second_toThird_btn)
        val but3To1: Matcher<View> = withId(R.id.activity_third_toFirst_btn)
        val but3To2: Matcher<View> = withId(R.id.activity_third_toSecond_btn)

        //check firstActivity with about
        activityChecker(but1To2, but2To1, but2to3, but3To1, but3To2, true)

        //check secondActivity with about
        onView(but1To2).perform(ViewActions.click())
        activityChecker(but2To1, but2to3, but3To1, but3To2, but1To2)

        //check thirdActivity with about
        onView(but2to3).perform(ViewActions.click())
        activityChecker(but3To1, but3To2, but1To2, but2To1, but2to3)

        /**  go back an check */
        //check secondActivity with about
        onView(but3To2).perform(ViewActions.click())
        activityChecker(but2To1, but2to3, but3To1, but3To2, but1To2)

        //check firstActivity with about
        onView(but2To1).perform(ViewActions.click())
        activityChecker(but1To2, but2To1, but2to3, but3To1, but3To2, true)

        /** let's check backStack and but3to1 */
        onView(but1To2).perform(ViewActions.click())
        onView(but2to3).perform(ViewActions.click())
        onView(but3To1).perform(ViewActions.click())
        activityChecker(but1To2, but2To1, but2to3, but3To1, but3To2, true)

        onView(but1To2).perform(ViewActions.click())
        onView(but2to3).perform(ViewActions.click())
        onView(but3To1).perform(ViewActions.click())

        onView(but1To2).perform(ViewActions.click())
        onView(but2to3).perform(ViewActions.click())
        onView(but3To1).perform(ViewActions.click())
        //pressBack()
    }
}
```
### Вывод
`приобретенные знания:`

Структура теста эспрессо практически всегда будет одинаковой :

`onView()` : Это точка входа в наш тест. Это позволит нам позже определить, на каком View мы хотим провести тест.

`withId()` : Это ViewMatchers, который позволяет точно определить представление (view), на котором мы хотим выполнить тест.

`perform(click())` : Метод **perform()** позволит нам выполнять действия типа **ViewActions**, такие как метод **click()**. Конечно, есть много других, как вы можете видеть в классе [ViewActions](https://developer.android.com/reference/android/support/test/espresso/action/ViewActions.html) !

`check(matches(isDisplayed()))` : Метод `check()` используется для выполнения теста типа `ViewAssertion` , например метода `Match ()`. Который выполняет метод типа [ViewMatchers](https://developer.android.com/reference/android/support/test/espresso/matcher/ViewMatchers.html#isDisplayed()), выполняющий здесь проверку `isDisplayed ()`.
