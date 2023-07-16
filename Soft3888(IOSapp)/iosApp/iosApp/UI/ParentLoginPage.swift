//
//  ContentView.swift
//  onTaskArchiever
//
//  Created by chris on 22/8/2022.
//

import SwiftUI
import shared
import Firebase
import AuthenticationServices
import CryptoKit
import CodeScanner

//var child1 = Child(userID: "1", name: "Linda", dateOfBirth: "2012/02/14", chooseTheme: Theme(name: "Disney"), avatarPic: "Poly")
//
//var child2 = Child(userID: "2", name: "Anna", dateOfBirth: "2012/03/14", chooseTheme: Theme(name: "Marvel"), avatarPic: "IronMan")
//
//var child3 = Child(userID: "3", name: "Bulankin", dateOfBirth: "2012/05/14", chooseTheme: Theme(name: "T-34"), avatarPic: "PP_50")
//
//var child4: Child = Child(userID: "4", name: "Frank", dateOfBirth: "2001", chooseTheme: Theme(name: "T-34"), avatarPic: "Default")
//
//var child5: Child = Child(userID: "5", name: "Frank", dateOfBirth: "2001", chooseTheme: Theme(name: "Minecraft"), avatarPic: "Default")

struct ParentLoginPage: View {
    
    @State var email: String = ""
    @State var password: String = ""
    @State var goToScan = false
    @State var goToChildDashboard = false
    @State var valid: Float = 0
    @State var logInFail = false
    
    @EnvironmentObject var authViewModel: AuthViewModel
    @EnvironmentObject var childAuthViewModel: ChildAuthViewModel
    @EnvironmentObject var contractViewModel: ContractViewModel
    @State var isPresentingScanner = false
    @State var scannedCode: String = "scan qr code"
    
    @EnvironmentObject var assignFinishChoresModel : AssignChoreModel
    
        
    var scannerSheet : some View {
        CodeScannerView(
            codeTypes: [.qr],
            completion: { result in
                if case let .success(code) = result {
                    self.scannedCode = code.string
                    self.isPresentingScanner = false
                    
                    print("current qr code is :" + self.scannedCode)
                    if(scannedCode != "scan qr code"){

                        if(scannedCode != ""){
                            self.childAuthViewModel.signIn(childSession: scannedCode)
                            self.assignFinishChoresModel.fetchUserFinishedChoresInChildVer(currentChildID: String(self.childAuthViewModel.childSession.split(separator: " ")[0]), currentparentID: String(self.childAuthViewModel.childSession.split(separator: " ")[1]))
                            goToChildDashboard = true
                        }
                    }
                }
            })
    }
                

    var body: some View {

        NavigationView {
            ZStack {

//                another way to navigate with button and NavigationLink
                Button(action: {
                   // goToScan = true
                    self.isPresentingScanner = true
                }, label: {
                    Image("QrCodeScan")
                })
                .sheet(isPresented: $isPresentingScanner){
                    self.scannerSheet
                }
                .frame(width: UIScreen.main.bounds.width*0.9, height: UIScreen.main.bounds.height*0.9, alignment: .topTrailing)
                .zIndex(100)


                VStack {

                    ProfilePhoto()

                    WelcomeAndSignUpText()

                    EntryField(textValue: $email, icon: Image("emailIcon"), placeholder: "Email Address", prompt: "", validation: $valid, isPassword: false)
                        .padding(.bottom, 3)
                    EntryField(textValue: $password, icon: Image("locksign"), placeholder: "Password: ", prompt: "", validation: $valid, isPassword: true)



                    Text("Forgot your password?")
                        .font(.caption)
                        .fontWeight(.light)
                        .foregroundColor(Color.blue)
                        .padding(.bottom, UIScreen.main.bounds.height*0.05)

                    if authViewModel.loginFailed == true{
                        Text("email or password is incorrect")
                    }

                    Button(action: {
                        contractViewModel.setParentID(parentID: authViewModel.userSession?.uid ?? "Empty UID")
                        authViewModel.loginWithEmail(email: email, password: password)
                        
                    },
                           label: {
                        Image("loginBtn")
                    })
                    .padding(.bottom, UIScreen.main.bounds.height*0.03)


                    Image("separateLine")
                        .padding(.bottom, UIScreen.main.bounds.height*0.03)

                    ThirdPartyLogo()
                }
            }
            .background(Image("Background").ignoresSafeArea().opacity(0.2))
            .onTapGesture {
                hideKeyboard()
            }

        }
        .navigationBarHidden(true)


    }

}



