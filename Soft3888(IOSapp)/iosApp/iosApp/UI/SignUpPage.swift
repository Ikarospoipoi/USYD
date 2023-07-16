//
//  SignUpPage.swift
//  iosApp
//
//  Created by Edward Zheng on 22/8/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import Firebase

struct SignUpPage: View {
    @State var name = ""
    @State var email = ""
    @State var password = ""
    @State var dateOfBirth: Date?
    
    
    @State var textFieldHeight: CGFloat = 0
    @State var goToLogin = false
    
    @EnvironmentObject var  authViewModel: AuthViewModel
    
    var body: some View {
        ZStack{
//            Image("Background").resizable().scaledToFill().ignoresSafeArea().opacity(0.2)
            
            VStack{
                
                VStack(alignment: .center) {
                    Text("Let's Get Started")
                        .font(.largeTitle)
                    .fontWeight(.bold)
                    Text("Create account with On Task Achievers")
                        .font(.caption2)
                        .fontWeight(.thin)
                        .padding(.bottom)
                }
                .padding(.bottom, 10)
                
                EntryField(textValue: $email, icon: Image("emailIcon"), placeholder: "email", prompt: authViewModel.emailPrompt, validation: $authViewModel.emailValid, isPassword: false)

                EntryField(textValue: $password, icon: Image("locksign"), placeholder: "password", prompt: authViewModel.passwordPrompt, validation: $authViewModel.passwordValid, isPassword: true)
                
                EntryField(textValue: $name, icon: Image("userIcon"), placeholder: "name", prompt: authViewModel.namePrompt, validation: $authViewModel.nameValid, isPassword: false)
                    .overlay(GeometryReader{
                        proxy -> AnyView in
                        DispatchQueue.main.async {
                            textFieldHeight = proxy.size.height
                        }
                        return AnyView(EmptyView())
                        
                    })
                
                
//                struct for the datepicker textfield
//                the height is got from the GeometryReader above
                VStack {
                    HStack {
                        Image("dateIcon")
                        DatePickerTextField(placeholder: "date", date: $dateOfBirth)
                    }
                    .padding(8)
                    .background(Color(UIColor.secondarySystemBackground))
                    .overlay(RoundedRectangle(cornerRadius: 8).stroke(Color.gray, lineWidth: 1))
                    .frame(width: UIScreen.main.bounds.width*0.8, height: textFieldHeight)
                    .border(.red, width: CGFloat(authViewModel.dateOfBirthValid))
                    
                    Text(authViewModel.dateOfBirthPrompt)
                        .font(.caption)
                        .frame(width: UIScreen.main.bounds.width*0.8, alignment: .leading)
                }
             
                
                
                HStack {
                    Text("Have an account? ")
                        .font(.footnote)
                        .fontWeight(.thin)
                    
                    Button(action: {
                        goToLogin = true
                    }, label: {
                        Text("Login")
                            .font(.footnote)
                            .fontWeight(.thin)
                    })
                    
                    NavigationLink(destination: ParentLoginPage(), isActive: $goToLogin){
                        EmptyView()
                    }
                    
                    
                
                }
                .padding(8)
                .frame(width: UIScreen.main.bounds.width*0.8, alignment: .leading)
                
                
                Button(action: {authViewModel.register(email: email, password: password, name: name, dateOfBirth: dateOfBirth)},
                       label: {
                    Image("signUpBtn")
                })
                .frame(width: UIScreen.main.bounds.width*0.8)
                .cornerRadius(8)
                
                
                Image("separateLine")
                
                
                HStack{
                    Spacer()
                    Image("AppleLoginBtn")
                    Spacer()
                    Image("GoogleLoginBtn")
                    Spacer()
                    Image("FacebookLoginBtn")
                    Spacer()
                }
                
            }
            
        }
        .background(Image("Background").ignoresSafeArea().opacity(0.2))
        .onTapGesture {
            hideKeyboard()
        }
        
    }
    
    
}

struct SignUpPage_Previews: PreviewProvider {
    static var previews: some View {
        SignUpPage()
    }
    
    
}


struct EntryField: View {
    @Binding var textValue: String
    var icon: Image
    var placeholder: String
    var prompt: String
    @Binding var validation: Float
    var isPassword: Bool
    
    
    var body: some View {
        HStack{
            icon
            if !isPassword{
                TextField(placeholder, text: $textValue).autocapitalization(.none)
            }
            else{
                SecureField(placeholder, text: $textValue).autocapitalization(.none)
                
            }
        }
        .padding(8)
        .background(Color(UIColor.secondarySystemBackground))
        .border(.red, width: CGFloat(validation))
        .overlay(RoundedRectangle(cornerRadius: 8).stroke(Color.gray, lineWidth: 1))
        .frame(width: UIScreen.main.bounds.width*0.8)
        
        Text(prompt)
            .font(.caption)
            .frame(width: UIScreen.main.bounds.width*0.8, alignment: .leading)
    }
}
