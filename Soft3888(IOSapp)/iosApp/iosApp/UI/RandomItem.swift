//
//  RadomItem.swift
//  iosApp
//
//  Created by chris on 31/8/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

struct RandomItem: Identifiable {
    var id:UUID = UUID()
    var title: String
    var createTime: Date = Date()
}

