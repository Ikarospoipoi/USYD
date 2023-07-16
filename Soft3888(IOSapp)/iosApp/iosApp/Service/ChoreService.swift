//
//  UploadChore.swift
//  iosApp
//
//  Created by Frank Shi on 11/10/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import FirebaseAuth
import FirebaseFirestore
import FirebaseStorage
import shared

struct ChoreService{
    
    func addChore(choreName: String, imageData: Data?, completion: @escaping (Bool) -> Void){
        
        let fileName = UUID().uuidString
        let path = "choreImages/\(fileName).jpg"
        let ref = Storage.storage().reference().child(path)
        
        let date = Date.now
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "dd/MM/YYYY"
        let now = dateFormatter.string(from: date)
        
        ref.putData(imageData!, metadata: nil) { _, error in
            if let error = error{
                print("DEBUG: failed to store chore image in firebase storage \(error.localizedDescription)")
                return
            }
            else{
                guard let parentId = Auth.auth().currentUser?.uid else{return}
                
                let choreDocument = Firestore.firestore().collection("users").document(parentId).collection("chores")
                
                ref.downloadURL { url, error in
                    if error == nil && url != nil {
                        let urlString = url!.absoluteString
                        let data = ["Name": choreName, "PicturePath": urlString, "createdDate": now] as [String : Any]
                        choreDocument.addDocument(data: data as [String : Any]){ error in
                            if let error = error {
                                print("DEBUG: add chorefailed \(error.localizedDescription)")
                                completion(false)
                                return
                            }
                            else{
                                print("DEBUG: added chore")
                                completion(true)
                                return
                            }
                        }
                    }
                    else{
                        return
                    }
                }
            
            }
        }
        
    }
    
    func fetchChores(completion: @escaping ([ChoreTask]) -> Void){
        guard let parentId = Auth.auth().currentUser?.uid else {return}
        
        Firestore.firestore().collection("users").document(parentId).collection("chores").getDocuments { snapshot, error in
            if let error = error{
                print("DEBUG: fetch chores failed")
                return
            }
            
            var result = [ChoreTask]()
            snapshot?.documents.forEach{ doc in
                let choreName = doc["Name"] as? String ?? ""
                let choreImageUrl = doc["PicturePath"] as? String ?? ""
                let achievement = Achievement(points: 0, message: "")
                let createdDate = doc["createdDate"] as? String ?? ""
                
                let chore = ChoreTask(taskID: doc.documentID, name: choreName, description: nil, achievement: achievement, iconImage: choreImageUrl, createdDate: createdDate)
                result.append(chore)
            }
            completion(result)
            
        }
    }
    
    func listenChores(viewModel: ChoreViewModel){
        guard let parentId = Auth.auth().currentUser?.uid else {return}
        
        Firestore.firestore().collection("users").document(parentId).collection("chores").addSnapshotListener { snapshot, error in
            if let error = error{
                print("DEBUG: failed to listen to chores \(error.localizedDescription)")
                return
            }
            
            snapshot?.documentChanges.forEach({ change in
                if change.type == .added{
                    fetchChores { result in
                        viewModel.chores = result
                    }
                }
                
                if change.type == .removed{
                    fetchChores { result in
                        viewModel.chores = result
                    }
                }
            })
        }
    }
}
