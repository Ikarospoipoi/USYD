//
//  DashBoardViewModel.swift
//  iosApp
//
//  Created by Edward Zheng on 29/9/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import FirebaseAuth
import FirebaseFirestore
import shared

class AddChildViewModel: ObservableObject{
    @Published var children = [Child]()
   
    @Published var parents = [Parent]()
    @Published var success = false
    @Published var processing = false
    
    @Published var nameValid: Float = 0
    @Published var namePrompt = ""
    @Published var dateValid: Float = 0
    let service = DashBoardService()
    
    init() {
        print("DEBUG: dashboard view model initializing")
        self.fetchChildren()
        self.service.listenChildren(viewModel: self)
    }
    
    func addChild(name: String, dateOfBirth: Date?, theme: String) {
        
        if !checkValid(name: name, date: dateOfBirth, theme: theme){
            return
        }
        self.processing = true
        service.addChild(name: name, dateOfBirth: dateOfBirth, theme: theme) { success, result in
            self.success = success
            if success {
                self.children = result
            }
            print("DEBUG: \(self.children)")
            self.processing = false
        }
        
    }
    
    func fetchChildren(){
        service.fetchChildren() { result in
            self.children = result
        }
    }
    
    func checkValid(name: String?, date: Date?, theme: String?) -> Bool{
        guard name != "" else{nameValid = 2; namePrompt = "name cannot be empty"; return false}
        guard date != nil else {dateValid = 2; return false}
        guard theme != nil else{return false}
        return true
    }
    
    func relogin(){
        
        self.fetchChildren()
        self.service.listenChildren(viewModel: self)
    }
}
