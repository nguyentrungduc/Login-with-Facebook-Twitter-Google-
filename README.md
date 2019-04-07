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


