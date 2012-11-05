package com.tpg.androidindexbar;

import java.util.HashMap;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AlphabetIndexer;
import android.widget.ListView;
import android.widget.TextView;

public class IndexBarListView extends Activity implements OnItemClickListener{

	HashMap<Character, Integer> alphabetToIndex;
	static String TAG="androidindexbar";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.indexbarlayout);
		populateHashMap();
	}

	protected void onResume() {
		ListView listview=(ListView)findViewById(R.id.listView_alphabets);
		listview.setOnItemClickListener(this);
		
		String alphabets[]=getResources().getStringArray(R.array.alphabtes_array);
		IndexBarAdapter adapter=new IndexBarAdapter(this,R.layout.alphabtes_row,alphabets);
		listview.setAdapter(adapter);
		super.onResume();
	}

	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		
		if (!(  view instanceof TextView || view==null))
			return;
		TextView rowview=(TextView)view;
		
		String selected_alpahbet=rowview.getText().toString();
		//Log.d(TAG, "Selected Alphabet is:"+selected_alpahbet);
		if(selected_alpahbet==null || selected_alpahbet.equals(""))
			return;
		Integer position=alphabetToIndex.get(selected_alpahbet.charAt(0));
		//Log.d(TAG, "position is:"+position);
				
		if(position==null)
			return;
		ListView listview=(ListView)findViewById(R.id.listView_main);
		listview.setSelection(position);
	}
	private void populateHashMap(){
		alphabetToIndex=new HashMap<Character, Integer>();
		String base_list[]=getResources().getStringArray(R.array.base_array);
		int base_list_length=base_list.length;
		
		for(int i=0; i < base_list_length; i++){
			char firstCharacter=base_list[i].charAt(0);
			boolean presentOrNot=alphabetToIndex.containsKey(firstCharacter);
			if(!presentOrNot){
				alphabetToIndex.put(firstCharacter, i);
				Log.d(TAG,"Character="+firstCharacter+"  position="+i);
			}
		}
		String alphabets[]=getResources().getStringArray(R.array.alphabtes_array);
		int index=-1;
		
		for(String alpha1: alphabets){
			char alpha=alpha1.charAt(0);
			index++;
			
			if(alphabetToIndex.containsKey(alpha))
				continue;

			/*Start searching the next character position. Example, here alpha is E. Since there is no entry for E, we need to find the position of next Character, F.*/
			for(int i=index+1  ; i< 26 ;i++){		//start from next character to last character
				char searchAlphabet=alphabets[i].charAt(0);   
				
				/*If we find the position of F character, then on click event on E should take the user to F*/	
				if(  alphabetToIndex.containsKey(searchAlphabet)){
					alphabetToIndex.put(alpha, alphabetToIndex.get(searchAlphabet));
					break;
				}
				else
					if(i==25) /*If there are no entries after E, then on click event on E should take the user to end of the list*/
						alphabetToIndex.put(alpha, base_list_length-1);
					else
						continue;
					
			}//
		}//
	}
}
