//
//  DashBoardService.swift
//  iosApp
//
//  Created by Edward Zheng on 3/10/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import FirebaseAuth
import FirebaseFirestore
import shared
import SwiftUI

struct DashBoardService{
    
    func addChild(name: String, dateOfBirth: Date?, theme: String, completion: @escaping (Bool, [Child]) -> Void){
        let uid = Auth.auth().currentUser?.uid
        
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "dd/MM/YYYY"
        let birthday = dateFormatter.string(from: dateOfBirth!)
        
        let data = ["name": name, "dateOfBirth": birthday, "theme": theme, "parentId": uid, "points": 0] as [String : Any]
        
        
        Firestore.firestore().collection("users").document(uid!).collection("children").addDocument(data: data as [String : Any]){ error in
            if let error = error {
                print("DEBUG: add child failed \(error.localizedDescription)")
                self.fetchChildren { result in
                    completion(false, result)
                }

                return
            }
            else{
                print("DEBUG: added child")
                self.fetchChildren { result in
                    completion(true, result)
                }
                return
            }
        }
        
        
        
    }
    
    func fetchChildren(compeltion: @escaping([Child]) -> Void) {
        var result = [Child]()
        
        guard let uid = Auth.auth().currentUser?.uid else{return}
        
        Firestore.firestore().collection("users").document(uid).collection("children").getDocuments { snapshot, error in
            if let error = error{
                print("DEBUG: failed to fetch children. \(error.localizedDescription)")
                return
            }
            print("DEBUG: fetching children")
            snapshot?.documents.forEach({ doc in
                let name = doc["name"] as? String ?? ""
                let dateOfBirth = doc["dateOfBirth"] as? String ?? ""
                let theme = Theme(name: doc["theme"] as? String ?? "")
                let avatarPic = doc["avatarPic"] as? String? ?? nil
                let points = doc["points"] as? Int32 ?? 0
                
                let child = Child(userID: doc.documentID, name: name, dateOfBirth: dateOfBirth, chooseTheme: theme, avatarPic: avatarPic, points: points)


                result.append(child)
            })
            compeltion(result)
        }
        
        
        
    }
    
    func listenChildren(viewModel: AddChildViewModel){
        guard let uid = Auth.auth().currentUser?.uid else {return}
        
        Firestore.firestore().collection("users").document(uid).collection("children").addSnapshotListener { snapshot, error in
            guard let snapshot = snapshot else {return}
            if let error = error {
                print("DEBUG: failed to fetch children. \(error.localizedDescription)")
            }
            
            snapshot.documentChanges.forEach { changes in
                if changes.type == .added {
                    viewModel.fetchChildren()
                }
                if changes.type == .removed {
                    viewModel.fetchChildren()
                }
                if changes.type == .modified{
                    viewModel.fetchChildren()
                }
            }
        }
    }
    
    func fetchContract(parentId: String, childId: String, completion: @escaping (Contract) -> Contract) -> Contract?{
        var result: Contract?
        
        Firestore.firestore().collection("users").document(parentId).collection("children").document(childId).collection("contract").getDocuments { snapshot, error in
            if let error = error{
                print("DEBUG: failed to fetch children. \(error.localizedDescription)")
                return
            }
            print("DEBUG: fetching contract")
            
            snapshot?.documents.forEach({ doc in
                let parentId = doc["ParentID"] as? String ?? ""
                let childID = doc["ChildID"] as? String ?? ""
                let description = doc["Description"] as? String ?? ""
                let pointArray = doc["PointArray"] as? Array<String> ?? []
                let rewardArray = doc["RewardArray"] as? Array<String> ?? []
                let maxPoint = doc["maxPoint"] as? Int32 ?? 0
                let childName = doc["ChildName"] as? String ?? ""
                result = Contract(parentId: parentId, childName: childName, childId: childID, maxPoint: maxPoint, rewardList: rewardArray, pointList: pointArray, detail: description)
                
                completion(result!)
            })
     
        }
        return result
    }
    
}
