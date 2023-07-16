//
//  AddChorePage.swift
//  iosApp
//
//  Created by Frank Shi on 4/9/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import Foundation
import SwiftUI
import shared
import UIKit

//struct Preview_AddChorePage: PreviewProvider {
//
//    static var previews: some View {
//        AddChorePage(choreName: "")
//    }
//}


struct AddChorePage: View {
    @State var choreName: String = ""
    @State var showingLocalImage: Bool = false
    @State var choreImage: UIImage?
    @EnvironmentObject var addChoreViewModel: ChoreViewModel
    @Environment(\.presentationMode) var presentationMode
    var uploadchore = ChoreService()
    var body: some View {
        
        GeometryReader{ geo in
            Image("Background").resizable()
                .ignoresSafeArea()
                .opacity(0.2)
                
            
            ScrollView {
                VStack{
                    HStack{
                        Image("AddChoreIcon")
                            .frame(width: UIScreen.main.bounds.width*0.3,alignment: .leading)
                        
                        Text("Add chore")
                            .fontWeight(.heavy)
                            .frame(width: UIScreen.main.bounds.width*0.6,alignment: .leading)
                        
                    }
                    
                    VStack{
                        HStack{
                            Image("laundry-machineIcon")
                            Text("Name: ")
                                .fontWeight(.thin)
                        }.frame(width: UIScreen.main.bounds.width*0.9,alignment: .leading)
                        TextField("name", text: $choreName)
                            .padding()
                            .frame(width: UIScreen.main.bounds.width*0.9,
                                   height:UIScreen.main.bounds.height*0.05,alignment: .leading)
                            .border(Color.gray, width: 2)
                            .cornerRadius(6)
                        
                        
                    }.frame(height: UIScreen.main.bounds.height*0.2)
                    VStack{
                        HStack{
                            Image("ChorseIconSelectionIcon")
                            Text("Add chore")
                                .fontWeight(.thin)
                            
                        }.frame(width: UIScreen.main.bounds.width*0.9,alignment: .leading)
                        
                        Button(action: {
                            //                var createReward = RewardCreater()
                            //                rewardList.append(createReward)
                            showingLocalImage.toggle()
                            
                            
                        }, label: {
                            VStack{
                                if let image = self.choreImage{
                                    Image(uiImage: choreImage!)
                                        .resizable()
                                        .frame(width: 200, height: 200)
                                        .scaledToFill()
                                        .cornerRadius(10)
                                }else{
                                    Image("Rectangle 44")
                                        .resizable()
                                        .frame(width: 200, height: 200)
                                        .scaledToFill()
                                        
                                }
                            }
                        }).frame(width: 200, height: 200, alignment: .center)
                        
                        
                            
                    }
                    
                    Button(action: {
                        addChoreViewModel.addChore(choreName: choreName, choreImage: choreImage)
                        
                    }, label: {
                        Text("ADD CHORE")
                            .fontWeight(.bold)
                            .frame(width: UIScreen.main.bounds.width*0.8, height:50)
                            .background(Color.blue.opacity(0.7))
                            .foregroundColor(Color.white)
                            .cornerRadius(8)
                    })
                }.frame(width: UIScreen.main.bounds.width)
            }.frame(width: UIScreen.main.bounds.width, alignment: .center)
            
        }
        .overlay{
            ProgressSpinner()
        }
        .fullScreenCover(isPresented: $showingLocalImage, content: {
            ImagePicker(image: $choreImage)
        })
        .onReceive(addChoreViewModel.$addChoreImageSuccess) { success in
            if success{
                presentationMode.wrappedValue.dismiss()
            }
        }
        
        
        
    }
}


