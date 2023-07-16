//
//  ContractViewModel.swift
//  iosApp
//
//  Created by Frank Shi on 5/10/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import FirebaseAuth
import FirebaseFirestore
import shared
import SwiftUI

class ContractViewModel: ObservableObject{
    var childsession: String?
    @Published var checkSuccessAdded = false
    @Published var maxpoint = 0
    @Published var totalCheckpoint = 0
    @Published var parentID: String = ""
    @Published var contractResultDic: [String: Array<Int>] = [:]
    
    
    
    
    
    func getContractDetail(parentID: String){
        var result: [Int]=[]
        var childID: String = ""
        print("Debug", parentID)
        let contract = Firestore.firestore().collection("contract")
        contract.whereField("ParentID", isEqualTo: parentID)
            .getDocuments() { (querySnapshot, err) in
                if let err = err {
                    print("Debug: Error getting documents: \(err)")
                    
                } else {
                    for document in querySnapshot!.documents {
                        result = document["PointArray"]as? Array<Int> ?? [0]
                        print("Debug: Result: ", result)
                        childID = document["ChildID"]as? String ?? "Empty"
                        print("Debug: childID: ", childID)
                        self.contractResultDic[childID] = result
                        print("Debug: get contract", self.contractResultDic)
                    }
                }
            }
    }
    
    
    
    func setParentID(parentID: String){
        self.parentID = parentID
        print("Debug: ", parentID)
    }
}
