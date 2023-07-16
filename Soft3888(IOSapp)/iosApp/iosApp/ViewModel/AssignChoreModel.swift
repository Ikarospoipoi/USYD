//
//  AssignChoreModel.swift
//  iosApp
//
//  Created by chris on 18/10/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import FirebaseAuth
import FirebaseFirestore
import shared


class AssignChoreModel: ObservableObject {
    
    
    @Published var finishedChoreList = [FinishedChore]()
    @Published var userfinishedChoreList = [FinishedChore]()
    @Published var success = false
    @Published var points = [String: Int]()
    let service = AssignChoreService()
    
    
    init() {
        print("DEBUG: assign chore  model initializing")
    }
    
    
    func assigneFinishdChore(currentChildID: String, choreName: String, StringImageData: String, selectPoint: Int){
        
        service.AssignChore(currentChildID: currentChildID, choreName: choreName, imageStringData: StringImageData, point: selectPoint){ success, result in
            self.success = success
            if success {
                self.finishedChoreList = result
            }
            print("DEBUG: \(self.finishedChoreList)")
        }
    }
    
    func fetchFinishedChores(currentChildID: String){
        service.fetchAssignedChores(currentChildID: currentChildID) { result in
            self.finishedChoreList = result
        }
    }
    
    func fetFinishedChoresInChildVer(currentChildID: String, currentparentID: String){
        service.fetchAssignedChoresInChildVer(currentParentID: currentparentID, currentChildID: currentChildID){ result in
            self.finishedChoreList = result
        }
    }
    
    func fetchUserFinishedChoresInChildVer(currentChildID: String, currentparentID: String){
        service.fetchAssignedChoresInChildVer(currentParentID: currentparentID, currentChildID: currentChildID){ result in
            self.userfinishedChoreList = result
        }
    }
    
    func signOut(){
        self.userfinishedChoreList = [FinishedChore]()
        self.finishedChoreList = [FinishedChore]()
        print("DEBUG: clean all the assign chores")
    }
    
    
    
}


