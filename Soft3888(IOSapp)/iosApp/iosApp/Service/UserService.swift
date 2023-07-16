//
//  UserService.swift
//  iosApp
//
//  Created by Edward Zheng on 19/9/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import Firebase
import FirebaseFirestore
import shared
import FirebaseStorage

class UserService: ObservableObject{
    
    func fetchUser(uid: String, completion: @escaping (Parent?) -> Void){
        Firestore.firestore().collection("users")
            .document(uid)
            .getDocument { snapshot, error in
                if let error = error{
                    print("DEBUG: fetch user information failed, \(error.localizedDescription)")
                    return
                }
                guard let snapshot = snapshot else{completion(nil) ;return}
                
                guard let data = snapshot.data() else {completion(nil); return}
                print("DEBUG: in service \(data)")
                
                let userId: String = data["userId"] as! String
                let name: String = data["name"] as! String
                let dateOfBirth: String = data["dateOfBirth"] as? String ?? "no date of birth recorded"
                let email = data["email"] as? String? ?? nil
                let avatarPic = data["avatarPic"] as? String? ?? nil
                let theme = Theme(name: data["theme"] as? String ?? "")
                let currentUser = Parent(userID: userId, name: name, dateOfBirth: dateOfBirth, email: email, chooseTheme: theme, avatarPic: avatarPic)
                
                completion(currentUser)
            }
    }
    
    func updateUserInfo(currentParent: Parent?,newName: String, newDateOfBirth: String, newTheme: String,newAvatarPic: Data, completion: @escaping (Parent) -> Void){
        guard let currentParent = currentParent else {return}
        
        guard let uid = Auth.auth().currentUser?.uid else{
            print("DEBUG: upload user info failed, no user logged in.")
            return}
        
        let email = currentParent.email
        
        
        let fileName = UUID().uuidString
        let path = "parentAvatarPic/\(fileName).jpg"
        let ref = Storage.storage().reference().child(path)
        
        ref.putData(newAvatarPic,metadata: nil) { _, error in
            if let error = error{
                print("DEBUG: failed to store chore image in firebase storage \(error.localizedDescription)")
                return
            }
            else{
                ref.downloadURL { url, error in
                    if error == nil && url != nil {
                        let urlString = url!.absoluteString
                        let data = ["name": newName, "dateOfBirth": newDateOfBirth, "avatarPic": urlString, "email": email, "theme": newTheme]
                        Firestore.firestore().collection("users").document(uid).setData(data, merge: true)
                        self.fetchUser(uid: uid) { parent in
                            guard let parent = parent else{return}
                            completion(parent)
                        }
                        
                    }
                }
            }
        }
    }
}


