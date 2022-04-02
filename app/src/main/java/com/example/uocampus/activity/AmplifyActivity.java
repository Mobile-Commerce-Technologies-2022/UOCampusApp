package com.example.uocampus.activity;

import static android.widget.Toast.LENGTH_SHORT;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.Consumer;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.DataStoreItemChange;
import com.amplifyframework.datastore.generated.model.TestModel;
import com.example.uocampus.R;

public class AmplifyActivity extends AppCompatActivity {
    TestModel testModel;
    String objectId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amplify);
        configAmplify();
        init();

    }

    private void init() {
        Button btnCreate = findViewById(R.id.btn_create);
        btnCreate.setOnClickListener(view -> {
            create();
        });

        Button btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(view -> {
            update();
        });

        Button btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(view -> {
            delete();
        });

        Button btnQuery = findViewById(R.id.btn_query_last);
        btnQuery.setOnClickListener(view -> {
            readById(objectId);
        });
    }

    private void configAmplify() {
        try {
            Amplify.addPlugin(new AWSApiPlugin()); // UNCOMMENT this line once backend is deployed
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.configure(getApplicationContext());
            Log.i("Amplify", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("Amplify", "Could not initialize Amplify", error);
        }
    }

    private void create() {
        TestModel item = TestModel.builder()
                .name("Test Model works")
                .build();
        this.testModel = item;
        Amplify.DataStore.save(
                item,
                success -> {
                    Log.i("Amplify", "Saved item: " + success.item().getId());
                    objectId = success.item().getId();
                },
                error -> Log.e("Amplify", "Could not save item to DataStore", error)
        );
    }

    private void update() {
        TestModel updatedItem= this.testModel.copyOfBuilder()
                .name(testModel.getName() + "[UPDATE]")
                .build();

        Amplify.DataStore.save(
                updatedItem,
                success -> Log.i("Amplify", "Item updated: " + success.item().getId()),
                error -> Log.e("Amplify", "Could not save item to DataStore", error)
        );
    }

    private void delete() {
        TestModel toDeleteItem = TestModel.justId(objectId);

        Amplify.DataStore.delete(toDeleteItem,
                deleted -> Log.i("Amplify", "Deleted item " + objectId + "."),
                failure -> Log.e("Amplify", "Delete failed.", failure)
        );
    }

    private void readAll() {
        Amplify.DataStore.query(
                TestModel.class,
                items -> {
                    while (items.hasNext()) {
                        TestModel item = items.next();
                        Log.i("Amplify", "Id " + item.getId());
                    }
                },
                failure -> Log.e("Amplify", "Could not query DataStore", failure)
        );
    }

    private void readById(String objectId) {
        Amplify.DataStore.query(
                TestModel.class,
                Where.id(objectId),
                items -> {
                    if (items.hasNext()) {
                        TestModel item = items.next();
                        Log.i("Amplify", "Id " + item.getId());
                        this.testModel = item;
                    }
                },
                failure -> Log.e("Amplify", "Could not query DataStore", failure)
        );
    }


}
