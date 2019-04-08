# Login-with-Facebook-Twitter-Google-
## Oauth2.0 
### oauth
- auth là một phương thức chứng thực, mà nhờ đó một web service hay một application bên thứ 3 có thể đại diện cho người dùng để truy cập vào tài nguyên người dùng nằm trên một dịch vụ nào đó. Các trào lưu cũng như sự lan rộng của khái niệm oauth bắt đầu khi mà các dịch vụ mạng xã hội như twitter hay facebook nở rộ, đặc biệt khi các công ty như twitter cung cấp API cho bên thứ 3 để có thể truy cập vào tài nguyên của người dùng, qua đó mở nên một thị trường mới gọi là 3rd party app, ví dụ như tweetdeck hay hootsuite.
### Tại sao oauth2 lại ra mắt 
- Oauth xuất hiện vào năm 2006, khi mà twitter phát triển hệ thống openId của họ , họ đã thống nhất nhiều bên gồm có facebook và google để thống nhất một "chuẩn" nhằm giúp cho application của bên thứ 3 có thể truy cập API của họ một cách dễ dàng hơn. Sau đó vào năm 2008, IETF (tổ chức chuyên đưa ra các chuẩn của internet) đã quyết định hỗ trợ cho chuẩn này, nhằm đưa ra một qui chuẩn thống nhất. Việc này dẫn đến bản RFC chính thức đầu tiên của oauth 1.0 vào năm 2010 (RFC 5849).

Sau đó xảy ra một sự kiện không ngờ khi có một lỗi bào mật khá nghiệm trọng xảy ra trên oauth1, gọi là session fixation. Lỗi này giúp cho attacker có thể "trick" cho một application thuộc bên thứ 3 "trao" cho hắn cái quyền để access vào account/resource của một ai đó bất kì. Bạn có thể tưởng tượng tài khoản facebook của bạn đang được ai đó ung dung sử dụng mà bạn không hề biết orz ...

Sự kiện này dẫn đến sự ra đời của oauth2 vào 2012. Vào thời gian đầu thì oauth2 cũng có không ít lỗi bảo mật, ví dụ như bạn có thể sử dụng chrome để hack facebook :)). Cho đến hiện nay thì các chuyên gia an ninh mạng vẫn đang cảnh báo hàng ngày về các khe hở bảo mật của oauth2, tuy nhiên oauth2 vẫn được sử dụng rộng rãi. 
### Oauth2.0

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
            
- Add meta data 

                        <meta-data
                android:name="com.facebook.sdk.ApplicationId"
                android:value="@string/facebook_app_id" />

            <activity
                android:name="com.facebook.FacebookActivity"
                android:configChanges="keyboard|keyboardHidden|screenLayout|screenSize|orientation"
                android:label="@string/app_name" />
            <activity
                android:name="com.facebook.CustomTabActivity"
                android:exported="true">
                <intent-filter><action android:name="android.intent.action.VIEW" />

                    <category android:name="android.intent.category.DEFAULT" />
                    <category android:name="android.intent.category.BROWSABLE" />

                    <data android:scheme="@string/fb_login_protocol_scheme" />
                </intent-filter>
            </activity>
            
- Khởi tạo Facebook SDK     
            
            @Override
            public void onCreate() {
                super.onCreate();
                FacebookSdk.sdkInitialize(getApplicationContext());
                AppEventsLogger.activateApp(this);
            }
            
- Add button Sign in 

             <com.facebook.login.widget.LoginButton
                android:id="@+id/login_button"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center_horizontal"
                android:layout_marginTop="8dp"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent" />
                
- Get access token 
        
            loginButton = (LoginButton) findViewById(R.id.login_button);
            loginButton.setReadPermissions("email",  "public_profile");

            // Callback registration
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    // Lấy access token sử dụng LoginResult
                    AccessToken accessToken = loginResult.getAccessToken();
                    getUserProfile(accessToken);
                }

                @Override
                public void onCancel() {
                    // App code
                }

                @Override
                public void onError(FacebookException exception) {
                    // App code
                }
            });
        
- Log out

                LoginManager.getInstance().logOut();
 
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
- Sử dụng method GoogleSignIn.getLastSignInAccount để lấy thông tin ng dùng đã đăng nhập 
            
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
            if (acct != null) {
              String personName = acct.getDisplayName();
              String personGivenName = acct.getGivenName();
              String personFamilyName = acct.getFamilyName();
              String personEmail = acct.getEmail();
              String personId = acct.getId();
              Uri personPhoto = acct.getPhotoUrl();
            }
            
- Có thể 1 số trường bị null tùy thuộc vào scopes request
### Signout and disconnecting Accounts
- Sign out 
                
                private void signOut() {
                    mGoogleSignInClient.signOut()
                            .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    // ...
                                }
                            });
                }
                
- Disconnect accounts

                private void revokeAccess() {
                    mGoogleSignInClient.revokeAccess()
                            .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    // ...
                                }
                            });
                }

