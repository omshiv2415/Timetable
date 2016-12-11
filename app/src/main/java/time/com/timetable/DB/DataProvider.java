package time.com.timetable.DB;

/**
 * Created by VIRALKUMAR on 09/04/2016.
 */
public class DataProvider {

    private String mcode;
    private String mstartTime;
    private String mfinishTime;



    private String mDate;

    public DataProvider(String mcode, String mstartTime, String mfinishTime, String mDate) {

        this.mcode = mcode;
        this.mstartTime = mstartTime;
        this.mfinishTime = mfinishTime;
        this.mDate = mDate;


    }
    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }
    public String getMcode() {
        return mcode;
    }

    public void setMcode(String mcode) {
        this.mcode = mcode;
    }

    public String getMstartTime() {
        return mstartTime;
    }

    public void setMstartTime(String mstartTime) {
        this.mstartTime = mstartTime;
    }

    public String getMfinishTime() {
        return mfinishTime;
    }

    public void setMfinishTime(String mfinishTime) {
        this.mfinishTime = mfinishTime;
    }
}
