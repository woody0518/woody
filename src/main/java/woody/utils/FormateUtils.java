package woody.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;

import com.woody.lib.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 格式化的工具类
 * Created by woody on 2015/12/8.
 */
public class FormateUtils {


    public static final String TIME_FORMATE_YY_MM_DD = "yyyy-MM-dd";
    public static final String TIME_FORMATE_YY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    private Context mContext;

    private FormateUtils() {
    }

    private FormateUtils(Context mContext) {
        mContext = mContext;
    }

    private static FormateUtils mFormateUtils;

    public static FormateUtils getInstance(Context context) {
        if (mFormateUtils == null) {
            mFormateUtils = new FormateUtils(context);
        }

        return mFormateUtils;
    }

    /**
     * 解析一天的时间成00:00:00的形式
     *
     * @param time
     * @return
     */
    public String formatNumberOneDay(long time) {
        String ft = "00:00:00";// 没匹配上时:1.等于0时; 2.大于等于86400时.
        if (time > 0 && time < 60) {
            ft = "00:00:" + (time < 10 ? "0" + time : time);
        } else if (time >= 60 && time < 3600) {
            ft = "00:" + (time / 60 >= 10 ? time / 60 : "0" + time / 60) + ":"
                    + (time % 60 >= 10 ? time % 60 : "0" + time % 60);
        } else if (time >= 3600 && time < 86400) {
            ft = (time / 3600 >= 10 ? time / 3600 : "0" + time / 3600) + ":"
                    + (time % 3600 / 60 >= 10 ? time % 3600 / 60 : "0" + time % 3600 / 60) + ":"
                    + (time % 60 >= 10 ? time % 60 : "0" + time % 60);
        }
        return ft;
    }

    /**
     * formateServerTime by long time
     *
     * @param serverTime
     * @return yyyy-MM-dd/昨天/小时.分钟
     */
    public String formateTime(long serverTime, String formateType) {
        Date date = new Date(serverTime);
        Date nowData = new Date();
        long nowTime = nowData.getTime();
        long l = nowTime - serverTime;
        return parseTime(date, l, formateType);
    }

    /**
     * formateTime by type yyyy-MM-dd HH:mm:ss
     * 解析一段时间时间成文本
     *
     * @param time
     * @return
     */
    public String parsePastTime(String time) {
        String result = "";
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date pastTime = df.parse(time);
            long l = System.currentTimeMillis() - pastTime.getTime();
            return parseTime(pastTime, l, FormateUtils.TIME_FORMATE_YY_MM_DD);
        } catch (ParseException e) {
            return "";
        }
    }


    /**
     * 解析时间成文本
     *
     * @param pastTime
     * @param l
     * @return
     */
    @NonNull
    private String parseTime(Date pastTime, long l, String formateType) {
        if (l >= 0 && l < 60 * 1000) {
            return mContext.getString(R.string.now);
        } else if (l >= 60 * 1000 && l < 60 * 60 * 1000) {
            int minutes = (int) (l / (60 * 1000));
            return minutes + mContext.getString(R.string.min_before);
        } else if (l >= 60 * 60 * 1000 && l < 24 * 60 * 60 * 1000) {
            int hour = (int) (l / (60 * 60 * 1000));
            return hour + mContext.getString(R.string.hour_before);
        } else if (l >= 24 * 60 * 60 * 1000 && l < 2 * 24 * 60 * 60 * 1000) {
            return mContext.getString(R.string.yesterday_before);
        } else {
            return DateFormat.format(formateType, pastTime).toString();
        }
    }


    public String formateHoldTime(long l) {

        if (l >= 0 && l < 60 * 1000) {
            return "一分钟";
        } else if (l >= 60 * 1000 && l < 60 * 60 * 1000) {
            int minutes = (int) (l / (60 * 1000));
            return "0时" + minutes + "分";
        } else if (l >= 60 * 60 * 1000 && l < 24 * 60 * 60 * 1000) {
            int hour = (int) (l / (60 * 60 * 1000));
            int minutes = (int) ((l / (60 * 1000)) % 60);
            return hour + "时" + minutes + "分";
        } else if (l >= 24 * 60 * 60 * 1000 && l < 2 * 24 * 60 * 60 * 1000) {
            return "一天";
        } else {
            return "0时0分";
        }
    }


}
