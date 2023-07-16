//
//  ChildrenDashboardService.swift
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

struct ChildrenDashBoardService{
    
    
    func fetchChildren(currentUserID : String, compeltion: @escaping([Child]) -> Void) {
        var result = [Child]()
        //var childID : String = String(currentUserID.split(separator: " ")[0])
        let parentID : String = String(currentUserID.split(separator: " ")[1])
        print("Current children parent id is " + parentID)

        Firestore.firestore().collection("users").document(parentID).collection("children").getDocuments { snapshot, error in
            if let error = error{
                print("DEBUG: failed to fetch children. \(error.localizedDescription)")
                return
            }
            print("DEBUG: fetching children")
            snapshot?.documents.forEach({ doc in
                let parentId = doc["parentId"] as? String ?? ""
                let name = doc["name"] as? String ?? ""
                let dateOfBirth = doc["dateOfBirth"] as? String ?? ""
                let theme = Theme(name: doc["theme"] as? String ?? "")
                let avatarPic = doc["avatarPic"] as? String? ?? nil
                
                let child = Child(userID: doc.documentID, name: name, dateOfBirth: dateOfBirth, chooseTheme: theme, avatarPic: avatarPic, points: 0)
                result.append(child)
                print("Number of children in this parent is " + String(result.count))
                
                
            })
            compeltion(result)
                    }
        
    }
    
    func listenChildren(viewModel: ChildAuthViewModel, currentUserID : String){
        //var childID : String = String(currentUserID.split(separator: " ")[0])
        if currentUserID == "nil nil"{
            return
        }
        let parentID : String = String(currentUserID.split(separator: " ")[1])
        print("Current children parent id is " + parentID)
        
        Firestore.firestore().collection("users").document(parentID).collection("children").addSnapshotListener { snapshot, error in
            guard let snapshot = snapshot else {return}
            if let error = error {
                print("DEBUG: failed to fetch children. \(error.localizedDescription)")
            }
            snapshot.documentChanges.forEach { changes in
                if changes.type == .added {
                    print("DEBUG: detected child added")
                    viewModel.fetchChildren()
                }
                if changes.type == .removed {
                    viewModel.fetchChildren()
                }
                if changes.type == .modified{
                    print("DEBUG: hey")
                    viewModel.fetchChildren()
                }
            }
        }
    }
    
}
