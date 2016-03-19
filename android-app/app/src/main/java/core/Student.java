package core;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

/**
 * Created by saini on 19-03-2016.
 */
public class Student {
    Context mContext;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public static final String DIR = Environment.getExternalStorageDirectory().getAbsolutePath();

    public Student(Context context) {
        mContext = context;
        preferences = mContext.getSharedPreferences("stu", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }


}
