//
//  DashBoardPage.swift
//  iosApp
//
//  Created by Frank Shi on 30/8/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared
import Kingfisher




struct DashBoardPage: View {
    @State var goToChildProfilePage = false
    @State var goToParentProfilePage = false
    @EnvironmentObject var contractViewModel:ContractViewModel
    @EnvironmentObject var authViewModel: AuthViewModel
    @EnvironmentObject var choreViewModel: ChoreViewModel
    @EnvironmentObject var addChildViewModel: AddChildViewModel
    @State var currentSelectChild: Child = Child(userID: "1", name: "", dateOfBirth: "", chooseTheme: Theme(name: ""), avatarPic: "", points: 0)
    @State var currentParent: Parent = Parent(userID: "", name: "", dateOfBirth: "", email: nil, chooseTheme: nil, avatarPic: nil)
    

    
    var body: some View {
        
        NavigationView {
            ZStack{
                Image("Background").resizable().edgesIgnoringSafeArea(.all)
                    .opacity(0.2)
                
                ScrollView{
                    VStack{
                      
                        HStack{
                            
                            HStack{
                                if self.authViewModel.currentUser?.avatarPic != nil{
                                    KFImage(URL(string: self.authViewModel.currentUser!.avatarPic!))
                                        .resizable()
                                        .frame(width: 80, height: 80, alignment: .center)
                                        .clipShape(Circle())
                                        .aspectRatio(contentMode: .fit)
                                }
                                else{
                                    UserPhoto()
                                }
                                Message_And_Name(username: authViewModel.currentUser?.name ?? "")
                                
                            }.frame(width: UIScreen.main.bounds.width*0.85, alignment: .leading)
                                .padding(.top, 3)
                            
                            Plus_button_in_DashBoard(currentParent: authViewModel.currentUser ?? currentParent)
                        }
                        
                        VStack{
                            
                            //Spacer(minLength: 50)
                            Title_and_home_Page().frame(width: UIScreen.main.bounds.width*0.95,alignment: .leading)
                            
                            LazyVStack{
                                ForEach(self.addChildViewModel.parents, id: \.self){parent in
                                    HStack{
                                        Button(action: {
                                            goToParentProfilePage = true
                                        
                                            //currentSelectParent = parent
                                         
                                        }, label: {
                                            Parent_Button_Label(currentParent: parent)
                                                .foregroundColor(Color("AdaptiveColorForText"))
                                        }).frame(width: UIScreen.main.bounds.width*0.95, height: UIScreen.main.bounds.width*0.3)
                                            .background(Color("AdaptiveColorForBackground"))
                                            .cornerRadius(25)
                                            .shadow(color: Color.gray, radius: 10)
                                        
                                    }
//                                    NavigationLink(destination: ParentProfilePage(chores: []), isActive: $goToParentProfilePage){
//                                        EmptyView()
//                                    }
                                    
                                }
                                ForEach(self.addChildViewModel.children, id: \.self){child in
                                    childCard(child: child, currentContract: contractViewModel)
                                }
                            }
                        }
                    }
                }
               
            }.navigationBarHidden(true)

        }
            .navigationBarBackButtonHidden(true)
            .navigationBarHidden(true)
            .environmentObject(addChildViewModel)
            .onAppear {
                contractViewModel.setParentID(parentID: authViewModel.userSession?.uid ?? "Empty")
                contractViewModel.getContractDetail(parentID: authViewModel.userSession?.uid ?? "Empty")
                choreViewModel.relogin()
                addChildViewModel.relogin()
                
            }
         
       
        
    }
    
}



struct UserPhoto: View {
    
    
    var body: some View {
        
        ZStack{
            Image("photoframe")
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: 80, height: 80, alignment: .center)
            
            
            Image("userPhoto")
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: 50, height: 50, alignment: .center)
            
        }
    }
}

struct Message_And_Name: View{
    let username: String
    init(username: String){
        self.username = username
    }
    var body: some View {
        VStack(alignment: .leading){
            Text("Welcome Back").fontWeight(.thin)
            Text(username)
        }
    }
}

struct Plus_button_in_DashBoard: View{
    @State var goToAddChild = false
    @State var goToAddChore = false
    @State var goToAddContract = false
    var currentParent: Parent
    
    var body: some View{
        HStack{
            Menu {
                Button("Add child"){goToAddChild = true}
                Button("Add chore"){goToAddChore = true}
                
            } label: {
                Image("PlusIcon")
            }
            
            NavigationLink(isActive: $goToAddChild) {
                AddChildPage()
            } label: {
                EmptyView()
            }

            NavigationLink(isActive: $goToAddChore) {
                AddChorePage()
            } label: {
                EmptyView()
            }
            
            
        }
    }
}

