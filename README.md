# Login-with-Facebook-Twitter-Google-
## Login with facebook
## SDK Facebook
- SDK Facebook dành cho Android cho phép ta đăng nhập ứng dụng với Facebook. Khi ta đăng nhập với Facebook, ta có thể cấp quyền để truy xuất thông tin, hoặc thực hiện hành động trên Facebook thay mặt họ
## Guide
- Vào https://developers.facebook.com/ để tạo app mới
- Tạo xong app vào dashboard của ứng dụng ta sẽ thấy App ID của ứng dụng ở góc màn hình 
- Select Platform cho app : Android 
- Add mavenCentral() vào buildgradle
- Thêm dependecy Facebook
    
            implementation 'com.facebook.android:facebook-android-sdk:[4,5)'

- Add package name ứng dụng, và tên activity default của ứng dụng 
- Gen hash key -> add vào
- Add manifest internet

                <uses-permission android:name="android.permission.INTERNET"/>

- Thêm appid và protocol_scheme vào app 

                <string name="facebook_app_id">xxx</string>
                <string name="fb_login_protocol_scheme">fbxxx</string>
                
