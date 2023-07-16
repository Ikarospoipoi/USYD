//
//  ApplicationUtil.swift
//  iosApp
//
//  Created by Edward Zheng on 19/9/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI

final class ApplicationUtil{
    static var rootViewController: UIViewController{
        guard let screen = UIApplication.shared.connectedScenes.first as? UIWindowScene else{
            return .init()
            
        }
        
        guard let root = screen.windows.first?.rootViewController else{
            return .init()
        }
        
        return root
    }
}
