package com.codepath.example.tipcalculator.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;


public class CalculateTipActivity extends ActionBarActivity {

    EditText txtBillAmnt;
    SeekBar sbTipAmnt;
    TextView tvFinalAmnt;
    TextView tvTipPercent;
    NumberPicker splitNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_tip);

        txtBillAmnt = (EditText) findViewById(R.id.etBillTotal);
        sbTipAmnt = (SeekBar) findViewById(R.id.sbTipAmnt);
        tvFinalAmnt = (TextView) findViewById(R.id.txtFinalAmnt);
        tvTipPercent = (TextView) findViewById(R.id.tvTipPercent);
        splitNumber = (NumberPicker) findViewById(R.id.numberPicker);

        splitNumber.setMinValue(1);
        splitNumber.setMaxValue(10);

        setupSeekerListener();
        setupNumberPickerListener();
        setupBillAmountListener();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calculate_tip, menu);
        return true;
    }

    private void setupBillAmountListener() {
        txtBillAmnt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setupNumberPickerListener() {
        splitNumber.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                System.out.println(newVal);
            }
        });

    }

    private void setupSeekerListener() {
        sbTipAmnt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;
            int billAmount = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
                BigDecimal billAmount = getBillAmount();
                BigDecimal tipAmount = getTipAmount();
                BigDecimal finalBill = getFinalBill();
                tvTipPercent.setText(progressChanged + "% tip");
                tvFinalAmnt.setText(finalBill.toString());
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public BigDecimal getBillAmount() {
        return new BigDecimal(txtBillAmnt.getText().toString());
    }

    public BigDecimal getTipAmount() {
        return new BigDecimal(String.valueOf(sbTipAmnt.getProgress()*.01)).setScale(2, BigDecimal.ROUND_CEILING);
    }

    public BigDecimal getFinalBill() {
        BigDecimal billAmount = getBillAmount();
        BigDecimal tipAmount = getTipAmount();
        return billAmount.add(billAmount.multiply(tipAmount)).setScale(2, BigDecimal.ROUND_CEILING);
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
