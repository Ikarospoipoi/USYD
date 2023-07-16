//
//  AuthViewModel.swift
//  iosApp
//
//  Created by Edward Zheng on 13/9/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import Firebase
import FirebaseAuth
import FirebaseCore
import FirebaseFirestore
import GoogleSignIn
import shared
import CryptoKit
import AuthenticationServices
import FirebaseAuth

import FacebookCore
import FacebookLogin
import FacebookShare

class AuthViewModel: ObservableObject{
    @Published var nonce = ""
    
    @Published var userSession:FirebaseAuth.User?
    @Published var currentUser: Parent?
    
    @Published var emailValid: Float = 0
    @Published var emailPrompt = ""
    @Published var passwordValid: Float = 0
    @Published var passwordPrompt = ""
    @Published var nameValid: Float = 0
    @Published var namePrompt = ""
    @Published var dateOfBirthValid: Float = 0
    @Published var dateOfBirthPrompt = ""
    
    @Published var loginFailed = false
    @Published var progressing = false
    @Published var isEditSheetPresent = false
    
    @Published var logged = true
    @Published var email = ""
    @State var manager = LoginManager()
    @AppStorage("childSession") var childSession = ""
    
    
    
    var service = UserService()
    
    init(){
        self.userSession = Auth.auth().currentUser
        self.fetchUser()
        
    }
    
    func loginWithEmail(email: String, password: String){
        self.progressing = true
        Auth.auth().signIn(withEmail: email, password: password){
            auth,err in
            if let error = err{
                self.loginFailed = true
                self.progressing = false
                print("DEBUG:\(error.localizedDescription)")
            }
            else{
                guard let user = auth?.user else {return}
                self.progressing = false
                self.userSession = user
                self.reload { success in
                    if !success{
                        print("DEBUG: login failed")
                        
                    }
                    self.childSession = "nil nil"
                }
                
               
            }
        }
    }
    
    func loginWithGoogle(){
        guard let clientID = FirebaseApp.app()?.options.clientID else { return }

        // Create Google Sign In configuration object.
        let config = GIDConfiguration(clientID: clientID)
        self.progressing = true
        
        GIDSignIn.sharedInstance.signIn(with: config, presenting: ApplicationUtil.rootViewController){user, error in
            if let error = error{
                print("DEBUG: LOGIN FAILED WITH GOOGLE, \(error.localizedDescription)")
                return
            }
            
            guard
                let authentication = user?.authentication,
                let idToken = authentication.idToken
              else {
                return
              }
            
            let credential = GoogleAuthProvider.credential(withIDToken: idToken,
                                                             accessToken: authentication.accessToken)
            
            Auth.auth().signIn(with: credential){
                result, error in
                if let error = error{
                    print("DEBUG: \(error.localizedDescription)")
                    return 
                }
                
                guard let user = result?.user else{return}
                print("DEBUG: login with google \(user.uid)")
                
                self.userSession = user
                self.currentUser = nil
                
                self.reload { success in
                    if !success {
                        print("DEBUG: registring for third party google")
                        self.registerForThirdparty(email: user.email ?? "", name: user.displayName ?? "", userId: user.uid)
                        self.fetchUser()
                        self.progressing = false
                    }
                    self.childSession = "nil nil"
                    self.progressing = false
                    
                }
            }
            
        }
    }
    
    func loginWithFacebook() {
        if logged{
            manager.logOut()
            self.email = ""
            logged = false
        }
        else{
            manager.logIn(permissions: ["email"], from: nil){
                (result, error) in
                if error != nil{
                    print(error!.localizedDescription)
                    return
                }
                if !result!.isCancelled{
                    self.logged = true
                    let request = GraphRequest(graphPath: "me", parameters: ["fields":"email"])
                    self.progressing = true
                    
                    request.start{(_, res, _) in
                        //it will return as dictionary
                        guard let profileData = res as? [String : Any] else {return}
                        
                        
                        let credential = FacebookAuthProvider
                            .credential(withAccessToken: AccessToken.current!.tokenString)
                        
                        Auth.auth().signIn(with: credential){
                            result, error in
                            if let error = error{
                                print("DEBUG: \(error.localizedDescription)")
                                return
                            }
                            
                            guard let user = result?.user else{return}
                            print("DEBUG: login with facebook\(user.uid)")
                            
                            self.userSession = user
                            self.currentUser = nil
                            
                            self.reload { success in
                                if !success{
                                    print("DEBUG: registring for third party google")
                                    self.registerForThirdparty(email: user.email ?? "", name: user.displayName ?? "", userId: user.uid)
                                    self.fetchUser()
                                    
                                }
                                self.childSession = "nil nil"
                                self.progressing = false
                            }
                        }
                        
                    }
                }
        
                
            }
            
        }
    }
    
