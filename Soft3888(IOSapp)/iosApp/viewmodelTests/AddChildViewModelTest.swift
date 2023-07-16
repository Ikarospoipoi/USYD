//
//  AddChildViewModelTest.swift
//  viewmodelTests
//
//  Created by Edward Zheng on 23/10/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import XCTest

final class AddChildViewModelTest: XCTestCase {

    func testAddChild() {
        let authViewModel = AuthViewModel()
        authViewModel.register(email: "testAddChild@gmail.com", password: "testing", name: "addChildTest", dateOfBirth: "23/10/2022")
        let viewmodel = AddChildViewModel()
        XCTAssertEqual(viewmodel.children.count(), 0)
        
        let formatter = DateFormatter()
        formatter.dateFormat = "dd/MM/yyyy"
        let birthday = formatter.date(from: "23/10/2022")
        viewmodel.addChild(name: "kid1", dateOfBirth: birthday, theme: "default")
        XCTAssertEqual(viewmodel.children.count(), 1)
        let addedChild = viewmodel.children[0]
        XCTAssertEqual(addedChild.name, "kid1")
        XCTAssertEqual(addedChild.dateOfBirth, "23/10/2022")
        XCTAssertEqual(addedChild.theme, "default")
    }

}
