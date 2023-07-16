//
//  editUserInfoModel.swift
//  iosApp
//
//  Created by chris on 15/10/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import FirebaseAuth
import FirebaseFirestore
import shared
import UIKit


class editUserInfoModel: ObservableObject {
    
    
    let service = EditChildInfoService()
    @Published var currentChild : Child = Child(userID: "", name: "", dateOfBirth: "", chooseTheme: Theme(name: ""), avatarPic: "", points: 0)
    @Published var success = false
    @Published var children  = [Child]()
    
    @Published var finishedObtainNewestChildInfo : Bool = false
    @Published var isEditSheetPresent = false
    @Published var processing = false
    
    let thread1 = DispatchQueue(label: "queue1 for fetching update child info")
    let thread2 = DispatchQueue(label: "queue3 for telling update child info complete")
    
    
    func editChildInfo(parentID: String, childID: String, childName: String, dateOfBirth: Date?, theme: String, imageData: UIImage?){
        
        guard let childImageData = imageData!.jpegData(compressionQuality: 0.5) else{
            print("DEBUG: failed to turn image to data")
            return
        }
        self.processing = true
        service.updateChildInfo(parentID: parentID, childID: childID, childName: childName, dateOfBirth: dateOfBirth, theme: theme, imageData: childImageData){ success, result in
            self.success = success
            self.processing = false
            if success {
                print("DEBUG: successfully update")
                return
            }else{
                print("DEBUG: error when edit child information")
                return 
            }
        }
    }
    
    func fetchChildren(){
        service.fetchChildren() { result in
            self.children = result
        }
    }
    
    func getNewestChildInfo(childID: String){
        thread1.async {
            for child in self.children{
                if child.userID == childID{
                    self.currentChild = Child(userID: child.userID, name: child.name, dateOfBirth: child.dateOfBirth, chooseTheme: child.chooseTheme, avatarPic: "", points: 0)
                }
            }
        }

        DispatchQueue.main.asyncAfter(deadline: .now()+1){
            self.finishedObtainNewestChildInfo = true
        }
    }
    
    
}

