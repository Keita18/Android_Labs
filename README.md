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
