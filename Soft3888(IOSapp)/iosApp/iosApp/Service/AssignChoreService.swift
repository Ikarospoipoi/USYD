//
//  AssignChoreService.swift
//  iosApp
//
//  Created by chris on 18/10/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import FirebaseAuth
import FirebaseFirestore
import FirebaseStorage
import shared
import Kingfisher

struct AssignChoreService{
    func currentTime() -> String {
            let date = Date()
            let timeFormatter = DateFormatter()
            timeFormatter.dateFormat = "yyyy-MM-dd"
            return timeFormatter.string(from: date)
        }

    
    func AssignChore(currentChildID: String, choreName: String, imageStringData: String , point: Int, completion: @escaping (Bool, [FinishedChore]) -> Void){
        
        //        let fileName = UUID().uuidString
        //        let path = "choreImages/\(fileName).jpg"
        //        let ref = Storage.storage().reference().child(path)
        let uid = Auth.auth().currentUser?.uid
//        let dummyDate = "fake date"
        let date = Date.now
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "dd/MM/YYYY"
        let now = currentTime()
        
        let assignChoreDocument = Firestore.firestore().collection("users").document(uid!).collection("children").document(currentChildID).collection("finishedChore")
        
        let newAssignedChoreData = ["name": choreName, "childID": currentChildID, "choreImg": imageStringData, "point":point, "finishedDate": now] as [String : Any]
        
        Firestore.firestore().collection("users").document(uid!).collection("children").document(currentChildID).collection("finishedChore").addDocument(data: newAssignedChoreData){ error in
            if let error = error {
                print("DEBUG: Assign finished Chore Failed \(error.localizedDescription)")
                self.fetchAssignedChores(currentChildID: currentChildID) { result in
                    completion(false, result)
                }
                
                return
            }
            else{
                print("DEBUG: successfully assign finished chore tp this child")
                self.fetchAssignedChores(currentChildID: currentChildID) { result in
                    completion(true, result)
                }
                return
            }
            
        }
        
        Firestore.firestore().collection("users").document(uid!).collection("children").document(currentChildID).getDocument { snapshot, error in
            if let error = error{
                return
            }
            else{
                guard let data = snapshot?.data() else{return}
                var points = data["points"] as? Int ?? 0
                Firestore.firestore().collection("users").document(uid!).collection("children").document(currentChildID).setData(["points": points+point], merge: true)
            }
        }
        
    }
    
    
    func fetchAssignedChores(currentChildID: String, compeltion: @escaping([FinishedChore]) -> Void) {
        var finishedChoreResult = [FinishedChore]()
        
        let uid = Auth.auth().currentUser?.uid
        
        Firestore.firestore().collection("users").document(uid!).collection("children").document(currentChildID).collection("finishedChore").getDocuments { snapshot, error in
            if let error = error{
                print("DEBUG: failed to fetch finished chores. \(error.localizedDescription)")
                return
            }
            print("DEBUG: fetching finished chores")
            snapshot?.documents.forEach({ doc in
                let choreName = doc["name"] as? String ?? ""
                let childId = doc["childID"] as? String ?? ""
                let point = doc["point"] as? Int ?? 0
                let choreImgString = doc["choreImg"] as? String ?? ""
                let date = doc["finishedDate"] as? String ?? ""
                
                let singleFinishChore = FinishedChore(choreID: doc.documentID , name: choreName, finishedDate: date, point: Int32(point), choreImg: choreImgString)

                finishedChoreResult.append(singleFinishChore)
            })
            compeltion(finishedChoreResult)
            
        }
    }
    
    func fetchAssignedChoresInChildVer(currentParentID: String, currentChildID: String, compeltion: @escaping([FinishedChore]) -> Void){
        var finishedChoreResult = [FinishedChore]()
        
        Firestore.firestore().collection("users").document(currentParentID).collection("children").document(currentChildID).collection("finishedChore").getDocuments { snapshot, error in
            if let error = error{
                print("DEBUG: failed to fetch finished chores. \(error.localizedDescription)")
                return
            }
            print("DEBUG: fetching finished chores")
            snapshot?.documents.forEach({ doc in
                let choreName = doc["name"] as? String ?? ""
                let childId = doc["childID"] as? String ?? ""
                let point = doc["point"] as? Int ?? 0
                let choreImgString = doc["choreImg"] as? String ?? ""
                let date = doc["finishedDate"] as? String ?? ""
                
                let singleFinishChore = FinishedChore(choreID: doc.documentID , name: choreName, finishedDate: date, point: Int32(point), choreImg: choreImgString)

                finishedChoreResult.append(singleFinishChore)
            })
            compeltion(finishedChoreResult)
            
        }
        
        
    }
    
    
    
}
