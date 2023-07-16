import SwiftUI
import shared
import Firebase
import GoogleSignIn
import FBSDKCoreKit
import CoreImage

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(_ application: UIApplication,
                     didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        FirebaseApp.configure()
        
        ApplicationDelegate.shared.application(
            application,
            didFinishLaunchingWithOptions: launchOptions
        )
        
        return true
    }
    
    func application(_ application: UIApplication, open url: URL,
                     options: [UIApplication.OpenURLOptionsKey: Any] = [:])
    -> Bool {
        ApplicationDelegate.shared.application(
            application,
            open: url,
            sourceApplication: options[UIApplication.OpenURLOptionsKey.sourceApplication] as? String,
            annotation: options[UIApplication.OpenURLOptionsKey.annotation]
        )
        //return GIDSignIn.sharedInstance.handle(url)
    }
}

@main
struct iOSApp: App {
    @StateObject var authViewModel = AuthViewModel()
    @StateObject var childAuthViewModel = ChildAuthViewModel()
    @StateObject var choreViewModel = ChoreViewModel()
    @StateObject var userInfoEditModel = editUserInfoModel()
    @StateObject var contractViewModel = ContractViewModel()
    @StateObject var assignFinishChoresModel = AssignChoreModel()
    @StateObject var addChildViewModel = AddChildViewModel()

    // register app delegate for Firebase setup
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
    
    var body: some Scene {
        WindowGroup {
            
            ContentView()
                .environmentObject(authViewModel)
                .environmentObject(childAuthViewModel)
                .environmentObject(choreViewModel)
                .environmentObject(userInfoEditModel)
                .environmentObject(contractViewModel)
                .environmentObject(assignFinishChoresModel)
                .environmentObject(addChildViewModel)

            
            
        }
    }
    
}
