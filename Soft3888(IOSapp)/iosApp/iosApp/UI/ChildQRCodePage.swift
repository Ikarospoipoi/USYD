//
//  ChildQRCodePage.swift
//  iosApp
//
//  Created by chris on 5/9/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared
import CoreImage.CIFilterBuiltins
import Foundation

import Firebase

import shared
import CryptoKit
import AuthenticationServices







struct ChildQRCodePage: View {
    var child : Child
    @State var goToChildDashboard = false
    
    
    let filter = CIFilter.qrCodeGenerator()
    let context = CIContext()
    
    func generateQRCodeImage(_ url : String) -> UIImage {
        let data = Data(url.utf8)
        
        filter.setValue(data, forKey: "inputMessage")
        
        if let qrCodeImage = filter.outputImage {
            if let qrCodeCGImage = context.createCGImage(qrCodeImage, from: qrCodeImage.extent){
                return UIImage(cgImage: qrCodeCGImage)
            }
            
        }
        return UIImage(systemName: "xmark") ?? UIImage()
    }
    
    
    
    var body: some View {
        ZStack{
            Image("Background").resizable().edgesIgnoringSafeArea(.all)
                .opacity(0.2)
            VStack{
                let info = child.userID + " " + Auth.auth().currentUser!.uid
                Image(uiImage: generateQRCodeImage(info)).interpolation(.none).resizable().frame(width: 330, height: 330, alignment: .center)
                Button(action: {goToChildDashboard = true}, label: {Text("Please scan the QR code using " + child.name + " device")})
                
            }
        }
        //var li = [child10, child12, child11]
//        NavigationLink(destination: ChildNavigationBarView(username: child.name,childList: li).ignoresSafeArea(), isActive: $goToChildDashboard){
//            EmptyView()
//        }.navigationBarHidden(true)
    }
   
   
    
    
    
}

struct ChildQRCodePage_Previews: PreviewProvider {
    static var previews: some View {
        ChildQRCodePage(child: Child(userID: "0", name: "Chris", dateOfBirth: "1234/05/06", chooseTheme: Theme(name: "Devil May Cry"), avatarPic: "Nero", points: 0))
        
    }
}