struct ProfilePhoto: View {
    var body: some View {
        ZStack{
            Image("photoframe")
                .frame(alignment: .center)
            Image("userPhoto")
                .frame(alignment: .center)
        }
    }
}

struct WelcomeAndSignUpText: View {
    var body: some View {
        HStack {
            VStack {
                Text("   Welcome back!")
                    .fontWeight(.thin)
                    .multilineTextAlignment(.leading)
                
                
                HStack {
                    Text("If you dont have account,")
                        .fontWeight(.thin)
                    
                    
                    NavigationLink(destination: SignUpPage(), label: {
                        Text("Sign up")
                    })
                }
            }
            
        }
        .padding(.bottom, 10)
    }
}

struct ThirdPartyLogo: View {
    @EnvironmentObject var authViewModel: AuthViewModel
    @EnvironmentObject var contractViewModel: ContractViewModel
    @EnvironmentObject var choreViewModel: ChoreViewModel
    @EnvironmentObject var addChildViewModel: AddChildViewModel
    @State private var signInWithAppleDelegates: SignInWithAppleDelegates! = nil
    @Environment(\.window) private var window: UIWindow?
    var body: some View {
        HStack{
            Spacer()
            
            Button(action: {
                showAppleLogin()
                contractViewModel.setParentID(parentID: authViewModel.userSession?.uid ?? "Empty ID")
                choreViewModel.relogin()
                addChildViewModel.relogin()
            }, label: {Image("AppleLoginBtn")})
            
            Spacer()
            
            Button(action: {
                authViewModel.loginWithGoogle()
                
                contractViewModel.setParentID(parentID: authViewModel.userSession?.uid ?? "Empty ID")
            }, label: {Image("GoogleLoginBtn")})
            Spacer()
            Button(action: {
                authViewModel.loginWithFacebook()
                contractViewModel.setParentID(parentID: authViewModel.userSession?.uid ?? "Empty ID")
            }, label: { Image("FacebookLoginBtn")})
           
            Spacer()
        }
    }
    
    func showAppleLogin(){
        
        let currentNonce = randomNonceString()
        let request = ASAuthorizationAppleIDProvider().createRequest()
        request.requestedScopes = [.fullName, .email]
        request.nonce = sha256(currentNonce)
        
        performSignIn(using: [request], currentNonce: currentNonce)
    }
    
//    func performExistingAccountSetUpFlow(){
//        let requsts = [
//            ASAuthorizationAppleIDProvider().createRequest(),
//            ASAuthorizationPasswordProvider().createRequest()
//        ]
//        performSignIn(using: requsts, )
//    }
    
    func performSignIn(using requests: [ASAuthorizationRequest], currentNonce: String){
        signInWithAppleDelegates = SignInWithAppleDelegates(window: window, currentNonce, authViewModel: authViewModel, choreViewModel: choreViewModel, addChildViewModel: addChildViewModel,onSignedIn: { result in
            switch result{
            case .success(let userId):
                print("DEBUG: sign in succeed \(userId)")

            case .failure(let error):
                print("DEBUG: sign in with apple error \(error.localizedDescription)")
                print("DEBUG: \(error)")
            }
        })
        let controller = ASAuthorizationController(authorizationRequests: requests)
        controller.delegate = self.signInWithAppleDelegates
        controller.presentationContextProvider = signInWithAppleDelegates
        
        controller.performRequests()
    }
}

