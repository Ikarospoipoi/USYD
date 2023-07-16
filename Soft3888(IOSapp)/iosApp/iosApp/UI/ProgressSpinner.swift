//
//  ProgressSpinner.swift
//  iosApp
//
//  Created by Edward Zheng on 19/9/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct ProgressSpinner: View {
    @EnvironmentObject var authViewModel: AuthViewModel
    @EnvironmentObject var addChoreViewModel: ChoreViewModel
    @EnvironmentObject var addChildViewModel: AddChildViewModel
    var body: some View {
        if authViewModel.progressing || addChoreViewModel.processing || addChoreViewModel.processing{
            ProgressView()
        }
    }
}

struct ProgressSpinner_Previews: PreviewProvider {
    static var previews: some View {
        ProgressSpinner()
    }
}
