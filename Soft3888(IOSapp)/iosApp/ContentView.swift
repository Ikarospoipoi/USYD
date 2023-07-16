//
//  Testing.swift
//  iosApp
//
//  Created by Edward Zheng on 31/8/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared
import AuthenticationServices


struct ContentView: View{
    @EnvironmentObject var authViewModel: AuthViewModel
    @EnvironmentObject var childAuthViewModel: ChildAuthViewModel
    @AppStorage("childSession") var childSession = " "
    
    var body: some View{
        if authViewModel.userSession == nil{
            if self.childSession != "nil nil"{
                ChildNavigationBarView()
            }else{
                ParentLoginPage()
                    .overlay(
                        ProgressSpinner()
                    )
            }
        }
        else{
            NavigationBarView()
        }
    }
}
