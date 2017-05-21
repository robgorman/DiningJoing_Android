package com.ranchosoftware.diningjoint;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

import com.sandiegorestaurantsandbars.diningjoint.R;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;


public class TipCalculatorActivity extends DiningJointActivity
{
  
  
  private BigDecimal billAmount; 
  private int        tipPercent; // 0 to 100z not a decimal
  private int        numberOfPeople; 
  
  EditText editBillAmount; 
  EditText editTipPercent;
  EditText editNumberOfPeople ;
  EditText editTotalTip;
  EditText editPerPerson ;
  SeekBar seekBillAmount;
  SeekBar seekTipPercent;
  SeekBar seekNumberOfPeople;
  
  RelativeLayout fakeFocusable; 
  
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);    //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
   // requestWindowFeature(Window.FEATURE_NO_TITLE);
    billAmount = new BigDecimal(10.00);
    tipPercent = 15; 
    numberOfPeople = 4; 

    setContentView(R.layout.activity_tip_calculator);
   // getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
   // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    seekBillAmount = (SeekBar) findViewById(R.id.seekBillAmount);
    seekTipPercent = (SeekBar) findViewById(R.id.seekTipPercent);
    seekNumberOfPeople = (SeekBar) findViewById(R.id.seekNumberOfPeople);
    
    editBillAmount = (EditText) findViewById(R.id.editBillAmount);
    editTipPercent  = (EditText) findViewById(R.id.editTipPercent);
    editNumberOfPeople = (EditText) findViewById(R.id.editNumberOfPeople); 
    editTotalTip = (EditText) findViewById(R.id.editTotalTip);
    editPerPerson = (EditText) findViewById(R.id.editPerPerson);
   
    // 100 to 50000
    seekBillAmount.setMax(50000-100);
    // 1 to 20
    seekTipPercent.setMax(20-1);
    // 1 to 20
    seekNumberOfPeople.setMax(20-1);
    
    editBillAmount.setOnEditorActionListener(new OnEditorActionListener(){

      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
      {
        if (actionId == EditorInfo.IME_ACTION_DONE) 
        {
          fakeFocusable.requestFocus();
        }
        
        return false;
      }});
    editBillAmount.setOnFocusChangeListener(new OnFocusChangeListener(){

      @Override
      public void onFocusChange(View v, boolean hasFocus)
      {
        if (hasFocus)
        {
          seekBillAmount.setEnabled(false);
          editBillAmount.setText(Double.toString(billAmount.doubleValue()));
        }
        else
        {
          double d = Double.parseDouble(editBillAmount.getText().toString());
          if (d < 1.0)
          {
            d = 1.0;
          }
          else if (d > 500)
          {
            d = 500.0;
          }
          billAmount = new BigDecimal(d);
          
          seekBillAmount.setEnabled(true);
          updateView();
        }
      }
        
      });
    
    editTipPercent.setOnEditorActionListener(new OnEditorActionListener(){

      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
      {
        if (actionId == EditorInfo.IME_ACTION_DONE) 
        {
          fakeFocusable.requestFocus();;
        }
        
        return true;
      }});

    editTipPercent.setOnFocusChangeListener(new OnFocusChangeListener(){

      @Override
      public void onFocusChange(View v, boolean hasFocus)
      {
        if (hasFocus)
        {
          seekTipPercent.setEnabled(false);
         
          editTipPercent.setText(Integer.toString(tipPercent));
          
        }
        else
        {
          
          int value = Integer.parseInt(editTipPercent.getText().toString());
          if (value < 1)
            value = 1; 
          else if (value > 20)
            value = 20;
          tipPercent = value;
          seekTipPercent.setEnabled(true);
          updateView();
        }
        
      }});
    
    editNumberOfPeople.setOnEditorActionListener(new OnEditorActionListener(){

      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
      {
        if (actionId == EditorInfo.IME_ACTION_DONE) 
        {
          fakeFocusable.requestFocus();;
        }
        
        return true;
      }});
    
    editNumberOfPeople.setOnFocusChangeListener(new OnFocusChangeListener(){

      @Override
      public void onFocusChange(View v, boolean hasFocus)
      {
        if (hasFocus)
        {
          seekNumberOfPeople.setEnabled(false);
          editNumberOfPeople.setText(Integer.toString(numberOfPeople));
          
        }
        else
        {
          int value = Integer.parseInt(editNumberOfPeople.getText().toString());
          if (value < 1)
            value = 1; 
          else if (value > 20)
            value = 20;
          numberOfPeople = value;
          seekNumberOfPeople.setEnabled(true);
          updateView();
        }
        
      }});
    seekBillAmount.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
      {
        billAmountChanged(progress);
        
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar)
      {
        activate(editBillAmount);
        
      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar)
      {
        deactivate(editBillAmount);
        
      }});
    seekTipPercent.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
      {
        tipPercentChanged(progress);
        
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar)
      {
        activate(editTipPercent);
        
      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar)
      {
        deactivate(editTipPercent);
        
      }});
    seekNumberOfPeople.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
      {
        numberOfPeopleChanged(progress);
        
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar)
      {
        activate(editNumberOfPeople);
        
      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar)
      {
        deactivate(editNumberOfPeople);
        
      }});
    
    
    seekBillAmount.setProgress(1000-100);
    seekTipPercent.setProgress(14); 
    seekNumberOfPeople.setProgress(3);
  
    updateView();
    fakeFocusable = (RelativeLayout) findViewById(R.id.mainLayout);
    
    fakeFocusable.setOnFocusChangeListener(new OnFocusChangeListener(){

      @Override
      public void onFocusChange(View view, boolean hasFocus)
      {
        if (hasFocus)
          hideKeyboard(); 
        
      }});
    
    fakeFocusable.requestFocus();
   //this.editBillAmount.setFocusable(true);
  }

  private void hideKeyboard()
  {
    InputMethodManager imm = (InputMethodManager) getSystemService(
        android.content.Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
  }
  private void activate(EditText editText)
  {
    
  }
  private void deactivate(EditText edtiText)
  {
    
  }
  
  // progress 0 to 50000-100
  // range 1.00 to 50.000
  // y = x + 100;
  private BigDecimal progressToBillAmount(int progress)
  {
    
    BigDecimal result = new BigDecimal(progress + 100);
    result = result.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
    return result;
  }
  
  private int billAmountToProgress(double amount)
  {
    int progress = (int)(amount *100 - 100.0);
    return progress; 
  }
  
  // progress 0 to 19
  // range 1 to 20
  // y = x + 1;
  private int progressToTipPercent(int progress)
  {
    int result = progress + 1;
    return result; 
  }
  
  private int tipPercentToProgress(int percent)
  {
    return percent -1;
  }
  private int progressToNumberOfPeople(int progress)
  {
    int result = progress + 1; 
    return result; 
  }
  private int numberOfPeopleToProgress(int progress)
  {
    int result = progress -1; 
    return result; 
  }
  
 
  private void updateView()
  {
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
    currencyFormatter.setMinimumFractionDigits(2);
    currencyFormatter.setMinimumFractionDigits(2);
    editBillAmount.setText(currencyFormatter.format(billAmount.doubleValue()));
    seekBillAmount.setProgress(billAmountToProgress(billAmount.doubleValue()));
    
    editTipPercent.setText(Integer.toString(tipPercent) + "%");
    seekTipPercent.setProgress(tipPercentToProgress(tipPercent));
    
    editNumberOfPeople.setText(Integer.toString(numberOfPeople));
    seekNumberOfPeople.setProgress(numberOfPeopleToProgress(numberOfPeople));
    
   
    BigDecimal bigDecimalTipPercent = new BigDecimal(tipPercent);
    bigDecimalTipPercent = bigDecimalTipPercent.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
    
    BigDecimal totalTip = billAmount.multiply((bigDecimalTipPercent));
    BigDecimal bdNumberOfPeople = new BigDecimal(numberOfPeople);
    BigDecimal perPerson = totalTip.divide(bdNumberOfPeople,2,  RoundingMode.HALF_UP);
    
      
    perPerson = perPerson.add(billAmount.divide(bdNumberOfPeople,RoundingMode.HALF_EVEN));
    
    editTotalTip.setText(currencyFormatter.format(totalTip.doubleValue()));
    editPerPerson.setText(currencyFormatter.format(perPerson.doubleValue()));
    
    
  }
  
  private void billAmountChanged(int progress)
  {
    billAmount = progressToBillAmount(progress);
    
    updateView();
  }
  
  private void tipPercentChanged(int progress)
  {
    tipPercent = progressToTipPercent(progress);
    updateView(); 
  }
  
  private void numberOfPeopleChanged(int progress)
  {
    numberOfPeople = progressToNumberOfPeople(progress);
    if (numberOfPeople < 1)
      numberOfPeople = 1; 
    updateView(); 
  }

}
