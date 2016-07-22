package titans.calculator;


import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateFormat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ResultData implements Parcelable{
    private String mSaveResultDate;
    private Double mResultString;

    public ResultData(Double mResultDouble){
        this.mResultString = mResultDouble;

        Date saveResultDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd. H:mm:ss", Locale.ENGLISH);

        mSaveResultDate = dateFormat.format(saveResultDate);
    }

    protected ResultData(Parcel in) {
        mSaveResultDate = in.readString();

        Date saveResultDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd. H:mm:ss", Locale.ENGLISH);

        mSaveResultDate = dateFormat.format(saveResultDate);
    }

    public static final Creator<ResultData> CREATOR = new Creator<ResultData>() {
        @Override
        public ResultData createFromParcel(Parcel in) {
            return new ResultData(in);
        }

        @Override
        public ResultData[] newArray(int size) {
            return new ResultData[size];
        }
    };

    public String getSavedResultDate(){
        return this.mSaveResultDate;
    }

    public Double getResultValue(){
        return this.mResultString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSaveResultDate);
        dest.writeDouble(mResultString);
    }
}

