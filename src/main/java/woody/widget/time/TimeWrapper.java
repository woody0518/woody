package woody.widget.time;

/**
 * Created by DELL on 2015/12/11.
 */
public interface TimeWrapper {

    enum TimeType {
        HH_MM_SS_PERDAY, YYYY_MM_DD_PERYEAR, YYYY_MM_DD_HH_MM_SS, TIME_STRING
    }

    int getInerVal();

    int getStartTime();

    TimeType getTimeType();

    String getFormateType();

}
