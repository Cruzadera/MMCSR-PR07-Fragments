package es.iessaladillo.pedrojoya.pr05.utils;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.widget.EditText;

public class IntentsImplicitUtils {
    private IntentsImplicitUtils(){}

    public static Intent sendEmail(EditText txt){
        String email = txt.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.fromParts("mailto", email, null));
        return intent;
    }

    public static Intent openMaps(EditText txt){
        String address = txt.getText().toString();
        Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        return mapIntent;
    }

    public static Intent startCall(EditText txt){
        String phoneNumber = txt.getText().toString();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        return intent;
    }

    public static Intent searchWeb(EditText txt){
        String web = txt.getText().toString();
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, web); // query contains search string
        return intent;
    }
}
