/*
 Copyright 2012 MadEngProductions

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

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
