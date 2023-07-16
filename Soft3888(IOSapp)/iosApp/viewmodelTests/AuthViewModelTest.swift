//
//  viewmodelTests.swift
//  viewmodelTests
//
//  Created by Edward Zheng on 23/10/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

@testable import iosApp
import XCTest
//@testable import shared

class AuthViewModelTest: XCTestCase{
    func testRegisterWithEmail() {
        let viewmodel = AuthViewModel()
        let formatter = DateFormatter()
        formatter.dateFormat = "dd/MM/yyyy"
        let birthday = formatter.date(from: "23/10/2022")
        viewmodel.register(email: "testing@gmail.com", password: "testing",name: "testing", dateOfBirth: birthday)
        let loggedinUser = Parent(userID: viewmodel.userSession.uid, name: "testing", dateOfBirth: dateOfBirth, email: "testing@gmail.com", chooseTheme: nil, avatarPic: nil)
        
        XCTAssertEqual(viewmodel.currentUser.name, loggedinUser.name)
        XCTAssertEqual(viewmodel.currentUser.email, loggedinUser.email)
        XCTAssertEqual(viewmodel.currentUser.dateOfBirth, loggedinUser.dateOfBirth)
        XCTAssertEqual(viewmodel.currentUser.chooseTheme, loggedinUser.chooseTheme)
        XCTAssertEqual(viewmodel.currentUser.avatarPic, loggedinUser.avatarPic)
    }
    
    func testLoginWithEmail(){
        let viewmodel = AuthViewModel()
        viewmodel.loginWithEmail(email: "testing@gmail.com", password: "testing")
        let birthday = "23/10/2022"
        let loggedinUser = Parent(userID: viewmodel.userSession.uid, name: "testing", dateOfBirth: birthday, email: "testing@gmail.com", chooseTheme: nil, avatarPic: nil)
        
        XCTAssertEqual(viewmodel.currentUser.name, loggedinUser.name)
        XCTAssertEqual(viewmodel.currentUser.email, loggedinUser.email)
        XCTAssertEqual(viewmodel.currentUser.dateOfBirth, loggedinUser.dateOfBirth)
        XCTAssertEqual(viewmodel.currentUser.chooseTheme, loggedinUser.chooseTheme)
        XCTAssertEqual(viewmodel.currentUser.avatarPic, loggedinUser.avatarPic)
    }
    
    
}
