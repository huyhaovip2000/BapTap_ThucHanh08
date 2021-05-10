package com.example.tuan8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClickListner{
    RecyclerView rcv_name;
    CustomAdapterRecycler adt;
    List<User> mNames;
    Button btnAdd, btnRemove,btnSave;
    EditText tvSearch;
    AppDatabase db;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNames = new ArrayList<>();
        rcv_name = findViewById(R.id.rcv_name);
        tvSearch = findViewById(R.id.tvSearch);
        btnAdd = findViewById(R.id.btnAdd);
        btnRemove = findViewById(R.id.btnRemove);
        btnAdd = findViewById(R.id.btnAdd);
        btnSave = findViewById(R.id.btnSave);
        btnRemove.setEnabled(false);
        btnSave.setEnabled(false);

         db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name")
                .allowMainThreadQueries()
                .build();
         userDao = db.userDao();
//        userDao.insertAll(new User(8,"Hoan","Thai"));
        mNames= userDao.getAll();


        adt = new CustomAdapterRecycler(mNames,this);
        rcv_name.setHasFixedSize(true);
        rcv_name.setAdapter(adt);
        rcv_name.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=0;
                String name = tvSearch.getText().toString();
                for (User user:userDao.getAll()){
                    i++;
                }
                userDao.insertAll(new User(i+10,name,""));
                List<User> users =  userDao.getAll();
                adt.resetList(users);
                tvSearch.setText("");
                btnRemove.setVisibility(View.INVISIBLE);
                btnSave.setVisibility(View.INVISIBLE);
            }
        });
//        btnRemove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String name = tvSearch.getText().toString();
//                userDao.addStudent(new User(name));
//                List<User> users =  dataUser.getAllStudents();
//                adt.resetList(users);
//                tvSearch.setText("");
//                btnRemove.setVisibility(View.INVISIBLE);
//                btnSave.setVisibility(View.INVISIBLE);
//            }
//        });
    }

    @Override
    public void itemClicklistener(User user) {
        btnRemove.setEnabled(true);
        btnSave.setEnabled(true);
        tvSearch.setText(user.getFirstName().toString() + " " +user.getLastName().toString());
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDao.delete(userDao.findById(user.getUid()));
                List<User> users =  userDao.getAll();
                adt.resetList(users);
                tvSearch.setText("");
                btnRemove.setEnabled(false);
                btnSave.setEnabled(false);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setFirstName(tvSearch.getText().toString());
                userDao.update(userDao.findById(user.getUid()));
                List<User> users =  userDao.getAll();
                adt.resetList(users);
                tvSearch.setText("");
                btnRemove.setEnabled(false);
                btnSave.setEnabled(false);
            }
        });

    }
}