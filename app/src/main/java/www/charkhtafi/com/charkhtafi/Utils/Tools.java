package www.charkhtafi.com.charkhtafi.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import www.charkhtafi.com.charkhtafi.MainActivity;
import www.charkhtafi.com.charkhtafi.R;
import www.charkhtafi.com.charkhtafi.Utils.Views.CFProvider;
import www.charkhtafi.com.charkhtafi.Utils.Views.CustomTextView;


/**
 * Created by "SAJJAD JAFARI"
 * Author by sajjad jafari
 * Hamnama Application
 * 16 Novambr 2017 && 1396/8/25
 */

public class Tools {

    private static Tools tools;
    private static SharedPreferences sharedPreferences;
    private static String Name = "Restaurant";
    public Context context;

    private Tools(Context context) {
        this.context = context;
    }

    public static synchronized Tools getInstance(Context myContext) {
        if (tools == null) {
            tools = new Tools(myContext);
            if (sharedPreferences == null) {
                sharedPreferences = myContext.getSharedPreferences(Name, Context.MODE_PRIVATE);
            }
        }
        return tools;
    }

    public String FormattedPrice(String number) {
        String FormattedNumber = "";
        for (int i = 0 ; i < number.length() ; i++) {
            if (((number.length()) - i) % 3 == 0 && i != 0) {
                FormattedNumber += ",";
            }
            FormattedNumber += number.charAt(i);
        }
        return FormattedNumber;
    }

    public String UnFormattedPrice(String number) {
        String FormattedNumber = "";
        for (int i = 0 ; i < number.length() ; i++) {
            if (isNumeric(String.valueOf(number.charAt(i)))) {
                FormattedNumber += number.charAt(i);
            }
        }
        return FormattedNumber;
    }

