package lander.expandable;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("");

        recyclerView = (RecyclerView) findViewById(R.id.container);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new ListAdapter(this, criarListaFake());
        adapter.onRestoreInstanceState(savedInstanceState);

        recyclerView.setAdapter(adapter);
    }

    private List<ParentObject> criarListaFake() {
        List<HeaderParent> teste = new ArrayList<>();
        teste.add(new HeaderParent(getResources().getString(R.string.title)));
        teste.add(new HeaderParent(getResources().getString(R.string.title)));
        teste.add(new HeaderParent(getResources().getString(R.string.title)));
        teste.add(new HeaderParent(getResources().getString(R.string.title)));
        teste.add(new HeaderParent(getResources().getString(R.string.title)));
        teste.add(new HeaderParent(getResources().getString(R.string.title)));
        teste.add(new HeaderParent(getResources().getString(R.string.title)));
        List<ParentObject> parentList = new ArrayList<>();
        for (HeaderParent item : teste) {
            List<Object> child = new ArrayList<>();
            child.add(new BodyContent(getResources().getString(R.string.large_text)));
            item.setChildObjectList(child);
            parentList.add(item);
        }
        return parentList;
    }
}
