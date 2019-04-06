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
                
## Login with Google 
- Add Google's Maven repository 

            allprojects {
                repositories {
                    google()

                    // If you're using a version of Gradle lower than 4.1, you must instead use:
                    // maven {
                    //     url 'https://maven.google.com'
                    // }
                }
            }
            
- Add Google Play Servies dependency

            apply plugin: 'com.android.application'
            ...

            dependencies {
                compile 'com.google.android.gms:play-services-auth:16.0.1'
            }
### Add sign-in  
- Cấu hình đăng nhập Google

            // Configure sign-in to request the user's ID, email address, and basic
            // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
                    
- Tạo GoogleSignInCLient Object 

            // Build a GoogleSignInClient with the options specified by gso.
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            
- Check xem người dùng đã đăng nhập chưa 

            // Check for existing Google Sign In account, if the user is already signed in
            // the GoogleSignInAccount will be non-null.
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            updateUI(account);
 - Nếu account != null thì người dùng đã đăng nhập = null -> chưa đăng nhập
 - Add Google Button Sign
 
                <com.google.android.gms.common.SignInButton
                 android:id="@+id/sign_in_button"
                 android:layout_width="wrap_content"
                 android:layout_height="wrap_content" />
                 
 - Sign in 
            
            private void signIn() {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
            
- Intent sẽ mở ra dialog cho người dùng chọn Account để đăng nhập, nếu yêu cầu thêm về profile, email, openid, user cũng được nhắc quyền trong yêu cầu 
- Sau khi chọn account, ta có thể lấy GoogleSignInAccount trong onActivityForResult()

                @Override
                public void onActivityResult(int requestCode, int resultCode, Intent data) {
                    super.onActivityResult(requestCode, resultCode, data);

                    // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
                    if (requestCode == RC_SIGN_IN) {
                        // The Task returned from this call is always completed, no need to attach
                        // a listener.
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        handleSignInResult(task);
                    }
                }
- GoogleSignInAccount chứa thông tin về người dùng đã đăng nhập như name, mail,...



