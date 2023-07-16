//
//  AddChildViewModelForChild.swift
//  iosApp
//
//  Created by chris on 7/10/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import FirebaseAuth
import FirebaseFirestore
import shared
import SwiftUI

class ChildAuthViewModel: ObservableObject{
    @Published var children = [Child]()
    @Published var success = false
    @Published var currentChild: Child = Child(userID: "", name: "", dateOfBirth: "", chooseTheme: Theme(name: ""), avatarPic: nil, points: 0)
    
//    @Published var childSession : String
    
    @AppStorage("childSession") var childSession = "nil nil"
    
    let service = ChildrenDashBoardService()
    
    init() {
//        self.childSession = "nil nil"
        print("DEBUG: dashboard view model initializing")
        print("DEBUG: \(childSession)")
        self.fetchChildren()
        self.fetchChild()
        //add listener to children collection
        self.service.listenChildren(viewModel: self, currentUserID: childSession)
        
    }
    
    func fetchChildren(){
//        guard let parentID : String = String(childSession.split(separator: " ")[0]) else{return}
//        print(parentID)
        if childSession == "nil nil"{
            return
        }
        service.fetchChildren(currentUserID: childSession) { result in
            self.children = result
            print("now there is " + String(self.children.count) + " in this user")
            self.fetchChild()
        }
    }
    
    func fetchChild(){
        children.forEach { child in
            if child.userID == String(childSession.split(separator: " ")[0]){
                self.currentChild = child
                return
            }
        }
    }
    
    func signIn(childSession: String){
        self.childSession = childSession
        self.fetchChildren()
//        self.fetchChild()
        UserDefaults.standard.set(childSession, forKey: "childSession")
        
    }
    
    func signOut(){
        self.childSession = "nil nil"
        print("DEBUG: \(childSession)")
        UserDefaults.standard.set("nil nil", forKey: "childSession")
    }
}
