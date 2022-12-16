package com.mohammed.mosa.eg.egsim.objects;

import android.view.View;

public interface UsedListener {

    void onCall(String code);

    void onSendSms(String msg, String code);

}
