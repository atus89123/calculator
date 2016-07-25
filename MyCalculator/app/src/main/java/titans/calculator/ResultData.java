package titans.calculator;


import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ResultData implements Parcelable{
    private Double mResultValue;
    private String mSaveResultDate;

    public ResultData(Double mResultDouble){
        this.mResultValue = mResultDouble;

        Date saveResultDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd. H:mm:ss", Locale.ENGLISH);

        mSaveResultDate = dateFormat.format(saveResultDate);
    }

    public String getSavedResultDate(){
        return this.mSaveResultDate;
    }

    public Double getResultValue(){
        return this.mResultValue;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(mResultValue);
        dest.writeString(mSaveResultDate);
    }

    private ResultData(Parcel in){
        mResultValue = in.readDouble();
        mSaveResultDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

     public static final Parcelable.Creator<ResultData> CREATOR = new Parcelable.Creator<ResultData>() {
        @Override
        public ResultData createFromParcel(Parcel in) {
            return new ResultData(in);
        }

        @Override
        public ResultData[] newArray(int size) {
            return new ResultData[size];
        }
    };
}