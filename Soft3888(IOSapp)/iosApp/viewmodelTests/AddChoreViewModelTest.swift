//
//  AddChoreViewModelTest.swift
//  viewmodelTests
//
//  Created by Edward Zheng on 23/10/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import XCTest

final class AddChoreViewModelTest: XCTestCase {

    func testAddChore(){
        let authViewModel = AuthViewModel()
        authViewModel.register(email: "testAddChore@gmail.com", password: "testing", name: "addChoreTest", dateOfBirth: "23/10/2022")
        
        let viewmodel = ChoreViewModel()
        let choreImage = UIImage()
        XCTAssertEqual(viewmodel.chores.count, 0)
        
        viewmodel.addChore(choreName: "test", choreImage: choreImage)
        XCTAssertEqual(viewmodel.chores.count, 1)
        let addedChore = viewmodel.chores[0]
        XCTAssertEqual(addedChore.name, "test")
        XCTAssertNotNil(addedChore.iconImage)
        
    }

}
