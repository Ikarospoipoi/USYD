//
//  EditChildInfoService.swift
//  iosApp
//
//  Created by chris on 15/10/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import Firebase
import FirebaseStorage
import shared
import SwiftUI


struct EditChildInfoService{
    
    private var ref: DatabaseReference = Database.database().reference()
    @AppStorage("childSession") var childSession = ""
    
    func updateChildInfo(parentID: String, childID: String, childName: String, dateOfBirth: Date?, theme: String, imageData: Data?, completion: @escaping (Bool, [Child]) -> Void){
        
        let fileName = UUID().uuidString
        let path = "childAvatarImages/\(fileName).jpg"
        let ref = Storage.storage().reference().child(path)
        
        let uid: String?
        if Auth.auth().currentUser != nil{
            uid = Auth.auth().currentUser?.uid
        }
        else{
            uid = String(childSession.split(separator: " ")[1])
        }
        
        var newChild = Child(userID: "", name: "", dateOfBirth: "", chooseTheme: Theme(name: ""), avatarPic: "", points: 0)
        
        
        ref.putData(imageData!, metadata: nil) { _, error in
            if let error = error{
                print("DEBUG: failed to store child avatar image in firebase storage \(error.localizedDescription)")
                return
            }
            else{
                guard uid != nil else{return}
                
                Firestore.firestore().collection("users").document(uid!).collection("children").document(childID).getDocument { snapshot, error in
                    if let error = error{
                        print("DEBUG: failed to fetch children. \(error.localizedDescription)")
                        self.fetchChildren { result in
                            completion(false, result)
                        }
                        return
                    }
                    else{
                        ref.downloadURL { url, error in
                            if error == nil && url != nil {
                                let urlString = url!.absoluteString
                                let dateFormatter = DateFormatter()
                                dateFormatter.dateFormat = "dd/MM/YYYY"
                                let birthday = dateFormatter.string(from: dateOfBirth!)
                                
                                let newData = ["name": childName, "dateOfBirth": birthday, "theme": theme, "parentId": uid, "avatarPic": urlString]
                                let db = Firestore.firestore()
                                do{
                                    print("DEBUG: start update")
                                    print(uid)
                                    print(childID)
                                    print(newData)
                                    try db.collection("users").document(uid!).collection("children").document(childID).setData(newData, merge: true)
                                    
                                    
                                }catch{
                                    print(error)
                                }
                                newChild = Child(userID: childID, name: childName, dateOfBirth: birthday, chooseTheme: Theme(name: theme), avatarPic: "aaa", points: 0)
                                
                            }
                            else{
                                return
                            }
                        }
                        
                        
                        
                    }
                }
                
            }
        }
    }
                            
    func fetchChildren(compeltion: @escaping([Child]) -> Void) {
                var result = [Child]()
                                 
                let uid = Auth.auth().currentUser?.uid
                                 
                Firestore.firestore().collection("users").document(uid!).collection("children").getDocuments { snapshot, error in
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
                                         
                    let child = Child(userID: doc.documentID, name: name, dateOfBirth: dateOfBirth, chooseTheme: theme, avatarPic: "", points: 0)
                result.append(child)
                                         
                                         
                })
                compeltion(result)
            }
                                 
                                 
    }
//        }
//
//    }
    
//    func listenChildren(viewModel: editUserInfoModel, currentUserParentID : String){
//        //var childID : String = String(currentUserID.split(separator: " ")[0])
//        let parentID : String = currentUserParentID
//        print("Current children parent id is " + parentID)
//        
//        Firestore.firestore().collection("users").document(parentID).collection("children").addSnapshotListener { snapshot, error in
//            guard let snapshot = snapshot else {return}
//            if let error = error {
//                print("DEBUG: failed to fetch children. \(error.localizedDescription)")
//            }
//            snapshot.documentChanges.forEach { changes in
//                if changes.type == .added {
//                    print("DEBUG: detected child added")
//                    viewModel.fetchChildren()
//                }
//                if changes.type == .removed {
//                    viewModel.fetchChildren()
//                }
//            }
//        }
//    }
    
}

