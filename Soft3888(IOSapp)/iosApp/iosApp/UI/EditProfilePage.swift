//
//  EditProfilePage.swift
//  iosApp
//
//  Created by Edward Zheng on 18/10/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct EditChildProfileSheet: View{
    @State var currentChild: Child
    @State var currentParentid: String
    
    @State var newDateOfBirth: Date?
    @State var newUserName: String = ""
    @State var newTheme: String = ""
    @State var textFieldHeight: CGFloat = 0
    @State var userImage: UIImage?
    
    @State var showingLocalImage = false
    
    
    @EnvironmentObject var childUserEditModel : editUserInfoModel
    
    var body: some View{
        VStack{
            Button(action: {
                showingLocalImage.toggle()
            }, label: {
                VStack{
                    if let image = self.userImage{
                        Image(uiImage: userImage!)
                            .resizable()
                            .frame(width: 200, height: 200)
                            .scaledToFill()
                            .cornerRadius(10)
                    }else{
                        Image("edituserphoto")
                            .resizable()
                            .frame(width: 200, height: 200)
                            .scaledToFill()
                            
                    }
                }
            }).frame(width: 200, height: 200, alignment: .center)
          
            HStack{
                Image("userIcon").resizable().frame(width: 32.0, height: 32.0)
                TextField("Name", text: $newUserName)
            }.underlinetextfield()
          
            HStack{
                Image("dateIcon").resizable().frame(width: 32.0, height: 32.0)
                DatePickerTextField(placeholder: "Date of Bitrh", date: $newDateOfBirth)
            }  .padding(.vertical, 20)
                .overlay(Rectangle().frame(width: 360, height: 2).padding(.top, 50))
                .foregroundColor(Color.black)
                .frame(width: UIScreen.main.bounds.width*0.95, height: textFieldHeight)
                .padding(10)
                
                    
            HStack{
                Image("brush").resizable().frame(width: 32.0, height: 32.0)
                TextField("Theme", text: $newTheme)
            }.underlinetextfield()
            
            Button(action: {
                childUserEditModel.editChildInfo(parentID: currentParentid, childID: self.currentChild.userID, childName: newUserName, dateOfBirth: newDateOfBirth, theme: newTheme, imageData: userImage)
                
//                                childUserEditModel.fetchChildren()
//
//
//                                childUserEditModel.getNewestChildInfo(childID: self.childAuthViewModel.currentChild.userID)
                self.childUserEditModel.isEditSheetPresent = false
               
                
            },
                   label: {
                Image("UpdateProfileBtn")
            })
            .padding(.bottom, UIScreen.main.bounds.height*0.03)
               
           
        }
        .fullScreenCover(isPresented: $showingLocalImage, content: {
        ImagePicker(image: $userImage)
    })
    }
}

struct EditParentProfileSheet: View{
    
    @State var newDateOfBirth: Date?
    @State var newUserName: String = ""
    @State var newTheme: String = ""
    @State var textFieldHeight: CGFloat = 0
    @State var userImage: UIImage?
    
    @State var showingLocalImage = false
    
    @EnvironmentObject var authViewModel : AuthViewModel
    
    var body: some View{
        VStack{
            Button(action: {
                showingLocalImage.toggle()
            }, label: {
                VStack{
                    if let image = self.userImage{
                        Image(uiImage: userImage!)
                            .resizable()
                            .frame(width: 200, height: 200)
                            .scaledToFill()
                            .cornerRadius(10)
                    }else{
                        Image("edituserphoto")
                            .resizable()
                            .frame(width: 200, height: 200)
                            .scaledToFill()
                            
                    }
                }
            }).frame(width: 200, height: 200, alignment: .center)
          
            HStack{
                Image("userIcon").resizable().frame(width: 32.0, height: 32.0)
                TextField("Name", text: $newUserName)
            }.underlinetextfield()
          
            HStack{
                Image("dateIcon").resizable().frame(width: 32.0, height: 32.0)
                DatePickerTextField(placeholder: "Date of Bitrh", date: $newDateOfBirth)
            }  .padding(.vertical, 20)
                .overlay(Rectangle().frame(width: 360, height: 2).padding(.top, 50))
                .foregroundColor(Color.black)
                .frame(width: UIScreen.main.bounds.width*0.95, height: textFieldHeight)
                .padding(10)
                
                    
            HStack{
                Image("brush").resizable().frame(width: 32.0, height: 32.0)
                TextField("Theme", text: $newTheme)
            }.underlinetextfield()
            
            Button(action: {
                self.authViewModel.editParentInfo(newName: newUserName, newDateOfBirth: newDateOfBirth, newTheme: newTheme, newAvatarPic: userImage)
                self.authViewModel.isEditSheetPresent = false
            },
                   label: {
                Image("UpdateProfileBtn")
            })
            .padding(.bottom, UIScreen.main.bounds.height*0.03)
               
           
        }
        .fullScreenCover(isPresented: $showingLocalImage, content: {
        ImagePicker(image: $userImage)
    })
    }
}
