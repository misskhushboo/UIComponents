package com.tpg.androidindexbar;

import java.util.HashMap;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Khushboo.kaur
 * This is the base activity which creates the UI and handles UI interaction with the client.
 *
 */
public class IndexBar extends Activity implements OnItemClickListener{

	HashMap<Character, Integer> alphabetToIndex;
	static String TAG="IndexBar";
	int number_of_alphabets=-1;
	static IndexBarHandler handler;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.indexbarlayout);
		
		getScreenHeight(this);
		getScreenWidth(this);
		handler=new IndexBarHandler(this);
	}

	protected void onResume() {
		super.onResume();
		
		/* populating the base listview */
		CustomListView mainlistview =		(CustomListView)findViewById(R.id.listView_main);
		String main_list_array[]    =		getResources().getStringArray(R.array.base_array);
		if(main_list_array==null){
			Log.d(TAG,"Array of the main listview is null");
			return;
		}
		ArrayAdapter<String> mainadapter=	new ArrayAdapter<String>(getBaseContext(),R.layout.mainlistview_row/*android.R.layout.simple_list_item_1*/,main_list_array);
		mainlistview.setAdapter(mainadapter);
		populateHashMap();
		
	}
	public int convertDipToPx(int dp, Context context){	//10dp=15px
		Resources r = context.getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
		return (int)px;
	}
	public int convertPxtoDip(int pixel){	//15px=23dp
	    float scale = getResources().getDisplayMetrics().density;
	    int dips=(int) ((pixel / scale) + 0.5f);
	    return dips;
	}

	/**
	 * Determines the width of the screen in pixels
	 * @return width
	 */
	public int getScreenWidth(Activity activity){
		Display display = activity.getWindowManager().getDefaultDisplay(); 
		int width = display.getWidth();	
		Log.d(TAG,"Screen Width in pixels="+width);
		return width;
	}
	/**
	 * Determines the height of the screen in pixels
	 * @return height
	 */
	public int getScreenHeight(Activity activity){
		Display display = activity.getWindowManager().getDefaultDisplay(); 
		int height = display.getHeight();
		Log.d(TAG,"Screen Height in pixels="+height);
		return height;
	}
	public float pixelsToSp(Context context, Float px) {
	    float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
	    Log.d(TAG,"GetTextSize in pixels="+px+" In Sp="+(px/scaledDensity));
	    return px/scaledDensity;
	}
	
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		
		if (!(  view instanceof TextView || view==null))
			return;
		TextView rowview=(TextView)view;
		
		CharSequence alpahbet=rowview.getText();
		
		if(alpahbet==null || alpahbet.equals(""))
			return;
		
		String selected_alpahbet=alpahbet.toString().trim();
		Integer position=alphabetToIndex.get(selected_alpahbet.charAt(0));
		Log.d(TAG, "Selected Alphabet is:"+selected_alpahbet+"   position is:"+position);
				
		ListView listview=(ListView)findViewById(R.id.listView_main);
		listview.setSelection(position);
	}
	
	/**
	 * This populates the HashMap which contains the mapping between the alphabets and their relative position index. 
	 */
	private void populateHashMap(){
		alphabetToIndex=new HashMap<Character, Integer>();
		String base_list[]=getResources().getStringArray(R.array.base_array);
		int base_list_length=base_list.length;
		
		for(int i=0; i < base_list_length; i++){
			char firstCharacter=base_list[i].charAt(0);
			boolean presentOrNot=alphabetToIndex.containsKey(firstCharacter);
			if(!presentOrNot){
				alphabetToIndex.put(firstCharacter, i);
				//Log.d(TAG,"Character="+firstCharacter+"  position="+i);
			}
		}
		number_of_alphabets=alphabetToIndex.size();		//Number of enteries in the map is equal to number of letters that would necessarily display on the right.
		
		/*Now I am making an entry of those alphabets which are not there in the Map*/
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
