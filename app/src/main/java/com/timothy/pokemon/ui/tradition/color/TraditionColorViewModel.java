package com.timothy.pokemon.ui.tradition.color;

import android.content.res.XmlResourceParser;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.timothy.common.base.BaseViewModel;
import com.timothy.common.data.base.DataStatus;
import com.timothy.common.data.livedata.CustomBaseDataListLiveData;
import com.timothy.common.manager.AppManager;
import com.timothy.pokemon.data.tradition.TraditionColorData;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TraditionColorViewModel extends BaseViewModel {

    private CustomBaseDataListLiveData<TraditionColorData> traditionColorsLiveData = new CustomBaseDataListLiveData<>();

    private List<TraditionColorData> mTraditionColors;

    @Override
    protected void initData() {
        getDataStatus().postValue(DataStatus.START);
        mTraditionColors = new ArrayList<>();

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                XmlResourceParser xmlResourceParser = AppManager.getInstance().getApplication().getResources().getXml(com.timothy.resource.R.xml.tradition_colors);
                try {
                    while (xmlResourceParser.getEventType() != XmlPullParser.END_DOCUMENT){
                        if (xmlResourceParser.getEventType() == XmlPullParser.START_TAG){
                            if ("ListItem".equals(xmlResourceParser.getName())){
                                mTraditionColors.addAll(getXmlItems(xmlResourceParser));
                            }
                        }
                        xmlResourceParser.next();
                    };
                    if (mTraditionColors.isEmpty()){
                        getDataStatus().postValue(DataStatus.EMPTY_DATA);
                    }else {
                        traditionColorsLiveData.postValue(mTraditionColors);
                        getDataStatus().postValue(DataStatus.SUCCESS);
                    }
                } catch (XmlPullParserException | IOException e) {
                    e.printStackTrace();
                    getDataStatus().postValue(DataStatus.EMPTY_DATA);
                }
//                TraditionColorData colorData = new TraditionColorData();
//                colorData.id = "1";
//                colorData.name = "樱草色";
//                colorData.colorHex = "#eaff56";
//                mTraditionColors.add(colorData);
//                traditionColorsLiveData.postValue(mTraditionColors);
//                getDataStatus().postValue(DataStatus.SUCCESS);
            }
        }, 2000);
    }

    public CustomBaseDataListLiveData<TraditionColorData> getTraditionColorList(){
        return traditionColorsLiveData;
    }

    private List<TraditionColorData> getXmlItems(XmlResourceParser parser) throws XmlPullParserException, IOException {
        List<TraditionColorData> colorDataList = new ArrayList<>();
        while (true){
            parser.next();
            if ((parser.getEventType() == XmlPullParser.END_TAG && "ListItem".equals(parser.getName())) || parser.getEventType() == XmlPullParser.END_DOCUMENT){
                break;
            }

            if (parser.getEventType() == XmlPullParser.START_TAG){
                if ("item".equals(parser.getName())){
                    TraditionColorData data = new TraditionColorData();
                    data.colorHex = parser.nextText();
                    StringBuilder builder = new StringBuilder("item: ");
                    for (int i = 0; i < parser.getAttributeCount(); i++){
                        String tagName = parser.getAttributeName(i);
                        String value = parser.getAttributeValue(i);
                        if ("id".equals(tagName)){
                            data.id = value;
                        }else if ("name".equals(tagName)){
                            data.name = value;
                        }
                        builder.append(tagName).append("=").append(value).append(";");
                    }
                    colorDataList.add(data);
                    builder.append("colorHex=").append(parser.nextText());
                    Log.d("motao", builder.toString());
                }
            }
        }
        return colorDataList;
    }
}
