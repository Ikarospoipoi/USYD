//
//  DatePickerTextField.swift
//  iosApp
//
//  Created by Edward Zheng on 25/8/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct DatePickerTextField: UIViewRepresentable {
//    typealias UIViewType = UITextField
    
    private let textField = UITextField(frame: CGRect(x: 10, y: 60, width: 10, height: 10))
    private let datePicker = UIDatePicker()
    private let helper = Helper()
    private let dateFormatter: DateFormatter = {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy/MM/dd"
        return dateFormatter
    }()
    
    public var placeholder: String
    
    @Binding var date: Date?
    
    func makeUIView(context: Context) -> UITextField {
        self.datePicker.datePickerMode = .date
        self.datePicker.maximumDate = Date()
        self.datePicker.preferredDatePickerStyle = .wheels
        self.datePicker.addTarget(self.helper,
                                  action: #selector(self.helper.dateValueChanged),
                                  for: .valueChanged)
        
        self.textField.placeholder = self.placeholder
        self.textField.inputView = self.datePicker
        
//        Accessory view
        let toolbar = UIToolbar()
        toolbar.sizeToFit()
        let flexibleSpace = UIBarButtonItem(barButtonSystemItem: .flexibleSpace, target: nil, action: nil)
        let doneButton = UIBarButtonItem(title: "Done", style: .plain, target: self.helper, action: #selector(self.helper.doneButtonAction))
        toolbar.setItems([flexibleSpace, doneButton], animated: true)
        self.textField.inputAccessoryView = toolbar
        
        
        self.helper.dateChanged = {
            self.date = self.datePicker.date
        }
        
        self.helper.doneButtonTapped = {
            if self.date == nil{
                self.date = self.datePicker.date
            }
            self.textField.resignFirstResponder()
        }
        
        return self.textField
    }
    
    func updateUIView(_ uiView: UITextField, context: Context) {
        if let selectedDate = self.date {
            uiView.text = self.dateFormatter.string(from: selectedDate)
        }
    }
    
    func makeCoordinator() -> Coordinator {
        Coordinator()
    }
    
    class Helper{
        
        public var dateChanged: (() -> Void)?
        public var doneButtonTapped: (() -> Void)?
        
        
        @objc func dateValueChanged() {
            self.dateChanged?()
        }
        @objc func doneButtonAction(){
            self.doneButtonTapped?()
        }
    }
    
    class Coordinator{
        
    }
}
