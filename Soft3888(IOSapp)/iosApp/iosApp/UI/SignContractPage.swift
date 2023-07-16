//
//  AddContractPage.swift
//  iosApp
//
//  Created by Frank Shi on 31/8/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared
import Combine
import Kingfisher



struct SignContractPage: View {
    var child: Child
    @EnvironmentObject var authViewModel: AuthViewModel
    @ObservedObject var contractCreator = ContractCreater()
    @ObservedObject var errormessage = errormessagehandler()
    
    init(child: Child ){
        self.child = child
    }
    var body: some View {
        
        NavigationView{
            GeometryReader{ geo in
                Image("Background").resizable().ignoresSafeArea().opacity(0.2)
                    .onTapGesture {
                        hideKeyboard()
                    }
                ScrollView{
                    VStack{
                        Username_and_Avator(username: child.name ?? "", currentChild: self.child)
                        
                        
                    }.frame(width: UIScreen.main.bounds.width*0.8, height: UIScreen.main.bounds.height*0.2, alignment: .leading)
                    
                    VStack{
                        Contarct_Detail_View(child: child,maxpoint: "", description: "", contractCreator: contractCreator, errormessagehandler: errormessage)
                            .frame(width: UIScreen.main.bounds.width, alignment: .center)
                    }
                }
            }
            .navigationBarHidden(true)
            .background(Image("Background").ignoresSafeArea().opacity(0.2))
            
        }
    }
}

struct Username_and_Avator: View {
    
    var username: String
    @State var currentChild: Child
    
    
//    init(username: String){
//        self.username = username
//    }
    
    var body: some View{
        VStack(alignment: .leading){
            HStack{
                Text(username)
            }
            HStack{
                if currentChild.avatarPic != nil{
                    KFImage(URL(string: currentChild.avatarPic!))
                        .resizable()
                        .frame(width: 80, height: 80, alignment: .center)
                        .clipShape(Circle())
                        .aspectRatio(contentMode: .fit)
                }
                else{
                    UserPhoto_AddContractPage()
                }
            }
        }
    }
}


struct UserPhoto_AddContractPage: View {
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




struct Contarct_Detail_View: View{
    @State var showingAlert: Bool = false
    var child: Child
    @EnvironmentObject var authViewModel: AuthViewModel
    @State var isAdd : Bool = false
    @State var maxpoint: String
    @State var description: String
    @State var name  : String = ""
    @State var point : String = ""
    @State var goToDashboardPage = false
    @ObservedObject var contractCreator: ContractCreater
    @ObservedObject var errormessagehandler: errormessagehandler
    
    var uploadContract = UploafContract()
    
    
    