    public boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }

    public String FormattedPrice2(String number) {
        String prezzo = "";
        try {

            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###", symbols);
            prezzo = decimalFormat.format(Integer.parseInt(number));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prezzo;
    }

    public String read(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    public void write(String key, String value) {
        SharedPreferences.Editor text = sharedPreferences.edit();
        text.putString(key, value);
        text.apply();
    }

    public boolean read(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    public void write(String key, boolean value) {
        SharedPreferences.Editor text = sharedPreferences.edit();
        text.putBoolean(key, value);
        text.apply();
    }

    public int read(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    public void write(String key, int value) {
        SharedPreferences.Editor number = sharedPreferences.edit();
        number.putInt(key, value);
        number.apply();
    }

    public boolean Exist(String key) {
        return sharedPreferences.contains(key);
    }

    public void Clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public boolean isOnline() {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            return (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI
                    ||
                    activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) ? true : false;
        }
        return false;
    }

    public String getDate() {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.getTimeInMillis();
        return DateFormat.format("yyyy-MM-dd", calendar).toString();
    }

    public void SnackBar(ViewGroup viewGroup, String text, int type) {
        // if type == 1 means it dosn't need button if typee == 2 means it need button
        Snackbar snackbar = Snackbar.make(viewGroup, "", Snackbar.LENGTH_LONG);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
//        layout.setBackgroundColor(Color.rgb(231,76,60));
        layout.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_login_btn));
        TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setVisibility(View.INVISIBLE);
        View snackView = LayoutInflater.from(context).inflate(R.layout.custom_snackbar, null);

        CustomTextView SingleText = (CustomTextView) snackView.findViewById(R.id.Custom_SnackBar_Text);
        TextView FirstText = (TextView) snackView.findViewById(R.id.Custom_SnackBar_Linear_First);
        TextView SecondText = (TextView) snackView.findViewById(R.id.Custom_SnackBar_Linear_Second);
        LinearLayout Linear = (LinearLayout) snackView.findViewById(R.id.Custom_SnackBar_Linear);
        if (type == 1) {
            Linear.setVisibility(View.GONE);
            SingleText.setVisibility(View.VISIBLE);
            SingleText.setText(text);
        } else {
            Linear.setVisibility(View.VISIBLE);
            SingleText.setVisibility(View.GONE);
            FirstText.setText(text);
        }


        layout.addView(snackView);
        snackbar.show();
    }

    public void ToastMessage(String Text) {
        CustomTextView mytext = new CustomTextView(context);
        mytext.setText(Text);
        mytext.setTextColor(Color.WHITE);
        mytext.setBackgroundColor(Color.rgb(231, 76, 60));
        mytext.setPadding(17, 17, 17, 17);
        mytext.setTypeface(CFProvider.getIRANIANSANS(context));
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(mytext);
        toast.show();
    }

    public boolean isValidEmail(CharSequence target) {
        return target != null && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public boolean isValidPhone(CharSequence target) {
        return target != null && Patterns.PHONE.matcher(target).matches();
    }

    public boolean hasIceCreamSandwich() {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    public boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public boolean hasMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public boolean hasNougat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    public String getStringResourceByName(String aString) {
        int resId = context.getResources().getIdentifier(aString, "string", context.getPackageName());
        return context.getString(resId);
    }

    public String encodeData(String hash) {
        try {
            return String.valueOf(Base64.encode(hash.getBytes("UTF-8"), Base64.DEFAULT));
        } catch (Exception e) {
            // TODO: 10/24/2017 16:53
        }
        return null;
    }

    public String decodeData(String hash) {
        try {
            return String.valueOf(Base64.decode(hash, Base64.DEFAULT));
        } catch (Exception e) {
            // TODO: 10/24/2017 16:53
        }
        return null;
    }

    public boolean compareDates(String d1, String d2) {
        boolean result = false;
        try {
            // If you already have date objects then skip 1

            //1
            // Create 2 dates starts
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(d1);
            Date date2 = sdf.parse(d2);

            Log.e("mydate1", "Date1" + sdf.format(date1));
            Log.e("mydate2", "Date2" + sdf.format(date2));

            // Create 2 dates ends
            //1

            // Date object is having 3 methods namely after,before and equals for comparing
            // after() will return true if and only if date1 is after date 2
//            if(date1.after(date2)){
//                Log.e("mydate3", "Date1 is after Date2");
//            }

            // before() will return true if and only if date1 is before date2
            if (date1.before(date2) || date1.equals(date2)) {
                result = true;
            } else {
                result = false;
            }

            //equals() returns true if both the dates are equal
//            if(date1.equals(date2)){
//                Log.e("mydate5", "Date1 is equal Date2");
//            }

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    // change the format of date
    public String parseDateToddMMyyyy(String time) {
        String outputPattern = "yyyy-MM-dd";
        String inputPattern = "dd-MMM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public String ConvertNumberFormat(int number, int status) {
        String result = "";
        DecimalFormat Format = new DecimalFormat(".#");
        if (number > 999) {
            result = (Format.format((float) number / 1000)) + " k";
        } else if (number > 999999) {
            result = (Format.format((float) number / 1000)) + "m";
        } else if (number > 999999) {
            result = (Format.format((float) number / 1000)) + "g";
        } else {
            if (status == 0) {
                result = "+" + number;
            } else {
                result = String.valueOf(number);
            }
        }

        return result;
    }

    public String ConvertPersianNumberToEnglishNumber(String number) {
//      private static String arabicToDecimal(String number) {
        char[] chars = new char[number.length()];
        for (int i = 0 ; i < number.length() ; i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
        return new String(chars);

    }

    public String TypeOfUser(int type) {
        switch (type) {
            case 1:
                return "LimitLessUser";
            case 2:
                return "LimitLessUser";
            case 3:
                return "LimitUser";
            case 4:
                return "BikeDelivery";
        }
        return null;
    }

    public float CalculateOff(float sum, float off) {

        return sum - ((off / 100f) * sum);

    }


    public String DecodeData(String data) {
        byte[] decededString = Base64.decode(data, Base64.DEFAULT);
        String stringconverted = new String(decededString, Charset.forName("UTF-8"));
        String newData = new String(Base64.decode
                (stringconverted.substring(20, stringconverted.length() - 20), Base64.DEFAULT), Charset.forName("UTF-8"));
        return new String(newData.getBytes(), Charset.forName("UTF-8"));
    }

    public String EncodeData(String data) {
        byte[] decededString = Base64.encode(data.getBytes(), Base64.DEFAULT);
//        Log.e("step1", new String(decededString, Charset.forName("UTF-8")) + " | ");

        String generatedString = generateString(new Random(), "abcdefghijklmnopqrstuwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890", 20);

        String generatedString1 = generateString(new Random(), "abcdefghijklmnopqrstuwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890", 20);

        String stringconverted = new String(decededString, Charset.forName("UTF-8"));

        stringconverted = generatedString + stringconverted + generatedString1;

//        Log.e("step2", new String(stringconverted.getBytes(), Charset.forName("UTF-8")) + " | ");


//        Log.e("step3", new String(Base64.encode(stringconverted.getBytes(), Base64.DEFAULT), Charset.forName("UTF-8")) + " | " +
//                encodeData(new String(Base64.encode(stringconverted.getBytes(), Base64.DEFAULT), Charset.forName("UTF-8"))));
        return new String(stringconverted.getBytes(), Charset.forName("UTF-8"));
    }

    public static String generateString(Random rng, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0 ; i < length ; i++) {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }

    public String getUserType(MainActivity.UserType userType) {

        switch (userType) {
            case MainKargozar:
                return "3213655546";
            case SecondKargozar:
                return "3213655547";
            case Bike:
                return "3213655548";
        }
        return "";
    }

}
