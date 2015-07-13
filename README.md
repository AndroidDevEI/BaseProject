# Base Database and Networking project

## Motivation
This project is created with the purpose of provide **basic reusable** structure for Android projects.

####The project setup contains:
Database structure created with **ORM Lite library.** 
Basic network setup for server client communication used 3rd party library **Volley Networking library.**
Implement **Google Cloud Messaging** for receiving push notifications. 

### Project documentation reference 
[javadoc](http://androiddevei.github.io/android-documentation/apidoc)

## Code Examples

#### Simple Volley request
##### Request item from web service
```
   private void requestNetworkData( ){
        RequestQueue queue = VolleySingleton.getRequestQueue();

        String url = Const.URL_API_REQUEST_CATEGORY_ITEM;

        StringRequest request = new StringRequest(Request.Method.GET,url,
                responseNetworkDataSuccess(),
                responseError()){
        };
        queue.add(request);
    }
```
#### Simple database request 
##### Create the category item
```
 public static CategoryItem createRecord(CategoryItem categoryItem){

        if(categoryItem !=null) {
            try {
                DatabaseManager.getInstance().getCategoryItemsDaoInstance().create(categoryItem);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            Log.d(TAG, "Creating null object");
        }
        return categoryItem;
    }

```
#### Register device for Google Cloud Messaging

        @Override
        protected String doInBackground(Void... params) {
               String msg = "";
                try {
                    if (mGoogleCloudMessaging == null) {
                        mGoogleCloudMessaging = GoogleCloudMessaging.getInstance(mActivity);
                    }

                    regId = mGoogleCloudMessaging.register(Const.PROJECT_NUMBER);
                    if(regId!=null){

                        mMessage = "Device registered, registration ID=" + regId;
                        Log.i("GCM", msg);

                        // You should send the registration ID to your server over HTTP,
                        // so it can use GCM/HTTP or CCS to send messages to your app.
                        // The request to your server should be authenticated if your app
                        // is using accounts.
                        sendRegistrationIdToBackend();


                        // Store the device registration ID in application preferences.
                        // There is no need to register again. Unless the app version is changed
                        // or the registration Id is lost.
                        storeRegistrationId(mContext, regId);
                    }

                } catch (IOException ex) {
                    mMessage  = "Error GCM exception :" + ex.getMessage();
                }
                return mMessage;
}

### Tests
#### Works on
* Supports devise with API 15 and above

