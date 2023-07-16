//
//  ParentProfilePage.swift
//  iosApp
//
//  Created by Edward Zheng on 30/8/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared
import Kingfisher

struct ParentProfilePage: View {
    @State var numberOfChildren = "2"
    @State var numberOfRelatives = "2"
    @EnvironmentObject var authViewModel: AuthViewModel
    @EnvironmentObject var choreViewModel: ChoreViewModel
    @EnvironmentObject var childUserEditModel : editUserInfoModel
    
    
    var body: some View {
        
        ZStack {
            Image("Background").resizable().edgesIgnoringSafeArea(.all)
                .opacity(0.2)
            
            NavigationView {
                ScrollView {
                    VStack{
                        VStack {
                            
                            AvatorBar(numberOfChildren: $numberOfChildren, numberOfRelatives: $numberOfRelatives)
                            Text("Date of Birth: \(self.authViewModel.currentUser?.dateOfBirth ?? "")")
                                .frame(width: UIScreen.main.bounds.width*0.9, alignment: .leading)
                            Text("Choosen theme: \(self.authViewModel.currentUser?.chooseTheme?.name ?? "")")
                                .frame(width: UIScreen.main.bounds.width*0.9, alignment: .leading)
                            
                        }
                        .padding(.top, 4)
                        
                        Button(action: {self.authViewModel.isEditSheetPresent.toggle()
                            
                        }, label: {
                            Text("Edit profile").foregroundColor(Color("AdaptiveColorForText"))
                                .frame(width: UIScreen.main.bounds.width*0.9, height: 27)
                                .background(Rectangle().fill(Color("AdaptiveColorForBackground")).shadow(color: Color.gray, radius: 1))
                                
                        })
                        .padding(.bottom)
                        .sheet(isPresented: self.$authViewModel.isEditSheetPresent) {
                            EditParentProfileSheet()
                        }
                        
                        
                        //                display all chores
                        LazyVStack {
                            ForEach(choreViewModel.chores, id: \.self){chore in
                                
                                ChoreCard(chore: chore)
                                    .frame(width: UIScreen.main.bounds.width*0.95, height: UIScreen.main.bounds.width*0.3)
                                    .background(Color("AdaptiveColorForBackground"))
                                    .cornerRadius(25)
                                    .shadow(color: Color.gray, radius: 10)
                                    
                                
                            }
                        }
                    }

                }
                .background(Image("Background").ignoresSafeArea().opacity(0.2))
                .navigationTitle(authViewModel.currentUser?.name ?? "")
                    .toolbar{Menu {
                        Button(action: { authViewModel.signOut()}, label: {
                            Text("sign out")
                        })} label: {
                            Image("PlusIcon-ChildProfilePage")
                        }}
            }
        }
        
    }
}

//struct ParentProfilePage_Previews: PreviewProvider {
//    static var previews: some View {
//        let achievement = Achievement(points: 1, message: "free three")
//        let chore = ChoreTask(taskID: "0", name: "Make bed", description: "Make bed", achievement: achievement, iconImage: "BedIcon")
//        Group {
//            ParentProfilePage()
//                .preferredColorScheme(.light)
//            ParentProfilePage()
//                .preferredColorScheme(.dark)
//        }
//    }
//}


struct AvatorBar: View {
    @Binding var numberOfChildren: String
    @Binding var numberOfRelatives: String
    @EnvironmentObject var authViewModel: AuthViewModel
    @EnvironmentObject var addChildViewModel: AddChildViewModel
    
    var body: some View {
        HStack{
            if self.authViewModel.currentUser?.avatarPic != nil{
                KFImage(URL(string: self.authViewModel.currentUser!.avatarPic!))
                    .resizable()
                    .frame(width: 80, height: 80)
                    .padding(.horizontal, 7)
                    .clipShape(Circle())
            }
            else{
                Image("parentImage")
                    .resizable()
                    .frame(width: 80, height: 80)
                    .padding(.horizontal, 7)
                    .clipShape(Circle())
            }
            Spacer()
            HStack {
                HStack{
                    Image("ChildrenNumberIcon")
                    Text(String(self.addChildViewModel.children.count))
                }
                .padding(.horizontal, 8)
                HStack{
                    Image("RelativeNumberIcon")
                    Text(numberOfRelatives)
                }
                .padding(.horizontal, 8)
            }
            .frame(width: UIScreen.main.bounds.width*0.7, alignment: .leading)
        }
        .frame(width: UIScreen.main.bounds.width*0.9)
    }
}

struct ChoreCard: View {
    var chore: ChoreTask
    var body: some View {
        HStack{
            KFImage(URL(string: chore.iconImage))
                .resizable()
                .frame(width: 59, height: 59)
                .padding(.horizontal, 7)
            
            
            VStack(alignment: .leading){
                Text(chore.name)
                    .font(.title)
                    .fontWeight(.semibold)
                    .padding(.top)
                Spacer()
                HStack{
//                    Text(chore.achievement.message + ":")
//                    Text(String(chore.achievement.points))
                    
                    //                                    Image("PointIcon")
                }
                
                
                Text(chore.createdDate)
                    .frame(width: UIScreen.main.bounds.width*0.85 - 59,  alignment: .trailing)
                    .padding(.bottom)
            }
            
        }
        
    }
}
