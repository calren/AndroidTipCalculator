package com.codepath.example.tipcalculator.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

import java.math.BigDecimal;


public class CalculateTipActivity extends ActionBarActivity {

    EditText txtBillAmnt;
    SeekBar sbTipAmnt;
    TextView tvFinalAmnt;
    TextView tvToTalBill;
    TextView tvTipPercent;
    NumberPicker splitNumber;
    ImageView p1;
    ImageView p2;
    ImageView p3;
    ImageView p4;
    ImageView p5;
    ImageView p6;
    ImageView p7;
    ImageView p8;
    ImageView p9;
    ImageView p10;

    ImageView[] images = new ImageView[10];
    int lastImageSet = 0;

    int progressChanged = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_tip);

        txtBillAmnt = (EditText) findViewById(R.id.etBillTotal);
        sbTipAmnt = (SeekBar) findViewById(R.id.sbTipAmnt);
        tvFinalAmnt = (TextView) findViewById(R.id.txtFinalAmnt);
        tvToTalBill = (TextView) findViewById(R.id.totalBill);
        tvTipPercent = (TextView) findViewById(R.id.tvTipPercent);
        splitNumber = (NumberPicker) findViewById(R.id.numberPicker);
        p1 = (ImageView) findViewById(R.id.pic1);
        p2 = (ImageView) findViewById(R.id.pic2);
        p3 = (ImageView) findViewById(R.id.pic3);
        p4 = (ImageView) findViewById(R.id.pic4);
        p5 = (ImageView) findViewById(R.id.pic5);
        p6 = (ImageView) findViewById(R.id.pic6);
        p7 = (ImageView) findViewById(R.id.pic7);
        p8 = (ImageView) findViewById(R.id.pic8);
        p9 = (ImageView) findViewById(R.id.pic9);
        p10 = (ImageView) findViewById(R.id.pic10);

        p1.setVisibility(View.VISIBLE);
        p2.setVisibility(View.INVISIBLE);
        p3.setVisibility(View.INVISIBLE);
        p4.setVisibility(View.INVISIBLE);
        p5.setVisibility(View.INVISIBLE);
        p6.setVisibility(View.INVISIBLE);
        p7.setVisibility(View.INVISIBLE);
        p8.setVisibility(View.INVISIBLE);
        p9.setVisibility(View.INVISIBLE);
        p10.setVisibility(View.INVISIBLE);

        images[0] = p1;
        images[1] = p2;
        images[2] = p3;
        images[3] = p4;
        images[4] = p5;
        images[5] = p6;
        images[6] = p7;
        images[7] = p8;
        images[8] = p9;
        images[9] = p10;

        splitNumber.setMinValue(1);
        splitNumber.setMaxValue(10);

        InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(txtBillAmnt, InputMethodManager.SHOW_IMPLICIT);
        txtBillAmnt.setImeOptions(EditorInfo.IME_ACTION_DONE);

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
                tvFinalAmnt.setText(getFinalBillPerPerson().toString());
                tvToTalBill.setText(getFinalBill().toString());
                tvTipPercent.setText(progressChanged + "% tip ($" + getTip() + ")");
            }
        });
    }

    private void setupNumberPickerListener() {
        splitNumber.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                tvFinalAmnt.setText(getFinalBillPerPerson().toString());
                tvToTalBill.setText(getFinalBill().toString());
                setAndroidIcons(newVal, oldVal);
            }
        });

    }

    private void setupSeekerListener() {
        sbTipAmnt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int billAmount = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
                BigDecimal finalBill = getFinalBill();
                tvTipPercent.setText(progressChanged + "% tip ($" + getTip() + ")");
                tvFinalAmnt.setText(getFinalBillPerPerson().toString());
                tvToTalBill.setText(getFinalBill().toString());
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void setAndroidIcons(int newNumPickerVal, int oldNumPickerVal) {
        if (newNumPickerVal > oldNumPickerVal) {
            lastImageSet++;
            images[lastImageSet].setVisibility(View.VISIBLE);
        } else {
            images[lastImageSet].setVisibility(View.INVISIBLE);
            lastImageSet--;
        }
    }

    public String getTip() {
        return (getBillAmount().multiply(getTipAmount()).setScale(2, BigDecimal.ROUND_CEILING)).toString();
    }

    // add error handling for non numbers
    public BigDecimal getBillAmount() {
        String bill = txtBillAmnt.getText().toString();
        if (!bill.isEmpty()) {
            return new BigDecimal(txtBillAmnt.getText().toString());
        } else {
            return new BigDecimal("0");
        }
    }

    public BigDecimal getTipAmount() {
        return new BigDecimal(String.valueOf(sbTipAmnt.getProgress()*.01)).setScale(2, BigDecimal.ROUND_CEILING);
    }

    public BigDecimal splitAmount() {
        return new BigDecimal(String.valueOf(splitNumber.getValue()));
    }

    public BigDecimal getFinalBillPerPerson() {
        BigDecimal billAmount = getBillAmount();
        BigDecimal tipAmount = getTipAmount();

        return ((billAmount.add(billAmount.multiply(tipAmount))).divide(splitAmount(), BigDecimal.ROUND_HALF_UP)).setScale(2, BigDecimal.ROUND_CEILING);
    }

    public BigDecimal getFinalBill() {
        BigDecimal billAmount = getBillAmount();
        BigDecimal tipAmount = getTipAmount();
        return (billAmount.add(billAmount.multiply(tipAmount))
                .setScale(2, BigDecimal.ROUND_CEILING));
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