//Menu button and drop down menu at the top left corner

struct childCard: View{
    var child: Child
//    var currentContract: ContractViewModel
    var contractResultDic: [String: Array<Int>] = [:]
    var result: [Int] = []
    @State var goToChildProfilePage = false
    
    @EnvironmentObject var authViewModel: AuthViewModel
    @EnvironmentObject var assignFinishChoresModel : AssignChoreModel
    @EnvironmentObject var contractViewModel: ContractViewModel
    
    
    init(child: Child, currentContract: ContractViewModel){
        self.child = child
//        self.currentContract = currentContract
        
        self.contractResultDic = currentContract.contractResultDic
        self.result = contractResultDic[child.userID] ?? [0]
    }
    
    
    var body: some View{
        HStack{
            Button(action: {
                self.assignFinishChoresModel.fetchFinishedChores(currentChildID: child.userID)
                goToChildProfilePage = true
             
            }, label: {
                Button_Label(currentChild: child,contractResultDic: contractResultDic).foregroundColor(Color("AdaptiveColorForText"))
            }).frame(width: UIScreen.main.bounds.width*0.95, height: UIScreen.main.bounds.width*0.3)
                .background(Color("AdaptiveColorForBackground"))
                .cornerRadius(25)
                .shadow(color: Color.gray, radius: 10)
            

            NavigationLink(destination: ChildProfilePage(currentChild: child, currentParentid: self.authViewModel.currentUser?.userID ?? "", contractViewModel: self.contractViewModel,  result: removeZero(pointArray: result)), isActive: $goToChildProfilePage){


                EmptyView()
            }
    
        }
    }
    
    
}


struct Title_and_home_Page: View{
    var body: some View{
        HStack{
            Image("home")
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: 30, height: 30, alignment: .leading)
            Text("Family List")
        }
    }
}


struct Button_Label: View{
    @EnvironmentObject var contractViewModel: ContractViewModel
    
    @State var currentChild : Child
    var contractResultDic: [String: Array<Int>] = [:]
    var result: [Int] = []
    init(currentChild: Child, contractResultDic: [String: Array<Int>]) {
        self.currentChild = currentChild
//        self.contractResultDic = contractResultDic
//        self.result = contractResultDic[currentChild.userID] ?? [0]
//        self.result = self.contractViewModel.contractResultDic[currentChild.userID] ?? [0]

        
    }
    var body: some View{

        HStack{
            if currentChild.avatarPic != nil {
                KFImage(URL(string: currentChild.avatarPic!))
                    .resizable()
                    .frame(width: 80, height: 80, alignment: .center)
                    .clipShape(Circle())
                    .aspectRatio(contentMode: .fit)
            }
            else{
                ZStack{
                    Image("photoframe")
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 80, height: 80, alignment: .center)
                    
                    
                    Image("userPhoto")
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 50, height: 50, alignment: .center)
                    
                    
                }.frame( alignment: .leading)
            }
            VStack{
                Text(currentChild.name).frame(width: UIScreen.main.bounds.width*0.6,height: UIScreen.main.bounds.width*0.16, alignment: .topLeading)
                
                HStack{
                    Spacer()
                    Image("token 5_In_DashBoard")
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 20, height: 20, alignment: .leading)
                    Text(String(self.currentChild.points))
                    Image("goal_Dashboard")
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 20, height: 20, alignment: .leading)
                    Text(String(removeZero(pointArray: result)[removeZero(pointArray: result).count - 1]))
                    Image("RewardIcon_Dashboard")
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 20, height: 20, alignment: .leading)
                    if self.contractViewModel.contractResultDic[self.currentChild.userID] == nil{
                        Text("0/0")
                    }
                    else{
                        Text("2/" + String(removeZero(pointArray: self.contractViewModel.contractResultDic[self.currentChild.userID] ?? []).count))
                    }
                }
            }
        }.frame( width: 300)
        
    }
        
}


struct Parent_Button_Label: View{
    var currentParent: Parent
    var body: some View{
        HStack{
            ZStack{
                Image("photoframe")
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: 80, height: 80, alignment: .center)
                
                
                Image("userPhoto")
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: 50, height: 50, alignment: .center)
                
            }.frame( alignment: .leading)
            VStack{
                Text(currentParent.name).frame(width: UIScreen.main.bounds.width*0.6,height: UIScreen.main.bounds.width*0.16, alignment: .topLeading)
            }
        }.frame( width: 300)
    }
}


