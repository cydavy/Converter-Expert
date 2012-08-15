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

import com.madeng.converter.R;
import com.mopub.mobileads.MoPubView;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Menu extends ListActivity {

	private final String conversion[] = { "Area", "Data", "Density", "Dynamic Viscosity", "Energy", "Force", "Frequency", "Kinematic Viscosity", "Length", "Mass", "Pressure", "Speed", "Temperature", "Time" };
	private final int[] imgs = { R.drawable.areaico, R.drawable.dataico, R.drawable.densityico, R.drawable.dvisico, R.drawable.energyico, R.drawable.forceico, R.drawable.freqico, R.drawable.kvisico, R.drawable.lengthico,
			R.drawable.massico, R.drawable.pressureico, R.drawable.speedico, R.drawable.tempico, R.drawable.timeico, R.drawable.timeico };

	private MoPubView mpv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setListAdapter(new IconicAdapter(imgs, conversion));
		setContentView(R.layout.menu);

		// Set up ad
		mpv = (MoPubView) findViewById(R.id.adview);
		mpv.setAdUnitId("agltb3B1Yi1pbmNyDQsSBFNpdGUY1LLYEgw");
		mpv.loadAd();
	}

	class IconicAdapter extends ArrayAdapter<String> {
		int[] img;
		String[] items;

		IconicAdapter(int[] images, String[] items) {
			super(Menu.this, R.layout.row, R.id.label, items);
			this.items = items;
			img = images;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;

			if (convertView == null) {
				LayoutInflater minflater = getLayoutInflater();
				convertView = minflater.inflate(R.layout.row, null);

				holder = new ViewHolder();
				holder.text = (TextView) convertView.findViewById(R.id.label);
				holder.icon = (ImageView) convertView.findViewById(R.id.icon);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.text.setText("\t\t" + items[position]);
			holder.icon.setImageResource(img[position]);

			return (convertView);
		}
	}

	static class ViewHolder {
		TextView text;
		ImageView icon;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		UnitConvert.convTemp = false;
		switch (position) {
		case 0: // Area
			UnitConvert.setIds(R.array.areaConv, R.array.areaConvRates, "Area");
			break;
		case 1: // Data
			UnitConvert.setIds(R.array.dataConv, R.array.dataConvRates, "Data");
			break;
		case 2: // Density
			UnitConvert.setIds(R.array.densityConv, R.array.densityConvRates, "Density");
			break;
		case 3: // Frequency
			UnitConvert.setIds(R.array.dvisConv, R.array.dvisConvRates, "Dynamic Viscosity");
			break;
		case 4: // Energy
			UnitConvert.setIds(R.array.energyConv, R.array.energyConvRates, "Energy");
			break;
		case 5: // Force
			UnitConvert.setIds(R.array.forceConv, R.array.forceConvRates, "Force");
			break;
		case 6: // Frequency
			UnitConvert.setIds(R.array.freqConv, R.array.freqConvRates, "Frequency");
			break;
		case 7: // Frequency
			UnitConvert.setIds(R.array.kvisConv, R.array.kvisConvRates, "Kinematic Viscosity");
			break;
		case 8:// length
			UnitConvert.setIds(R.array.lengthConv, R.array.lengthConvRates, "Length");
			break;
		case 9:// Mass
			UnitConvert.setIds(R.array.massConv, R.array.massConvRates, "Mass");
			break;
		case 10: // Frequency
			UnitConvert.setIds(R.array.pressureConv, R.array.pressureConvRates, "Pressure");
			break;
		case 11: // Speed
			UnitConvert.setIds(R.array.speedConv, R.array.speedConvRates, "Speed");
			break;
		case 12: // Frequency
			UnitConvert.setIds(R.array.tempConv, 0, "Temperature");
			UnitConvert.convTemp = true;
			break;
		case 13: // Time
			UnitConvert.setIds(R.array.timeConv, R.array.timeConvRates, "Time");
			break;
		default:
			// startAct = new Intent ("com.madeng.converter.Menu");
		}

		Intent outIntent = new Intent(Menu.this, UnitConvert.class);
		startActivity(outIntent);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mpv.destroy();
	}
}