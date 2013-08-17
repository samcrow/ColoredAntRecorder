package org.samcrow.antrecorder.util;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class AlertDialogFragment extends DialogFragment {

    public static AlertDialogFragment newInstance(int title) {
        AlertDialogFragment frag = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title_int", title);
        frag.setArguments(args);
        return frag;
    }
    
    public static AlertDialogFragment newInstance(String title) {
        AlertDialogFragment frag = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putString("title_string", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	if(getArguments().containsKey("title_string")) {
	        String title = getArguments().getString("title_string");
	
	        return new AlertDialog.Builder(getActivity())
	                .setIcon(android.R.drawable.ic_dialog_alert)
	                .setTitle(title)
	                .setPositiveButton(android.R.string.ok,
	                    new DialogInterface.OnClickListener() {
	                        public void onClick(DialogInterface dialog, int whichButton) {
	                            
	                        }
	                    }
	                )
	                .create();
	        
    	}
    	else {
    		//Try with an int parameter
    		int title = getArguments().getInt("title_int");
    		
	        return new AlertDialog.Builder(getActivity())
	                .setIcon(android.R.drawable.ic_dialog_alert)
	                .setTitle(title)
	                .setPositiveButton(android.R.string.ok,
	                    new DialogInterface.OnClickListener() {
	                        public void onClick(DialogInterface dialog, int whichButton) {
	                            
	                        }
	                    }
	                )
	                .create();
    	}
    }
}