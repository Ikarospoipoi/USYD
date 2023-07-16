//
//  KeyboardExtension.swift
//  iosApp
//
//  Created by Edward Zheng on 3/9/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

extension View {
    func hideKeyboard() {
        UIApplication.shared.sendAction(
            #selector(UIResponder.resignFirstResponder),
            to: nil,
            from: nil,
            for: nil
        )
    }
}

