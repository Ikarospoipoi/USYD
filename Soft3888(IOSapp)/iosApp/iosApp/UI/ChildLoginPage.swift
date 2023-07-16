//
//  childLoginPage.swift
//  onTaskArchiever
//
//  Created by chris on 22/8/2022.
//

import SwiftUI

struct ChildLoginPage: View {
    var body: some View {
       ShowQRCode()
    }
}



struct ChildLoginPage_Previews: PreviewProvider {
    static var previews: some View {
        ChildLoginPage()
    }
}

struct ShowQRCode: View {
    var body: some View{
        ZStack{
            Image("Background").resizable().scaledToFill().ignoresSafeArea()
            VStack{
                Image("QRCodeView")
                Text("Please scan the QR code on your\n" +
                     "              parent’s device")
                .font(.body)
                .fontWeight(.regular)
                .multilineTextAlignment(.center)
            
        
            }
        }
    }
}

