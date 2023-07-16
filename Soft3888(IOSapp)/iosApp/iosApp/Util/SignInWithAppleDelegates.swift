
import UIKit
import AuthenticationServices
import CryptoKit
import Firebase

import shared

class SignInWithAppleDelegates: NSObject {
    private let signInSucceeded: (Result<String, Error>) -> ()
    private weak var window: UIWindow!
    var currentNonce = ""
    var authViewModel: AuthViewModel
    var choreViewModel: ChoreViewModel
    var addChildViewModel: AddChildViewModel
    
    init(window: UIWindow?, _ currentNonce: String, authViewModel: AuthViewModel,choreViewModel: ChoreViewModel, addChildViewModel: AddChildViewModel,onSignedIn: @escaping (Result<String, Error>) -> ()) {
        self.window = window
        self.signInSucceeded = onSignedIn
        self.currentNonce = currentNonce
        self.authViewModel = authViewModel
        self.choreViewModel = choreViewModel
        self.addChildViewModel = addChildViewModel
    }
}

extension SignInWithAppleDelegates: ASAuthorizationControllerDelegate {
    private func registerNewAccount(credential: ASAuthorizationAppleIDCredential) {
        print("Registering new account with user: \(credential.user)")
        self.signInSucceeded(.success(credential.user))
    }
    
    private func signInWithExistingAccount(credential: ASAuthorizationAppleIDCredential) {
        print("Signing in with existing account with user: \(credential.user)")
        self.signInSucceeded(.success(credential.user))
    }
    
    private func signInWithUserAndPassword(credential: ASPasswordCredential) {
        print("Signing in using an existing iCloud Keychain credential with user: \(credential.user)")
        self.signInSucceeded(.success(credential.user))
    }
    
    func authorizationController(controller: ASAuthorizationController, didCompleteWithAuthorization authorization: ASAuthorization) {
        
        if let credential = authorization.credential as? ASAuthorizationAppleIDCredential{
                
            authViewModel.authenticateWithApple(credential: credential, currentNonce: currentNonce) 
        }
    }
    
    func authorizationController(controller: ASAuthorizationController, didCompleteWithError error: Error) {
        self.signInSucceeded(.failure(error))
    }
}

extension SignInWithAppleDelegates: ASAuthorizationControllerPresentationContextProviding {
    func presentationAnchor(for controller: ASAuthorizationController) -> ASPresentationAnchor {
        return self.window
    }
}

class StoreNonce{
    @Published var currentNonce = ""
    
    func SetCurrentNonce(currentNonce: String){
        self.currentNonce = currentNonce
    }

    func GetCurrentNonce() -> String{
        return currentNonce
    }
}
