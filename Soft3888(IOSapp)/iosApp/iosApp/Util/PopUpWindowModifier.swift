
//
//  PopUpWindowModifier.swift
//  iosApp
//
//  Created by chris on 31/8/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct PopUpWindowModifier : ViewModifier {
    
    @Binding var isPresented : Bool
    var currentChildID: String
    var action: (_ :String)->Void
    
    func body(content: Content) -> some View {
        ZStack{
            content
            if(isPresented){
                AssignChorePopUpWindow(isPresented: $isPresented, currentChildID: currentChildID, action: { item in
                    action(item)
                }).zIndex(5000)
                
            }
        }
    }
    
}


extension View {
    func PopUpWindow(currentChildID: String, isPresented: Binding<Bool>, action: @escaping (_ _input: String)-> Void) -> some View {
        ModifiedContent(content: self, modifier: PopUpWindowModifier(isPresented: isPresented, currentChildID: currentChildID, action: action))
    }
}
