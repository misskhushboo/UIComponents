package com.tpg.androidindexbar;

import org.w3c.dom.Text;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class IndexBarAdapter extends ArrayAdapter<String> {

	static String TAG="androidindexbar";
	public IndexBarAdapter(Context context, int textViewResourceId, String[] objects) {
		super(context, textViewResourceId, objects);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d(TAG,"Convertview="+convertView);
		if(convertView!=null){
			//TextView tv=(TextView)convertView;
			//tv.setOnClickListener(rowclick);
			convertView.setOnClickListener(rowclick);
		}
		return super.getView(position, convertView, parent);
	}
	
	OnClickListener rowclick=new OnClickListener() {
		
		public void onClick(View v) {
			Log.d(TAG,"Sets the text size as bold");
			if (!(v instanceof TextView))
				return;
			TextView tv=(TextView)v;
			tv.setTypeface(Typeface.DEFAULT_BOLD);
			
		}
	};
}
