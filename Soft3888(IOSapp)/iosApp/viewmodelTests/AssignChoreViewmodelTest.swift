//
//  AssignChoreViewmodelTest.swift
//  viewmodelTests
//
//  Created by Edward Zheng on 23/10/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import XCTest

final class AssignChoreViewmodelTest: XCTestCase {

    func testAssignChore(){
        let authViewModel = AuthViewModel()
        authViewModel.register(email: "testAddChore@gmail.com", password: "testing", name: "addChoreTest", dateOfBirth: "23/10/2022")
        
        let addChildViewModel = AddChildViewModel()
        let formatter = DateFormatter()
        formatter.dateFormat = "dd/MM/yyyy"
        let birthday = formatter.date(from: "23/10/2022")
        viewmodel.addChild(name: "kid1", dateOfBirth: birthday, theme: "default")
        
        let viewmodel = AssignChoreModel()
        let child = addChildViewModel.children[0]
        XCTAssertEqual(viewmodel.finishedChoreList.count, 0)
        
        viewmodel.assigneFinishdChore(currentChildID: child.userID, choreName: "testChore", StringImageData: "", selectPoint: 1)
        XCTAssertEqual(viewmodel.finishedChoreList.count, 1)
        let finishedChore = viewmodel.finishedChoreList[0]
        XCTAssertEqual(finishedChore.name, "testChore")
        XCTAssertEqual(finishedChore.point, 1)
        
    }

}
