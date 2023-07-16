//
//  RewardCreater.swift
//  iosApp
//
//  Created by Frank Shi on 1/9/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

import SwiftUI

class RewardCreater: ObservableObject, Identifiable{
    //init with UUID
//    var id = UUID()
    @Published var name: String = ""
    @Published var point: String = ""
    
    
//    func Add_To_List(reward: RewardCreategdggr) -> [RewardCreater]{
//        rewardList.append(reward)
//        return rewardList
//    }
}


class ContractCreater: ObservableObject{
    @Published var maxpoint: String
    @Published var description: String
    @Published var rewardArray: [RewardCreater] = []
    
    public init(){
        self.maxpoint = "0"
        self.description = "Null"
        var reward1 = RewardCreater()
        var reward2 = RewardCreater()
        var reward3 = RewardCreater()
        self.rewardArray.append(reward1)
        self.rewardArray.append(reward2)
        self.rewardArray.append(reward3)
    }
    
    func append_new_reward(newReward: RewardCreater){
        rewardArray.append(newReward)
    }
    
    func get_rewardArray() -> Array<String>{
        var rewardItemArray: [String] = []
        for reward in rewardArray{
            rewardItemArray.append(reward.name)
        }
        return rewardItemArray
    }
    
    func get_pointArray() -> Array<Int>{
        var pointArray: [Int] = []
        for reward in rewardArray{
            pointArray.append(Int(reward.point) ?? 0)
        }
        return pointArray
    }
}