### Get ID Token
- Khi cấu hình Google Sign-in, gọi method requestTokenId để đẩy lên server

                // Request only the user's ID token, which can be used to identify the
                // user securely to your backend. This will contain the user's basic
                // profile (name, profile picture URL, etc) so you should not need to
                // make an additional call to personalize your application.
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.server_client_id))
                        .requestEmail()
                        .build();
                        
- Khi bật app, kiểm tra xem ng dùng đã đặp nhập vào ứng dụng của mình bằng Google trên tb này hay tb khác

                GoogleSignIn.silentSignIn()
                        .addOnCompleteListener(this, new OnCompleteListener<GoogleSignInAccount>() {
                            @Override
                            public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                                handleSignInResult(task);
                            }
                        });

- Get ID Token qua GoogleSign Object 

                private void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
                    try {
                        GoogleSignInAccount account = completedTask.getResult(ApiException.class);
                        String idToken = account.getIdToken();

                        // TODO(developer): send ID Token to server and validate

                        updateUI(account);
                    } catch (ApiException e) {
                        Log.w(TAG, "handleSignInResult:error", e);
                        updateUI(null);
                    }
                }
                
### Verify the integrity of the ID token
- Sử dụng Google API Client để xác thực Google ID token 

                import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
                import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
                import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

                ...

                GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    // Specify the CLIENT_ID of the app that accesses the backend:
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    // Or, if multiple clients access the backend:
                    //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                    .build();

                // (Receive idTokenString by HTTPS POST)

                GoogleIdToken idToken = verifier.verify(idTokenString);
                if (idToken != null) {
                  Payload payload = idToken.getPayload();

                  // Print user identifier
                  String userId = payload.getSubject();
                  System.out.println("User ID: " + userId);

                  // Get profile information from payload
                  String email = payload.getEmail();
                  boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
                  String name = (String) payload.get("name");
                  String pictureUrl = (String) payload.get("picture");
                  String locale = (String) payload.get("locale");
                  String familyName = (String) payload.get("family_name");
                  String givenName = (String) payload.get("given_name");

                  // Use or store profile information
                  // ...

                } else {
                  System.out.println("Invalid ID token.");
                }

### Enableing Server-Side Access
- Nếu bạn muốn server của mình có thể thực hiện các cuộc gọi API Google thay mặt cho người dùng có thể trong khi họ ngoại tuyến, thì server của bạn yêu cầu access token
- Khi cấu hình Google Sign-in, built GoogleSignInOptions với method requestServerAuthCode, truyền server's client Id cho requestServerAuthcode

                // Configure sign-in to request offline access to the user's ID, basic
                // profile, and Google Drive. The first time you request a code you will
                // be able to exchange it for an access token and refresh token, which
                // you should store. In subsequent calls, the code will only result in
                // an access token. By asking for profile access (through
                // DEFAULT_SIGN_IN) you will also get an ID Token as a result of the
                // code exchange.
                String serverClientId = getString(R.string.server_client_id);
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))
                        .requestServerAuthCode(serverClientId)
                        .requestEmail()
                        .build();
                        
- Get Auth Server code

                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    try {
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        String authCode = account.getServerAuthCode();

                        // Show signed-un UI
                        updateUI(account);

                        // TODO(developer): send code to server and exchange for access/refresh/ID tokens
                    } catch (ApiException e) {
                        Log.w(TAG, "Sign-in failed", e);
                        updateUI(null);
                    }
                    
## Login with twitter
- Tạo button Login twitter

                <com.twitter.sdk.android.core.identity.TwitterLoginButton
                 android:id="@+id/login_button"
                 android:layout_width="wrap_content"
                 android:layout_height="wrap_content" />
                 
 - Tạo call back
 
                loginButton = (TwitterLoginButton) findViewById(R.id.login_button);
                loginButton.setCallback(new Callback<TwitterSession>() {
                   @Override
                   public void success(Result<TwitterSession> result) {
                       // Do something with result, which provides a TwitterSession for making API calls
                   }

                   @Override
                   public void failure(TwitterException exception) {
                       // Do something on failure
                   }
                });
                
### TwitterSession
- Nếu đăng nhập hoàn tất thành công, TwitterSession sẽ được cung cấp trong kết quả thành công. TwitterSession này sẽ chứa toekn, scret, tên người dùng và ID người dùng của người dùng và trở thành session và được tự động duy trì. Nếu bạn cần truy xuất TwitterSession sau đó, bạn có thể làm như vậy bằng session manager
            
            TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
            TwitterAuthToken authToken = session.getAuthToken();
            String token = authToken.token;
            String secret = authToken.secret;
            
- Request email

            TwitterAuthClient authClient = new TwitterAuthClient();
            authClient.requestEmail(session, new Callback<String>() {
                @Override
                public void success(Result<String> result) {
                    // Do something with the result, which provides the email address
                }

                @Override
                public void failure(TwitterException exception) {
                  // Do something on failure
                }
            });

