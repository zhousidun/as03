package abc.com.abcdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import abc.com.abcdemo.R;

public class AccListActivity extends Activity {

    String TAG = "AccListActivity";
    List<String> accList = new ArrayList<>();
    String[] accArr = {"6228490083156998732","6228490083156998732","6228490083156996653","6228870287156998163"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_list);

        accList.add("6228490083156998732");
        accList.add("6228870287156997765");
        accList.add("6228490083156996653");
        accList.add("6228870287156998163");

//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, accList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_acc, accList);
        ListView lvAcc = (ListView) findViewById(R.id.lv_acc);
        lvAcc.setAdapter(adapter);
        lvAcc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String accNo = accList.get(position);
                Log.i(TAG,"选择的账户是 "+accNo);

                Intent intent = new Intent(getApplicationContext(), InputAmtActivity.class);
                intent.putExtra("accNo",accNo);
                startActivity(intent);
                finish();
            }
        });

    }
}