    func authenticateWithApple (credential: ASAuthorizationAppleIDCredential, currentNonce: String){
        guard let token = credential.identityToken else{
            print("error with firebase")
            return
        }
        print("I am tokemn: \n",token)
        guard let tokenString = String(data:token, encoding: .utf8) else{
            print("Error with token")
            return
        }
        
        let firebaseCredential = OAuthProvider.credential(withProviderID: "apple.com", idToken: tokenString, rawNonce: currentNonce)
        self.progressing = true
        
        Auth.auth().signIn(with: firebaseCredential){
            result, error in
            if let error = error{
                print("DEBUG: \(error.localizedDescription)")
                return
            }
            
            guard let user = result?.user else{return}
            print("DEBUG: login with apple \(user.uid)")
            
            self.userSession = user
            self.currentUser = nil
            
            self.reload { success in
                if !success{
                    print("DEBUG: registering for third party apple")
                    let fullname: String = (credential.fullName?.givenName ?? "") + " " + (credential.fullName?.familyName ?? "")
                    let email: String = credential.email ?? ""
                    self.registerForThirdparty(email: email, name: fullname, userId: user.uid)
                    self.fetchUser()
                    
                }
                self.childSession = "nil nil"
                self.progressing = false
                
            }
        }
    }
    
    func register(email: String, password: String, name: String, dateOfBirth: Date?) {
        
        if checkValid(email: email, password: password, name: name, dateOfBirth: dateOfBirth) == false{
            return
        }
       
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "dd/MM/YYYY"
        let birthday = dateFormatter.string(from: dateOfBirth!)
        print("DEBUG: \(birthday)")
        
        Auth.auth().createUser(withEmail: email, password: password){result,err in
            if err != nil{
                if err?.localizedDescription == "The email address is already in use by another account."{
                    self.emailValid = 2
                    self.emailPrompt = "this email has already registered, please try another one."
                }
            }
            else{
                guard let user = result?.user else {return}
                self.userSession = user
                let data = ["email": email, "name": name, "dateOfBirth": birthday, "userId": user.uid]
                Firestore.firestore().collection("users")
                    .document(user.uid)
                    .setData(data)
                self.reload { success in
                    print("DEBUG: \(success)")
                    if !success{
                        print("DEBUG: no user found")
                    }
                }
                
            }
            
        }
    }
    
    func registerForThirdparty(email: String, name: String, userId: String){
        let data = ["email": email, "name": name, "userId": userId]
        Firestore.firestore().collection("users")
            .document(userId)
            .setData(data)
    }
    
    func signOut(){
        self.userSession = nil
        self.currentUser = nil 
        try? Auth.auth().signOut()
        print("DEBUG: user logged out \(userSession == nil)")
    }
    
    func checkValid(email: String, password: String, name: String, dateOfBirth: Date?) -> Bool{
//        check email format
               let emailRegEx = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"
               let emailTest = NSPredicate(format:"SELF MATCHES %@", emailRegEx)
               var valid = true
               
               if !emailTest.evaluate(with: email){
                   self.emailValid = 2
                   self.emailPrompt = "please enter a correct email format."
                   valid = false
               }
               else{
                   self.emailValid = 0
                   self.emailPrompt = ""
               }
               
       //        check password length
               if password.count < 6{
                   passwordValid = 2
                   passwordPrompt = "password cannot must be 6 characters long or more."
                   valid = false
               }
               else{
                   self.passwordValid = 0
                   self.passwordPrompt = ""
               }
               
               if name == "" {
                   nameValid = 2
                   namePrompt = "name can not be empty."
                   valid = false
               }
               else{
                   nameValid = 0
                   namePrompt = ""
               }
               
               if dateOfBirth == nil{
                   dateOfBirthPrompt = "date of birth can not be empty."
                   dateOfBirthValid = 2
                   valid = false
               }
               else{
                   dateOfBirthValid = 0
                   dateOfBirthPrompt = ""
               }
        
        return valid
               
    }
    
    func fetchUser(){
        guard let uid = Auth.auth().currentUser?.uid else { return}
        self.service.fetchUser(uid: uid) { user in
            self.currentUser = user
        }
        
    }
    
    func reload(completion: @escaping (Bool) -> Void){
        guard let uid = Auth.auth().currentUser?.uid else {return}
        
        self.service.fetchUser(uid: uid) { user in
            self.currentUser = user
            
            completion(self.currentUser != nil)
        }
    }
    
    func editParentInfo(newName: String, newDateOfBirth: Date?, newTheme: String,newAvatarPic: UIImage?){
        guard newName != "" else{return}
        
        guard let newDateOfBirth = newDateOfBirth else{return}
        
        if newAvatarPic == nil{
            return
        }
        
        guard let imageData = newAvatarPic!.jpegData(compressionQuality: 0.5) else{
            print("DEBUG: failed to turn image to data")
            return
            
        }
        
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "dd/MM/YYYY"
        let birthday = dateFormatter.string(from: newDateOfBirth)
        
        service.updateUserInfo(currentParent: self.currentUser, newName: newName, newDateOfBirth: birthday, newTheme: newTheme, newAvatarPic: imageData) { parent in
            self.currentUser = parent
        }
    }
}




