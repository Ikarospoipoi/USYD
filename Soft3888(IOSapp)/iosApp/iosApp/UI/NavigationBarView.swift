//
//  NavigationBarView.swift
//  iosApp
//
//  Created by chris on 4/9/2022.
//  Copyright © 2022 orgName. All rights reserved.
//
import SwiftUI
import shared
import AuthenticationServices


struct NavigationBarView: View {

    @State private var selection: Tab = .exclusion

        enum Tab {
            case dashboard
            case currentUser
            case childProfile
            case exclusion

        }
    
    
    var body: some View {
            NavigationView { //整体设置，下级页面不会在出现底部tabbar
                TabView(selection: $selection) {
                        DashBoardPage()
                             .tabItem{//使用label 创建tabitem图文
                                 Label("Family", systemImage: "house")
                             }
                             .tag(Tab.dashboard)

                        ParentProfilePage()
                            .tabItem{
                                Label("parent", systemImage: "person")
                            }
                            .tag(Tab.currentUser)

                }
                .accentColor(.blue) //设置文字默认选中颜色
            }.navigationBarHidden(true)
        
            
    }
}
