package com.codepath.example.tipcalculator.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;


public class CalculateTipActivity extends ActionBarActivity {

    EditText txtBillAmnt;
    SeekBar sbTipAmnt;
    TextView tvFinalAmnt;
    TextView tvTipPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_tip);

        txtBillAmnt = (EditText) findViewById(R.id.etBillTotal);
        sbTipAmnt = (SeekBar) findViewById(R.id.sbTipAmnt);
        tvFinalAmnt = (TextView) findViewById(R.id.txtFinalAmnt);
        tvTipPercent = (TextView) findViewById(R.id.tvTipPercent);
        tvFinalAmnt.setText(" ");

        sbTipAmnt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;
            int billAmount = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
                BigDecimal billAmount = new BigDecimal(txtBillAmnt.getText().toString());
                BigDecimal tipAmount = new BigDecimal(String.valueOf(progressChanged*.01)).setScale(2, BigDecimal.ROUND_CEILING);
                BigDecimal finalBill = billAmount.add(billAmount.multiply(tipAmount)).setScale(2, BigDecimal.ROUND_CEILING);;
                tvTipPercent.setText(progressChanged + "% tip");
                tvFinalAmnt.setText(finalBill.toString());
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
//        lvItems = (ListView) findViewById(R.id.lvItems);
//        preSorted = new TreeMap<String, Integer>();
//        items = sort(preSorted);
//        readItems();
//        itemsAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, items);
//        lvItems.setAdapter(itemsAdapter);
//        items.add("Start adding items!");
//        setupListViewListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calculate_tip, menu);
        return true;
    }

    private void setupSeekerListener() {

    }

    private void calculateTipAmount(int billAmnt) {

    }

    private void calculateTotalBill(int billAmnt) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
