package com.madeng.converter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class UnitConvert extends Activity implements View.OnClickListener {

	private static int arrIdConv, arrIdRate;

	private Spinner spFrom, spTo;
	private static ArrayList<String> unitArray;
	private String from, to = " ";
	private Button swapBtn, copyBtn;
	private EditText et, etRes;
	private static String title = "";

	public static boolean convTemp = false;

	private static HashMap<String, Double> convRatesMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.conversiontest);
		setTitle(title);

		init();

		et.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable arg0) {
				return;
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				return;
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (convTemp)
					updateResTemp();
				else
					updateRes();
			}

		});

		// Button Listeners
		swapBtn.setOnClickListener(this);
		copyBtn.setOnClickListener(this);

		spFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				int temp = parent.getSelectedItemPosition();
				if (temp == AdapterView.INVALID_POSITION) {
					return;
				}
				from = unitArray.get(temp);
				if (convTemp) {
					updateResTemp();
				} else
					updateRes();

			}

			public void onNothingSelected(AdapterView<?> adapterView) {
				return;
			}

		});
		spTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				int temp = parent.getSelectedItemPosition();
				if (temp == AdapterView.INVALID_POSITION) {
					return;
				}
				to = unitArray.get(temp);
				if (convTemp) {
					updateResTemp();
				} else
					updateRes();
			}

			public void onNothingSelected(AdapterView<?> adapterView) {
				return;
			}

		});

	}

	private void init() {
		// Initialize Variables
		swapBtn = (Button) findViewById(R.id.swapBtn);
		copyBtn = (Button) findViewById(R.id.copybtn);
		et = (EditText) findViewById(R.id.editTxt);
		etRes = (EditText) findViewById(R.id.editTxt2);

		// tv = (TextView)findViewById(R.id.resultTxt);
		spFrom = (Spinner) findViewById(R.id.SpinnerFrom);
		spTo = (Spinner) findViewById(R.id.SpinnerTo);
		unitArray = new ArrayList<String>();
		convRatesMap = new HashMap<String, Double>();

		// setup properties
		etRes.setFocusable(false);
		populateList(arrIdConv, unitArray, spFrom, spTo, this);

		if (!convTemp) {
			et.setKeyListener(DigitsKeyListener.getInstance(false, true));
			Conversion.populateUnit(getApplicationContext(), convRatesMap, arrIdRate);
		} else {
			et.setKeyListener(DigitsKeyListener.getInstance(true, true));
		}

		et.requestFocus();

		// Force Keyboard to be Visible
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

		from = to = unitArray.get(0);
	}

	public void populateList(int resArray, ArrayList<String> unitArray, Spinner spFrom, Spinner spTo, Context con) {
		Resources r = con.getResources();
		String[] items = r.getStringArray(resArray);
		ArrayList<String> displayArr = new ArrayList<String>();
		String[] temp;

		for (String str : items) {
			temp = str.split("~");
			unitArray.add(temp[0]);
			displayArr.add(temp[0] + "   " + temp[1]);
		}

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(con, android.R.layout.simple_spinner_item, displayArr);
		// ArrayAdapter<CharSequence>
		// dataAdapter=ArrayAdapter.createFromResource(this, resArray,
		// android.R.layout.simple_spinner_item);//(con,android.R.layout.simple_spinner_item,displayArr);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spFrom.setAdapter(dataAdapter);
		spTo.setAdapter(dataAdapter);

	}

	public static void setIds(int arrIdConvV, int arrIdRateV, String titleV) {
		arrIdConv = arrIdConvV;
		arrIdRate = arrIdRateV;
		title = titleV;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.swapBtn:
			swapUnits();
			break;
		case R.id.copybtn:
			displayCopyConf();

		}

	}

	private void displayCopyConf() {
		String msg = et.getText().toString() + " " + from + "=" + etRes.getText().toString() + " " + to;
		ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		clipboard.setText(msg);

		Context context = getApplicationContext();
		CharSequence text = "Copied: \"" + msg + "\".";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
		toast.show();
	}

	private void updateResTemp() {
		String temp;

		if (et.getText().toString().length() < 1) {// &&
													// etRes.getText().toString().length()>1){
			etRes.setText("");
			return;
		} else if (to.equals(from)) {
			etRes.setText(et.getText().toString());
			return;
		}
		temp = et.getText().toString();
		double t = Double.parseDouble(temp);

		if (from.equals("Celsius")) {
			if (to.equals("Fahrenheit")) {
				t = (9 / (double) 5) * t + 32;
			} else {
				t += 273.15;
			}
		} else if (from.equals("Fahrenheit")) {
			t = (5 / (double) 9) * (t - 32);
			if (to.equals("Kelvin")) {
				t += 273.15;
			}
		} else if (from.equals("Kelvin")) {
			t -= 273.15;
			if (to.equals("Fahrenheit")) {
				t = (9 / (double) 5) * t + 32;
			}
		}

		double tt = format(t);
		etRes.setText(Double.toString(tt));

	}

	private void updateRes() {
		Double temp;
		double rate, val, tmp;
		boolean div = false;

		if (et.getText().toString().length() < 1) {// &&
													// etRes.getText().toString().length()>1){
			etRes.setText("");
			return;
		} else if (to.equals(from)) {
			etRes.setText(et.getText().toString());
			return;
		}

		// get the conversion rate
		temp = convRatesMap.get(from + to);
		// if doesn't exist check the other way
		if (temp == null) {
			temp = convRatesMap.get(to + from);// get the other conversion way
			// if it still equals to null or if there is nothing entered, return
			if (temp == null || et.getText().toString().length() < 1)
				return;
			div = true;
		}
		rate = temp.doubleValue();

		// Get the value entered
		val = Double.parseDouble(et.getText().toString());

		if (div) {
			tmp = format(val / rate);
			System.out.println("temp: " + etRes.getText().toString());
			etRes.setText(Double.toString(tmp));
		} else {
			tmp = format(val * rate);
			etRes.setText(Double.toString(tmp));
		}

	}

	private double format(double num) {
		DecimalFormat f;
		if (num < 999999999.0 && num > 0.000000001) {
			if (num < 0.001) {
				f = new DecimalFormat("#0.00000000000E00");
			} else {
				f = new DecimalFormat("#0.00000");
			}
		} else {
			f = new DecimalFormat("#0.00000000000E00");
		}

		return Double.parseDouble(f.format(num));
	}

	private void swapUnits() {
		// get currently selected units index
		int toInd = unitArray.indexOf(to);
		int fromInd = unitArray.indexOf(from);

		// swap to and from
		String temp = to;
		to = from;
		from = temp;

		// set spinners
		spFrom.setSelection(toInd, true);
		spTo.setSelection(fromInd, true);

	}
	// public void onItemSelected(AdapterView<?> parent, View view, int pos,
	// long id) {
	//
	// int temp = parent.getSelectedItemPosition();
	// if (temp == AdapterView.INVALID_POSITION){
	// //System.out.println ("INVALID POSITION");
	// return;
	// }
	//
	// switch (view.getId()){
	// case R.id.SpinnerFrom:
	// from = unitArray.get(temp);
	// break;
	// case R.id.SpinnerTo:
	// to = unitArray.get(temp);
	// break;
	//
	// }
	// updateRes();
	//
	// }
	// public void onNothingSelected(AdapterView<?> arg0) {
	// // TODO Auto-generated method stub
	//
	// }

}
