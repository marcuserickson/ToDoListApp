package com.thundergodsoftware.todolistapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ArrayList<HashMap<String,String>> list;
    private ListView listEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        // load default events and categories
        DataSourceCategory categoryDataSource = new DataSourceCategory(this.getBaseContext());
        int categoryCount = categoryDataSource.getCategoryCount();
        if ( categoryCount == 0 ) {
            Category cat1 = new Category( "Home");
            categoryDataSource.createCategory(cat1);
            Category cat2 = new Category( "Bills");
            categoryDataSource.createCategory(cat2);
            Category cat3 = new Category( "Pool");
            categoryDataSource.createCategory(cat3);
            Category cat4 = new Category( "Car");
            categoryDataSource.createCategory(cat4);
        }
        DataSourceTodoItem todoItemDataSource = new DataSourceTodoItem(this.getBaseContext());
        int todoItemCount = todoItemDataSource.getTodoItemCount();
        if ( todoItemCount == 0 ) {
            long categoryId = categoryDataSource.getCategoryId("Pool");
            TodoItem td1 = new TodoItem(categoryId, "Add Salt", TodoItem.Recurrence.REPEATED, 0, 1 );
            todoItemDataSource.createTodoItem(td1);

            categoryId = categoryDataSource.getCategoryId("Bills");
            TodoItem td2 = new TodoItem(categoryId, "TDECU", TodoItem.Recurrence.REPEATED, 0, 1 );
            todoItemDataSource.createTodoItem(td2);

            categoryId = categoryDataSource.getCategoryId("Home");
            TodoItem td3 = new TodoItem(categoryId, "Add Chlorine to Septic", TodoItem.Recurrence.REPEATED, 0, 1 );
            todoItemDataSource.createTodoItem(td3);

            TodoItem td4 = new TodoItem(categoryId, "Fix Exterior Trim", new Date(2017,9,1) );
            todoItemDataSource.createTodoItem(td4);

            categoryId = categoryDataSource.getCategoryId("Bills");
            categoryId = categoryDataSource.getCategoryId("Home");
            categoryId = categoryDataSource.getCategoryId("Car");
        }

        SimpleDateFormat dtFormat = new SimpleDateFormat("M/d/yy");
        listEvents = (ListView)findViewById(R.id.listEvents);
        View headerView = View.inflate(this.getBaseContext(),R.layout.header_eventlist, null );
        listEvents.addHeaderView(headerView);

        final ArrayList<TodoItem> todoItemList = todoItemDataSource.getAllTodoItems();

        list = new ArrayList<HashMap<String,String>>();
        for ( int i=0; i<todoItemList.size(); i++) {
            TodoItem todoItem = todoItemList.get(i);
            HashMap<String,String> temp = new HashMap<String,String>();
            temp.put(TodoItem.EVENTLIST_NAME_COLUMN, todoItem.name);
            temp.put(TodoItem.EVENTLIST_DATE_COLUMN, dtFormat.format(todoItem.nextOccurrence));
            list.add(temp);
        }
        EventListAdapter adapter = new EventListAdapter(this, list );
        listEvents.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
