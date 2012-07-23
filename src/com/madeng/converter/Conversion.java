package com.madeng.converter;

import java.util.HashMap;

import android.content.Context;
import android.content.res.Resources;

public class Conversion {

	public static void populateUnit(Context con, HashMap<String, Double> hm, int arrayIdRates) {

		Resources r = con.getResources();
		String[] convRates = r.getStringArray(arrayIdRates);

		for (String str : convRates) {
			String temp = str;
			String[] split = temp.split("~");
			Double val = new Double(split[1]);

			hm.put(split[0], val);
		}
	}
}
