package lander.expandable;

import android.app.ActionBar;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DatabaseReference databaseReference;

    private List<ParentObject> chapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://docs-ifpi.firebaseio.com/documents/-KT2fklX1zYZohhFbFkq");

        chapters = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.container);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                childAdd(dataSnapshot);
                adapter = new ListAdapter(getApplicationContext(), chapters);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void childAdd(DataSnapshot dataSnapshot) {
        switch (dataSnapshot.getKey()){
            case "title":
                setTitle(dataSnapshot.getValue(String.class));
                break;
            case "chapters":
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    String number = "";
                    String chapter = "";
                    String contentKey = "";
                    String description = "";
                    for(DataSnapshot d: data.getChildren()) {
                        switch (d.getKey()){
                            case "number":
                                number = d.getValue(String.class);
                                break;
                            case "title":
                                chapter = " - " + d.getValue(String.class);
                                break;
                            case "description":
                                description = d.getValue(String.class);
                                break;
                            case "content":
                                contentKey = d.getValue(String.class);
                                break;
                        }
                    }
                    chapter = number + chapter;
                    HeaderParent parent = new HeaderParent(chapter);
                    parent.setKey(contentKey);
                    List<Object> child = new ArrayList<>();
                    child.add(new BodyContent(description));
                    parent.setChildObjectList(child);
                    chapters.add(parent);
                }
        }

    }
}
