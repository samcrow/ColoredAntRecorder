package org.samcrow.antrecorder;

import java.io.IOException;

import org.samcrow.antrecorder.data.ColorDataSet;
import org.samcrow.antrecorder.data.ColorDataSet.Color;
import org.samcrow.antrecorder.data.ColorDataSet.ColorChangeListener;
import org.samcrow.antrecorder.io.RecorderFileInterface;
import org.samcrow.antrecorder.io.RecorderFileInterface.InOut;
import org.samcrow.antrecorder.util.AlertDialogFragment;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class EntryActivity extends Activity implements ColorChangeListener {

	private ColorDataSet colors = new ColorDataSet();
	
	private Button currentEntry1;
	private Button currentEntry2;
	private Button currentEntry3;
	
	private EditText experimentIdField;
	
	private RadioButton inRadio;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entry);
		
		currentEntry1 = (Button) findViewById(R.id.currentEntry1);
		currentEntry2 = (Button) findViewById(R.id.currentEntry2);
		currentEntry3 = (Button) findViewById(R.id.currentEntry3);
		
		experimentIdField = (EditText) findViewById(R.id.experimentId);
		
		inRadio = (RadioButton) findViewById(R.id.inRadio);
		
		colors.addListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.entry, menu);
		return true;
	}

	@Override
	public void colorsChanged(Color color1, Color color2, Color color3) {
		// set drawables
		
		Drawable drawable1 = buttonForColor(color1);
		Drawable drawable2 = buttonForColor(color2);
		Drawable drawable3 = buttonForColor(color3);
		
		currentEntry1.setBackground(drawable1);
		currentEntry2.setBackground(drawable2);
		currentEntry3.setBackground(drawable3);
	}
	
	public void clearButtonClicked(View view) {
		colors.clear();
	}
	
	public void enterButtonClicked(View view) {
		if(!colors.isFull()) {
			AlertDialogFragment.newInstance(R.string.error_enter_three_colors).show(getFragmentManager(), "dialog");
			return;
		}
		String experimentId = experimentIdField.getText().toString();
		if(experimentId.isEmpty()) {
			AlertDialogFragment.newInstance(R.string.error_enter_experiment_ID).show(getFragmentManager(), "dialog");
			return;
		}
		if(experimentId.contains("/")) {
			AlertDialogFragment.newInstance("Experiment ID must not contain a slash character. Consider using a dash instead.").show(getFragmentManager(), "dialog");
			return;
		}
		
		//Get the ant direction
		InOut direction;
		if(inRadio.isChecked()) {
			direction = InOut.In;
		}
		else {
			direction = InOut.Out;
		}
		
		try {
			RecorderFileInterface.writeEvent(experimentId, direction, colors);
		} catch (IOException e) {
			e.printStackTrace();
			AlertDialogFragment.newInstance("Recording failed: "+e.getLocalizedMessage()).show(getFragmentManager(), "dialog");
		}
		colors.clear();
	}
	
	public void blueButtonClicked(View view) {
		colors.addColor(Color.Blue);
	}
	public void greenButtonClicked(View view) {
		colors.addColor(Color.Green);
	}
	public void orangeButtonClicked(View view) {
		colors.addColor(Color.Orange);
	}
	public void pinkButtonClicked(View view) {
		colors.addColor(Color.Pink);
	}
	public void whiteYellowSilverButtonClicked(View view) {
		colors.addColor(Color.WhiteYellowSilver);
	}
	public void redButtonClicked(View view) {
		colors.addColor(Color.Red);
	}
	
	private Drawable buttonForColor(Color color) {
		
		if(color == null) {
			return getResources().getDrawable(R.drawable.empty_color_button);
		}
		
		switch(color) {
		case Blue:
			return getResources().getDrawable(R.drawable.blue_button);
		case Green:
			return getResources().getDrawable(R.drawable.green_button);
		case Orange:
			return getResources().getDrawable(R.drawable.orange_button);
		case Pink:
			return getResources().getDrawable(R.drawable.pink_button);
		case WhiteYellowSilver:
			return getResources().getDrawable(R.drawable.white_yellow_silver_button);
		case Red:
			return getResources().getDrawable(R.drawable.red_button);
		
		}
		throw new UnsupportedOperationException("Color "+color+" does not have a known corresponding drawable");
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if(savedInstanceState.containsKey("colors")) {
			colors = (ColorDataSet) savedInstanceState.get("colors");
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("colors", colors);
	}
	
	
	

}
