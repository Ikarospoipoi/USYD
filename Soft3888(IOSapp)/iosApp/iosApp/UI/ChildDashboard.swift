//
//  ChildDashboard.swift
//  iosApp
//
//  Created by chris on 5/9/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared
import Kingfisher


struct ChildDashBoardPage: View {
    //let username: String
    //var children: [Child]
    //var parents: [Parent]
    
    @State var goToChildProfilePage = false
    
    @State var currentSelectChild: Child = Child(userID: "-1", name: "", dateOfBirth: "", chooseTheme: Theme(name: ""), avatarPic: "", points: 0)
    @EnvironmentObject var childAuthViewModel: ChildAuthViewModel
    @EnvironmentObject var contractViewModel: ContractViewModel

    
    
    var body: some View {
        
        
        NavigationView {
            ZStack{
                Image("Background").resizable().edgesIgnoringSafeArea(.all)
                    .opacity(0.2)
                
                ScrollView{
                    VStack{
                        HStack{
                            HStack{
                                if self.childAuthViewModel.currentChild.avatarPic != nil{
                                    KFImage(URL(string: self.childAuthViewModel.currentChild.avatarPic!))
                                        .resizable()
                                        .frame(width: 80, height: 80)
                                        .padding(.horizontal, 7)
                                        .clipShape(Circle())
                                    
                                }
                                else{
                                    UserPhoto()
                                }
                                Message_And_Name(username: self.childAuthViewModel.currentChild.name)
                                
                            }
                            .frame(width: UIScreen.main.bounds.width*0.85, alignment: .leading)
                            .padding(.top, 3)
                            
                        }
                        
                        VStack{
                            
                            //Spacer(minLength: 50)
                            Title_and_home_Page().frame(width: UIScreen.main.bounds.width*0.95,alignment: .leading)
                            LazyVStack{
                                ForEach(self.childAuthViewModel.children, id: \.self){child in
                                    
                                    childCardforChild(child: child, currentContract: contractViewModel)
                                    
                                    
                                }
                            }
                        }
                        
                    }
                }
                
            }.navigationBarHidden(true)
        }.navigationBarBackButtonHidden(true)
            .navigationBarHidden(true)
            .environmentObject(contractViewModel)
            .onAppear {
                contractViewModel.setParentID(parentID: String(childAuthViewModel.childSession.split(separator: " ")[1]))
                contractViewModel.getContractDetail(parentID: String(childAuthViewModel.childSession.split(separator: " ")[1]))
            }

    }
    
    
    struct childCardforChild: View{
        var child: Child
        var currentContract: ContractViewModel
        var contractResultDic: [String: Array<Int>] = [:]
        var result: [Int] = []
        @State var goToChildProfilePage = false
        
        
        @EnvironmentObject var authViewModel: AuthViewModel
        @EnvironmentObject var assignFinishChoresModel : AssignChoreModel
        @EnvironmentObject var childAuthViewModel: ChildAuthViewModel
        
        
        init(child: Child, currentContract: ContractViewModel){
            self.child = child
            self.currentContract = currentContract
            self.contractResultDic = currentContract.contractResultDic
            self.result = contractResultDic[child.userID] ?? [0]
        }
        
        
        var body: some View{
            HStack{
                Button(action: {
                    print("current select child ID: " + child.userID)
                    self.assignFinishChoresModel.fetFinishedChoresInChildVer(currentChildID: child.userID, currentparentID: String(self.childAuthViewModel.childSession.split(separator: " ")[1]))
                    goToChildProfilePage = true
                    
                }, label: {
                    Button_Label(currentChild: child ,contractResultDic: contractResultDic).foregroundColor(Color("AdaptiveColorForText"))
                }).frame(width: UIScreen.main.bounds.width*0.95, height: UIScreen.main.bounds.width*0.3)
                    .background(Color("AdaptiveColorForBackground"))
                    .cornerRadius(25)
                    .shadow(color: Color.gray, radius: 10)
                
                
                NavigationLink(destination: ChildAccountPage(selectedChild: child, result: removeZero(pointArray: result)), isActive: $goToChildProfilePage){
                    EmptyView()
                }
                
            }
        }
        
    }
}
