//
//  AddChoreModel.swift
//  iosApp
//
//  Created by Frank Shi on 11/10/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import FirebaseAuth
import FirebaseFirestore
import shared
import UIKit

class ChoreViewModel: ObservableObject{
    @Published var addChoreImageSuccess = false
    @Published var chores = [ChoreTask]()
    @Published var processing = false
    
    var service = ChoreService()
    
    init(){
        service.listenChores(viewModel: self)
        print("DEBUG: in chore view model \(chores)")
    }
    
    func addChore(choreName: String, choreImage: UIImage?){
        if choreImage == nil{
            return
        }
        
        guard let imageData = choreImage!.jpegData(compressionQuality: 0.5) else{
            print("DEBUG: failed to turn image to data")
            return
            
        }
        self.processing = true
        service.addChore(choreName: choreName, imageData: imageData) { success in
            self.addChoreImageSuccess = success
            self.processing = false
        }
    }
    
    func relogin(){
        service.fetchChores { result in
            self.chores = result
        }
        service.listenChores(viewModel: self)
    }

}
