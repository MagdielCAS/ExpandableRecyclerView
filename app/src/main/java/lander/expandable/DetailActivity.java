package lander.expandable;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ContentAdapter mAdapter;
    private DatabaseReference databaseReference;
    private ContentItem item;
    private List<Object> dataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        item = new ContentItem();
        dataSet = new ArrayList<>();

        String contentKey = "key";

        if (getIntent().getExtras() != null) {
            contentKey = getIntent().getExtras().getString("content");
        }

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://docs-ifpi.firebaseio.com/document-content");
        System.out.println(databaseReference.getRef());

        recyclerView = (RecyclerView) findViewById(R.id.content_container);
        recyclerView.setHasFixedSize(true); //Seta os elementos de tamanho fixo, ajudar a ganhar desempenho
        linearLayoutManager = new LinearLayoutManager(this); //Define como os dados são apresentados no recycler view
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); //define como uma lista vertical
        recyclerView.setLayoutManager(linearLayoutManager); //configurando o recycler view com a especificação de layout
        recyclerView.setNestedScrollingEnabled(false);
        //recyclerView.setAdapter(mAdapter);

        Query query = databaseReference.orderByKey().equalTo(contentKey);

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                childAdd(dataSnapshot);
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
        item = dataSnapshot.getValue(ContentItem.class);
        String teste = "";
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(item.getTitle());
        if(item.getDefinicao() != null){
            StickyHeader h = new StickyHeader("Definição");
            h.setSectionFirstPosition(0);
            dataSet.add(h);
            dataSet.add(item.getDefinicao());
            System.out.println("d");
        }
        if(item.getInformacoesGerais() != null){
            StickyHeader h = new StickyHeader("Informações Gerais");
            h.setSectionFirstPosition(dataSet.size());
            dataSet.add(h);
            dataSet.add(item.getInformacoesGerais());
            System.out.println(item.getInformacoesGerais());
            System.out.println("ig");
        }
        if(item.getRequisitosBasicos() != null){
            StickyHeader h = new StickyHeader("Requisitos Básicos");
            h.setSectionFirstPosition(dataSet.size());
            dataSet.add(h);
            dataSet.add(item.getRequisitosBasicos());
            System.out.println("rqb");
        }
        if(item.getLegislacao() != null){
            StickyHeader h = new StickyHeader("Legislação");
            h.setSectionFirstPosition(dataSet.size());
            dataSet.add(h);
            dataSet.add(item.getLegislacao());
            System.out.println("lg");
        }
        if(item.getProcedimentosTramites() != null){
            StickyHeader h = new StickyHeader("Procedimentos e Tramites");
            h.setSectionFirstPosition(dataSet.size());
            dataSet.add(h);
            for(ContentItem.Procedimento i:
                    item.getProcedimentosTramites()) {
                dataSet.add("Passo " + i.getPasso());
                dataSet.add(i.getProcedimentos());
                dataSet.add("Responsável: "+i.getResponsavel());
                System.out.println("pt add");
            }
        }
        System.out.println(dataSet.size()+" size");
        teste += "<h3>"+item.getTitle() + "</h3><br><br>";
        for(Object o : dataSet){
            if(o instanceof StickyHeader){
                teste += "<b>"+((StickyHeader) o).getHeader()+"</b>"+"<br><br>";
            }else {
                teste += o+"<br><br>";
            }
        }
        dataSet.clear();
        dataSet.add(teste);
        mAdapter = new ContentAdapter(dataSet);
        recyclerView.setAdapter(mAdapter);
    }
}