    var body: some View{
        VStack{
            VStack{
                HStack{
                    VStack{
                        
                        VStack(alignment: .leading) {
                            HStack{
                                Image("goal_Dashboard")
                                    .resizable()
                                    .aspectRatio(contentMode: .fit)
                                    .frame(width: 20, height: 20, alignment: .leading)
                                Text("Target")
                                    .fontWeight(.light)
                            }.frame(width: UIScreen.main.bounds.width*0.6, alignment: .topLeading)
                            
                            TextField("Max Point", text: $maxpoint)
                                .padding()
                                .frame(width: UIScreen.main.bounds.width*0.5,
                                       height:UIScreen.main.bounds.height*0.05,alignment: .leading)
                            
                                .border(Color.gray.opacity(0.15), width: 2)
                                .cornerRadius(6)
                                .keyboardType(.decimalPad)
                        }
                        
                        
                    }
                    Image("contract_DashBoard")
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 50, height: 50, alignment: .topLeading)
                }.frame(width: UIScreen.main.bounds.width*0.8,height:UIScreen.main.bounds.height*0.17,alignment: .topLeading)
                
                
                
                //This is LOOP to create input
                ForEach(Array(contractCreator.rewardArray.enumerated()), id: \.offset) { index, element in
                    HStack{
                        VStack{
                            HStack{
                                Image("RewardIcon_ContractPage")
                                    .resizable()
                                    .frame(width: 20, height: 20,alignment: .trailing)
                                Text("Award \(index + 1):")
                                    .fontWeight(.light)
                                    .frame(width: UIScreen.main.bounds.width*0.45, alignment: .bottomLeading)
                            }.frame(width: UIScreen.main.bounds.width*0.5,alignment: .bottomLeading)
                            
                            TextField("Award", text: $contractCreator.rewardArray[index].name)
                                .padding()
                                .frame(width: UIScreen.main.bounds.width*0.5,
                                       height:UIScreen.main.bounds.height*0.05)
                                .border(Color.gray.opacity(0.15), width: 2)
                                .cornerRadius(6)
                            
                        }.frame(alignment: .leading)
                        
                        VStack{
                            HStack{
                                Image("token 5_In_DashBoard")
                                    .resizable()
                                    .aspectRatio(contentMode: .fit)
                                    .frame(width: 20, height: 20, alignment: .leading)
                                Text("Point:")
                                    .fontWeight(.light)
                            }.frame(width: UIScreen.main.bounds.width*0.3, alignment:.bottomLeading)
                            
                            
                            HStack{
                                TextField("Point", text: $contractCreator.rewardArray[index].point)
                                //                                .onChange(of: contractCreator.rewardArray[index].$point, perform: { text in
                                //                                    self.contractCreator.rewardArray[index].point = text
                                //                                })
                                    .padding()
                                    .frame(width: UIScreen.main.bounds.width*0.3,
                                           height:UIScreen.main.bounds.height*0.05,alignment: .leading)
                                    .border(Color.gray.opacity(0.15), width: 2)
                                    .cornerRadius(6)
                                    .keyboardType(.decimalPad)
                                //                                .keyboardType(.numberPad)
                                //                                .onReceive(Just($contractCreator.rewardArray[index].point)) { newValue in
                                //                                    let filtered = newValue.filter { "0123456789".contains($0) }
                                //                                    if filtered != newValue {
                                //                                        contractCreator.rewardArray[index].point = filtered
                                //                                    }
                                //
                                //                                }
                            }
                        }
                    }
                    .frame(width: UIScreen.main.bounds.width*0.8)
                    
                }
                
                
                
                Button(action: {
                    //                var createReward = RewardCreater()
                    //                rewardList.append(createReward)
                    var newReward = RewardCreater()
                    contractCreator.append_new_reward(newReward: newReward)
                    
                    
                }, label: {
                    Image("Add_Contract")
                }).frame(width: UIScreen.main.bounds.width*0.8, height:40)
                    .background(Color.blue.opacity(0.7))
                    .cornerRadius(8)
                
                VStack{
                    HStack{
                        Image("reviewIcon_DashBoard")
                        Text("Description")
                    }.frame(width:UIScreen.main.bounds.width*0.8, height: UIScreen.main.bounds.height*0.03, alignment: .topLeading)
                    TextEditor(text: $description)
                    
                        .frame(width: UIScreen.main.bounds.width*0.8, height: UIScreen.main.bounds.height*0.2)
                        .background(Color.white)
                        .cornerRadius(10)
                        .border(Color.gray.opacity(0.15), width: 4)
                        .cornerRadius(10)
                }
                
            }.frame(width: UIScreen.main.bounds.width * 0.8, alignment: .center)
                .padding()
                .background(Color.white)
            
                .clipped()
                .clipShape(RoundedRectangle(cornerRadius:20))
                .shadow(color: Color.gray, radius: 10, x: 0, y: 0)
            
            
            Button(action: {
                
                
                var pointArray = contractCreator.get_pointArray()
                var rewardArray = contractCreator.get_rewardArray()
                var UploadContract = UploafContract()
                
                
                
                var checkMaxPoint = checkMaxpoint(maxPoint: maxpoint)
                var checkRewardarrayLength = checkRewardarrayLength(rewardArray: rewardArray)
                var checkPointarray = checkPointarray(pointArray: pointArray)
                var checkPointSequnece = checkPointSequnece(pointArray: pointArray)
                var checkCorrespond = checkCorrespond(rewardArray: rewardArray, pointArray: pointArray)
                if(checkMaxPoint != 1){
                    showingAlert = true
                    errormessagehandler.message = "Please Check: \n1. Max Point should not be null\n2.Max Point should not over 1000 "
                }
                else if (checkRewardarrayLength != 1){
                    showingAlert = true
                    errormessagehandler.message = "Please fill at least one reward"
                }else if (checkPointarray != 1){
                    showingAlert = true
                    errormessagehandler.message = "Please Fill at least one point"
                }else if(checkPointSequnece != 1){
                    showingAlert = true
                    errormessagehandler.message = "Points must be Incremental"
                }else if (checkCorrespond != 1){
                    showingAlert = true
                    errormessagehandler.message = "The Point and Reward is not correspond"
                }else if (checkMaxpointMatch(pointArray: pointArray, maxpoint: Int(maxpoint) ?? 0) != 1){
                    showingAlert = true
                    errormessagehandler.message = "The Final Point should be same as the MaxPoint"
                }else{
                    uploadContract.removeContract(childID: child.userID){(success) -> Void in
                        if success {
                            // do second task if success
                            
                            uploadContract.addContract(childName: child.name , childID: child.userID, maxPoint: Int(maxpoint) ?? 0, rewardArray: rewardArray, pointArray: pointArray, decription: description, parentID: authViewModel.currentUser?.userID ?? "", parentName: authViewModel.currentUser?.name ?? "")
                            goToDashboardPage = true
                            
                        }
                    }
                }
                
                
                
            }, label: {
                Text("ADD")
                    .fontWeight(.bold)
            })
            .alert(isPresented: $showingAlert){
                Alert(
                    title: Text("Error"),
                    message: Text(errormessagehandler.message)
                )
            }
            .frame(width: UIScreen.main.bounds.width*0.8, height:40)
            .background(Color.blue.opacity(0.7))
            .foregroundColor(Color.white)
            .cornerRadius(8)
            
            NavigationLink(isActive: $goToDashboardPage) {
                DashBoardPage()
            } label: {
                EmptyView()
            }.navigationBarBackButtonHidden(true)
        }
    }
    
}





