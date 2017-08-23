package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    ArrayList<String>  usernames ;
    ListView userListView ;
    ArrayAdapter<String> arrayAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        usernames = new ArrayList<>();
        setTitle("users list");
        userListView = (ListView)findViewById(R.id.userListView);
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext() , ChatActivity.class);
                intent.putExtra("username" , usernames.get(i));
                startActivity(intent);
            }
        });
        arrayAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_dropdown_item_1line , usernames);



        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username" , ParseUser.getCurrentUser().getUsername());
        query.addAscendingOrder("username");
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null){
                    if(objects.size() > 0){
                        for(ParseUser user : objects ){
                            usernames.add(user.getUsername());
                        }
                        userListView.setAdapter(arrayAdapter);
                        arrayAdapter.notifyDataSetChanged();
                    }else{
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
