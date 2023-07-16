//
//  UploadContract.swift
//  iosApp
//
//  Created by Frank Shi on 3/10/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import FirebaseAuth
import FirebaseFirestore
import shared

struct UploafContract{
    
    func removeContract(childID: String, completion: @escaping (Bool) -> Void){
        let contract = Firestore.firestore().collection("contract")
        
        contract.whereField("ChildID", isEqualTo: childID)
            .getDocuments() { (querySnapshot, err) in
                if let err = err {
                    print("Debug: Error getting documents: \(err)")
                } else {
                    for document in querySnapshot!.documents {
                        document.reference.delete()
                    }
                    completion(true)
                }
            }
        
    }
    
    func addContract(childName: String, childID: String, maxPoint: Int, rewardArray: Array<String>, pointArray: Array<Int>, decription: String?, parentID: String, parentName: String){
        
        let data = ["ChildID": childID, "ChildName": childName, "Description": decription, "MaxPoint": maxPoint,  "PointArray": pointArray, "RewardArray": rewardArray, "ParentID": parentID] as [String : Any]
        
        let contract = Firestore.firestore().collection("contract")

        contract.addDocument(data: data as [String : Any]){ error in
            if let error = error {
                print("DEBUG: add contract failed \(error.localizedDescription)")
                return
            }
            else{
                print("DEBUG: added contract")
                return
            }
        }
    }
}
